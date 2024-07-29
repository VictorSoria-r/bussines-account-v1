package com.codepar.bussinesaccountv1.domains.client.domain.repository;

import com.codepar.bussinesaccountv1.domains.client.infrastructure.dao.entity.ClientEntity;

public interface ClientRepository {

    ClientEntity getClientById(Long id);

    ClientEntity findByIdentification(String identification);

}
