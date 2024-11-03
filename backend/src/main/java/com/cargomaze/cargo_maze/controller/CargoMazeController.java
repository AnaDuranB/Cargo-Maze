package com.cargomaze.cargo_maze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.cargomaze.cargo_maze.persistance.exceptions.GameSessionNotFoundException;
import com.cargomaze.cargo_maze.persistance.exceptions.InvalidNicknameException;
import com.cargomaze.cargo_maze.persistance.exceptions.PlayerAlreadyExistsException;
import com.cargomaze.cargo_maze.services.CargoMazeServices;
import com.cargomaze.cargo_maze.persistance.exceptions.PlayerNotFoundException;

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

    /**
     * Reurns the base lobby
     * @return 
     */
    @GetMapping("/session/{id}")
    public ResponseEntity<?> getGameSession(@PathVariable String id) {
        try {
            return new ResponseEntity<>(cargoMazeServices.getGameSession(id),HttpStatus.ACCEPTED);
        } catch ( GameSessionNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }        
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable String id) {
        try {
            return new ResponseEntity<>(cargoMazeServices.getPlayer(id),HttpStatus.ACCEPTED);
        } catch ( PlayerNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }        
    }

    /**
     * Creates a new player
     * @param player The player data sent in the request body
     * @return Status indicating success or failure
     */
    @PostMapping("/player")
    public ResponseEntity<?> createPlayer(@RequestBody Map<String, String> nickname) {
        try {
            cargoMazeServices.createPlayer(nickname.get("nickname"));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PlayerAlreadyExistsException | InvalidNicknameException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
