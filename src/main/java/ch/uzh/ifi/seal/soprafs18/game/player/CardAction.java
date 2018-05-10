package ch.uzh.ifi.seal.soprafs18.game.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CardAction  implements Serializable {

    @Id
    @GeneratedValue
    @JsonIgnore
    private int cardActionId;

    /*public CardAction(Card card, String actionName){
        this();
        addCard(card);
        this.actionName = actionName;
    }*/

    public CardAction(List<Card> cards, String actionName){
        this.cards = cards;
        this.actionName = actionName;
    }

    public CardAction(String actionName){
        this();
        this.actionName = actionName;
    }

    public CardAction(){
        cards = new ArrayList<>();
    }

    /*
    Name of the corresponding Action that is then displayed in the FrontEnd
     */
    private String actionName;

    /*
    List of Cards that were used to perform a certain action.
     */

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Card> cards;

    public void addCard(Card card){
        cards.add(card);
    }

}