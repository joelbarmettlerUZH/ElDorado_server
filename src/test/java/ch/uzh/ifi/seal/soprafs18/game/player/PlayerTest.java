package ch.uzh.ifi.seal.soprafs18.game.player;

import ch.uzh.ifi.seal.soprafs18.game.cards.ActionCard;
import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.cards.SpecialActions;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.*;

public class PlayerTest {

    Game testGame = new Game();
    PlayingPiece testPiece = new PlayingPiece();
    HexSpace testJungle = new HexSpace(COLOR.JUNGLE, 1, 1, 1, new Point(-1, -2));
    SpecialActions testActions = new SpecialActions(0,0,0);
    ActionCard testCard = new ActionCard("test", 4, 3, testActions);
    Player testPlayer = new Player(1, "testPlayer", testGame,"testToken");

    @Before
    public void setUp() {
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(testPlayer);
        testGame.setPlayers(testPlayers);
    }

    // TODO: Write move and action test
    @Test
    public void move() {
        /*List<Card> movingCards = new ArrayList<>();
        movingCards.add(testPlayer.getHandPile().get(1));
        testPlayer.move(testPiece, movingCards, testJungle);*/
    }

    @Test
    public void removeBlockade() {
    }

    @Test
    public void myTurn() {
        assertEquals(TRUE, testPlayer.myTurn());
    }

    @Test
    public void drawAction(){
        SpecialActions specAct = new SpecialActions(1,0,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.drawAction();
        assertEquals(5,testPlayer.getHandPile().size());
    }

    @Test
    public void stealAction(){
        Market testMarket = new Market();
        SpecialActions specAct = new SpecialActions(0,0,1);
        testPlayer.setSpecialAction(specAct);
        assertEquals(0,testPlayer.getDiscardPile().size());
        testPlayer.stealAction(testMarket.getActive().get(0));
        assertEquals(1,testPlayer.getDiscardPile().size());
    }

    @Test
    public void removeAction(){
        SpecialActions specAct = new SpecialActions(0,1,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.removeAction(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
    }

    @Test
    public void removeBlockadeId(){

    }

    @Test
    public void action() {
        testPlayer.action(testCard);
        assertEquals(1,testPlayer.getHistory().size());

    }

    @Test
    public void discard() {
        assertEquals(4,testPlayer.getHandPile().size());
        assertEquals(0, testPlayer.getDiscardPile().size());
        testPlayer.discard(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
        assertEquals(1, testPlayer.getDiscardPile().size());
    }

    @Test
    public void sell() {
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.sell(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
    }

    @Test
    public void buy() {
        Market testMarket = new Market();
        testPlayer.addCoins((float) 1);
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        assertEquals(4,testPlayer.getDiscardPile().size());
        testPlayer.buy(testMarket.getActive().get(1));
        assertEquals(5,testPlayer.getDiscardPile().size());
    }

    @Test
    public void draw() {

        SpecialActions specAct = new SpecialActions(1,0,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.draw(1);
        assertEquals(5,testPlayer.getHandPile().size());
    }

    @Test
    public void draw1() {
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        assertEquals(0,testPlayer.getHandPile().size());
        testPlayer.draw();
        assertEquals(4,testPlayer.getHandPile().size());
    }

    @Test
    public void remove() {
        SpecialActions specAct = new SpecialActions(0,1,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.remove(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
    }

    @Test
    public void endRound() {
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        assertEquals(0,testPlayer.getHandPile().size());
        testPlayer.endRound();
        assertEquals(4,testPlayer.getHandPile().size());
    }
}