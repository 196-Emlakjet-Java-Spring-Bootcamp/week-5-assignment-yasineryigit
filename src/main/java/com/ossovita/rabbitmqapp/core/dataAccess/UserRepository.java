package com.ossovita.rabbitmqapp.core.dataAccess;

import com.ossovita.rabbitmqapp.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmail(String userEmail);

    User getByUserEmail(String userEmail);

    @Query(value = "SELECT * FROM users ORDER BY random() LIMIT 1", nativeQuery = true)
    User getRandomUser();

}
