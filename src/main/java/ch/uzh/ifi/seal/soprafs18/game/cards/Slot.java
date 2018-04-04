package ch.uzh.ifi.seal.soprafs18.game.cards;

import java.util.ArrayList;
import java.util.List;

public class Slot {

    public Slot(int id, Card card1, Card card2, Card card3){
        pile = new ArrayList<Card>();
        pile.add(card1);
        pile.add(card2);
        pile.add(card3);
        SlotID = id;
    }
    /*
    Unique identifier for a slot
     */
    private int SlotID;

    /*
    Each pile consists of 1 to 3 Cards.
     */
    private ArrayList<Card> pile;

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

    public int getSlotID() {
        return SlotID;
    }

    public List<Card> getPile() {
        return pile;
    }
}
