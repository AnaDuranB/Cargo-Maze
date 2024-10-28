
package com.cargomaze.cargo_maze.ModelTests;

import com.cargomaze.cargo_maze.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private GameSession gameSession;
    private Player player1,player2, player3, player4;
    private Board board;

    @BeforeEach
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
    void testValidPlayerMoveBoxUpToDown() {
        System.out.println("Tablero inicial:");
        board.printBoard(gameSession.getPlayers());

        Position initialPos = player1.getPosition();
        Position newPos0 = new Position(initialPos.getX() + 1, initialPos.getY());
        assertTrue(gameSession.movePlayer(player1.getId(), newPos0));


        Position newPos1 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos1));


        Position newPos2 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos2));


        Position newPos3 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos3));


        Position newPos4 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos4));

        //verificar moviento de la caja
        assertInstanceOf(Box.class, gameSession.getBoard().getBoxAt(new Position(newPos4.getX(), newPos4.getY() +1)));

        System.out.println("Tablero después del movimiento:");
        board.printBoard(gameSession.getPlayers());
    }

    @Test
    void testValidPlayerMoveBoxLeftToRight() {
        System.out.println("Tablero inicial:");
        board.printBoard(gameSession.getPlayers());
        
        Position newPos1 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos1));


        Position newPos2 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos2));


        Position newPos3 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos3));


        Position newPos4 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos4));

        Position newPos5 = new Position(player1.getPosition().getX() + 1, player1.getPosition().getY());
        assertTrue(gameSession.movePlayer(player1.getId(), newPos5));

        assertInstanceOf(Box.class, gameSession.getBoard().getBoxAt(new Position(newPos5.getX() +1, newPos4.getY())));

        System.out.println("Tablero después del movimiento:");
        board.printBoard(gameSession.getPlayers());
    }

    @Test
    void testValidPlayerMoveBoxDownToUp() {
        System.out.println("Tablero inicial:");
        board.printBoard(gameSession.getPlayers());

        Position newPos1 = new Position(player2.getPosition().getX() + 1, player2.getPosition().getY());
        assertTrue(gameSession.movePlayer(player2.getId(), newPos1));
        

        Position newPos2 = new Position(player2.getPosition().getX(), player2.getPosition().getY() - 1);
        assertTrue(gameSession.movePlayer(player2.getId(), newPos2));


        Position newPos3 = new Position(player2.getPosition().getX(), player2.getPosition().getY() - 1);
        assertTrue(gameSession.movePlayer(player2.getId(), newPos3));


        Position newPos4 = new Position(player2.getPosition().getX(), player2.getPosition().getY() - 1);
        assertTrue(gameSession.movePlayer(player2.getId(), newPos4));


        assertInstanceOf(Box.class, gameSession.getBoard().getBoxAt(new Position(newPos4.getX(), newPos4.getY() - 1)));

        System.out.println("Tablero después del movimiento:");
        board.printBoard(gameSession.getPlayers());
    }

    @Test
    void testValidPlayerMoveRightToLeft() {
        System.out.println("Tablero inicial:");
        board.printBoard(gameSession.getPlayers());

        Position newPos1 = new Position(player2.getPosition().getX() + 1, player2.getPosition().getY());
        assertTrue(gameSession.movePlayer(player2.getId(), newPos1));
        

        Position newPos2 = new Position(player2.getPosition().getX(), player2.getPosition().getY() - 1);
        assertTrue(gameSession.movePlayer(player2.getId(), newPos2));


        Position newPos3 = new Position(player2.getPosition().getX(), player2.getPosition().getY() - 1);
        assertTrue(gameSession.movePlayer(player2.getId(), newPos3));


        Position newPos4 = new Position(player2.getPosition().getX() +1, player2.getPosition().getY());
        assertTrue(gameSession.movePlayer(player2.getId(), newPos4));

        Position newPos5 = new Position(player2.getPosition().getX(), player2.getPosition().getY() -1);
        assertTrue(gameSession.movePlayer(player2.getId(), newPos5));

        Position newPos6 = new Position(player2.getPosition().getX() - 1, player2.getPosition().getY());
        assertTrue(gameSession.movePlayer(player2.getId(), newPos6));


        assertInstanceOf(Box.class, gameSession.getBoard().getBoxAt(new Position(newPos6.getX() -1, newPos6.getY())));

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
        Position wallPos = new Position(0, 1);

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

        System.out.println("Tablero inicial:");
        board.printBoard(gameSession.getPlayers());

        Position newPos1 = new Position(player1.getPosition().getX() , player1.getPosition().getY()+ 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos1));

        Position newPos2 = new Position(player1.getPosition().getX(),  player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos2));

        Position newPos3 = new Position(player1.getPosition().getX() , player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos3));

        Position newPos4 = new Position(player1.getPosition().getX() , player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos4));

        Position newPos5 = new Position(player1.getPosition().getX() , player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos5));

        Position newPos6 = new Position(player1.getPosition().getX() , player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos6));

        Position newPos7 = new Position(player1.getPosition().getX() , player1.getPosition().getY() + 1);
        assertFalse(gameSession.movePlayer(player1.getId(), newPos7));

        assertEquals(newPos6, player1.getPosition());
        assertNotEquals(player2Pos, player1.getPosition());

        System.out.println("Tablero después del movimiento:");
        board.printBoard(gameSession.getPlayers());

        
    }

    @Test
    void testInvalidPlayerMoveBoxToWall() {
        System.out.println("Tablero inicial:");
        board.printBoard(gameSession.getPlayers());

        Position newPos1 = new Position(player2.getPosition().getX() + 1, player2.getPosition().getY());
        assertTrue(gameSession.movePlayer(player2.getId(), newPos1));
        

        Position newPos2 = new Position(player2.getPosition().getX(), player2.getPosition().getY() - 1);
        assertTrue(gameSession.movePlayer(player2.getId(), newPos2));


        Position newPos3 = new Position(player2.getPosition().getX(), player2.getPosition().getY() - 1);
        assertTrue(gameSession.movePlayer(player2.getId(), newPos3));


        Position newPos4 = new Position(player2.getPosition().getX() +1, player2.getPosition().getY());
        assertTrue(gameSession.movePlayer(player2.getId(), newPos4));

        Position newPos5 = new Position(player2.getPosition().getX(), player2.getPosition().getY() -1);
        assertTrue(gameSession.movePlayer(player2.getId(), newPos5));

        Position newPos6 = new Position(player2.getPosition().getX() - 1, player2.getPosition().getY());
        assertTrue(gameSession.movePlayer(player2.getId(), newPos6));

        Position newPos7 = new Position(player2.getPosition().getX() - 1, player2.getPosition().getY());
        assertFalse(gameSession.movePlayer(player2.getId(), newPos7)); //No permite mover ni la caja ni el jugador a una nueva posición.

        assertInstanceOf(Box.class, gameSession.getBoard().getBoxAt(new Position(newPos6.getX() -1, newPos6.getY())));
        assertEquals(newPos6, player2.getPosition());

        System.out.println("Tablero después del movimiento:");
        board.printBoard(gameSession.getPlayers());
    }


    @Test
    void testValidPlayerMoveBoxToAnotherBox() {
        System.out.println("Tablero inicial:");
        board.printBoard(gameSession.getPlayers());

        Position initialPos = player1.getPosition();
        Position newPos0 = new Position(initialPos.getX() + 1, initialPos.getY());
        assertTrue(gameSession.movePlayer(player1.getId(), newPos0));


        Position newPos1 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos1));


        Position newPos2 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos2));


        Position newPos3 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos3));


        Position newPos4 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos4));

        Position newPos5 = new Position(player1.getPosition().getX() -1, player1.getPosition().getY());
        assertTrue(gameSession.movePlayer(player1.getId(), newPos5));

        Position newPos6 = new Position(player1.getPosition().getX(), player1.getPosition().getY() + 1);
        assertTrue(gameSession.movePlayer(player1.getId(), newPos6));

        Position newPos7 = new Position(player1.getPosition().getX() + 1, player1.getPosition().getY());
        assertTrue(gameSession.movePlayer(player1.getId(), newPos7));

        Position newPos8 = new Position(player1.getPosition().getX() + 1, player1.getPosition().getY());
        assertFalse(gameSession.movePlayer(player1.getId(), newPos8)); //Se verifica que no se mueven cajas continuas

        //verificar moviento de la caja
        assertInstanceOf(Box.class, gameSession.getBoard().getBoxAt(new Position(newPos7.getX() +1, newPos7.getY())));
        assertEquals(newPos7, player1.getPosition());

        System.out.println("Tablero después del movimiento:");
        board.printBoard(gameSession.getPlayers());
    }
}


