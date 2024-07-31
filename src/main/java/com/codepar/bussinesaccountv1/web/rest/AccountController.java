package com.codepar.bussinesaccountv1.web.rest;

import com.codepar.bussinesaccountv1.domains.account.domain.model.Account;
import com.codepar.bussinesaccountv1.domains.account.domain.model.AccountState;
import com.codepar.bussinesaccountv1.web.dto.AccountRequestDto;
import com.codepar.bussinesaccountv1.web.dto.CreateAccountResponseDto;
import com.codepar.bussinesaccountv1.web.service.AccountApplicationService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/account")
public class AccountController {

    private final AccountApplicationService accountApplicationService;
    @PostMapping
    public Single<ResponseEntity<CreateAccountResponseDto>> createAccount(
            @RequestBody AccountRequestDto request) {
        return accountApplicationService.saveAccount(request)
                .map(accountResponse -> {
                    URI location = URI.create("api/account/"+ accountResponse.getAccountId());
                    return ResponseEntity.created(location)
                            .body(accountResponse);
                });
    }

    @GetMapping("/{id}")
    public Single<ResponseEntity<Account>> getAccountById(
            @PathVariable Long id) {
        return accountApplicationService.getAccountById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build())
                .doOnError(System.out::println);
    }

    @DeleteMapping("/{id}")
    public Completable deleteAccount(
            @PathVariable Long id) {
        return accountApplicationService.deleteAccount(id)
                .andThen(Completable.fromAction(
                        () -> ResponseEntity.noContent().build()));
    }
    @GetMapping(value = "/state",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Maybe<ResponseEntity<Flowable<AccountState>>> getStateAccounts(@RequestParam String identificationClient,
                                                                         @RequestParam String startDate,
                                                                         @RequestParam String endDate) {

        return accountApplicationService.getStateAccountByIdentificationClientAndRangeDate(
                        identificationClient,startDate,endDate)
                .switchIfEmpty(Flowable.fromAction(() ->
                        ResponseEntity.noContent().build()))
                .to(response ->Maybe.just(ResponseEntity.ok(response)))
                .doOnError(System.out::println);
    }
}
