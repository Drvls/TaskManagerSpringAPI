package org.alexvsi.taskmanager.domain.enums;

import lombok.Getter;

@Getter
public enum Priority {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private final String description;

    Priority(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
