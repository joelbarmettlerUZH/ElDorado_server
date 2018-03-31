package ch.uzh.ifi.seal.soprafs18.game.player;

import java.util.List;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;

public class CardAction {

    /*
    List of Cards that were used to perform a certain action.
     */
    private List<Card> cards;

    /*
    Name of the corresponding Action that is then displayed in the FrontEnd
     */
    private String actionName;
}
