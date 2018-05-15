package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.Matrix;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "GAME_ENTITY")
public class Game implements Serializable {

    @Id
    @Column(name = "GLOBAL_GAMEID")
    private int gameId;

    //Constructor
    public Game(int boardNumber, int gameID, String gameName) {
        //assembler uses boardNumber
        this();
        this.boardId = boardNumber;
        this.gameId = gameID;
        this.gameName = gameName;
        System.out.println("****created game*******");
    }

    public Game() {
        this.boardId = 0;
        this.players = new ArrayList<>();
        ;
        this.running = true;
        this.gameId = -1;
        this.startingSpaces = new ArrayList<>();
        this.winners = new ArrayList<>();
        this.marketPlace = new Market();
        this.memento = new Memento();
        this.elDoradoSpaces = new ArrayList<>();

    }


    //private Assembler assembler;

    private String gameName;

    private int boardId;

    /*
    Player that can currently play the round. When one player calls endRound,
    the turn of the next player starts. The next player is always the one with either
    the next bigger ID or, there is none, the one with ID 0.
    With N players: current = (current + 1) % N.
     */
    @OneToOne
    private Player current; //

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<HexSpace> startingSpaces;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<HexSpace> elDoradoSpaces;

    /*
    Serves as an identifier for the current player for hibernate
     */
    private int currentPlayerNumber;

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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Player> players;

    /*
    List containing all players that have reached ElDorado.
    Is used to calculate the final winner and to determine when the game is ended.
    Winners are not directly returned in the gameEntity but only on request via the GameService.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Player> winners;

    /*
    List of all blockades that are in the game so that we can set the strength
    of all blockades belonging together to 0 when one blockade is removed.

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BLOCKADES", joinColumns = @JoinColumn(name = "gameId"), inverseJoinColumns = @JoinColumn(name = "waduhek"))
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Memento memento;

    /*
    Gets a point with X/Y coordinates and returns a reference to the instance
    of HexSpaceEntity that is located at that position in the pathMatrix.
     */
    public HexSpace getHexSpace(Point point) {
        return pathMatrix.get(point.x, point.y);
    }

    public void setPlayers(List<Player> players) {
        Collections.shuffle(players);
        this.players = players;
        this.currentPlayerNumber = 0;
        this.current = players.get(this.currentPlayerNumber);

        System.out.println("***set current***");
    }

    public void assemble() {
        Assembler assembler = new Assembler();
        System.out.println(this.getGameId());
        this.pathMatrix = new Matrix(assembler.assembleBoard(this.boardId));
        this.startingSpaces.addAll(assembler.getStartingFields(this.boardId));
        System.out.println("test");
        assembler.getElDoradoFields(this.boardId).forEach(x -> System.out.println(x.toString()));
        this.elDoradoSpaces.addAll(assembler.getElDoradoFields(this.boardId));
        this.blockades = assembler.getBlockades(this);

        //
        this.blockades.forEach(x -> System.out.println("blockades returned by Assemble" + x.toString()));

        for (Blockade blockade : blockades) {
            for (BlockadeSpace blockadeSpace : blockade.getSpaces()) {
                blockadeSpace.setParentBlockade(blockade.getBlockadeId());
            }
        }
        System.out.println("post ghettos blockados");
        int i = 3;
        int j = 0;
        do {
            for (Player player : players) {
                player.addPlayingPiece(new PlayingPiece(startingSpaces.get(i), j));
                i--;
            }
            j++;
        }
        while (players.size() - j == 1);


    }

    public void endRound() {
        currentPlayerNumber = (currentPlayerNumber + 1) % players.size();
        current = players.get(currentPlayerNumber);
    }

    public Player getWinner() {
        if (winners.size() == 0) {
            return null;
        }
        if (this.winners.size() < 2) {
            return this.winners.get(0);
        }
        for (Player potentialWinner : winners) {
            boolean wins = true;
            for (Player player : winners) {
                wins = wins & potentialWinner.getCollectedBlockades().size() > player.getCollectedBlockades().size();
            }
            if (wins) {
                return potentialWinner;
            }
        }
        for (Player potentialWinner : winners) {
            boolean wins = true;
            for (Player player : winners) {
                int sumPotentialWinner = potentialWinner.getCollectedBlockades().stream().mapToInt(Integer::intValue).sum();
                int sumPlayer = player.getCollectedBlockades().stream().mapToInt(Integer::intValue).sum();
                wins = wins & sumPotentialWinner > sumPlayer;
            }
            if (wins) {
                return potentialWinner;
            }
        }
        return winners.get(0);
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public Player getCurrent() {
        return current;
    }

    public void setCurrent(Player current) {
        this.current = current;
    }

    public List<HexSpace> getStartingSpaces() {
        return startingSpaces;
    }

    public void setStartingSpaces(List<HexSpace> startingSpaces) {
        this.startingSpaces = startingSpaces;
    }

    public List<HexSpace> getElDoradoSpaces() {
        return elDoradoSpaces;
    }

    public void setElDoradoSpaces(List<HexSpace> elDoradoSpaces) {
        this.elDoradoSpaces = elDoradoSpaces;
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int currentPlayerNumber) {
        this.currentPlayerNumber = currentPlayerNumber;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Matrix getPathMatrix() {
        return pathMatrix;
    }

    public void setPathMatrix(Matrix pathMatrix) {
        this.pathMatrix = pathMatrix;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    public List<Blockade> getBlockades() {
        return blockades;
    }

    public void setBlockades(List<Blockade> blockades) {
        this.blockades = blockades;
    }

    public Market getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(Market marketPlace) {
        this.marketPlace = marketPlace;
    }

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
