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
    
	@MessageMapping("/sessions")
    public void handleGameSessionEvent() throws Exception{
        
    }

    @MessageMapping("/player")
    public void handlePlayerEvent(){

    }

    @MessageMapping("/newMovement/sessions/{id}") 
	public void handleMoveEvent(@DestinationVariable String gameSessionId) throws Exception{
        msgt.convertAndSend("/topic/sessions/"+gameSessionId);
    }
		
}
