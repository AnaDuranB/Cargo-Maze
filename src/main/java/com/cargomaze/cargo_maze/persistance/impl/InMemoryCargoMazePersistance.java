package com.cargomaze.cargo_maze.persistance.impl;


import com.cargomaze.cargo_maze.model.GameSession;
import com.cargomaze.cargo_maze.model.Player;
import com.cargomaze.cargo_maze.persistance.CargoMazePersistance;
import com.cargomaze.cargo_maze.persistance.exceptions.CargoMazePersistanceException;

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
    public void addPlayer(Player player) throws CargoMazePersistanceException {
        String playerId = player.getId();
        if (players.put(playerId, player) != null) {
            throw new CargoMazePersistanceException(CargoMazePersistanceException.PLAYER_ALREADY_EXISTS);
        }
    }

    @Override
    public Player getPlayer(String playerId) throws CargoMazePersistanceException {
        Player player = players.get(playerId);
        if (player == null) {
            throw new CargoMazePersistanceException(CargoMazePersistanceException.PLAYER_NOT_FOUND);
        }
        return player;
    }

    @Override
    public void addSession(GameSession session) throws CargoMazePersistanceException {
        String sessionId = session.getSessionId();
        if (sessions.put(sessionId, session) != null) {
            throw new CargoMazePersistanceException(CargoMazePersistanceException.GAME_SESSION_ALREADY_EXISTS);
        }
    }

    @Override
    public GameSession getSession(String gameSessionId) throws CargoMazePersistanceException {
        GameSession session = sessions.get(gameSessionId);
        if (session == null) {
            throw new CargoMazePersistanceException(CargoMazePersistanceException.GAME_SESSION_NOT_FOUND);
        }
        return session;
    }
}