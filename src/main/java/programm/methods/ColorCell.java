package programm.methods;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

public class ColorCell<S> extends TextFieldTableCell<S, String> {

    public ColorCell() {
        super(new DefaultStringConverter());
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            setStyle("");
        } else {
            if (item.contains("!")) {
                setStyle("-fx-background-color: green");
            } else {
                setStyle("");
            }
        }
    }
}
