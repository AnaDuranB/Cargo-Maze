package com.cargomaze.cargo_maze.persistance.exceptions;

public class GameSessionNotFoundException extends Exception {

    public GameSessionNotFoundException(String message) {
        super(message);
    }

    public GameSessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
