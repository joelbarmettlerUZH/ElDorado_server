package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.cards.Slot;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
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
        Game testGame = new Game(1, 1);
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
    public void getPlayers() {

        List<Player> found = playerService.getPlayers();

        assertEquals("Playernames found (Player 1)", "TestPlayer", found.get(0).getName());
        assertEquals("Playernames found (Player 2)", "TestPlayer", found.get(1).getName());
        assertEquals("PlayerIds found (Player 1)", 99, found.get(0).getPlayerId());
        assertEquals("PlayerIds found (Player 2)", 99, found.get(1).getPlayerId());
    }

    @Test
    public void getPlayer() {
        Player found = playerService.getPlayer(99);
        assertEquals("Playername found (Player 1)", "TestPlayer", found.getName());
        assertEquals("PlayerId found (Player 1)", 99, found.getPlayerId());
    }

    @Test
    public void getGame() {
        Game found = playerService.getGame(99, "TESTTOKEN");
        assertEquals(1, found.getGameId());
        ArrayList arl = new ArrayList();
        assertEquals(arl, found.getWinners());
        assertEquals(2, found.getPlayers().size());
    }

    @Test
    public void getPlayingPieces() {
        List<PlayingPiece> found = playerService.getPlayingPieces(99);
        assertEquals(0, found.size());
    }

    @Test
    public void getBlockades() {
        List<Blockade> found = playerService.getBlockades(99);
        assertEquals(null, found);
    }

    @Test
    public void getHandPile() {
        //TOKEN gets overwritten currently
        List<Card> found = playerService.getHandPile(99, "TESTTOKEN");
        assertEquals("Handpile found (Player 1)", 4, found.size());

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
        assertEquals(3, found.getHandPile().size());
    }

    @Test
    public void removeCard() {
        Player testPlayer = playerService.getPlayer(99);
        Player found = playerService.removeCard(99, testPlayer.getHandPile().get(0), "TESTTOKEN");
        assertEquals(3, found.getHandPile().size());

    }

    @Test
    public void sellCard() {
        Player testPlayer = playerService.getPlayer(99);
        Player found = playerService.sellCard(99, testPlayer.getHandPile().get(0), "TESTTOKEN");
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
    }

    @Test
    public void performAction() {
    }

    @Test
    public void findPath() {

    }

    @Test
    public void endRound() {

        Game found = playerService.endRound(99, "TESTTOKEN");
        assertEquals(found.getGameId(), 1);
    }

    @Test
    public void removeBlockade() {
    }
}