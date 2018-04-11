package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class RemoveActionCard extends ActionCard {
    /*
   Calls the parents performAction to use Player.remove(Card) instead of Player.discard(Card).
     */

    public RemoveActionCard(String name, int coinValue, int coinCost){
        super(name, coinValue, coinCost);
    }

    public RemoveActionCard(){
        super();
    }

    @Override
    public SpecialActions performAction(Player player){
        player.remove(this);
        return actions;
    }
}
