 package com.cargomaze.cargo_maze.persistance.exceptions;

public class PlayerAlreadyExistsException extends Exception {

    public PlayerAlreadyExistsException(String message) {
        super(message);
    }

    public PlayerAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}