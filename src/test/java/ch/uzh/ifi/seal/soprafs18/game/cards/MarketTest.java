package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class MarketTest {

    /*
    private Card card1 = new MovingCard("test1", 3, 4, 2, 2, COLOR.JUNGLE);
    private Card card2 = new MovingCard("test2", 3, 4, 2, 2, COLOR.JUNGLE);
    private Card card3 = new MovingCard("test3", 3, 4, 2, 2, COLOR.JUNGLE);

    private Slot activeSlot1 = new Slot(1, card1, card2, card3);
    private Slot activeSlot2 = new Slot(1, card1, card2, card3);
    private Slot activeSlot3 = new Slot(1, card1, card2, card3);
    private Slot activeSlot4 = new Slot(1, card1, card2, card3);
    private Slot activeSlot5 = new Slot(1, card1, card2, card3);
    private Slot activeSlot6 = new Slot(1, card1, card2, card3);

    private Slot passiveSlot1 = new Slot(1, card1, card2, card3);
    private Slot passiveSlot2 = new Slot(1, card1, card2, card3);
    private Slot passiveSlot3 = new Slot(1, card1, card2, card3);
    private Slot passiveSlot4 = new Slot(1, card1, card2, card3);
    private Slot passiveSlot5 = new Slot(1, card1, card2, card3);

    private ArrayList<Slot> active1 = new ArrayList<>();
    private ArrayList<Slot> passive1 = new ArrayList<>();
    private ArrayList<Slot> both = new ArrayList<>();

    @Test
    public void buy() {
        active1.add(activeSlot1);
        active1.add(activeSlot2);
        active1.add(activeSlot3);
        active1.add(activeSlot4);
        active1.add(activeSlot5);
        active1.add(activeSlot6);

        passive1.add(passiveSlot1);
        passive1.add(passiveSlot2);
        passive1.add(passiveSlot3);
        passive1.add(passiveSlot4);
        passive1.add(passiveSlot5);

        Market testMarket = new Market(active1, passive1);

        assertEquals(card1, testMarket.buy(activeSlot1));
    }

    @Test
    public void getPurchasable() {
        active1.add(activeSlot1);
        active1.add(activeSlot2);
        active1.add(activeSlot3);
        active1.add(activeSlot4);
        active1.add(activeSlot5);
        active1.add(activeSlot6);

        passive1.add(passiveSlot1);
        passive1.add(passiveSlot2);
        passive1.add(passiveSlot3);
        passive1.add(passiveSlot4);
        passive1.add(passiveSlot5);

        Market testMarket = new Market(active1, passive1);

        assertEquals(card1, testMarket.buy(activeSlot1));
        assertEquals(card2, testMarket.buy(activeSlot1));
        assertEquals(card3, testMarket.buy(activeSlot1));

        both.add(activeSlot2);
        both.add(activeSlot3);
        both.add(activeSlot4);
        both.add(activeSlot5);
        both.add(activeSlot6);

        both.addAll(passive1);

        assertEquals(both, testMarket.getPurchasable());
    }

    @Test
    public void getActive() {
        active1.add(activeSlot1);
        Market testMarket = new Market(active1, passive1);

        assertEquals(active1, testMarket.getActive());
    }

    @Test
    public void getPassive() {
        passive1.add(activeSlot1);
        Market testMarket = new Market(active1, passive1);

        assertEquals(passive1, testMarket.getPassive());
    }

    @Test
    public void stealCard() {

        active1.add(activeSlot1);
        passive1.add(passiveSlot1);
        Market testMarket = new Market(active1, passive1);

        assertEquals(card1, testMarket.stealCard(activeSlot1));
    }
    */
}