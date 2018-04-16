package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovingCardTest {

    Game testGame = new Game();
    HexSpace testJungle= new HexSpace();
    Player testPlayer = new Player(1, "testPlayer", testGame,"testToken");

    @Test
    public void validateMove() {
    }

    @Test
    public void moveAction() {
        //There is a problem because draw() randomizes the order, test has to be revised.
        testPlayer.draw();
        assertEquals(0, testPlayer.getDiscardPile().size());
        testPlayer.getHandPile().get(0).moveAction(testPlayer, testJungle);
        assertEquals(1, testPlayer.getDiscardPile().size());
    }
}