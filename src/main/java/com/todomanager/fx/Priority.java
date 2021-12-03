package com.todomanager.fx;

public enum Priority {
    HIGH("h"),
    STANDARD("s"),
    LOW("l");

    private final String value;

    Priority(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}