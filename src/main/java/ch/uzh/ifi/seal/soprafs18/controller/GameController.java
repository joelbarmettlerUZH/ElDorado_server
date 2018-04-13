package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.Matrix;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.service.GameService;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
public class GameController  implements Serializable {
    private final String context = CONSTANTS.APICONTEXT + "/Game";

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    //gets all games
    @GetMapping(value = context)
    @ResponseStatus(HttpStatus.OK)
    private List<Game> getGames(){
        return gameService.getAll();
    }

    //gets game id
    @GetMapping(value = context+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Game getGame(@PathVariable int id){
        return gameService.getGame(id);
    }

    //Gets current player
    @GetMapping(value = context+"/{id}/Current")
    @ResponseStatus(HttpStatus.OK)
    private Player getCurrent(@PathVariable int id){
        return gameService.getCurrentPlayer(id);
    }

    //Gets all players
    @GetMapping(value = context+"/{id}/Players")
    @ResponseStatus(HttpStatus.OK)
    private List<Player> getPlayers(@PathVariable int id){
        return gameService.getPlayers(id);
    }

    //Gets board
    @GetMapping(value = context+"/{id}/Board")
    @ResponseStatus(HttpStatus.OK)
    private Matrix getBoard(@PathVariable int id){
        return gameService.getBoard(id);
    }

    //Gets Market
    @GetMapping(value = context+"/{id}/Market")
    @ResponseStatus(HttpStatus.OK)
    private Market getMarket(@PathVariable int id){
        return gameService.getMarket(id);
    }

    //Gets blockades
    @GetMapping(value = context+"/{id}/Blockade")
    @ResponseStatus(HttpStatus.OK)
    private List<Blockade> getBlockades(@PathVariable int id){
        return gameService.getBlockades(id);
    }

    //Gets winners
    @GetMapping(value = context+"/{id}/Winner")
    @ResponseStatus(HttpStatus.OK)
    private List<Player> getWinners(@PathVariable int id){
        return gameService.getWinners(id);
    }
}
