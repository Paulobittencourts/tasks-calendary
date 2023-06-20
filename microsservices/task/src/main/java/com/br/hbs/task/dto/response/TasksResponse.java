package com.br.hbs.task.dto.response;


import com.br.hbs.task.enums.PriorityTask;
import com.br.hbs.task.enums.StatusTask;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TasksResponse {

    private Long id;
    private String nameTask;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateTask;
    private StatusTask statusTask;
    private PriorityTask priorityTask;
    private Long user;
}
