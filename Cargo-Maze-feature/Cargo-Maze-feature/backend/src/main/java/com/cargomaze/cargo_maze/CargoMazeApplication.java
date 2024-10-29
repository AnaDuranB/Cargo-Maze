package com.cargomaze.cargo_maze;

import com.cargomaze.cargo_maze.model.GameSession;
import com.cargomaze.cargo_maze.model.Player;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CargoMazeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CargoMazeApplication.class, args);
    }

    @Bean
    public CommandLineRunner initPlayers() {
        return args -> {
            GameSession gameSession = new GameSession("Session1");

            // Crear 4 jugadores 
            List<Player> players = new ArrayList<>();
            players.add(new Player("1", "Alice", gameSession));
            players.add(new Player("2", "Bob", gameSession));
            players.add(new Player("3", "Charlie", gameSession));
            players.add(new Player("4", "David", gameSession));

            // Imprimir la información de los jugadores
            players.forEach(this::printPlayerInfo);
        };
    }

    // Método para imprimir la información del jugador
    private void printPlayerInfo(Player player) {
        System.out.println("ID: " + player.getId());
        System.out.println("Nickname: " + player.getNickname());
        System.out.println("Is Ready: " + player.isReady());
        System.out.println("Position: " + player.getPosition());
        System.out.println("Game Session: " + player.getGameSession().getSessionId());
        System.out.println("--------------------------");
    }
}

