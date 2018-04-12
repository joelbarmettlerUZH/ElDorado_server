package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.entity.PlayerEntity;
import ch.uzh.ifi.seal.soprafs18.game.cards.*;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.main.Pathfinder;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import ch.uzh.ifi.seal.soprafs18.repository.CardRepository;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
import ch.uzh.ifi.seal.soprafs18.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class PlayerService  implements Serializable {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private SlotRepository slotRepository;

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

    private boolean validate(PlayerEntity pE, GameEntity gE, String token) {
        PlayerEntity playerEntity = playerRepository.findByPlayerID(pE.getPlayerID()).get(0);
        Player player = playerEntity.getPlayer();
        LOGGER.info("Validating user with game");
        return playerEntity.getToken().equals(token) && gameRepository.findByGameID(gE.getGameID()).get(0).getGame().getPlayers().contains(player);
    }

    private boolean validate(PlayerEntity pE, String token) {
        PlayerEntity playerEntity = playerRepository.findByPlayerID(pE.getPlayerID()).get(0);
        LOGGER.info("Validating user");
        return playerEntity.getToken().equals(token);
    }

    public List<PlayerEntity> getPlayers(){
        List<PlayerEntity> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        LOGGER.info("Returning all Players");
        return players;
    }

    public PlayerEntity getPlayer(int playerID) {
        return playerRepository.findByPlayerID(playerID).get(0);
    }

    public Game getGame(int playerID, String token) {
        PlayerEntity player = playerRepository.findByPlayerID(playerID).get(0);
        if(!validate(player, token)){
            LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
            return null;
        }
        LOGGER.info("Returning game of player " + player.getPlayerID());
        Game game = player.getGame().getGame();
        System.out.println("*************"+game.getGameId());
        return game;
    }

    public List<PlayingPiece> getPlayingPieces(int id) {
        LOGGER.info("Returning playing pieces of Player" + id);
        PlayerEntity player = playerRepository.findByPlayerID(id).get(0);
        System.out.println("***********"+player.getPlayer().getPlayingPieces().get(0).getStandsOn().getColor().toString());
        return player.getPlayer().getPlayingPieces();
    }

    public List<Blockade> getBlockades(int id){
        LOGGER.info("Returning Blockades of Player" + id);
        PlayerEntity player = playerRepository.findByPlayerID(id).get(0);
        return player.getPlayer().getBlockades();
    }

    public List<Card> getHandPile(int id, String token){
        PlayerEntity player = playerRepository.findByPlayerID(id).get(0);
        if (validate(player, token)) {
            LOGGER.info("Returning Handpile of Player" + id);
            return player.getPlayer().getHandPile();
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return new ArrayList<Card>();
    }

    public PlayerEntity buyCard(int id, Slot s, String token) {
        PlayerEntity player = playerRepository.findByPlayerID(id).get(0);
        Slot slot = slotRepository.findBySlotId(s.getSlotId()).get(0);
        if (validate(player, token)) {
            player.getPlayer().buy(slot);
            LOGGER.info("Player " + player.getPlayerID() + " buys" + slot.getCard().getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return player;
    }

    public PlayerEntity discardCard(int id, Card c, String token) {
        PlayerEntity player = playerRepository.findByPlayerID(id).get(0);
        Card card = cardRepository.findById(c.getId()).get(0);
        if (validate(player, token)) {
            player.getPlayer().discard(card);
            LOGGER.info("Player " + player.getPlayerID() + " Discards card " + card.getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return player;
    }

    public PlayerEntity removeCard(int id, Card c, String token) {
        PlayerEntity player = playerRepository.findByPlayerID(id).get(0);
        Card card = cardRepository.findById(c.getId()).get(0);
        if (validate(player, token)) {
            player.getPlayer().remove(card);
            LOGGER.info("Player " + player.getPlayerID() + " removes card " + card.getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return player;
    }

    public PlayerEntity sellCard(int id, Card c, String token) {
        PlayerEntity player = playerRepository.findByPlayerID(id).get(0);
        Card card = cardRepository.findById(c.getId()).get(0);
        if (validate(player, token)) {
            player.getPlayer().sell(card);
            LOGGER.info("Player " + player.getPlayerID() + " sells card " + card.getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return player;
    }

    public PlayerEntity stealCard(int id, Slot s, String token) {
        PlayerEntity player = playerRepository.findByPlayerID(id).get(0);
        Slot slot = slotRepository.findBySlotId(s.getSlotId()).get(0);
        if (validate(player, token)) {
            player.getPlayer().steal(slot);
            LOGGER.info("Player " + player.getPlayerID() + " steals " + slot.getCard().getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return player;
    }

    public PlayerEntity movePlayer(int id, List<Card> c, int playingPiece, Point point, String token){
        PlayerEntity playerEntity = playerRepository.findByPlayerID(id).get(0);
        if (validate(playerEntity, token)) {
            LOGGER.info("Player " + playerEntity.getPlayerID() + " is moving to Field at position ( "+point.getX()+" / "+point.getY()+" ).");
            Player player = playerEntity.getPlayer();
            List<Card> cards = new ArrayList<>();
            if(playingPiece>player.getPlayingPieces().size()){
                LOGGER.warning("Player " + playerEntity.getPlayerID() + " tries to move with PlayingPiece '" + playingPiece + " that is out of bounds.");
                return playerEntity;
            }
            for(Card card:c){
                Card validCard = cardRepository.findById(card.getId()).get(0);
                if(!player.getHandPile().contains(validCard)){
                    LOGGER.warning("Player " + playerEntity.getPlayerID() + " does NOT have the card '" + card.getName() + "' in his Handpile.");
                    return playerEntity;
                }
                cards.add(validCard);
                LOGGER.info("Player " + playerEntity.getPlayerID() + " uses card '" + card.getName() + "' for his move. ");
            }
            player.move(player.getPlayingPieces().get(playingPiece), cards, player.getBoard().getHexSpace(point));

            return playerEntity;
        }
        LOGGER.warning("Player "+playerEntity.getPlayerID()+" provided wrong token "+token);
        return playerEntity;
    }

    public PlayerEntity performAction(int id, Card card, String token) {
        PlayerEntity playerEntity = playerRepository.findByPlayerID(id).get(0);
        if (validate(playerEntity, token)) {
            LOGGER.info("Player " + playerEntity.getPlayerID() + " is performing action.");
            Card validCard = cardRepository.findById(card.getId()).get(0);
            Player player = playerEntity.getPlayer();
            if(!player.getHandPile().contains(validCard)){
                LOGGER.warning("Player " + playerEntity.getPlayerID() + " does NOT have the card '" + card.getName() + "' in his Handpile.");
                return playerEntity;
            }
            player.action((ActionCard) validCard);
            LOGGER.info("Player "+player.getPlayerID()+" performs action with "+card.getName());
            return playerEntity;
        }
        LOGGER.warning("Player "+playerEntity.getPlayerID()+" provided wrong token "+token);
        return playerEntity;
    }

    //TODO: FindPath
    public List<HexSpace> findPath(int id, String token, List<Card> c, PlayingPiece playingPiece) {
        PlayerEntity playerEntity = playerRepository.findByPlayerID(id).get(0);
        if (!validate(playerEntity, token)) {
            LOGGER.warning("Player "+playerEntity.getPlayerID()+" provided wrong token "+token);
            return null;
        }
        List<Card> cards = new ArrayList<>();
        Player player = playerEntity.getPlayer();
        for(Card card:c){
            Card validCard = cardRepository.findById(card.getId()).get(0);
            if(!player.getHandPile().contains(validCard)){
                LOGGER.warning("Player " + playerEntity.getPlayerID() + " does NOT have the card '" + card.getName() + "' in his Handpile.");
                return new ArrayList<HexSpace>();
            }
            cards.add(validCard);
            LOGGER.info("Player " + playerEntity.getPlayerID() + " uses card '" + card.getName() + "' for his move. ");
        }
        LOGGER.info("Player "+player.getPlayerID()+" requested pathfinding.");
        return Pathfinder.getWay(cards, playingPiece);
    }

    public GameEntity endRound(int id, String token) {
        PlayerEntity player = playerRepository.findByPlayerID(id).get(0);
        if (validate(player, token)) {
            player.getPlayer().endRound();
            LOGGER.info("Player "+player.getPlayerID()+" ends his round.");
            player.getPlayer().endRound();
            return player.getGame();
        }
        LOGGER.warning("Player "+player.getPlayerID()+" provided wrong token "+token);
        return player.getGame();
    }

}
