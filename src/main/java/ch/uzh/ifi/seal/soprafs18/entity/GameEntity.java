package ch.uzh.ifi.seal.soprafs18.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GAME_ENTITY")
public class GameEntity {

    public GameEntity(Game game, int gameID, int[] players){
        this.game = game;
        this.gameID = gameID;
        this.players = players;
    }

    public GameEntity(){

    }

    private int gameID;
    @Id
    @Column(name = "GAMEID")
    public int getGameID(){
        return gameID;
    }
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    private Game game;
    @JsonIgnore
    @Embedded
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }

    private int[] players;
    @Column(name = "PLAYER")
    @Transient
    public int[] getPlayers(){
        return players;
    }
    public void setPlayers(int[] players){
        this.players = players;
    }


}
