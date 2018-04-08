package ch.uzh.ifi.seal.soprafs18.game.player;

import java.io.Serializable;
import java.util.List;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
@Data
public class CardAction  implements Serializable {

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


    public CardAction(Card card, String actionName){
        addCard(card);
        this.actionName = actionName;
    }

    public CardAction(String actionName){
        this.actionName = actionName;
    }

    public CardAction(){

    }

    public void addCard(Card card){
        cards.add(card);
    }

}