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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GAME_ENTITY")
public class GameEntity implements Serializable {

    public GameEntity(Game game, int gameID, List<PlayerEntity> players){
        this.game = game;
        this.gameID = gameID;
        this.players = players;
    }

    public GameEntity(){ }

    @Id
    @Column(name = "GLOBAL_GAMEID")
    private int gameID;

    @Embedded
    private Game game;

    @Column(name = "PLAYER_ENTITIES")
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "game", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PlayerEntity> players;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        System.out.println("++++"+game.getID());
        this.game = game;
        System.out.println("Game set (in theory)");
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntity> players) {
        this.players = players;
    }
}
