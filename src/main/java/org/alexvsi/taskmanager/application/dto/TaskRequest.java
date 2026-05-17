package org.alexvsi.taskmanager.application.dto;

import jakarta.validation.constraints.*;
import org.alexvsi.taskmanager.domain.enums.Priority;
import org.alexvsi.taskmanager.domain.enums.Status;

import java.time.LocalDate;

public record TaskRequest(

        @NotBlank(message = "Valid title is required")
        @Size(max = 50, message = "Maximum of 50 characters")
        String title,

        @Size(max = 150, message = "Maximum of 150 characters")
        String description,

        @NotNull(message = "Priority is required")
        Priority priority,

        @FutureOrPresent(message = "The deadline should be from today onwards")
        LocalDate deadline,

        @NotNull(message = "Task status is required")
        Status taskStatus
) {
}
