package com.cargomaze.cargo_maze.controller;

import com.cargomaze.cargo_maze.model.Position;
import com.cargomaze.cargo_maze.persistance.exceptions.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cargomaze.cargo_maze.services.CargoMazeServices;

import java.util.Map;

import org.json.JSONObject;

@RestController
@RequestMapping("/cargoMaze")
public class CargoMazeController {

    private final CargoMazeServices cargoMazeServices;
    
    @Autowired
    public CargoMazeController(CargoMazeServices cargoMazeServices){
        this.cargoMazeServices = cargoMazeServices;
    }

    //Session controller

    /**
     * Reurns the base lobby
     * @return 
     */
    @GetMapping("/sessions/{id}")
    public ResponseEntity<?> getGameSession(@PathVariable String id) {
        try {
            return new ResponseEntity<>(cargoMazeServices.getGameSession(id),HttpStatus.OK);
        } catch (CargoMazePersistanceException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }        
    }


    @GetMapping("/sessions/{id}/board/state")
    public ResponseEntity<?> getBoardState(@PathVariable String id) {
        try {
            return new ResponseEntity<>(cargoMazeServices.getBoardState(id),HttpStatus.ACCEPTED);
        } catch ( CargoMazePersistanceException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }        
    }

    //Player controller
    
    @GetMapping("/players/{nickName}")

    public ResponseEntity<?> getPlayer(@PathVariable String nickName) {
        try {
            return new ResponseEntity<>(cargoMazeServices.getPlayer(nickName),HttpStatus.ACCEPTED);
        } catch (CargoMazePersistanceException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }        
    }

    /**
     * Creates a new player
     */
    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(@RequestBody Map<String, String> nickname, HttpSession session) {
        try {
            cargoMazeServices.createPlayer(nickname.get("nickname"));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CargoMazePersistanceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/sessions/{id}/players/count")
    public ResponseEntity<?> getPlayerCount(@PathVariable String id) {
        try {
            int playerCount = cargoMazeServices.getPlayerCount(id);
            return new ResponseEntity<>(playerCount, HttpStatus.OK);
        } catch (CargoMazePersistanceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/sessions/{id}/players")
    public ResponseEntity<?> addPlayerToGame(@RequestBody Map<String, String> requestBody, @PathVariable String id) {
        String nickname = requestBody.get("nickname");
        if (nickname == null || nickname.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nickname is required and cannot be empty"));
        }
        try {
            cargoMazeServices.addNewPlayerToGame(nickname, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "Player added to game session", "sessionId", id, "nickname", nickname));
        } catch (CargoMazePersistanceException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/sessions/{sessionId}/players/{nickname}/move")
    public ResponseEntity<?> movePlayer(@RequestBody Position position, @PathVariable String sessionId, @PathVariable String nickname) {
        if (position == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "position is required"));
        }
        System.out.println("POSICION EN EL CONTROLADOR " + position.getX() + " " + position.getY());
        try {
            if(!cargoMazeServices.movePlayer(nickname, sessionId, position)){
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid move"));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "Player moved", "sessionId", sessionId, "nickname", nickname));
        } catch (CargoMazePersistanceException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }


//    @DeleteMapping("/sessions/{id}/players/{nickname}")
//    public ResponseEntity<?> removePlayerFromGame(@PathVariable String id, @PathVariable String nickname) {
//        try {
//            cargoMazeServices.removePlayerFromGame(nickname, id);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        } catch (PlayerNotFoundException | GameSessionNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
//        }
//    }


}
