package com.jadson.exceptions;
// Código baseado no repositório do projeto de BD2

public class PersistenceDAOException extends Exception {

    public PersistenceDAOException() {
        super("An error occurred in the DAO persistence.");
    }

}
