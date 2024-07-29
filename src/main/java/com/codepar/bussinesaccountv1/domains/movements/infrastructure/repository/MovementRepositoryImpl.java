package com.codepar.bussinesaccountv1.domains.movements.infrastructure.repository;

import com.codepar.bussinesaccountv1.domains.movements.domain.repository.MovementRepository;
import com.codepar.bussinesaccountv1.domains.movements.infrastructure.dao.entity.MovementEntity;
import com.codepar.bussinesaccountv1.domains.movements.infrastructure.dao.repository.MovementRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Repository
public class MovementRepositoryImpl implements MovementRepository {

    private final MovementRepositoryJpa movementRepositoryJpa;

    @Transactional
    @Override
    public MovementEntity saveMovement(MovementEntity movementEntity) {
        return movementRepositoryJpa.save(movementEntity);
    }
}
