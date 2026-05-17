package org.alexvsi.taskmanager.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alexvsi.taskmanager.domain.enums.Priority;
import org.alexvsi.taskmanager.domain.enums.Status;

import java.time.LocalDate;

@Table(name = "task")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 150)
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    public Task(String title, String description, Priority priority, LocalDate deadline, Status status) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.taskStatus = status;
    }
}
