package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.entity.PlayerEntity;
import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class GameService {

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
            LOGGER.setLevel(Level.FINEST);
            filehandler.setLevel(Level.INFO);
        } catch (IOException io) {
            System.out.println("ERROR: Could not set logging handler to file");
        }
    }

    public List<GameEntity> getAll() {
        List<GameEntity> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);
        LOGGER.info("Returning all games");
        return games;
    }

    public void newGame(RoomEntity room) {
        List<UserEntity> users = room.getUsers();
        LOGGER.fine("Shuffling users");
        Collections.shuffle(users);
        String gameName = room.getName();
        int board = room.getBoardnumber();
        LOGGER.fine("Creating empty list of players and player entities");
        List<Player> players = new ArrayList<>();
        List<PlayerEntity> playerEntities = new ArrayList<>();
        LOGGER.fine("Create game with board " + board + " with no players.");
        Game game = new Game(board, null);
        LOGGER.fine("Creating gameEntity " + gameName + " corresponding to board");
        GameEntity gameEntity = new GameEntity(game, gameName);
        gameRepository.save(gameEntity);
        LOGGER.fine("Saved gameEntity to database");
        int i = 0;
        for (UserEntity user : users) {
            LOGGER.fine("Creating new player "+user.getUserID()+" with name "+user.getName()+" and position "+i);
            Player player = new Player(user.getUserID(), user.getName(), game, i);
            LOGGER.fine("Added player to players");
            players.add(player);
            LOGGER.fine("Create new playerEntity "+user.getUserID()+" with player "+player.getId()+" and gameentity "+gameEntity.getName()+" and token "+user.getToken());
            PlayerEntity playerEntity = new PlayerEntity(user.getUserID(), player, gameEntity, user.getToken());
            LOGGER.fine("Adding player entity to list of playerEntities");
            playerEntities.add(playerEntity);
            LOGGER.fine("Save playerEntity to database");
            playerRepository.save(playerEntity);
            i++;
            LOGGER.info("Converted " + user.getUserID() + " to playerEntity and created new Player");
        }
        LOGGER.fine("Setting players of game");
        game.setPlayers(players);
        game.setID(gameEntity.getGameID());
        LOGGER.fine("Set game ID to "+game.getID());
        gameEntity.setPlayers(playerEntities);
        LOGGER.fine("Setting playerEntities to gameEntity and saving it to database");
        gameRepository.save(gameEntity);
        LOGGER.info("Save game " + gameEntity.getGameID() + "to the database");
    }

    public GameEntity getGame(int gameID) {
        LOGGER.info("Returning game with ID " + gameID);
        return gameRepository.findByGameID(gameID).get(0);
    }


    public PlayerEntity getCurrentPlayer(GameEntity game) {
        LOGGER.info("Returning current player " + game.getCurrentPlayer().getPlayerID() + " of game " + game.getGameID());
        return game.getCurrentPlayer();
    }


    public List<PlayerEntity> getPlayers(GameEntity game) {
        LOGGER.info("Returning Players of game " + game.getGameID());
        return game.getPlayers();
    }


    public void stop(GameEntity game) {
        LOGGER.info("Stoppping game " + game.getGameID());
        game.getGame().setRunning(false);
    }


    public PlayerEntity getWinner(GameEntity game) {
        LOGGER.info("Returning winners of game " + game.getGameID());
        return game.getWinner().get(0);
    }


    public HexSpace[][] getBoard(GameEntity game) {
        LOGGER.info("Returning Board of game " + game.getGameID());
        return game.getBoard();
    }


}
