package com.codepar.bussinesaccountv1.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CoderPadException extends RuntimeException {

    private HttpStatus status;
    private String shortMessage;

    public CoderPadException(HttpStatus httpStatus, String message) {
        super(message);
        this.status = httpStatus;
        this.shortMessage = message;
    }

    public static BussinesCoderPadExceptionBuilder builder() {
        return new BussinesCoderPadExceptionBuilder();
    }

    public static class BussinesCoderPadExceptionBuilder {
        private HttpStatus status;
        private String shortMessage;


        public BussinesCoderPadExceptionBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public BussinesCoderPadExceptionBuilder shortMessage(String shortMessage) {
            this.shortMessage = shortMessage;
            return this;
        }

        public CoderPadException build() {
            return new CoderPadException(this.status,this.shortMessage);
        }
    }
}
