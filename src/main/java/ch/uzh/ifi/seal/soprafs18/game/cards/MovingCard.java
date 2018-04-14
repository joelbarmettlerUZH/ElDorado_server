package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.board.repository.TileRepository;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
public class MovingCard extends Card{

    @JsonCreator
    public MovingCard(String name, float coinValue, int coinCost, int strength, int depth, COLOR[] colors){
        super(name, coinValue, coinCost);
        this.strength = strength;
        this.depth = depth;
        this.colors = new ArrayList<>(Arrays.asList(colors));
    }

    @JsonCreator
    public MovingCard(){
        super();
    }

    /*
     Defines the cards moving-value: The moving cards strength defines the initial strengthLeft value for the
     path finding algorithm.
     */
    protected int strength;

    /*
    The depth value defines how far you can walk at most with this card at most.
     */
    protected int depth;

    /*
    List of all colors on which this card can move on. Every movingCard always supports the color rubble and basecamp
    as well.
     */
    @ElementCollection(targetClass = COLOR.class)
    @Enumerated
    protected List<COLOR> colors;

    /*
    Checks whether the HexSpaces color matches one of the cards colors and whether the strengthLeft is as least as high
    as the strength of the HexSpaceEntity. Also checks if the depthLeft is high enough to even move, so whether depthLeft is
    smaller than depth. Returns True if both properties are True, false otherwise.
     */
    public boolean validateMove(HexSpace moveTo, int strengthLeft, COLOR color, int depth){
        return false;
    }

    /*
    Does NOT move the Player directly, that is his job. This method lets the card decide of what action is to be taken
    after a move is fullfilled: Player.discard(this) or Player.remove(this). Implementation here is Player.discard(this),
    all special cards that want to be removed after moving overwrite this method with Player.remove(this).
    Before Player.discard(this) is called, we check for one special case: if the to-HexSpaceEntity is of type basecamp,
    the card is removed instead of discarded.
     */

    @Override
    public void moveAction(Player player, HexSpace moveTo) {

        if (moveTo.getColor() == COLOR.BASECAMP){
            player.remove(this);
        } else {
            player.discard(this);
        }
    }
}
