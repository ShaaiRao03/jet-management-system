package com.example.programming2_c1.UserClasses;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetSysDatabase;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cart {

    int cartID;
    String jetID;
    String jetName;
    String status;

    public Cart(int cartID, String jetID, String jetName, String status) {
        this.cartID = cartID;
        this.jetID = jetID;
        this.jetName = jetName;
        this.status = status;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getJetID() {
        return jetID;
    }

    public void setJetID(String jetID) {
        this.jetID = jetID;
    }

    public String getJetName() {
        return jetName;
    }

    public void setJetName(String jetName) {
        this.jetName = jetName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public static void removeCartFromDatabase(int cartId){

            Connection connectCart = null;
            ResultSet resultCart = null;
            PreparedStatement statementCart = null;

            try {
                String cartDelete_query = "DELETE FROM cart WHERE cartID = ?";
                connectCart = JetSysDatabase.getConnection();

                statementCart = connectCart.prepareStatement(cartDelete_query);
                statementCart.setInt(1,cartId);

                statementCart.execute();

            }catch (Exception e){
                System.out.println("CartController (removeCartFromDatabase()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectCart,statementCart,resultCart);
            }

    }


    public static void editCartStatus(int cartID,String status){

            Connection connectCart = null;
            ResultSet resultCart = null;
            PreparedStatement statementCart = null;

            try {
                String updateCart_query = "UPDATE cart SET status = ? WHERE cartID = ?; ";
                connectCart = JetSysDatabase.getConnection();

                statementCart = connectCart.prepareStatement(updateCart_query);
                statementCart.setString(1,status);
                statementCart.setInt(2,cartID);

                statementCart.execute();

            }catch (Exception e){
                System.out.println("CartController (editCartStatus()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectCart,statementCart,resultCart);
            }

    }

    public static void addCartToDatabase(String jetID, String jetName, String status,String username){

            Connection connectCart = null;
            ResultSet resultCart = null;
            PreparedStatement statementCart = null;

            try {
                String cartInsert_query = "INSERT INTO cart (userName,jetID,jetName,status) VALUES (?,?,?,?)";
                connectCart = JetSysDatabase.getConnection();

                statementCart = connectCart.prepareStatement(cartInsert_query);
                statementCart.setString(1,username);
                statementCart.setString(2,jetID);
                statementCart.setString(3,jetName);
                statementCart.setString(4,status);

                statementCart.execute();

            }catch (Exception e){
                System.out.println("CartController (addCartToDatabase()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectCart,statementCart,resultCart);
            }


        CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully added into the cart!");
    }


    public static Transaction getSubmissionGivenCartID(int cartID) throws InterruptedException {

        //storing in transaction in order to be processed (intentional procedure)
        //used in preview mode
        //retrieving submission and setting up in transaction because the preview mode uses transaction as the parameter
        //the purpose is to make the preview mode versatile which means it can be used for both submission and transaction by using a single parameter
        //submission != transaction , although they have the same structure , but their purpose is different , refer to submission class for explanation

        Transaction transaction = new Transaction();

        Connection connectCart = null;
        ResultSet resultCart = null;
        PreparedStatement statementCart = null;

        try {

            String cartSelect_query = "SELECT * FROM submission JOIN cart USING (cartID)  WHERE cartID = ? ;";
            connectCart = JetSysDatabase.getConnection();
            statementCart = connectCart.prepareStatement(cartSelect_query);

            statementCart.setInt(1,cartID);
            resultCart = statementCart.executeQuery();


            while (resultCart.next()){
                LocalDate tempDate = resultCart.getDate("submissionDate").toLocalDate();
                transaction = new Transaction(resultCart.getInt("submissionID"),resultCart.getInt("jetQuantity"),resultCart.getString("additionalFeatures"),
                        resultCart.getLong("totalAmount"),resultCart.getInt("paymentmethod"),resultCart.getString("bankName"),resultCart.getString("deliveryCountry"),resultCart.getString("deliveryLocation"),
                        resultCart.getString("jetID"),tempDate,resultCart.getString("userName"));
            }

        }catch (Exception e){
            System.out.println("Cart (getSubmissionGivenCartID()) has not been connected to database");
            e.printStackTrace();

        } finally {
            JetSysDatabase.closeConnection(connectCart,statementCart,resultCart);
        }

        return transaction;
    }


    public static int getCartJetRealQuantity(String jetID){
        //returns the REAL quantity for the jet and the jet is also in the cart
        //this is to ensure that , if the status is pending , and the real quantity of jet is 0 , the application form will not be visible.
        Connection connectCart = null;
        ResultSet resultCart = null;
        PreparedStatement statementCart = null;

        int quantity = 0;

        try {
            String cartQuantity_query = "SELECT quantity FROM privatejet JOIN cart USING (jetID) WHERE jetID = ?";
            connectCart = JetSysDatabase.getConnection();

            statementCart = connectCart.prepareStatement(cartQuantity_query);
            statementCart.setString(1,jetID);
            resultCart = statementCart.executeQuery();

            while (resultCart.next()){
                quantity = resultCart.getInt(1);
            }

        }catch (Exception e){
            System.out.println("CartController (getCartQuantity()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectCart,statementCart,resultCart);
        }
        return quantity;
    }


}

