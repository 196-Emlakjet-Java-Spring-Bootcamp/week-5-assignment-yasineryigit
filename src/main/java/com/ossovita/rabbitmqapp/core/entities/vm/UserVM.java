package com.ossovita.rabbitmqapp.core.entities.vm;

import com.ossovita.rabbitmqapp.core.entities.User;
import lombok.Data;

@Data
public class UserVM {

    private long userPk;

    private String userEmail;

    private String userFirstName;

    private String userLastName;


    public UserVM(User user) {
        this.setUserPk(user.getUserPk());
        this.setUserEmail(user.getUserEmail());
        this.setUserFirstName(user.getUserFirstName());
        this.setUserLastName(user.getUserLastName());
    }
}