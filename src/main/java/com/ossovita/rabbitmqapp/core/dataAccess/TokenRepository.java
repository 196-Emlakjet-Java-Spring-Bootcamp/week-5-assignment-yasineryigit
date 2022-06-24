package com.ossovita.rabbitmqapp.core.dataAccess;

import com.ossovita.rabbitmqapp.security.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    Token findTokenByToken(String token);

    void deleteTokensByUser_UserPk(long userPk);

    List<Token> findTokensByUser_UserPk(long userPk);

}
