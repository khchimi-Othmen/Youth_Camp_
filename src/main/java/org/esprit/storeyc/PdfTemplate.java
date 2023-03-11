package org.esprit.storeyc;//package org.esprit.storeyc;
//
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.PageSize;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.pdf.PdfWriter;
//import org.esprit.storeyc.entities.Command;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//
//public class PdfTemplate {
//
//    public static ByteArrayInputStream createDocument(Command command) {
//        Document document = new Document(PageSize.A4);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        try {
//            PdfWriter.getInstance(document, outputStream);
//            document.open();
//
//            // add content to the document
//            document.add(new Paragraph("Command Details:"));
//            document.add(new Paragraph("Name: " + command.getUser().getNom()));
//            document.add(new Paragraph("Description: " + command.getCommmandType()));
//
//            document.close();
//            outputStream.close();
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        }
//
//        return new ByteArrayInputStream(outputStream.toByteArray());
//    }
//}
