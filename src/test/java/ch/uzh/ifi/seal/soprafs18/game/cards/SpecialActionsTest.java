package ch.uzh.ifi.seal.soprafs18.game.cards;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpecialActionsTest {

    SpecialActions testActions = new SpecialActions(1,2,3);

    @Test
    public void reduceDraw() {
        testActions.reduceDraw();
        assertEquals(0, testActions.getDraw());
    }

    @Test
    public void reduceRemove() {
        testActions.reduceRemove();
        assertEquals(1, testActions.getRemove());
    }

    @Test
    public void reduceSteal() {
        testActions.reduceSteal();
        assertEquals(2, testActions.getSteal());
    }

    @Test
    public void getDraw() {
        assertEquals(1, testActions.getDraw());
    }

    @Test
    public void getRemove() {
        assertEquals(2, testActions.getRemove());
    }

    @Test
    public void getSteal() {
        assertEquals(3, testActions.getSteal());
    }
}