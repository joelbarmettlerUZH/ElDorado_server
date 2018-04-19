package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RemoveMoveSellCardTest {

    Game testGame = new Game();
    HexSpace testJungle= new HexSpace();
    Player testPlayer = new Player(1, "testPlayer", testGame,"testToken");

    @Before
    public void setUp() {
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(testPlayer);
        testGame.setPlayers(testPlayers);
    }

    //Test insufficient
    @Test
    public void sellAction() {
        testPlayer.draw();
        assertEquals(0, testPlayer.getDiscardPile().size());
    }
}