package com.br.hbs.customer.dto;

import com.br.hbs.customer.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long roleId;
    private RoleEnum roleName;
}
