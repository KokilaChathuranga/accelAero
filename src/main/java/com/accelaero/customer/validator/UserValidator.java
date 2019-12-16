package com.accelaero.customer.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accelaero.customer.Response.ResponseStatus;
import com.accelaero.customer.model.User;
import com.accelaero.customer.service.UserService;

@Component
public class UserValidator {
    @Autowired
    private UserService userService;

    public ResponseStatus validate(Object o) {
        User user = (User) o;

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseStatus.EMPTY_USER_NAME;
        }
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            return ResponseStatus.INVALID_LENGTH_FOR_USER_NAME;
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseStatus.DUPLICATE_USER_NAME;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseStatus.EMPTY_PASSwORD;
        }

        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            return ResponseStatus.INVALID_LENGTH_FOR_PASSWORD;
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            return ResponseStatus.PASSWORDS_DOES_NOT_MATCH;
        }
        return ResponseStatus.SUCCESS;
    }
}
