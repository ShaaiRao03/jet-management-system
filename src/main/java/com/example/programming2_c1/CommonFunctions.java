package com.example.programming2_c1;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.math.BigInteger;
import java.time.Year;
import java.util.Optional;

public class CommonFunctions {

    public static String pending = "Pending", u_consideration = "Under Consideration" , approved = "Approved" , reject = "Rejected";
    public static String rootAdmin = "Root Admin", client =  "Client" , admin = "Admin";

    public static void createAlert(Alert.AlertType alertType , String title, String message){
        Alert alert;
        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean createAlertConfirmation(String title, String message){
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }


    public static void initializeReset(ComboBox<String> box) { //this is duplicated from login , do something about it
        box.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (item == null || empty ){
                    setText(box.getPromptText());
                } else {
                    setText(item);
                }
            }
        });
    }

    public static boolean validationInteger (String input, String message){
        //any program that require integer as input can use this method for validation
        try {
            int choice = Integer.parseInt(input);
            return true;
        } catch(NumberFormatException e) {
            if (!message.equals("Test")){
                createAlert(Alert.AlertType.ERROR,"Input format error",message);
            }
        }
        return false;
    }


    public static boolean validationLong(String input, String message){
        //phone and IC num uses value that exceeds the value that can be hold by integer
        //hence 'Long' is used to check
        try {
            Long choice = Long.parseLong(input);
            return true;
        } catch(NumberFormatException e) {
            if (!message.equals("Test")){
                createAlert(Alert.AlertType.ERROR,"Input format error",message);
            }
        }
        return false;
    }

}
