package org.alexvsi.taskmanager.domain.service;

import org.alexvsi.taskmanager.application.dto.TaskRequest;
import org.alexvsi.taskmanager.application.dto.TaskResponse;
import org.alexvsi.taskmanager.domain.entity.Task;
import org.alexvsi.taskmanager.domain.enums.Priority;
import org.alexvsi.taskmanager.domain.enums.Status;
import org.alexvsi.taskmanager.domain.specification.TaskSpecification;
import org.alexvsi.taskmanager.infra.exception.TaskNotFoundException;
import org.alexvsi.taskmanager.infra.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Should create a task with success")
    void shouldCreateTaskSuccessfully() {
        TaskRequest request = new TaskRequest(
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        TaskResponse response = new TaskResponse(task);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse result = taskService.addTask(request);

        assertEquals(response, result);
    }

    @Test
    @DisplayName("Should throw exception when updating non existing task")
    void shouldThrowExceptionWhenUpdatingNonExistingTask() {

        Long id = 99L;
        TaskRequest request = new TaskRequest(
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(id, request));
    }

    @Test
    @DisplayName("Should update task successfully")
    void shouldUpdateTaskSuccessfully() {
        Long id = 1L;
        TaskRequest updateTask = new TaskRequest(
                "i eat cement",
                "Watch Dead Poets Society, it's really good!",
                Priority.LOW,
                LocalDate.of(2027, 6, 21),
                Status.COMPLETED
        );

        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task updatedTask = new Task(
                1L,
                "i eat cement",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.COMPLETED
        );

        TaskResponse response = new TaskResponse(updatedTask);

        when(taskRepository.findById(id)).thenReturn(of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        TaskResponse result = taskService.updateTask(id, updateTask);

        assertEquals(response, result);
    }

    @Test
    @DisplayName("Should throw exception when patching non existing task")
    void shouldThrowExceptionWhenPatchingNonExistingTask() {

        Long id = 99L;
        TaskRequest request = new TaskRequest(
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.patchTask(id, request));
    }

    @Test
    @DisplayName("Should patch task title successfully")
    void shouldPatchTaskTitleSuccessfully(){
        Long id = 1L;
        TaskRequest patchTask = new TaskRequest(
                "i eat cement",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task patchedTask = new Task(
                1L,
                "i eat cement",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        TaskResponse response = new TaskResponse(patchedTask);

        when(taskRepository.findById(id)).thenReturn(of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(patchedTask);

        TaskResponse result = taskService.patchTask(id, patchTask);

        assertEquals(response, result);
    }

    @Test
    @DisplayName("Should patch task description successfully")
    void shouldPatchTaskDescriptionSuccessfully(){
        Long id = 1L;
        TaskRequest patchTask = new TaskRequest(
                "Read a book",
                "Watch Dead Poets Society, it's really good!",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task patchedTask = new Task(
                1L,
                "Read a book",
                "Watch Dead Poets Society, it's really good!",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        TaskResponse response = new TaskResponse(patchedTask);

        when(taskRepository.findById(id)).thenReturn(of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(patchedTask);

        TaskResponse result = taskService.patchTask(id, patchTask);

        assertEquals(response, result);
    }

    @Test
    @DisplayName("Should patch task priority successfully")
    void shouldPatchTaskPrioritySuccessfully(){
        Long id = 1L;
        TaskRequest patchTask = new TaskRequest(
                "Read a book",
                "How to get a job",
                Priority.LOW,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task patchedTask = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.LOW,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        TaskResponse response = new TaskResponse(patchedTask);

        when(taskRepository.findById(id)).thenReturn(of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(patchedTask);

        TaskResponse result = taskService.patchTask(id, patchTask);

        assertEquals(response, result);
    }

    @Test
    @DisplayName("Should patch task deadline successfully")
    void shouldPatchTaskDeadlineSuccessfully(){
        Long id = 1L;
        TaskRequest patchTask = new TaskRequest(
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2027, 6, 21),
                Status.PENDING
        );

        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task patchedTask = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2027, 6, 21),
                Status.PENDING
        );

        TaskResponse response = new TaskResponse(patchedTask);

        when(taskRepository.findById(id)).thenReturn(of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(patchedTask);

        TaskResponse result = taskService.patchTask(id, patchTask);

        assertEquals(response, result);
    }

    @Test
    @DisplayName("Should patch task status successfully")
    void shouldPatchTaskStatusSuccessfully() {
        Long id = 1L;
        TaskRequest patchTask = new TaskRequest(
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.COMPLETED
        );

        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task patchedTask = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.COMPLETED
        );

        TaskResponse response = new TaskResponse(patchedTask);

        when(taskRepository.findById(id)).thenReturn(of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(patchedTask);

        TaskResponse result = taskService.patchTask(id, patchTask);

        assertEquals(response, result);
    }

    @Test
    @DisplayName("Should throw exception when getting by id non existing task")
    void shouldThrowExceptionWhenGettingByIdNonExistingTask() {

        Long id = 404L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(id));
    }

    @Test
    @DisplayName("Should get task by id successfully")
    void shouldGetTaskByIdSuccessfully() {
        Long id = 1L;
        TaskRequest request = new TaskRequest(
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        TaskResponse response = new TaskResponse(task);

        when(taskRepository.findById(id)).thenReturn(of(task));

        TaskResponse result = taskService.getTask(id);

        assertEquals(response, result);
    }

    @Test
    @DisplayName("Should search tasks successfully")
    void shouldSearchTasksSuccessfully() {
        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        Task task2 = new Task(
                2L,
                "i eat cement",
                "Watch Dead Poets Society, it's really good!",
                Priority.LOW,
                LocalDate.of(2027, 6, 21),
                Status.COMPLETED
        );

        String title = null;
        Priority priority = null;
        LocalDate deadline = null;
        Status status = null;
        int page = 1;
        int size = 10;

        Specification<Task> spec = TaskSpecification.filter(title, priority, deadline, status);
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Task> listTasks = new  ArrayList<>();
        listTasks.add(task);
        listTasks.add(task2);

        Page<Task> pageTasks = new PageImpl<>(listTasks, pageRequest, size);

        List<TaskResponse> listResponse = pageTasks.map(TaskResponse::new).toList();

        when(taskRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(pageTasks);

        List<TaskResponse> result = taskService.searchTasks(page, size, title, priority, deadline, status);

        assertEquals(listResponse, result);
    }

    @Test
    @DisplayName("Should throw exception when deleting non existing task")
    void shouldThrowExceptionWhenDeletingNonExistingTask(){
        Long id = 404L;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(id));
    }

    @Test
    @DisplayName("Should delete task successfully")
    void shouldTDeleteTaskSuccessfully() {
        Long id = 1L;

        Task task = new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        );

        when(taskRepository.findById(id)).thenReturn(of(task));
        taskService.deleteTask(id);

        verify(taskRepository, times(1)).delete(task);
    }
}