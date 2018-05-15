package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SlotTest {

    Card card1 = new MovingCard("test1", 3,4,2,2, new COLOR[]{COLOR.JUNGLE});
    Slot testSlot = new Slot(card1);

    @Test
    public void buy() {
        assertEquals(card1.getName(), testSlot.buy().getName());
    }

    @Test
    public void getCard() {
        assertEquals(card1.getName(), testSlot.getCard().getName());
    }

    @Test
    public void getPile() {
        ArrayList<Card> pile = new ArrayList<Card>();
        pile.add(card1);
    }
}