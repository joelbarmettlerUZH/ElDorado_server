package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.Slot;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class PlayerController  implements Serializable {
    private final String context = CONSTANTS.APICONTEXT + "/Player";

    @Autowired
    private PlayerService playerService;

    //Gets all players
    @GetMapping(value = context)
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getPlayers(){
        return playerService.getPlayers();
    }

    //Get specific player
    @GetMapping(value = context+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player getUser(@PathVariable int id){
        return playerService.getPlayer(id);
    }

    //Get playing pieces
    @GetMapping(value = context+"/{id}/PlayingPiece")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayingPiece> getPlayingPiece(@PathVariable int id){
        return playerService.getPlayingPieces(id);
    }

    //Get handpile
    @GetMapping(value = context+"/{id}/HandPile")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> getHandPile(@PathVariable int id, @RequestParam("token") String token){
        return playerService.getHandPile(id, token);
    }

    //Sells a Card
    @PutMapping(value = context+"/{id}/Sell")
    @ResponseStatus(HttpStatus.OK)
    public Player sellCard(@PathVariable int id, @RequestBody Card card, @RequestParam("token") String token){
        return playerService.sellCard(id, card, token);
    }

    //Buy a Card
    @PutMapping(value = context+"/{id}/Buy")
    @ResponseStatus(HttpStatus.OK)
    public Player buyCard(@PathVariable int id, @RequestBody Slot slot, @RequestParam("token") String token){
        return playerService.buyCard(id, slot, token);
    }

    //Removes a Card
    @PutMapping(value = context+"/{id}/Remove")
    @ResponseStatus(HttpStatus.OK)
    public Player removeCard(@PathVariable int id, @RequestBody Card card, @RequestParam("token") String token){
        return playerService.removeCard(id, card, token);
    }

    //Discards a Card
    @PutMapping(value = context+"/{id}/Discard")
    @ResponseStatus(HttpStatus.OK)
    public Player DiscardCard(@PathVariable int id, @RequestBody Card card, @RequestParam("token") String token){
        return playerService.discardCard(id, card, token);
    }

    //Steal a Card
    @PutMapping(value = context+"/{id}/Steal")
    @ResponseStatus(HttpStatus.OK)
    public Player stealCard(@PathVariable int id, @RequestBody Slot slot, @RequestParam("token") String token){
        return playerService.stealCard(id, slot, token);
    }

    /*
    //Move the Player
    @PutMapping(value = context+"/{id}/Move/{playingPiece}")
    @ResponseStatus(HttpStatus.OK)
    public Player movePlayer(@PathVariable int id, @PathVariable int playingPiece, @RequestBody Point point, @RequestBody List<Card> cards, @RequestParam("token") String token){
        return playerService.movePlayer(id, cards, playingPiece, point, token);
    }
    */
    @PutMapping(value = context+"/{id}/Move/{playingPiece}")
    @ResponseStatus(HttpStatus.OK)
    public Player movePlayer(@PathVariable int id, @PathVariable int playingPiece, @RequestBody MoveWrapper moveWrapper, @RequestParam("token") String token){
        return playerService.movePlayer(id, moveWrapper.getCards(), playingPiece, moveWrapper.getHexSpace(), token);
    }

    //Remove Blockades
    @PutMapping(value = context+"/{id}/Blockade")
    @ResponseStatus(HttpStatus.OK)
    public Player removeBlockades(@PathVariable int id, @RequestBody Blockade blockade, @RequestParam("token") String token){
        return playerService.removeBlockade(id, token, blockade);
    }

    //Use ActionCard
    @PutMapping(value = context+"/{id}/Action")
    @ResponseStatus(HttpStatus.OK)
    public Player actionPlayer(@PathVariable int id, @RequestBody Card card, @RequestParam("token") String token){
        return playerService.performAction(id, card, token);
    }

    //End Round
    @PutMapping(value = context+"/{id}/End")
    @ResponseStatus(HttpStatus.OK)
    public Game actionPlayer(@PathVariable int id, @RequestParam("token") String token){
        return playerService.endRound(id, token);
    }

    //Path
    @CrossOrigin
    @PutMapping(value = context+"/{id}/Path/{playingPiece}")
    @ResponseStatus(HttpStatus.OK)
    public List<HexSpace> findPath(@PathVariable int id, @PathVariable int playingPiece, @RequestBody MoveWrapper moveWrapper, @RequestParam("token") String token){
        return playerService.findPath(id, moveWrapper.getCards(), playingPiece, token);
    }
}
