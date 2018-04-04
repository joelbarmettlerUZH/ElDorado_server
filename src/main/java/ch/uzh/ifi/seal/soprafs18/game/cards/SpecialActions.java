package ch.uzh.ifi.seal.soprafs18.game.cards;

public class SpecialActions {

    public SpecialActions(){
        draw = 0;
        remove = 0;
        steal = 0;
    }

    public SpecialActions(int draw, int remove, int steal){
        this.draw = draw;
        this.remove = remove;
        this.steal = steal;
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

    public int getRemove() {
        return remove;
    }

    public int getSteal() {
        return steal;
    }
}
