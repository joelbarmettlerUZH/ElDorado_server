package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class RemoveMoveCard extends MovingCard {

    public RemoveMoveCard(String name, float coinValue, int coinCost, int strength, int depth, COLOR[] colors){
        super(name, coinValue, coinCost, strength, depth, colors);
    }

    public RemoveMoveCard(){
        super();
    }
    /*
    Overwrites the move method to make Player.discard(this) the standard behaviour. Also checks for one special case:
    If the to-HexSpaceEntity is of type Rubble, the card is discarded via Player.discard(this).
     */
    @Override
    public void moveAction(Player player, HexSpace moveTo) {
        if (player.getHandPile().contains(this)) {
            if (this.colors.contains(COLOR.RUBBLE)) {
                player.discard(this);
            } else {
                player.remove(this);
            }
        }
    }
}
