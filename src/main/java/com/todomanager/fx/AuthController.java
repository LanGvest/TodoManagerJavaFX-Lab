package com.todomanager.fx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authBtn;

    @FXML
    private TextField loginInput;

    @FXML
    void initialize() {
        loginInput.textProperty().addListener((observable, oldValue, newValue) -> authBtn.setDisable(!Pattern.matches("^[a-zA-Z_\\d]+$", newValue)));
        authBtn.setOnAction(actionEvent -> {
            Auth.getInstance().setLogin(loginInput.getText());

            // go home window
            loginInput.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("home.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.setTitle("TODO Manager");
            stage.showAndWait();
        });
    }
}
