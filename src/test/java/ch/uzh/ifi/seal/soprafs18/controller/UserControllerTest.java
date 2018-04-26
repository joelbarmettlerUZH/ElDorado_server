package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import ch.uzh.ifi.seal.soprafs18.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        RoomEntity room = new RoomEntity();
        UserEntity user = new UserEntity("Testuser",1,room);
        List<UserEntity> users = new ArrayList<UserEntity>();
        users.add(user);
        Mockito.when(userRepository.findByUserID(user.getUserID())).thenReturn(users);
        Mockito.when(userRepository.findByUserID(0)).thenReturn(users);
    }

    @Test
    public void getUsers() {
        /*
       RoomEntity room = new RoomEntity();
        UserEntity user = new UserEntity("Testuser",1,room);
        Mockito.when(userService.getByID(1)).thenReturn(user);
        UserController cont = new UserController();

        System.out.println("------------------------------------------------------------debug");
        System.out.println(cont.getUser(1));*/
    }

    @Test
    public void createUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void changeUser() {
    }
}