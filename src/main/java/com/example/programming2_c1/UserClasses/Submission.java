package com.example.programming2_c1.UserClasses;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.Elements.Country;
import com.example.programming2_c1.Elements.Location;
import com.example.programming2_c1.Elements.Payment;
import com.example.programming2_c1.JetSysDatabase;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class Submission {

    //Submission is connected with cart
    //Each the user submits the application for the selected cart , this entity will keep track of those submissions
    //This class is status independent , meaning the record in this class can be in any status
    //If a cart that has been filled with application (regardless of any status) has been deleted , this record will be deleted automatically
    //Transaction class is the same as this , but it is status dependent and independent of Cart class.
    //Any approved cart application will be stored in Transaction class and Submission class.
    //If the approved cart has been removed from the table by the user , the record in Submission will be removed as well but in Transaction remain
    //The purpose is to keep track of the approved applications.

    public static void setSubmitForm(String payment , String country, String location, String quantity, String feature, Long totalPrice, String bankName, int cartID){
        Thread addToSubmission = new Thread(() ->{
            Connection connectSubmission = null;
            ResultSet resultSubmission = null;
            PreparedStatement statementSubmission = null;

            try {
                String submissionInsert_query = "INSERT INTO submission (jetQuantity,additionalFeatures,totalAmount,paymentmethod,bankName,deliveryCountry,deliveryLocation,cartID,submissionDate) VALUES (?,?,?,?,?,?,?,?,?)";
                connectSubmission = JetSysDatabase.getConnection();

                int paymentID = Payment.getPaymentID(payment);
                String  countryID = Country.getCountryID(country);
                String locationID = Location.getLocationID(location);
                LocalDate date = LocalDate.now();

                statementSubmission = connectSubmission.prepareStatement(submissionInsert_query);
                statementSubmission.setInt(1,Integer.parseInt(quantity));
                statementSubmission.setString(2,feature);
                statementSubmission.setLong(3,totalPrice);
                statementSubmission.setInt(4,paymentID);
                statementSubmission.setString(5,bankName);
                statementSubmission.setString(6,countryID);
                statementSubmission.setString(7,locationID);
                statementSubmission.setInt(8,cartID);
                statementSubmission.setDate(9, Date.valueOf(date));

                statementSubmission.execute();

            }catch (Exception e){
                System.out.println("Submission (setSubmitForm()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectSubmission,statementSubmission,resultSubmission);
            }
        }); addToSubmission.start();

    }

    public static int getCartIDGivenSubmissionID (int submissionID){
        //to remove/update cart

        int cartID = -1;

        Connection connectSubmission = null;
        ResultSet resultSubmission = null;
        PreparedStatement statementSubmission = null;

        try {
            String submissionSelect_query = "SELECT cartID FROM submission JOIN cart USING (cartID)  WHERE submissionID = ?;";
            connectSubmission = JetSysDatabase.getConnection();
            statementSubmission = connectSubmission.prepareStatement(submissionSelect_query);

            statementSubmission.setInt(1,submissionID);
            resultSubmission = statementSubmission.executeQuery();

            while (resultSubmission.next()){
                cartID = resultSubmission.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Submission (getCartIDGivenSubmissionID()) has not been connected to database");
            e.printStackTrace();

        } finally {
            JetSysDatabase.closeConnection(connectSubmission,statementSubmission,resultSubmission);
        }

        return cartID;
    }



    public static ArrayList<Transaction> getAllUnderConsiderationSubmissionDetails() throws InterruptedException {

        //storing in transaction to be processed (intentional procedure)
        ArrayList<Transaction> transactions = new ArrayList<>();

        Thread getSubmission = new Thread(() ->{
            Connection connectSubmission = null;
            ResultSet resultSubmission = null;
            PreparedStatement statementSubmission = null;

            try {
                String submissionSelect_query = "SELECT * FROM submission JOIN cart USING (cartID)  WHERE status = 'Under Consideration';";
                connectSubmission = JetSysDatabase.getConnection();
                statementSubmission = connectSubmission.prepareStatement(submissionSelect_query);

                resultSubmission = statementSubmission.executeQuery();

                while (resultSubmission.next()){
                    LocalDate tempDate = resultSubmission.getDate("submissionDate").toLocalDate();

                    transactions.add(new Transaction(resultSubmission.getInt("submissionID"),resultSubmission.getInt("jetQuantity"),resultSubmission.getString("additionalFeatures"),
                            resultSubmission.getLong("totalAmount"),resultSubmission.getInt("paymentmethod"),resultSubmission.getString("bankName"),resultSubmission.getString("deliveryCountry"),resultSubmission.getString("deliveryLocation"),
                            resultSubmission.getString("jetID"),tempDate,resultSubmission.getString("userName")));
                }

            }catch (Exception e){
                System.out.println("Submission (setSubmitForm()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectSubmission,statementSubmission,resultSubmission);
            }
        }); getSubmission.start(); getSubmission.join();

        return transactions;
    }
}
