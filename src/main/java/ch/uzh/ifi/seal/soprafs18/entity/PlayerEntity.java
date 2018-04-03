package ch.uzh.ifi.seal.soprafs18.entity;


import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.SpecialActions;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.CardAction;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class PlayerEntity {
    @Id
    @GeneratedValue
    @Column(name = "PLYERID")
    private int plaerID;

    @Column(name = "PLAYER")
    private Player player;

    @Column(name = "NAME")
    public String player() {
        return player.getName();
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

    @Column(name = "GAME")
    @ManyToOne
    @JoinColumn(name = "GAMEID")
    private Game game() {
        return player.getGame();
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

}