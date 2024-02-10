package com.example.programming2_c1.AdminControllers;

import com.example.programming2_c1.ClientControllers.ApplicationFormController;
import com.example.programming2_c1.ClientControllers.TransactionController;
import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.Elements.PDFGenerator;
import com.example.programming2_c1.JetClasses.*;
import com.example.programming2_c1.UserClasses.*;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

public class TransactionOperationController implements Initializable {


    @FXML
    private TableColumn<Transaction, String> actionCol;

    @FXML
    private TableColumn<Transaction, LocalDate> dateCol;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private ComboBox<String> yearCombo;

    @FXML
    private TableColumn<Transaction, String> jetIDCol;

    @FXML
    private Button modelBtn;

    @FXML
    private TableColumn<Transaction, Long> totalAmountCol;

    @FXML
    private Slider priceSlider;

    @FXML
    private Button transactionSearchBtn;

    @FXML
    private TextField transactionSearchField;

    @FXML
    private TableView<Transaction> transactionViewTable;

    @FXML
    private TableColumn<Transaction, Integer> transactionIDCol;

    @FXML
    private TableColumn<Transaction, String> usernameCol;

    @FXML
    private Button underConsiderationBtn;

    @FXML
    private Button approvedBtn;

    @FXML
    private Button resetTransactionBtn;

    public static AdminInterfaceController adminInterfaceController;

    final ObservableList<String> months = FXCollections.observableArrayList(
            "January","February","March","April","May","June","July","August","September","October","November","December"
    );

    ObservableList<String> years = FXCollections.observableArrayList(
            "2021","2022","2023"
    );

    String previousSearch = "";
    String previousMonth = null;
    Long previousPriceRange = 0L;
    String previousYear = null;

    public TableColumn<Transaction, Integer> getTransactionIDCol() {
        return transactionIDCol;
    }


    public void displayTransactionOperationTables(boolean createDownlaodButton, String type) throws InterruptedException {
        //shows all approved transaction

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                totalAmountCol.setCellValueFactory(new PropertyValueFactory<Transaction,Long>("totalAmount"));
                jetIDCol.setCellValueFactory(new PropertyValueFactory<Transaction,String>("jetID"));
                dateCol.setCellValueFactory(new PropertyValueFactory<Transaction,LocalDate>("transactionDate"));
                transactionIDCol.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("transactionID"));
                usernameCol.setCellValueFactory(new PropertyValueFactory<Transaction,String>("userName"));

                try {
                    if (type.equals("Transaction")){
                        createActionButtonsTransaction(true);
                        transactionViewTable.setItems(FXCollections.observableArrayList(Transaction.getAllTransactionDetails("")));
                    } else {
                        createActionButtonsTransaction(false);
                        transactionViewTable.setItems(FXCollections.observableArrayList(Submission.getAllUnderConsiderationSubmissionDetails()));
                        transactionIDCol.setText("Submission ID");
                    }
                } catch (InterruptedException e){
                    throw  new RuntimeException(e);
                }

            }
        });
    }

    private void createActionButtonsTransaction(boolean showDownload) {

        //add cell of button edit
        Callback<TableColumn<Transaction, String>, TableCell<Transaction, String>> cellFactory = (TableColumn<Transaction, String> param) -> {
            // make cell containing buttons
            final TableCell<Transaction, String> cell = new TableCell<Transaction, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button previewBtn = new Button("Preview");
                        previewBtn.setStyle("-fx-background-color: #e79214 ; -fx-text-fill: white");

                        previewBtn.setOnAction(e -> {
                            if (transactionIDCol.getText().equals("Submission ID")){
                                adminInterfaceController.displayPreviewTransaction(transactionViewTable.getItems().get(getIndex()),"Submission"); // passing transaction
                            }else {
                                adminInterfaceController.displayPreviewTransaction(transactionViewTable.getItems().get(getIndex()),"Transaction"); // passing transaction
                            }
                        });

                        HBox managebtn = new HBox(previewBtn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(previewBtn, new Insets(2, 2, 0, 3));


                        if (showDownload){
                            Button downloadBtn = new Button("Download");

                            downloadBtn.setStyle("-fx-background-color: #f33a00 ; -fx-text-fill: white");
                            managebtn.getChildren().add(downloadBtn);
                            HBox.setMargin(downloadBtn, new Insets(2, 3, 0, 2));

                            Transaction transaction = getTableView().getItems().get(getIndex());

                            //generating pdf
                            downloadBtn.setOnAction(e -> {

                                DirectoryChooser directoryChooser = new DirectoryChooser();
                                directoryChooser.setTitle("Choose Directory");
                                Stage stage = new Stage();
                                File selectedDirectory = directoryChooser.showDialog(stage);

                                if (selectedDirectory != null) {
                                    String directory = selectedDirectory.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\");
                                    try {
                                        PDFGenerator.generatePDF(Transaction.getTransactionDetailsGivenID(transaction.getTransactionID()), Client.getClientGivenUsername(transaction.getUserName()),JetData.getPrivateJetGivenID(transaction.getJetID()),directory);
                                    } catch (FileNotFoundException | DocumentException | InterruptedException f) {
                                        throw new RuntimeException(f);
                                    }
                                } else {
                                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"ERROR","No directory has been selected");
                                }

                            });

                        }

                        setGraphic(managebtn);
                        setText(null);
                    }
                }

            };

            return cell;
        };
        actionCol.setCellFactory(cellFactory);
    }


    public void applicationAccepted(int submissionID) {
        Cart.editCartStatus(Submission.getCartIDGivenSubmissionID(submissionID),CommonFunctions.approved);
        Transaction.addTransactionToDatabase(Submission.getCartIDGivenSubmissionID(submissionID));
        CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Application Approved","Application has been approved.");
    }

    public void applicationRejected(int submissionID){
        Cart.editCartStatus(Submission.getCartIDGivenSubmissionID(submissionID),CommonFunctions.reject);
        CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Application Rejected","Application has been rejected.");
    }
    
    //-----------Search-----------------------
    public void initiateTransactionSearch(String caller) throws InterruptedException {


        String prefSearch = transactionSearchField.getText(); //only can search by transactionID or jetID
        String prefYear = yearCombo.getValue();
        String prefMonth = monthCombo.getValue();



        if (prefMonth != null){
            prefMonth = monthCombo.getValue().toUpperCase();

        }

        Long prefPriceRange = (long) priceSlider.getValue();


        if (isTransactionSearchChanged(prefSearch,prefMonth,prefYear,prefPriceRange)){

            if (caller.equals("Transaction")){

                transactionViewTable.setItems(FXCollections.observableArrayList(redisplayBasedOnTransactionFilter(Transaction.getAllTransactionDetails(""),prefSearch,prefMonth,prefYear,prefPriceRange)));
            }else {
                transactionViewTable.setItems(FXCollections.observableArrayList(redisplayBasedOnTransactionFilter(Submission.getAllUnderConsiderationSubmissionDetails(),prefSearch,prefMonth,prefYear,prefPriceRange)));
            }
            resetPreviousSearchValueTransaction(prefSearch,prefMonth,prefYear,prefPriceRange);

        }
    }

    public ArrayList<Transaction> redisplayBasedOnTransactionFilter(ArrayList<Transaction> transactionsArr,String prefSearch, String prefMonth , String prefYear , Long prefPriceRange) {

        ArrayList<Transaction> newTransaction = new ArrayList<>();

        Iterator entries;
        entries = transactionsArr.iterator();

        while (entries.hasNext()){
            Transaction jetTransaction = (Transaction) entries.next();

            //priceChanged will always be checked (first priority)
            // prefSearch = empty , prefMonth = empty , prefYear = empty
            if (prefYear == null && prefMonth == null && prefSearch.isEmpty()) { // year = empty , month = empty , search term = empty
                if (jetTransaction.getTotalAmount() >= prefPriceRange*1000000) {
                    newTransaction.add(jetTransaction);
                }

            } else if (prefYear == null && prefMonth == null) {   // year = empty , month = empty , search term = !empty
                if (String.valueOf(jetTransaction.getTransactionID()).equals(prefSearch) && jetTransaction.getTotalAmount() >= prefPriceRange * 1000000) {
                    newTransaction.add(jetTransaction);
                }

            }else if (prefYear == null && prefSearch.isEmpty()){   // year = empty , month = !empty , search term = empty
                if (String.valueOf(jetTransaction.getTransactionDate().getMonth()).equals(prefMonth) && jetTransaction.getTotalAmount() >= prefPriceRange*1000000) {
                    newTransaction.add(jetTransaction);
                }

            }else if (prefMonth == null && prefSearch.isEmpty()){   // year = !empty , month = empty , search term = empty
                if (prefYear.equals(String.valueOf(jetTransaction.getTransactionDate().getYear())) && jetTransaction.getTotalAmount() >= prefPriceRange*1000000) {
                    newTransaction.add(jetTransaction);
                }

            } else if (prefYear == null){   // year = empty , month = !empty , search term = !empty
                if (String.valueOf(jetTransaction.getTransactionID()).equals(prefSearch) && (jetTransaction.getTotalAmount() >= (prefPriceRange*1000000)) && String.valueOf(jetTransaction.getTransactionDate().getMonth()).equals(prefMonth) ){
                    newTransaction.add(jetTransaction);
                }

            } else if (prefMonth == null){   // year = !empty , month = empty , search term = !empty
                if (String.valueOf(jetTransaction.getTransactionID()).equals(prefSearch) && (jetTransaction.getTotalAmount() >= (prefPriceRange*1000000)) && prefYear.equals(String.valueOf(jetTransaction.getTransactionDate().getYear())) ){
                    newTransaction.add(jetTransaction);
                }

            } else if (prefSearch.equals("") && prefMonth != null && prefYear != null){   // year = !empty , month = !empty , search term = empty //here
                if (prefMonth.equals(String.valueOf(jetTransaction.getTransactionDate().getMonth())) && (jetTransaction.getTotalAmount() >= (prefPriceRange*1000000)) && prefYear.equals(String.valueOf(jetTransaction.getTransactionDate().getYear())) ) {
                    newTransaction.add(jetTransaction);
                }

            } else { // year = !empty , month = !empty , search term = !empty
                if (String.valueOf(jetTransaction.getTransactionID()).equals(prefSearch) && (jetTransaction.getTotalAmount() >= (prefPriceRange*1000000)) &&  prefMonth.equals(String.valueOf(jetTransaction.getTransactionDate().getMonth())) && prefYear.equals(String.valueOf(jetTransaction.getTransactionDate().getYear()))){
                    newTransaction.add(jetTransaction);
                }
            }
        }
        return newTransaction;
    }

    public boolean isTransactionSearchChanged(String prefSearch, String prefMonth , String prefYear , Long prefPriceRange){
        return prefYear != previousYear || prefMonth != previousMonth || !previousPriceRange.equals(prefPriceRange) || !previousSearch.equals(prefSearch);
    }

    public void resetPreviousSearchValueTransaction(String prefSearch, String prefMonth , String prefYear , Long prefPriceRange){  //SEARCH
        previousSearch = prefSearch;
        previousMonth = prefMonth;
        previousYear = prefYear;
        previousPriceRange = prefPriceRange;
    }

    public void setResetTransactionBtn(String caller){
        transactionSearchField.clear();
        transactionSearchField.setPromptText("Search by Transaction ID");
        monthCombo.getSelectionModel().clearSelection();
        yearCombo.getSelectionModel().clearSelection();
        CommonFunctions.initializeReset(monthCombo);
        CommonFunctions.initializeReset(yearCombo);
        resetPreviousSearchValueTransaction("",null,null,0L);
        priceSlider.setValue(0);

        try {
            if (caller.equals("Transaction")){
                displayTransactionOperationTables(true,"Transaction");
            } else {
                displayTransactionOperationTables(false,"Submission");
            }
        } catch (InterruptedException e){
            throw  new RuntimeException(e);
        }
    }

    //--------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        adminInterfaceController.setSliderProperty(0.0,0.4);
        adminInterfaceController.getScrollPaneAdmin().setVmax(1);
        priceSlider.setShowTickLabels(true);

        ApplicationFormController.transactionOperationController = TransactionOperationController.this;

        yearCombo.setItems(years);
        yearCombo.setVisibleRowCount(5);
        monthCombo.setItems(months);
        monthCombo.setVisibleRowCount(5);

        approvedBtn.setOnAction(e -> {
            adminInterfaceController.setNavigationLabel("Transaction Operation  /  Approved transactions");
            transactionIDCol.setText("Transaction ID");
            setResetTransactionBtn("Transaction");
            adminInterfaceController.setSliderProperty(0.0,0.4);
        });


        underConsiderationBtn.setOnAction(e -> {
            adminInterfaceController.setNavigationLabel("Transaction Operation  /  Under Consideration");
            transactionIDCol.setText("Submission ID");
            setResetTransactionBtn("Submission");
            adminInterfaceController.setSliderProperty(0.0,0.4);
        });


        transactionSearchBtn.setOnAction(e -> {
            try {
                if (transactionIDCol.getText().equals("Submission ID")){
                    initiateTransactionSearch("Submission");

                }else {
                    initiateTransactionSearch("Transaction");

                }

            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        });

        resetTransactionBtn.setOnAction(e -> {
            if (transactionIDCol.getText().equals("Submission ID")){
                setResetTransactionBtn("Submission");
            }else {
                setResetTransactionBtn("Transaction");
            }
        });

    }
}
