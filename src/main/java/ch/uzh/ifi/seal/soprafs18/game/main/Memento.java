package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;

public class Memento {

    /*
    List of HexSpaces that the PathFinder reached.
     */
    private HexSpace[] reachables;

    /*
    List of Cards that were used to perform the pathfinding-algorithm.
     */
    private Card[] selectedCards;

    /*
    PlayingPiece from which the PathFinder performed its pathfinder-algorithm.
     */
    private PlayingPiece playingPiece;

    /*
    Refill Memento with new information from the PathFinder.
     */
    public void reset(HexSpace[] reachables, Card[] selectedCards){

    }

    /*
    Deletes all entries from reachables and selectedCards, playingPiece can be set to Null.
     */
    public void reset(){

    }
}
