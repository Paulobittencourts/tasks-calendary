package com.br.hbs.task.services;

import com.br.hbs.task.dto.request.TasksRequest;
import com.br.hbs.task.dto.response.TasksResponse;
import com.br.hbs.task.enums.StatusTask;
import com.br.hbs.task.model.TasksModel;
import com.br.hbs.task.respositories.TaskRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public Page<TasksResponse> getAllTask(Pageable pageable) {

        List<TasksResponse> getTasks = taskRepository.findAll(pageable)
                .stream()
                .map(tasks -> modelMapper.map(tasks, TasksResponse.class))
                .toList();

        return new PageImpl<>(getTasks);
    }

    public TasksResponse getTasksByID(Long id) {
        TasksModel tasksID = taskRepository.findById(id)
                .orElseThrow(EntityExistsException::new);
        return modelMapper.map(tasksID, TasksResponse.class);
    }

    public void createTasks(TasksRequest tasks, Long id) {
        TasksModel createTasks = modelMapper.map(tasks, TasksModel.class);
        createTasks.setUser(id);
        taskRepository.save(createTasks);
    }

    public void updatedTasks(TasksRequest tasks, Long id) {
        TasksModel tasksID = taskRepository.findById(id)
                .orElseThrow(EntityExistsException::new);
        tasksID.setNameTask(tasks.getNameTask());
        tasksID.setDateTask(tasks.getDateTask());
        tasksID.setStatusTask(tasks.getStatusTask());
        tasksID.setPriorityTask(tasks.getPriorityTask());
        taskRepository.save(tasksID);
    }

    public void updatedStatus(Long tasksId, String tasks) {
        TasksModel tasksID = taskRepository.findById(tasksId)
                .orElseThrow(EntityExistsException::new);
        tasksID.setStatusTask(StatusTask.valueOf(tasks));
        taskRepository.save(tasksID);
    }

    public List<TasksResponse> getTasksIDUser(Long id) {
        return taskRepository.findByUser(id)
                .stream()
                .map(tasks -> modelMapper.map(tasks, TasksResponse.class))
                .toList();
    }

    public void deleteTask(Long id) {
        TasksModel tasksID = taskRepository.findById(id)
                .orElseThrow(EntityExistsException::new);
        taskRepository.delete(tasksID);
    }

    public boolean isDateValid(LocalDate date){
        LocalDate date1 = LocalDate.now();
        return date.isBefore(date1);
    }


}
