package org.alexvsi.taskmanager.api.controller;

import jakarta.validation.Valid;
import org.alexvsi.taskmanager.application.dto.TaskRequest;
import org.alexvsi.taskmanager.application.dto.TaskResponse;
import org.alexvsi.taskmanager.domain.enums.Priority;
import org.alexvsi.taskmanager.domain.enums.Status;
import org.alexvsi.taskmanager.domain.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> addTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse task = taskService.addTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id , @Valid @RequestBody TaskRequest request) {
        TaskResponse task = taskService.updateTask(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> patchUpdate(@PathVariable Long id, @Valid @RequestBody TaskRequest request){
        TaskResponse task = taskService.patchTask(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id){
        TaskResponse task = taskService.getTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> searchTask(
            @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size,
             @RequestParam(required = false) String title,
             @RequestParam(required = false) Priority priority,
             @RequestParam(required = false) LocalDate deadline,
             @RequestParam(required = false) Status status
             ){
        List<TaskResponse> tasks = taskService.searchTasks(page, size, title, priority, deadline, status);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
}
