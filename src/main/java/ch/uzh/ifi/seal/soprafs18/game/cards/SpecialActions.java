package ch.uzh.ifi.seal.soprafs18.game.cards;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class SpecialActions implements Serializable{
    /*
    Amount of cards that can be drawn
     */
    private int draw;

    /*
    Amount of cards that can be removed
     */
    private int remove;

    /*
    Amount of cards that can be stolen from the market
     */
    private int steal;

    public void reduceDraw(){
        draw = draw - 1;
    }

    public void reduceRemove(){
        remove = remove - 1;
    }

    public void reduceSteal(){
        steal = steal - 1;
    }

}
