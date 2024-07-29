package com.codepar.bussinesaccountv1.domains.client.infrastructure.dao.repository;

import com.codepar.bussinesaccountv1.domains.client.infrastructure.dao.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepositoryJpa extends JpaRepository<ClientEntity,Long> {
    Optional<ClientEntity> findByIdentification(String identification);

}
