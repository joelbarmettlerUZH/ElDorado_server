package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.entity.PlayerEntity;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.Slot;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
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

    //Get playing pieces
    @GetMapping(value = context+"/{id}/PlayingPiece")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayingPiece> getPlayingPiece(@PathVariable int id){
        return playerService.getPlayingPieces(id);
    }

    //Sells a Card
    @PutMapping(value = context+"/{id}/Sell")
    @ResponseStatus(HttpStatus.OK)
    public PlayerEntity sellCard(@PathVariable int id, @RequestBody Card card, @RequestParam("token") String token){
        return playerService.sellCard(id, card, token);
    }

    //Buy a Card
    @PutMapping(value = context+"/{id}/Buy")
    @ResponseStatus(HttpStatus.OK)
    public PlayerEntity buyCard(@PathVariable int id, @RequestBody Slot slot, @RequestParam("token") String token){
        return playerService.buyCard(id, slot, token);
    }

    //Removes a Card
    @PutMapping(value = context+"/{id}/Remove")
    @ResponseStatus(HttpStatus.OK)
    public PlayerEntity removeCard(@PathVariable int id, @RequestBody Card card, @RequestParam("token") String token){
        return playerService.removeCard(id, card, token);
    }

    //Discards a Card
    @PutMapping(value = context+"/{id}/Discard")
    @ResponseStatus(HttpStatus.OK)
    public PlayerEntity DiscardCard(@PathVariable int id, @RequestBody Card card, @RequestParam("token") String token){
        return playerService.discardCard(id, card, token);
    }

    //Steal a Card
    @PutMapping(value = context+"/{id}/Steal")
    @ResponseStatus(HttpStatus.OK)
    public PlayerEntity stealCard(@PathVariable int id, @RequestBody Slot slot, @RequestParam("token") String token){
        return playerService.stealCard(id, slot, token);
    }

    //Move the Player
    @PutMapping(value = context+"/{id}/Move")
    @ResponseStatus(HttpStatus.OK)
    public PlayerEntity movePlayer(@PathVariable int id, @RequestBody Point point, @RequestBody int playingPiece, @RequestBody List<Card> cards, @RequestParam("token") String token){
        return playerService.movePlayer(id, cards, playingPiece, point, token);
    }

    //Use ActionCard
    @PutMapping(value = context+"/{id}/Action")
    @ResponseStatus(HttpStatus.OK)
    public PlayerEntity actionPlayer(@PathVariable int id, Card card, @RequestParam("token") String token){
        return playerService.performAction(id, card, token);
    }

    //TODO: FindPath

    //End Round
    @PutMapping(value = context+"/{id}/End")
    @ResponseStatus(HttpStatus.OK)
    public GameEntity actionPlayer(@PathVariable int id, @RequestParam("token") String token){
        return playerService.endRound(id, token);
    }
}
