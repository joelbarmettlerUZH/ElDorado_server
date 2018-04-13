package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.Matrix;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.springframework.beans.factory.annotation.Autowired;
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

@Entity
@Data
@Table(name = "GAME_ENTITY")
public class Game implements Serializable {

    @Id
    @Column(name = "GLOBAL_GAMEID")
    private int gameId;

    //Constructor
    public Game(int boardNumber, int gameID){
        //assembler uses boardNumber
        this();
        this.boardId = boardNumber;
        this.gameId = gameID;
        System.out.println("****created game*******");
    }

    public Game(){
        this.boardId = 0;
        this.players = new ArrayList<>();;
        this.running = true;
        this.gameId = -1;
        this.startingSpaces = new ArrayList<>();
        //Temporary
        this.startingSpaces.add(new HexSpace());
        this.winners = new ArrayList<>();
        this.blockades = new ArrayList<>();
        ArrayList<HexSpace> blockadeSpaces = new ArrayList<>();
        blockadeSpaces.add(new BlockadeSpace(COLOR.JUNGLE, 3, 30, 300, new Point(-3, -3), null, 1));
        blockadeSpaces.add(new BlockadeSpace(COLOR.JUNGLE, 4, 40, 400, new Point(-4, -3), null, 1));
        Blockade blockade = new Blockade(blockadeSpaces);
        this.blockades.add(blockade);
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
    @OneToOne
    private Player current; //

    @Embedded
    @ElementCollection
    private List<HexSpace> startingSpaces;

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
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "board", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Player> players;

    /*
    List containing all players that have reached ElDorado.
    Is used to calculate the final winner and to determine when the game is ended.
    Winners are not directly returned in the gameEntity but only on request via the GameService.
     */
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "board", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Player> winners;

    /*
    List of all blockades that are in the game so that we can set the strength
    of all blockades belonging together to 0 when one blockade is removed.
     */

    @Embedded
    @ElementCollection
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
    @JsonIgnore
    private Memento memento;

    /*
    Gets a point with X/Y coordinates and returns a reference to the instance
    of HexSpaceEntity that is located at that position in the pathMatrix.
     */
    public HexSpace getHexSpace(Point point){
        return pathMatrix.get(point.x,point.y);
    }

    public void setPlayers(List<Player> players) {
        this.current = players.get(0);
        this.currentPlayerID = current.getPlayerId();
        this.players = players;
        System.out.println("***set current***");
    }

    public void assemble(){
        Assembler assembler = new Assembler();
        this.pathMatrix = new Matrix(assembler.assembleBoard(boardId, this));
    }
}
