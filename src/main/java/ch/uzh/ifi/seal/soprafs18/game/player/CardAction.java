package ch.uzh.ifi.seal.soprafs18.game.player;

import java.util.List;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;

public class CardAction {

    /*
    List of Cards that were used to perform a certain action.
     */
    private List<Card> cards;

    public void addCard(Card card){
        cards.add(card);
    }

    /*
    Name of the corresponding Action that is then displayed in the FrontEnd
     */
    private String actionName;

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

}
