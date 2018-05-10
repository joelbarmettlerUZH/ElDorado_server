package ch.uzh.ifi.seal.soprafs18.game.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Data
public class CardAction  implements Serializable {

    @Id
    @GeneratedValue
    @JsonIgnore
    private int cardActionId;

    public CardAction(Card card, String actionName){
        this.cards = new ArrayList<>();
        List<Card> mycards = new ArrayList<>();
        mycards.add(card);
        this.cards = mycards;
        this.actionName = actionName;
    }

    public CardAction(List<Card> cards, String actionName){
        this.cards = new ArrayList<>(cards);
        this.actionName = actionName;
    }

    public CardAction(){
    }

    /*
    Name of the corresponding Action that is then displayed in the FrontEnd
     */
    private String actionName;

    /*
    List of Cards that were used to perform a certain action.
     */

    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Card> cards;

    //public void addCard(Card card){
    //    cards.add(card);
    //}

}