package com.codepar.bussinesaccountv1.domains.account.domain.repository;

import com.codepar.bussinesaccountv1.domains.account.infrastructure.dao.entity.AccountEntity;

public interface AccountRepository {

    AccountEntity findAccountById(Long id);

    AccountEntity findAccountByAccountNumber(String accountNumber);

    Long saveAccount(AccountEntity accountEntity);

    AccountEntity updateAccount(AccountEntity accountEntity);

    void deleteAccount(AccountEntity accountEntity);

}
