package com.codepar.bussinesaccountv1.web.rest;

import com.codepar.bussinesaccountv1.domains.movements.domain.model.Movement;
import com.codepar.bussinesaccountv1.web.dto.AccountRequestDto;
import com.codepar.bussinesaccountv1.web.dto.CreateAccountResponseDto;
import com.codepar.bussinesaccountv1.web.dto.MovementRequestDto;
import com.codepar.bussinesaccountv1.web.service.AccountApplicationService;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/movement")
public class MovementController {

    private final AccountApplicationService accountApplicationService;
    @PostMapping
    public Single<ResponseEntity<Movement>> createMovement(
            @RequestBody MovementRequestDto request) {
        return accountApplicationService.saveMovement(request)
                .map(movement -> {
                    URI location = URI.create("api/movement/"+ movement.getMovementId());
                    return ResponseEntity.created(location)
                            .body(movement);
                });
    }
}
