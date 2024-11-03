package com.cargomaze.cargo_maze.persistance.impl;


import com.cargomaze.cargo_maze.model.GameSession;
import com.cargomaze.cargo_maze.model.Player;
import com.cargomaze.cargo_maze.persistance.CargoMazePersistance;
import com.cargomaze.cargo_maze.persistance.exceptions.GameSessionAlreadyExists;
import com.cargomaze.cargo_maze.persistance.exceptions.GameSessionNotFoundException;
import com.cargomaze.cargo_maze.persistance.exceptions.PlayerAlreadyExistsException;
import com.cargomaze.cargo_maze.persistance.exceptions.PlayerNotFoundException;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component("inMemoryCargoMazePersistance")
public class InMemoryCargoMazePersistance implements CargoMazePersistance{

    private final ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, GameSession> sessions = new ConcurrentHashMap<>();

    public InMemoryCargoMazePersistance() {
        //Load base session
        GameSession baseSession = new GameSession("1");
        sessions.put("1", baseSession);
    }

    @Override
    public void addPlayer(Player player) throws PlayerAlreadyExistsException{
        String playerId = player.getId();
        if(players.put(playerId, player) != null){
            throw new PlayerAlreadyExistsException("Player with id " + playerId + " already exists");
        }
    }

    @Override
    public Player getPlayer(String playerId) throws PlayerNotFoundException {
        Player player = players.get(playerId);
        if(player == null){
            throw new PlayerNotFoundException("Player with id " + playerId + " not found");
        }
        return player;
    }

    @Override
    public void addSession(GameSession session) throws GameSessionAlreadyExists {
        String sessionId = session.getSessionId();
        if(sessions.put(sessionId, session) != null){
            throw new GameSessionAlreadyExists("Session with id " + sessionId + " already exists");
        }
    }

    @Override
    public GameSession getSession(String gameSessionId) throws GameSessionNotFoundException {
        GameSession session = sessions.get(gameSessionId);
        if(session == null){
            throw new GameSessionNotFoundException("Session with id " + gameSessionId + " not found");
        }
        return session;
    }



    
    



     
}