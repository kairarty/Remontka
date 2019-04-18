package programm.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainTableModel {
    final IntegerProperty id;
    final IntegerProperty counter;
    final StringProperty podrazdel;
    final StringProperty cabinetNum;
    final StringProperty user;
    final StringProperty networkName;
    final StringProperty position;
    final StringProperty os;
    final StringProperty pcNum;
    final StringProperty cpu;
    final StringProperty ram;
    final StringProperty hdd;
    final StringProperty monitor;
    final StringProperty monitorNum;
    final StringProperty printDevice;
    final StringProperty printDeviceType;
    final StringProperty printDeviceNum;
    final StringProperty repair;

    public MainTableModel() {   // пустой конструктор нужен только для Spring JDBC Template
        this(0,0,"","","","","","","","","","","","","","","","");
    }

    public MainTableModel(int id, int counter, String podrazdel, String cabinetNum, String user, String networkName,
                          String position, String os, String pcNum, String cpu, String ram, String hdd, String monitor,
                          String monitorNum, String printDevice, String printDeviceType, String printDeviceNum,
                          String repair) {
        this.id = new SimpleIntegerProperty(id);
        this.counter = new SimpleIntegerProperty(counter);
        this.podrazdel = new SimpleStringProperty(podrazdel);
        this.cabinetNum = new SimpleStringProperty(cabinetNum);
        this.user = new SimpleStringProperty(user);
        this.networkName = new SimpleStringProperty(networkName);
        this.position = new SimpleStringProperty(position);
        this.os = new SimpleStringProperty(os);
        this.pcNum = new SimpleStringProperty(pcNum);
        this.cpu = new SimpleStringProperty(cpu);
        this.ram = new SimpleStringProperty(ram);
        this.hdd = new SimpleStringProperty(hdd);
        this.monitor = new SimpleStringProperty(monitor);
        this.monitorNum = new SimpleStringProperty(monitorNum);
        this.printDevice = new SimpleStringProperty(printDevice);
        this.printDeviceType = new SimpleStringProperty(printDeviceType);
        this.printDeviceNum = new SimpleStringProperty(printDeviceNum);
        this.repair = new SimpleStringProperty(repair);
    }

    public void setCpu(String cpu) {
        this.cpu.set(cpu);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getCounter() {
        return counter.get();
    }

    public IntegerProperty counterProperty() {
        return counter;
    }

    public String getPodrazdel() {
        return podrazdel.get();
    }

    public StringProperty podrazdelProperty() {
        return podrazdel;
    }

    public void setPodrazdel(String podrazdel) {
        this.podrazdel.set(podrazdel);
    }

    public String getCabinetNum() {
        return cabinetNum.get();
    }

    public StringProperty cabinetNumProperty() {
        return cabinetNum;
    }

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public String getNetworkName() {
        return networkName.get();
    }

    public StringProperty networkNameProperty() {
        return networkName;
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public String getOs() {
        return os.get();
    }

    public StringProperty osProperty() {
        return os;
    }

    public void setOs(String os) {
        this.os.set(os);
    }

    public String getPcNum() {
        return pcNum.get();
    }

    public StringProperty pcNumProperty() {
        return pcNum;
    }

    public String getCpu() {
        return cpu.get();
    }

    public StringProperty cpuProperty() {
        return cpu;
    }

    public String getRam() {
        return ram.get();
    }

    public StringProperty ramProperty() {
        return ram;
    }

    public String getHdd() {
        return hdd.get();
    }

    public StringProperty hddProperty() {
        return hdd;
    }

    public String getMonitor() {
        return monitor.get();
    }

    public StringProperty monitorProperty() {
        return monitor;
    }

    public String getMonitorNum() {
        return monitorNum.get();
    }

    public StringProperty monitorNumProperty() {
        return monitorNum;
    }

    public String getPrintDevice() {
        return printDevice.get();
    }

    public StringProperty printDeviceProperty() {
        return printDevice;
    }

    public String getPrintDeviceType() {
        return printDeviceType.get();
    }

    public StringProperty printDeviceTypeProperty() {
        return printDeviceType;
    }

    public String getPrintDeviceNum() {
        return printDeviceNum.get();
    }

    public StringProperty printDeviceNumProperty() {
        return printDeviceNum;
    }

    public String getRepair() {
        return repair.get();
    }

    public StringProperty repairProperty() {
        return repair;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setCounter(int counter) {
        this.counter.set(counter);
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum.set(cabinetNum);
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public void setNetworkName(String networkName) {
        this.networkName.set(networkName);
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public void setPcNum(String pcNum) {
        this.pcNum.set(pcNum);
    }

    public void setRam(String ram) {
        this.ram.set(ram);
    }

    public void setHdd(String hdd) {
        this.hdd.set(hdd);
    }

    public void setMonitor(String monitor) {
        this.monitor.set(monitor);
    }

    public void setMonitorNum(String monitorNum) {
        this.monitorNum.set(monitorNum);
    }

    public void setPrintDevice(String printDevice) {
        this.printDevice.set(printDevice);
    }

    public void setPrintDeviceType(String printDeviceType) {
        this.printDeviceType.set(printDeviceType);
    }

    public void setPrintDeviceNum(String printDeviceNum) {
        this.printDeviceNum.set(printDeviceNum);
    }

    public void setRepair(String repair) {
        this.repair.set(repair);
    }
}