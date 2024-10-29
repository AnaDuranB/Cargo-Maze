package com.cargomaze.cargo_maze.persistance.exceptions;

public class GameSessionAlreadyExists extends Exception {

    public GameSessionAlreadyExists(String message) {
        super(message);
    }

    public GameSessionAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
    
}
