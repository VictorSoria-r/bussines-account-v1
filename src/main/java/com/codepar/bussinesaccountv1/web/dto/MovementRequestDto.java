package com.codepar.bussinesaccountv1.web.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class MovementRequestDto implements Serializable {

    private String accountNumber;
    private String typeMovement;
    private BigDecimal value;
}
