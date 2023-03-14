package org.esprit.storeyc.services.Task;

import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.services.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductScheduler {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ProductServiceImpl productService;

    @Scheduled(fixedDelay = 10000) // run every 10 seconds
    public void verifyProductQuantities() {
        List<Product> products = productService.getAllP();

        // sort products by sales in descending order
        List<Product> topSellingProducts = products.stream()
                .sorted(Comparator.comparingInt(Product::getSales).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // sort products by sales in ascending order
        List<Product> topWorstProducts = products.stream()
                .filter(p -> p.getSales() > 0)
                .sorted(Comparator.comparingInt(Product::getSales))
                .limit(5)
                .collect(Collectors.toList());

        // sort products by quantity available in ascending order
        List<Product> lowStockProducts = products.stream()
                .filter(p -> p.getQuantityAvailable() < 10)
                .sorted(Comparator.comparingInt(Product::getQuantityAvailable))
                .limit(5)
                .collect(Collectors.toList());

        // create email message
        String subject = "Product sales and stock report";
        StringBuilder text = new StringBuilder();
        text.append("Top 5 selling products:\n");
        for (Product product : topSellingProducts) {
            text.append("- ").append(product.getName()).append(": ").append(product.getSales()).append("\n");
        }
        text.append("\nTop 5 worst selling products:\n");
        for (Product product : topWorstProducts) {
            text.append("- ").append(product.getName()).append(": ").append(product.getSales());
            if (Objects.equals(product.getPromotion(), "to be promoted")) {
                productService.addPromotionToProduct(product.getProductId(), "Products of the days");
                productService.applyPercentageDiscountToProduct(product.getProductId(), 10f);
                BigDecimal discountedPrice = product.getPrice().multiply(BigDecimal.valueOf(0.8));
                text.append(" (original price: ").append(product.getPrice()).append(", discounted price: ").append(discountedPrice).append(")");
            } else {
                text.append(" (original price: ").append(product.getPrice()).append(")");
            }
            text.append("\n");
        }
        text.append("\nLow stock products:\n");
        for (Product product : lowStockProducts) {
            text.append("- ").append(product.getName()).append(": ").append(product.getQuantityAvailable()).append("\n");
        }

        // send email to admin
        sendEmail(subject, text.toString());
    }

    private void sendEmail(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("othmen.khchimi@esprit.tn");
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("${spring.mail.username}"); // Use the configured email address
        javaMailSender.send(message);
        log.info("Email sent to admin: " + subject);
    }
}
