
package com.cargomaze.cargo_maze.ModelTests;

import com.cargomaze.cargo_maze.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private GameSession gameSession;
    private Player player1,player2, player3, player4;
    private Board board;

    /*@BeforeEach
    public void setUp() {
        gameSession = new GameSession("session1");
        board = gameSession.getBoard();
        player1 = new Player("p1","Player 1", gameSession);
        player2 = new Player("p2","Player 2", gameSession);
        player3 = new Player("p3","Player 3", gameSession);
        player4 = new Player("p4","Player 4", gameSession);

        gameSession.addPlayer(player1);
        gameSession.addPlayer(player2);
        gameSession.addPlayer(player3);
        gameSession.addPlayer(player4);

        player1.setReady(true);
        player2.setReady(true);
        player3.setReady(true);
        player4.setReady(true);
        gameSession.startGame();
    }


    //Test de jugador 
    @Test
    void testValidPlayerMove() {
        System.out.println("Tablero inicial:");
        board.printBoard(gameSession.getPlayers());

        Position initialPos = player1.getPosition();
        Position newPos = new Position(initialPos.getX() + 1, initialPos.getY());

        assertTrue(board.isValidPosition(newPos));
        assertFalse(board.hasWallAt(newPos));
        assertFalse(board.hasBoxAt(newPos));

        boolean moved = gameSession.movePlayer(player1.getId(), newPos);

        assertTrue(moved);
        assertEquals(newPos, player1.getPosition());

        System.out.println("Tablero después del movimiento:");
        board.printBoard(gameSession.getPlayers());
    }

    
    @Test
    void testInvalidPlayerMove() {
        System.out.println("Tablero inicial:");
        board.printBoard(gameSession.getPlayers());

        Position initialPos = player1.getPosition();
        Position newPos = new Position(initialPos.getX() + 1, initialPos.getY()+1);

    //       assertFalse(board.isValidPosition(newPos));
        assertFalse(board.hasWallAt(newPos));
        assertFalse(board.hasBoxAt(newPos));

        boolean moved = gameSession.movePlayer(player1.getId(), newPos);

        assertFalse(moved);
        assertNotEquals(newPos, player1.getPosition());

        System.out.println("Tablero después del movimiento inválido:");
        board.printBoard(gameSession.getPlayers());
    }
    @Test
    void testInvalidPlayerMove_Wall() {
        // try to move into a wall
        Position wallPos = new Position(0, 0);

        boolean moved = gameSession.movePlayer(player1.getId(), wallPos);

        assertFalse(moved);
        assertNotEquals(wallPos, player1.getPosition());
    }

    @Test
    void testInvalidPlayerMove_OutOfBounds() {
        // try to move outside the board
        Position outPos = new Position(-1, -1);
        boolean moved = gameSession.movePlayer(player1.getId(), outPos);

        assertFalse(moved);
        assertNotEquals(outPos, player1.getPosition());
    }

    @Test
    void testInvalidPlayerMove_OtherPlayer() {
        // try to move to another player's position
        Position player2Pos = player2.getPosition();
        boolean moved = gameSession.movePlayer(player1.getId(), player2Pos);

        assertFalse(moved);
        assertNotEquals(player2Pos, player1.getPosition());
    }


    @Test
    void testPushBox() {

    }

    @Test
    public void testInvalidMoveBox() {
        Player player = gameSession.getPlayers().get(0);
        Position boxPosition = new Position(2, 2);
        Position invalidPosition = new Position(3, 2);

        assertFalse(gameSession.moveBox(player.getId(), boxPosition, invalidPosition));
    }*/
}


