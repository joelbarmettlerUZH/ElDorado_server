package ch.uzh.ifi.seal.soprafs18.game.player;

import java.util.ArrayList;
import java.util.List;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;

public class CardAction {

    public CardAction(Card card, String actionName){
        cards = new ArrayList<Card>();
        addCard(card);
        this.actionName = actionName;
    }

    public CardAction(String actionName){
        this.actionName = actionName;
    }

    /*
    List of Cards that were used to perform a certain action.
     */
    private ArrayList<Card> cards;

    public void addCard(Card card){
        cards.add(card);
    }

    /*
    Name of the corresponding Action that is then displayed in the FrontEnd
     */
    private String actionName;
}
