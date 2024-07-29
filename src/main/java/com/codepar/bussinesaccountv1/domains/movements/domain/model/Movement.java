package com.codepar.bussinesaccountv1.domains.movements.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Movement {

    private Long movementId;
    private BigDecimal value;
    private BigDecimal balance;
    private String name;
    private String accountNumber;
}
