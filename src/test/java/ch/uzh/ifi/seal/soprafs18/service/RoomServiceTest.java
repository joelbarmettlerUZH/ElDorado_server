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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@WebAppConfiguration
public class RoomServiceTest implements Serializable {

    /*@TestConfiguration
    static class RoomServiceTestContextConfiguration {

        @Bean
        public RoomService roomService() { return new RoomService(); }
    }*/

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

    @Before
    public void setUp() {

    }

    @Test
    public void joinUser() {

        RoomEntity roomEntity = new RoomEntity();
        RoomEntity newRoom = roomService.newRoom(roomEntity);
        System.out.println(newRoom);
        UserEntity user1 = new UserEntity("user1", 1, newRoom);
        UserEntity user2 = new UserEntity("user2", 2, newRoom);
        UserEntity user3 = new UserEntity("user3", 3, newRoom);
        UserEntity user4 = new UserEntity("user4", 0, newRoom);
        UserEntity user5 = new UserEntity("user5", 0, newRoom);

        List<String> token_1 = userService.createUser(user1);
        List<String> token_2 = userService.createUser(user2);
        List<String> token_3 = userService.createUser(user3);
        List<String> token_4 = userService.createUser(user4);
        List<String> token_5 = userService.createUser(user5);

        System.out.println(userService.getAll());

        List<UserEntity> users = new ArrayList<>();
        users.add(user1);
        //users.add(user2);
        users.add(user3);
        users.add(user4);

        newRoom.setUsers(users);

        int id = newRoom.getRoomID();

        roomService.joinUser(id,user1, token_1.get(0));
        roomService.joinUser(id,user2, token_2.get(0));
        roomService.joinUser(id,user3, token_3.get(0));
        roomService.joinUser(id,user4, token_4.get(0));

        System.out.println(roomRepository.findByRoomID(id).get(0).getUsers().size());

        users.add(user5);
        roomEntity.setUsers(users);
        roomService.joinUser(id,user5, token_5.get(0));

        assertEquals("user1", roomService.getRoom(id).getUsers().get(0).getName());
    }

    @Test
    public void newRoom() {
        RoomEntity roomEntity = new RoomEntity("TestRoom");
        roomService.newRoom(roomEntity);
        assertEquals("TestRoom", roomService.getRooms().get(0).getName());
    }

    @Test
    public void getRoom() {
        RoomEntity roomEntity = new RoomEntity("TestRoomID");
        RoomEntity roomIdTest = roomService.newRoom(roomEntity);
        assertEquals(roomIdTest,  roomService.getRoom(roomIdTest.getRoomID()));
    }

    @Test
    public void getRooms() {
        RoomEntity roomEntity = new RoomEntity("TestRoom");
        roomService.newRoom(roomEntity);
        assertEquals("TestRoom", roomService.getRooms(0,1).get(0).getName());
    }

    @Test
    public void getRooms2() {
        RoomEntity roomEntity = new RoomEntity("TestRoom");
        roomService.newRoom(roomEntity);
        assertEquals(0, roomService.getRooms(5,0).size());
    }

    @Test(expected = Exception.class)
    public void getRoomsException() {
        RoomEntity roomEntity = new RoomEntity("TestRoom");
        roomService.newRoom(roomEntity);
        roomService.getRooms(-10,-4).get(0).getName();
    }

    @Test(expected = java.lang.Exception.class)
    public void getRoomIdException() {
        RoomEntity roomEntity = new RoomEntity("TestRoomID");
        RoomEntity roomIdTest = roomService.newRoom(roomEntity);
        System.out.println(roomService.getRoom(1111111).getName());
    }

    @Test
    public void startGame() {
        RoomEntity roomEntity = new RoomEntity("TestRoom1");
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
        assertEquals(rooom.getRoomID(), gameService.getGame(rooom.getRoomID()).getGameId());
    }

    @Test
    public void updateRoom() {
    }





/*
    public void startGame(RoomEntity roomEntity) {
        LOGGER.info("Starting a new Game with room " + roomEntity.getRoomID());
        gameService.newGame(roomEntity);
    }

    public void updateRoom(RoomEntity roomEntity) {
        boolean ready = true;
        for (UserEntity user : roomEntity.getUsers()) {
            ready = ready && user.isReady();
        }
        if (ready && (roomEntity.getUsers().size() >= 2)) {
            LOGGER.info("Room " + roomEntity.getRoomID() + " is starting.");
            startGame(roomEntity);
        }
    }
     */
}