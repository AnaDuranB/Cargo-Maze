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

    @MessageMapping("/sessions")
    public void handleGameSessionEvent() throws Exception {
        msgt.convertAndSend("/topic/sessions", true);
    }

    @MessageMapping("/sessions/updateBoard.{gameSessionId}")
    public void handleGeneralGameBoardEvent(@DestinationVariable String gameSessionId) throws Exception {
        msgt.convertAndSend("/topic/sessions/" + gameSessionId + "/update", true);
    }

    @MessageMapping("/sessions/move.{gameSessionId}")
    public void handleMoveEvent(@DestinationVariable String gameSessionId) throws Exception {
        msgt.convertAndSend("/topic/sessions/" + gameSessionId + "/move", true);
    }
}
