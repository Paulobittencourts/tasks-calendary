package com.br.hbs.task.controllers;

import com.br.hbs.task.dto.TasksDTO;
import com.br.hbs.task.enums.StatusTask;
import com.br.hbs.task.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public Page<TasksDTO> getTasks(@PageableDefault(page = 0, size = 5) Pageable pageable){
        return taskService.getAllTask(pageable);
    }

    @GetMapping("/{tasksId}")
    public TasksDTO getTasksID(@PathVariable @Valid @NotNull Long tasksId){
        return taskService.getTasksByID(tasksId);
    }

    @GetMapping("/users/{id}")
    public List<TasksDTO> getTasksIDUser(@PathVariable Long id){
        return taskService.getTasksIDUser(id);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Object> creatingTasks(@RequestBody @Valid @NotNull TasksDTO tasks, @PathVariable Long userId){
        if (taskService.isDateValid(tasks.getDateTask())){
            throw new DateTimeException("Date cannot be less than current");
        }
        taskService.createTasks(tasks, userId);
        return ResponseEntity.ok().body("Creating with success");
    }

    @PutMapping("/{tasksId}")
    public ResponseEntity<Object> updatingTasks(@RequestBody @Valid @NotNull TasksDTO tasks, @PathVariable Long tasksId){
        taskService.updatedTasks(tasks, tasksId);
        return ResponseEntity.ok().body("Updating with success");
    }

    @PutMapping("/{tasksId}/status")
    public ResponseEntity<Object> updatingStatusTasks(@RequestParam String tasks , @PathVariable Long tasksId){
        taskService.updatedStatus(tasksId, tasks);
        return ResponseEntity.ok().body("Updating with success");
    }

    @DeleteMapping("/{tasksId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTasks(@PathVariable @Valid Long tasksId){
        taskService.deleteTask(tasksId);
    }

}
