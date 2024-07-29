package com.codepar.bussinesaccountv1.domains.account.infrastructure.repository;

import com.codepar.bussinesaccountv1.domains.account.domain.repository.AccountRepository;
import com.codepar.bussinesaccountv1.domains.account.infrastructure.dao.entity.AccountEntity;
import com.codepar.bussinesaccountv1.domains.account.infrastructure.dao.repository.AccountRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountRepositoryJpa accountRepositoryJpa;

    @Transactional(readOnly = true)
    @Override
    public AccountEntity findAccountById(Long id) {
        return accountRepositoryJpa.findById(id)
                .orElse(null);
    }
    @Transactional(readOnly = true)
    @Override
    public AccountEntity findAccountByAccountNumber(String accountNumber) {
        return accountRepositoryJpa.findAccountEntityByAccountNumber(accountNumber)
                .orElse(null);
    }
    @Transactional
    @Override
    public Long saveAccount(AccountEntity accountEntity) {
        return accountRepositoryJpa.save(accountEntity)
                .getId();
    }
    @Transactional
    @Override
    public AccountEntity updateAccount(AccountEntity accountEntity) {
        return accountRepositoryJpa.save(accountEntity);
    }

    @Transactional
    @Override
    public void deleteAccount(AccountEntity accountEntity) {
        accountRepositoryJpa.delete(accountEntity);
    }

}
