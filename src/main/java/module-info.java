module com.todomanager.fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.todomanager.fx to javafx.fxml;
    exports com.todomanager.fx;
}