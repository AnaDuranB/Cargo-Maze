package com.cargomaze.cargo_maze.model.exceptions;

public class CargoMazeModelExceptions extends Exception{

    public static final String GAME_SESSION_IS_FULL_EXCEPTION = "Game session is full";
    public static final String GAME_SESSION_STARTED_ALREADY = "Game session has already started";
    public static final String INVALID_MOVE = "Invalid move";
    public static final String PLAYER_ALREADY_ADDED = "Player already added";
    

    public CargoMazeModelExceptions(String message) {
        super(message);
    }

    public CargoMazeModelExceptions(String message, Throwable cause) {
        super(message, cause);
    }
    
}