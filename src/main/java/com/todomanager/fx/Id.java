package com.todomanager.fx;

public class Id {
    private static Id id;
    private int baseId;

    private Id() {}

    public static synchronized Id getInstance() {
        if(id == null) id = new Id();
        return id;
    }

    public void setBaseId(int id) {
        baseId = id;
    }

    public int generateId() {
        return ++baseId;
    }
}