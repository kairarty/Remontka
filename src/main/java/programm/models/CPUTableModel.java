package programm.models;

import javafx.beans.property.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CPUTableModel {
    final IntegerProperty id;
    final IntegerProperty rating;
    final StringProperty cpu;

    public CPUTableModel() {
        this(0, 0, "");
    }

    public CPUTableModel(int id, int rating, String cpu) {
        this.id = new SimpleIntegerProperty(id);
        this.rating = new SimpleIntegerProperty(rating);
        this.cpu = new SimpleStringProperty(cpu);
    }

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getCpu() {
        return cpu.get();
    }

    public StringProperty cpuProperty() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu.set(cpu);
    }
}