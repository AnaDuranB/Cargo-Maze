package com.cargomaze.cargo_maze.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board {
    private static final int WIDTH = 15;
    private static final int HEIGHT = 10;
    private Cell[][] cells;
    private List<Position> targetPositions;
    private List<Box> boxes;
    private List<Position> playerStartPositions;
    private List<Player> players;

    public Board() {
        initializeBoard();
    }

    private void initializeBoard(){
        cells = new Cell[WIDTH][HEIGHT];
        targetPositions = new ArrayList<>();
        boxes = new ArrayList<>();
        playerStartPositions = new ArrayList<>();
        loadDefaultLayout();
    }

    private void loadDefaultLayout() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                cells[x][y] = new Cell(Cell.EMPTY);
            }
        }
        for (int x = 0; x < WIDTH; x++) {
            cells[x][0] = new Cell(Cell.WALL);
            cells[x][HEIGHT-1] = new Cell(Cell.WALL);
        }
        for (int y = 0; y < HEIGHT; y++) {
            cells[0][y] = new Cell(Cell.WALL);
            cells[WIDTH-1][y] = new Cell(Cell.WALL);
        }

        // walls
        cells[5][2] = new Cell(Cell.WALL);
        cells[5][3] = new Cell(Cell.WALL);
        cells[6][6] = new Cell(Cell.WALL);
        cells[6][7] = new Cell(Cell.WALL);

        // target positions
        addTarget(new Position(13, 3));
        addTarget(new Position(13, 4));
        addTarget(new Position(13, 5));
        addTarget(new Position(13, 6));
        addTarget(new Position(6, 5));

        // boxes
//       addBox(new Position(2, 2));
        addBox(new Position(4, 4));
        addBox(new Position(2, 5));
        addBox(new Position(4, 6));
        addBox(new Position(7, 3));

        // player start positions
        playerStartPositions.add(new Position(1, 1));
        playerStartPositions.add(new Position(1, HEIGHT-2));
        playerStartPositions.add(new Position(WIDTH-2, 1));
        playerStartPositions.add(new Position(WIDTH-2, HEIGHT-2));

    }

    public boolean isValidPosition(Position position) {
        return position.getX() >= 0 && position.getX() < WIDTH &&
                position.getY() >= 0 && position.getY() < HEIGHT;
    }

    public boolean hasWallAt(Position position) {
        return cells[position.getX()][position.getY()].getState().equals(Cell.WALL);
    }

    public boolean hasBoxAt(Position position) {
        return cells[position.getX()][position.getY()].getState().equals(Cell.BOX);
    }

    public boolean isPlayerAt(Position position){
        return cells[position.getX()][position.getY()].getState().equals(Cell.PLAYER);
    }

    public Box getBoxAt(Position position) {
        return boxes.stream()
                .filter(box -> box.getPosition().equals(position))
                .findFirst()
                .orElse(null);
    }

    public Position getPlayerStartPosition(int playerIndex) {
        return playerStartPositions.get(playerIndex);
    }

    public boolean isComplete() {
        return boxes.stream().allMatch(Box::isAtTarget);
    }

    private void addTarget(Position position) {
        cells[position.getX()][position.getY()] = new Cell(Cell.TARGET);;
        targetPositions.add(position);
    }

    public void addBox(Position position) {
        cells[position.getX()][position.getY()] = new Cell(Cell.BOX);;
        boxes.add(new Box(UUID.randomUUID().toString(), position));
    }

    // getters :)
    public List<Position> getTargetPositions() { return new ArrayList<>(targetPositions); }
    public List<Box> getBoxes() { return new ArrayList<>(boxes); }

    // printing the board :o
    public void printBoard(List<Player> players) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(getCellSymbol(cells[x][y]) + " ");
            }
            System.out.println();
        }
    }


    private boolean isPlayerStartPosition(Position position) {
        return playerStartPositions.contains(position);
    }


    private String getCellSymbol(Cell cell) {
        switch (cell.getState()) {
            case Cell.EMPTY: return ".";
            case Cell.WALL: return "#";
            case Cell.TARGET: return "T";
            case Cell.PLAYER: return "P";
            case Cell.BOX: return "B";
            default: return "?";
        }
    }

    public void setPlayers(List<Player> players){ this.players = players;}

    public void setPlayerInBoard(Position position){
        cells[position.getX()][position.getY()] = new Cell(Cell.PLAYER);
    }

    public Cell getCellAt(Position position){
        return cells[position.getX()][position.getY()];
    }
}