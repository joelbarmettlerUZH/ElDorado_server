package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.*;
import ch.uzh.ifi.seal.soprafs18.repository.*;
import ch.uzh.ifi.seal.soprafs18.service.RoomService;
import ch.uzh.ifi.seal.soprafs18.service.UserService;
import ch.uzh.ifi.seal.soprafs18.utils.SpringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


import org.junit.Test;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

//@WebMvcTest(UserController.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    private final String context = CONSTANTS.APICONTEXT + "/User";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoomService roomService;

    // I don't know ehy the following Beans are needen but without them it won't work
    @MockBean
    private BlockadeSpaceRepository blockadeSpaceRepository;
    @MockBean
    private HexSpaceRepository hexSpaceRepository;
    @MockBean
    private TileRepository tileRepository;
    @MockBean
    private StripRepository stripRepository;
    @MockBean
    private BoardRepository boardRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoomRepository roomRepository;
    @MockBean
    private GameRepository gameRepository;
    @MockBean
    private PlayerRepository playerRepository;
    @MockBean
    private CardRepository cardRepository;
    @MockBean
    private SlotRepository slotRepository;

    @Test
    public void getUsers() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser = new UserEntity("testUser", 1, testRoom);

        List<UserEntity> allUser = Arrays.asList(testUser);

        given(userService.getAll()).willReturn(allUser);

        mvc.perform(get(context)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$",hasSize(1)))
        .andExpect(jsonPath("$[0].name", is(testUser.getName())));
    }

    @Test
    public void createUser() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post(context)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(testUser2)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteUser() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        ObjectMapper mapper = new ObjectMapper();

        testUser2.setToken("TESTTOKEN");
        testUser2.setUserID(99);
        userService.createUser(testUser2);


        mvc.perform(delete(context+"/99")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token","TESTTOKEN"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getUser() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser = new UserEntity("testUser", 1, testRoom);

        given(userService.getByID(99)).willReturn(testUser);

        mvc.perform(get(context+"/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testUser.getName())));
    }

    @Test
    public void changeUser() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        ObjectMapper mapper = new ObjectMapper();

        testUser2.setToken("TESTTOKEN");
        testUser2.setUserID(98);
        userService.createUser(testUser2);

        testUser2.setName("changedName");

        mvc.perform(put(context)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(testUser2))
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }
}