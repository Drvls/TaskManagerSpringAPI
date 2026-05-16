package org.alexvsi.taskmanager.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.alexvsi.taskmanager.domain.enums.Priority;
import org.alexvsi.taskmanager.domain.enums.Status;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "task")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDate deadline;
    private Status taskStatus;

    /*
    @Override
    public String toString() {
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "\nID: " + id
                + "\nTítulo: " + title
                + "\nDescrição: " + description
                + "\nPrioridade: " + priority
                + "\nStatus: " + taskStatus
                + "\nData limite: " + deadline.format(dataFormato);
    }
     */
}
