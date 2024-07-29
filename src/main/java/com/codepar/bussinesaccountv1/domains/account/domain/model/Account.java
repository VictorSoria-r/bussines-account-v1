package com.codepar.bussinesaccountv1.domains.account.domain.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class Account implements Serializable {

    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private String clientName;
    private boolean status;
}
