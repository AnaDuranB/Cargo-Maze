package com.cargomaze.cargo_maze.model;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private String sessionId;
    private List<Player> players;
    private GameStatus status;
    private Board board;

    public GameSession(String sessionId) {
        this.sessionId = sessionId;
        this.players = new ArrayList<>();
        this.status = GameStatus.WAITING_FOR_PLAYERS;
        this.board = new Board();
    }

    public boolean addPlayer(Player player){
        if (players.size() >= 4 || status != GameStatus.WAITING_FOR_PLAYERS){
            return false;
        }
        players.add(player);
        assignPlayerStartPosition(player);
        return true;
    }

    private void assignPlayerStartPosition(Player player){
        int playerIndex = players.size() - 1;
        Position startPosition = board.getPlayerStartPosition(playerIndex);
        player.updatePosition(startPosition);
    }

    public boolean moveBox(String playerId, Position boxPosition, Position newPosition){
        if (status != GameStatus.IN_PROGRESS){
            return false;
        }
        Player player = findPlayerById(playerId);
        if (player == null){
            return false;
        }

        Box box = board.getBoxAt(boxPosition);
        if (box==null || (box.getCurrentMover() != null && !box.getCurrentMover().getId().equals(playerId))){
            return false;
        }

        if (isValidBoxMove(player, box, newPosition)){
            box.setCurrentMover(player);
            box.move(newPosition);
            updateGameStatus();
            return true;
        }
        return false;
    }

    public boolean movePlayer(String playerId, Position newPosition) {
        if (status != GameStatus.IN_PROGRESS) {
            return false;
        }

        Player player = findPlayerById(playerId);
        if (player == null) {
            return false;
        }
        Position currentPos = player.getPosition();

        if (currentPos.isAdjacent(newPosition) && board.isValidPosition(newPosition) && !board.hasWallAt(newPosition)) {
            if (board.hasBoxAt(newPosition)){
                Position boxNewPosition = new Position(newPosition.getX() + 1, newPosition.getY()); // only validating movements to the right for now :(
                Box box = board.getBoxAt(newPosition);
                if (box != null && isValidBoxMove(player, box, boxNewPosition)) {
                    box.move(boxNewPosition);
                } else {
                    return false;
                }
            }
            player.updatePosition(newPosition);
            return true;
        }
        return false;
    }

    private boolean isValidBoxMove(Player player, Box box, Position newPosition){
        return player.getPosition().isAdjacent(box.getPosition()) &&
                board.isValidPosition(newPosition) &&
                !board.hasWallAt(newPosition) &&
                !board.hasBoxAt(newPosition);
    }
    public void startGame(){
        if (players.size() == 4 && players.stream().allMatch(Player::isReady)){
            status = GameStatus.IN_PROGRESS;
        }
    }
    public void updateGameStatus(){
        if (board.isComplete()){
            status = GameStatus.COMPLETED;
        }
    }
    private Player findPlayerById(String playerId){
        return players.stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElse(null);
    }

    public String getSessionId() {
        return sessionId;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public GameStatus getStatus() {
        return status;
    }
    public Board getBoard() {
        return board;
    }
}
