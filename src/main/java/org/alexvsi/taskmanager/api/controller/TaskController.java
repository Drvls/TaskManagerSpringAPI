package org.alexvsi.taskmanager.api.controller;

import org.alexvsi.taskmanager.application.dto.TaskRequest;
import org.alexvsi.taskmanager.application.dto.TaskResponse;
import org.alexvsi.taskmanager.domain.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> addTask(@RequestBody TaskRequest request) {
        TaskResponse task = taskService.addTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id , @RequestBody TaskRequest request) {
        TaskResponse task = taskService.updateTask(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> patchUpdate(@PathVariable Long id, @RequestBody TaskRequest request){
        TaskResponse task = taskService.patchTask(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id){
        TaskResponse task = taskService.getTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size){
        List<TaskResponse> tasks = taskService.getAllTasks(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
}
