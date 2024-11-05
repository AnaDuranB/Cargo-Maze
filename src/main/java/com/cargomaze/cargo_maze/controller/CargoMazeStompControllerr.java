package com.cargomaze.cargo_maze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class CargoMazeStompControllerr {


	@Autowired
	SimpMessagingTemplate msgt;
    
	@MessageMapping("/gameSession")
    public void handleGameSessionEvent(){
        
    }

    @MessageMapping("/player")
    public void handlePlayerEvent(){

    }

    @MessageMapping("/newMovement/session/{id}") 
	public void handleMoveEvent(@DestinationVariable String gameSession) throws Exception{
        msgt.convertAndSend("/topic/gameSession/"+gameSession);
    }
		
}
