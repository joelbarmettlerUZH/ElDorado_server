package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.Matrix;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Game  implements Serializable {

    //Constructor
    public Game(int boardNumber, List<Player> players, int gameID){
        //assembler uses boardNumber
        this();
        this.boardId = boardNumber;
        this.players = players;
        this.ID = gameID;
        System.out.println("****created game*******");
    }

    public Game(){
        this.boardId = 0;
        this.players = new ArrayList<>();
        this.running = true;
        this.ID = -1;
        HexSpace[][] temp = new HexSpace[2][2];
        for (int row = 0; row < 2; row ++)
            for (int col = 0; col < 2; col++)
                temp[row][col] = new HexSpace();
        this.pathMatrix = new Matrix(temp);
        this.winners = new ArrayList<>();
        this.blockades = new ArrayList<>();
        this.marketPlace = new Market();
        this.memento = new Memento();
    }


    //private Assembler assembler;

    private int boardId;

    /*
    Globally unique Identifier to identify a running game
     */
    private int ID;

    /*
    Player that can currently play the round. When one player calls endRound,
    the turn of the next player starts. The next player is always the one with either
    the next bigger ID or, there is none, the one with ID 0.
    With N players: current = (current + 1) % N.
     */
    @Transient
    @JsonIgnore
    private Player current; //

    /*
    Indicates whether the game is still in a running state or whether it has finished.
    Only allow manipulations when the running boolean is True.
     */
    private boolean running;

    /*
    Matrix of HexSpaces representing the whole playable field, also containing
    all blockades and starting-ending Fields. The Matrix has no Null-Entries but
    instances of HexSpaces with infinite costs and a specific colour wherever
    no HexSpaceEntity is in located on the GameEntity.
     */
    @Embedded
    private Matrix pathMatrix;

    /*
    List of all players participating in the GameEntity.
     */
    @Transient
    @JsonIgnore
    private List<Player> players;

    /*
    List containing all players that have reached ElDorado.
    Is used to calculate the final winner and to determine when the game is ended.
     */
    @Transient
    @JsonIgnore
    private List<Player> winners;

    /*
    List of all blockades that are in the game so that we can set the strength
    of all blockades belonging together to 0 when one blockade is removed.
     */
    @Transient
    @JsonIgnore
    private List<Blockade> blockades;

    /*
    Instance of the current Marketplace that contains active and passive cards.
     */
    @Transient
    @JsonIgnore
    private Market marketPlace;

    /*
    Instance of the memento which save the state of the HexSpaces while the
    PathFinder modifies them, so that the HexSpaces can be reset.
     */
    @Transient
    @JsonIgnore
    private Memento memento;

    /*
    Gets a point with X/Y coordinates and returns a reference to the instance
    of HexSpaceEntity that is located at that position in the pathMatrix.
     */
    public HexSpace getHexSpace(Point point){
        return null;
    }

    public int getID() {
        return ID;
    }

    public Player getCurrent() {
        return current;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public Matrix getPathMatrix() {
        return pathMatrix;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public List<Blockade> getBlockades() {
        return blockades;
    }

    public Market getMarketPlace() {
        return marketPlace;
    }

    public Memento getMemento() {
        return memento;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setPlayers(List<Player> players) {
        this.current = players.get(0);
        this.players = players;
        System.out.println("***set current***");
    }

    public void setCurrent(Player current) {
        this.current = current;
    }

    public void setPathMatrix() {

    }

    public void assemble(){
        Assembler assembler = new Assembler();
        this.pathMatrix = new Matrix(assembler.assembleBoard(boardId, this));
    }

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    public void setBlockades(List<Blockade> blockades) {
        this.blockades = blockades;
    }

    public void setMarketPlace(Market marketPlace) {
        this.marketPlace = marketPlace;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
