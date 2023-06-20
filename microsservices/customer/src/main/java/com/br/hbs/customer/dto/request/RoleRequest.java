package com.br.hbs.customer.dto.request;

import com.br.hbs.customer.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private Long roleId;
    private RoleEnum roleName;
}
