package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

public class RemoveActionCard extends ActionCard {
    /*
   Calls the parents performAction to use Player.remove(Card) instead of Player.discard(Card).
     */
    @Override
    public SpecialActions performAction(){return null;}
}
