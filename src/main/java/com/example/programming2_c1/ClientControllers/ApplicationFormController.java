package com.example.programming2_c1.ClientControllers;

import com.example.programming2_c1.AdminControllers.AdminInterfaceController;
import com.example.programming2_c1.AdminControllers.TransactionOperationController;
import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.Elements.Country;
import com.example.programming2_c1.Elements.Location;
import com.example.programming2_c1.Elements.Payment;
import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.JetClasses.PrivateJet;
import com.example.programming2_c1.JetSysDatabase;
import com.example.programming2_c1.UserClasses.Cart;
import com.example.programming2_c1.UserClasses.Submission;
import com.example.programming2_c1.UserClasses.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;


public class ApplicationFormController implements Initializable {

    @FXML
    private TextField bankNameTextField;

    @FXML
    private AnchorPane btnAnchor;

    @FXML
    private ComboBox<String> countryBox;

    @FXML
    private TextArea featureTextArea;

    @FXML
    private ComboBox<String> locationBox;

    @FXML
    private ComboBox<String> paymentBox;

    @FXML
    private Label purchase_priceLabel;

    @FXML
    private ComboBox<String> quantityBox;

    @FXML
    private Button backFormPreviewBtn;

    @FXML
    private Button resetForm;

    @FXML
    private Button submitForm;

    @FXML
    private Button acceptBtn;

    @FXML
    private Button rejectBtn;

    @FXML
    private Button backFormClientBtn;

    final int MAX_CHARS = 100;
    long totalPrice=0;
    int cartID;

    private ObservableList<String> availableCountry =  FXCollections.observableArrayList();
    private ObservableList<String> availableLocation =  FXCollections.observableArrayList();
    private ObservableList<String> availableQuantity =  FXCollections.observableArrayList();
    private ObservableList<String> availablePaymentMethod =  FXCollections.observableArrayList();

    private PrivateJet jet;

    private  Transaction transaction;

    private String caller;

    public static ClientInterfaceController clientInterfaceController;
    public static AdminInterfaceController adminInterfaceController;

    public static TransactionOperationController transactionOperationController;


    public void setupAvailableQuantity(int quantity){
        for (int i = 1; i <= quantity; i++) {
            availableQuantity.add(String.valueOf(i));
        }
        quantityBox.setItems(availableQuantity);
        quantityBox.setVisibleRowCount(5);
    }

    public void setupAvailableCountry(){

        Thread countryAvailable = new Thread(() ->{
            Connection connectCountry = null;
            ResultSet resultCountry = null;
            PreparedStatement statementCountry = null;

            try {
                String obtainCountry = "SELECT * FROM countrydelivery";
                connectCountry = JetSysDatabase.getConnection();

                statementCountry = connectCountry.prepareStatement(obtainCountry);
                resultCountry = statementCountry.executeQuery();

                while (resultCountry.next()){
                    availableCountry.add(resultCountry.getString(2));
                }

            }catch (Exception e){
                System.out.println("ApplicationFormController (setupAvailableCountry()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectCountry,statementCountry,resultCountry);
            }
        }); countryAvailable.start();

        countryBox.setItems(availableCountry);
        countryBox.setVisibleRowCount(5);
        locationBox.setDisable(true);
    }


    public void setupAvailableAirports(String country){

        locationBox.getSelectionModel().clearSelection();
        CommonFunctions.initializeReset(locationBox);
        availableLocation.clear();

        Thread airportsAvailable = new Thread(() ->{
            Connection connectAirport= null;
            ResultSet resultAirport = null;
            PreparedStatement statementAirport = null;

            try {
                String obtainAirport = "SELECT * FROM countrydelivery JOIN airports ON countrydelivery.isoCode = airports.isoCode WHERE countryName= ? ;";
                connectAirport = JetSysDatabase.getConnection();

                statementAirport = connectAirport.prepareStatement(obtainAirport);
                statementAirport.setString(1,country);
                resultAirport = statementAirport.executeQuery();

                while (resultAirport.next()){
                    availableLocation.add(resultAirport.getString(5));
                }

            }catch (Exception e){
                System.out.println("ApplicationFormController (setupAvailableAirports()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectAirport,statementAirport,resultAirport);
            }
        }); airportsAvailable.start();

        locationBox.setItems(availableLocation);
        locationBox.setDisable(false);
        locationBox.setVisibleRowCount(5);
    }

    public void setupAvailablePaymentMethod(){

        Thread paymentAvailable = new Thread(() ->{
            Connection connectPayment= null;
            ResultSet resultPayment = null;
            PreparedStatement statementPayment = null;

            try {
                String obtainPayment = "SELECT * FROM paymentmethod";
                connectPayment = JetSysDatabase.getConnection();

                statementPayment = connectPayment.prepareStatement(obtainPayment);
                resultPayment = statementPayment.executeQuery();

                while (resultPayment.next()){
                    availablePaymentMethod.add(resultPayment.getString(2));
                }

            }catch (Exception e){
                System.out.println("ApplicationFormController (setupPaymentMethod()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectPayment,statementPayment,resultPayment);
            }
        }); paymentAvailable.start();
        paymentBox.setItems(availablePaymentMethod);
    }

    public void updatePriceLabel(){
        try {
            NumberFormat nf = NumberFormat.getInstance();
            purchase_priceLabel.setText("$ "+String.valueOf(nf.format(Integer.parseInt(quantityBox.getValue())*jet.getPrice())));
            totalPrice = Long.parseLong(quantityBox.getValue())*jet.getPrice();

        }catch (NumberFormatException e){
            System.out.println("ApplicationFormController class : Error in updatePriceLabel() ");
        }
    }

    public void setResetForm(){
        purchase_priceLabel.setText("-");
        featureTextArea.clear();
        bankNameTextField.clear();
        quantityBox.getSelectionModel().clearSelection();
        paymentBox.getSelectionModel().clearSelection();
        countryBox.getSelectionModel().clearSelection();
        locationBox.getSelectionModel().clearSelection();
        CommonFunctions.initializeReset(quantityBox);
        CommonFunctions.initializeReset(paymentBox);
        CommonFunctions.initializeReset(countryBox);
        CommonFunctions.initializeReset(locationBox);
        locationBox.setDisable(true);
    }


    public boolean checkEmptyFields(){
        if (quantityBox.getValue() == null || paymentBox.getValue()== null || bankNameTextField.getText().isEmpty() || countryBox.getValue()== null || locationBox.getValue()== null){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill in all the * fields.");
            return true;
        }else {
            return false;
        }
    }

    public void setData(PrivateJet jet, int cartID){
        setRole(CommonFunctions.client);
        this.jet = jet;
        this.cartID = cartID;
        setupAvailableQuantity(jet.getQuantity());
        setupAvailableCountry();
        setupAvailablePaymentMethod();
    }


    public void setRole(String role){
        if (role.equals(CommonFunctions.client)){
            backFormClientBtn.setVisible(true);
            resetForm.setVisible(true);
            submitForm.setVisible(true);

            backFormPreviewBtn.setVisible(false);
            acceptBtn.setVisible(false);
            rejectBtn.setVisible(false);
        } else {
            backFormClientBtn.setVisible(false);
            resetForm.setVisible(false);
            submitForm.setVisible(false);

            backFormPreviewBtn.setVisible(true);
        }
    }

    public void displayInPreviewMode(Transaction transaction,String caller) throws InterruptedException { //used by both client and admin

        this.jet = JetData.getPrivateJetGivenID(transaction.getJetID());

        setRole(caller);

        if (caller.equals("Submission")){
            btnAnchor.setVisible(true);
        } else {
            btnAnchor.setVisible(false);
        }

        this.caller = caller;
        this.transaction = transaction;

        try {
            setupAvailableQuantity(Objects.requireNonNull(JetData.getPrivateJetGivenID(transaction.getJetID())).getQuantity());
        }catch (NullPointerException e){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Transaction not found. Please contact admin");
        }

        setupAvailableCountry();
        setupAvailablePaymentMethod();

        purchase_priceLabel.setText("$ "+String.valueOf(transaction.getTotalAmount()));
        featureTextArea.setText(transaction.getAdditionalFeatures());
        bankNameTextField.setText(transaction.getBankName());
        quantityBox.setValue(String.valueOf(transaction.getJetQuantity()));
        paymentBox.setValue(Payment.getPaymentName(transaction.getPaymentmethod()));
        countryBox.setValue(Country.getCountryName(transaction.getDeliveryCountry()));
        locationBox.setValue(Location.getLocationName(transaction.getDeliveryLocation()));

        featureTextArea.setEditable(false);
        bankNameTextField.setEditable(false);
        quantityBox.setDisable(true);
        paymentBox.setDisable(true);
        countryBox.setDisable(true);
        locationBox.setDisable(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //to limit the number of characters in text area (100 characters)
        featureTextArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));

        backFormClientBtn.setOnAction(e -> {
            clientInterfaceController.displayCartTable();
        });

        countryBox.setOnAction(e -> {
            setupAvailableAirports(countryBox.getValue());
        });

        quantityBox.setOnAction(e->{
            updatePriceLabel();
        });

        resetForm.setOnAction(e -> {
            setResetForm();
        });

        submitForm.setOnAction(e -> {
            if (!checkEmptyFields()){
                Submission.setSubmitForm(paymentBox.getValue(),countryBox.getValue(),locationBox.getValue(),quantityBox.getValue(),featureTextArea.getText().trim(),totalPrice,bankNameTextField.getText().trim(),cartID);
                Cart.editCartStatus(cartID, CommonFunctions.u_consideration);
                PrivateJet.updateJetQuantity(jet,false, Integer.parseInt(quantityBox.getValue())); //reducing the quantity
                CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Your application is successful!");
                clientInterfaceController.displayCartTable();
            }
        });

        backFormPreviewBtn.setOnAction(e->{
            adminInterfaceController.removeAll();
            if (caller.equals("Submission")){
                adminInterfaceController.displayTransactionTables("Submission");
            } else if (caller.equals("Transaction")){
                adminInterfaceController.displayTransactionTables("Transaction");
            }
        });

        acceptBtn.setOnAction(e -> {
            transactionOperationController.applicationAccepted(transaction.getTransactionID());
            adminInterfaceController.displayTransactionTables("Submission");
        });

        rejectBtn.setOnAction(e -> {
            adminInterfaceController.removeAll();
            transactionOperationController.applicationRejected(transaction.getTransactionID());
            adminInterfaceController.displayTransactionTables("Submission");
            PrivateJet.updateJetQuantity(jet,true, Integer.parseInt(quantityBox.getValue())); //reducing the quantity
        });

    }
}
