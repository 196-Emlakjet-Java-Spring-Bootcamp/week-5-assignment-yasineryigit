package com.ossovita.rabbitmqapp.security.auth;

import com.ossovita.rabbitmqapp.core.entities.vm.UserVM;
import lombok.Data;

@Data
public class AuthResponse<T> {

    private String token;

    private UserVM user;

    private T additionalData;


}
