package org.alexvsi.taskmanager.domain.service;

import jakarta.persistence.EntityNotFoundException;
import org.alexvsi.taskmanager.application.dto.TaskRequest;
import org.alexvsi.taskmanager.application.dto.TaskResponse;
import org.alexvsi.taskmanager.domain.entity.Task;
import org.alexvsi.taskmanager.infra.exception.TaskNotFoundException;
import org.alexvsi.taskmanager.infra.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public TaskResponse addTask(TaskRequest request) {
        Task task = new Task(
                request.title(),
                request.description(),
                request.priority(),
                request.deadline(),
                request.taskStatus()
        );

        return new TaskResponse(repository.save(task));
    }

    public TaskResponse updateTask(Long id, TaskRequest response) {
        Task task = repository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with id: " + id + " not found")
        );

        task.setTitle(response.title());
        task.setDescription(response.description());
        task.setPriority(response.priority());
        task.setDeadline(response.deadline());
        task.setTaskStatus(response.taskStatus());

        return new TaskResponse(repository.save(task));
    }

}
