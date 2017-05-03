package com.excilys.mlemaile.cdb.web.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password2", "error.password2");
        UserDto userDto = (UserDto) target;
        if (!userDto.getPassword().equals(userDto.getPassword2())) {
            errors.reject("password", "The password must be equals");
        }
    }

}
