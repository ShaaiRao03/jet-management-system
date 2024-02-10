package com.example.programming2_c1.ClientControllers;

import com.example.programming2_c1.JetClasses.PrivateJet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class InventoryDisplayController {

    @FXML
    private Label highlight1;

    @FXML
    private Label highlight2;

    @FXML
    private Label highlight3;

    @FXML
    private Label jetName;

    @FXML
    private Label snValue;

    @FXML
    private Button viewDetailsbtn;

    @FXML
    private ImageView jetImg;

    public static ClientInterfaceController clientInterfaceController;

    public void setData(PrivateJet privateJet) throws NullPointerException {

        jetName.setText(privateJet.getJetName());
        snValue.setText("S/N "+privateJet.getJetID());
        highlight1.setText(privateJet.getHighlight1());
        highlight2.setText(privateJet.getHighlight2());
        highlight3.setText(privateJet.getAirframe().getTimeSinceNew()+" Hours;"+ privateJet.getAirframe().getLandings()+" Landings");

        Image image;

        // load the image . if image does not exist , then use the default errorPic.png image
        try{
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/jets/"+privateJet.getImgSrc())));
        }catch (NullPointerException e){
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/jets/errorPIC.png")));
        }

        //set the image
        jetImg.setImage(image);

        viewDetailsbtn.setOnAction(e->{
            try {
                clientInterfaceController.displayDetails(privateJet);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
