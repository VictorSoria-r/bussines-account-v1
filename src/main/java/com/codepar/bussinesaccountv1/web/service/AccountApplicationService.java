package com.codepar.bussinesaccountv1.web.service;

import com.codepar.bussinesaccountv1.domains.account.domain.model.Account;
import com.codepar.bussinesaccountv1.domains.account.domain.model.AccountState;
import com.codepar.bussinesaccountv1.domains.movements.domain.model.Movement;
import com.codepar.bussinesaccountv1.web.dto.AccountRequestDto;
import com.codepar.bussinesaccountv1.web.dto.CreateAccountResponseDto;
import com.codepar.bussinesaccountv1.web.dto.MovementRequestDto;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface AccountApplicationService {
    Single<CreateAccountResponseDto> saveAccount(AccountRequestDto requestDto);

    Maybe<Account> getAccountById(Long id);

    Completable deleteAccount(Long id);

    Single<Movement> saveMovement(MovementRequestDto requestDto);


    Flowable<AccountState> getStateAccountByIdentificationClientAndRangeDate(String identification,
                                                                 String startDate,
                                                                 String localDate);
}
