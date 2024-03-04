package com.epam.xstack.exceptions.validator;

import com.epam.xstack.exceptions.exception.UserAlreadyExistsException;
import com.epam.xstack.models.entity.User;
import com.epam.xstack.models.enums.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserNameExistenceValidator {
    private final SessionFactory sessionFactory;

    public void userNameExists(String userName) {

        Session session = sessionFactory.openSession();
        User userNameInDb = session.createQuery("FROM User u WHERE u.userName=:userName", User.class)
                .setParameter("userName", userName).uniqueResult();

        if (userNameInDb != null) {

            throw UserAlreadyExistsException.builder()
                    .codeStatus(Code.USER_NAME_EXISTS)
                    .message("User with name - " + userNameInDb.getUsername() + " already exists in database")
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        }
    }
}

