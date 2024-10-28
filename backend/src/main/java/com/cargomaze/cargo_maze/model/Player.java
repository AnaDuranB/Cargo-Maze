package com.cargomaze.cargo_maze.model;

public class Player{
    private String id;
    private String nickname;
    private Position position;
    private boolean isReady;
    private GameSession gameSession;

    public Player(String id, String nickname, GameSession gameSession) {
        this.id = id;
        this.nickname = nickname;
        this.isReady = false;
        this.gameSession = gameSession;
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }


    public boolean updatePosition(Position newPosition){
        return false;
    }

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
