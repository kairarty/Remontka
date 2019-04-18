package programm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        primaryStage.setTitle("Ремонтка");
        primaryStage.getIcons().add(new Image("/icon.jpg"));
        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().getStylesheets().add(Main.class.getResource("/css/main.css").toExternalForm()); // добавление .css
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
