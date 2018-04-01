package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.player.Player;

public class RemoveMoveSellCard extends RemoveMoveCard {
    /*
    Overwrites the sellAction method that calls Player.remove(this) instead of Player.discard(this) when a Card is sold.
     */
    @Override
    public void sellAction(Player player){
        super.sellAction(player);
    }


}
