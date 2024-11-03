 package com.cargomaze.cargo_maze.persistance.exceptions;

public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}