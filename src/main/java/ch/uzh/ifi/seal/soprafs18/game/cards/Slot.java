package ch.uzh.ifi.seal.soprafs18.game.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Slot implements Serializable{
    public Slot(){}

    public Slot(Card card){
        this.pile = new ArrayList<>();
        pile.add((Card) card.clone());
        pile.add((Card) card.clone());
        pile.add((Card) card.clone());
    }

    /*
    Unique identifier for a slot
     */
    @Id
    @GeneratedValue
    private int slotId;

    /*
    Each pile consists of 1 to 3 Cards.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> pile;

    /*
    Returns one contained Card instance and removes it from the Pile.
     */
    public Card buy() {
        Card tmp = pile.get(0);
        pile.remove(0);
        return tmp;
    }

    /*
    Returns one of the Card from the pile without removing it. Is used to compare card values before the user buys a card.
     */
    @JsonIgnore
    public Card getCard(){
        return pile.get(0);
    }

}
