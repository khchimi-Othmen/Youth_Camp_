package org.esprit.storeyc.services.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.esprit.storeyc.entities.Command;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

public class PdfGenerator {

    public static byte[] generateReceipt(Command command) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        // Open the document
        document.open();

        // Add some content to the document
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
        Font bodyFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        document.add(new Paragraph("Receipt", titleFont));
        document.add(new Paragraph("Customer Name: " + command.getUser().getPrenom(), bodyFont));
        document.add(new Paragraph("Item Name: " + command.getCommandLines().get(0).getProduct().getName(), bodyFont));
        document.add(new Paragraph("Item Price: $" + command.getTotalC(), bodyFont));

        // Close the document
        document.close();

        // Return the PDF as a byte array
        return outputStream.toByteArray();
    }
}
