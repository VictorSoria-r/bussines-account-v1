package com.codepar.bussinesaccountv1.core.advice;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ErrorResponse implements Serializable {
    private String message;

    public ErrorResponse(String message) {
        this.setMessage(message);
    }
    public ErrorResponse() {

    }
}
