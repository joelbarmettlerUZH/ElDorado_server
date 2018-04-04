package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR.BASECAMP;
import static ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR.JUNGLE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.*;

public class ActionCardTest {

    Game testGame = new Game();
    HexSpace testBaseCamp = new HexSpace(4,1,3, BASECAMP, new Point(), new ArrayList<>(), testGame);
    HexSpace testJungle= new HexSpace(4,1,3, JUNGLE, new Point(), new ArrayList<>(), testGame);
    SpecialActions testActions = new SpecialActions(3,3,3);
    Player testPlayer = new Player("testPlayer", testGame, 1, new SpecialActions(), "testToken");
    ActionCard testCard = new ActionCard("test", 4, 3, testActions);

    @Test
    public void performAction() {
        assertEquals(testActions, testCard.performAction(testPlayer));
    }

    @Test
    public void moveAction() {

        /* have no idea how to test this /////////////////////
        testCard.moveAction(testPlayer, testBaseCamp);

        testPlayer.
        assertEquals(null, testPlayer.);
        *////////////////////
    }
}