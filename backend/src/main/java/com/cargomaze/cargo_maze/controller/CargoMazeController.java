package com.cargomaze.cargo_maze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cargomaze.cargo_maze.model.Player;
import com.cargomaze.cargo_maze.persistance.exceptions.GameSessionNotFoundException;
import com.cargomaze.cargo_maze.persistance.exceptions.PlayerAlreadyExistsException;
import com.cargomaze.cargo_maze.services.CargoMazeServices;

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
    @RequestMapping(value = "/baseLobby", method = RequestMethod.GET)
    public ResponseEntity<?> getBaseLobbyManager(){
        try {
            return new ResponseEntity<>(cargoMazeServices.getGameSession("1"),HttpStatus.ACCEPTED);
        } catch ( GameSessionNotFoundException ex) {
            return new ResponseEntity<>("GameSessionNotFound",HttpStatus.NOT_FOUND);
        }        
    }

    /**
     * Create a new player
     * @param bp
     * @return
     */
    @RequestMapping(value = "/createPlayer",method = RequestMethod.POST)
    public ResponseEntity<?> postCreatePlayerEntity(@RequestBody Player player) {
        try {
            cargoMazeServices.createPlayer(player.getNickname(), player.getId());
            return new ResponseEntity<>(HttpStatus.CREATED, HttpStatus.OK);
        } catch (PlayerAlreadyExistsException e) {
            return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
        }
    }
}
