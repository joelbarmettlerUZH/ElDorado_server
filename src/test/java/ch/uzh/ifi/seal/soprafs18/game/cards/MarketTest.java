package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class MarketTest {


    private Card card1 = new MovingCard("test1", 3, 4, 2, 2, new COLOR[]{COLOR.JUNGLE});

    private Slot passiveSlot4 = new Slot(card1);
    private Slot passiveSlot5 = new Slot(card1);

    private ArrayList<Slot> active1 = new ArrayList<>();
    private ArrayList<Slot> passive1 = new ArrayList<>();
    private ArrayList<Slot> both = new ArrayList<>();

    private Market testMarket = new Market();


    @Test
    public void buy() {
        Market testMarket = new Market();

        assertEquals(testMarket.getActive().get(0).getCard(), testMarket.buy(testMarket.getActive().get(0)));
    }

    @Test
    public void getPurchasable() {
        Market testMarket = new Market();
        assertEquals(testMarket.getActive(), testMarket.getPurchasable());
    }

    @Test
    public void getActive() {

        assertEquals(6, testMarket.getActive().size());
    }

    @Test
    public void getPassive() {

        assertEquals(12, testMarket.getPassive().size());
    }

    @Test
    public void stealCard() {

        assertEquals(testMarket.getActive().get(0).getCard(), testMarket.stealCard(testMarket.getActive().get(0)));
    }
}