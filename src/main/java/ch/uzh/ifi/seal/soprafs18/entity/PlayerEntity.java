package ch.uzh.ifi.seal.soprafs18.entity;


import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.SpecialActions;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.player.CardAction;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "PLAYER_ENTITY")
public class PlayerEntity implements Serializable {

    public PlayerEntity(int id, Player player, String token, GameEntity game){
        this.playerID = id;
        this.player = player;
        this.token = token;
        this.game = game;
    }

    public PlayerEntity(){}

    @Id
    @Column(name = "GLOBAL_PLAYERID")
    private int playerID;

    @JsonIgnore
    @Column(name = "TOKEN")
    private String token;

    @Embedded
    private Player player;

    @ManyToOne
    @JoinColumn(name="GAMEENTITY")
    @JsonBackReference
    private GameEntity game;

}