package com.cargomaze.cargo_maze.persistance.exceptions;

public class CargoMazePersistanceException extends Exception {
    public CargoMazePersistanceException(String message) {
        super(message);
    }

    public CargoMazePersistanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
