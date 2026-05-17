package org.alexvsi.taskmanager.domain.enums;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("pending"),
    COMPLETED("completed");

    private final String description;

    Status(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
