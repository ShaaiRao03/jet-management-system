package com.example.programming2_c1.ClientControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.JetClasses.PrivateJet;
import com.example.programming2_c1.JetSysDatabase;
import com.example.programming2_c1.UserClasses.Cart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;



public class CartController implements Initializable {

    @FXML
    private AnchorPane cartAnchor;

    @FXML
    private TableColumn<Cart, String> actionsCol;

    @FXML
    private TableColumn<Cart, Integer> cartIDCol;

    @FXML
    private TableView<Cart> cartTable;

    @FXML
    private Button clearBtn;

    @FXML
    private TableColumn<Cart,String> jetIDCol;

    @FXML
    private TableColumn<Cart, String> jetNameCol;

    @FXML
    private TableColumn<Cart, String> statusCol;

    public static ClientInterfaceController clientInterfaceController;

    private ObservableList<Cart> carts = FXCollections.observableArrayList();

    public void getAllCartDetails(String userName){
        Thread cartDetails = new Thread(() ->{

            Connection connectCart = null;
            ResultSet resultCart = null;
            PreparedStatement statementCart = null;

            try {
                String obtainManufacturer = "SELECT * FROM cart WHERE userName = ?";
                connectCart = JetSysDatabase.getConnection();

                statementCart = connectCart.prepareStatement(obtainManufacturer);
                statementCart.setString(1, userName);
                resultCart = statementCart.executeQuery();

                while (resultCart.next()){
                    carts.add(new Cart(resultCart.getInt(2),resultCart.getString(3),resultCart.getString(4),resultCart.getString(5)));
                }

            }catch (Exception e){
                System.out.println("CartController (getAllCartDetails()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectCart,statementCart,resultCart);
            }
        }); cartDetails.start();
    }

    public void displayTable(){

        getAllCartDetails(clientInterfaceController.getUser().getUserName());

        cartIDCol.setCellValueFactory(new PropertyValueFactory<Cart,Integer>("cartID"));
        jetIDCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("jetID"));
        jetNameCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("jetName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("status"));

        cartIDCol.setResizable(false);
        jetIDCol.setResizable(false);
        jetNameCol.setResizable(false);
        statusCol.setResizable(false);

        createActionButtonsCart();
        cartTable.setItems(carts);
    }

    private void createActionButtonsCart() {
        //to add button into the table
        //add cell of button edit
        Callback<TableColumn<Cart, String>, TableCell<Cart, String>> cellFactory = (TableColumn<Cart, String> param) -> {
            // make cell containing buttons
            final TableCell<Cart, String> cell = new TableCell<Cart, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button removeBtn = new Button("Remove");
                        removeBtn.setStyle("-fx-background-color: red ; -fx-text-fill: white");

                        removeBtn.setOnAction(e -> {

                            if(getTableView().getItems().get(getIndex()).getStatus().equals(CommonFunctions.u_consideration)){ // if under consideration means , the application is submitted and quantity is reduced
                                try {
                                    //need to re-add the stock
                                    PrivateJet.updateJetQuantity(JetData.getPrivateJetGivenID(getTableView().getItems().get(getIndex()).getJetID()),true,Cart.getSubmissionGivenCartID(getTableView().getItems().get(getIndex()).getCartID()).getJetQuantity()); //jetID , boolean true for adding , get the submission's quantity
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            Cart.removeCartFromDatabase(getTableView().getItems().get(getIndex()).getCartID()); //getTableView().getItems().get(getIndex()) returns Cart
                            cartTable.getItems().remove(getIndex());
                            CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Removed","Removed from the cart.");

                        });


                        Button previewBtn = new Button("Preview");
                        previewBtn.setStyle("-fx-background-color: #f5bb00 ; -fx-text-fill: white");


                        previewBtn.setOnAction(e -> {
                            try {
                                clientInterfaceController.anchorDisplayController(false,false,false,true,false);
                                clientInterfaceController.displayPreviewTransaction(Cart.getSubmissionGivenCartID(getTableView().getItems().get(getIndex()).getCartID())); // as long as there's cartID , we can acesss submissionID
                                clientInterfaceController.setVboxPreview(JetData.getPrivateJetGivenID(getTableView().getItems().get(getIndex()).getJetID()));
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        });


                        Button applicationBtn = new Button("Fill application");
                        applicationBtn.setStyle("-fx-background-color: green ; -fx-text-fill: white");

                        previewBtn.setDisable(true);
                        applicationBtn.setDisable(true);

                        if (getTableView().getItems().get(getIndex()).getStatus().equals(CommonFunctions.pending) && Cart.getCartJetRealQuantity(getTableView().getItems().get(getIndex()).getJetID()) > 0){ //getTableView().getItems().get(getIndex()) returns Transaction
                            applicationBtn.setDisable(false);
                            applicationBtn.setOnAction(e -> {
                                clientInterfaceController.displayApplicationForm(getTableView().getItems().get(getIndex()).getJetID(),getTableView().getItems().get(getIndex()).getCartID());
                                clientInterfaceController.anchorDisplayController(false,false,false,true,false);
                            });
                        } else if (!getTableView().getItems().get(getIndex()).getStatus().equals(CommonFunctions.pending)){
                            previewBtn.setDisable(false);
                        }

                        HBox managebtn = new HBox(removeBtn, applicationBtn,previewBtn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(applicationBtn, new Insets(2, 2, 0, 0));
                        HBox.setMargin(removeBtn, new Insets(2, 3, 0, 1));
                        HBox.setMargin(previewBtn, new Insets(2, 3, 0, 1));

                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
        actionsCol.setCellFactory(cellFactory);
    }

    public void clearAllCartID(){
        for (Cart data: cartTable.getItems()) {

            if(data.getStatus().equals(CommonFunctions.u_consideration)){ // if under consideration means , the application is submitted and quantity is reduced
                try {
                    //need to re-add the stock
                    PrivateJet.updateJetQuantity(JetData.getPrivateJetGivenID(data.getJetID()),true,Cart.getSubmissionGivenCartID(data.getCartID()).getJetQuantity()); //jetID , boolean true for adding , get the submission's quantity
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

            Cart.removeCartFromDatabase(data.getCartID());
        }
        cartTable.getItems().removeAll(List.copyOf(carts));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearBtn.setOnAction(e -> {
            if (CommonFunctions.createAlertConfirmation("Confirmation","Are you sure to clear all ?")){
                clearAllCartID();
            }
        });


    }
}
