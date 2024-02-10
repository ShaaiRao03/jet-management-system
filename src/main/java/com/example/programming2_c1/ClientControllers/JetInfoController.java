package com.example.programming2_c1.ClientControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.JetClasses.PrivateJet;
import com.example.programming2_c1.UserClasses.Cart;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class JetInfoController implements Initializable {

    @FXML
    private Label aftCabinConfigLabel;

    @FXML
    private Label basePaintLabel;

    @FXML
    private Label cyclessSinceNewLabel;

    @FXML
    private Label engineDescLabel;

    @FXML
    private Label forwardCabinConfigLabel;

    @FXML
    private Label hoursSinceNewLabel;

    @FXML
    private ImageView imgJetInfo;

    @FXML
    private AnchorPane jetInfoAnchor;

    @FXML
    private VBox jetInfoVbox;

    @FXML
    private Label landingSinceNewLabel;

    @FXML
    private Label leftEngineIDLabel;

    @FXML
    private Label numPassengerLabel;

    @FXML
    private Label rightEngineIDLabel;

    @FXML
    private Label stripeLabel;

    @FXML
    private Label timeSinceNewLabel;

    @FXML
    private Button backDetailsBtn;

    public static ClientInterfaceController clientInterfaceController;

    public void setData(PrivateJet jet){

        Image image;

        try{
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/jets/"+jet.getImgSrc())));
        }catch (NullPointerException e){
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/jets/errorPIC.png")));
        }

        imgJetInfo.setImage(image);

        //effect on the image
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage imageRound = imgJetInfo.snapshot(parameters,null);
        imgJetInfo.setEffect(new DropShadow(30,Color.rgb(207, 102, 0)));
        imgJetInfo.setImage(image);

        //Airframe
        timeSinceNewLabel.setText(String.valueOf(jet.getAirframe().getTimeSinceNew())+" Hours");
        landingSinceNewLabel.setText(String.valueOf(jet.getAirframe().getLandings())+" Landings");

        //Engine
        hoursSinceNewLabel.setText(String.valueOf(jet.getEngine().getHoursSinceNew())+" Hours");
        cyclessSinceNewLabel.setText(String.valueOf(jet.getEngine().getCycleSinceNew())+" Cycles");
        rightEngineIDLabel.setText(jet.getEngine().getRightEngineID());
        leftEngineIDLabel.setText(jet.getEngine().getLeftEngineID());
        engineDescLabel.setText(jet.getEngine().getEngineDescription());

        //Interior
        forwardCabinConfigLabel.setText(jet.getInterior().getForwardCabinConfig());
        aftCabinConfigLabel.setText(jet.getInterior().getAftCabinConfig());
        numPassengerLabel.setText(String.valueOf(jet.getInterior().getNumPassengers()));

        //Exterior
        stripeLabel.setText(jet.getExterior().getStripe());
        basePaintLabel.setText(jet.getExterior().getBasePaint());

        clientInterfaceController.setUserNavigationMessage(clientInterfaceController.getUserNavigationMessage()+" / "+jet.getJetName());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws NullPointerException {

        backDetailsBtn.setOnAction(e->{
            clientInterfaceController.anchorDisplayController(true,false,false,false,false);
            clientInterfaceController.setUserNavigationMessage("Inventory");
            clientInterfaceController.resetFilters();
        });

    }
}
