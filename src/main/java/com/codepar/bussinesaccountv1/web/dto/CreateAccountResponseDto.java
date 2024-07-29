package com.codepar.bussinesaccountv1.web.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@Builder
public class CreateAccountResponseDto implements Serializable {
    private Long accountId;
}
