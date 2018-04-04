package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

public class RemoveMoveCard extends MovingCard {

    public RemoveMoveCard(String name, float value, int cost, int strenght, int depth, COLOR color){
        super(name,value,cost, strenght, depth, color);
    }
    /*
    Overwrites the move method to make Player.discard(this) the standard behaviour. Also checks for one special case:
    If the to-HexSpaceEntity is of type Rubble, the card is discarded via Player.discard(this).
     */
    @Override
    public void moveAction(Player player, HexSpace moveTo) {

        if (this.colors == COLOR.RUBBLE){
            player.discard(this);
        } else {
            player.remove(this);
        }
    }
}
