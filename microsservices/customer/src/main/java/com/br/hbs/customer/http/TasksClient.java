package com.br.hbs.customer.http;

import com.br.hbs.customer.dto.ItemsTask;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("tasks-ms")
public interface TasksClient {
    @GetMapping("/tasks/users/{id}")
    List<ItemsTask> getByIDTask(@PathVariable Long id);
    @PutMapping("/tasks/{tasksId}/status")
    void updatedStatus(@RequestParam String tasks, @PathVariable Long tasksId);

}
