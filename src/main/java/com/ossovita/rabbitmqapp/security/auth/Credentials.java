package com.ossovita.rabbitmqapp.security.auth;

import lombok.Data;

@Data
public class Credentials {

    private String userEmail;

    private String userPassword;


}
