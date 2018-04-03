package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.entity.PlayerEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.Slot;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.main.Pathfinder;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    private boolean validate(int playerID, String token){
        Player player = playerRepository.findByPlayerID(playerID).get(0).getPlayer();
        return player.getToken().equals(token) && player.getGame().getCurrent().equals(player);
    }

    public String create(GameEntity gameEntity){
        // TODO: Use new constructor of Player when created
        PlayerEntity playerEntity = new PlayerEntity(new Player(), gameEntity);
        playerRepository.save(playerEntity);
        return playerEntity.getPlayer().getToken();
    }

    public PlayerEntity getPlayer(int playerID, String token){
        if(validate(playerID, token)){
            return playerRepository.findByPlayerID(playerID).get(0);
        }
        return null;
    }

    public GameEntity getGame(PlayerEntity player){
        return player.getGame();
    }

    public List<PlayingPiece> getPlayingPieces(Player player){
        return player.getPlayingPieces();
    }

    public boolean buyCard(PlayerEntity player, Slot slot, String token){
        if(validate(player.getPlayerID(), token)){
            player.getPlayer().buy(slot);
            return true;
        }
        return false;
    }

    public boolean discardCard(PlayerEntity player, Card card, String token){
        if(validate(player.getPlayerID(), token)){
            player.getPlayer().discard(card);
            return true;
        }
        return false;
    }

    public boolean removeCard(PlayerEntity player, Card card, String token){
        if(validate(player.getPlayerID(), token)){
            player.getPlayer().remove(card);
            return true;
        }
        return false;
    }

    public boolean sellCard(PlayerEntity player, Card card, String token){
        if(validate(player.getPlayerID(), token)){
            player.getPlayer().sell(card);
            return true;
        }
        return false;
    }

    public boolean stealCard(PlayerEntity player, Slot slot, String token){
        if(validate(player.getPlayerID(), token)){
            player.getPlayer().steal(slot);
            return true;
        }
        return false;
    }

    public boolean performAction(PlayerEntity player, Card card, String token){
        if(validate(player.getPlayerID(), token)){
            player.getPlayer().action(card);
            return true;
        }
        return false;
    }

    public List<HexSpace> findPath(PlayerEntity player, String token, List<Card> cards, PlayingPiece playingPiece){
        if(!validate(player.getPlayerID(), token)){
            return null;
        }
        return Pathfinder.getWay(cards, playingPiece);
    }

    public boolean endRound(PlayerEntity player, String token){
        if(validate(player.getPlayerID(), token)){
            player.getPlayer().endRound();
            return true;
        }
        return false;
    }
}
