module com.example.dashboardjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dashboardjavafx to javafx.fxml;
    exports com.example.dashboardjavafx;
}