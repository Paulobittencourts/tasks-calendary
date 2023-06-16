package com.br.hbs.task.dto;

import com.br.hbs.task.enums.PriorityTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriorityTaskDTO {

    private PriorityTask priorityTask;
}
