package com.br.hbs.task.model;

import com.br.hbs.task.enums.PriorityTask;
import com.br.hbs.task.enums.StatusTask;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TasksModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private StatusTask statusTask;
    @NotNull
    private LocalDate dateTask;
    @NotNull
    private String nameTask;
    @NotNull
    private PriorityTask priorityTask;
    @NotNull
    @Column(name = "user_id")
    private Long user;
}
