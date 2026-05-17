package org.alexvsi.taskmanager.domain.service;

import org.alexvsi.taskmanager.application.dto.TaskRequest;
import org.alexvsi.taskmanager.application.dto.TaskResponse;
import org.alexvsi.taskmanager.domain.entity.Task;
import org.alexvsi.taskmanager.infra.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public TaskResponse addTask(TaskRequest taskRequestdto) {
        Task task = new Task(
                taskRequestdto.title(),
                taskRequestdto.description(),
                taskRequestdto.priority(),
                taskRequestdto.deadline(),
                taskRequestdto.taskStatus()
        );

        return new TaskResponse(repository.save(task));
    }

}
