package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.game.cards.*;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.main.Pathfinder;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import ch.uzh.ifi.seal.soprafs18.repository.CardRepository;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
import ch.uzh.ifi.seal.soprafs18.repository.SlotRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@WebAppConfiguration
@DirtiesContext
public class PlayerServiceTest {

    @TestConfiguration
    static class PlayerServiceTestContextConfiguration {

        @Bean
        public PlayerService playerService() {
            return new PlayerService();
        }
    }

    @Autowired
    private PlayerService playerService;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private CardRepository cardRepository;

    @MockBean
    private SlotRepository slotRepository;

    @MockBean
    private GameRepository gameRepository;

    @Before
    public void setUp() {
        Game testGame = new Game(0, 1, "testgame");
        testGame.assemble();
        Market testMarket = testGame.getMarketPlace();

        Player testPlayer = new Player(99, "TestPlayer", testGame, "TESTTOKEN");

        List<Player> playerList = new ArrayList<>();
        playerList.add(testPlayer);
        playerList.add(testPlayer);
        List<Player> playerList2 = new ArrayList<>();
        playerList2.add(testPlayer);
        testGame.setPlayers(playerList);



        Mockito.when(playerRepository.findAll()).thenReturn(playerList);
        Mockito.when(playerRepository.findByPlayerId(99)).thenReturn(playerList);
        Mockito.when(cardRepository.findById(testPlayer.getHandPile().get(0).getId())).thenReturn(testPlayer.getHandPile());
        Mockito.when(slotRepository.findBySlotId(99)).thenReturn(testMarket.getActive());
        Mockito.when(slotRepository.findBySlotId(testMarket.getActive().get(0).getSlotId())).thenReturn(testMarket.getActive());
        Mockito.when(gameRepository.save(testPlayer.getBoard())).thenReturn(testPlayer.getBoard());
    }

    @Test
    public void buyCard() {

        Game testGame = playerService.getGame(99, "TESTTOKEN");
        Player found = playerService.buyCard(99, testGame.getMarketPlace().getActive().get(0), "TESTTOKEN");
        assertEquals(0, found.getDiscardPile().size());
    }

    @Test
    public void discardCard() {
        Player testPlayer = playerService.getPlayer(99);
        Player found = playerService.discardCard(99, testPlayer.getHandPile().get(0), "TESTTOKEN");
        Player found2 = playerService.discardCard(99, testPlayer.getHandPile().get(0), "WRONGTOKEN");
        assertEquals(3, found.getHandPile().size());
    }

    @Test
    public void removeCard() {
        Player testPlayer = playerService.getPlayer(99);
        Player found = playerService.removeCard(99, testPlayer.getHandPile().get(0), "TESTTOKEN");
        Player found2 = playerService.removeCard(99, testPlayer.getHandPile().get(0), "WRONGTOKEN");
        assertEquals(4, found.getHandPile().size());

    }

    @Test
    public void drawCard() {
        Player testPlayer = playerService.getPlayer(99);
        Player found = playerService.removeCard(99, testPlayer.getHandPile().get(0), "TESTTOKEN");
        playerService.drawCard(99,"WRONGTOKEN");
        playerService.drawCard(99, "TESTTOKEN");
        assertEquals(4, found.getHandPile().size());
    }

    @Test
    public void sellCard() {
        Player testPlayer = playerService.getPlayer(99);
        Player found = playerService.sellCard(99, testPlayer.getHandPile().get(0), "TESTTOKEN");
        Player found2 = playerService.sellCard(99, testPlayer.getHandPile().get(0), "WRONGTOKEN");
        assertEquals(3, found.getHandPile().size());
    }

    @Test
    public void stealCard() {
        Game testGame = playerService.getGame(99, "TESTTOKEN");
        Player found = playerService.stealCard(99, testGame.getMarketPlace().getActive().get(0), "TESTTOKEN");
        assertEquals(0, found.getDiscardPile().size());




    }

    @Test
    public void movePlayer() {
        Game testGame = playerService.getGame(99, "TESTTOKEN");
        Game testGame2 = playerService.getGame(99, "WRONGTOKEN");
        Player found = playerService.getPlayer(99);
        testGame.setCurrent(found);
        System.out.println(found.getPlayingPieces());
        List<Card> oldcards = found.getHandPile();
        List<Card> handCards = new ArrayList<>();
        handCards.add(cardRepository.findById(0).get(0));
        found.setHandPile(handCards);
        List<Card> movingCards = new ArrayList<>();
        movingCards.add(found.getHandPile().get(0));
        HexSpace testHex = new HexSpace(COLOR.JUNGLE, 10, 100, 1000, new Point(4, 4));
        PlayingPiece testPiece = new PlayingPiece(testHex, 0);
        testPiece.setStandsOn(testGame.getHexSpace(new Point(4,4)));
        List<PlayingPiece> pp = new ArrayList<>();
        pp.add(testPiece);
        found.setPlayingPieces(pp);
        Player found2 = playerService.getPlayer(99);
        playerService.findPath(99, movingCards, 0, "TESTTOKEN");
        playerService.findPath(99, movingCards, 0, "WRONGTOKEN");
        List<HexSpace> reachable = new ArrayList<>();
        reachable.add(testGame.getHexSpace(new Point(3,3)));
        playerService.getGame(99, "TESTTOKEN").getMemento().setReachables(reachable);
//        playerService.getGame(99, "WRONGTOKEN").getMemento().setReachables(reachable);
        playerService.movePlayer(99,playerService.getPlayer(99).getHandPile(),0, playerService.getGame(99, "TESTTOKEN").getHexSpace(new Point(3,3)),"TESTTOKEN");
        playerService.movePlayer(99,playerService.getPlayer(99).getHandPile(),0, playerService.getGame(99, "TESTTOKEN").getHexSpace(new Point(3,3)),"WRONGTOKEN");
        assertEquals("playing piece moved",testGame.getHexSpace(new Point(3,3)), testPiece.getStandsOn());
        found.setHandPile(oldcards);
        found.getPlayingPieces().get(0).setStandsOn(testGame.getHexSpace(new Point(4,4)));
    }

    @Test
    public void performAction() {
        Game testGame = playerService.getGame(99, "TESTTOKEN");
        Player found = playerService.getPlayer(99);
        testGame.setCurrent(found);
        System.out.println(found.getPlayingPieces());
        List<Card> cards = new ArrayList<Card>();
        cards.add(new ActionCard("Wissenschaftlerin", (float) 0.5, 4, new SpecialActions(1, 1, 0)));
        found.setHandPile(cards);
        Mockito.when(cardRepository.findById(cards.get(0).getId())).thenReturn(cards);
        playerService.performAction(99, cards.get(0), "WRONGTOKEN");
        playerService.performAction(99, cards.get(0), "TESTTOKEN");
        int histSize = found.getHistory().size();
        assertEquals(histSize, found.getHistory().size());
    }

    @Test
    public void resetSpecialActions(){
        Game testGame = playerService.getGame(99, "TESTTOKEN");
        Player found = playerService.getPlayer(99);
        testGame.setCurrent(found);
        playerService.resetSpacialActions(99,"WRONGTOKEN");
        playerService.resetSpacialActions(99,"TESTTOKEN");
    }

    @Test
    public void findPath() {

    }

    @Test
    public void getHandPile(){

        List<Card> allCards = new ArrayList<Card>();

        allCards.add(new MovingCard("Matrose", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.RIVER, COLOR.ENDFIELDRIVER}));
        allCards.add(new MovingCard("Matrose", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.RIVER, COLOR.ENDFIELDRIVER}));
        allCards.add(new MovingCard("Forscher", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));

        allCards.add(new ActionCard("Wissenschaftlerin", (float) 0.5, 4, new SpecialActions(1, 1, 0)));
        allCards.add(new MovingCard("Entdecker", (float) 0.5, 3, 3, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));
        allCards.add(new MovingCard("Tausendsassa", 1, 2, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.SAND, COLOR.RIVER, COLOR.ENDFIELDJUNGLE, COLOR.ENDFIELDRIVER}));
        allCards.add(new RemoveActionCard("Fernsprechgerät", (float) 0.5, 4, new SpecialActions(0, 0, 1)));

        allCards.add(new MovingCard("Millionärin", 4, 5, 4, 99, new COLOR[]{COLOR.SAND}));

        Game testGame = playerService.getGame(99, "TESTTOKEN");
        Player found = playerService.getPlayer(99);
        List<Card> cards = playerService.getHandPile(99, "TESTTOKEN");
        List<Card> cards2 = playerService.getHandPile(99, "WRONGTOKEN");

        int counter = 0;

        for (int i = 0; i < 4; i++){
            if (allCards.contains(cards.get(i))) {
                counter++;
            }

        }
        assertEquals(4,counter);
    }

    @Test
    public void endRound() {

        Game found = playerService.endRound(99, "TESTTOKEN");
        Game found2 = playerService.endRound(99, "WRONGTOKEN");
        assertEquals(found.getGameId(), 1);
    }

    @Test
    public void removeBlockade() {
    }
}