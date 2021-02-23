import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author Nevena Kolev
 * @version 21.02.2021
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //creates a file
        File file = new File("src\\files\\Phonebook.csv");
        file.createNewFile();

        Parent root = FXMLLoader.load(getClass().getResource("viewcontroller/view.fxml"));
        primaryStage.setTitle("Phonebook");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
