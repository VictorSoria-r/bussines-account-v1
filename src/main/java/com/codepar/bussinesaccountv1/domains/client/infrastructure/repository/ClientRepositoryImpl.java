package com.codepar.bussinesaccountv1.domains.client.infrastructure.repository;

import com.codepar.bussinesaccountv1.domains.client.domain.repository.ClientRepository;
import com.codepar.bussinesaccountv1.domains.client.infrastructure.dao.entity.ClientEntity;
import com.codepar.bussinesaccountv1.domains.client.infrastructure.dao.repository.ClientRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientRepositoryJpa clientRepositoryJpa;

    @Transactional(readOnly = true)
    @Override
    public ClientEntity getClientById(Long id) {
        return clientRepositoryJpa.findById(id)
                .orElse(null);
    }
    @Transactional(readOnly = true)
    @Override
    public ClientEntity findByIdentification(String identification) {
        return clientRepositoryJpa.findByIdentification(identification)
                .orElse(null);
    }

}
