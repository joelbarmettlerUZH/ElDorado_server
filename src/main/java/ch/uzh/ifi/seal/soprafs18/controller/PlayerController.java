package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.PlayerEntity;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {
    private final String context = CONSTANTS.APICONTEXT + "/Player";

    @Autowired
    private PlayerService playerService;

    //Gets all players
    @GetMapping(value = context)
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerEntity> getPlayers(){
        return playerService.getPlayers();
    }

}
