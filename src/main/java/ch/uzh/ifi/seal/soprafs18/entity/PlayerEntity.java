package ch.uzh.ifi.seal.soprafs18.entity;


import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.SpecialActions;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.player.CardAction;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PLAYER_ENTITY")
public class PlayerEntity {

    public PlayerEntity(int id, Player player, String token, int gameID){
        this.playerID = id;
        this.player = player;
        this.token = token;
        this.gameID = gameID;
    }

    public PlayerEntity(){

    }

    private int playerID;
    @Id
    @Column(name = "PLAYERID")
    public int getPlayerID() {
        return playerID;
    }

    private String token;
    @JsonIgnore
    @Column(name = "TOKEN")
    public String getToken(){
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }

    private Player player;
    @JsonIgnore
    public Player getPlayer() {
        return player;
    }

    private int gameID;
    @Column(name = "GAMEID")
    public int getGameID(){
        return gameID;
    }
    public void setGameID(int gameID){
        this.gameID = gameID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}