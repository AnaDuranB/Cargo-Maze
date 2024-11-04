package com.cargomaze.cargo_maze.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cargomaze.cargo_maze.model.GameSession;
import com.cargomaze.cargo_maze.model.Player;
import com.cargomaze.cargo_maze.persistance.exceptions.CargoMazePersistanceException;
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

    public void createPlayer(String nickname) throws CargoMazePersistanceException{
        System.out.println("Creating player with nickname: " + nickname);
        if(nickname.isEmpty()) throw new CargoMazePersistanceException(CargoMazePersistanceException.INVALID_NICKNAME_EXCEPTION);
        Player player = new Player(nickname);
        persistance.addPlayer(player);
    }

    public void addNewPlayerToGame(String playerName, String gameSessionId) throws CargoMazePersistanceException {    
        Player player = persistance.getPlayer(playerName);
        GameSession gameSession = persistance.getSession(gameSessionId);
        if(!gameSession.addPlayer(player)){
            throw new CargoMazePersistanceException(CargoMazePersistanceException.FULL_SESSION_EXCEPTION);
        }
    }

    public void createSession(String sessionId) throws CargoMazePersistanceException{
        GameSession session = new GameSession(sessionId);
        persistance.addSession(session);
    }

    public GameSession getGameSession(String gameSessionId) throws CargoMazePersistanceException {
        return persistance.getSession(gameSessionId);
    }

    public Player getPlayer(String playerName) throws CargoMazePersistanceException {
        return persistance.getPlayer(playerName);
    }

    public String[][] getBoardState(String gameSessionId) throws CargoMazePersistanceException {
        return persistance.getSession(gameSessionId).getBoardState();
    }

}

