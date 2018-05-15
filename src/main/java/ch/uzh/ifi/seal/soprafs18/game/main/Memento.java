package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Memento  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int momentoId;

    public Memento(Set<HexSpace> reachables, Set<Card> selectedCards, PlayingPiece playingPiece){
        this.reachables = new ArrayList<>(reachables);
        this.selectedCards = new HashSet<>(selectedCards);
        this.playingPiece = playingPiece;
    }

    public Memento(){
        this.reachables = new ArrayList<>();
        this.selectedCards = new HashSet<>();
        this.playingPiece = null;
    }

    /*
    List of HexSpaces that the PathFinder reached.
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<HexSpace> reachables;

    /*
    List of Cards that were used to perform the pathfinding-algorithm.
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<Card> selectedCards;

    /*
    PlayingPiece from which the PathFinder performed its pathfinder-algorithm.
     */
    @Embedded
    private PlayingPiece playingPiece;

    /*
    Refill Memento with new information from the PathFinder.
     */
    public void reset(Game game){
        for(HexSpace hexSpace: reachables){
            hexSpace.setMinimalCost(1000);
            hexSpace.setMinimalDepth(1000);
            hexSpace.setPrevious(new ArrayList<>());
        }
        for (Blockade blockade: game.getBlockades()){
            for (HexSpace hexSpace: blockade.getSpaces()){
                hexSpace.setMinimalCost(1000);
                hexSpace.setMinimalDepth(1000);
                hexSpace.setPrevious(new ArrayList<>());
            }
        }
        this.playingPiece = null;
        this.selectedCards = new HashSet<>();
    }

    public int getMomentoId() {
        return momentoId;
    }

    public void setMomentoId(int momentoId) {
        this.momentoId = momentoId;
    }

    public List<HexSpace> getReachables() {
        return reachables;
    }

    public void setReachables(List<HexSpace> reachables) {
        this.reachables = reachables;
    }

    public Set<Card> getSelectedCards() {
        return selectedCards;
    }

    public void setSelectedCards(Set<Card> selectedCards) {
        this.selectedCards = selectedCards;
    }

    public PlayingPiece getPlayingPiece() {
        return playingPiece;
    }

    public void setPlayingPiece(PlayingPiece playingPiece) {
        this.playingPiece = playingPiece;
    }
}
