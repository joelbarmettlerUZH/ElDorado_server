package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.repository.*;
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
@Transactional
public class RoomServiceTest {

    @TestConfiguration
    static class RoomServiceTestContextConfiguration {

        @Bean
        public RoomService roomService() { return new RoomService(); }
    }

    @Autowired
    private RoomService roomService;

 //   @MockBean
  //  private RoomRepository roomRepository;

    @Before
    public void setUp() {

        RoomEntity testRoom = new RoomEntity("TestRoom");
        List<RoomEntity> rooms = new ArrayList<RoomEntity>();
        rooms.add(testRoom);

        /*
        Game testGame = new Game(1, 1);
        List<Game> games = new ArrayList<Game>();
        games.add(testGame);

        Mockito.when(gameRepository.findAll()).thenReturn(games);
        Mockito.when(gameRepository.findByGameId(1)).thenReturn(games); */

        //Mockito.when(roomRepository.findByRoomID(1)).thenReturn(rooms);
    }

    @Test
    public void joinUser() {
//        UserEntity user1 = new UserEntity("user1", 1, roomService.getRoom(1));
//        roomService.joinUser(1,user1, "TESTTOKEN");
    }

    @Test
    public void leaveUser() {
    }

    @Test
    public void getUsers() {
    }

    @Test
    public void newRoom() {
    }

    @Test
    public void removeRoom() {
    }

    @Test
    public void getRoom() {
    }

    @Test
    public void getRooms() {
    }

    @Test
    public void getRooms1() {
    }

    @Test
    public void startGame() {
    }

    @Test
    public void updateRoom() {
    }
}