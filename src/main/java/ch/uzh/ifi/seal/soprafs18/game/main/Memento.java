package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;

import java.util.List;

public class Memento {

    /*
    List of HexSpaces that the PathFinder reached.
     */
    private List<HexSpace> reachables;

    /*
    List of Cards that were used to perform the pathfinding-algorithm.
     */
    private List<Card> selectedCards;

    /*
    PlayingPiece from which the PathFinder performed its pathfinder-algorithm.
     */
    private PlayingPiece playingPiece;

    /*
    Refill Memento with new information from the PathFinder.
     */
    public void reset(List<HexSpace> reachables, List<Card> selectedCards){

    }

    /*
    Deletes all entries from reachables and selectedCards, playingPiece can be set to Null.
     */
    public void reset(){

    }

    public List<HexSpace> getReachables() {
        return reachables;
    }

    public List<Card> getSelectedCards() {
        return selectedCards;
    }

    public PlayingPiece getPlayingPiece() {
        return playingPiece;
    }
}
