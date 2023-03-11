package org.esprit.storeyc.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.services.interfaces.EmailService;
import org.esprit.storeyc.services.pdf.PdfGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;

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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            // Create a PDF document with iText
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add a header with a title and logo
            Image logo = Image.getInstance("src/main/resources/images/logo.png");
            logo.scaleToFit(100f, 100f);
            logo.setAlignment(Element.ALIGN_LEFT);
            document.add(logo);

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph title = new Paragraph("Order Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(20f);
            title.setSpacingAfter(20f);
            document.add(title);

            // Add order details to the PDF document
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[] { 3f, 1f, 1f, 1f });
            table.setHeaderRows(1);

            PdfPCell cell1 = new PdfPCell(new Phrase("Product"));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase("Price"));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase("Quantity"));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase("Total Price"));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell4);

            BigDecimal totalPrice = BigDecimal.ZERO;

            for (LineCmd lineCmd : command.getCommandLines()) {
                Product product = lineCmd.getProduct();
                BigDecimal price = product.getPrice();
                BigDecimal quantity = new BigDecimal(lineCmd.getQuantite());

                PdfPCell productNameCell = new PdfPCell(new Phrase(product.getName()));
                productNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(productNameCell);

                PdfPCell priceCell = new PdfPCell(new Phrase(price.toString()));
                priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(priceCell);

                PdfPCell quantityCell = new PdfPCell(new Phrase(quantity.toString()));
                quantityCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(quantityCell);

                BigDecimal productTotalPrice = price.multiply(quantity);
                totalPrice = totalPrice.add(productTotalPrice);
                PdfPCell totalPriceCell = new PdfPCell(new Phrase(productTotalPrice.toString()));
                totalPriceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(totalPriceCell);
            }

            PdfPCell totalCell = new PdfPCell(new Phrase("Total"));
            totalCell.setColspan(3);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);

            PdfPCell totalPriceCell = new PdfPCell(new Phrase(totalPrice.toString()));
            totalPriceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(totalPriceCell);

            document.add(table);

            // Add a footer with the date and store information
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            Paragraph footer = new Paragraph("Thank you for shopping with us.\nFor inquiries, please contact us at info@storeyc.com.\nDate: " + new Date(), footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20f);
            document.add(footer);

            document.close();


        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        // Create a mail message with attachment
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Order Confirmation");
            helper.setText("Thank you for shopping with us.\n\nBest regards,\nThe StoreYC Team");
            // Set HTML content for the email body
            String emailContent = "<html><body><div style='text-align:center;background-color:#f7f7f7;padding:20px;'><h2>Order Confirmation</h2></div>" +
                    "<div style='margin:30px;'><p>Dear customer,</p>" +
                    "<p>Thank you for shopping with us. Please find attached the confirmation of your order.</p></div>" +
                    "<div style='margin:30px;'><h3>Order details</h3>" +
                    "<table style='border-collapse:collapse;width:100%;'>" +
                    "<thead><tr><th style='border:1px solid black;padding:5px;'>Product</th><th style='border:1px solid black;padding:5px;'>Price</th><th style='border:1px solid black;padding:5px;'>Quantity</th><th style='border:1px solid black;padding:5px;'>Total Price</th></tr></thead>" +
                    "<tbody>";

            // Add order details to the HTML content
            BigDecimal totalPrice = BigDecimal.ZERO;

            for (LineCmd lineCmd : command.getCommandLines()) {
                Product product = lineCmd.getProduct();
                BigDecimal price = product.getPrice();
                BigDecimal quantity = new BigDecimal(lineCmd.getQuantite());

                emailContent += "<tr><td style='border:1px solid black;padding:5px;'>" + product.getName() + "</td>" +
                        "<td style='border:1px solid black;padding:5px;text-align:right;'>" + price.toString() + "</td>" +
                        "<td style='border:1px solid black;padding:5px;text-align:right;'>" + quantity.toString() + "</td>" +
                        "<td style='border:1px solid black;padding:5px;text-align:right;'>" + price.multiply(quantity).toString() + "</td></tr>";

                totalPrice = totalPrice.add(price.multiply(quantity));
            }

            emailContent += "</tbody><tfoot><tr><td style='border:1px solid black;padding:5px;text-align:right;' colspan='3'>Total</td><td style='border:1px solid black;padding:5px;text-align:right;'>" + totalPrice.toString() + "</td></tr></tfoot></table></div>" +
                    "<div style='text-align:center;background-color:#f7f7f7;padding:20px;'><p>Thank you for shopping with us.</p><p>The StoreYC Team</p></div></body></html>";

            helper.setText(emailContent, true);

            // Attach the PDF file to the email
            ByteArrayDataSource dataSource = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf");
            helper.addAttachment("receipt.pdf", dataSource);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);




    }
}
