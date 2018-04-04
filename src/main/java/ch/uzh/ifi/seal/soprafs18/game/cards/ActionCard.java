package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

public class ActionCard extends Card {

    public ActionCard(String name, float value, int cost, SpecialActions actions) {
        super(name, value, cost);
        this.actions = actions;
    }

    /*
    The Budget that is granted to the user when the ActionCards action is performed. Bugeds stores how many
     cards the user can draw from the draw pile, how many card she/he can remove and how many cards she/he  
     can steal from the market according to the cards type.
     */
    protected SpecialActions actions;

    /*
   The performAction returns a Budget of how many cards the Player can draw/remove/steal for free.
     */
    public SpecialActions performAction(Player player){
        return actions;
    }

    /*
    Calls Player.discard(this) in the standard case. If the to-HexSpace happens to be of color BaseCamp,
    then call Player.remove(this) instead.
     */
    @Override
    public void moveAction(Player player, HexSpace moveTo){
        if (moveTo.getColor() == COLOR.BASECAMP) {
            player.remove(this);
        } else {
            player.discard(this);
        }
    }
}
