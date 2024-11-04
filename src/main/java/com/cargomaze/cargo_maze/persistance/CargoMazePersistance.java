package com.cargomaze.cargo_maze.persistance;


import com.cargomaze.cargo_maze.model.GameSession;
import com.cargomaze.cargo_maze.model.Player;
import com.cargomaze.cargo_maze.persistance.exceptions.CargoMazePersistanceException;


public interface CargoMazePersistance {

    public void addPlayer(Player player) throws CargoMazePersistanceException;

    public Player getPlayer(String playerName) throws CargoMazePersistanceException;

    public void addSession(GameSession session) throws CargoMazePersistanceException;

    public GameSession getSession(String gameSessionId) throws CargoMazePersistanceException;

}