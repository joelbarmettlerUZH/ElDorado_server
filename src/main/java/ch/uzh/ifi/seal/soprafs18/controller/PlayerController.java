package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.entity.PlayerEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
public class PlayerController  implements Serializable {
    private final String context = CONSTANTS.APICONTEXT + "/Player";

    @Autowired
    private PlayerService playerService;

    //Gets all players
    @GetMapping(value = context)
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerEntity> getPlayers(){
        return playerService.getPlayers();
    }

    //Get specific player
    @GetMapping(value = context+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerEntity getUser(@PathVariable int id){
        return playerService.getPlayer(id);
    }

    //Get game
    @GetMapping(value = context+"/{id}/Update")
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable int id, @RequestParam("token") String token){
        return playerService.getGame(id, token);
    }

}
