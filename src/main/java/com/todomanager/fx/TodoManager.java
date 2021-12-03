package com.todomanager.fx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TodoManager {
    private static TodoManager manager;
    private RenderFunction render;
    private List<TodoRecord> todoRecords = new ArrayList<>();
    private String filePath;

    @FunctionalInterface
    public interface RenderFunction {
        void execute();
    }

    private TodoManager() {}

    public static synchronized TodoManager getInstance() {
        if(manager == null) manager = new TodoManager();
        return manager;
    }

    public void setRender(RenderFunction render) {
        this.render = render;
    }

    public RenderFunction getRender() {
        return render;
    }

    public TodoManager connect() {
        filePath = Auth.getInstance().getLogin() + ".bin";
        return this;
    }

    public TodoManager load() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filePath))) {
            todoRecords = (ArrayList<TodoRecord>) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            todoRecords = new ArrayList<>();
        }
        return this;
    }

    public int getLastId() {
        int lastId = 0;
        for(TodoRecord record : todoRecords) if(record.getId() > lastId) lastId = record.getId();
        return lastId;
    }

    public List<TodoRecord> getAllRecords() {
        return todoRecords;
    }

    public List<TodoRecord> getDoneRecords() {
        List<TodoRecord> newList = new ArrayList<>();
        for(TodoRecord record : todoRecords) if(record.getIsDone()) newList.add(record);
        return newList;
    }

    public List<TodoRecord> getNotDoneRecords() {
        List<TodoRecord> newList = new ArrayList<>();
        for(TodoRecord record : todoRecords) if(!record.getIsDone()) newList.add(record);
        return newList;
    }

    public void addRecord(String text, String priorityLabel) {
        Priority priority = switch (priorityLabel) {
            case "h" -> Priority.HIGH;
            case "l" -> Priority.LOW;
            default -> Priority.STANDARD;
        };
        TodoRecord newRecord = new TodoRecord(text, priority);
        todoRecords.add(newRecord);
    }

    public void deleteRecord(int id) {
        for(TodoRecord record : todoRecords) if(record.getId() == id) {
            todoRecords.remove(record);
            return;
        }
    }

    public void deleteAllRecords() {
        todoRecords = new ArrayList<>();
    }

    public void markRecordAsDone(int id) {
        for(TodoRecord record : todoRecords) if(record.getId() == id) {
            record.setIsDone(true);
            return;
        }
    }

    public void markRecordAsNotDone(int id) {
        for(TodoRecord record : todoRecords) if(record.getId() == id) {
            record.setIsDone(false);
            return;
        }
    }

    public void saveRecords() {
        try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filePath))) {
            writer.writeObject(todoRecords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
