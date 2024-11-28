package com.cargomaze.cargo_maze.model;

import java.util.concurrent.locks.ReentrantLock;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Box {
    @Id
    private String id;
    private Position position;
    private boolean isAtTarget;
    public final ReentrantLock lock;

    public Box(String id, Position position) {
        this.id = id;
        this.position = position;
        this.isAtTarget = false;
        this.lock = new ReentrantLock();
    }

    public void move(Position newPosition){
        this.position = newPosition;
    }

    public void setAtTarget(boolean atTarget) {
        isAtTarget = atTarget;
    }

    // getters :)
    public String getId() {
        return id;
    }
    public Position getPosition() {
        return position;
    }
    
    public boolean isAtTarget() {
        return isAtTarget;
    }
}
