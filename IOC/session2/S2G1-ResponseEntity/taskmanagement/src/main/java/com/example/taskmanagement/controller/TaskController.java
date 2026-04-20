package com.example.taskmanagement.controller;


import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) String search) {
        List<Task> allTasks = taskService.findAllTasks();

        if (search != null && !search.isEmpty()) {
            List<Task> filteredTasks = allTasks.stream()
                    .filter(task -> task.getTitle().toLowerCase().contains(search.toLowerCase()))
                    .toList();
            return ResponseEntity.ok(filteredTasks); // Trả về 200 OK kèm danh sách đã lọc
        }

        // Nếu không có search, trả về toàn bộ
        return ResponseEntity.status(200).body(allTasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable int id) {
        Task task = taskService.findTaskById(id);
        if(task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }
}
