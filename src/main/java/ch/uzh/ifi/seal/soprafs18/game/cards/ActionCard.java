package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

public class ActionCard extends Card {
    /*
    The Budget that is granted to the user when the ActionCards action is performed. Bugeds stores how many cards the
    user can draw from the draw pile, how many card she/he can remove and how many cards she/he can steal from the
     market according to the cards type.
     */
    protected SpecialActions actions;

    /*
    The performAction returns a Budget of how many cards the Player can draw/remove/steal for free.
     */
    public SpecialActions performAction(){
        return null;
    }

    /*
    Calls Player.discard(this) in the standard case. If the to-HexSpaceEntity happens to be of color BaseCamp,
    then call Player.remove(this) instead.
     */
    public void moveAction(Player player, HexSpace moveTo){

    }
}
