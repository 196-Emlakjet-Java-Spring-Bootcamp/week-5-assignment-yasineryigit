package com.ossovita.rabbitmqapp.business.abstracts;

import com.ossovita.rabbitmqapp.core.entities.User;

import java.util.List;

public interface UserService {

    public User save(User user);

    public List<User> getAllUsers();

    User getByUserEmail(String userEmail);


    User updateUserLockedStatus(long userPk, boolean status);

    void createDummyUserRequest(int piece);

    void createDummyUser(int piece);
}
