package org.esprit.storeyc.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String toEmail, Command command) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Order Confirmation");
//
//        String messageBody = String.format("Dear Customer,\n\nThank you for your order.\n\nHere are the details of your order:\n\n"
//                        + "Order Number: %s\n"
//                        + "Total: %s\n"
//                        + "Date: %s\n"
//                        + "Payment Method: %s\n"
//                        + "Shipping Address: %s\n\n"
//                        + "Here is a list of your order items: \n",
//                command.getCommandeNumber(), command.getTotalC(), command.getDeliveryDate(),
//                command.getPaymentMethod(), command.getDelivery().getAddress());
//
//        for (LineCmd lineCmd : command.getCommandLines()) {
//            messageBody += String.format("Product Name: %s\nQuantity: %d\nPrice: %s\n\n",
//                    lineCmd.getProduct().getName(), lineCmd.getQuantite(), lineCmd.getProduct().getPrice());
//        }

//        messageBody += "We will notify you when your order has been shipped.\n\n"
//                + "Thank you for shopping with us.\n\nBest regards,\nThe StoreYC Team";

        message.setText("Thank you for shopping with us.\n\nBest regards,\nThe StoreYC Team");
        mailSender.send(message);
    }

}
