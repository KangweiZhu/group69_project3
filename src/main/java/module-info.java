module com.example.mike_project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project3_fitnesschainfx to javafx.fxml;
    exports com.example.project3_fitnesschainfx;
}