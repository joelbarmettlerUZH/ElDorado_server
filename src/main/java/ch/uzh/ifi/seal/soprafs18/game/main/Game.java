package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.Matrix;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
public class Game implements Serializable {

    //Constructor
    public Game(int boardNumber, int gameID){
        //assembler uses boardNumber
        this();
        this.ID = gameID;
        System.out.println("****created game*******");
    }

    public Game(){
        this.players = new ArrayList<>();;
        this.running = true;
        this.ID = -1;
        HexSpace[][] temp = new HexSpace[2][2];
        for (int row = 0; row < 2; row ++)
            for (int col = 0; col < 2; col++)
                temp[row][col] = new HexSpace();
        this.pathMatrix = new Matrix(temp);
        this.winners = new ArrayList<>();
        this.blockades = new ArrayList<>();
        List<BlockadeSpace> blockadeSpaces = new ArrayList<>();
        blockadeSpaces.add(new BlockadeSpace(COLOR.JUNGLE, 3, 30, 300, new ArrayList<>(), new Point(-3, -3), null, 1));
        blockadeSpaces.add(new BlockadeSpace(COLOR.JUNGLE, 4, 40, 400, new ArrayList<>(), new Point(-4, -3), null, 1));
        Blockade blockade = new Blockade(blockadeSpaces);
        this.blockades.add(blockade);
        this.marketPlace = new Market();
        this.memento = new Memento();
    }

    /*
    Globally unique Identifier to identify a running game
     */
    @JsonIgnore
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
    Serves as an identifier for the current player for hibernate
     */
    private int currentPlayerID;

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
    Winners are not directly returned in the gameEntity but only on request via the GameService.
     */
    @Transient
    @JsonIgnore
    private List<Player> winners;

    /*
    List of all blockades that are in the game so that we can set the strength
    of all blockades belonging together to 0 when one blockade is removed.
     */
    @JsonIgnore
    @Transient
    private List<Blockade> blockades;

    /*
    Instance of the current Marketplace that contains active and passive cards.
     */
    @Embedded
    private Market marketPlace;

    /*
    Instance of the memento which save the state of the HexSpaces while the
    PathFinder modifies them, so that the HexSpaces can be reset.
    Json does not need to be in the gameEntity
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

    public void setPlayers(List<Player> players) {
        this.current = players.get(0);
        this.currentPlayerID = current.getPlayerID();
        this.players = players;
        System.out.println("***set current***");
    }

}
