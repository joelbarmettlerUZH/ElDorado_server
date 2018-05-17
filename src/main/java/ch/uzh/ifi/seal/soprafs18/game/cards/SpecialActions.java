package ch.uzh.ifi.seal.soprafs18.game.cards;



import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SpecialActions implements Serializable{

    public SpecialActions(int draw, int remove, int steal){
        this.draw = draw;
        this.remove = remove;
        this.steal = steal;
    }

    public SpecialActions(){
        this(0, 0, 0);
    }
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

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRemove() {
        return remove;
    }

    public void setRemove(int remove) {
        this.remove = remove;
    }

    public int getSteal() {
        return steal;
    }

    public void setSteal(int steal) {
        this.steal = steal;
    }
}
