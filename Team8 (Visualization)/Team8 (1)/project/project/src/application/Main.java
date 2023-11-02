package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    // The main entry point of the JavaFX application.
    public static void main(String[] args) {
        // Launch the JavaFX application by calling the launch() method.
        launch(args);
    }

    // The start() method is called when the JavaFX application is launched.
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file that defines the user interface structure.
        Parent root = FXMLLoader.load(getClass().getResource("FXMLtest.fxml"));
        
        // Set the title of the application window.
        primaryStage.setTitle("Taylor Borden CS420 Application");
        
        // Create a new scene and set the root node and dimensions.
        primaryStage.setScene(new Scene(root, 600, 700));
        
        // Display the application window the.
        primaryStage.show();
    }
}
