package com.cargomaze.cargo_maze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CargoMazeStompController {

    @Autowired
    private SimpMessagingTemplate msgt;

    String topicUri = "/topic/sessions/";

    @MessageMapping("/sessions")
    public void handleGameSessionEvent() throws Exception {
        msgt.convertAndSend(topicUri, true);
    }

    @MessageMapping("/sessions/enterOrExitSession.{gameSessionId}")
    public void handleGeneralGameBoardEvent(@DestinationVariable String gameSessionId) throws Exception {
        System.out.println("hola");
        msgt.convertAndSend(topicUri + gameSessionId + "/updatePlayerList", true);
        msgt.convertAndSend(topicUri + gameSessionId + "/updateBoard", true);
    }
    

    @MessageMapping("/sessions/move.{gameSessionId}")
    public void handleMoveEvent(@DestinationVariable String gameSessionId) throws Exception {
        msgt.convertAndSend(topicUri + gameSessionId + "/move", true);
    }
}
