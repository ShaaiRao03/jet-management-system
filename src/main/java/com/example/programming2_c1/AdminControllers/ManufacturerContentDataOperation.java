package com.example.programming2_c1.AdminControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.JetClasses.Manufacturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManufacturerContentDataOperation extends  DataOperationController{

    public DataOperationController dataOperationController;

    int numManufacturer;

    public void displayManufacturerTable() throws InterruptedException {

        dataOperationController.manufacturerIDCol.setCellValueFactory(new PropertyValueFactory<Manufacturer,Integer>("manufacturerID"));
        dataOperationController.manufacturerNameCol.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("manufacturerName"));
        dataOperationController.manufacturerTable.setItems(FXCollections.observableArrayList(JetData.generateManufacturerList()));

        dataOperationController.manufacturerIDCol.setResizable(false);
        dataOperationController.manufacturerNameCol.setResizable(false);

    }

    public boolean isManufacturerEmptyFields(){
        if (dataOperationController.manufacturerIDfield.getText().isEmpty() || dataOperationController.manufacturerNameField.getText().isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public boolean isManufacturerAlreadyExist(){
        //to check if the manufacturerID already exist in the database
        try {
            return JetData.getManufacturerGivenID(Integer.valueOf(dataOperationController.manufacturerIDfield.getText())) != null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOperationManufacturerBtn( boolean manufacturerNameField,boolean addBtn , boolean updateBtn, boolean dltBtn,String colouraddUpdateBtn, String colourdeleteBtn){
        dataOperationController.manufacturerNameField.setDisable(manufacturerNameField);
        dataOperationController.manufacturerUpdateBtn.setDisable(updateBtn);
        dataOperationController.manufacturerDltBtn.setDisable(dltBtn);
        dataOperationController.manufacturerAddBtn.setDisable(addBtn);
        dataOperationController.addUpdateOperationManufacturerBtn.setStyle("-fx-text-fill:%s".formatted(colouraddUpdateBtn));
        dataOperationController.deleteOperationManufacturerBtn.setStyle("-fx-text-fill:%s".formatted(colourdeleteBtn));
    }

    public void manufacturerSelect() {
        Manufacturer manufacturer = dataOperationController.manufacturerTable.getSelectionModel().getSelectedItem();
        numManufacturer = dataOperationController.manufacturerTable.getSelectionModel().getSelectedIndex();

        if ((numManufacturer - 1) < -1) {
            return;
        }

        dataOperationController.manufacturerIDfield.setText(String.valueOf(manufacturer.getManufacturerID()));
        dataOperationController.manufacturerNameField.setText(manufacturer.getManufacturerName());
    }

    public void resetManufacturer() throws InterruptedException {
        dataOperationController.manufacturerIDfield.clear();
        dataOperationController.manufacturerNameField.clear();
        dataOperationController.searchManufacter.clear();
        dataOperationController.displayManufacturerTable();
    }


    public void manufacturerSearch(){
        if (dataOperationController.searchManufacter.getText().isEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill the blank fields");
        }else {
            try {
                ObservableList<Manufacturer> manufacturersAll = FXCollections.observableArrayList();

                for (Manufacturer temp:JetData.generateManufacturerList()) {
                    //search by manufacturer name
                    if (temp.getManufacturerName().contains(dataOperationController.searchManufacter.getText())){
                        manufacturersAll.add(temp);
                    }
                }

                dataOperationController.manufacturerTable.setItems(FXCollections.observableArrayList(manufacturersAll));

            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }

    }


    public void addManufacturerDetails(){
        if (isManufacturerEmptyFields()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill all the blank fields");
        }else {

            if (CommonFunctions.validationInteger(dataOperationController.manufacturerIDfield.getText(),"Invalid input for 'Manufacturer ID field'")){

                if (!(isManufacturerAlreadyExist())){
                    Manufacturer.addManufacturerToDatabase(Integer.valueOf(dataOperationController.manufacturerIDfield.getText()),dataOperationController.manufacturerNameField.getText());
                    CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully added into the manufacturer!");

                    try {
                        resetManufacturer();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"SQL Error","Error : Duplicate entry for "+dataOperationController.manufacturerIDfield.getText()+"");
                }
            }
        }
    }


    public void updateManufacturerDetails(){
        if (isManufacturerEmptyFields()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill all the blank fields");

        } else {

            if (CommonFunctions.validationInteger(dataOperationController.manufacturerIDfield.getText(),"Invalid input for 'Manufacturer ID field'")){
                if (!(isManufacturerAlreadyExist())){
                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"SQL Error","Error : Manufacturer id = "+dataOperationController.manufacturerIDfield.getText()+" does not exist in database.");

                } else {
                    Manufacturer.updateManufacturer(Integer.valueOf(dataOperationController.manufacturerIDfield.getText()),dataOperationController.manufacturerNameField.getText());
                    CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully update manufacturer id = "+dataOperationController.manufacturerIDfield.getText()+" in database.");
                    try {
                        resetManufacturer();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        }
    }


    public void deleteManufacturerDetails(){
        if (dataOperationController.manufacturerIDfield.getText().isEmpty()){
            CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error","Please fill in the Manufacturer ID field");
        }else {

            if (CommonFunctions.validationInteger(dataOperationController.manufacturerIDfield.getText(),"Invalid input for 'Manufacturer ID field'")){
                if (!(isManufacturerAlreadyExist())){
                    CommonFunctions.createAlert(Alert.AlertType.ERROR,"SQL Error","Error : Manufacturer id = "+dataOperationController.manufacturerIDfield.getText()+" does not exist in database.");

                } else {

                    if (CommonFunctions.createAlertConfirmation("Confirmation","Are you sure you want to the delete record for "+dataOperationController.manufacturerIDfield.getText()+" ?")){
                        Manufacturer.removeManufacturerFromDatabase(Integer.valueOf(dataOperationController.manufacturerIDfield.getText()));
                        try {
                            resetManufacturer();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Successfully removed into the manufacturer!");
                    }

                }
            }

        }
    }




}
