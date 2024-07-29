package com.codepar.bussinesaccountv1.domains.movements.domain.repository;


import com.codepar.bussinesaccountv1.domains.movements.infrastructure.dao.entity.MovementEntity;

public interface MovementRepository {

    MovementEntity saveMovement(MovementEntity accountEntity);
}
