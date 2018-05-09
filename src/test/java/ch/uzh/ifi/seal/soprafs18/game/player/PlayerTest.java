package ch.uzh.ifi.seal.soprafs18.game.player;

import ch.uzh.ifi.seal.soprafs18.game.cards.ActionCard;
import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.cards.SpecialActions;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlayerTest {

    Game testGame;
    HexSpace testHex = new HexSpace(COLOR.JUNGLE, 10, 100, 1000, new Point(4, 4));
    PlayingPiece testPiece = new PlayingPiece(testHex, 1);
    //HexSpace testJungle = new HexSpace(COLOR.JUNGLE, 1, 1, 1, new Point(-1, -2));
    SpecialActions testActions = new SpecialActions(0,0,0);
    ActionCard testCard = new ActionCard("test", 4, 3, testActions);
    Player testPlayer = new Player(1, "testPlayer", testGame,"TESTTOKEN");

    @Before
    public void setUp() {

        System.out.println("Testing Hexspace Setup");
        this.testGame = new Game();
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Testplayer1", testGame, "TESTTOKEN"));
        //players.add(new Player(2, "Testplayer2", game, "TESTTOKEN"));
        testGame.setPlayers(players);
        testGame.assemble();

        //List<Player> testPlayers = new ArrayList<>();
        //testPlayers.add(testPlayer);
        //testGame.setPlayers(testPlayers);
        //testGame.assemble();
    }

    // TODO: Write move and action test
    @Test
    public void move() {

        List<Card> movingCards = new ArrayList<>();
        testPlayer.setBoard(testGame);
        movingCards.add(testPlayer.getHandPile().get(1));
        testPlayer.move(testPiece, movingCards, testHex);

        testPlayer.addPlayingPiece(testPiece);
        testPlayer.move(testPiece, movingCards, testHex);
    }

    @Test
    public void removeBlockade() {
    }

    @Test
    public void myTurn() {
        testPlayer.setBoard(testGame);
        assertEquals(TRUE, testPlayer.myTurn());
    }

    @Test
    public void drawAction(){
        testPlayer.setBoard(testGame);
        SpecialActions specAct = new SpecialActions(1,0,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.drawAction();
        assertEquals(5,testPlayer.getHandPile().size());
    }

    @Test
    public void stealAction(){
        testPlayer.setBoard(testGame);
        Market testMarket = new Market();
        SpecialActions specAct = new SpecialActions(0,0,6);
        testPlayer.setSpecialAction(specAct);
        assertEquals(0,testPlayer.getDiscardPile().size());
        testPlayer.stealAction(testMarket.getActive().get(0));
        assertEquals(1,testPlayer.getDiscardPile().size());

        /*
        testMarket.getPurchasable();
        System.out.println("Market size active: " + testMarket.getActive().size());
        System.out.println(testMarket.getActive().get(1));
        testPlayer.stealAction(testMarket.getActive().get(1));

        testMarket.getPurchasable();
        System.out.println("Market size active: " + testMarket.getActive().size());
        System.out.println(testMarket.getActive().get(1));
        testPlayer.stealAction(testMarket.getActive().get(1));

        testMarket.getPurchasable();
        System.out.println("Market size active: " + testMarket.getActive().size());
        System.out.println(testMarket.getActive().get(1));
        testPlayer.stealAction(testMarket.getActive().get(1));

        testMarket.getPurchasable();
        System.out.println("Market size active: " + testMarket.getActive().size());
        System.out.println(testMarket.getActive().get(1));
        testPlayer.stealAction(testMarket.getActive().get(1));*/
    }

    @Test
    public void removeAction(){
        testPlayer.setBoard(testGame);
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
        testPlayer.setBoard(testGame);
        testPlayer.action(testCard);
        assertEquals(1,testPlayer.getHistory().size());

    }

    @Test
    public void discard() {
        testPlayer.setBoard(testGame);
        assertEquals(4,testPlayer.getHandPile().size());
        assertEquals(0, testPlayer.getDiscardPile().size());
        testPlayer.discard(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
        assertEquals(1, testPlayer.getDiscardPile().size());
    }

    @Test
    public void sell() {
        testPlayer.setBoard(testGame);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.sell(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
    }

    @Test
    public void buy() {
        testPlayer.setBoard(testGame);
        Market testMarket = new Market();
        testPlayer.addCoins((float) 1);
        assertEquals(4, testPlayer.getHandPile().size());
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        assertEquals(0,testPlayer.getHandPile().size());
        assertEquals(4,testPlayer.getDiscardPile().size());
        testPlayer.buy(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        assertEquals(5,testPlayer.getDiscardPile().size());

        System.out.println("---------------------------DEBOGO---------------------------------------");
        testPlayer.addCoins((float) 10);
        testPlayer.setBought(FALSE);

        testPlayer.getBoard().getMarketPlace().getPurchasable();
        System.out.println("Market size active: " + testPlayer.getBoard().getMarketPlace().getActive().size());
        System.out.println(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.buy(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.addCoins((float) 10);
        testPlayer.setBought(FALSE);

        testPlayer.getBoard().getMarketPlace().getPurchasable();
        System.out.println("Market size active: " + testPlayer.getBoard().getMarketPlace().getActive().size());
        System.out.println(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.buy(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.addCoins((float) 10);
        testPlayer.setBought(FALSE);

        testPlayer.getBoard().getMarketPlace().getPurchasable();
        System.out.println("Market size active: " + testPlayer.getBoard().getMarketPlace().getActive().size());
        System.out.println(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.buy(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.addCoins((float) 10);
        testPlayer.setBought(FALSE);

        testPlayer.getBoard().getMarketPlace().getPurchasable();
        System.out.println("Market size active: " + testPlayer.getBoard().getMarketPlace().getActive().size());
        System.out.println(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.buy(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.addCoins((float) 10);
        testPlayer.setBought(FALSE);

        testPlayer.getBoard().getMarketPlace().getPurchasable();
        System.out.println("Market size active: " + testPlayer.getBoard().getMarketPlace().getActive().size());
        System.out.println(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.buy(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.addCoins((float) 10);
        testPlayer.setBought(FALSE);
        System.out.println("Market size active: " + testPlayer.getBoard().getMarketPlace().getActive().size());

        testPlayer.getBoard().getMarketPlace().getPurchasable();
        System.out.println("Market size active: " + testPlayer.getBoard().getMarketPlace().getPassive().size());
        System.out.println(testPlayer.getBoard().getMarketPlace().getPassive().get(1));
        testPlayer.buy(testPlayer.getBoard().getMarketPlace().getPassive().get(1));
        testPlayer.addCoins((float) 10);
        testPlayer.setBought(FALSE);

        testPlayer.getBoard().getMarketPlace().getPurchasable();
        System.out.println("Market size active: " + testPlayer.getBoard().getMarketPlace().getActive().size());
        System.out.println(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.buy(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        testPlayer.addCoins((float) 10);
        testPlayer.setBought(FALSE);



        testPlayer.getBoard().getMarketPlace().getPurchasable();
        System.out.println("Market size active: " + testPlayer.getBoard().getMarketPlace().getActive().size());
        System.out.println(testPlayer.getBoard().getMarketPlace().getActive().get(1));


    }

    @Test
    public void draw() {

        testPlayer.setBoard(testGame);
        SpecialActions specAct = new SpecialActions(1,0,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.draw(1);
        assertEquals(5,testPlayer.getHandPile().size());
    }

    @Test
    public void draw1() {
        testPlayer.setBoard(testGame);
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
        testPlayer.setBoard(testGame);
        SpecialActions specAct = new SpecialActions(0,1,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.remove(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
    }

    @Test
    public void endRound() {
        testPlayer.setBoard(testGame);
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        assertEquals(0,testPlayer.getHandPile().size());
        testPlayer.endRound();
        assertEquals(4,testPlayer.getHandPile().size());
    }
}