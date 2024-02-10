package com.example.programming2_c1.AdminControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.JetClasses.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;


public class ModelContentDataOperation extends DataOperationController{

    public static DataOperationController dataOperationController;

    int numModel;

    public void displayModelTable() throws InterruptedException{
        dataOperationController.manufacturerModelIDCol.setCellValueFactory(new PropertyValueFactory<Model,Integer>("manufacturerID"));
        dataOperationController.modelIDCol.setCellValueFactory(new PropertyValueFactory<Model,Integer>("modelID"));
        dataOperationController.modelNameCol.setCellValueFactory(new PropertyValueFactory<Model,String>("modelName"));

        dataOperationController.manufacturerModelIDCol.setResizable(false);
        dataOperationController.modelIDCol.setResizable(false);
        dataOperationController.modelNameCol.setResizable(false);

        dataOperationController.modelTable.setItems(FXCollections.observableArrayList(JetData.generateModelList()));
        dataOperationController.manufacturerIDModelCombo.setItems(FXCollections.observableArrayList(JetData.manufacturerList(CommonFunctions.admin)));
        dataOperationController.manufacturerIDSearchCombo.setItems(FXCollections.observableArrayList(JetData.manufacturerList(CommonFunctions.admin)));
        dataOperationController.manufacturerIDModelCombo.setVisibleRowCount(5);
        dataOperationController.manufacturerIDSearchCombo.setVisibleRowCount(5);
        dataOperationController.setupAllManufacturers();
    }


    public void modelSelect() throws InterruptedException {
        Model model = dataOperationController.modelTable.getSelectionModel().getSelectedItem();
        numModel = dataOperationController.modelTable.getSelectionModel().getSelectedIndex();

        if ((numModel - 1) < -1) {
            return;
        }

        dataOperationController.modelIDField.setText(String.valueOf(model.getModelID()));
        dataOperationController.modelNameField.setText(model.getModelName());
        dataOperationController.manufacturerIDModelCombo.setValue(dataOperationController.manufacturerPosition.get(model.getManufacturerID()));
    }



    public void setOperationModelBtn(boolean manufacturerIDModelCombo,boolean modelNameField, boolean addBtn , boolean updateBtn,boolean dltBtn,String colouraddUpdateBtn, String colourdeleteBtn){
        dataOperationController.manufacturerIDModelCombo.setDisable(manufacturerIDModelCombo);
        dataOperationController.modelNameField.setDisable(modelNameField);
        dataOperationController.deleteModelBtn.setDisable(dltBtn);
        dataOperationController.modelAddBtn.setDisable(addBtn);
        dataOperationController.modelUpdateBtn.setDisable(updateBtn);
        dataOperationController.addUpdateOperationModelBtn.setStyle("-fx-text-fill:%s".formatted(colouraddUpdateBtn));
        dataOperationController.deleteOperationModelBtn.setStyle("-fx-text-fill:%s".formatted(colourdeleteBtn));
    };

    public void resetModel(boolean onlySearchPart) throws InterruptedException {

        if (!onlySearchPart){
            dataOperationController.manufacturerIDModelCombo.getSelectionModel().clearSelection();
            CommonFunctions.initializeReset(dataOperationController.manufacturerIDModelCombo);
            dataOperationController.modelIDField.clear();
            dataOperationController.modelNameField.clear();
        }
        dataOperationController.searchModel.clear();
        dataOperationController.manufacturerIDSearchCombo.getSelectionModel().clearSelection();
        CommonFunctions.initializeReset(dataOperationController.manufacturerIDSearchCombo);
        displayModelTable();
    }

    public boolean isModelAlreadyExist(){
        //to check if the manufacturerID already exist in the database
        try {
            return JetData.getModelGivenID(Integer.valueOf(dataOperationController.modelIDField.getText())) != null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void initiateSeachModel(){

        String prefSearch = dataOperationController.searchModel.getText().trim().toLowerCase();
        String prefManufacturer = dataOperationController.manufacturerIDSearchCombo.getValue();

        try {
            ObservableList<Model> modelsAll = FXCollections.observableArrayList();

            for (Model temp:JetData.generateModelList()) {

                //manufacturer = empty , search = !empty
                if(prefManufacturer == null){
                    //search by model name
                    if (temp.getModelName().toLowerCase().contains(prefSearch)){
                        modelsAll.add(temp);
                    }

                    //manufacturer = !empty , search = empty
                } else if (prefSearch.isEmpty()){

                    if (JetData.getManufacturerGivenID(temp.getManufacturerID()).getManufacturerName().contains(dataOperationController.getSelectedManufacturer(prefManufacturer))){
                        modelsAll.add(temp);
                    }

                    //manufacturer = !empty , search = !empty
                } else {
                    if (JetData.getManufacturerGivenID(temp.getManufacturerID()).getManufacturerName().contains(dataOperationController.getSelectedManufacturer(prefManufacturer)) && temp.getModelName().toLowerCase().contains(prefSearch)){
                        modelsAll.add(temp);
                    }
                }
            }

            dataOperationController.modelTable.setItems(FXCollections.observableArrayList(modelsAll));

        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }


    public boolean isModelFieldEmpty(){
        return dataOperationController.modelIDField.getText().isEmpty() || dataOperationController.manufacturerIDModelCombo.getSelectionModel().isEmpty() || dataOperationController.modelNameField.getText().isEmpty();
    }


    public void addModelDetails(){
        if (isModelFieldEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill all the blank fields");
        }else {

            if(CommonFunctions.validationInteger(dataOperationController.modelIDField.getText(),"Invalid input for 'Model ID field'")){
                if (!(isModelAlreadyExist())){
                    String[] splitManufacturer = dataOperationController.manufacturerIDModelCombo.getValue().split(" ");

                    //adding all info related to database
                    Model.addModelToDatabase(Integer.parseInt(splitManufacturer[0]),Integer.parseInt(dataOperationController.modelIDField.getText()),dataOperationController.modelNameField.getText());

                    try {
                        resetModel(false);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully added into the model!");

                } else {
                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"SQL Error","Error : Duplicate entry for "+dataOperationController.modelIDField.getText()+"");
                }
            }
        }
    }


    public void updateModelDetails(){
        if (isModelFieldEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill all the blank fields");

        } else {

            if(CommonFunctions.validationInteger(dataOperationController.modelIDField.getText(),"Invalid input for 'Model ID field'")){
                if (!(isModelAlreadyExist())){
                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"SQL Error","Error : Model id = "+dataOperationController.modelIDField.getText()+" does not exist in database.");

                } else {
                    String[] splitManufacturer = dataOperationController.manufacturerIDModelCombo.getValue().split(" ");
                    Model.updateModel(Integer.parseInt(splitManufacturer[0]),Integer.parseInt(dataOperationController.modelIDField.getText()),dataOperationController.modelNameField.getText());
                    CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully update model id = "+dataOperationController.modelIDField.getText()+" in database.");

                    try {
                        dataOperationController.modelTable.setItems(FXCollections.observableArrayList(JetData.generateModelList()));
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }


    public void deleteModelDetails(){
        if (dataOperationController.modelIDField.getText().isEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill in the Model ID Field");

        }else {

            if (CommonFunctions.validationInteger(dataOperationController.modelIDField.getText(),"Invalid input for 'Model ID field'")){
                if (!isModelAlreadyExist()){
                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"SQL Error","Error : Model id = "+dataOperationController.modelIDField.getText()+" does not exist in database.");

                } else {

                    if (CommonFunctions.createAlertConfirmation("Confirmation","Are you sure you want to the delete record for "+dataOperationController.modelIDField.getText()+" ?")){
                        Model.removeModelFromDatabase(Integer.valueOf(dataOperationController.modelIDField.getText()));
                        try {
                            resetModel(false);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully removed into the model!");
                    }
                }
            }
        }
    }


}
