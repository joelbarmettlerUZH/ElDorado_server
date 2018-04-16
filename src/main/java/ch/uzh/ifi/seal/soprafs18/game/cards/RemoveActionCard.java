package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class RemoveActionCard extends ActionCard {

    public RemoveActionCard(String name, float value, int cost, SpecialActions actions){
        super(name, value, cost, actions);
    }

    /*
   Calls the parents performAction to use Player.remove(Card) instead of Player.discard(Card).
     */

    public RemoveActionCard(){
        super();
    }

    @Override
    public SpecialActions performAction(Player player) {
        if (player.getHandPile().contains(this)) {
            player.remove(this);
            return actions;
        } else {
            return new SpecialActions();
        }
    }
}
