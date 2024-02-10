package com.example.programming2_c1.ClientControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.UserClasses.Client;
import com.example.programming2_c1.UserClasses.Login;
import com.example.programming2_c1.UserClasses.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientDashboardController implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private TextField company;

    @FXML
    private Button editBtn;

    @FXML
    private TextField email;

    @FXML
    private TextField f_name;

    @FXML
    private TextField job;

    @FXML
    private TextField l_name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phoneNum;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField username;

    @FXML
    private TextField validationPassword;

    public static ClientInterfaceController clientInterfaceController;

    public String prevUsername;

    public void statusField(boolean status){
        address.setDisable(status);
        company.setDisable(status);
        email.setDisable(status);
        f_name.setDisable(status);
        l_name.setDisable(status);
        job.setDisable(status);
        password.setDisable(status);
        username.setDisable(status);
        phoneNum.setDisable(status);
    }

    public void setAllField(){
        address.setText(clientInterfaceController.getUser().getAddress());
        company.setText(clientInterfaceController.getUser().getCompany());
        email.setText(clientInterfaceController.getUser().getEmail());
        f_name.setText(clientInterfaceController.getUser().getFirst_name());
        l_name.setText(clientInterfaceController.getUser().getLast_name());
        job.setText(clientInterfaceController.getUser().getJob());
        password.setText(clientInterfaceController.getUser().getPassword());
        username.setText(clientInterfaceController.getUser().getUserName());
        phoneNum.setText(String.valueOf(clientInterfaceController.getUser().getPhoneNum()));
    }

    public void resetUserClass(){
        // setting the previous value in the Admin class
        clientInterfaceController.getUser().setAddress(address.getText());
        clientInterfaceController.getUser().setCompany(company.getText());
        clientInterfaceController.getUser().setEmail(email.getText());
        clientInterfaceController.getUser().setFirst_name(f_name.getText());
        clientInterfaceController.getUser().setLast_name(l_name.getText());
        clientInterfaceController.getUser().setJob(job.getText());
        clientInterfaceController.getUser().setPassword(password.getText());
        clientInterfaceController.getUser().setUserName(username.getText());
        clientInterfaceController.getUser().setPhoneNum(phoneNum.getText());
    }

    public boolean checkIsEmpty(){
        return f_name.getText().isEmpty() || l_name.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty() || address.getText().isEmpty() || email.getText().isEmpty() || phoneNum.getText().isEmpty() || company.getText().isEmpty() || job.getText().isEmpty();
    }


    public void setValidatorComponents(boolean editBtn, boolean updateBtn, boolean validationPassword){
        this.updateBtn.setDisable(updateBtn);
        this.editBtn.setDisable(editBtn);
        this.validationPassword.setDisable(validationPassword);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setAllField();
        statusField(true);
        updateBtn.setDisable(true);

        editBtn.setOnAction(e -> {
            prevUsername = username.getText();
            if (validationPassword.getText().equals(clientInterfaceController.getUser().getPassword())){
                statusField(false);

                setValidatorComponents(true,false,true);

                validationPassword.clear();
                validationPassword.setPromptText("Enter password to edit");

            } else {
                CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error Message","Incorrect password");
            }
        });


        updateBtn.setOnAction(e -> {
            if (checkIsEmpty()){
                CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error Message","Please fill in all the blank fields");

            } else {

                if (CommonFunctions.validationLong(phoneNum.getText(),"Invalid input for 'Phone number field'")){
                    try {
                        //threads are used in the called methods below to avoid the gui from freezing during the updating process
                        User.updateUserJet(username.getText(), f_name.getText(), l_name.getText(),email.getText(),phoneNum.getText(),
                                address.getText(),prevUsername);

                        Login.updateLoginJet(prevUsername,password.getText());

                        Client.updateClient(job.getText(),company.getText(),prevUsername);

                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    setValidatorComponents(false,true,false);

                    resetUserClass();

                    setAllField();
                    statusField(true);
                    CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Update Successful !"); //CLEANUP

                }
            }
        });
    }
}
