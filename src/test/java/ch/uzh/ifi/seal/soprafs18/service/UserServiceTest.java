package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
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
        UserEntity testUser = new UserEntity();
        List<UserEntity> users = new ArrayList();
        users.add(testUser);
        //Game testGame = new Game(1, 1);
        //List<Game> games = new ArrayList<Game>();
        //games.add(testGame);

        Mockito.when(userRepository.findByUserID(testUser.getUserID())).thenReturn(users);
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
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getByID() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUser() {
    }
}