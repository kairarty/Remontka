package programm.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import programm.methods.DBMethods;
import programm.models.CPUTableModel;
import programm.models.MainTableModel;

import java.util.Optional;

public class CPUController {
    private CPUTableModel selectedRowItem;
    @FXML TableView<CPUTableModel> cpuTable;
    @FXML TableColumn<CPUTableModel, Integer> cpuRatingColumn;
    @FXML TableColumn<CPUTableModel, String> cpuNameColumn;
    @FXML Button upButton;
    @FXML Button downButton;
    @FXML MenuItem deleteMenuItem;

    @FXML
    private void initialize() {
        cpuRatingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
        cpuNameColumn.setCellValueFactory(cellData -> cellData.getValue().cpuProperty());

        cpuNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        cpuTable.setColumnResizePolicy((param) -> true);
        setButtonsActivity();
        startCPUTable();
    }

    private void startCPUTable() {
        fillAndRefreshTable();
        cpuTable.setItems(DBMethods.getCpuCatalogList());
    }

    private void fillAndRefreshTable() {
        DBMethods.getCpuCatalogList().clear();
        DBMethods.fillCPUCatalogList();
    }

    @FXML
    private void moveUp() {
        DBMethods dbMethods = new DBMethods();
        int selectedCPUId = cpuTable.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = cpuTable.getSelectionModel().getSelectedIndex();
        String selectedCPU = cpuTable.getSelectionModel().getSelectedItem().getCpu();
        int upperCPUId = cpuTable.getItems().get(selectedIndex - 1).getId();
        String upperCPU = cpuTable.getItems().get(selectedIndex - 1).getCpu();
        dbMethods.moveCPU(selectedCPUId, selectedCPU, upperCPUId, upperCPU);
        fillAndRefreshTable();
        cpuTable.requestFocus();    // установить фокус
        cpuTable.getSelectionModel().select(selectedIndex - 1);   // выбрать строку под индексом
    }

    @FXML
    private void moveDown() {
        DBMethods dbMethods = new DBMethods();
        int selectedCPUId = cpuTable.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = cpuTable.getSelectionModel().getSelectedIndex();
        String selectedCPU = cpuTable.getSelectionModel().getSelectedItem().getCpu();
        int underCPUId = cpuTable.getItems().get(selectedIndex + 1).getId();
        String underCPU = cpuTable.getItems().get(selectedIndex + 1).getCpu();
        dbMethods.moveCPU(selectedCPUId, selectedCPU, underCPUId, underCPU);
        fillAndRefreshTable();
        cpuTable.requestFocus();
        cpuTable.getSelectionModel().select(selectedIndex + 1);
    }

    private void setButtonsActivity() { // управляет активностью кнопок при различных вариантах выбора
        ReadOnlyIntegerProperty selectedIndex = cpuTable.getSelectionModel().selectedIndexProperty();
        upButton.disableProperty().bind(selectedIndex.lessThanOrEqualTo(0));
        downButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            int index = selectedIndex.get();
            return index < 0 || index+1 >= cpuTable.getItems().size();
        }, selectedIndex, cpuTable.getItems()));
    }

    @FXML
    private void addCPU() {
        DBMethods.addEmptyCPU();
        fillAndRefreshTable();
    }

    @FXML
    private void saveEnteredCPU(TableColumn.CellEditEvent<MainTableModel, String> event) {
        selectedRowItem = cpuTable.getSelectionModel().getSelectedItem();
        String enteredValue = event.getNewValue();
        selectedRowItem.setCpu(enteredValue);
        DBMethods.saveCPU(selectedRowItem);
    }

    @FXML
    private void deleteCPU() {
        selectedRowItem = cpuTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтвердите удаление");
        MainController.setAlertIcon(alert);
        alert.setHeaderText(null);
        alert.setContentText("Вы действительно хотите удалить процессор " + selectedRowItem.getCpu() + "?");

        ButtonType yesBtn = new ButtonType("Да");
        ButtonType NoBtn = new ButtonType("Нет");
        alert.getButtonTypes().setAll(yesBtn, NoBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesBtn) {
            DBMethods.removeCPU(selectedRowItem.getId());
            fillAndRefreshTable();
        }
    }
}
