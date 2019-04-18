package programm.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lombok.Setter;
import programm.methods.DBMethods;
import programm.methods.SupportMethods;
import programm.methods.WrappingTextFieldTableCell;
import programm.models.RepairTableModel;

import java.util.Arrays;
import java.util.List;

public class RepairController {
    @Setter
    private static int selectedRowId;
    private RepairTableModel selectedRowItems;
    @FXML TableView<RepairTableModel> repairTable;
    @FXML TableColumn<RepairTableModel, String> startRepairColumn;
    @FXML TableColumn<RepairTableModel, String> endRepairColumn;
    @FXML TableColumn<RepairTableModel, String> osColumn;
    @FXML TableColumn<RepairTableModel, String> motherboardColumn;
    @FXML TableColumn<RepairTableModel, String> cpuColumn;
    @FXML TableColumn<RepairTableModel, String> ramColumn;
    @FXML TableColumn<RepairTableModel, String> hddColumn;
    @FXML TableColumn<RepairTableModel, String> monitorColumn;
    @FXML TableColumn<RepairTableModel, String> bpColumn;
    @FXML TableColumn<RepairTableModel, String> netCardColumn;
    @FXML TableColumn<RepairTableModel, String> printDeviceColumn;
    @FXML MenuItem deleteMenuItem;

    @FXML
    private void initialize() {
        startRepairColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        endRepairColumn.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        osColumn.setCellValueFactory(cellData -> cellData.getValue().osProperty());
        motherboardColumn.setCellValueFactory(cellData -> cellData.getValue(). motherboardProperty());
        cpuColumn.setCellValueFactory(cellData -> cellData.getValue().cpuProperty());
        ramColumn.setCellValueFactory(cellData -> cellData.getValue().ramProperty());
        hddColumn.setCellValueFactory(cellData -> cellData.getValue().hddProperty());
        monitorColumn.setCellValueFactory(cellData -> cellData.getValue().monitorProperty());
        bpColumn.setCellValueFactory(cellData -> cellData.getValue().bpProperty());
        netCardColumn.setCellValueFactory(cellData -> cellData.getValue().netCardProperty());
        printDeviceColumn.setCellValueFactory(cellData -> cellData.getValue().printDeviceProperty());
        repairTable.setColumnResizePolicy((param) -> true);

        fillAndRefreshTable();
        setEditableWrapCells();
        repairTable.setItems(DBMethods.getRepairTableList());
    }

    private void setEditableWrapCells() {
        List<TableColumn<RepairTableModel, String>> columnList = Arrays.asList(osColumn, motherboardColumn, cpuColumn,
                ramColumn, hddColumn, monitorColumn, bpColumn, netCardColumn, printDeviceColumn);
        columnList.forEach(col -> col.setCellFactory(param -> new WrappingTextFieldTableCell<>()));
    }

    private void fillAndRefreshTable() {
        DBMethods.getRepairTableList().clear();
        DBMethods.fillRepairTableList(selectedRowId);
    }

    @FXML
    private void saveEnteredRepairInfo(TableColumn.CellEditEvent<RepairTableModel, String> event) { // стоит в On Edit Commit
        TablePosition<RepairTableModel, String> pos = event.getTablePosition();                       // на колонках
        selectedRowItems = repairTable.getSelectionModel().getSelectedItem();

        int columnNumber = pos.getColumn();
        String enteredValue = event.getNewValue();

        switch (columnNumber) {
            case 1:
                selectedRowItems.setEnd(enteredValue);
                break;
            case 2:
                selectedRowItems.setOs(enteredValue);
                break;
            case 3:
                selectedRowItems.setMotherboard(enteredValue);
                break;
            case 4:
                selectedRowItems.setCpu(enteredValue);
                break;
            case 5:
                selectedRowItems.setRam(enteredValue);
                break;
            case 6:
                selectedRowItems.setHdd(enteredValue);
                break;
            case 7:
                selectedRowItems.setMonitor(enteredValue);
                break;
            case 8:
                selectedRowItems.setBp(enteredValue);
                break;
            case 9:
                selectedRowItems.setNetCard(enteredValue);
                break;
            case 10:
                selectedRowItems.setPrintDevice(enteredValue);
                break;
        }
        DBMethods.updateRepair(selectedRowItems);
    }

    @FXML
    private void addNewRepair() {  // стоит в MenuItem в On Action
        DBMethods.addEmptyRepair(selectedRowId);
        fillAndRefreshTable();
    }

    @FXML
    private void checkDeleteMenuItem() {    // стоит в On Context Menu Requested на таблице
        selectedRowItems = repairTable.getSelectionModel().getSelectedItem();
        if (selectedRowItems == null) {
            deleteMenuItem.setVisible(false);
        } else {
            deleteMenuItem.setVisible(true);
        }
    }

    @FXML
    private void removeSelectedRepair() {   // стоит в MenuItem в On Action
        selectedRowItems = repairTable.getSelectionModel().getSelectedItem();
        DBMethods.removeRepairById(selectedRowItems.getId());
        fillAndRefreshTable();
    }

    @FXML
    private void autofillColumn(TableColumn.CellEditEvent<RepairTableModel, String> event) { // стоит в On Edit Start
        TablePosition<RepairTableModel, String> pos = event.getTablePosition(); // на каждой колонке, кроме первой
        selectedRowItems = repairTable.getSelectionModel().getSelectedItem();
        String currentCellValue = event.getOldValue();
        int columnNumber = pos.getColumn();
        String todayDate = SupportMethods.getTodayDate();
        String repairText = "Ремонт " + todayDate;
        String replaceText = "Замена " + todayDate;


        if (currentCellValue == null || currentCellValue.isEmpty()) {
            switch (columnNumber) {
                case 1:
                    selectedRowItems.setEnd(todayDate);
                    break;
                case 2:
                    selectedRowItems.setOs("Переустановка " + todayDate);
                    break;
                case 3:
                    selectedRowItems.setMotherboard(repairText);
                    break;
                case 4:
                    selectedRowItems.setCpu(replaceText);
                    break;
                case 5:
                    selectedRowItems.setRam(replaceText);
                    break;
                case 6:
                    selectedRowItems.setHdd(replaceText);
                    break;
                case 7:
                    selectedRowItems.setMonitor(repairText);
                    break;
                case 8:
                    selectedRowItems.setBp(repairText);
                    break;
                case 9:
                    selectedRowItems.setNetCard(replaceText);
                    break;
                case 10:
                    selectedRowItems.setPrintDevice(repairText);
                    break;
            }
            repairTable.getColumns().get(0).setVisible(false);  // эта и следующая строки для того, чтобы при клике на
            repairTable.getColumns().get(0).setVisible(true);   // пустую ячейку в ней сразу появлялось содержимое
            DBMethods.updateRepair(selectedRowItems);
        }
    }

    @FXML
    private void detectClick(MouseEvent evt) {  // очистка фокуса выбранной строки при клике на пустое место таблицы. Стоит в On Mouse Clicked в SceneBuilder на таблице
        Node selectedNode = evt.getPickResult().getIntersectedNode();   // выбрать узел при клике
        while (selectedNode != null && !(selectedNode instanceof TableRow)) {
            selectedNode = selectedNode.getParent();
        }
        if (selectedNode == null || ((TableRow) selectedNode).isEmpty()) {
            repairTable.getSelectionModel().clearSelection();   // снять фокус с выбранной строки
        }
    }
}