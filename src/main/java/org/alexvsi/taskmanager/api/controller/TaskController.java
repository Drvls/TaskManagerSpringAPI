package org.alexvsi.taskmanager.api.controller;

import org.alexvsi.taskmanager.application.dto.TaskRequest;
import org.alexvsi.taskmanager.application.dto.TaskResponse;
import org.alexvsi.taskmanager.domain.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> addTask(@RequestBody TaskRequest request) {
        TaskResponse task = taskService.addTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
}
