package com.cargomaze.cargo_maze.model;

public class Player {
    private String id;
    private String nickname;
    private Position position;
    private boolean isReady;

    public Player(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.isReady = false;
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }

    public void updatePosition(Position newPosition) {
        this.position = newPosition;
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
