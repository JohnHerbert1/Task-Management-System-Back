package com.jadson.exceptions;

public class ActiveTypeNotFoundException extends RuntimeException {

    public ActiveTypeNotFoundException(Long id) {
        super("Active Type with id " + id + " not found on database");
    }

}
