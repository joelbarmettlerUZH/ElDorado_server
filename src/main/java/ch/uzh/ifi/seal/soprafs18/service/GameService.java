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

    private final Logger LOGGER = Logger.getLogger(RoomService.class.getName());
    private FileHandler filehandler;

    public GameService() {
        try {
            filehandler = new FileHandler("Serverlog.log", 1024 * 8, 1, true);
            LOGGER.addHandler(filehandler);
            SimpleFormatter formatter = new SimpleFormatter();
            filehandler.setFormatter(formatter);
            LOGGER.setLevel(Level.ALL);
            filehandler.setLevel(Level.ALL);
        } catch (IOException io) {
            System.out.println("ERROR: Could not set logging handler to file");
        }
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
        Game game = new Game(board, room.getRoomID());
        int i = 0;
        for (UserEntity user : users) {
            LOGGER.info("Creating new player "+user.getUserID()+" with name "+user.getName()+" and position "+i);
            Player player = new Player(user.getUserID(), user.getName(), null, user.getToken());
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
            playerRepository.save(players.get(n));
        }
    }

    public Game getGame(int gameID) {
        LOGGER.info("Returning game with ID " + gameID);
        Game game = gameRepository.findByGameId(gameID).get(0);
        System.out.println("WA-DU-HEK: "+game.getPathMatrix().get(4, 4).getColor());
        return game;
    }

    public List<Player> getPlayers(int id) {
        LOGGER.info("Returning Players of game " + id);
        Game g = gameRepository.findByGameId(id).get(0);
        return g.getPlayers();
    }

    public List<Player> getWinners(int id){
        LOGGER.info("Returning possible winners of Game " + id);
        return gameRepository.findByGameId(id).get(0).getWinners();
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

    public void stop(int id) {
        LOGGER.info("Stoppping game " + id);
        Game game = gameRepository.findByGameId(id).get(0);
        game.setRunning(false);
        gameRepository.save(game);
    }
}
