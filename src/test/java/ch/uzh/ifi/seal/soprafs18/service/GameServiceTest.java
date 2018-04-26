package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.main.Assembler;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.CardRepository;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
import ch.uzh.ifi.seal.soprafs18.repository.SlotRepository;
import org.assertj.core.util.ArrayWrapperList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameServiceTest {

    @TestConfiguration
    static class GameServiceTestContextConfiguration {

        @Bean
        public GameService gameService() { return new GameService(); }
    }

    @Autowired
    private GameService gameService;

    @MockBean
    private GameRepository gameRepository;

    /*
    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private CardRepository cardRepository;

    @MockBean
    private SlotRepository slotRepository;

    @MockBean
    private RoomService roomService;*/


    @Before
    public void setUp() {
        Game testGame = new Game(1, 1);
        List<Game> games = new ArrayList<Game>();
        games.add(testGame);

        Mockito.when(gameRepository.findAll()).thenReturn(games);
        Mockito.when(gameRepository.findByGameId(1)).thenReturn(games);
    }

    @Test
    public void getAll() {
        List<Game> found = gameService.getAll();
        assertEquals(1,found.size());
    }

    @Test
    public void newGame() {

        /*
        RoomEntity room = new RoomEntity();
        UserEntity user1 = new UserEntity("user1", 0, room);
        UserEntity user2 = new UserEntity("user2", 1, room);

        roomService.getRoom(0);


        Assembler assembler = new Assembler();
        assembler.
        room.addUser(user1);
        assertEquals(1,gameService.getAll().size());
        gameService.newGame(room);
        assertEquals(2,gameService.getAll().size()); */
    }

    @Test
    public void getGame() {
        Game found = gameService.getGame(1);
        assertEquals(1,found.getGameId());
    }

    /*
    @Test
    public void getPlayers() {
        List<Player> players = new ArrayList<Player>();
        assertEquals(players, gameService.getPlayers(1));
    }

    @Test
    public void getWinners() {
        assertEquals(null, gameService.getWinner(1));
    }

    @Test
    public void getCurrentPlayer() {
        assertEquals(null, gameService.getCurrentPlayer(1));
    }

    @Test
    public void getBoard() {
        assertEquals(null, gameService.getBoard(1));
    }

    @Test
    public void getBlockades() {
        assertEquals(null, gameService.getBlockades(1));
    }

    @Test
    public void getMarket() {
        assertEquals(null, gameService.getBlockades(1));
    }

    @Test
    public void stop() {
        Game found = gameService.getGame(1);
        assertEquals(true, found.isRunning());
        gameService.stop(1);
        assertEquals(false, found.isRunning());
    }*/
}