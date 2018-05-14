package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs18.service.GameService;
import ch.uzh.ifi.seal.soprafs18.service.RoomService;
import ch.uzh.ifi.seal.soprafs18.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@WebAppConfiguration
@DirtiesContext
public class GameTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    //If this is not commented everything fails!
    //@MockBean
    //public RoomRepository roomRepository;
    @Autowired
    public RoomRepository roomRepository;

    //@MockBean
    //public UserRepository userRepository;
    @Autowired
    public UserRepository userRepository;

    @Test
    public void getHexSpace() {
        
    }

    @Test
    public void getHexSpace1() {
    }

    @Test
    public void setPlayers() {
    }

    @Test
    public void assemble() {
    }

    @Test
    public void endRound() {
    }

    @Test
    public void getWinner() {
        RoomEntity roomEntity = new RoomEntity("TestRoomGame");
        roomEntity.setBoardnumber(2);
        RoomEntity rooom = roomService.newRoom(roomEntity);

        UserEntity user1 = new UserEntity("user11", 1, rooom);
        UserEntity user2 = new UserEntity("user21", 2, rooom);

        List<String> token_1 = userService.createUser(user1);
        List<String> token_2 = userService.createUser(user2);

        List<UserEntity> users = new ArrayList<>();
        users.add(user1);
        //users.add(user2);

        rooom.setUsers(users);

        roomService.joinUser(rooom.getRoomID(),user1, token_1.get(0));
        roomService.joinUser(rooom.getRoomID(),user2, token_2.get(0));

        roomService.startGame(rooom);
        gameService.getGame(rooom.getRoomID()).getWinner();

        assertEquals(null, gameService.getGame(rooom.getRoomID()).getWinner());

        List<Player> players = new ArrayList<>();
        players.add(gameService.getGame(rooom.getRoomID()).getPlayers().get(0));
        gameService.getGame(rooom.getRoomID()).setWinners(players);
        assertEquals(players.get(0), gameService.getGame(rooom.getRoomID()).getWinner());

        players.add(gameService.getGame(rooom.getRoomID()).getPlayers().get(1));
        gameService.getGame(rooom.getRoomID()).setWinners(players);
        assertEquals(players.get(0), gameService.getGame(rooom.getRoomID()).getWinner());




        /*
        public Player getWinner() {
        if (winners.size() == 0) {
            return null;
        }
        if (this.winners.size() < 2) {
            return this.winners.get(0);
        }
        for (Player potentialWinner : winners) {
            boolean wins = true;
            for (Player player : winners) {
                wins = wins & potentialWinner.getCollectedBlockades().size() > player.getCollectedBlockades().size();
            }
            if (wins) {
                return potentialWinner;
            }
        }
        for (Player potentialWinner : winners) {
            boolean wins = true;
            for (Player player : winners) {
                int sumPotentialWinner = potentialWinner.getCollectedBlockades().stream().mapToInt(Integer::intValue).sum();
                int sumPlayer = player.getCollectedBlockades().stream().mapToInt(Integer::intValue).sum();
                wins = wins & sumPotentialWinner > sumPlayer;
            }
            if (wins) {
                return potentialWinner;
            }
        }
        return winners.get(0);
    }
         */
    }
}