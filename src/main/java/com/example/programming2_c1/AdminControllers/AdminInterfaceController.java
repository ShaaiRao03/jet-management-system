package com.example.programming2_c1.AdminControllers;


import com.example.programming2_c1.ClientControllers.ApplicationFormController;
import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.Interfaces;
import com.example.programming2_c1.ScreenController;
import com.example.programming2_c1.UserClasses.Admin;
import com.example.programming2_c1.UserClasses.Client;
import com.example.programming2_c1.UserClasses.Transaction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminInterfaceController implements Initializable, Interfaces.AdminInterface {

    @FXML
    public ScrollPane scrollPaneAdmin;

    @FXML
    private GridPane gridAdmin;

    @FXML
    private Button addJetDataBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button transactionOperationBtn;
    @FXML
    private Button adminLogOutBtn;

    @FXML
    private Label navigationLabel;

    Admin user;

    public Admin getUser() {
        return user;
    }

    public void initData(Admin user){
        this.user = user;
    }

    public void setNavigationLabel(String navigationLabel) {
        this.navigationLabel.setText(navigationLabel);
    }

    public ScrollPane getScrollPaneAdmin() {
        return scrollPaneAdmin;
    }


    public void setSliderProperty(double vValue, double vMax){
        scrollPaneAdmin.setVvalue(vValue);
        scrollPaneAdmin.setVmax(vMax);
    }

    public void removeAll(){
        try{
            ArrayList<Node> temp = new ArrayList<Node>(gridAdmin.getChildren().stream().toList());

            Iterator<Node> iterateNode = temp.iterator();
            while (iterateNode.hasNext()){
                Node children = iterateNode.next();
                gridAdmin.getChildren().remove(children);
            }

        }catch (ConcurrentModificationException e){
            e.printStackTrace();
        }
    }

    public void resetScrollPane(){
        scrollPaneAdmin.setVvalue(0.0);
        scrollPaneAdmin.setVmax(1);
    }

    public void displayTransactionTables(String type){

        removeAll();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/transactionAdmin.fxml"));
                    AnchorPane anchorDisplay = fxmlLoader.load();
                    TransactionOperationController transactionOperationController = fxmlLoader.getController();

                    if (type.equals("Transaction")){
                        transactionOperationController.displayTransactionOperationTables(true,"Transaction");
                    } else {

                        transactionOperationController.displayTransactionOperationTables(false,"Submission");
                    }


                    gridAdmin.add(anchorDisplay, 0,1); //(child,column,row)
                    scrollPaneAdmin.setVvalue(0.0);

                    //set grid width
                    gridAdmin.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridAdmin.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridAdmin.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    gridAdmin.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridAdmin.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridAdmin.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorDisplay, new Insets(10));

                } catch (IOException | NullPointerException | ConcurrentModificationException e) {
                    e.printStackTrace();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void displayAddDataTable(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/insertTables.fxml"));
                    AnchorPane anchorDisplay = fxmlLoader.load();
                    DataOperationController dataOperationController = fxmlLoader.getController();
                    dataOperationController.displayManufacturerTable();

                    gridAdmin.add(anchorDisplay, 0,1); //(child,column,row)
                    scrollPaneAdmin.setVvalue(0.0);

                    //set grid width
                    gridAdmin.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridAdmin.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridAdmin.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    gridAdmin.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridAdmin.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridAdmin.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorDisplay, new Insets(10));

                } catch (IOException | NullPointerException | ConcurrentModificationException e) {
                    e.printStackTrace();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void displayPreviewTransaction(Transaction transaction,String caller){

        removeAll();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/applicationForm.fxml"));
            AnchorPane anchorForm = fxmlLoader.load();

            ApplicationFormController applicationFormController = fxmlLoader.getController();
            applicationFormController.displayInPreviewMode(transaction,caller);
            applicationFormController.setRole(CommonFunctions.admin);

            resetScrollPane();
            gridAdmin.setHgap(12);
            GridPane.setMargin(anchorForm, new Insets(20));

            gridAdmin.add(anchorForm,0,1);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void displayDashboard(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/adminDashboard.fxml"));
            AnchorPane anchorForm = fxmlLoader.load();

            gridAdmin.setHgap(12);
            GridPane.setMargin(anchorForm, new Insets(20));

            gridAdmin.add(anchorForm,0,1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void redirectLoginPage(){
        ScreenController screenControllerInventory = new ScreenController();
        try {
            adminLogOutBtn.getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            screenControllerInventory.start(primaryStage);
            screenControllerInventory.changeSceneSpecific(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/programming2_c1/login.fxml"))),1132,706));

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setNavigationLabel("Data Operation  /  Manufacturer");

        displayAddDataTable();

        DataOperationController.adminInterfaceController = AdminInterfaceController.this;
        TransactionOperationController.adminInterfaceController = AdminInterfaceController.this;
        ApplicationFormController.adminInterfaceController = AdminInterfaceController.this;
        AdminDashboardController.adminInterfaceController = AdminInterfaceController.this;

        addJetDataBtn.setOnAction(e -> {
            setNavigationLabel("Data Operation  /  Manufacturer");
            removeAll();
            displayAddDataTable();
        });

        transactionOperationBtn.setOnAction(e -> {
            setNavigationLabel("Transaction Operation  /  Under Consideration");
            removeAll();
            displayTransactionTables("Submission");
        });

        dashboardBtn.setOnAction(e -> {
            setSliderProperty(0.0,0.4);
            setNavigationLabel("Dashboard  /  Profile");
            removeAll();
            displayDashboard();
        });

        adminLogOutBtn.setOnAction(e -> {
            redirectLoginPage();
        });


    }
}
