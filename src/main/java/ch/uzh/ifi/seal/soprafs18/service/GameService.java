package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    public GameRepository gameRepository;

    public GameEntity getGame(int gameID){
        return gameRepository.findByGameID(gameID).get(0);
    }

    public Player getCurrentPlayer(GameEntity game){
        return game.getCurrentPlayer();
    }

    public List<Player> getPlayers(GameEntity game){
        return game.getPlayers();
    }

    public void stop(GameEntity game){
        game.getGame().setRunning(false);
    }

    public Player getWinner(GameEntity game){
        return game.getWinner().get(0);
    }

    public HexSpace[][] getBoard(GameEntity game){
        return game.getBoard();
    }



}
