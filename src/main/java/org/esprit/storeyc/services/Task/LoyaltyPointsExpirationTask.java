package org.esprit.storeyc.services.Task;

import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.entities.User;
import org.esprit.storeyc.repositories.UserRepository;
import org.esprit.storeyc.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
@Component
@Slf4j
public class LoyaltyPointsExpirationTask {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Scheduled(cron = "0 0 0 1 */3 ?") // run at midnight on the 1st day of every 3rd month
    public void expireLoyaltyPoints() {
        List<User> users = userRepository.findAllByLoyaltyPointsExpireDateBefore(LocalDate.now());
        for (User user : users) {
            user.setLoyaltyPts(0.0f);
            user.setLoyaltyPointsExpireDate(null);
            userRepository.save(user);
        }
    }


    @Scheduled(cron = "0 0 0 * * ?") // run at midnight every day
    public void cleanupExpiredLoyaltyPoints() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getLoyaltyPointsExpireDate() != null && user.getLoyaltyPointsExpireDate().isBefore(LocalDate.now())) {
                user.setLoyaltyPts(0f);
                user.setLoyaltyPointsExpireDate(null);
                userRepository.save(user);
            } else if (user.getLoyaltyPointsExpireDate() != null && user.getLoyaltyPointsExpireDate().minusDays(7).isBefore(LocalDate.now())) {
                // send reminder email 7 days before points expire
                userService.sendLoyaltyPointsReminder(user);
            }
        }
    }
//    @Scheduled(fixedDelay = 10000) // run every 10 seconds
//    public void testMailer() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        log.info("Sending email to: " + fromEmail);
//        message.setFrom(fromEmail);
//        message.setTo("khchimiothmen@gmail.com");
//        message.setSubject("Test email");
//        message.setText("This is a test email sent every 10 seconds.");
//        message.setText("Dear Customer,\n\nThis is a reminder that your loyalty points will expire in 7 days. Please use them before they expire.\n\nThank you for shopping with us.\n\nBest regards,\nThe StoreYC Team");
//        log.info("Email sent successfully to: othmenkhchimi@esprit.tn");
//        mailSender.send(message);
//    }
}

