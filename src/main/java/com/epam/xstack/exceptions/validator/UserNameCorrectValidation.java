package com.epam.xstack.exceptions.validator;

import com.epam.xstack.exceptions.exception.UserNameNotExistsException;
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
public class UserNameCorrectValidation {
    private final SessionFactory sessionFactory;

    public void userNameExists(String userName) {

        Session session = sessionFactory.openSession();
        User userNameInDb = session.createQuery("FROM User u WHERE u.userName=:userName", User.class)
                .setParameter("userName", userName).uniqueResult();

        if (userNameInDb == null) {
            throw UserNameNotExistsException
                    .builder()
                    .codeStatus(Code.USER_NOT_FOUND)
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .message("You entered not correct username more than 3 times")
                    .build();
        }
    }
}
