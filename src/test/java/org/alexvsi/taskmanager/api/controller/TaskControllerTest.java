package org.alexvsi.taskmanager.api.controller;

import org.alexvsi.taskmanager.application.dto.TaskRequest;
import org.alexvsi.taskmanager.application.dto.TaskResponse;
import org.alexvsi.taskmanager.domain.entity.Task;
import org.alexvsi.taskmanager.domain.enums.Priority;
import org.alexvsi.taskmanager.domain.enums.Status;
import org.alexvsi.taskmanager.domain.service.TaskService;
import org.alexvsi.taskmanager.infra.exception.TaskNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    @DisplayName("Should return 201 when creating task")
    void shouldReturn201WhenCreatingTask() throws Exception {
        TaskResponse response = new TaskResponse(new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        ));

        when(taskService.addTask(any(TaskRequest.class))).thenReturn(response);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Read a book",
                                    "description": "How to get a job",
                                    "priority": "HIGH",
                                    "deadline": "2026-05-20",
                                    "taskStatus": "PENDING"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.task.id").value("1"))
                .andExpect(jsonPath("$.task.title").value("Read a book"))
                .andExpect(jsonPath("$.task.description").value("How to get a job"))
                .andExpect(jsonPath("$.task.priority").value("HIGH"))
                .andExpect(jsonPath("$.task.deadline").value("2026-05-20"))
                .andExpect(jsonPath("$.task.taskStatus").value("PENDING"));
    }

    @Test
    @DisplayName("Should return 400 when title is blank")
    void shouldReturn400WhenTitleIsBlank() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": " ",
                                    "description": "How to get a job",
                                    "priority": "HIGH",
                                    "deadline": "2026-05-20",
                                    "taskStatus": "PENDING"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when deadline is past")
    void shouldReturn400WhenDeadlineIsPast() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Read a book",
                                    "description": "How to get a job",
                                    "priority": "HIGH",
                                    "deadline": "1995-05-23",
                                    "taskStatus": "PENDING"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 200 when update task")
    void shouldReturn200WhenUpdateTask() throws Exception {
        TaskResponse response = new TaskResponse(new Task(
                1L,
                "I eat cement",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        ));

        when(taskService.updateTask(anyLong(), any(TaskRequest.class))).thenReturn(response);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "I eat cement",
                                    "description": "How to get a job",
                                    "priority": "HIGH",
                                    "deadline": "2026-05-20",
                                    "taskStatus": "PENDING"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task.id").value("1"))
                .andExpect(jsonPath("$.task.title").value("I eat cement"))
                .andExpect(jsonPath("$.task.description").value("How to get a job"))
                .andExpect(jsonPath("$.task.priority").value("HIGH"))
                .andExpect(jsonPath("$.task.deadline").value("2026-05-20"))
                .andExpect(jsonPath("$.task.taskStatus").value("PENDING"));
    }

    @Test
    @DisplayName("Should return 404 when update task not found")
    void shouldReturn404WhenUpdateTaskNotFound() throws Exception {
        when(taskService.updateTask(anyLong(), any(TaskRequest.class))).thenThrow(
                new TaskNotFoundException("Task Not Found")
        );

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "I eat cement",
                                    "description": "How to get a job",
                                    "priority": "HIGH",
                                    "deadline": "2026-05-20",
                                    "taskStatus": "PENDING"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 200 when patch task")
    void shouldReturn200WhenPatchTask() throws Exception {
        TaskResponse response = new TaskResponse(new Task(
                1L,
                "I eat cement",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        ));

        when(taskService.patchTask(anyLong(), any(TaskRequest.class))).thenReturn(response);

        mockMvc.perform(patch("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "I eat cement",
                                    "description": "How to get a job",
                                    "priority": "HIGH",
                                    "deadline": "2026-05-20",
                                    "taskStatus": "PENDING"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task.id").value("1"))
                .andExpect(jsonPath("$.task.title").value("I eat cement"))
                .andExpect(jsonPath("$.task.description").value("How to get a job"))
                .andExpect(jsonPath("$.task.priority").value("HIGH"))
                .andExpect(jsonPath("$.task.deadline").value("2026-05-20"))
                .andExpect(jsonPath("$.task.taskStatus").value("PENDING"));
    }

    @Test
    @DisplayName("Should return 404 when patch task not found")
    void shouldReturn404WhenPatchTaskNotFound() throws Exception {
        when(taskService.patchTask(anyLong(), any(TaskRequest.class))).thenThrow(
                new TaskNotFoundException("Task Not Found")
        );

        mockMvc.perform(patch("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "I eat cement",
                                    "description": "How to get a job",
                                    "priority": "HIGH",
                                    "deadline": "2026-05-20",
                                    "taskStatus": "PENDING"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 200 when get task")
    void shouldReturn200WhenGetTask() throws Exception {
        TaskResponse response = new TaskResponse(new Task(
                1L,
                "Read a book",
                "How to get a job",
                Priority.HIGH,
                LocalDate.of(2026, 5, 20),
                Status.PENDING
        ));

        when(taskService.getTask(anyLong())).thenReturn(response);

        mockMvc.perform(get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task.id").value("1"))
                .andExpect(jsonPath("$.task.title").value("Read a book"))
                .andExpect(jsonPath("$.task.description").value("How to get a job"))
                .andExpect(jsonPath("$.task.priority").value("HIGH"))
                .andExpect(jsonPath("$.task.deadline").value("2026-05-20"))
                .andExpect(jsonPath("$.task.taskStatus").value("PENDING"));
    }

    @Test
    @DisplayName("Should return 404 when get task not found")
    void shouldReturn404WhenGetTaskNotFound() throws Exception {
        when(taskService.getTask(anyLong())).thenThrow(
                new TaskNotFoundException("Task Not Found")
        );

        mockMvc.perform(get("/tasks/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 200 when search tasks")
    void shouldReturn200WhenSearchTasks() throws Exception {
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

        int page = 1;
        int size = 10;

        PageRequest pageRequest = PageRequest.of(page, size);

        List<Task> listTasks = List.of(task, task2);

        Page<Task> pageTasks = new PageImpl<>(listTasks, pageRequest, size);

        List<TaskResponse> listResponse = pageTasks.map(TaskResponse::new).toList();

        when(taskService.searchTasks(
                anyInt(),
                anyInt(),
                isNull(),
                isNull(),
                isNull(),
                isNull()
        )).thenReturn(listResponse);

        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Should return 204 when delete task")
    void shouldReturn204WhenDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(anyLong());

        mockMvc.perform(delete("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404 when delete task not found")
    void shouldReturn404WhenDeleteTaskNotFound() throws Exception {
        doThrow(new TaskNotFoundException("Task not found")).when(taskService).deleteTask(anyLong());

        mockMvc.perform(delete("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isNotFound());
    }
}