package com.cargomaze.cargo_maze.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;




public class GameSession {
    private String sessionId;
    private List<Player> players;
    private GameStatus status;
    private Board board;

    public GameSession(String sessionId) {
        this.sessionId = sessionId;
        players = new ArrayList<>();
        status = GameStatus.WAITING_FOR_PLAYERS;
        board = new Board(); //Sera una instancia inyectada (para más mapas en el futuro)
        board.setPlayers(players);
    }

    public boolean addPlayer(Player player) {
        System.out.println("JUGADOR " + player.getNickname()+ " INDEX " + player.getIndex());
        if (players.size() >= 4 || status != GameStatus.WAITING_FOR_PLAYERS || player.getIndex() != -1) {
            return false;
        }
        player.setIndex(players.size());
        player.setGameSession(this);
        players.add(player);
        assignPlayerStartPosition(player);
        return true;
    }

    public int getPlayerCount() {
        return players.size();
    }

    private void assignPlayerStartPosition(Player player) {
        Position startPosition = board.getPlayerStartPosition(player.getIndex());
        player.updatePosition(startPosition);
        board.setPlayerInBoard(startPosition);
    }

    public boolean moveBox(Player player, Position playerPosition, Position boxPosition) {
        Position boxNewPosition = getPositionFromMovingABox(boxPosition, playerPosition); // Validates all postions (in theory);
        Box box = board.getBoxAt(boxPosition);
        if (isValidBoxMove(player, box, boxNewPosition)) {
            if (box.lock.tryLock() && board.getCellAt(boxNewPosition).lock.tryLock()) { // Lockeamos tanto la caja a mover y la celda a donde se va mover la caja
                try {
                    box.move(boxNewPosition); // se cambia el lugar donde esta la caja
                    board.getCellAt(boxNewPosition).setState(Cell.BOX); // se cambia el estado de la celda
                } finally {
                    box.lock.unlock(); // se desbloquean los elementos accedidos
                    board.getCellAt(boxNewPosition).lock.unlock();
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean isValidPlayerMove(Position currentPosition, Position newPosition){
        return currentPosition.isAdjacent(newPosition) && board.isValidPosition(newPosition) && !board.hasWallAt(newPosition) && !board.isPlayerAt(newPosition);
    }

    public boolean movePlayer(Player player, Position newPosition) {
        if (status != GameStatus.IN_PROGRESS) {
            return false;
        }

        /*Player player = findPlayerByIndex(playerName);
        if (player == null) {
            return false;
        }*/

        Position currentPos = player.getPosition();

        if (isValidPlayerMove(currentPos, newPosition)){
            if(board.hasBoxAt(newPosition)){
                boolean moveBox = moveBox(player, currentPos, newPosition);
                if(!moveBox){
                    return false;
                }
            }
            ReentrantLock lock = board.getCellAt(newPosition).lock;
            if(lock.tryLock()){ // se bloquea la celda a donde se va a mover el jugador por si alguno otro intenta acceder a este.
                try{
                    player.updatePosition(newPosition);
                    board.getCellAt(currentPos).setState(Cell.EMPTY); //se
                    board.getCellAt(newPosition).setState(Cell.PLAYER);
                }
                finally{
                    lock.unlock();
                }
                return true;
            }
        }
        return false;
    }

    private Position getPositionFromMovingABox(Position boxPosition, Position playerPosition) {
        //Eje y del jugador es menor al de la caja
        if(playerPosition.getY() < boxPosition.getY()){
            return new Position(boxPosition.getX(), boxPosition.getY() + 1);
        }
        //Eje y del jugador es mayor al de la caja
        else if(playerPosition.getY() > boxPosition.getY()){
            return new Position(boxPosition.getX(), boxPosition.getY() - 1);
        }
        //Eje x del jugador es menor al de la caja
        else if(playerPosition.getX() < boxPosition.getX()){
            return new Position(boxPosition.getX() + 1, boxPosition.getY());
        }
        return  new Position(boxPosition.getX() - 1, boxPosition.getY());
    }

    private boolean isValidBoxMove(Player player, Box box, Position newPosition) {
        return player.getPosition().isAdjacent(box.getPosition()) &&
                board.isValidPosition(newPosition) &&
                !board.hasWallAt(newPosition) &&
                !board.hasBoxAt(newPosition);
    }

    public void startGame() {
        if (players.size() == 4 && players.stream().allMatch(Player::isReady)) {
            status = GameStatus.IN_PROGRESS;
        }
        System.out.println(players.size() == 4);
        System.out.println(players.stream().allMatch(Player::isReady));
        for (Player player : players) {
            System.out.println(player.getNickname() + " " + player.isReady());
        }
    }

    public void updateGameStatus() {
        if (board.isComplete()) {
            status = GameStatus.COMPLETED;
        }
    }

    private Player findPlayerByName(String playerName) {
        return players.stream()
                .filter(p -> p.getNickname().equals(playerName))
                .findFirst()
                .orElse(null);
    }

    private Player findPlayerByIndex(Player player) {
        if(players.isEmpty()){
            return null;
        }
        return players.get(player.getIndex());
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