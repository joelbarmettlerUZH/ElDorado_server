package ch.uzh.ifi.seal.soprafs18.service;

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

    private boolean validate(Player p, Game g, String token) {
        Player player = playerRepository.findByPlayerId(p.getPlayerId()).get(0);
        LOGGER.info("Validating user with game");
        return player.getToken().equals(token) && gameRepository.findByGameId(g.getGameId()).get(0).getPlayers().contains(player);
    }

    private boolean validate(Player p, String token) {
        Player player = playerRepository.findByPlayerId(p.getPlayerId()).get(0);
        LOGGER.info("Validating user");
        return player.getToken().equals(token);
    }

    public List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        LOGGER.info("Returning all Players");
        return players;
    }

    public Player getPlayer(int playerID) {
        return playerRepository.findByPlayerId(playerID).get(0);
    }

    public Game getGame(int playerID, String token) {
        Player player = playerRepository.findByPlayerId(playerID).get(0);
        if(!validate(player, token)){
            LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
            return null;
        }
        LOGGER.info("Returning game of player " + player.getPlayerId());
        Game game = player.getBoard();
        System.out.println("*************"+game.getGameId());
        return game;
    }

    public List<PlayingPiece> getPlayingPieces(int id) {
        LOGGER.info("Returning playing pieces of Player" + id);
        Player player = playerRepository.findByPlayerId(id).get(0);
        System.out.println("***********"+player.getPlayingPieces().get(0).getStandsOn().getColor().toString());
        return player.getPlayingPieces();
    }

    public List<Blockade> getBlockades(int id){
        LOGGER.info("Returning Blockades of Player" + id);
        Player player = playerRepository.findByPlayerId(id).get(0);
        return player.getBlockades();
    }

    public List<Card> getHandPile(int id, String token){
        Player player = playerRepository.findByPlayerId(id).get(0);
        if (validate(player, token)) {
            LOGGER.info("Returning Handpile of Player" + id);
            return player.getHandPile();
        }
        LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
        return new ArrayList<Card>();
    }

    public Player buyCard(int id, Slot s, String token) {
        Player player = playerRepository.findByPlayerId(id).get(0);
        Slot slot = slotRepository.findBySlotId(s.getSlotId()).get(0);
        if (validate(player, token)) {
            player.buy(slot);
            LOGGER.info("Player " + player.getPlayerId() + " buys" + slot.getCard().getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
        return player;
    }

    public Player discardCard(int id, Card c, String token) {
        Player player = playerRepository.findByPlayerId(id).get(0);
        Card card = cardRepository.findById(c.getId()).get(0);
        if (validate(player, token)) {
            player.discard(card);
            LOGGER.info("Player " + player.getPlayerId() + " Discards card " + card.getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
        return player;
    }

    public Player removeCard(int id, Card c, String token) {
        Player player = playerRepository.findByPlayerId(id).get(0);
        Card card = cardRepository.findById(c.getId()).get(0);
        if (validate(player, token)) {
            player.remove(card);
            LOGGER.info("Player " + player.getPlayerId() + " removes card " + card.getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
        return player;
    }

    public Player sellCard(int id, Card c, String token) {
        Player player = playerRepository.findByPlayerId(id).get(0);
        Card card = cardRepository.findById(c.getId()).get(0);
        if (validate(player, token)) {
            player.sell(card);
            LOGGER.info("Player " + player.getPlayerId() + " sells card " + card.getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
        return player;
    }

    public Player stealCard(int id, Slot s, String token) {
        Player player = playerRepository.findByPlayerId(id).get(0);
        Slot slot = slotRepository.findBySlotId(s.getSlotId()).get(0);
        if (validate(player, token)) {
            player.steal(slot);
            LOGGER.info("Player " + player.getPlayerId() + " steals " + slot.getCard().getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
        return player;
    }

    public Player movePlayer(int id, List<Card> c, int playingPiece, Point point, String token){
        Player player = playerRepository.findByPlayerId(id).get(0);
        if (validate(player, token)) {
            LOGGER.info("Player " + player.getPlayerId() + " is moving to Field at position ( "+point.getX()+" / "+point.getY()+" ).");
            List<Card> cards = new ArrayList<>();
            if(playingPiece>player.getPlayingPieces().size()){
                LOGGER.warning("Player " + player.getPlayerId() + " tries to move with PlayingPiece '" + playingPiece + " that is out of bounds.");
                return player;
            }
            for(Card card:c){
                Card validCard = cardRepository.findById(card.getId()).get(0);
                if(!player.getHandPile().contains(validCard)){
                    LOGGER.warning("Player " + player.getPlayerId() + " does NOT have the card '" + card.getName() + "' in his Handpile.");
                    return player;
                }
                cards.add(validCard);
                LOGGER.info("Player " + player.getPlayerId() + " uses card '" + card.getName() + "' for his move. ");
            }
            player.move(player.getPlayingPieces().get(playingPiece), cards, player.getBoard().getHexSpace(point));

            return player;
        }
        LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
        return player;
    }

    public Player performAction(int id, Card card, String token) {
        Player player = playerRepository.findByPlayerId(id).get(0);
        if (validate(player, token)) {
            LOGGER.info("Player " + player.getPlayerId() + " is performing action.");
            Card validCard = cardRepository.findById(card.getId()).get(0);
            if(!player.getHandPile().contains(validCard)){
                LOGGER.warning("Player " + player.getPlayerId() + " does NOT have the card '" + card.getName() + "' in his Handpile.");
                return player;
            }
            player.action((ActionCard) validCard);
            LOGGER.info("Player "+player.getPlayerId()+" performs action with "+card.getName());
            return player;
        }
        LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
        return player;
    }

    //TODO: FindPath
    public List<HexSpace> findPath(int id, String token, List<Card> c, PlayingPiece playingPiece) {
        Player player = playerRepository.findByPlayerId(id).get(0);
        if (!validate(player, token)) {
            LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
            return null;
        }
        List<Card> cards = new ArrayList<>();
        for(Card card:c){
            Card validCard = cardRepository.findById(card.getId()).get(0);
            if(!player.getHandPile().contains(validCard)){
                LOGGER.warning("Player " + player.getPlayerId() + " does NOT have the card '" + card.getName() + "' in his Handpile.");
                return new ArrayList<HexSpace>();
            }
            cards.add(validCard);
            LOGGER.info("Player " + player.getPlayerId() + " uses card '" + card.getName() + "' for his move. ");
        }
        LOGGER.info("Player "+player.getPlayerId()+" requested pathfinding.");
        return Pathfinder.getWay(cards, playingPiece);
    }

    public Game endRound(int id, String token) {
        Player player = playerRepository.findByPlayerId(id).get(0);
        if (validate(player, token)) {
            player.endRound();
            LOGGER.info("Player "+player.getPlayerId()+" ends his round.");
            player.endRound();
            return player.getBoard();
        }
        LOGGER.warning("Player "+player.getPlayerId()+" provided wrong token "+token);
        return player.getBoard();
    }

}
