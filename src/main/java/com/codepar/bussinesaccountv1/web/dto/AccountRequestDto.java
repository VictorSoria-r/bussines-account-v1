package com.codepar.bussinesaccountv1.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDto {
    private Long clientId;
    private String accountNumber;
    private String accountTypeCode;
    private BigDecimal initialBalance;
    private boolean status;
}
