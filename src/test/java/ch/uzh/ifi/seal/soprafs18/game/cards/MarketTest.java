package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarketTest {


    Card card1 = new MovingCard("test1", 3,4,2,2, COLOR.JUNGLE);
    Card card2 = new MovingCard("test2", 3,4,2,2, COLOR.JUNGLE);
    Card card3 = new MovingCard("test3", 3,4,2,2, COLOR.JUNGLE);

    Slot activeSlot1 = new Slot(1, card1, card2, card3);
    Slot activeSlot2 = new Slot(1, card1, card2, card3);
    Slot activeSlot3 = new Slot(1, card1, card2, card3);
    Slot activeSlot4 = new Slot(1, card1, card2, card3);
    Slot activeSlot5 = new Slot(1, card1, card2, card3);
    Slot activeSlot6 = new Slot(1, card1, card2, card3);

    Slot passiveSlot1 = new Slot(1, card1, card2, card3);
    Slot passiveSlot2 = new Slot(1, card1, card2, card3);
    Slot passiveSlot3 = new Slot(1, card1, card2, card3);
    Slot passiveSlot4 = new Slot(1, card1, card2, card3);
    Slot passiveSlot5 = new Slot(1, card1, card2, card3);

    ArrayList<Slot> active1 = new ArrayList<Slot>();
    ArrayList<Slot> passive1 = new ArrayList<Slot>();

   /* active1.add(activeSlot1);


    Market testMarket = new Market(active, passive);*/


    @Test
    public void buy() {
    }

    @Test
    public void getPurchasable() {
    }

    @Test
    public void getActive() {
    }

    @Test
    public void getPassive() {
    }

    @Test
    public void stealCard() {
    }
}