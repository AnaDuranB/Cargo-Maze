package com.cargomaze.cargo_maze.services;

import com.cargomaze.cargo_maze.persistance.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cargomaze.cargo_maze.model.GameSession;
import com.cargomaze.cargo_maze.model.Player;
import com.cargomaze.cargo_maze.model.Position;
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

    public void createPlayer(String nickname) throws CargoMazePersistanceException {
        if (nickname == null || nickname.isEmpty()) {
            throw new CargoMazePersistanceException("Invalid nickname");
        }

        Player player = new Player(nickname);
        try {
            persistance.addPlayer(player);
        } catch (CargoMazePersistanceException e) {
            throw new CargoMazePersistanceException(CargoMazePersistanceException.PLAYER_ALREADY_EXISTS);
        }
    }

    public void addNewPlayerToGame(String nickname, String gameSessionId) throws CargoMazePersistanceException{
        GameSession gameSession = persistance.getSession(gameSessionId);

        if (gameSession.getPlayers().size() >= 4) {
            throw new CargoMazePersistanceException(CargoMazePersistanceException.FULL_SESSION_EXCEPTION);
        }
        try {
            persistance.addPlayerToGame(nickname,gameSessionId);

        } catch (CargoMazePersistanceException e) {
            throw new CargoMazePersistanceException(CargoMazePersistanceException.PLAYER_NOT_FOUND);
        }

    }

    public void createSession(String sessionId) throws CargoMazePersistanceException{
        GameSession session = new GameSession(sessionId);
        persistance.addSession(session);
    }

    public GameSession getGameSession(String gameSessionId) throws CargoMazePersistanceException {
        return persistance.getSession(gameSessionId);
    }

    public Player getPlayer(String playerId) throws CargoMazePersistanceException {
        return persistance.getPlayer(playerId);
    }


    public int getPlayerCount(String gameSessionId) throws CargoMazePersistanceException{
        return persistance.getPlayerCount(gameSessionId);
    }

    public String[][] getBoardState(String gameSessionId) throws CargoMazePersistanceException {
        return persistance.getSession(gameSessionId).getBoardState();
    }


    public boolean movePlayer(String playerId, String gameSessionId, Position direction) throws CargoMazePersistanceException {
        Player player = persistance.getPlayer(playerId);
        GameSession gameSession = persistance.getSession(gameSessionId); 
        Position newPosition = new Position(player.getPosition().getX() + direction.getX(), player.getPosition().getY() + direction.getY());
        System.out.println("POSICION EN EL SERVICIO " + newPosition.getX() + " " + newPosition.getY());
        boolean result = gameSession.movePlayer(player, newPosition);
        System.out.println("RESULTADO DEL MOVIMIENTO " + result);
        return result;
    }
}

