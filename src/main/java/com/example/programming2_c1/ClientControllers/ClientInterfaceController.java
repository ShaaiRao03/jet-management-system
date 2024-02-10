package com.example.programming2_c1.ClientControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.Interfaces;
import com.example.programming2_c1.JetClasses.*;

import com.example.programming2_c1.ScreenController;
import com.example.programming2_c1.UserClasses.Cart;
import com.example.programming2_c1.UserClasses.Client;
import com.example.programming2_c1.UserClasses.Transaction;
import com.example.programming2_c1.UserClasses.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.lang.ref.Cleaner;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;


public class ClientInterfaceController implements Initializable, Interfaces.ClientInterface {

    @FXML
    private Label pricePreviewLabel;

    @FXML
    private Button transactionBtn;

    @FXML
    private AnchorPane profileAnchor;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Label modelPreviewLabel;

    @FXML
    private Label highlight3PreviewLabel;

    @FXML
    private ImageView imgPreview;

    @FXML
    private Label highlight1PreviewLabel;

    @FXML
    private Label highlight2PreviewLabel;

    @FXML
    private Label manufacturerPreviewLabel;

    @FXML
    private Label highlight1Label;

    @FXML
    private Label highlight2Label;

    @FXML
    private Label highlight3Label;

    @FXML
    private Label modelLabel;

    @FXML
    private Button profileBtn;

    @FXML
    private Label manufacturerLabel;

    @FXML
    private Label jetIDLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Button cartBtn;

    @FXML
    private GridPane gridInv;

    @FXML
    private Button inventoryBtn;

    @FXML
    private Button logOutbtn;

    @FXML
    private Button findFinanceBtn;

    @FXML
    private ComboBox<String> manufacturerBox;

    @FXML
    private ComboBox<String> modelBox;

    @FXML
    private Slider passenger;

    @FXML
    private Button resetFilter;

    @FXML
    private ScrollPane scrollpaneInv;

    @FXML
    private Button searchInventory;

    @FXML
    private TextField searchTerm;

    @FXML
    private Slider totalHours;

    @FXML
    private Label userNavigation;

    @FXML
    private Button addToCartBtn;

    @FXML
    private AnchorPane detailAnchor;

    @FXML
    private AnchorPane searchAnchor;

    @FXML
    private AnchorPane previewAnchor;

    @FXML
    private AnchorPane guideAnchor;

    private ObservableList<String> availableModels = FXCollections.observableArrayList();

    private String previousSearch = "";
    private String previousManufacturer = null;
    private String previousModel = null;
    private int previousPassenger = 4;
    private int previousHours = 0;
    private NumberFormat nf = NumberFormat.getInstance();
    private Client user;



//----------------General methods--------------------------------

    public Client getUser() {
        return user;
    }

    public void initData(Client user){
        this.user = user;
    }
    public void setUserNavigationMessage(String message) {
        userNavigation.setText(message);
    }
    public String getUserNavigationMessage() {
        return userNavigation.getText();
    }

    public void removeAll(){
        //remove all the contents in the gridpane
        try{
            ArrayList<Node> temp = new ArrayList<Node>(gridInv.getChildren().stream().toList());

            Iterator<Node> iterateNode = temp.iterator();
            while (iterateNode.hasNext()){
                Node children = iterateNode.next();
                gridInv.getChildren().remove(children);
            }

        }catch (ConcurrentModificationException e){
            System.out.println("ClientInterfaceController class : Error in removeAll() ");
        }
    }

    public void anchorDisplayController(boolean searchAnchor, boolean detailAnchor, boolean guideAnchor, boolean previewAnchor, boolean profileAnchor){
        //controls all the anchor visibility ( one stop for all anchor displays )
        this.searchAnchor.setVisible(searchAnchor);
        this.detailAnchor.setVisible(detailAnchor);
        this.previewAnchor.setVisible(previewAnchor);
        this.guideAnchor.setVisible(guideAnchor);
        this.profileAnchor.setVisible(profileAnchor);
    }
//----------------------------------------------------------------------------------------------------------

//---------Display specific details about the jet (contents controlled by : JetInfoController & ClientInterfaceController)---------------
    public void setVboxDetails(PrivateJet privateJet) throws InterruptedException {

        try {

            manufacturerLabel.setText(privateJet.getYear()+" "+Objects.requireNonNull(JetData.getManufacturerGivenID(Objects.requireNonNull(JetData.getModelGivenID(privateJet.getModelID())).getManufacturerID())).getManufacturerName());
            modelLabel.setText(Objects.requireNonNull(JetData.getModelGivenID(privateJet.getModelID())).getModelName());
            jetIDLabel.setText("S/N "+privateJet.getJetID());
            highlight1Label.setText(privateJet.getHighlight1());
            highlight2Label.setText(privateJet.getHighlight2());
            highlight3Label.setText(privateJet.getAirframe().getTimeSinceNew()+" Hours ; "+ privateJet.getAirframe().getLandings()+" Landings");
            priceLabel.setText("Price : $ "+String.valueOf(nf.format(privateJet.getPrice())));

            addToCartBtn.setOnAction(e->{
                Cart.addCartToDatabase(privateJet.getJetID(),manufacturerLabel.getText()+" "+modelLabel.getText(),CommonFunctions.pending,user.getUserName());
            });

            findFinanceBtn.setOnAction(e -> {
                Stage primaryStage = new Stage();
                generateFinanceFinder(primaryStage);
            });

        }catch (NullPointerException e){
            System.out.println("setVboxDetails(PrivateJet privateJet) null pointer exception");
        }
    }


    public void generateFinanceFinder(Stage stage){
        // Create hyperlinks to bank websites
        Hyperlink bank1Link = new Hyperlink("Maybank");
        bank1Link.setOnAction(e -> {
            openWebpage("https://www.maybank2u.com.my/home/m2u/common/login.do");
        });
        bank1Link.setStyle("-fx-text-fill: #ffff");
        bank1Link.setFocusTraversable(false);

        Hyperlink bank2Link = new Hyperlink("Hong Leong Bank");
        bank2Link.setOnAction(e-> {
            openWebpage("https://www.hlb.com.my/en/personal-banking/home.html");
        });
        bank2Link.setStyle("-fx-text-fill: #ffff");
        bank2Link.setFocusTraversable(false);

        Hyperlink bank3Link = new Hyperlink("Alliance Bank");
        bank3Link.setOnAction(e-> {
            openWebpage("https://www.alliancebank.com.my/");
        });
        bank3Link.setStyle("-fx-text-fill: #ffff");
        bank3Link.setFocusTraversable(false);

        Label bankLabel = new Label("Proudly , presenting our finance partners : ");
        bankLabel.setStyle("-fx-font-size: 15 ; -fx-text-fill: #ffdc47");

        // Create a VBox layout to hold the hyperlinks
        VBox root = new VBox();
        root.getChildren().addAll(bankLabel,bank1Link, bank2Link, bank3Link);
        root.setAlignment(Pos.CENTER);
        root.setStyle(" -fx-background-color:  #25282A");

        // Create a Scene with the VBox layout
        Scene scene = new Scene(root, 300, 250);
        VBox.setMargin(root, new Insets(20));

        // Set the title of the stage
        stage.setTitle("Bank Hyperlinks");

        // Set the Scene of the stage
        stage.setScene(scene);

        //to make sure only this scene is active until it is closed
        stage.initModality(Modality.APPLICATION_MODAL);

        // Show the stage
        stage.show();
    }


    public void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void displayDetails(PrivateJet privateJet) throws InterruptedException {

        anchorDisplayController(false,true,false,false, false);

        setVboxDetails(privateJet);

        removeAll(); //to remove all the previous element

        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(getClass().getResource("/com/example/programming2_c1/jetInfo.fxml"));
        try {
            AnchorPane anchorDetails = fxmlLoader2.load(); //loading the anchor pane from the fxml
            JetInfoController jetInfoController = fxmlLoader2.getController();
            jetInfoController.setData(privateJet); //setting the content for the pane
            gridInv.add(anchorDetails, 0, 1); //(child,column,row)
            scrollpaneInv.setVvalue(0.0);

            gridInv.setHgap(12);
            GridPane.setMargin(anchorDetails, new Insets(20));

        } catch (IOException e) {
            System.out.println("Loader in displayDetails() failed to load");
            throw new RuntimeException(e);
        }
    }
//---------------------------------------------------------------------------

//------------------------Contents in the Inventory Page (contents controlled by : InventoryDisplayController & ClientInterfaceController)----------------------

    public void displayInventory(ArrayList<PrivateJet> privateJets){

        if (privateJets.size()!=0){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    int column = 0;
                    int row = 1;

                    try {
                        Iterator<PrivateJet> jetArray = privateJets.iterator();

                        while (jetArray.hasNext()){

                            PrivateJet jet = jetArray.next();

                            if (jet.getQuantity() <= 0){
                                continue;
                            }

                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/inventoryDisplay.fxml"));
                            AnchorPane anchorDisplay = fxmlLoader.load();

                            InventoryDisplayController inventoryDisplayController = fxmlLoader.getController();
                            inventoryDisplayController.setData(jet);

                            if (column == 2) {
                                column = 0;
                                row++;
                            }

                            gridInv.add(anchorDisplay, column++, row); //(child,column,row)
                            scrollpaneInv.setVvalue(0.0);

                            //set grid width
                            gridInv.setMinWidth(Region.USE_COMPUTED_SIZE);
                            gridInv.setPrefWidth(Region.USE_COMPUTED_SIZE);
                            gridInv.setMaxWidth(Region.USE_PREF_SIZE);

                            //set grid height
                            gridInv.setMinHeight(Region.USE_COMPUTED_SIZE);
                            gridInv.setPrefHeight(Region.USE_COMPUTED_SIZE);
                            gridInv.setMaxHeight(Region.USE_PREF_SIZE);

                            gridInv.setHgap(12);
                            GridPane.setMargin(anchorDisplay, new Insets(20));
                        }

                    } catch (IOException | NullPointerException | ConcurrentModificationException e) {
                        System.out.println("ClientInterfaceController class : Error in displayInventory() ");
                    }
                }
            });

        } else {

            Label notFound = new Label("Item Not Found");
            notFound.setFont(Font.font("Times New Roman"));
            notFound.setAlignment(Pos.BOTTOM_CENTER);
            notFound.setStyle("-fx-background-color: white ; -fx-font-size: 30");
            notFound.setPrefHeight(600);
            notFound.setPrefWidth(800);
            notFound.setMinHeight(500);
            notFound.setMaxHeight(700);
            gridInv.add(notFound,0,0);
        }
    }


    public void setupModelComboBox(String manufacturerName) {

        try {
            CommonFunctions.initializeReset(modelBox);
            availableModels.clear();

            List<String> models = JetData.modelList(manufacturerName,CommonFunctions.client);

            if (models.size()>0){
                modelBox.setDisable(false);
                modelBox.setItems(FXCollections.observableArrayList(models));
                modelBox.setVisibleRowCount(5);
            }else{
                modelBox.setDisable(true);
            }
        }catch (InterruptedException | NullPointerException e){
            System.out.println("ClientInterfaceController class : Error in setupModelComboBox() ");
        }
    }

    public void resetFilters(){

        Platform.runLater(()->{
            searchTerm.clear();
            searchTerm.setPromptText("Search by ID or Name");
            totalHours.setValue(0);
            passenger.setValue(4);
            manufacturerBox.getSelectionModel().clearSelection();
            modelBox.getSelectionModel().clearSelection();
            CommonFunctions.initializeReset(manufacturerBox);
            CommonFunctions.initializeReset(modelBox);
            modelBox.setDisable(true); //only available if the manufacturerBox is selected
            removeAll();

            try {
                displayInventory(JetData.getJetData());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            resetPreviousSearchValue("",null,null,4,0);
        });
    }


    public void initiateSearch() throws InterruptedException {

        String prefSearch = searchTerm.getText().toLowerCase();
        String prefManufacturer = manufacturerBox.getValue();
        String prefModel = modelBox.getValue();
        int prefPassenger = (int) passenger.getValue();
        int prefHours = (int) totalHours.getValue();

        if (isValueChanged(prefSearch,prefManufacturer,prefModel,prefPassenger,prefHours)){
            //only edit the display if prev value and current value is not the same . This is to save processing power and time.
            removeAll();
            displayInventory(redisplayBasedOnFilter(JetData.getJetData(),prefSearch,prefManufacturer,prefModel,prefPassenger,prefHours));
            resetPreviousSearchValue(prefSearch,prefManufacturer,prefModel,prefPassenger,prefHours);
        }
    }

    public void resetPreviousSearchValue(String prefSearch,String prefManufacturer,String prefModel,int prefPassenger,int prefHours){
        previousSearch = prefSearch;
        previousManufacturer = prefManufacturer;
        previousModel = prefModel;
        previousPassenger = prefPassenger;
        previousHours = prefHours;
    }

    public ArrayList<PrivateJet> redisplayBasedOnFilter(ArrayList<PrivateJet> arr,String prefSearch,String prefManufacturer,String prefModel,int prefPassenger,int prefHours){
        ArrayList<PrivateJet> newJets = new ArrayList<>();
        try{
            Iterator entries;
            entries = arr.iterator();

            while (entries.hasNext()){
                PrivateJet jet = (PrivateJet) entries.next();
                JetInterior jetInterior = jet.getInterior();
                JetEngine jetEngine = jet.getEngine();

                if (jet.getQuantity()<=0){
                    continue;
                }

                //first priority is given to  number of passengers and hours since new
                //second priority is given to manufacturer and model
                //third priority is given to search term ( search terms can be used to search by using jet name , year or ID only[without S/N] )

                if (prefManufacturer == null && !prefSearch.isEmpty()){ // manufacturer = empty , model = empty , search term = !empty
                    if ((jet.getJetName().toLowerCase().contains(prefSearch) || jet.getJetID().toLowerCase().contains(prefSearch)) && (jetEngine.getHoursSinceNew() >= (prefHours*1000)) && (jetInterior.getNumPassengers()>=prefPassenger)){
                        newJets.add(jet);
                    }


                } else if (prefManufacturer == null){  // manufacturer = empty , model = empty , search term = empty
                    if ((jetEngine.getHoursSinceNew() >= (prefHours*1000)) && (jetInterior.getNumPassengers()>=prefPassenger)){
                        newJets.add(jet);
                    }

                } else if (prefModel == null && !prefSearch.isEmpty()){   // manufacturer = !empty , model = empty , search term = !empty
                    if (jet.getJetName().toLowerCase().contains(prefManufacturer.toLowerCase()) && (jetEngine.getHoursSinceNew() >= (prefHours*1000)) && (jetInterior.getNumPassengers()>=prefPassenger) && (jet.getJetName().toLowerCase().contains(prefSearch) || jet.getJetID().toLowerCase().contains(prefSearch)) ){
                        newJets.add(jet);
                    }

                } else if (prefModel == null){ // manufacturer = !empty , model = empty , search term = empty
                    if (jet.getJetName().toLowerCase().contains(prefManufacturer.toLowerCase()) && (jetEngine.getHoursSinceNew() >= (prefHours*1000)) && (jetInterior.getNumPassengers()>=prefPassenger) ){
                        newJets.add(jet);
                    }

                } else { // manufacturer = !empty , model = !empty , search term = !empty
                    if (jet.getJetName().toLowerCase().contains(prefManufacturer.toLowerCase()) && jet.getJetName().toLowerCase().contains(prefModel.toLowerCase()) &&(jetEngine.getHoursSinceNew() >= (prefHours*1000)) && (jetInterior.getNumPassengers()>=prefPassenger) && (jet.getJetName().toLowerCase().contains(prefSearch) || jet.getJetID().toLowerCase().contains(prefSearch))){
                            newJets.add(jet);
                    }
                }
            }

        }catch (ConcurrentModificationException e) {
            System.out.println("ClientInterfaceController class : Error in redisplayBasedOnFilter() ");
        }
        return newJets;
    }

    public boolean isValueChanged(String prefSearch,String prefManufacturer,String prefModel,int prefPassenger,int prefHours){

        if (prefSearch.equals(previousSearch) && prefManufacturer == previousManufacturer && prefModel == previousModel && prefPassenger == previousPassenger && prefHours == previousHours){
            return false;
        } else if (prefSearch.equals("") && prefManufacturer == null && prefModel == null && prefPassenger == 4 && prefHours == 0){
            return false;
        } else {
            return true;
        }
    }
//-------------------------------------------------------------------------

//-----------------To go to the login page---------------------------------
    public void redirectLoginPage(){
        ScreenController screenControllerInventory = new ScreenController();

        try {
            logOutbtn.getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            screenControllerInventory.start(primaryStage);
            screenControllerInventory.changeSceneSpecific(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/programming2_c1/login.fxml"))),1132,706));

        } catch (IOException ex) {
            System.out.println("ClientInterfaceController class : Error in redirectLoginPage() ");
            throw new RuntimeException(ex);
        }
    }

//-------------------------------------------------------------------------------------

//------------------Display Cart (contents controlled by : CartController)--------------

    public void displayCartTable() {

        removeAll();
        anchorDisplayController(false,false,true,false, false);
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/cart.fxml"));
            AnchorPane anchorCartDisplay = fxmlLoader.load();

            CartController cartController = fxmlLoader.getController();
            cartController.displayTable();

            gridInv.add(anchorCartDisplay,0,1);

        } catch (IOException e) {
            System.out.println("ClientInterfaceController class : Error in displayCartTable() ");
            throw new RuntimeException(e);
        }
    }

//-------------------------------------------------------------------------------------------------

//-------------Application form (contents controlled by ApplicationFormController & ClientInterfaceController)-----------------

    public void setVboxPreview(PrivateJet jet) throws InterruptedException {

        try {
            Image image;

            try{
                image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/jets/"+jet.getImgSrc())));
            }catch (NullPointerException e){
                image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/jets/errorPIC.png")));
            }

            imgPreview.setImage(image);

            //effect on the image
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage imageRound = imgPreview.snapshot(parameters,null);
            imgPreview.setEffect(new DropShadow(30,Color.rgb(255,255, 255)));
            imgPreview.setImage(image);


            manufacturerPreviewLabel.setText(jet.getYear()+" "+Objects.requireNonNull(JetData.getManufacturerGivenID(Objects.requireNonNull(JetData.getModelGivenID(jet.getModelID())).getManufacturerID())).getManufacturerName());
            modelPreviewLabel.setText(Objects.requireNonNull(JetData.getModelGivenID(jet.getModelID())).getModelName());
            highlight1PreviewLabel.setText(jet.getHighlight1());
            highlight2PreviewLabel.setText(jet.getHighlight2());
            highlight3PreviewLabel.setText(jet.getAirframe().getTimeSinceNew()+" Hours ; "+ jet.getAirframe().getLandings()+" Landings");
            pricePreviewLabel.setText("Price : $ "+String.valueOf(nf.format(jet.getPrice())+" per unit"));
        }catch (NullPointerException e){
            System.out.println("setVboxPreview(PrivateJet jet) null pointer exception");
        }
    }

    public void displayApplicationForm(String jetID,int cartID){

        anchorDisplayController(false,false,false,true, false);

        try {
            setVboxPreview(Objects.requireNonNull(JetData.getPrivateJetGivenID(jetID)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        removeAll();

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/applicationForm.fxml"));
            AnchorPane anchorForm = fxmlLoader.load();

            ApplicationFormController applicationFormController = fxmlLoader.getController();
            applicationFormController.setData(Objects.requireNonNull(JetData.getPrivateJetGivenID(jetID)),cartID);

            scrollpaneInv.setVvalue(0.0);
            gridInv.setHgap(12);
            GridPane.setMargin(anchorForm, new Insets(20));

            gridInv.add(anchorForm,0,1);

        } catch (IOException | InterruptedException e) {
            System.out.println("ClientInterfaceController class : Error in displayApplicationForm() ");
            throw new RuntimeException(e);
        }
    }

    public void displayPreviewTransaction(Transaction transaction){
        removeAll();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/applicationForm.fxml"));
            AnchorPane anchorForm = fxmlLoader.load();

            ApplicationFormController applicationFormController = fxmlLoader.getController();
            applicationFormController.displayInPreviewMode(transaction,CommonFunctions.client);

            scrollpaneInv.setVvalue(0.0);
            gridInv.setHgap(12);
            GridPane.setMargin(anchorForm, new Insets(20));

            gridInv.add(anchorForm,0,1);

        } catch (IOException | InterruptedException e) {
            System.out.println("ClientInterfaceController class : Error in displayPreviewTransaction() ");
            throw new RuntimeException(e);
        }
    }

//----------------------------------------------------------------------------


//-----------------------Profile manager---------------------------------------


    public void displayTransactionTable(){

        anchorDisplayController(false,false,false,false, true);
        removeAll();

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/transaction.fxml"));
            AnchorPane anchorTransaction = fxmlLoader.load();

            scrollpaneInv.setVvalue(0.0);
            gridInv.add(anchorTransaction,0,1);

        } catch (IOException e) {
            System.out.println("ClientInterfaceController class : Error in displayTransactionTable() ");
            throw new RuntimeException(e);
        }
    }

    public void displayDashboard(){

        anchorDisplayController(false,false,false,false, true);
        removeAll();

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/programming2_c1/clientDashboard.fxml"));
            AnchorPane anchorDashboard = fxmlLoader.load();

            scrollpaneInv.setVvalue(0.0);
            gridInv.add(anchorDashboard,0,1);

        } catch (IOException e) {
            System.out.println("ClientInterfaceController class : Error in displayDashboard() ");
            throw new RuntimeException(e);
        }

    }

//----------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        anchorDisplayController(true,false,false,false, false);

        //set up dependent controller classes
        JetInfoController.clientInterfaceController = ClientInterfaceController.this;
        InventoryDisplayController.clientInterfaceController = ClientInterfaceController.this;
        ApplicationFormController.clientInterfaceController = ClientInterfaceController.this;
        TransactionController.clientInterfaceController = ClientInterfaceController.this;
        ClientDashboardController.clientInterfaceController = ClientInterfaceController.this;
        CartController.clientInterfaceController = ClientInterfaceController.this;

        //to make sure that only the value in the tick label is selected
        passenger.setSnapToTicks(true);
        totalHours.setSnapToTicks(true);

            //to set up the combobox choices and add into the respective combo boxes
        try {
            manufacturerBox.setItems(FXCollections.observableArrayList(JetData.manufacturerList(CommonFunctions.client)));
        } catch (InterruptedException e) {
            System.out.println("ClientInterfaceController class : Error in Initialize  -> manufacturerBox.setItems() ");
            throw new RuntimeException(e);
        }

        manufacturerBox.setVisibleRowCount(5);
        modelBox.setDisable(true); //only enable it once manufacturer has been chosen

        //intially , display the full inventory
        try {
            displayInventory(JetData.getJetData());
        } catch (InterruptedException e) {
            System.out.println("ClientInterfaceController class : Error in Initialize  -> displayInventory ");
            throw new RuntimeException(e);
        }


        //button/slider/combobox actions----------------------------
        resetFilter.setOnAction(e -> {
            resetFilters();
            try {
                displayInventory(JetData.getJetData());
            } catch (InterruptedException ex) {
                System.out.println("ClientInterfaceController class : Error in Initialize  -> resetFilter.setOnAction() ");
                throw new RuntimeException(ex);
            }
        });

        logOutbtn.setOnAction(e->{
            redirectLoginPage();
        });

        cartBtn.setOnAction(e -> {
            setUserNavigationMessage("Cart");
            displayCartTable();
        });

        inventoryBtn.setOnAction(e -> {
            setUserNavigationMessage("Inventory");
            resetFilters();
            anchorDisplayController(true,false,false,false, false);

            try {
                displayInventory(JetData.getJetData());
            } catch (InterruptedException ex) {
                System.out.println("ClientInterfaceController class : Error in Initialize -> inventoryBtn.setOnAction() ");
                throw new RuntimeException(ex);
            }
        });


        searchInventory.setOnAction(e -> {
            try {
                initiateSearch();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        manufacturerBox.setOnAction(e -> {
            setupModelComboBox(manufacturerBox.getValue());
        });

        dashboardBtn.setOnAction(e -> {
            setUserNavigationMessage("Dashboard / Profile");
            displayDashboard();
            anchorDisplayController(false,false,false,false, true);
        });

        profileBtn.setOnAction(e -> {
            setUserNavigationMessage("Dashboard / Profile");
            displayDashboard();
            anchorDisplayController(false,false,false,false, true);
        });

        transactionBtn.setOnAction(e->{
            setUserNavigationMessage("Dashboard / Transaction");
            displayTransactionTable();
        });

        //---------------------------------------------------------

    }
}