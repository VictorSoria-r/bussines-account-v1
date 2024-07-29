package com.codepar.bussinesaccountv1.domains.account.domain.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AccountState implements Serializable {
    private String clientName;
    private String accountNumber;
    private String typeAccount;
    private List<MovementState> movementStateList;


    @Data
    @Builder
    public static class MovementState {
        private LocalDate transactionDate;
        private BigDecimal initialBalance;
        private BigDecimal movementAmount;
        private BigDecimal availableBalance;
    }
}
