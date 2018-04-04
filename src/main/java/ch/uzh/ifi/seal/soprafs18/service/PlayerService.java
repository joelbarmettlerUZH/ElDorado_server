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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    private final Logger LOGGER = Logger.getLogger(RoomService.class.getName());
    private FileHandler filehandler;

    public PlayerService() {
        try {
            filehandler = new FileHandler("Serverlog.log", 1024 * 8, 1, true);
            LOGGER.addHandler(filehandler);
            SimpleFormatter formatter = new SimpleFormatter();
            filehandler.setFormatter(formatter);
            LOGGER.setLevel(Level.FINE);
            filehandler.setLevel(Level.INFO);
        } catch (IOException io) {
            System.out.println("ERROR: Could not set logging handler to file");
        }
    }

    private boolean validate(int playerID, String token) {
        PlayerEntity player = playerRepository.findByPlayerID(playerID).get(0);
        LOGGER.info("Validating user");
        return player.getToken().equals(token) && playerRepository.findByPlayerID(player.getGame().getCurrentPlayer().getPlayerID()).equals(player);
    }

    public List<PlayerEntity> getPlayers(){
        List<PlayerEntity> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        LOGGER.info("Returning all Players");
        return players;
    }

    public PlayerEntity getPlayer(int playerID, String token) {
        if (validate(playerID, token)) {
            LOGGER.info("Returning player " + playerID);
            return playerRepository.findByPlayerID(playerID).get(0);
        }
        LOGGER.warning("Player "+playerID+" provided wrong token "+token);
        return null;
    }

    public GameEntity getGame(PlayerEntity player) {
        LOGGER.info("Returning game of player " + player.getPlayer());
        return player.getGame();
    }

    public List<PlayingPiece> getPlayingPieces(PlayerEntity player) {
        LOGGER.info("Returning playing pieces of " + player.getPlayerID());
        return player.getPlayer().getPlayingPieces();
    }

    public boolean buyCard(PlayerEntity player, Slot slot, String token) {
        if (validate(player.getPlayerID(), token)) {
            player.getPlayer().buy(slot);
            LOGGER.info("Player " + player.getPlayerID() + " buys" + slot.getCard().getName());
            return true;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return false;
    }

    public boolean discardCard(PlayerEntity player, Card card, String token) {
        if (validate(player.getPlayerID(), token)) {
            player.getPlayer().discard(card);
            LOGGER.info("Player " + player.getPlayerID() + " Discards card " + card.getName());
            return true;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return false;
    }

    public boolean removeCard(PlayerEntity player, Card card, String token) {
        if (validate(player.getPlayerID(), token)) {
            player.getPlayer().remove(card);
            LOGGER.info("Player " + player.getPlayerID() + " removes card " + card.getName());
            return true;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return false;
    }

    public boolean sellCard(PlayerEntity player, Card card, String token) {
        if (validate(player.getPlayerID(), token)) {
            player.getPlayer().sell(card);
            LOGGER.info("Player " + player.getPlayerID() + " sells card " + card.getName());
            return true;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return false;
    }

    public boolean stealCard(PlayerEntity player, Slot slot, String token) {
        if (validate(player.getPlayerID(), token)) {
            player.getPlayer().steal(slot);
            LOGGER.info("Player " + player.getPlayerID() + " steals " + slot.getCard().getName());
            return true;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return false;
    }

    public boolean performAction(PlayerEntity player, Card card, String token) {
        if (validate(player.getPlayerID(), token)) {
            player.getPlayer().action(card);
            LOGGER.info("Player "+player.getPlayerID()+" performs action with "+card.getName());
            return true;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return false;
    }

    public List<HexSpace> findPath(PlayerEntity player, String token, List<Card> cards, PlayingPiece playingPiece) {
        if (!validate(player.getPlayerID(), token)) {
            LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
            return null;
        }
        LOGGER.info("Player "+player.getPlayerID()+" requested pathfinding.");
        return Pathfinder.getWay(cards, playingPiece);
    }

    public boolean endRound(PlayerEntity player, String token) {
        if (validate(player.getPlayerID(), token)) {
            player.getPlayer().endRound();
            LOGGER.info("Player "+player.getPlayerID()+" ends his round.");
            return true;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return false;
    }
}
