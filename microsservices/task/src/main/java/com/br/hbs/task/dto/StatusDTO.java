package com.br.hbs.task.dto;


import com.br.hbs.task.enums.StatusTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {
    private StatusTask statusTask;
}
