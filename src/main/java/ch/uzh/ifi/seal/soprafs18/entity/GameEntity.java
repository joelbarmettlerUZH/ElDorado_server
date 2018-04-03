package ch.uzh.ifi.seal.soprafs18.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GAME")
public class GameEntity {
    @Id
    @GeneratedValue
    @Column(name = "GAMEID")
    private int gameID;

    @Column(name = "GAME")
    protected Game game;

    @Column(name = "CURRENTPLAYER")
    public Player getCurrentPlayer(){
        return game.getCurrent();
    }

    @Column(name = "PLAYERS")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    public List<Player> getPlayers(){
        return game.getPlayers();
    }

    @Column(name = "RUNNING")
    public boolean getRunning(){
        return game.isRunning();
    }

    @Column(name = "WINNER")
    public List<Player> getWinner(){
        return game.getWinners();
    }

    @Column(name = "BOARDID")
    public int getBoardID(){
        return game.getGameID();
    }

    @Column(name = "BOARD")
    public HexSpace[][] getBoard(){
        return game.getPathMatrix();
    }

    @Column(name = "BLOCKADE")
    public List<Blockade> getBlockade(){
        return game.getBlockades();
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public ch.uzh.ifi.seal.soprafs18.game.main.Game getGame() {
        return game;
    }

    public void setGame(ch.uzh.ifi.seal.soprafs18.game.main.Game game) {
        this.game = game;
    }

}
