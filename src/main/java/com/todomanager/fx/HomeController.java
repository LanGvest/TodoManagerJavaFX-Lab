package com.todomanager.fx;

import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class HomeController {

    private static TodoManager todoManager;

    @FXML
    private ListView<TodoRecord> listview;

    private ObservableList<TodoRecord> studentObservableList;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBtn;

    @FXML
    private VBox container;

    @FXML
    private Button deleteAllBtn;

    @FXML
    private ChoiceBox<String> filterSelect;

    @FXML
    private Label infoText;

    @FXML
    private Label loginText;

    @FXML
    private ChoiceBox<String> prioritySelect;

    @FXML
    private TextField recordInput;

    private void render() {
        List<TodoRecord> todoRecords = switch(filterSelect.getValue()) {
            case "DONE" -> todoManager.getDoneRecords();
            case "NOT DONE" -> todoManager.getNotDoneRecords();
            default -> todoManager.getAllRecords();
        };
        List<TodoRecord> reversedTodoRecords = new ArrayList<>(todoRecords);
        Collections.reverse(reversedTodoRecords);
        studentObservableList.clear();
        studentObservableList.addAll(reversedTodoRecords);
        todoManager.saveRecords();

        int len = todoRecords.toArray().length;
        if(len == 0) {
            infoText.setText("No TODO records yet");
        } else if(len == 1) {
            infoText.setText("1 TODO record");
        } else {
            infoText.setText(len + " TODO records");
        }

        deleteAllBtn.setDisable(len == 0);
    }

    @FXML
    void initialize() {
        todoManager = TodoManager.getInstance().connect().load();
        Id.getInstance().setBaseId(todoManager.getLastId());
        todoManager.setRender(this::render);

        studentObservableList = FXCollections.observableArrayList();

        prioritySelect.getItems().addAll("HIGH", "STANDARD", "LOW");
        prioritySelect.setValue("STANDARD");

        filterSelect.getItems().addAll("ALL", "DONE", "NOT DONE");
        filterSelect.setValue("ALL");
        filterSelect.setOnAction(actionEvent -> render());

        listview.setItems(studentObservableList);
        listview.setCellFactory(studentListView -> new TodoRecordItem());

        todoManager.getRender().execute();

        loginText.setText("@" + Auth.getInstance().getLogin());

        recordInput.textProperty().addListener((observable, oldValue, newValue) -> addBtn.setDisable(newValue.trim().equals("")));

        addBtn.setOnAction(actionEvent -> {
            todoManager.addRecord(recordInput.getText(), prioritySelect.getValue().toLowerCase().substring(0, 1));
            recordInput.setText("");
            todoManager.getRender().execute();
        });

        deleteAllBtn.setOnAction(actionEvent -> {
            todoManager.deleteAllRecords();
            todoManager.getRender().execute();
        });
    }
}
