// Sources:
// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/AnchorPane.html
// https://www.tutorialspoint.com/javafx/2dshapes_rounded_rectangle.htm
// https://www.youtube.com/watch?v=7nlU3_kEjTE&list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev&index=4
// https://www.geeksforgeeks.org/getter-and-setter-in-java/
// https://stackoverflow.com/questions/28543957/java-the-value-of-the-field-is-not-used

package com.example.dashboardjavafx;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;


public class DashboardMain extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
        primaryStage.setTitle("Farm Management Dashboard");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





