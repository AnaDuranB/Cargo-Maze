package com.cargomaze.cargo_maze.model;

import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    public static final String EMPTY =  "EMPTY";
    public static final String TARGET =  "TARGET";
    public static final String WALL =  "WALL";
    public static final String PLAYER =  "PLAYER";
    public static final String BOX =  "BOX";
    private String state = "";
    public final ReentrantLock lock = new ReentrantLock();

    public Cell(String state){
        this.state = state;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

}