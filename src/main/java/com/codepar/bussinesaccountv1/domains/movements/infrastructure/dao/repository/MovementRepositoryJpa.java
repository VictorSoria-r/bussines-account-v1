package com.codepar.bussinesaccountv1.domains.movements.infrastructure.dao.repository;

import com.codepar.bussinesaccountv1.domains.movements.infrastructure.dao.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepositoryJpa extends JpaRepository<MovementEntity,Long> {

}
