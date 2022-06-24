package com.ossovita.rabbitmqapp.security.auth;


import com.ossovita.rabbitmqapp.core.dataAccess.TokenRepository;
import com.ossovita.rabbitmqapp.core.dataAccess.UserRepository;
import com.ossovita.rabbitmqapp.core.entities.User;
import com.ossovita.rabbitmqapp.core.entities.vm.UserVM;
import com.ossovita.rabbitmqapp.core.utilities.error.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AuthService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    TokenRepository tokenRepository;

    public AuthResponse<Object> authenticateUser(Credentials credentials) {//customer girişi için
        User inDB = checkUserFromDB(credentials);

        AuthResponse<Object> response = new AuthResponse<>();
        if (inDB.isAccountNonLocked()) {

            UserVM userVM = new UserVM(inDB);
            String token = generateRandomToken();
            Token tokenEntity = new Token();
            tokenEntity.setToken(token);
            tokenEntity.setUser(inDB);
            tokenRepository.save(tokenEntity);
            response.setUser(userVM);
            response.setToken(token);

        } else {
            throw new AuthException("Your account has been locked. Please contact with our support team.");
        }

        return response;
    }


    public User checkUserFromDB(Credentials credentials) {

        User inDB = userRepository.findByUserEmail(credentials.getUserEmail());
        if (inDB == null) {
            throw new AuthException("Please check your email and password");//bu email'de bir kullanıcı yoksa
        }

        boolean matches = passwordEncoder.matches(credentials.getUserPassword(), inDB.getUserPassword());
        if (!matches) {
            throw new AuthException("Please check your email and password");//şifre yanlışsa
        }

        return inDB;
    }

    @Transactional
    public User getUserDetails(String token) {
        Optional<Token> optionalToken = tokenRepository.findById(token);
        return optionalToken.map(Token::getUser).orElse(null);
    }


    public String generateRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");//üretilen uuid'deki çizgileri kaldır
    }

    @Transactional
    public void clearToken(String tokenString) {
        Token token = tokenRepository.findTokenByToken(tokenString);
        System.out.println("token:" + token.getToken() + " tokenleri silinecek user: " + token.getUser().getUserPk());
        tokenRepository.deleteTokensByUser_UserPk(token.getUser().getUserPk());
    }


}
