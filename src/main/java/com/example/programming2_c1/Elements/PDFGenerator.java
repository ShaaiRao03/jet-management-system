package com.example.programming2_c1.Elements;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetClasses.PrivateJet;
import com.example.programming2_c1.UserClasses.Transaction;
import com.example.programming2_c1.UserClasses.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.FileSystemNotFoundException;

import static com.itextpdf.text.Font.FontFamily.TIMES_ROMAN;

public class PDFGenerator {


    public static void generatePDF(Transaction transaction , User user , PrivateJet privateJet,String directoryPath) throws FileSystemNotFoundException, FileNotFoundException, DocumentException {

        String path = directoryPath+"\\"+user.getFirst_name()+"_"+transaction.getTransactionID()+".pdf";

        Document document = new Document();
        PdfWriter.getInstance(document,new FileOutputStream(path));

        document.open();

        Font jetcraft  = new Font(TIMES_ROMAN, 30, Font.BOLD);
        jetcraft.setStyle(Font.UNDEFINED);
        jetcraft.setColor(176, 82, 0);
        Font titleFont = new Font(TIMES_ROMAN, 15, Font.BOLD);
        titleFont.setColor(176, 82, 0);
        Font sectionFont = new Font(TIMES_ROMAN, 12, Font.BOLD);
        Font normalFont = new Font(TIMES_ROMAN, 10);

        //Main title
        Paragraph jetCraftBrand = new Paragraph("JET CRAFT INVOICE",jetcraft);
        jetCraftBrand.setAlignment(Element.ALIGN_CENTER);
        jetCraftBrand.setSpacingBefore(20f);
        jetCraftBrand.setSpacingAfter(20f);
        document.add(jetCraftBrand);

        // User details section (user class)
        Paragraph userHeading = new Paragraph("USER DETAILS",titleFont);
        userHeading.setSpacingBefore(20f);
        userHeading.setSpacingAfter(20f);
        userHeading.setAlignment(Element.ALIGN_CENTER);
        document.add(userHeading);
        document.add(new Paragraph("Name   : "+user.getFirst_name()+" "+user.getLast_name(),normalFont));
        document.add(new Paragraph("NRIC   : "+user.getIdentity_card(),normalFont));

        // Add transaction details (transaction class)
        Paragraph transactionHeading = new Paragraph("TRANSACTION DETAILS",titleFont);
        transactionHeading.setSpacingBefore(20f);
        transactionHeading.setSpacingAfter(20f);
        transactionHeading.setAlignment(Element.ALIGN_CENTER);
        document.add(transactionHeading);
        document.add(new Paragraph("Transaction ID                            : "+transaction.getTransactionID(),normalFont));
        document.add(new Paragraph("Issued Date                                 : "+transaction.getTransactionDate(),normalFont));
        document.add(new Paragraph("Quantity                                      : "+transaction.getJetQuantity(),normalFont));
        document.add(new Paragraph("Additional feature or equipment : "+transaction.getAdditionalFeatures(),normalFont));
        document.add(new Paragraph("Purchase price                             : $ "+transaction.getTotalAmount(),normalFont));
        document.add(new Paragraph("Payment Method                         : "+Payment.getPaymentName(transaction.getPaymentmethod()),normalFont));
        document.add(new Paragraph("Bank name                                  : "+transaction.getBankName(),normalFont));
        document.add(new Paragraph("Delivery Country                        : "+Country.getCountryName(transaction.getDeliveryCountry()),normalFont));
        document.add(new Paragraph("Delivery location                        : "+Location.getLocationName(transaction.getDeliveryLocation()),normalFont));

        // Add jet information (private jet class)
        Paragraph jetHeading = new Paragraph("JET INFORMATION",titleFont);
        transactionHeading.setSpacingBefore(20f);
        transactionHeading.setSpacingAfter(20f);
        jetHeading.setAlignment(Element.ALIGN_CENTER);
        document.add(jetHeading);

        //PrivateJet Details
        Paragraph privatejetHeading = new Paragraph("PRIVATE JET",sectionFont);
        privatejetHeading.setSpacingBefore(10f);
        privatejetHeading.setSpacingAfter(5f);
        document.add(privatejetHeading);
        document.add(new Paragraph("Jet name : "+privateJet.getJetName(),normalFont));
        document.add(new Paragraph("Jet ID      : "+privateJet.getJetID(),normalFont));

        // Airframe details
        Paragraph airframeHeading = new Paragraph("AIRFRAME",sectionFont);
        airframeHeading.setSpacingBefore(10f);
        airframeHeading.setSpacingBefore(5f);
        document.add(airframeHeading);
        document.add(new Paragraph("Total Time Since New             : "+privateJet.getAirframe().getTimeSinceNew()+" Hours",normalFont));
        document.add(new Paragraph("Total Landings Since New      : "+privateJet.getAirframe().getLandings()+" Landings",normalFont));

        // Engine details
        Paragraph engineHeading = new Paragraph("ENGINE",sectionFont);
        engineHeading.setSpacingBefore(10f);
        engineHeading.setSpacingBefore(5f);
        document.add(engineHeading);
        document.add(new Paragraph("Total Hours Since New      : "+privateJet.getEngine().getHoursSinceNew()+" Hours",normalFont));
        document.add(new Paragraph("Total Cycles Since New     : "+privateJet.getEngine().getCycleSinceNew()+" Cycles",normalFont));
        document.add(new Paragraph("Right Engine ID                 : "+privateJet.getEngine().getRightEngineID(),normalFont));
        document.add(new Paragraph("Left Engine ID                   : "+privateJet.getEngine().getLeftEngineID(),normalFont));
        document.add(new Paragraph("Engine Description            : "+privateJet.getEngine().getEngineDescription(),normalFont));

        // Interior details
        Paragraph interiorHeading = new Paragraph("INTERIOR",sectionFont);
        interiorHeading.setSpacingBefore(10f);
        interiorHeading.setSpacingBefore(5f);
        document.add(interiorHeading);
        document.add(new Paragraph("Forward Cabin Configuration   : "+privateJet.getInterior().getForwardCabinConfig(),normalFont));
        document.add(new Paragraph("Aft Cabin Configuration           : "+privateJet.getInterior().getAftCabinConfig(),normalFont));
        document.add(new Paragraph("Number of Passengers              : "+privateJet.getInterior().getNumPassengers(),normalFont));

        // Exterior details
        Paragraph exteriorHeading = new Paragraph("EXTERIOR",sectionFont);
        exteriorHeading.setSpacingBefore(10f);
        exteriorHeading.setSpacingBefore(5f);
        document.add(exteriorHeading);
        document.add(new Paragraph("Stripe           : "+privateJet.getExterior().getStripe(),normalFont));
        document.add(new Paragraph("Base Paint   : "+privateJet.getExterior().getBasePaint(),normalFont));

        Paragraph thanks = new Paragraph("Thank you for your business",sectionFont);
        thanks.setAlignment(Element.ALIGN_CENTER);
        document.add(thanks);
        document.close();
    }
}
