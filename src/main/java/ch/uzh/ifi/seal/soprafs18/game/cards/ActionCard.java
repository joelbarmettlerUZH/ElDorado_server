package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Data
@Entity
public class ActionCard extends Card {

    @JsonCreator
    public ActionCard(String name, float coinValue, int coinCost, SpecialActions specialActions){
        super(name, coinValue, coinCost);
        this.actions = specialActions;
    }

    @JsonCreator
    public ActionCard(){
        super();
    }
    /*
    The Budget that is granted to the user when the ActionCards action is performed. Bugeds stores how many
     cards the user can draw from the draw pile, how many card she/he can remove and how many cards she/he  
     can stealAction from the market according to the cards type.
     */
    @Embedded
    protected SpecialActions actions;

    /*
   The performAction returns a Budget of how many cards the Player can draw/remove/stealAction for free.
     */
    public SpecialActions performAction(Player player){
        player.discard(this);
        return actions;
    }


    /*
    Calls Player.discard(this) in the standard case. If the to-HexSpace happens to be of color BaseCamp,
    then call Player.remove(this) instead.
     */
    @Override
    public void moveAction(Player player, HexSpace moveTo){
        if (player.getHandPile().contains(this)) {
            if (moveTo.getColor() == COLOR.BASECAMP) {
                player.remove(this);
            } else {
                player.discard(this);
            }
        }
    }
}
