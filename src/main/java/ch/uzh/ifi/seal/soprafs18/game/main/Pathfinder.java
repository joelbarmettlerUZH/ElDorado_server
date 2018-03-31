package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;

import java.util.List;
import java.util.Set;

public class Pathfinder {

    /*
    The PlayingPiece has a HexSpace. This hexSpace is the first entry in the reachables array,
    because the player can obviously reach its own location. From there on, the neighbours of all
    reachables are checked whether they are reachable with the given set of cards.The PathFinder
    differentiates between two cases: When the Cards-array consists of just one entry of type MovingCard,
    he looks what colours the selected Card supports. Then, for each of the X different colours, he looks
    what HexSpaces are reachables with just using this colour by always adding the reachable neighbours to
    an array of colourspecific reachables an, in the same time, sets the previous path of how he got to the
    exSpace (the path he came from, so the path from the neighboured hexSpace + the neighbour itself, including
    barricadeSpaces) the cost that were needed to enter the field (the cards strengthLeft value - the fields stregth)
    and the depthLeft value, which is just the depth from the neighbour - 1. The PathFinder keeps track of which
    HexSpaces he found the best way already with a local variable currentIndex. CurrentIndex marks a position in the Array
    of reachable HexSpaces. To the left of the currentIndex, there are the HexSpaces that were already processed, e.g. of
    which the neighbours were already checked to be reachable. To the right of the currentIndex, there are the HexSpaces of
    which not all neighbours were yet checked. Whenever the pathFinder checks a HexSpac at currentIndex, it appends all its
    neighbours (if not yet in the array) to the reachables. When an array is already in the reachables, it checks whether
    it would now be reached with a cheaper path. If so, the corresponding HexSpace is moved from the left of the reachables
    to the end (because now we have to see whether one of its neighbours can be reached cheaper as well). PathFinder
    finishes when the currentIndex equals the length of the Array reachables. In the end, he is left with X arrays, each
    contining the reachable HexSpaces with using the Xth colour. The union of these arrays then is the returned
    value for reachables.

    If multiple cards were selected or just one card of type actionCard, PathFinder checks whether one of the neighbours
    is of color “rubble” and only allows the move if a rubble with strenght less than the number of cards selected is in the set of neighbours.

    When the PathFinder finished calculating the reachables, he sets the memento by setting the mementos cards and the
    references to all reachables. In the next call of the PathFinder, he will loop over all the HexSpace references saved
    inside the Memento and resets their states like previous and minialCost, minimalDepth so that he will not have false
    leftover information from the previous call.

    Before the PathFinder returns the reachables, all HexSpaces of type Blockade are filtered out of it so that the user
    can not move on Hexspace. Note that the Baricades to not disapear from the previous history of the individual reachable
    HexSpaces.
    */
    public static List<HexSpace> getWay(List<Card> cards, PlayingPiece playingPiece){
        return null;
    }
}
