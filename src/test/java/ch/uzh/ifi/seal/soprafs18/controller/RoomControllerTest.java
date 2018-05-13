package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.*;
import ch.uzh.ifi.seal.soprafs18.repository.*;
import ch.uzh.ifi.seal.soprafs18.service.RoomService;
import ch.uzh.ifi.seal.soprafs18.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RoomController.class)
public class RoomControllerTest {

    private final String context = CONSTANTS.APICONTEXT + "/Room";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomService roomService;

    @MockBean
    private UserService userService;

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
    public void getRooms() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");

        List<RoomEntity> allRooms = Arrays.asList(testRoom);

        given(roomService.getRooms(0,1)).willReturn(allRooms);

        mvc.perform(get(context)
                .contentType(MediaType.APPLICATION_JSON)
                .param("from","0")
                .param("to","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testRoom.getName())));
    }

    @Test
    public void createRoom() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post(context)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(testRoom)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getRoom() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");

        testRoom.setRoomID(99);

        mvc.perform(get(context+"/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void joinUser() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        ObjectMapper mapper = new ObjectMapper();

        RoomEntity testRoom2 = new RoomEntity("testRoom2");

        testRoom2.setRoomID(99);
        testUser2.setToken("TESTTOKEN");
        testUser2.setUserID(98);
        userService.createUser(testUser2);

        mvc.perform(put(context+"/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(testUser2))
                .param("token","TESTTOKEN"))
                .andExpect(status().isCreated());
    }








}