package com.todomanager.fx;

import java.io.Serializable;

public class TodoRecord implements Serializable {
    private final int id;
    private final String text;
    private final Priority priority;
    private boolean isDone;

    public TodoRecord(String initialString) {
        String[] meta = initialString.substring(0, 7).split(" ");
        this.text = initialString.substring(8);
        this.priority = switch(meta[1]) {
            case "h" -> Priority.HIGH;
            case "l" -> Priority.LOW;
            default -> Priority.STANDARD;
        };
        id = Integer.parseInt(meta[2]);
        isDone = meta[0].equals("+");
    }

    public TodoRecord(String text, Priority priority) {
        this.text = text;
        this.priority = priority;
        id = Id.getInstance().generateId();
        isDone = false;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return (isDone ? "+" : "-") + " " + priority + " " + String.format("%03d", id) + " " + text;
    }
}
