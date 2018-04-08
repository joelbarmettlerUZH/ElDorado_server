package ch.uzh.ifi.seal.soprafs18.game.cards;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Slot implements Serializable{
    /*
    Unique identifier for a slot
     */
    @Id
    @GeneratedValue
    private int SlotID;

    /*
    Each pile consists of 1 to 3 Cards.
     */
    @ElementCollection
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
    public Card getCard(){
        return pile.get(0);
    }

}
