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
                cells[x][y] = Cell.EMPTY;
            }
        }
        for (int x = 0; x < WIDTH; x++) {
            cells[x][0] = Cell.WALL;
            cells[x][HEIGHT-1] = Cell.WALL;
        }
        for (int y = 0; y < HEIGHT; y++) {
            cells[0][y] = Cell.WALL;
            cells[WIDTH-1][y] = Cell.WALL;
        }

        // walls
        cells[5][2] = Cell.WALL;
        cells[5][3] = Cell.WALL;
        cells[6][6] = Cell.WALL;
        cells[6][7] = Cell.WALL;

        // target positions
        addTarget(new Position(13, 3));
        addTarget(new Position(13, 4));
        addTarget(new Position(13, 5));
        addTarget(new Position(13, 6));
        addTarget(new Position(6, 5));

        // boxes
//        addBox(new Position(2, 2));
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
        return cells[position.getX()][position.getY()] == Cell.WALL;
    }

    public boolean hasBoxAt(Position position) {
        return boxes.stream().anyMatch(box -> box.getPosition().equals(position));
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
        cells[position.getX()][position.getY()] = Cell.TARGET;
        targetPositions.add(position);
    }

    public void addBox(Position position) {
        boxes.add(new Box(UUID.randomUUID().toString(), position));
    }

    // getters :)
    public List<Position> getTargetPositions() { return new ArrayList<>(targetPositions); }
    public List<Box> getBoxes() { return new ArrayList<>(boxes); }

    // printing the board :o
    public void printBoard(List<Player> players) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Position currentPos = new Position(x, y);

                boolean isPlayerHere = players.stream().anyMatch(player -> player.getPosition().equals(currentPos));
                if (isPlayerHere) {
                    System.out.print("P "); // players
                } else if (hasBoxAt(currentPos)) {
                    System.out.print("C "); // boxes
                } else {
                    System.out.print(getCellSymbol(cells[x][y]) + " ");
                }
            }
            System.out.println();
        }
    }


    private boolean isPlayerStartPosition(Position position) {
        return playerStartPositions.contains(position);
    }


    private String getCellSymbol(Cell cell) {
        switch (cell) {
            case EMPTY: return ".";
            case WALL: return "#";
            case TARGET: return "T";
            default: return "?";
        }
    }
}