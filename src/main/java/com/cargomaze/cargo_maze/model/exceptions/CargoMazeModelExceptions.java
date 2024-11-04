package com.cargomaze.cargo_maze.model.exceptions;

public class CargoMazeModelExceptions extends Exception{

    public final static String GAME_SESSION_IS_FULL_EXCEPTION = "Game session is full";
    public final static String GAME_SESSION_STARTED_ALREADY = "Game session has already started";
    

    public CargoMazeModelExceptions(String message) {
        super(message);
    }

    public CargoMazeModelExceptions(String message, Throwable cause) {
        super(message, cause);
    }
    
}