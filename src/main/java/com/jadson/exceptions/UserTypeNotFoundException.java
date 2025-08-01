package com.jadson.exceptions;

public class UserTypeNotFoundException extends RuntimeException {

    public UserTypeNotFoundException(Long id) {
        super("User Type with id " + id + " not found on database");
    }

}
