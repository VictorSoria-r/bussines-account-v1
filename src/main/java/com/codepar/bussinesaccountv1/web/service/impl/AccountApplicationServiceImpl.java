package com.codepar.bussinesaccountv1.web.service.impl;

import com.codepar.bussinesaccountv1.core.exception.CoderPadException;
import com.codepar.bussinesaccountv1.domains.account.domain.model.Account;
import com.codepar.bussinesaccountv1.domains.account.domain.model.AccountState;
import com.codepar.bussinesaccountv1.domains.account.domain.repository.AccountRepository;
import com.codepar.bussinesaccountv1.domains.account.domain.type.ProductType;
import com.codepar.bussinesaccountv1.domains.account.infrastructure.dao.entity.AccountEntity;
import com.codepar.bussinesaccountv1.domains.client.domain.repository.ClientRepository;
import com.codepar.bussinesaccountv1.domains.client.infrastructure.dao.entity.ClientEntity;
import com.codepar.bussinesaccountv1.domains.movements.domain.model.Movement;
import com.codepar.bussinesaccountv1.domains.movements.domain.repository.MovementRepository;
import com.codepar.bussinesaccountv1.domains.movements.infrastructure.dao.entity.MovementEntity;
import com.codepar.bussinesaccountv1.web.dto.AccountRequestDto;
import com.codepar.bussinesaccountv1.web.dto.CreateAccountResponseDto;
import com.codepar.bussinesaccountv1.web.dto.MovementRequestDto;
import com.codepar.bussinesaccountv1.web.service.AccountApplicationService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountApplicationServiceImpl implements AccountApplicationService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Single<CreateAccountResponseDto> saveAccount(AccountRequestDto requestDto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountType(ProductType.fromCode(
                requestDto.getAccountTypeCode()).getShortName());
        accountEntity.setAccountNumber(requestDto.getAccountNumber());
        accountEntity.setInitialBalance(requestDto.getInitialBalance());
        accountEntity.setStatus(true);

        return Maybe.fromCallable(() ->
                    clientRepository.getClientById(requestDto.getClientId()))
                .switchIfEmpty(Single.error(CoderPadException.builder()
                        .shortMessage("El usuario no se encuentra registrado")
                        .status(HttpStatus.CONFLICT)
                        .build()))
                .map(clientEntity -> {
                    if (clientEntity.getAccountEntityList()
                            .stream()
                            .anyMatch(account -> requestDto.getAccountNumber()
                                    .equals(account.getAccountNumber()))) {
                        throw CoderPadException.builder()
                                .shortMessage("Numero de cuenta para este usuario ya se encuentra registrado")
                                .status(HttpStatus.CONFLICT)
                                .build();
                    }
                    accountEntity.setClient(clientEntity);
                    return CreateAccountResponseDto.builder()
                            .accountId(accountRepository.saveAccount(accountEntity))
                            .build();
                });
    }

    @Override
    public Maybe<Account> getAccountById(Long id) {
        return Maybe.fromCallable(() ->
                accountRepository.findAccountById(id))
                .switchIfEmpty(Maybe.empty())
                .map(accountEntity -> Account.builder()
                        .accountNumber(accountEntity.getAccountNumber())
                        .accountType(accountEntity.getAccountType())
                        .clientName(accountEntity.getClient().getName())
                        .initialBalance(accountEntity.getInitialBalance())
                        .status(accountEntity.isStatus())
                        .build());
    }

    @Override
    public Completable deleteAccount(Long id) {
        return Maybe.fromCallable(() -> accountRepository.findAccountById(id))
                .switchIfEmpty(Single.error(CoderPadException.builder()
                                .shortMessage("Cuenta no existe")
                                .status(HttpStatus.CONFLICT)
                        .build()))
                .flatMapCompletable(accountEntity -> {
                    accountRepository.deleteAccount(accountEntity);
                    return Completable.complete();
                })
                .doOnError(Completable::error);

    }

    @Override
    public Single<Movement> saveMovement(MovementRequestDto requestDto) {
        MovementEntity movementEntity = new MovementEntity();
        movementEntity.setAmount(requestDto.getValue());
        movementEntity.setTransactionDate(LocalDate.now());
        return Maybe.fromCallable(() ->
                accountRepository.findAccountByAccountNumber(requestDto.getAccountNumber()))
                .switchIfEmpty(Single.error(CoderPadException.builder()
                        .shortMessage("El numero de cuenta no se encuentra registrado")
                        .status(HttpStatus.CONFLICT)
                        .build()))
                .map(accountEntity -> {
                    BigDecimal initialBalance;
                    if (accountEntity.getMovementEntities().size() > 0) {
                        initialBalance = accountEntity.getMovementEntities()
                                .get(accountEntity.getMovementEntities().size() -1)
                                .getBalance();
                    } else {
                        initialBalance = accountEntity.getInitialBalance();
                    }
                    if (initialBalance.add(requestDto.getValue()).compareTo(BigDecimal.ZERO) < 0) {
                        throw CoderPadException.builder()
                                .shortMessage("La cuenta tiene saldo insuficiente")
                                .status(HttpStatus.CONFLICT)
                                .build();
                    }
                    movementEntity.setBalance(initialBalance.add(requestDto.getValue()));
                    movementEntity.setAccount(accountEntity);
                    return movementRepository.saveMovement(movementEntity);

                })
                .map(entity -> Movement.builder()
                        .movementId(entity.getId())
                        .name(entity.getAccount().getClient().getName())
                        .accountNumber(entity.getAccount().getAccountNumber())
                        .value(entity.getAmount())
                        .balance(entity.getBalance())
                        .build());
    }

    @Override
    public Flowable<AccountState> getStateAccountByIdentificationClientAndRangeDate(String identificationClient,
                                                                        String startDate,
                                                                        String endDate) {
        LocalDate start = LocalDate.parse(startDate,formatter);
        LocalDate end = LocalDate.parse(endDate,formatter);
        return Maybe.fromCallable(() ->
                        clientRepository.findByIdentification(identificationClient))
                .switchIfEmpty(Single.error(CoderPadException.builder()
                        .shortMessage("El usuario no se encuentra registrado")
                        .status(HttpStatus.CONFLICT)
                        .build()))
                .flattenAsFlowable(ClientEntity::getAccountEntityList)
                .map(accountEntity -> {
                    List<MovementEntity> movementEntities = accountEntity.getMovementEntities()
                            .stream()
                            .filter(movementEntity ->
                                    (movementEntity.getTransactionDate().isAfter(start) || movementEntity.getTransactionDate().equals(start))
                                    && (movementEntity.getTransactionDate().isBefore(end) || movementEntity.getTransactionDate().equals(end)))
                            .toList();
                    return AccountState.builder()
                            .clientName(accountEntity.getClient().getName())
                            .accountNumber(accountEntity.getAccountNumber())
                            .typeAccount(accountEntity.getAccountType())
                            .movementStateList(movementEntities
                                    .stream()
                                    .map(movementEntity -> AccountState.MovementState.builder()
                                            .transactionDate(movementEntity.getTransactionDate())
                                            .initialBalance(movementEntity.getBalance()
                                                    .subtract(movementEntity.getAmount()))
                                            .movementAmount(movementEntity.getAmount())
                                            .availableBalance(movementEntity.getBalance())
                                            .build())
                                    .toList()
                            )
                            .build();
                });

    }
}
