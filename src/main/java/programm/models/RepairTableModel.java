package programm.models;

import javafx.beans.property.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepairTableModel {
    final LongProperty id;
    final StringProperty start;
    final StringProperty end;
    final StringProperty os;
    final StringProperty motherboard;
    final StringProperty cpu;
    final StringProperty ram;
    final StringProperty hdd;
    final StringProperty monitor;
    final StringProperty bp;
    final StringProperty netCard;
    final StringProperty printDevice;

    public RepairTableModel() {
        this(0,"","","","","","","","","","","");
    }

    public RepairTableModel(long id, String start, String end, String os, String motherboard, String cpu, String ram,
                            String hdd, String monitor, String bp, String netCard, String printDevice) {
        this.id = new SimpleLongProperty(id);
        this.start = new SimpleStringProperty(start);
        this.end = new SimpleStringProperty(end);
        this.os = new SimpleStringProperty(os);
        this.motherboard = new SimpleStringProperty(motherboard);
        this.cpu = new SimpleStringProperty(cpu);
        this.ram = new SimpleStringProperty(ram);
        this.hdd = new SimpleStringProperty(hdd);
        this.monitor = new SimpleStringProperty(monitor);
        this.bp = new SimpleStringProperty(bp);
        this.netCard = new SimpleStringProperty(netCard);
        this.printDevice = new SimpleStringProperty(printDevice);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getStart() {
        return start.get();
    }

    public StringProperty startProperty() {
        return start;
    }

    public String getEnd() {
        return end.get();
    }

    public StringProperty endProperty() {
        return end;
    }

    public String getOs() {
        return os.get();
    }

    public StringProperty osProperty() {
        return os;
    }

    public String getMotherboard() {
        return motherboard.get();
    }

    public StringProperty motherboardProperty() {
        return motherboard;
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

    public String getBp() {
        return bp.get();
    }

    public StringProperty bpProperty() {
        return bp;
    }

    public String getNetCard() {
        return netCard.get();
    }

    public StringProperty netCardProperty() {
        return netCard;
    }

    public String getPrintDevice() {
        return printDevice.get();
    }

    public StringProperty printDeviceProperty() {
        return printDevice;
    }

    public void setEnd(String end) {
        this.end.set(end);
    }

    public void setOs(String os) {
        this.os.set(os);
    }

    public void setMotherboard(String motherboard) {
        this.motherboard.set(motherboard);
    }

    public void setCpu(String cpu) {
        this.cpu.set(cpu);
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

    public void setBp(String bp) {
        this.bp.set(bp);
    }

    public void setNetCard(String netCard) {
        this.netCard.set(netCard);
    }

    public void setPrintDevice(String printDevice) {
        this.printDevice.set(printDevice);
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public void setId(long id) {
        this.id.set(id);
    }
}
