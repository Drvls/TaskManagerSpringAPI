package org.alexvsi.taskmanager.domain.enums;

import lombok.Getter;

@Getter
public enum Column {
    TITLE("title"),
    DESCRIPTION("description"),
    PRIORITY("priority"),
    DEADLINE("deadline");

    private final String description;

    Column(String description){

        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
