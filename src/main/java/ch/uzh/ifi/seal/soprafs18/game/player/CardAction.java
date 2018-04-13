package ch.uzh.ifi.seal.soprafs18.game.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Embeddable
@Data
public class CardAction  implements Serializable {
    public CardAction(Card card, String actionName){
        this.cards = new ArrayList<>();
        addCard(card);
        this.actionName = actionName;
    }

    public CardAction(String actionName){
        this.actionName = actionName;
    }

    public CardAction(){ }

    /*
    Name of the corresponding Action that is then displayed in the FrontEnd
     */
    private String actionName;

    /*
    List of Cards that were used to perform a certain action.
     */
    @JsonIgnore
    @Transient
    private List<Card> cards;

    public void addCard(Card card){
        cards.add(card);
    }

}