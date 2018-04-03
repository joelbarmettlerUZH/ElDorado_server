package ch.uzh.ifi.seal.soprafs18.entity;


import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.SpecialActions;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.player.CardAction;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PLAYER_ENTITY")
public class PlayerEntity {

    public PlayerEntity(Player player, GameEntity gameEntity){
        this.player = player;
        this.game = gameEntity;
    }

    public PlayerEntity(){

    }

    private int playerID;
    @Id
    @GeneratedValue
    @Column(name = "PLAYERID")
    public int getPlayerID() {
        return playerID;
    }

    public Player player;
    @Transient
    public Player getPlayer() {
        return player;
    }

    @Column(name = "NAME")
    public String player() {
        return player.getName();
    }

    private GameEntity game;
    @ManyToOne
    @JoinColumn(name = "GAMEID")
    public GameEntity getGame() {
        return game;
    }

    @JsonIgnore
    @Column(name = "TOKEN")
    protected String token() {
        return player.getToken();
    }

    @Column(name = "COINS")
    private float coins() {
        return player.getCoins();
    }

    @Column(name = "BOUGHT")
    private boolean bought() {
        return player.getBought();
    }

    @Column(name = "PLAYINGPIECES")
    @OneToMany
    private List<PlayingPiece> placingPieces() {
        return player.getPlayingPieces();
    }

    @Column(name = "BLOCKADES")
    @OneToMany
    private List<Blockade> blockades() {
        return player.getBlockades();
    }

    @Column(name = "HANDPILE")
    private List<Card> handPile() {
        return player.getHandPile();
    }

    @Column(name = "DRAWPILE")
    private List<Card> drawPile() {
        return player.getDrawPile();
    }

    @Column(name = "DISCARDPILE")
    private List<Card> discardPile() {
        return player.getDiscardPile();
    }

    @Column(name = "SPECIALACTION")
    private SpecialActions specialActions(){
        return player.getSpecialAction();
    }

    @Column(name = "HISTORY")
    private List<CardAction> history(){
        return player.getHistory();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}