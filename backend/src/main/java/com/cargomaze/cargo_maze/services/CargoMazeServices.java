package com.cargomaze.cargo_maze.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cargomaze.cargo_maze.model.GameSession;
import com.cargomaze.cargo_maze.model.Player;
import com.cargomaze.cargo_maze.persistance.exceptions.FullSessionException;
import com.cargomaze.cargo_maze.persistance.exceptions.GameSessionAlreadyExists;
import com.cargomaze.cargo_maze.persistance.exceptions.GameSessionNotFoundException;
import com.cargomaze.cargo_maze.persistance.exceptions.PlayerAlreadyExistsException;
import com.cargomaze.cargo_maze.persistance.exceptions.PlayerNotFoundException;
import com.cargomaze.cargo_maze.persistance.impl.InMemoryCargoMazePersistance;

import java.util.UUID;


@Service
public class CargoMazeServices {
    InMemoryCargoMazePersistance persistance;

    @Autowired
    @Qualifier("inMemoryCargoMazePersistance")
    public void setPersistance(InMemoryCargoMazePersistance persistance) {
        this.persistance = persistance;
    }

    public void createPlayer(String nickname) throws PlayerAlreadyExistsException {
        Player player = new Player(nickname, UUID.randomUUID().toString());
        persistance.addPlayer(player);
    }

    public void addNewPlayerToGame(String playerId, String gameSessionId) throws PlayerNotFoundException, FullSessionException, GameSessionNotFoundException {    
        Player player = persistance.getPlayer(playerId);
        GameSession gameSession = persistance.getSession(gameSessionId);
        if(!gameSession.addPlayer(player)){
            throw new FullSessionException("Session is full");
        }
    }

    public void createSession(String sessionId) throws GameSessionAlreadyExists{
        GameSession session = new GameSession(sessionId);
        persistance.addSession(session);
    }

    public GameSession getGameSession(String gameSessionId) throws GameSessionNotFoundException {
        return persistance.getSession(gameSessionId);
    }

    public Player getPlayer(String playerId) throws PlayerNotFoundException {
        return persistance.getPlayer(playerId);
    }


}

