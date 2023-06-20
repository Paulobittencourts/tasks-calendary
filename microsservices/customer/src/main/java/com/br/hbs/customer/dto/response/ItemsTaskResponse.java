package com.br.hbs.customer.dto.response;

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
public class ItemsTaskResponse {

    private Long id;
    private String nameTask;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate dateTask;
    private String statusTask;
    private String priorityTask;
    private Long user;
}
