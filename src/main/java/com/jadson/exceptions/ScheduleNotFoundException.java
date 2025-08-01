package com.jadson.exceptions;

public class ScheduleNotFoundException extends RuntimeException {

  public ScheduleNotFoundException(Long id) {
    super("Schedule with id " + id + " not found on database");
  }

}
