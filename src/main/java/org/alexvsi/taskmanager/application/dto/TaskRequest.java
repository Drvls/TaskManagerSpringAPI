package org.alexvsi.taskmanager.application.dto;

import org.alexvsi.taskmanager.domain.enums.Priority;
import org.alexvsi.taskmanager.domain.enums.Status;

import java.time.LocalDate;

public record TaskRequest(String title, String description, Priority priority, LocalDate deadline, Status taskStatus) {
}
