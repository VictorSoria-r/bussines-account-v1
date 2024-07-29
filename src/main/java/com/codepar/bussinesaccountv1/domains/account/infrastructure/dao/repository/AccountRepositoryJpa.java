package com.codepar.bussinesaccountv1.domains.account.infrastructure.dao.repository;

import com.codepar.bussinesaccountv1.domains.account.infrastructure.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountRepositoryJpa  extends JpaRepository<AccountEntity,Long> {

    Optional<AccountEntity> findAccountEntityByAccountNumber(String accountNumber);
}
