package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR.BASECAMP;
import static ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR.JUNGLE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.*;

public class ActionCardTest {

    Game testGame = new Game();
    HexSpace testJungle= new HexSpace();
    SpecialActions testActions = new SpecialActions(0,0,0);
    ActionCard testCard = new ActionCard("test", 4, 3, testActions);
    Player testPlayer = new Player(1, "testPlayer", testGame,"testToken");

    @Before
    public void setUp() {
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(testPlayer);
        testGame.setPlayers(testPlayers);
    }

    @Test
    public void performAction() {

        assertEquals(testPlayer.getSpecialAction(), testCard.performAction(testPlayer));
    }

    @Test
    public void moveAction() {
        testPlayer.draw();
        assertEquals(0, testPlayer.getDiscardPile().size());
        testPlayer.getHandPile().get(0).moveAction(testPlayer, testJungle);
        assertEquals(1, testPlayer.getTmpDiscardPile().size());
    }
}