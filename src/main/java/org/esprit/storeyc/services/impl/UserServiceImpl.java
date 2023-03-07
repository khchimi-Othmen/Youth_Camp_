package org.esprit.storeyc.services.impl;

import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.User;
import org.esprit.storeyc.repositories.CommandRepository;
import org.esprit.storeyc.repositories.UserRepository;
import org.esprit.storeyc.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommandRepository commandRepository;
    @Autowired
    private JavaMailSender mailSender; // Autowired mail sender for sending emails

    @Override
    public void registerUser(User user) {
        // Assign loyalty points to the new user
        user.setLoyaltyPts(100f);

        // Save the new user to the database
        userRepository.save(user);

    }
    public void sendLoyaltyPointsReminder(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Reminder: Your Loyalty Points are Expiring Soon!");
        message.setText("Dear " + user.getNom() + ",\n\n" +
                "This is a reminder that your loyalty points are expiring soon. You currently have " +
                user.getLoyaltyPts() + " points, which will expire on " + user.getLoyaltyPointsExpireDate() +
                ". Don't forget to use them before they expire!\n\n" +
                "Thank you for being a loyal customer of our online store.\n" +
                "Best regards,\n" +
                "The Store Team");
        mailSender.send(message);
    }

}

