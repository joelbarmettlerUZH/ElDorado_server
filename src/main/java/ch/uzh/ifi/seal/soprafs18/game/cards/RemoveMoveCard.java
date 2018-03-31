package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

public class RemoveMoveCard extends MovingCard {
    /*
    Overwrites the move method to make Player.discard(this) the standard behaviour. Also checks for one special case:
    If the to-HexSpaceEntity is of type Rubble, the card is discarded via Player.discard(this).
     */
    @Override
    public void moveAction(Player player, HexSpace moveTo) {
        super.moveAction(player, moveTo);
    }
}
