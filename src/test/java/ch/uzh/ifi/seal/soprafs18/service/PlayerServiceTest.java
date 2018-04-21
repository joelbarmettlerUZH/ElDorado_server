package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
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


    @Before
    public void setUp(){
        Player testPlayer = new Player(99,"TestPlayer",null,"TOKENFORTEST");
        Player testPlayer2 = new Player(98,"TestPlayer2",null,"TOKENFORTEST2");
                //    public Player(int PlayerID, String name, Game game, String token) {
        List<Player> playerList = new ArrayList<>();
        playerList.add(testPlayer);
        playerList.add(testPlayer2);
        List<Player> playerList2 = new ArrayList<>();
        playerList2.add(testPlayer);
        Mockito.when(playerRepository.findAll()).thenReturn(playerList);
        Mockito.when(playerRepository.findByPlayerId(testPlayer.getPlayerId())).thenReturn(playerList2);
    }


    @Test
    public void getPlayers() {

        List<Player> found = playerService.getPlayers();

        assertEquals("Playernames found (Player 1)","TestPlayer", found.get(0).getName());
        assertEquals("Playernames found (Player 2)","TestPlayer2", found.get(1).getName());
        assertEquals("PlayerIds found (Player 1)",99, found.get(0).getPlayerId());
        assertEquals("PlayerIds found (Player 2)",98, found.get(1).getPlayerId());
    }

    @Test
    public void getPlayer() {
        Player found = playerService.getPlayer(99);
        assertEquals("Playername found (Player 1)","TestPlayer", found.getName());
        assertEquals("PlayerId found (Player 1)",99, found.getPlayerId());

    }

    @Test
    public void getGame() {
    }

    @Test
    public void getPlayingPieces() {
    }

    @Test
    public void getBlockades() {
    }

    @Test
    public void getHandPile() {
        //TOKEN gets overwritten currently
        List<Card> found = playerService.getHandPile(99, "TESTTOKEN");
        assertEquals("Handpile found (Player 1)",4, found.size());

    }

    @Test
    public void buyCard() {
    }

    @Test
    public void discardCard() {
    }

    @Test
    public void removeCard() {
    }

    @Test
    public void sellCard() {
    }

    @Test
    public void stealCard() {
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
    }

    @Test
    public void removeBlockade() {
    }
}