package com.hms.service;

import com.hms.entity.Bookings;
import com.hms.entity.Property;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PdfService {


    public void generateBookingPdf(String filePath, Property property, Bookings bookings){
        try{
            Document document = new Document();
            PdfWriter writer =PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            PdfContentByte canvas = writer.getDirectContent();
            Image background = Image.getInstance("C:\\Users\\yogas\\Downloads\\Free  Card, Floral, Pattern Background Images, Frame Photograph Representation Creation Background Photo Background PNG and Vectors.jpeg"); // Provide the path to your background image
            background.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight()); // Scale to fit the page size
            background.setAbsolutePosition(0, 0); // Position the image at the bottom-left corner (0,0)
            canvas.addImage(background); // Add the image as the background

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80); // Set table width percentage (optional)
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Name of Customer");
            table.addCell(bookings.getName());
            table.addCell("Email Id");
            table.addCell(bookings.getEmailId());
            table.addCell("Check in date");
            table.addCell(bookings.getCheckInDate().toString());
            table.addCell("Check out date");
            table.addCell(bookings.getCheckOutDate().toString());
            table.addCell("Property Id");
            table.addCell(property.getId().toString());
            table.addCell("name");
            table.addCell(property.getName().toString());
            table.addCell("no of guests");
            table.addCell(property.getNo_of_guest().toString());
            table.addCell("no of bedrooms");
            table.addCell(property.getNo_of_bedrooms().toString());
            table.addCell("no of bathrooms");
            table.addCell(property.getNo_of_bathroom().toString());
            table.addCell("no of beds");
            table.addCell(property.getNo_of_beds().toString());


            table.addCell("Base Price");
            table.addCell(bookings.getBasePrice().toString());
            table.addCell("Gst Price");
            table.addCell(bookings.getTotalGST().toString());
            table.addCell("Total Price");
            table.addCell(bookings.getTotalPrice().toString());





            document.add(table);

            Font font = FontFactory.getFont(FontFactory.COURIER);
            Chunk chunk = new Chunk("Thank you for booking with us!", font);
            document.add(chunk);
            document.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

