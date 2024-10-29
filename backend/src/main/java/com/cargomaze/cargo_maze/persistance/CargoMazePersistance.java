package com.cargomaze.cargo_maze.persistance;


import com.cargomaze.cargo_maze.model.GameSession;
import com.cargomaze.cargo_maze.model.Player;
import com.cargomaze.cargo_maze.persistance.exceptions.*;


public interface CargoMazePersistance {

    public void addPlayer(Player player) throws PlayerAlreadyExistsException;

    public Player getPlayer(String playerId) throws PlayerNotFoundException;

    public void addSession(GameSession session) throws GameSessionAlreadyExists;

    public GameSession getSession(String gameSessionId) throws GameSessionNotFoundException;

}