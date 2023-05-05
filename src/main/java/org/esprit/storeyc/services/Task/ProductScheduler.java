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

    @Scheduled(fixedDelay = 10000)// run every 10 seconds
    public void verifyProductQuantities() {
        List<Product> products = productService.getAllP();

        List<Product> topSellingProducts = getTopSellingProducts(products);
        List<Product> topWorstProducts = getTopWorstProducts(products);
        List<Product> lowStockProducts = getLowStockProducts(products);

        String emailText = getEmailText(topSellingProducts, topWorstProducts, lowStockProducts);

        sendEmail("Product sales and stock report", emailText);
    }

    public List<Product> getTopSellingProducts(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparingInt(Product::getSales).reversed())
                .limit(4)
                .collect(Collectors.toList());
    }

    public List<Product> getTopWorstProducts(List<Product> products) {
        return products.stream()
                .filter(p -> p.getSales() > 0)
                .sorted(Comparator.comparingInt(Product::getSales))
                .limit(4)
                .collect(Collectors.toList());
    }

    public List<Product> getLowStockProducts(List<Product> products) {
        return products.stream()
                .filter(p -> p.getQuantityAvailable() < 10)
                .sorted(Comparator.comparingInt(Product::getQuantityAvailable))
                .limit(10)
                .collect(Collectors.toList());
    }

    private String getEmailText(List<Product> topSellingProducts, List<Product> topWorstProducts, List<Product> lowStockProducts) {
        StringBuilder text = new StringBuilder();
        text.append("Top 5 selling products:\n");
        for (Product product : topSellingProducts) {
            text.append("- ").append(product.getName()).append(": ").append(product.getSales()).append("\n");
        }
        text.append("\nTop 5 worst selling products:\n");
        for (Product product : topWorstProducts) {
            text.append("- ").append(product.getName()).append(": ").append(product.getSales());
            if (Objects.equals(product.getPromotion(), "to be promoted")) {
                applyProductPromotion(product, text);
            } else {
                text.append(" (original price: ").append(product.getPrice()).append(")");
            }
            text.append("\n");
        }
        text.append("\nLow stock products:\n");
        for (Product product : lowStockProducts) {
            text.append("- ").append(product.getName()).append(": ").append(product.getQuantityAvailable()).append("\n");
        }
        return text.toString();
    }

    private void applyProductPromotion(Product product, StringBuilder text) {
        productService.addPromotionToProduct(product.getProductId(), "Products of the days");
        productService.applyPercentageDiscountToProduct(product.getProductId(), 10f);
        BigDecimal discountedPrice = product.getPrice().multiply(BigDecimal.valueOf(0.8));
        text.append(" (original price: ").append(product.getPrice()).append(", discounted price: ").append(discountedPrice).append(")");
    }

    public BigDecimal getTotalRevenue(List<Product> products) {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for (Product product : products) {
            BigDecimal productRevenue = product.getPrice().multiply(BigDecimal.valueOf(product.getSales()));
            totalRevenue = totalRevenue.add(productRevenue);
        }
        return totalRevenue;
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
