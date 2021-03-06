package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.Matrix;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Assembler;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class GameService implements Serializable{

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RoomRepository roomRepository;

    private final Logger LOGGER = Logger.getLogger(RoomService.class.getName());

    public GameService() {
        LOGGER.setLevel(Level.WARNING);

    }

    public List<Game> getAll() {
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);
        LOGGER.info("Returning all games");
        return games;
    }

    public void newGame(RoomEntity room) {
        List<UserEntity> users = room.getUsers();
        LOGGER.info("Shuffling users");
        Collections.shuffle(users);
        int board = room.getBoardnumber();
        LOGGER.info("Creating empty list of players");
        List<Player> players = new ArrayList<>();
        LOGGER.info("Create game with board " + board + " with no players.");
        Game game = new Game(board, room.getRoomID(), room.getName());
        int i = 0;
        for (UserEntity user : users) {
            LOGGER.info("Creating new player "+user.getUserID()+" with name "+user.getName()+" and position "+i);
            Player player = new Player(user.getUserID(), user.getName(), null, user.getToken());
            player.setCharacterNumber(user.getCharacter());
            LOGGER.info("Added player to players");
            players.add(player);
            LOGGER.info("Save Player to database");
            playerRepository.save(player);
            i++;
        }

        LOGGER.info("Setting players of game");
        game.setPlayers(players);
        game.setWinners(new ArrayList<>());
        LOGGER.info("Setting PathMatrix of game");
        game.assemble();
        gameRepository.save(game);
        LOGGER.info("Save game " + game.getGameId() + " to the database");
        for(int n = 0; n<players.size(); n++){
            players.get(n).setBoard(game);
            // Hac for the Presenation to give better cards in the case of the Cutoff Path
            if (game.getBoardId() == 7){
                players.get(n).fakeCards();
            }
            playerRepository.save(players.get(n));
        }
        roomRepository.delete(room);
    }

    public Game getGame(int gameID) {
        LOGGER.info("Returning game with ID " + gameID);
        try {
            return gameRepository.findByGameId(gameID).get(0);
        } catch (Exception e) {
            return null;
        }
    }
    /*
    public List<Player> getPlayers(int id) {
        LOGGER.info("Returning Players of game " + id);
        Game g = gameRepository.findByGameId(id).get(0);
        return g.getPlayers();
    }

    public Player getWinner(int id){
        LOGGER.info("Returning possible winners of Game " + id);
        return gameRepository.findByGameId(id).get(0).getWinner();
    }

    public Player getCurrentPlayer(int id) {
        LOGGER.info("Returning current player of game " + id);
        return gameRepository.findByGameId(id).get(0).getCurrent();
    }

    public Matrix getBoard(int id){
        LOGGER.info("Returning board of game " + id);
        return gameRepository.findByGameId(id).get(0).getPathMatrix();
    }

    public List<Blockade> getBlockades(int id){
        LOGGER.info("Returning board of game " + id);
        return gameRepository.findByGameId(id).get(0).getBlockades();
    }

    public Market getMarket(int id){
        LOGGER.info("Returning board of game " + id);
        return gameRepository.findByGameId(id).get(0).getMarketPlace();
    }
    */
    public void stop(int id) {
        LOGGER.info("Stoppping game " + id);
        Game game = gameRepository.findByGameId(id).get(0);
        game.setRunning(false);
        gameRepository.save(game);
    }
}
