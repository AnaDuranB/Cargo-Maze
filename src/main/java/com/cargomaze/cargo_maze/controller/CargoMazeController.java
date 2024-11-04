package com.cargomaze.cargo_maze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.cargomaze.cargo_maze.persistance.exceptions.CargoMazePersistanceException;
import com.cargomaze.cargo_maze.services.CargoMazeServices;

import jakarta.servlet.http.HttpSession;

import java.util.Map;


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
        } catch ( CargoMazePersistanceException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }        
    }


    @GetMapping("/player/{nickName}")
    public ResponseEntity<?> getPlayer(@PathVariable String nickName) {
        try {
            return new ResponseEntity<>(cargoMazeServices.getPlayer(nickName),HttpStatus.ACCEPTED);
        } catch ( CargoMazePersistanceException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }        
    }

    /**
     * Creates a new player
     * @param player The player data sent in the request body
     * @return Status indicating success or failure
     */
    @PostMapping("/login")
    public ResponseEntity<?> createPlayer(@RequestBody Map<String, String> nickname, HttpSession session) {
        session.setAttribute("nickname", nickname);
        try {
            cargoMazeServices.createPlayer(nickname.get("nickname"));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CargoMazePersistanceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/session/{id}/player")
    public ResponseEntity<?> addPlayerToGame(@RequestBody Map<String, String> nickname, @PathVariable String id) {
        try {
            cargoMazeServices.addNewPlayerToGame(id, nickname.get("nickname"));
            return new ResponseEntity<>(HttpStatus.ACCEPTED); //hay que implementar la validacion si el usuario se sale de la sesion
                                                              // se debe eliminar de la lista de jugadores.
        } catch (CargoMazePersistanceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
