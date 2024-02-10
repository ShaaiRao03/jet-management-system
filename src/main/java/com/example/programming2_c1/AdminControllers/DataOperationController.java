package com.example.programming2_c1.AdminControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetClasses.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataOperationController implements Initializable {

    @FXML
    protected TextField TotalCyclesSinceNewField;

    @FXML
    protected TextField TotalHoursSinceNewField;

    @FXML
    protected Button addJetBtn;

    @FXML
    protected TextField aftCabinField;

    @FXML
    protected TextField basePaintField;

    @FXML
    protected Button clearModelBtn;

    @FXML
    protected Button deleteJetBtn;

    @FXML
    protected Button deleteModelBtn;

    @FXML
    protected TextField engineDescField;

    @FXML
    protected TextField forwardCabinField;

    @FXML
    protected TableColumn<PrivateJet,String> highlight1Col;

    @FXML
    protected TextField highlight1Field;

    @FXML
    protected TableColumn<PrivateJet, String> highlight2Col;

    @FXML
    protected TextField highlight2Field;

    @FXML
    protected TableColumn<PrivateJet, String> imageCol;

    @FXML
    protected TextField imageField;

    @FXML
    protected AnchorPane jetAnchor;

    @FXML
    protected TableColumn<PrivateJet, String> jetIDCol;

    @FXML
    protected TextField jetIDField;

    @FXML
    protected TextField leftEngineIDField;

    @FXML
    protected Button manufacturerAddBtn;

    @FXML
    protected Button manufacturerBtn;

    @FXML
    protected Button manufacturerDltBtn;

    @FXML
    protected TableColumn<Manufacturer, Integer> manufacturerIDCol;

    @FXML
    protected ComboBox<String> manufacturerIDModelCombo;

    @FXML
    protected ComboBox<String> manufacturerIDSearchCombo;

    @FXML
    protected TextField manufacturerIDfield;

    @FXML
    protected ComboBox<String> manufacturerJetCombo;

    @FXML
    protected TableColumn<Model,Integer> manufacturerModelIDCol;

    @FXML
    protected TableColumn<Manufacturer, String> manufacturerNameCol;

    @FXML
    protected TextField manufacturerNameField;

    @FXML
    protected TableView<Manufacturer> manufacturerTable;

    @FXML
    protected AnchorPane manufacturerTableAnchor;

    @FXML
    protected Button manufacturerUpdateBtn;

    @FXML
    protected Button modelAddBtn;

    @FXML
    protected Button modelBtn;

    @FXML
    protected TableColumn<Model, Integer> modelIDCol;

    @FXML
    protected TextField modelIDField;

    @FXML
    protected ComboBox<String> modelIDJetCombo;

    @FXML
    protected ComboBox<String> modelJetCombo;

    @FXML
    protected TableColumn<Model, String> modelNameCol;

    @FXML
    protected TableColumn<PrivateJet, Integer> modelIDColJet;

    @FXML
    protected TextField modelNameField;

    @FXML
    protected TableView<Model> modelTable;

    @FXML
    protected AnchorPane modelTableAnchor;

    @FXML
    protected Button modelUpdateBtn;

    @FXML
    protected TextField numPassengersField;

    @FXML
    protected TableColumn<PrivateJet, Long> priceCol;

    @FXML
    protected TextField priceField;

    @FXML
    protected Button privateJetBtn;

    @FXML
    protected TableView<PrivateJet> privateJetTable;

    @FXML
    protected AnchorPane privateJetTableAnchor;

    @FXML
    protected TableColumn<PrivateJet, Integer> quantityCol;

    @FXML
    protected TextField quantityField;

    @FXML
    protected Button resetJetBtn;

    @FXML
    protected Button resetJetFieldBtn;

    @FXML
    protected Button resetManufacturerBtn;

    @FXML
    protected TextField rightEngineIDField;

    @FXML
    protected Button searchJetBtn;

    @FXML
    protected TextField searchJetField;

    @FXML
    protected TextField searchManufacter;

    @FXML
    protected Button searchManufacturerBtn;

    @FXML
    protected TextField searchModel;

    @FXML
    protected Button searchModelBtn;

    @FXML
    protected TextField stripeField;

    @FXML
    protected TextField totalLandingsSinceNewField;

    @FXML
    protected TextField totalTimeSinceNewField;

    @FXML
    protected Button updateJetBtn;

    @FXML
    protected Label jetNameLabel;

    @FXML
    protected TableColumn<PrivateJet,String> yearCol;

    @FXML
    protected ComboBox<String> yearCombo;

    @FXML
    protected Button addUpdateOperationManufacturerBtn;

    @FXML
    protected Button addUpdateOperationModelBtn;

    @FXML
    protected Button addUpdateOperationPrivateJetBtn;


    @FXML
    protected Button deleteOperationManufacturerBtn;

    @FXML
    protected Button deleteOperationModelBtn;

    @FXML
    protected Button deleteOperationPrivateJetBtn;

    @FXML
    protected Button updateOperationModelBtn;

    @FXML
    protected Button resetManufacturerSearchBtn;

    @FXML
    protected HBox privateJetHboxMain;

    @FXML
    protected HBox privateJetHboxRest;

    @FXML
    protected Button resetModelBtn;

    public static AdminInterfaceController adminInterfaceController;
    public static ManufacturerContentDataOperation manufacturerContent = new ManufacturerContentDataOperation();
    public static JetContentDataOperation jetContent = new JetContentDataOperation();
    public static  ModelContentDataOperation modelContent = new ModelContentDataOperation();


    ConcurrentHashMap<Integer,String> modelPosition = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer,String> manufacturerPosition = new ConcurrentHashMap<>();


    public void setDataOperationControllerPaneVisibility(boolean manufacturerTableAnchor, boolean modelTableAnchor, boolean privateJetTableAnchor){
        //to control the anchor pane visibility
        this.privateJetTableAnchor.setVisible(privateJetTableAnchor);
        this.modelTableAnchor.setVisible(modelTableAnchor);
        this.manufacturerTableAnchor.setVisible(manufacturerTableAnchor);
    }


    public void displayManufacturerTable() throws InterruptedException {
        manufacturerContent.displayManufacturerTable();
    }


    public void setupAllModels() {
        try {
            ArrayList<Model> model = JetData.generateModelList();

            for (Model a :model) {
                modelPosition.put(a.getModelID(),a.getModelID()+" - "+a.getModelName());
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void setupAllManufacturers(){
        try {
            ArrayList<Manufacturer> manufacturers = JetData.generateManufacturerList();

            for (Manufacturer a :manufacturers) {
                manufacturerPosition.put(a.getManufacturerID(),a.getManufacturerID()+" - "+a.getManufacturerName());
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void displayPrivateJetTable() throws InterruptedException {
        jetContent.displayPrivateJetTable();
    }



    //Model PART----------------------------------------

    public String getSelectedManufacturer(String manufacturerName){ //to get only the manufacturer name { 1-Bombardier} , to only get Bombardier
        String [] manufacturer = manufacturerName.split(" ",3);
        return manufacturer[2];
    }


    public void displayModelTable() throws InterruptedException{
        modelContent.displayModelTable();
    }


    public void showManufacturer(){
        adminInterfaceController.setNavigationLabel("Data Operation  /  Manufacturer");
        setDataOperationControllerPaneVisibility(true,false,false);
        manufacturerContent.setOperationManufacturerBtn(false,false,false,true,"#ac5d00","#000000");
        adminInterfaceController.setSliderProperty(0.0,0.4);
        try {
            manufacturerContent.resetManufacturer();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void showModel(){
        adminInterfaceController.setNavigationLabel("Data Operation  /  Model");
        setDataOperationControllerPaneVisibility(false,true,false);
        modelContent.setOperationModelBtn(false,false,false,false,true,"#ac5d00","#000000");
        adminInterfaceController.setSliderProperty(0.0,0.4);
        try {
            modelContent.resetModel(false);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void showPrivateJet(){
        adminInterfaceController.setNavigationLabel("Data Operation  /  Private Jet");
        setDataOperationControllerPaneVisibility(false,false,true);
        jetContent.setOperationJetBtn(false,false,false,true,"#ac5d00","#000000");
        adminInterfaceController.setSliderProperty(0.0,1.0);
        try {
            displayPrivateJetTable();
            jetContent.resetPrivateJet(false);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        setupAllModels();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        manufacturerContent.dataOperationController = DataOperationController.this;
        jetContent.dataOperationController = DataOperationController.this;
        modelContent.dataOperationController = DataOperationController.this;

        showManufacturer();

        //this prevents the scroll pane form moving when we are scrolling the table
        privateJetTable.setOnScroll(event -> {
            event.consume();
        });

        //MAIN BUTTONS-------------------------------------

        manufacturerBtn.setOnAction(e -> {
            showManufacturer();
        });

        modelBtn.setOnAction(e -> {
            showModel();
        });

        privateJetBtn.setOnAction(e -> {
            showPrivateJet();
        });

        //----------------------------------------------------------

        // MANUFACTURER BUTTONS--------------------------------------

        manufacturerTable.setOnMouseClicked(e->{
            manufacturerContent.manufacturerSelect();
        });

        manufacturerAddBtn.setOnAction(e->{
            manufacturerContent.addManufacturerDetails();
        });

        manufacturerDltBtn.setOnAction( e ->{
            manufacturerContent.deleteManufacturerDetails();
        });

        manufacturerUpdateBtn.setOnAction(e -> {
            manufacturerContent.updateManufacturerDetails();
        });

        resetManufacturerBtn.setOnAction(e -> {
            //resets All
            try {
                manufacturerContent.resetManufacturer();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        searchManufacturerBtn.setOnAction(e -> {
            manufacturerContent.manufacturerSearch();
        });

        resetManufacturerSearchBtn.setOnAction(e -> {
            //only resets the table and search field
            try {
                searchManufacter.clear();
                manufacturerTable.setItems(FXCollections.observableArrayList(JetData.generateManufacturerList()));
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        addUpdateOperationManufacturerBtn.setOnAction(e -> {
            manufacturerContent.setOperationManufacturerBtn(false,false,false,true,"#ac5d00","#000000");
        });


        deleteOperationManufacturerBtn.setOnAction(e->{
            manufacturerContent.setOperationManufacturerBtn(true,true,true,false,"#000000","#ac5d00");
        });

        //-----------------------------------------------------------------

        //MODEL BUTTONS----------------------------------------------------

        modelTable.setOnMouseClicked(e -> {
            try {
                modelContent.modelSelect();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });


        addUpdateOperationModelBtn.setOnAction(e -> {
            modelContent.setOperationModelBtn(false,false,false,false,true,"#ac5d00","#000000");
        });

        deleteOperationModelBtn.setOnAction(e -> {
            modelContent.setOperationModelBtn(true,true,true,true,false,"#000000","#ac5d00");
        });


        searchModelBtn.setOnAction(e -> {
            if (searchModel.getText().isEmpty() && manufacturerIDSearchCombo.getSelectionModel().isEmpty()){
                CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill the blank fields");
            }else {
                modelContent.initiateSeachModel();
            }
        });


        resetModelBtn.setOnAction(e -> {
            try {
                modelContent.resetModel(true);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        clearModelBtn.setOnAction(e -> {
            try {
                modelContent.resetModel(false);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });


        modelAddBtn.setOnAction(e->{
            modelContent.addModelDetails();
        });


        modelUpdateBtn.setOnAction(e -> {
            modelContent.updateModelDetails();
        });


        deleteModelBtn.setOnAction(e -> {
            modelContent.deleteModelDetails();
        });

        //-----------------------------------------------------------------

        //PRIVATE JET BUTTONS----------------------------------------------

        modelJetCombo.setDisable(true);

        addUpdateOperationPrivateJetBtn.setOnAction(e->{
            jetContent.setOperationJetBtn(false,false,false,true,"#ac5d00","#000000");
        });

        deleteOperationPrivateJetBtn.setOnAction(e->{
            jetContent.setOperationJetBtn(true,true,true,false,"#000000","#ac5d00");
        });

        //this manufacturercombo has a fixed value
        manufacturerJetCombo.setOnAction( e -> {
            try {
                //the modelcombo will displayed based on chosen manfacturercombo
                jetContent.setupModelCombo(manufacturerJetCombo.getValue());

            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        privateJetTable.setOnMouseClicked(e -> {
            jetContent.privateJetSelect();
        });

        addJetBtn.setOnAction(e->{
            jetContent.addJetDetails();
        });

        updateJetBtn.setOnAction(e -> {
            jetContent.updateJetDetails();
        });


        deleteJetBtn.setOnAction(e -> {
            jetContent.deleteJetDetails();
        });


        resetJetFieldBtn.setOnAction(e -> {
            try {
                jetContent.resetPrivateJet(false);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });


        resetJetBtn.setOnAction(e -> {
            jetContent.resetPreviousSearchValue("",null,null);
            try {
                jetContent.resetPrivateJet(true);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });


        searchJetBtn.setOnAction(e -> {
            jetContent.initiateSearch();
        });

        //-----------------------------------------------------------------
    }
}
