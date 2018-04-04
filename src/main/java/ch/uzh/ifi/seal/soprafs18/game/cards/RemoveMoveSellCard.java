package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

public class RemoveMoveSellCard extends RemoveMoveCard {

    public RemoveMoveSellCard(String name, float value, int cost, int strenght, int depth, COLOR color){
        super(name,value,cost,strenght, depth, color);
    }
    /*
    Overwrites the sellAction method that calls Player.remove(this) instead of Player.discard(this) when a Card is sold.
     */
    @Override
    public void sellAction(Player player){
        super.sellAction(player);
        player.remove(this);
    }
}
