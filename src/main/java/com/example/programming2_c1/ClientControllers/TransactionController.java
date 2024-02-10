package com.example.programming2_c1.ClientControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.Elements.PDFGenerator;
import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.UserClasses.Client;
import com.example.programming2_c1.UserClasses.Transaction;
import com.itextpdf.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction,String> actionCol;

    @FXML
    private TableColumn<Transaction, LocalDate> dateCol;

    @FXML
    private TableColumn<Transaction, String> jetIDcol;

    @FXML
    private TableColumn<Transaction, String> nameCol;

    @FXML
    private TableColumn<Transaction, Integer> transactionIDCol;

    @FXML
    private Button hideBtn;

    @FXML
    private Button unhideBtn;

    @FXML
    private TextField passwordTransaction;

    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public static ClientInterfaceController clientInterfaceController;

    public void displayTransaction() throws InterruptedException {

        ArrayList<Transaction> temp = Transaction.getAllTransactionDetails(clientInterfaceController.getUser().getUserName());
        transactions.addAll(temp);

        transactionIDCol.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("transactionID"));
        jetIDcol.setCellValueFactory(new PropertyValueFactory<Transaction,String>("jetID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Transaction,String>("jetName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Transaction,LocalDate>("transactionDate"));

        transactionIDCol.setResizable(false);
        jetIDcol.setResizable(false);
        nameCol.setResizable(false);
        dateCol.setResizable(false);

        createActionButtonsTransaction();
        transactionTable.setItems(transactions);
    }

    public void createActionButtonsTransaction(){
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

                        Button downloadBtn = new Button("Download");
                        downloadBtn.setStyle("-fx-background-color: #e79214 ; -fx-text-fill: white");

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

                        HBox managebtn = new HBox(downloadBtn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(downloadBtn, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
        actionCol.setCellFactory(cellFactory);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        unhideBtn.setDisable(false);
        hideBtn.setDisable(true);

        unhideBtn.setOnAction(e -> {
            if (passwordTransaction.getText().equals(clientInterfaceController.getUser().getPassword())){
                try {
                    displayTransaction();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                hideBtn.setDisable(false);
                unhideBtn.setDisable(true);
                passwordTransaction.clear();
                passwordTransaction.setPromptText("Enter password to unhide");
                passwordTransaction.setDisable(true);

            } else {
                CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Incorrect password.");
            }
        });

        hideBtn.setOnAction(e -> {
            hideBtn.setDisable(true);
            unhideBtn.setDisable(false);
            passwordTransaction.setDisable(false);
            transactionTable.getItems().removeAll(List.copyOf(transactions));
        });

    }
}
