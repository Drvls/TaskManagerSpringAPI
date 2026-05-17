package org.alexvsi.taskmanager.domain.service;

import org.alexvsi.taskmanager.application.dto.TaskRequest;
import org.alexvsi.taskmanager.application.dto.TaskResponse;
import org.alexvsi.taskmanager.domain.entity.Task;
import org.alexvsi.taskmanager.infra.exception.TaskNotFoundException;
import org.alexvsi.taskmanager.infra.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = repository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with id: " + id + " not found")
        );

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority());
        task.setDeadline(request.deadline());
        task.setTaskStatus(request.taskStatus());

        return new TaskResponse(repository.save(task));
    }

    public TaskResponse patchTask(Long id, TaskRequest request){
        Task task = repository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task with id: " + id + " not found")
        );

        if(request.title() != null
                && !request.title().isBlank()
                && !task.getTitle().equals(request.title())
        ){
            task.setTitle(request.title());
        }

        if(request.description() != null
            && !request.description().isBlank()
            && !task.getDescription().equals(request.description())
        ){
            task.setDescription(request.description());
        }

        if(request.priority() != null
                && task.getPriority() != request.priority()
        ){
            task.setPriority(request.priority());
        }

        if(request.deadline() != null
                && task.getDeadline().isEqual(request.deadline())
        ){
            task.setDeadline(request.deadline());
        }

        if(request.taskStatus() != null
               && task.getTaskStatus() != request.taskStatus()
        ){
            task.setTaskStatus(request.taskStatus());
        }

        return new TaskResponse(repository.save(task));
    }

    public TaskResponse getTask(Long id){
        Task task = repository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task with id: " + id + " not found")
        );
        return new TaskResponse(task);
    }

    public List<TaskResponse> getAllTasks(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Task> tasks = repository.findAll(pageRequest);
        return tasks.map(TaskResponse::new).toList();
    }
}
