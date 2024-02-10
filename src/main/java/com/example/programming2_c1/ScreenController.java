package com.example.programming2_c1;

import com.example.programming2_c1.AdminControllers.AdminInterfaceController;
import com.example.programming2_c1.ClientControllers.ClientInterfaceController;
import com.example.programming2_c1.UserClasses.Admin;
import com.example.programming2_c1.UserClasses.Client;
import com.example.programming2_c1.UserClasses.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ScreenController extends JetManagementSys {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    //use this for general controllers
    public void changeSceneSpecific(Scene scene) throws IOException {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //use this for different type of interface controller
    public void changeSceneSpecific(FXMLLoader fxmlLoader, User user, String role) throws IOException {

        primaryStage.setScene(new Scene(fxmlLoader.load()));

        if (role.equals(CommonFunctions.client)){
            ClientInterfaceController controller = fxmlLoader.getController();
            Client client = (Client) user;
            controller.initData(client);
        } else {
            AdminInterfaceController controller = fxmlLoader.getController();
            Admin admin = (Admin) user;
            controller.initData(admin);
        }
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public ScreenController(){}

}
