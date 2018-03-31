package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

import java.awt.*;
import java.util.List;


public class Game {

    /*
    Globally unique Identifier to identify a running game
     */
    protected int gameID;

    /*
    Player that can currently play the round. When one player calls endRound,
    the turn of the next player starts. The next player is always the one with either
    the next bigger ID or, there is none, the one with ID 0.
    With N players: current = (current + 1) % N.
     */
    protected Player current; //

    /*
    Indicates whether the game is still in a running state or whether it has finished.
    Only allow manipulations when the running boolean is True.
     */
    protected boolean running = true;

    /*
    Matrix of HexSpaces representing the whole playable field, also containing
    all blockades and starting-ending Fields. The Matrix has no Null-Entries but
    instances of HexSpaces with infinite costs and a specific colour wherever
    no HexSpace is in located on the Game.
     */
    protected HexSpace[][] pathMatrix;


    /*
    List of all players participating in the Game.
     */
    protected List<Player> players;

    /*
    List containing all players that have reached ElDorado.
    Is used to calculate the final winner and to determine when the game is ended.
     */
    protected List<Player> winners;

    /*
    List of all blockades that are in the game so that we can set the strength
    of all blockades belonging together to 0 when one blockade is removed.
     */
    protected List<Blockade> blockades;

    /*
    Instance of the current Marketplace that contains active and passive cards.
     */
    protected Market marketPlace;

    /*
    Instance of the memento which save the state of the HexSpaces while the
    PathFinder modifies them, so that the HexSpaces can be reset.
     */
    protected Memento memento;

    /*
    Gets a point with X/Y coordinates and returns a reference to the instance
    of HexSpace that is located at that position in the pathMatrix.
     */
    public HexSpace getHexSpace(Point point){
        return null;
    }
}
