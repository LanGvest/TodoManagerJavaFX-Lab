package com.todomanager.fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class TodoRecordItem extends ListCell<TodoRecord> {
    @FXML
    private Button deleteIcon;

    @FXML
    private CheckBox isDoneBtn;

    @FXML
    private Label priorityLabelText;

    @FXML
    private Label recordText;

    @FXML
    private AnchorPane itemContainer;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(TodoRecord record, boolean empty) {
        super.updateItem(record, empty);

        if(empty || record == null) {
            setText(null);
            setGraphic(null);
        } else {
            if(mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("record.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            recordText.setText(record.getText());
            switch(record.getPriority()) {
                case HIGH -> {
                    priorityLabelText.setText("H");
                    priorityLabelText.setStyle("-fx-text-fill: #fc4561");
                }
                case LOW -> {
                    priorityLabelText.setText("L");
                    priorityLabelText.setStyle("-fx-text-fill: #07c056");
                }
                default -> {
                    priorityLabelText.setText("S");
                    priorityLabelText.setStyle("-fx-text-fill: #f1b709");
                }
            }
            isDoneBtn.setSelected(record.getIsDone());
            isDoneBtn.setOnAction(actionEvent -> {
                if(isDoneBtn.isSelected()) {
                    TodoManager.getInstance().markRecordAsDone(record.getId());
                } else {
                    TodoManager.getInstance().markRecordAsNotDone(record.getId());
                }
                TodoManager.getInstance().saveRecords();
            });
            deleteIcon.setOnAction(actionEvent -> {
                TodoManager.getInstance().deleteRecord(record.getId());
                TodoManager.getInstance().getRender().execute();
            });

            setText(null);
            setGraphic(itemContainer);
        }
    }
}
