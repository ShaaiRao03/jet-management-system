package com.example.programming2_c1;

import com.example.programming2_c1.*;
import com.example.programming2_c1.UserClasses.Admin;
import com.example.programming2_c1.UserClasses.Client;
import com.example.programming2_c1.UserClasses.Login;
import com.example.programming2_c1.UserClasses.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private ComboBox<String> country;

    @FXML
    private ComboBox<String> s_question;

    @FXML
    private TextField sq_answer;

    @FXML
    private TextField address;

    @FXML
    private Button backBtn;

    @FXML
    private TextField email;

    @FXML
    private TextField f_name;

    @FXML
    private TextField icNum;

    @FXML
    private TextField l_name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phoneNum;

    @FXML
    private TextField username;

    @FXML
    private TextField company;

    @FXML
    private Button resetBtn;

    @FXML
    private TextField job;


    private Connection connect;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private ObservableList<String> countries = FXCollections.observableArrayList(
            "Malaysia","USA","China","France","India","Greece");

    private ObservableList<String> questions = FXCollections.observableArrayList(
            "What is your favourite food ?","What is your favourite car ?","Who is your favourite actor ?");


    public void resetAll() {
        f_name.clear();
        l_name.clear();
        username.clear();
        password.clear();
        address.clear();
        email.clear();
        phoneNum.clear();
        icNum.clear();
        sq_answer.clear();
        job.clear();
        company.clear();
        country.getSelectionModel().clearSelection();
        s_question.getSelectionModel().clearSelection();
        CommonFunctions.initializeReset(country);
        CommonFunctions.initializeReset(s_question);
    }

    public boolean isUserExist(){
        //to prevent duplicated usernames
        return Login.getLoginDetailsGivenUsername(username.getText()) != null;
    }

    public void insertData() throws SQLException{
        //to insert data into the database

        if (isEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error Message","Please fill in all the blank fields");
        } else {

            if (CommonFunctions.validationLong(phoneNum.getText(),"Invalid input for 'Phone number field'") && CommonFunctions.validationLong(icNum.getText(),"Invalid input for 'Identification Card Number field'")){

                if (!isUserExist()){
                    try {
                        User.insertToUser(username.getText(), icNum.getText(),f_name.getText(),l_name.getText(),email.getText(),phoneNum.getText(),address.getText(),country.getValue());
                        Client.insertToClient(username.getText(),job.getText(),company.getText());
                        Login.insertToLoginDetails(username.getText(),password.getText(),s_question.getValue(),sq_answer.getText(),CommonFunctions.client);

                        CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Success","Registration successful !");
                        redirectLoginPage();

                    }catch (Exception e){
                        System.out.println("RegisterController (insertData()) has not been connected to database");
                    } finally {
                        JetSysDatabase.closeConnection(connect,preparedStatement,resultSet);
                    }

                } else {
                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"Username Error","Username already exist !");
                }
            }
        }
    }




    public boolean isEmpty(){
        //to check for empty fields
        return f_name.getText().isEmpty() || l_name.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty() || address.getText().isEmpty() || email.getText().isEmpty() || phoneNum.getText().isEmpty() || icNum.getText().isEmpty() || country.getValue() == null || company.getText().isEmpty() || job.getText().isEmpty() || sq_answer.getText().isEmpty() || s_question.getValue() == null;
    }


    public void redirectLoginPage(){
        ScreenController screenControllerRegister = new ScreenController();
        try {
            backBtn.getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            screenControllerRegister.start(primaryStage);
            screenControllerRegister.changeSceneSpecific(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/programming2_c1/login.fxml"))),1132,706));

        } catch (IOException  | NullPointerException ex) {
            System.out.println("Error in Redirect login page");
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        s_question.setItems(questions);
        s_question.setVisibleRowCount(3);

        country.setItems(countries);
        country.setVisibleRowCount(5);

        backBtn.setOnAction(e-> {
            redirectLoginPage();
        });

    }
}
