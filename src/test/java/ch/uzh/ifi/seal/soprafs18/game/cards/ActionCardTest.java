package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
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

    PlayingPiece testPiece = new PlayingPiece();

    Card card1 = new MovingCard("test1", 3,4,2,2, COLOR.JUNGLE);
    Card card2 = new MovingCard("test2", 3,4,2,2, COLOR.JUNGLE);
    Card card3 = new MovingCard("test3", 3,4,2,2, COLOR.JUNGLE);

    ArrayList drawPile = new ArrayList();

    ActionCard testCard = new ActionCard("test", 4, 3, testActions);

    @Test
    public void performAction() {

        drawPile.add(card1);
        drawPile.add(card2);
        drawPile.add(card3);

        Player testPlayer = new Player("testPlayer", testGame, 1, new SpecialActions(), drawPile,"testToken");

        assertEquals(testActions, testCard.performAction(testPlayer));
    }

    @Test
    public void moveAction() {

        drawPile.add(card1);
        drawPile.add(card2);
        drawPile.add(card3);

        Player testPlayer = new Player("testPlayer", testGame, 1, new SpecialActions(), drawPile,"testToken");

        //assertEquals(testPlayer. , testCard.performAction(testPlayer));

       // testPlayer.move(testPiece, testPlayer. , testBaseCamp);


        /* have no idea how to test this /////////////////////
        testCard.moveAction(testPlayer, testBaseCamp);

        testPlayer.
        assertEquals(null, testPlayer.);
        *////////////////////
    }
}