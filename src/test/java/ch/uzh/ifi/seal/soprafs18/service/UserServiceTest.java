package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() { return new UserService(); }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        RoomEntity room = new RoomEntity();
        UserEntity testUser = new UserEntity("TestUser",1 , room);
        List<UserEntity> users = new ArrayList();
        users.add(testUser);

        //Game testGame = new Game(1, 1);
        //List<Game> games = new ArrayList<Game>();
        //games.add(testGame);

        Mockito.when(userRepository.findByUserID(testUser.getUserID())).thenReturn(users);
        Mockito.when(userRepository.findByUserID(1)).thenReturn(users);
        //Mockito.when(gameRepository.findByGameId(1)).thenReturn(games);
    }

    @Test
    public void valid() {
        UserEntity testUser = new UserEntity();
        userService.valid("TESTTOKEN", testUser);
    }

    @Test
    public void valid1() {
        UserEntity testUser = new UserEntity();
        userService.valid("TESTTOKEN", testUser, this.userRepository);
    }

    @Test
    public void createUser() {
        UserEntity testUser = new UserEntity();
        List<String> users = userService.createUser(testUser);
        assertEquals("TESTTOKEN", users.get(0));
    }

    @Test public void getAll() {
        UserEntity testUser = new UserEntity();
        List<UserEntity> users = new ArrayList<UserEntity>();
        assertEquals(users, userService.getAll());
    }

    @Test
    public void getByID() {
        RoomEntity room = new RoomEntity();
        UserEntity testUser = new UserEntity("TestUser",1 , room);
        List<UserEntity> users = new ArrayList<UserEntity>();
        assertEquals(testUser, userService.getByID(1));
    }

    @Test
    public void updateUser() {
        RoomEntity room = new RoomEntity();
        UserEntity testUser = new UserEntity("TestUser",1 , room);
        room.addUser(testUser);
        room.addUser(testUser);

        List<UserEntity> users = new ArrayList<UserEntity>();
        assertEquals(null, userService.updateUser(testUser, "WrongToken"));
        userService.updateUser(testUser, "TESTTOKEN");
    }

    @Test
    public void deleteUser() {
        UserEntity testUser = new UserEntity();
        List<UserEntity> users = new ArrayList<UserEntity>();
        userService.deleteUser(1, "TESTTOKEN");
    }
}