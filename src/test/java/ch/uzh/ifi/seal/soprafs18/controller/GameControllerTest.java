package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.*;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.repository.*;
import ch.uzh.ifi.seal.soprafs18.service.GameService;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = GameController.class)
@DirtiesContext

public class GameControllerTest {

    private final String context = CONSTANTS.APICONTEXT + "/Game";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService gameService;

    // I don't know why the following Beans are needen but without them it won't work

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
    public void getGame() throws Exception {
        Game game = new Game(0,0,"testgame");

        game.setGameId(0);
        given(gameService.getGame(0)).willReturn(game);


        mvc.perform(get(context+"/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}