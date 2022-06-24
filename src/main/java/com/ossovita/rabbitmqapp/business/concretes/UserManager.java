package com.ossovita.rabbitmqapp.business.concretes;


import com.ossovita.rabbitmqapp.business.abstracts.UserService;
import com.ossovita.rabbitmqapp.core.dataAccess.TokenRepository;
import com.ossovita.rabbitmqapp.core.dataAccess.UserRepository;
import com.ossovita.rabbitmqapp.core.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class UserManager implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    TokenRepository tokenRepository;
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    public UserManager(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, RabbitTemplate rabbitTemplate, DirectExchange directExchange, Random random) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
        this.random = random;
    }

    Random random;


    @Override
    public User save(User user) {
        user.setUserPassword(this.passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);

        return user;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getByUserEmail(String userEmail) {
        return userRepository.getByUserEmail(userEmail);
    }


    @Override
    @Transactional
    public User updateUserLockedStatus(long userPk, boolean status) {
        User inDB = userRepository.getById(userPk);
        inDB.setLocked(status);
        if (status) {//locked olursa tokenleri sil
            tokenRepository.deleteTokensByUser_UserPk(userPk);
        }
        return userRepository.save(inDB);
    }


    @Override
    public void createDummyUserRequest(int piece) {
        rabbitTemplate.convertAndSend(directExchange.getName(), "userRouting", piece);
    }

    @Override
    public void createDummyUser(int piece) {

        for (int i = 0; i < piece; i++) {
            String userFirstName = getRandomUserFirstName();
            String userLastName = getRandomUserLastName();
            User user = User.builder()
                    .userFirstName(userFirstName)
                    .userLastName(userLastName)
                    .userEmail(userFirstName + "." + userLastName + "@mail.com")
                    .build();
            log.info("created user: " + user.toString());
            userRepository.save(user);
        }
    }

    private String getRandomUserFirstName() {
        List<String> userFirstNames = List.of("Hakan", "Ayşe", "Yasin", "Mert", "Bora", "Melike", "Mehmet");
        return userFirstNames.get(random.nextInt(userFirstNames.size()));
    }

    private String getRandomUserLastName() {
        List<String> userLastNames = List.of("Yılmaz", "Çetinok", "Eryigit", "Erpek", "Özyunus", "Yıldız", "Deveci");
        return userLastNames.get(random.nextInt(userLastNames.size()));
    }

    public long getRandomUserPk() {
        return userRepository.getRandomUser().getUserPk();
    }
}
