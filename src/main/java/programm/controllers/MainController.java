package programm.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.SegmentedButton;
import programm.InfoConstants;
import programm.methods.DBMethods;
import programm.methods.ColorCell;
import programm.models.MainTableModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainController {
    private MainTableModel selectedRowItems;
    @FXML TextField searchTF;
    @FXML TableView<MainTableModel> mainTable;
    @FXML TableColumn<MainTableModel, Integer> positionNumberColumn;
    @FXML TableColumn<MainTableModel, String> podrazdelColumn;
    @FXML TableColumn<MainTableModel, String> cabinetNumColumn;
    @FXML TableColumn<MainTableModel, String> userColumn;
    @FXML TableColumn<MainTableModel, String> networkNameColumn;
    @FXML TableColumn<MainTableModel, String> positionColumn;
    @FXML TableColumn<MainTableModel, String> osColumn;
    @FXML TableColumn<MainTableModel, String> pcNumColumn;
    @FXML TableColumn<MainTableModel, String> cpuColumn;
    @FXML TableColumn<MainTableModel, String> ramColumn;
    @FXML TableColumn<MainTableModel, String> hddColumn;
    @FXML TableColumn<MainTableModel, String> monitorColumn;
    @FXML TableColumn<MainTableModel, String> monitorNumColumn;
    @FXML TableColumn<MainTableModel, String> printDeviceColumn;
    @FXML TableColumn<MainTableModel, String> printDeviceTypeColumn;
    @FXML TableColumn<MainTableModel, String> printDeviceNumColumn;
    @FXML TableColumn<MainTableModel, String> repairColumn;
    @FXML ListView<Button> listView;

    @FXML
    private void initialize() {
        positionNumberColumn.setCellValueFactory(cellData -> cellData.getValue().counterProperty().asObject()); // такой тип cellValueFactory
        podrazdelColumn.setCellValueFactory(cellData -> cellData.getValue().podrazdelProperty());   // для Spring. Не позволяет
        cabinetNumColumn.setCellValueFactory(cellData -> cellData.getValue().cabinetNumProperty()); // автоматически обновлять инфу в
        userColumn.setCellValueFactory(cellData -> cellData.getValue().userProperty());  // mainTable.getSelectionModel().getSelectedItem();
        networkNameColumn.setCellValueFactory(cellData -> cellData.getValue().networkNameProperty());   // инфа обновляется в методе
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty()); // вручную через сеттеры
        osColumn.setCellValueFactory(cellData -> cellData.getValue().osProperty());
        pcNumColumn.setCellValueFactory(cellData -> cellData.getValue().pcNumProperty());
        cpuColumn.setCellValueFactory(cellData -> cellData.getValue().cpuProperty());
        ramColumn.setCellValueFactory(cellData -> cellData.getValue().ramProperty());
        hddColumn.setCellValueFactory(cellData -> cellData.getValue().hddProperty());
        monitorColumn.setCellValueFactory(cellData -> cellData.getValue().monitorProperty());
        monitorNumColumn.setCellValueFactory(cellData -> cellData.getValue().monitorNumProperty());
        printDeviceColumn.setCellValueFactory(cellData -> cellData.getValue().printDeviceProperty());
        printDeviceTypeColumn.setCellValueFactory(cellData -> cellData.getValue().printDeviceTypeProperty());
        printDeviceNumColumn.setCellValueFactory(cellData -> cellData.getValue().printDeviceNumProperty());
        repairColumn.setCellValueFactory(cellData -> cellData.getValue().repairProperty());

        searchTF.setFocusTraversable(false);    // отключение автофокуса
        mainTable.setColumnResizePolicy((param) -> true);

        fillCatalogs();
        setupComboboxFields();
        setEditableCells();
        startMainTable();
        listView.setItems(DBMethods.getButtonsLinkList());
        linkButtons();
    }

    private void fillCatalogs() {
        DBMethods.fillPodrazdelAndButtonsList();
        DBMethods.fillCPUList();
        DBMethods.fillPositionList();
        DBMethods.fillMainTableList();
    }

    private void setupComboboxFields() {
        ObservableList<String> osList = FXCollections.observableArrayList("Win10", "Win7", "WnXP");

        podrazdelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(DBMethods.getPodrazdelList()));
        positionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(DBMethods.getPositionList()));
        osColumn.setCellFactory(ComboBoxTableCell.forTableColumn(osList));
        cpuColumn.setCellFactory(ComboBoxTableCell.forTableColumn(DBMethods.getCpuList()));

        podrazdelColumn.setOnEditCommit((TableColumn.CellEditEvent<MainTableModel, String> event) -> {
            String selectedPodrazdel = event.getNewValue();
            selectedRowItems = mainTable.getSelectionModel().getSelectedItem();       // наполнение объекта выбранного ряда
            selectedRowItems.setPodrazdel(selectedPodrazdel);
            DBMethods.updateWorkplace(selectedRowItems);
        });

        positionColumn.setOnEditCommit((TableColumn.CellEditEvent<MainTableModel, String> event) -> {
            String selectedPosition = event.getNewValue();
            selectedRowItems = mainTable.getSelectionModel().getSelectedItem();
            selectedRowItems.setPosition(selectedPosition);
            DBMethods.updateWorkplace(selectedRowItems);
        });

        osColumn.setOnEditCommit((TableColumn.CellEditEvent<MainTableModel, String> event) -> {
            String selectedOs = event.getNewValue();
            selectedRowItems = mainTable.getSelectionModel().getSelectedItem();
            selectedRowItems.setOs(selectedOs);
            DBMethods.updateWorkplace(selectedRowItems);
        });

        cpuColumn.setOnEditCommit((TableColumn.CellEditEvent<MainTableModel, String> event) -> {
            String selectedPodrazdel = event.getNewValue();
            selectedRowItems = mainTable.getSelectionModel().getSelectedItem();
            selectedRowItems.setCpu(selectedPodrazdel);
            DBMethods.updateWorkplace(selectedRowItems);
        });
    }

    private void startMainTable() {
        FilteredList<MainTableModel> filteredData = new FilteredList<>(DBMethods.getMainTableList(), p -> true);    // обёртка в лист для поиска
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(model -> {   // при вводе в текстовое поле ввод слушается и варианты меняются
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (model.getPodrazdel().toLowerCase().contains(lowerCaseFilter)) {    // для String
                return true;
            } else if (model.getCabinetNum() != null && model.getCabinetNum().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (model.getUser() != null && model.getUser().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (model.getNetworkName() != null && model.getNetworkName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (model.getPosition() != null && model.getPosition().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (model.getOs() != null && model.getOs().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (model.getPcNum() != null && model.getPcNum().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (model.getCpu() != null && model.getCpu().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(model.getRam()) != null && String.valueOf(model.getRam()).contains(newValue)) {      // для int
                return true;
            } else if (String.valueOf(model.getHdd()) != null && String.valueOf(model.getHdd()).contains(newValue)) {
                return true;
            } else if (model.getMonitor() != null && model.getMonitor().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (model.getMonitorNum() != null && model.getMonitorNum().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (model.getPrintDevice() != null && model.getPrintDevice().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (model.getPrintDeviceType() != null && model.getPrintDeviceType().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            else return model.getPrintDeviceNum() != null && model.getPrintDeviceNum().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<MainTableModel> sortedData = new SortedList<>(filteredData);    // Обёртка в специальный сортировочный лист
        sortedData.comparatorProperty().bind(mainTable.comparatorProperty());     // чтобы работала и сортировка, и поиск
        mainTable.setItems(sortedData);
    }

    private void setEditableCells() {
        List<TableColumn<MainTableModel, String>> columnList = Arrays.asList(cabinetNumColumn, userColumn,
                networkNameColumn, pcNumColumn, ramColumn, hddColumn, monitorColumn, monitorNumColumn,
                printDeviceColumn, printDeviceTypeColumn, printDeviceNumColumn);
        columnList.forEach(col -> col.setCellFactory(TextFieldTableCell.forTableColumn()));     // Своя редактируемая ячейка с цветом
    }                                   // выставить в свойствах таблицы в Scene Builder галку "Editable, чтобы работало

    @FXML
    private void openRepair(MouseEvent event) { // стоит в таблице в On Mouse Pressed в Scene Builder
        selectedRowItems = mainTable.getSelectionModel().getSelectedItem();   // наполнение модели инфой выбранного столбца
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2 && selectedRowItems != null) {  // при двойном клике мышкой
            RepairController.setSelectedRowId(selectedRowItems.getId());
            showInfoWindow();
        }
    }

    private void linkButtons() {    // метод привязывает кнопки навигации к подразделениям
        List<String> podrazdelFromWorkplaceList = new ArrayList<>();
        DBMethods.getMainTableList().forEach(e -> podrazdelFromWorkplaceList.add(e.getPodrazdel()));  // получение подразделений из упорядоченного списка
        List<Integer> indexList = new ArrayList<>();
        DBMethods.getPodrazdelList().forEach(e -> indexList.add(podrazdelFromWorkplaceList.indexOf(e)));  // получение позиций
        // подразделений из ранее созданного списка
        for (int i = 0; i < DBMethods.getButtonsLinkList().size(); i++) {   // присвоение каждой кнопке позиций скролла
            int counterI = i;
            DBMethods.getButtonsLinkList().get(i).setOnAction(event -> mainTable.scrollTo(indexList.get(counterI)));
        }
    }

    @FXML
    private void generateExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(); // создание таблицы
        XSSFSheet sheet = workbook.createSheet("Выбранные ПК"); // создание листа

        int rownum = 0;
        Cell cell;
        Row row;
        row = sheet.createRow(rownum);  // создать 1-ый ряд

        XSSFFont font = workbook.createFont();  // настройка стиля шрифта ячейки
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        List<String> headerNames = Arrays.asList("№ п/п", "Подразделение", "№ кабинета", "Пользователь (ФИО)",
                "Имя в сети", "Должность", "ОС", "Инв. № ПЭВМ", "CPU", "RAM", "HDD", "Монитор", "Инв. № монитора",
                "Печатное устройство", "Тип печ. устр-ва", "Инв. или № печ. устр-ва", "Ремонт");
        for (int i = 0; i < headerNames.size(); i++) {  // создание шапки таблицы
            cell = row.createCell(i);
            cell.setCellValue(headerNames.get(i));
            cell.setCellStyle(style);
        }

        List<MainTableModel> workplaceList = mainTable.getItems();  // список со всем содержимым таблицы
        for (int i = 0; i < workplaceList.size(); i++) {
            rownum++;
            row = sheet.createRow(rownum);
            List<String> fields = Arrays.asList(    // запись одного объекта в список на каждой итерации для сокращения кода
                    String.valueOf(i + 1),
                    workplaceList.get(i).getPodrazdel(),
                    workplaceList.get(i).getCabinetNum(),
                    workplaceList.get(i).getUser(),
                    workplaceList.get(i).getNetworkName(),
                    workplaceList.get(i).getPodrazdel(),
                    workplaceList.get(i).getOs(),
                    workplaceList.get(i).getPcNum(),
                    workplaceList.get(i).getCpu(),
                    workplaceList.get(i).getRam(),
                    workplaceList.get(i).getHdd(),
                    workplaceList.get(i).getMonitor(),
                    workplaceList.get(i).getMonitorNum(),
                    workplaceList.get(i).getPrintDevice(),
                    workplaceList.get(i).getPrintDeviceType(),
                    workplaceList.get(i).getPrintDeviceNum(),
                    workplaceList.get(i).getRepair());
            for (int j = 0; j < fields.size(); j++) {   // создание строки в excel с одним объектом
                cell = row.createCell(j);
                cell.setCellValue(fields.get(j));
            }
        }
        saveToExcel(workbook);
    }

    private void saveToExcel(XSSFWorkbook workbook) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Файлы Excel (*.xlsx)",
                "*.xlsx");  // установка названия расширения и расширения в окне сохранения
        fileChooser.getExtensionFilters().add(extFilter);

        Stage mainStage = (Stage) searchTF.getScene().getWindow();
        File file = fileChooser.showSaveDialog(mainStage);
        if (file != null) { // чтобы не было ошибки при отмене экспорта
            FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
            workbook.write(outputStream);
            outputStream.close();
        }
    }

    @FXML
    private void saveEnteredWorkplaceInfo(TableColumn.CellEditEvent<MainTableModel, String> event) { // стоит на колонках
        TablePosition<MainTableModel, String> pos = event.getTablePosition();                       // в On Edit Commit
        selectedRowItems = mainTable.getSelectionModel().getSelectedItem();
        int columnNumber = pos.getColumn();
        String enteredValue = event.getNewValue();

        switch (columnNumber) {
            case 2:
                selectedRowItems.setCabinetNum(enteredValue);
                break;
            case 3:
                selectedRowItems.setUser(enteredValue);
                break;
            case 4:
                selectedRowItems.setNetworkName(enteredValue);
                break;
            case 7:
                selectedRowItems.setPcNum(enteredValue);
                break;
            case 9:
                selectedRowItems.setRam(enteredValue);
                break;
            case 10:
                selectedRowItems.setHdd(enteredValue);
                break;
            case 11:
                selectedRowItems.setMonitor(enteredValue);
                break;
            case 12:
                selectedRowItems.setMonitorNum(enteredValue);
                break;
            case 13:
                selectedRowItems.setPrintDevice(enteredValue);
                break;
            case 14:
                selectedRowItems.setPrintDeviceType(enteredValue);
                break;
            case 15:
                selectedRowItems.setPrintDeviceNum(enteredValue);
                break;
        }
        DBMethods.updateWorkplace(selectedRowItems);
    }

    @FXML
    private void removeSelectedRow() {  // стоит в MenuItem в On Action
        selectedRowItems = mainTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтвердите удаление");
        setAlertIcon(alert);
        alert.setHeaderText(null);
        alert.setContentText("Вы действительно хотите удалить ПК " + selectedRowItems.getUser() + " и всю связанную c " +
                "ним информацию о ремонте?");

        ButtonType yesBtn = new ButtonType("Да");
        ButtonType NoBtn = new ButtonType("Нет");
        alert.getButtonTypes().setAll(yesBtn, NoBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesBtn) {
            DBMethods dbMethods = new DBMethods();  // аннотация @Transactional не позволяет сделать static-метод
            dbMethods.removeWorkplace(selectedRowItems.getId());
            refreshTable();
            linkButtons();  // обновление привязки кнопок навигации к новым индексам после удаления
        }
    }

    @FXML
    private void addWorkplace() {   // стоит в MenuItem в On Action
        String selectedPodrazdel = mainTable.getSelectionModel().getSelectedItem().getPodrazdel();
        DBMethods.addEmptyWorkplace(selectedPodrazdel);
        refreshTable();
        linkButtons();
    }

    private void refreshTable() {
        DBMethods.getMainTableList().clear();
        DBMethods.fillMainTableList();
    }

    private void showInfoWindow() {
        int selectedIndex = mainTable.getSelectionModel().getSelectedIndex();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/repair.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Информация о ремонте");
            stage.getIcons().add(new Image("/icon.jpg"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getScene().getStylesheets().add(RepairController.class.getResource("/css/infoWindow.css").toExternalForm());
            stage.setOnHiding(event -> {
                refreshTable(); // при обновлении содержимого слетает выбранная строка
                mainTable.getSelectionModel().select(selectedIndex); // установка выбранной строки
            });
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCPUList() throws IOException {
        var fxmlLoader = new FXMLLoader(getClass().getResource("/cpuList.fxml"));
        Parent root = fxmlLoader.load();
        var stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Справочник процессоров");
        stage.getIcons().add(new Image("/icon.jpg"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnHiding(event -> {
            DBMethods.getCpuList().clear();
            DBMethods.fillCPUList();
        });
        stage.showAndWait();
    }

    @FXML
    private void showHelp() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Справка");
        setAlertIcon(alert);
        alert.setHeaderText(null);
        alert.setContentText(InfoConstants.HELP_TEXT);
        alert.showAndWait();
    }

    @FXML
    private void showAbout() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        setAlertIcon(alert);
        alert.setHeaderText(null);
        String version = System.getProperty("java.version");
        alert.setContentText(InfoConstants.ABOUT_TEXT + version);
        alert.getDialogPane().setPrefSize(450, 190);
        alert.showAndWait();
    }

    public static void showConnectionError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        setAlertIcon(alert);
        alert.setHeaderText("Ошибка соединения с базой данных!");
        alert.setContentText("Повторите попытку позже. Программа будет закрыта.");
        alert.showAndWait();
        System.exit(0);
    }

    static void setAlertIcon(Alert alert) {
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/icon.jpg"));     // иконка Alert'a
    }
}