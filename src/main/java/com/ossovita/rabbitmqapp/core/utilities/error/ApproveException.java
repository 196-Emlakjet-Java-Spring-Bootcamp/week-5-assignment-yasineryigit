package com.ossovita.rabbitmqapp.core.utilities.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ApproveException extends RuntimeException {

    public ApproveException(String message) {
        super(message);
    }
}
