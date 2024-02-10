package com.example.programming2_c1.AdminControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetClasses.*;
import com.example.programming2_c1.UserClasses.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class JetContentDataOperation extends DataOperationController{

    public DataOperationController dataOperationController;

    int numJet;

    String previousSearch = "";
    String previousManufacturer = null;
    String previousModel = null;

    ObservableList<String> years = FXCollections.observableArrayList();


    protected void setupYearsCombo(){
        int startYear = 1990;
        while (startYear <= 2023){
            years.add(String.valueOf(startYear));
            startYear++;

        }
        dataOperationController.yearCombo.setItems(years);
        dataOperationController.yearCombo.setVisibleRowCount(6);
    }

    public void setupModelCombo(String manufacturerName) throws InterruptedException {

        try {
            dataOperationController.modelJetCombo.getSelectionModel().clearSelection(); // put all this in a method
            CommonFunctions.initializeReset(dataOperationController.modelJetCombo);

            String splitName[] = manufacturerName.split(" ");
            List<String> models = JetData.modelList(splitName[2],CommonFunctions.admin);

            if (models.size()>0){

                dataOperationController.modelJetCombo.setItems(FXCollections.observableArrayList(models));

                dataOperationController.modelJetCombo.setVisibleRowCount(5);
                dataOperationController.modelJetCombo.setDisable(false);

            } else {
                dataOperationController.modelJetCombo.setDisable(true);
            }

        }catch ( NullPointerException e){

        }
    }

    public void displayPrivateJetTable() throws InterruptedException {
        dataOperationController.modelIDColJet.setCellValueFactory(new PropertyValueFactory<PrivateJet,Integer>("modelID"));
        dataOperationController.jetIDCol.setCellValueFactory(new PropertyValueFactory<PrivateJet,String>("jetID"));
        dataOperationController.yearCol.setCellValueFactory(new PropertyValueFactory<PrivateJet,String>("year"));
        dataOperationController.yearCol.setCellValueFactory(new PropertyValueFactory<PrivateJet,String>("year"));
        dataOperationController.highlight1Col.setCellValueFactory(new PropertyValueFactory<PrivateJet,String>("highlight1"));
        dataOperationController.highlight2Col.setCellValueFactory(new PropertyValueFactory<PrivateJet,String>("highlight2"));
        dataOperationController.imageCol.setCellValueFactory(new PropertyValueFactory<PrivateJet,String>("imgSrc"));
        dataOperationController.priceCol.setCellValueFactory(new PropertyValueFactory<PrivateJet,Long>("price"));
        dataOperationController.quantityCol.setCellValueFactory(new PropertyValueFactory<PrivateJet,Integer>("quantity"));

        //since there's many column , a for loop has been used
        for (TableColumn<PrivateJet, ?> column : dataOperationController.privateJetTable.getColumns()) {
            column.setResizable(false);
        }

        dataOperationController.privateJetTable.setItems(FXCollections.observableArrayList(JetData.getJetData()));
        setupYearsCombo();
        dataOperationController.manufacturerJetCombo.setItems(FXCollections.observableArrayList(JetData.manufacturerList(CommonFunctions.admin))); //displays all manufacturers
        dataOperationController.manufacturerJetCombo.setVisibleRowCount(5);
        dataOperationController.modelIDJetCombo.setItems(FXCollections.observableArrayList(JetData.modelList("",CommonFunctions.admin))); //displays all models
        dataOperationController.modelIDJetCombo.setVisibleRowCount(5);

    }


    public void privateJetSelect() {
        PrivateJet privateJet = dataOperationController.privateJetTable.getSelectionModel().getSelectedItem();
        numJet =  dataOperationController.privateJetTable.getSelectionModel().getSelectedIndex();

        if ((numJet - 1) < -1) {
            return;
        }

        dataOperationController.jetNameLabel.setText(privateJet.getJetName());

        //private jet
        dataOperationController.modelIDJetCombo.setValue( dataOperationController.modelPosition.get(privateJet.getModelID()));
        dataOperationController.yearCombo.setValue(String.valueOf(privateJet.getYear()));
        dataOperationController.jetIDField.setText(privateJet.getJetID());
        dataOperationController.highlight2Field.setText(privateJet.getHighlight2());
        dataOperationController.highlight1Field.setText(privateJet.getHighlight1());
        dataOperationController.imageField.setText(privateJet.getImgSrc());
        dataOperationController.priceField.setText(String.valueOf(privateJet.getPrice()));
        dataOperationController.quantityField.setText(String.valueOf(privateJet.getQuantity()));

        //Airframe
        dataOperationController.totalLandingsSinceNewField.setText(String.valueOf(privateJet.getAirframe().getLandings()));
        dataOperationController.totalTimeSinceNewField.setText(String.valueOf(privateJet.getAirframe().getTimeSinceNew()));

        //Engine
        dataOperationController.TotalCyclesSinceNewField.setText(String.valueOf(privateJet.getEngine().getCycleSinceNew()));
        dataOperationController.TotalHoursSinceNewField.setText(String.valueOf(privateJet.getEngine().getHoursSinceNew()));
        dataOperationController.rightEngineIDField.setText(String.valueOf(privateJet.getEngine().getRightEngineID()));
        dataOperationController.leftEngineIDField.setText(String.valueOf(privateJet.getEngine().getLeftEngineID()));
        dataOperationController.engineDescField.setText(privateJet.getEngine().getEngineDescription());

        //Interior
        dataOperationController.forwardCabinField.setText(privateJet.getInterior().getForwardCabinConfig());
        dataOperationController.aftCabinField.setText(privateJet.getInterior().getAftCabinConfig());
        dataOperationController.numPassengersField.setText(String.valueOf(privateJet.getInterior().getNumPassengers()));


        //Exterior
        dataOperationController.stripeField.setText(privateJet.getExterior().getStripe());
        dataOperationController.basePaintField.setText(privateJet.getExterior().getBasePaint());

    }

    public boolean isPrivateAlreadyExist(){
        //to check if the jetID already exist in the database

        try {
            return JetData.getPrivateJetGivenID( dataOperationController.jetIDField.getText()) != null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPrivateJetEmpty(){

        if ( dataOperationController.yearCombo.getSelectionModel().isEmpty() |  dataOperationController.yearCombo.getSelectionModel().isEmpty() |  dataOperationController.highlight1Field.getText().isEmpty() |  dataOperationController.highlight2Field.getText().isEmpty() |  dataOperationController.imageField.getText().isEmpty() |  dataOperationController.priceField.getText().isEmpty() |  dataOperationController.quantityField.getText().isEmpty()){
            return true;
        }else {
            return false;
        }

    }

    public boolean isAirframeEmpty(){
        if ( dataOperationController.totalTimeSinceNewField.getText().isEmpty() |  dataOperationController.totalLandingsSinceNewField.getText().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public boolean isEngineEmpty(){
        if ( dataOperationController.TotalHoursSinceNewField.getText().isEmpty() |  dataOperationController.TotalCyclesSinceNewField.getText().isEmpty() |  dataOperationController.rightEngineIDField.getText().isEmpty() |  dataOperationController.leftEngineIDField.getText().isEmpty() |  dataOperationController.engineDescField.getText().isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public boolean isInteriorEmpty(){
        if ( dataOperationController.forwardCabinField.getText().isEmpty() |  dataOperationController.aftCabinField.getText().isEmpty() |  dataOperationController.numPassengersField.getText().isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public boolean isExteriorEmpty(){
        if ( dataOperationController.stripeField.getText().isEmpty() |  dataOperationController.basePaintField.getText().isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public void resetPrivateJet(boolean onlySearhPart) throws InterruptedException {

        if(!onlySearhPart){

            dataOperationController.jetNameLabel.setText("-");

            //Private Jet
            dataOperationController.modelIDJetCombo.getSelectionModel().clearSelection();
            dataOperationController.yearCombo.getSelectionModel().clearSelection();
            CommonFunctions.initializeReset( dataOperationController.modelIDJetCombo);
            CommonFunctions.initializeReset( dataOperationController.yearCombo);
            dataOperationController.jetIDField.clear();
            dataOperationController.highlight1Field.clear();
            dataOperationController.highlight2Field.clear();
            dataOperationController.imageField.clear();
            dataOperationController.priceField.clear();
            dataOperationController.quantityField.clear();

            //Air frame
            dataOperationController.totalLandingsSinceNewField.clear();
            dataOperationController.totalTimeSinceNewField.clear();

            //Engine
            dataOperationController.TotalHoursSinceNewField.clear();
            dataOperationController.TotalCyclesSinceNewField.clear();
            dataOperationController.rightEngineIDField.clear();
            dataOperationController.leftEngineIDField.clear();
            dataOperationController.engineDescField.clear();

            //Interior
            dataOperationController.forwardCabinField.clear();
            dataOperationController.aftCabinField.clear();
            dataOperationController.numPassengersField.clear();

            //Exterior
            dataOperationController.stripeField.clear();
            dataOperationController.basePaintField.clear();
        }

        //search bar
        dataOperationController.searchJetField.clear();
        dataOperationController.manufacturerJetCombo.getSelectionModel().clearSelection();
        dataOperationController.modelJetCombo.getSelectionModel().clearSelection();
        dataOperationController.modelJetCombo.setDisable(true);
        CommonFunctions.initializeReset( dataOperationController.manufacturerJetCombo);
        CommonFunctions.initializeReset( dataOperationController.modelIDJetCombo);

        dataOperationController.displayPrivateJetTable();

    }

    public void setOperationJetBtn( boolean hboxDetails, boolean addBtn , boolean updateBtn, boolean dltBtn,String colouraddUpdateBtn, String colourdeleteBtn){
        dataOperationController.privateJetHboxMain.setDisable(hboxDetails);
        dataOperationController.privateJetHboxRest.setDisable(hboxDetails);
        dataOperationController.updateJetBtn.setDisable(updateBtn);
        dataOperationController.deleteJetBtn.setDisable(dltBtn);
        dataOperationController.addJetBtn.setDisable(addBtn);
        dataOperationController.addUpdateOperationPrivateJetBtn.setStyle("-fx-text-fill:%s".formatted(colouraddUpdateBtn));
        dataOperationController.deleteOperationPrivateJetBtn.setStyle("-fx-text-fill:%s".formatted(colourdeleteBtn));
    }


    public boolean isValueChangedPrivateJet(String prefSearch,String prefManufacturer,String prefModel){ //SEARCH
        if (prefSearch.equals(previousSearch) && prefManufacturer == previousManufacturer && prefModel == previousModel ){
            return false;
        } else if (prefSearch.equals("") && prefManufacturer == null && prefModel == null ){
            return false;
        } else {
            return true;
        }
    }

    public void initiateSearch() {  //SEARCH

        String prefSearch =  dataOperationController.searchJetField.getText().toLowerCase();
        String prefManufacturer =  dataOperationController.manufacturerJetCombo.getValue();
        String prefModel =  dataOperationController.modelJetCombo.getValue();


        if (isValueChangedPrivateJet(prefSearch,prefManufacturer,prefModel)){
            //only edit the display if prev value and current value is not the same . This is to save processing power and time.
            try {
                dataOperationController.privateJetTable.setItems(FXCollections.observableArrayList(redisplayBasedOnFilter(JetData.getJetData(),prefSearch,prefManufacturer,prefModel)));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            resetPreviousSearchValue(prefSearch,prefManufacturer,prefModel);
        }
    }

    public void resetPreviousSearchValue(String prefSearch,String prefManufacturer,String prefModel){  //SEARCH
        previousSearch = prefSearch;
        previousManufacturer = prefManufacturer;
        previousModel = prefModel;
    }

    public String getJetName(String jetName){ //to get only the manufacturer name { 1-Bombardier} , to only get Bombardier
        String [] manufacturer = jetName.split(" ",2);
        return manufacturer[1];
    }

    public String getSelectedManufacturer(String manufacturerName){ //to get only the jet name { 2007 Bombardier Challenger} , to only get Bombardier Challenger
        String [] manufacturer = manufacturerName.split(" ",3);
        return manufacturer[2];
    }

    public String getSelectedModel(String modelName){ //to get only the model name { 1-Challenger} , to only get Challenger
        String [] model = modelName.split(" ",3);
        return model[2];
    }

    public ArrayList<PrivateJet> redisplayBasedOnFilter(ArrayList<PrivateJet> jets, String prefSearch, String prefManufacturer, String prefModel){  //SEARCH

        ArrayList<PrivateJet> temp = new ArrayList<>();

        try{

            Iterator entries;

            entries = jets.iterator();

            while (entries.hasNext()){
                PrivateJet jet = (PrivateJet) entries.next();

                if (prefManufacturer == null && !prefSearch.isEmpty()){ // manufacturer = empty , model = empty , search term = !empty
                    if (getJetName(jet.getJetName()).toLowerCase().contains(prefSearch)){
                        temp.add(jet);
                    }

                } else if (prefManufacturer == null){  // manufacturer = empty , model = empty , search term = empty
                    temp.add(jet);

                } else if (prefModel == null && !prefSearch.isEmpty()){   // manufacturer = !empty , model = empty , search term = !empty
                    if (jet.getJetName().toLowerCase().contains(getSelectedManufacturer(prefManufacturer).toLowerCase()) &&  getJetName(jet.getJetName()).toLowerCase().contains(prefSearch) ){
                        temp.add(jet);
                    }

                } else if (prefModel == null){ // manufacturer = !empty , model = empty , search term = empty
                    if (jet.getJetName().toLowerCase().contains(getSelectedModel(prefManufacturer).toLowerCase())){
                        temp.add(jet);
                    }

                } else { // manufacturer = !empty , model = !empty , search term = !empty
                    if (jet.getJetName().toLowerCase().contains(getSelectedManufacturer(prefManufacturer).toLowerCase()) && jet.getJetName().toLowerCase().contains(getSelectedModel(prefModel).toLowerCase())  && getJetName(jet.getJetName()).toLowerCase().contains(prefSearch)){
                        temp.add(jet);
                    }
                }
            }

        }catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        return temp;
    }


    public boolean checkValidity(){

        if (CommonFunctions.validationInteger(dataOperationController.priceField.getText(),"Invalid input for 'Price field'")&&
                CommonFunctions.validationInteger(dataOperationController.quantityField.getText(),"Invalid input for 'Quantity field'")&&
                CommonFunctions.validationInteger(dataOperationController.quantityField.getText(),"Invalid input for 'Quantity field'")&&
                CommonFunctions.validationInteger(dataOperationController.numPassengersField.getText(),"Invalid input for 'No. of Passengers field'")&&
                CommonFunctions.validationInteger(dataOperationController.totalTimeSinceNewField.getText(),"Invalid input for 'Total Time Since New field'")&&
                CommonFunctions.validationInteger(dataOperationController.TotalCyclesSinceNewField.getText(),"Invalid input for 'Total Cycles Since New field'")&&
                CommonFunctions.validationInteger(dataOperationController.TotalHoursSinceNewField.getText(),"Invalid input for 'Total Hours Since New field'")&&
                CommonFunctions.validationInteger(dataOperationController.totalLandingsSinceNewField.getText(),"Invalid input for 'Total Landings Since New field'")){

            return true;
        }
        return false;
    }


    public void addJetDetails(){
        if (isPrivateJetEmpty() || isAirframeEmpty() || isEngineEmpty() || isInteriorEmpty() || isExteriorEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill all the blank fields");
        }else {


            if (checkValidity()){

                if (!(isPrivateAlreadyExist())){
                    String[] splitModel = dataOperationController.modelIDJetCombo.getValue().split(" ");

                    //adding all info related to database
                    PrivateJet.addPrivateJetToDatabase(Integer.valueOf(splitModel[0]), dataOperationController.jetIDField.getText() ,Integer.valueOf(dataOperationController.yearCombo.getValue()) , dataOperationController.highlight1Field.getText(),dataOperationController.highlight2Field.getText(),dataOperationController.imageField.getText(), Long.parseLong(dataOperationController.priceField.getText()), Integer.valueOf(dataOperationController.quantityField.getText()));
                    JetAirframe.addAirFrameToDatabase(dataOperationController.jetIDField.getText(),Integer.valueOf(dataOperationController.totalTimeSinceNewField.getText()),Integer.valueOf(dataOperationController.totalLandingsSinceNewField.getText()));
                    JetEngine.addEngineToDatabase(dataOperationController.jetIDField.getText(),Integer.valueOf(dataOperationController.TotalHoursSinceNewField.getText()),Integer.valueOf(dataOperationController.TotalCyclesSinceNewField.getText()),dataOperationController.rightEngineIDField.getText(),dataOperationController.leftEngineIDField.getText(),dataOperationController.engineDescField.getText());
                    JetInterior.addInteriorToDatabase(dataOperationController.jetIDField.getText(),dataOperationController.forwardCabinField.getText(),dataOperationController.aftCabinField.getText(),Integer.valueOf(dataOperationController.numPassengersField.getText()));
                    JetExterior.addExteriorToDatabase(dataOperationController.jetIDField.getText(),dataOperationController.stripeField.getText(),dataOperationController.basePaintField.getText());

                    try {
                        resetPrivateJet(false);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully added into the privatejet!");

                } else {
                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"SQL Error","Error : Duplicate entry for "+dataOperationController.jetIDField.getText()+"");
                }

            }

        }
    }


    public void updateJetDetails(){
        if (isPrivateJetEmpty() | isAirframeEmpty() | isEngineEmpty() | isInteriorEmpty() | isExteriorEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill all the blank fields");

        } else {

            if (checkValidity()){
                if (!isPrivateAlreadyExist()){
                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"SQL Error","Error : Jet id = "+dataOperationController.jetIDField.getText()+" does not exist in database.");

                } else {
                    String[] splitModel = dataOperationController.modelIDJetCombo.getValue().split(" ");

                    PrivateJet.updateJet(Integer.valueOf(splitModel[0]), dataOperationController.jetIDField.getText() ,Integer.valueOf(dataOperationController.yearCombo.getValue()) , dataOperationController.highlight1Field.getText(),dataOperationController.highlight2Field.getText(),dataOperationController.imageField.getText(), Long.parseLong(dataOperationController.priceField.getText()), Integer.valueOf(dataOperationController.quantityField.getText()));
                    JetAirframe.updateAirFrame(dataOperationController.jetIDField.getText(),Integer.valueOf(dataOperationController.totalTimeSinceNewField.getText()),Integer.valueOf(dataOperationController.totalLandingsSinceNewField.getText()));
                    JetEngine.updateEngine(dataOperationController.jetIDField.getText(),Integer.valueOf(dataOperationController.TotalHoursSinceNewField.getText()),Integer.valueOf(dataOperationController.TotalCyclesSinceNewField.getText()),dataOperationController.rightEngineIDField.getText(),dataOperationController.leftEngineIDField.getText(),dataOperationController.engineDescField.getText());
                    JetInterior.updateInterior(dataOperationController.jetIDField.getText(),dataOperationController.forwardCabinField.getText(),dataOperationController.aftCabinField.getText(),Integer.valueOf(dataOperationController.numPassengersField.getText()));
                    JetExterior.updateExterior(dataOperationController.jetIDField.getText(),dataOperationController.stripeField.getText(),dataOperationController.basePaintField.getText());

                    CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully update jet id = "+dataOperationController.jetIDField.getText()+" in database.");
                    try {
                        dataOperationController.privateJetTable.setItems(FXCollections.observableArrayList(JetData.getJetData()));
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }


    public void deleteJetDetails(){
        if (dataOperationController.jetIDField.getText().isEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill in the jetID fields");
        }else {
            if (!(isPrivateAlreadyExist())){
                CommonFunctions.createAlert(Alert.AlertType.ERROR,"SQL Error","Error : Jet id = "+dataOperationController.jetIDField.getText()+" does not exist in database.");

            } else {

                if (CommonFunctions.createAlertConfirmation("Confirmation","Are you sure you want to the delete record for "+dataOperationController.jetIDField.getText()+" ?")){

                    //removing a private jet will delete all the other data related to private jet to be deleted due to cascade constraint
                    PrivateJet.removeJetFromDatabase(dataOperationController.jetIDField.getText());

                    try {
                        dataOperationController.privateJetTable.setItems(FXCollections.observableArrayList(JetData.getJetData()));
                        resetPrivateJet(false);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully removed into the privatejet!");
                }

            }
        }
    }
}
