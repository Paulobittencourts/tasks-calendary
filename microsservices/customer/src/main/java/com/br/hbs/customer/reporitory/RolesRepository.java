package com.br.hbs.customer.reporitory;

import com.br.hbs.customer.model.RolesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RolesModel, Long> {
}
