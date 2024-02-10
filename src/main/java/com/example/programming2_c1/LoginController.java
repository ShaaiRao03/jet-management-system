package com.example.programming2_c1;

import com.example.programming2_c1.*;
import com.example.programming2_c1.UserClasses.Admin;
import com.example.programming2_c1.UserClasses.Client;
import com.example.programming2_c1.UserClasses.Login;
import com.example.programming2_c1.UserClasses.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField answerField;

    @FXML
    private Button backBtnFP;

    @FXML
    private AnchorPane forgotPassAnchor;

    @FXML
    private Hyperlink forgotPassword;

    @FXML
    private Button loginbtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField newPasswordField;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink register;

    @FXML
    private TextField sq_field;

    @FXML
    private Button updatePasswordBtn;

    @FXML
    private TextField userNameFP;

    @FXML
    private TextField username;

    @FXML
    private Button verifyAnswerFPBtn;

    @FXML
    private Button verifyUsernameFPBtn;


    public TextField getUsername() {
        return username;
    }

    private ScreenController screenControllerLogin = new ScreenController();

    private Login loginUser ;

    public void loginUsers() {
        Connection connect;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        String login_query = "SELECT * FROM logindetailsjet WHERE username = ? and password = ?";

        connect = JetSysDatabase.getConnection();

        boolean isFound = false;

        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            CommonFunctions.createAlert(Alert.AlertType.ERROR, "Error Message", "Please fill in all the blank fields");

        } else {
            try {

                preparedStatement = connect.prepareStatement(login_query);
                preparedStatement.setString(1, username.getText());
                preparedStatement.setString(2, password.getText());

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String db_username = resultSet.getString("userName");
                    String db_password = resultSet.getString("password");
                    String role = resultSet.getString("role");

                    if (username.getText().equals(db_username) && password.getText().equals(db_password)) {
                        CommonFunctions.createAlert(Alert.AlertType.INFORMATION, "Information", "Log In Successful");
                        isFound = true;

                        try {

                            User user = new User();
                            Stage primaryStage = new Stage();
                            screenControllerLogin.start(primaryStage);


                            if (role.equals(CommonFunctions.client)) {
                                user = Client.getClientGivenUsername(db_username);
                                screenControllerLogin.changeSceneSpecific(new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/programming2_c1/clientInterface.fxml"))), user, role);

                            } else {
                                user = Admin.getAdminGivenUsername(db_username);
                                screenControllerLogin.changeSceneSpecific(new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/programming2_c1/adminInterface.fxml"))), user, role);
                            }

                            loginbtn.getScene().getWindow().hide();

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

                if (!isFound) {
                    CommonFunctions.createAlert(Alert.AlertType.ERROR, "Error Message", "Wrong username/password. Please try again.");
                }

                connect.close();

            }catch (Exception e){
                System.out.println("LoginController (loginUsers()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connect,preparedStatement,resultSet);
            }
        }
    }

    public void setLoginAnchor(boolean loginAnchor, boolean fp_Anchor){
        main_form.setVisible(loginAnchor);
        forgotPassAnchor.setVisible(fp_Anchor);
    }

    public void field_BtnUsability(boolean userNameFP, boolean passwordFP,boolean answerField, boolean verifyUsername, boolean verifyAnswer, boolean updatePassword){
        this.userNameFP.setDisable(userNameFP);
        this.newPasswordField.setDisable(passwordFP);
        this.answerField.setDisable(answerField);
        this.verifyUsernameFPBtn.setDisable(verifyUsername);
        this.verifyAnswerFPBtn.setDisable(verifyAnswer);
        this.updatePasswordBtn.setDisable(updatePassword);
    }

    public void setVerifyUsernameFP(){
        if (userNameFP.getText().isEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR, "Error Message", "Please fill in the Username field");
        } else {
             loginUser = Login.getLoginDetailsGivenUsername(userNameFP.getText());

            if (loginUser == null){
                CommonFunctions.createAlert(Alert.AlertType.ERROR, "Error Message", "Wrong username. Please try again");
            } else {
                sq_field.setText(loginUser.getSecurityQues());
                field_BtnUsability(true,true,false,true,false,true);
            }
        }
    }

    public void setVerifyAnswerFP(){
        if (answerField.getText().isEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR, "Error Message", "Please fill in Answer field");
        } else {
            if (answerField.getText().equals(loginUser.getAnswer())){
                field_BtnUsability(true,false,true,true,true, false);
            } else {
                CommonFunctions.createAlert(Alert.AlertType.ERROR, "Error Message", "Wrong answer. Please try again.");
            }
        }
    }

    public void setNewPasswordField() throws InterruptedException {
        if (answerField.getText().isEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR, "Error Message", "Please fill in the Password field");
        } else {
            CommonFunctions.createAlert(Alert.AlertType.INFORMATION, "Successful", "Your new password has been updated successfully.");
            Login.updateLoginJet(loginUser.getUserName(),newPasswordField.getText());
            loginUser = null;
            setLoginAnchor(true,false);
        }
    }

    
    public void resetAlLFP(){
        userNameFP.clear();
        sq_field.clear();
        newPasswordField.clear();
        answerField.clear();
        field_BtnUsability(false,true,true,false,true,true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.requestFocus();

        sq_field.setDisable(true);
        field_BtnUsability(false,true,true,false,true,true);

        setLoginAnchor(true,false);

        loginbtn.setOnAction(e ->{
            loginUsers();
        });

        register.setOnAction(e-> {
            try {
                register.getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                screenControllerLogin.start(primaryStage);
                screenControllerLogin.changeSceneSpecific(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/programming2_c1/register.fxml"))),1132,706));

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        forgotPassword.setOnMouseClicked(e -> {
            setLoginAnchor(false,true);
        });

        backBtnFP.setOnAction(e -> {
            setLoginAnchor(true,false);
            resetAlLFP();
        });

        verifyUsernameFPBtn.setOnAction(e->{
           setVerifyUsernameFP();
        });

        verifyAnswerFPBtn.setOnAction(e -> {
            setVerifyAnswerFP();
        });

        updatePasswordBtn.setOnAction(e -> {
            try {
                setNewPasswordField();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}