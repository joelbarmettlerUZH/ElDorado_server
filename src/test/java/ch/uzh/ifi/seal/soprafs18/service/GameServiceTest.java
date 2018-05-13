package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.cards.Market;
import ch.uzh.ifi.seal.soprafs18.game.main.Assembler;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.*;
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
public class GameServiceTest {

    @TestConfiguration
    static class GameServiceTestContextConfiguration {

        @Bean
        public GameService gameService() { return new GameService(); }

        @Bean
        public RoomService roomService() { return new RoomService(); }

        @Bean
        public PlayerService playerService() { return new PlayerService(); }
    }

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlayerService playerService;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;


    @Before
    public void setUp() {
        Game testGame = new Game(1, 1, "testgame");
        List<Game> games = new ArrayList<Game>();
        games.add(testGame);

        Mockito.when(gameRepository.findAll()).thenReturn(games);
        Mockito.when(gameRepository.findByGameId(1)).thenReturn(games);

        RoomEntity room = new RoomEntity("TestRooom");
        room.setRoomID(99);
        UserEntity user1 = new UserEntity("user1", 0);
        UserEntity user2 = new UserEntity("user2", 1);
        user1.setReady(true);
        user2.setReady(true);
        user1.setUserID(0);
        user2.setUserID(1);

        List<UserEntity> users = new ArrayList<UserEntity>();
        users.add(user1);
        users.add(user2);
        room.setUsers(users);
        List<RoomEntity> rooms = new ArrayList<RoomEntity>();
        rooms.add(room);
        Mockito.when(roomRepository.findByRoomID(1000)).thenReturn(rooms);

        Player player1 = new Player(0, "user1", testGame, "TESTTOKEN");
        Player player2 = new Player(1, "user2", testGame, "TESTTOKEN");

        List<Player> players1 = new ArrayList<>();
        players1.add(player1);
        List<Player> players2 = new ArrayList<>();
        players2.add(player2);
        Mockito.when(playerRepository.findByPlayerId(0)).thenReturn(players1);
        Mockito.when(playerRepository.findByPlayerId(1)).thenReturn(players2);

        Game testGame99 = new Game(1, 99, "testgame99");
        List<Game> games99 = new ArrayList<Game>();
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(player1);
        allPlayers.add(player2);
        testGame99.setPlayers(allPlayers);
        games99.add(testGame99);

        Mockito.when(gameRepository.findByGameId(99)).thenReturn(games99);


    }

    @Test
    public void getAll() {
        List<Game> found = gameService.getAll();
        assertEquals(1,found.size());
    }

    @Test
    public void newGame() {

        gameService.newGame(roomService.getRoom(1000));
        Game newGameTest = gameService.getGame(99);
        System.out.println(newGameTest);
        assertEquals("2 players in game",2, newGameTest.getPlayers().size());
        assertEquals("user1 in players",true, newGameTest.getPlayers().get(0).getName()=="user1"
                || newGameTest.getPlayers().get(1).getName()=="user1");

    }

    @Test
    public void getGame() {
        Game found = gameService.getGame(1);
        assertEquals(1,found.getGameId());
    }

    @Test
    public void stop() {
        Game found = gameService.getGame(1);
        assertEquals(true, found.isRunning());
        gameService.stop(1);
        assertEquals(false, found.isRunning());
    }
}