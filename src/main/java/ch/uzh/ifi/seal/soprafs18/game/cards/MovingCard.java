package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLORS;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

import java.net.Inet4Address;
import java.util.List;

public class MovingCard extends Card{

    /*
    Defines the cards moving-value: The moving cards strength defines the initial strengthLeft value for
    the path finding algorithm.
     */
    private Integer strength;

    /*
    The depth value defines how far you can walk at most with this card at most.
     */
    private Integer depth;

    /*
    List of all colors on which this card can move on.
    Every movingCard always supports the color rubble and basecamp as well.
     */
    private List<COLORS> colors;

    /*
    Checks whether the HexSpaces color matches one of the cards colors and whether the strengthLeft is as least
    as high as the strength of the HexSpace. Also checks if the depthLeft is high enough to even move, so whether
    depthLeft is smaller than depth. Returns True if both properties are True, false otherwise.
     */
    public Boolean validateMove(HexSpace moveTo, Integer strengthLeft, COLORS color, Integer depthLeft){return null;}

    /*
    Does NOT move the Player directly, that is his job. This method lets the card decide of what action is to be taken
    after a move is fullfilled: Player.discard(this) or Player.remove(this). Implementation here is
    Player.discard(this), all special cards that want to be removed after moving overwrite this method
    with Player.remove(this).
    Before Player.dicard(this) is called, we check for one special case: if the to-HexSpace is of type basecamp,
    the card is removed instead of discarded.
     */
    @Override
    public void moveAction(Player player, HexSpace moveTo){}
}
