package ch.uzh.ifi.seal.soprafs18.game.player;

import ch.uzh.ifi.seal.soprafs18.game.cards.*;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.main.Pathfinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
@WebAppConfiguration
@DirtiesContext
public class PlayerTest {

    Game testGame;
    HexSpace testHex = new HexSpace(COLOR.JUNGLE, 10, 100, 1000, new Point(4, 4));
    PlayingPiece testPiece = new PlayingPiece(testHex, 1);
    HexSpace testJungle = new HexSpace(COLOR.JUNGLE, 1, 1, 1, new Point(-1, -2));
    SpecialActions testActions = new SpecialActions(0,0,0);
    ActionCard testCard = new ActionCard("test", 4, 3, testActions);
    Player testPlayer = new Player(1, "testPlayer", testGame,"TESTTOKEN");
    //PlayingPiece playerTestPiece = testPlayer.getPlayingPieces().get(0);
    Game twoPlayerGame;
    Player startingPlayer = new Player(2, "Testplayer2", twoPlayerGame, "TESTTOKEN");
    Player notStartingPlayer = new Player(3, "Testplayer3", twoPlayerGame, "TESTTOKEN");
    PlayingPiece testPiece2 = new PlayingPiece(testHex, 1);




    @Before
    public void setUp() {

        System.out.println("Testing Hexspace Setup");
        this.testGame = new Game();
        System.out.println(testGame.getPlayers());
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Testplayer1", testGame, "TESTTOKEN"));
        //players.add(new Player(2, "Testplayer2", game, "TESTTOKEN"));
        testGame.setPlayers(players);
        testGame.assemble();
        testGame.setCurrent(testPlayer);
        List<PlayingPiece> pp = new ArrayList<>();
        pp.add(testPiece);
        testPlayer.setPlayingPieces(pp);

        this.twoPlayerGame = new Game();
        List<Player> players2 = new ArrayList<>();
        players2.add(startingPlayer);
        players2.add(notStartingPlayer);
        twoPlayerGame.setPlayers(players2);
        twoPlayerGame.assemble();
        twoPlayerGame.setCurrent(startingPlayer);
    }

    @Test
    public void moveNotOnTurn() {
        notStartingPlayer.setBoard(twoPlayerGame);

        // JUNGLE to JUNGLE

        List<Card> handCards = new ArrayList<>();
        handCards.add(new MovingCard("Forscher", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));

        notStartingPlayer.setHandPile(handCards);
        List<Card> movingCards = new ArrayList<>();
        movingCards.add(notStartingPlayer.getHandPile().get(0));

        //Pathfinder.getWay(twoPlayerGame, movingCards, testPiece);
        notStartingPlayer.move(testPiece, movingCards, twoPlayerGame.getHexSpace(new Point(3,3)));
        assertEquals(0,notStartingPlayer.move(testPiece, movingCards, twoPlayerGame.getHexSpace(new Point(3,3))).size());
    }

    @Test
    public void move() {
        testPlayer.setBoard(testGame);

        // JUNGLE to JUNGLE
        testPiece.move(testGame.getHexSpace(new Point(4,4)));
        List<Card> handCards = new ArrayList<>();
        handCards.add(new MovingCard("Forscher", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));

        testPlayer.setHandPile(handCards);
        List<Card> movingCards = new ArrayList<>();
        movingCards.add(testPlayer.getHandPile().get(0));

        Pathfinder.getWay(testGame, movingCards, testPiece);
        testPlayer.move(testPiece, movingCards, testGame.getHexSpace(new Point(3,3)));
        assertEquals("playing piece moved",testGame.getHexSpace(new Point(3,3)).getPoint(), testPiece.getPosition());
    }

    @Test
    public void moveWithWrongCard() {
        testPlayer.setBoard(testGame);

        // JUNGLE to JUNGLE
        testPiece.setStandsOn(testGame.getHexSpace(new Point(4,4)));
        List<Card> handCards = new ArrayList<>();
        handCards.add(new MovingCard("Forscher", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));

        List<Card> wrongCards = new ArrayList<>();
        wrongCards.add(new MovingCard("FakeForscher", (float) 0.5, 0, 2, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));


        testPlayer.setHandPile(handCards);
        List<Card> movingCards = new ArrayList<>();
        movingCards.add(testPlayer.getHandPile().get(0));

        Pathfinder.getWay(testGame, movingCards, testPiece);
        testPlayer.move(testPiece, wrongCards, testGame.getHexSpace(new Point(3,3)));
        assertEquals("playing piece moved",testGame.getHexSpace(new Point(4,4)), testPiece.getStandsOn());
    }


    @Test
    public void moveWithWrongPiece() {
        testPlayer.setBoard(testGame);

        // JUNGLE to JUNGLE
        testPiece.setStandsOn(testGame.getHexSpace(new Point(4,4)));
        testPiece2.setStandsOn(testGame.getHexSpace(new Point(4,4)));

        List<Card> handCards = new ArrayList<>();
        handCards.add(new MovingCard("Forscher", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));

        testPlayer.setHandPile(handCards);
        List<Card> movingCards = new ArrayList<>();
        movingCards.add(testPlayer.getHandPile().get(0));

        Pathfinder.getWay(testGame, movingCards, testPiece);
        testPlayer.move(testPiece2, movingCards, testGame.getHexSpace(new Point(3,3)));
        assertEquals("playing piece moved",testGame.getHexSpace(new Point(4,4)), testPiece.getStandsOn());
    }

    @Test
    public void moveOverBlockade() {
        testPlayer.setBoard(testGame);

        //MOVE OVER BLOCKADE
        HexSpace blockadespace = testGame.getHexSpace(new Point(5,7));
        blockadespace.setColor(COLOR.JUNGLE);
        testPiece.setStandsOn(testGame.getHexSpace(new Point(5,6)));
        List<Card> handCards = new ArrayList<>();

        handCards.add(new MovingCard("Forscher", (float) 0.5, 0, 3, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));

        testPlayer.setHandPile(handCards);
        List<Card> movingCards = new ArrayList<>();
        movingCards.add(testPlayer.getHandPile().get(0));

        Pathfinder.getWay(testGame, movingCards, testPiece);

        testPlayer.move(testPiece, movingCards, testGame.getHexSpace(new Point(5,8)));
        assertEquals("playing piece moved",testGame.getHexSpace(new Point(5,8)), testPiece.getStandsOn());
        assertEquals("blockade collected",1, testPlayer.getCollectedBlockades().size());
    }

    @Test
    public void moveNextToBlockade() {
        testPlayer.setBoard(testGame);

        //MOVE NEXT TO BLOCKADE
        HexSpace blockadespace = testGame.getHexSpace(new Point(5,7));
        blockadespace.setColor(COLOR.JUNGLE);
        testPiece.setStandsOn(testGame.getHexSpace(new Point(5,5)));
        List<Card> handCards = new ArrayList<>();

        handCards.add(new MovingCard("Forscher", (float) 0.5, 0, 3, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));

        testPlayer.setHandPile(handCards);
        List<Card> movingCards = new ArrayList<>();
        movingCards.add(testPlayer.getHandPile().get(0));

        Pathfinder.getWay(testGame, movingCards, testPiece);

        testPlayer.move(testPiece, movingCards, testGame.getHexSpace(new Point(5,6)));
        assertEquals("playing piece moved",testGame.getHexSpace(new Point(5,6)), testPiece.getStandsOn());
        assertEquals("blockade removable",1, testPlayer.getRemovableBlockades().size());
    }

    @Test
    public void moveToEndfield() {
        testPlayer.setBoard(testGame);

        //MOVE TO ENDFIELD
        testPiece.setStandsOn(testGame.getHexSpace(new Point(27,20)));
        List<Card> handCards = new ArrayList<>();

        handCards.add(new MovingCard("test", (float) 0.5, 0, 3, 99, new COLOR[]{COLOR.RIVER, COLOR.ENDFIELDRIVER}));

        testPlayer.setHandPile(handCards);
        List<Card> movingCards = new ArrayList<>();
        movingCards.add(testPlayer.getHandPile().get(0));

        Pathfinder.getWay(testGame, movingCards, testPiece);

        testPlayer.move(testPiece, movingCards, testGame.getHexSpace(new Point(27,21)));
        assertEquals("playing piece moved",COLOR.ELDORADO, testPiece.getStandsOn().getColor());
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
    public void drawActionNotOnTurn(){
        notStartingPlayer.setBoard(twoPlayerGame);
        SpecialActions specAct = new SpecialActions(2,0,0);
        notStartingPlayer.setSpecialAction(specAct);
        assertEquals(4,notStartingPlayer.getHandPile().size());
        notStartingPlayer.drawAction();
        assertEquals(4,notStartingPlayer.getHandPile().size());
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
        SpecialActions specAct = new SpecialActions(0,0,5);
        testPlayer.setSpecialAction(specAct);
        assertEquals(0,testPlayer.getDiscardPile().size());
        testPlayer.stealAction(testMarket.getActive().get(0));
        assertEquals(1,testPlayer.getDiscardPile().size());
    }

    @Test
    public void stealActionNotOnTurn(){
        notStartingPlayer.setBoard(twoPlayerGame);
        Market testMarket = new Market();
        SpecialActions specAct = new SpecialActions(0,0,6);
        notStartingPlayer.setSpecialAction(specAct);
        assertEquals(0,notStartingPlayer.getDiscardPile().size());
        notStartingPlayer.stealAction(testMarket.getActive().get(0));
        assertEquals(0,notStartingPlayer.getDiscardPile().size());
    }

    @Test
    public void removeAction(){
        testPlayer.setBoard(testGame);
        SpecialActions specAct = new SpecialActions(0,2,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.removeAction(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
    }

    @Test
    public void removeActionNotOnTurn(){
        notStartingPlayer.setBoard(twoPlayerGame);
        SpecialActions specAct = new SpecialActions(0,1,0);
        notStartingPlayer.setSpecialAction(specAct);
        assertEquals(4,notStartingPlayer.getHandPile().size());
        notStartingPlayer.removeAction(notStartingPlayer.getHandPile().get(0));
        assertEquals(4,notStartingPlayer.getHandPile().size());
    }


    @Test
    public void removeBlockadeId(){

    }

    /*@Test
    public void action() {
        testPlayer.setBoard(testGame);
        testPlayer.action(testCard);
        assertEquals(1,testPlayer.getHistory().size());

    }*/

    @Test
    public void discard() {
        testPlayer.setBoard(testGame);
        assertEquals(4,testPlayer.getHandPile().size());
        assertEquals(0, testPlayer.getDiscardPile().size());
        testPlayer.discard(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
        testPlayer.endRound();
        assertEquals(1, testPlayer.getDiscardPile().size());
    }

    @Test
    public void discardNotOnTurn() {
        notStartingPlayer.setBoard(twoPlayerGame);
        assertEquals(4,notStartingPlayer.getHandPile().size());
        assertEquals(0,notStartingPlayer.getDiscardPile().size());
        notStartingPlayer.discard(notStartingPlayer.getHandPile().get(0));
        assertEquals(4,notStartingPlayer.getHandPile().size());
    }

    @Test
    public void sell() {
        testPlayer.setBoard(testGame);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.sell(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
    }

    @Test
    public void sellNotOnTurn() {
        notStartingPlayer.setBoard(twoPlayerGame);
        assertEquals(4,notStartingPlayer.getHandPile().size());
        notStartingPlayer.sell(notStartingPlayer.getHandPile().get(0));
        assertEquals(4,notStartingPlayer.getHandPile().size());
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
        //testPlayer.endRound();
        assertEquals(0,testPlayer.getHandPile().size());
        assertEquals(4,testPlayer.getTmpDiscardPile().size());
        testPlayer.endRound();
        assertEquals(0,testPlayer.getTmpDiscardPile().size());
        testPlayer.addCoins((float) 10);
        testPlayer.setBought(FALSE);
        testPlayer.buy(testPlayer.getBoard().getMarketPlace().getActive().get(1));
        assertEquals(1,testPlayer.getTmpDiscardPile().size());


        ///*
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
//*/

    }


    @Test
    public void buyNotOnTurn() {
        notStartingPlayer.setBoard(twoPlayerGame);
        Market testMarket = new Market();
        notStartingPlayer.addCoins((float) 10);
        notStartingPlayer.setBought(FALSE);
        notStartingPlayer.buy(notStartingPlayer.getBoard().getMarketPlace().getActive().get(1));
        assertEquals(0, notStartingPlayer.getTmpDiscardPile().size());
    }

    @Test
    public void draw() {
        testPlayer.setBoard(testGame);
        SpecialActions specAct = new SpecialActions(1,0,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.draw(1);
        assertEquals(5,testPlayer.getHandPile().size());

        System.out.println("---------------------------DEBOGO---------------------------------------");
        System.out.println("SizeHand: " + testPlayer.getHandPile().size());
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.sell(testPlayer.getHandPile().get(0));
        testPlayer.draw();
        testPlayer.endRound();
        System.out.println("SizeHand:" + testPlayer.getHandPile().size());
        System.out.println("SizeDraw:" + testPlayer.getDrawPile().size());

        testPlayer.draw(1);
        System.out.println("SizeHand:" + testPlayer.getHandPile().size());
        System.out.println("SizeDraw:" + testPlayer.getDrawPile().size());

        testPlayer.draw(1);
        System.out.println("SizeHand:" + testPlayer.getHandPile().size());
        System.out.println("SizeDraw:" + testPlayer.getDrawPile().size());
        System.out.println("SizeDiscard: " + testPlayer.getDiscardPile().size());

        testPlayer.draw(1);
        System.out.println("SizeHand:" + testPlayer.getHandPile().size());
        System.out.println("SizeDraw:" + testPlayer.getDrawPile().size());
        System.out.println("SizeDiscard: " + testPlayer.getDiscardPile().size());

        testPlayer.draw(1);
        System.out.println("SizeHand:" + testPlayer.getHandPile().size());
        System.out.println("SizeDraw:" + testPlayer.getDrawPile().size());
        System.out.println("SizeDiscard: " + testPlayer.getDiscardPile().size());

        testPlayer.draw();
        System.out.println("SizeHand:" + testPlayer.getHandPile().size());
        System.out.println("SizeDraw:" + testPlayer.getDrawPile().size());
        System.out.println("SizeDiscard: " + testPlayer.getDiscardPile().size());

    }

    @Test
    public void drawNotOnTurn() {
        notStartingPlayer.setBoard(twoPlayerGame);
        assertEquals(4, notStartingPlayer.getHandPile().size());
        notStartingPlayer.draw();
        assertEquals(4, notStartingPlayer.getHandPile().size());
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
        SpecialActions specAct = new SpecialActions(0,2,0);
        testPlayer.setSpecialAction(specAct);
        assertEquals(4,testPlayer.getHandPile().size());
        testPlayer.remove(testPlayer.getHandPile().get(0));
        assertEquals(3,testPlayer.getHandPile().size());
    }

    @Test
    public void removeNotOnTurn() {
        notStartingPlayer.setBoard(twoPlayerGame);
        SpecialActions specAct = new SpecialActions(0,1,0);
        notStartingPlayer.setSpecialAction(specAct);
        assertEquals(4,notStartingPlayer.getHandPile().size());
        notStartingPlayer.remove(notStartingPlayer.getHandPile().get(0));
        assertEquals(4,notStartingPlayer.getHandPile().size());
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

    @Test
    public void endRoundNotOnTurn() {
        notStartingPlayer.setBoard(twoPlayerGame);
        assertEquals(true,twoPlayerGame.getCurrent()!=notStartingPlayer);
        notStartingPlayer.endRound();
        assertEquals(true,twoPlayerGame.getCurrent()!=notStartingPlayer);
    }
}