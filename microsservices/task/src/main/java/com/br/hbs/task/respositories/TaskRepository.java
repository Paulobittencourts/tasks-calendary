package com.br.hbs.task.respositories;

import com.br.hbs.task.model.TasksModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TasksModel, Long>{

    List<TasksModel> findByUser(Long id);
}
