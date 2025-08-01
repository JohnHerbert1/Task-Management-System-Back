package com.jadson.exceptions;

public class ScheduleTypeNotFoundException extends RuntimeException {

    public ScheduleTypeNotFoundException(Long id) {
        super("Schedule Type with id " + id + " not found on database");
    }

}
