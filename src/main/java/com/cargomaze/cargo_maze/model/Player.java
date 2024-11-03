package com.cargomaze.cargo_maze.model;

public class Player{
    private String id;
    private String nickname;
    private Position position;
    private boolean isReady;
    private GameSession gameSession = null; // no se sabe si es necesario (si se crean servicios directos del game session en teoria no)

    public Player(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.isReady = false;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }


    public void updatePosition(Position newPosition){
        position = newPosition;
    }

    //Se deben crear las excepciones correspondientes al modelo InvalidMove - PlayerNotFOund etc

    // getters :)
    public String getId() {
        return id;
    }
    public String getNickname() {
        return nickname;
    }
    public Position getPosition() {
        return position;
    }
    public boolean isReady() {
        return isReady;
    }


}
