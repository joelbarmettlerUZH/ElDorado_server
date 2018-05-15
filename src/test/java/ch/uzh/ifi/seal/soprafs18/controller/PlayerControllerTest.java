package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.*;
import ch.uzh.ifi.seal.soprafs18.game.cards.*;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import ch.uzh.ifi.seal.soprafs18.repository.*;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import ch.uzh.ifi.seal.soprafs18.service.RoomService;
import ch.uzh.ifi.seal.soprafs18.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.data.repository.init.ResourceReader.Type.JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PlayerController.class)
@DirtiesContext

public class PlayerControllerTest {

    private final String context = CONSTANTS.APICONTEXT + "/Player";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlayerService playerService;

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
    public void sellCard() throws Exception {

        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(12, "testplayer", game, "TESTTOKEN");
        playerRepository.save(player);
        System.out.println(player.getPlayerId());
        MovingCard card = (MovingCard) player.getHandPile().get(0);
        Card card2 = (Card) card;
        System.out.println((Card) card2);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(player.getCoins());
        game.setCurrent(player);
        playerService.sellCard(12, card, "TESTTOKEN");
        System.out.println(player.getCoins());
        //String cardString = mapper.writeValueAsBytes(card);
        String jsonString = "{\"type\":\"RemoveMoveCard\",\"id\":152,\"name\":\"Schatztruhe\",\"coinValue\":4,\"coinCost\":3,\"strength\":4,\"depth\":99,\"colors\":[\"JUNGLE\",\"ENDFIELDJUNGLE\"]}\n";

        mvc.perform(put(context+"/12/Sell")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void buyCard() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(13, "testplayer", game, "TESTTOKEN");
        player.setPlayerId(12);
        player.setCoins(10f);
        playerRepository.save(player);
        game.setCurrent(player);
        Market market = game.getMarketPlace();
        Slot slot = market.getActive().get(0);
        //given(playerService.buyCard(12,slot,"TESTTOKEN")).willReturn(player);
        String jsonString = "{\"slotId\":159,\"pile\":[{\"type\":\"RemoveActionCard\",\"id\":160,\"name\":\"Fernsprechgerät\",\"coinValue\":0.5,\"coinCost\":4,\"actions\":{\"draw\":0,\"remove\":0,\"steal\":1}},{\"type\":\"RemoveActionCard\",\"id\":161,\"name\":\"Fernsprechgerät\",\"coinValue\":0.5,\"coinCost\":4,\"actions\":{\"draw\":0,\"remove\":0,\"steal\":1}},{\"type\":\"RemoveActionCard\",\"id\":162,\"name\":\"Fernsprechgerät\",\"coinValue\":0.5,\"coinCost\":4,\"actions\":{\"draw\":0,\"remove\":0,\"steal\":1}}],\"empty\":false}\n";

        mvc.perform(put(context+"/13/Buy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void removeCard() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(12, "testplayer", game, "TESTTOKEN");
        playerRepository.save(player);
        System.out.println(player.getPlayerId());
        MovingCard card = (MovingCard) player.getHandPile().get(0);
        Card card2 = (Card) card;
        System.out.println((Card) card2);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(player.getCoins());
        game.setCurrent(player);
        playerService.sellCard(12, card, "TESTTOKEN");
        System.out.println(player.getCoins());
        //String cardString = mapper.writeValueAsBytes(card);
        String jsonString = "{\"type\":\"RemoveMoveCard\",\"id\":152,\"name\":\"Schatztruhe\",\"coinValue\":4,\"coinCost\":3,\"strength\":4,\"depth\":99,\"colors\":[\"JUNGLE\",\"ENDFIELDJUNGLE\"]}\n";

        mvc.perform(put(context+"/12/Remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void discardCard() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(12, "testplayer", game, "TESTTOKEN");
        playerRepository.save(player);
        System.out.println(player.getPlayerId());
        MovingCard card = (MovingCard) player.getHandPile().get(0);
        Card card2 = (Card) card;
        System.out.println((Card) card2);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(player.getCoins());
        game.setCurrent(player);
        playerService.sellCard(12, card, "TESTTOKEN");
        System.out.println(player.getCoins());
        //String cardString = mapper.writeValueAsBytes(card);
        String jsonString = "{\"type\":\"RemoveMoveCard\",\"id\":152,\"name\":\"Schatztruhe\",\"coinValue\":4,\"coinCost\":3,\"strength\":4,\"depth\":99,\"colors\":[\"JUNGLE\",\"ENDFIELDJUNGLE\"]}\n";

        mvc.perform(put(context+"/12/Discard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void stealCard() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(13, "testplayer", game, "TESTTOKEN");
        player.setPlayerId(12);
        player.setCoins(10f);
        playerRepository.save(player);
        game.setCurrent(player);
        Market market = game.getMarketPlace();
        Slot slot = market.getActive().get(0);
        //given(playerService.buyCard(12,slot,"TESTTOKEN")).willReturn(player);
        String jsonString = "{\"slotId\":159,\"pile\":[{\"type\":\"RemoveActionCard\",\"id\":160,\"name\":\"Fernsprechgerät\",\"coinValue\":0.5,\"coinCost\":4,\"actions\":{\"draw\":0,\"remove\":0,\"steal\":1}},{\"type\":\"RemoveActionCard\",\"id\":161,\"name\":\"Fernsprechgerät\",\"coinValue\":0.5,\"coinCost\":4,\"actions\":{\"draw\":0,\"remove\":0,\"steal\":1}},{\"type\":\"RemoveActionCard\",\"id\":162,\"name\":\"Fernsprechgerät\",\"coinValue\":0.5,\"coinCost\":4,\"actions\":{\"draw\":0,\"remove\":0,\"steal\":1}}],\"empty\":false}\n";

        mvc.perform(put(context+"/13/Steal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void movePlayer() throws Exception {

        /*ObjectMapper mapper = new ObjectMapper();

        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(13, "testplayer", game, "TESTTOKEN");
        player.setPlayerId(12);
        player.setCoins(10f);
        HexSpace testJungle = new HexSpace(COLOR.JUNGLE, 1, 1, 1, new Point(-1, -2));
        PlayingPiece piece = new PlayingPiece(testJungle, 0);
        List<PlayingPiece> pieces = new ArrayList<>();
        pieces.add(piece);
        player.setPlayingPieces(pieces);
        playerRepository.save(player);
        game.setCurrent(player);
        Market market = game.getMarketPlace();
        Slot slot = market.getActive().get(0);
        //given(playerService.buyCard(12,slot,"TESTTOKEN")).willReturn(player);
        String jsonString = "{\"cards\":[{\"type\":\"MovingCard\",\"name\":\"Forscher\",\"coinValue\":0.5,\"coinCost\":0,\"strength\":1,\"depth\":99,\"colors\":[\"JUNGLE\",\"ENDFIELDJUNGLE\"],\"id\":237}],\"hexSpace\":{\"hexSpaceId\":878,\"strength\":1,\"minimalCost\":1000,\"minimalDepth\":0,\"color\":\"JUNGLE\",\"point\":{\"x\":3,\"y\":5}}}\n";

        mvc.perform(put(context+"/13/Move/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());*/
    }

    @Test
    public void removeBlockades() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(13, "testplayer", game, "TESTTOKEN");
        player.setPlayerId(12);
        player.setCoins(10f);
        playerRepository.save(player);
        game.setCurrent(player);
        Market market = game.getMarketPlace();
        Slot slot = market.getActive().get(0);
        //given(playerService.buyCard(12,slot,"TESTTOKEN")).willReturn(player);
        String jsonString = "{\"hibernateBlockadeId\":5,\"blockadeId\":4,\"spaces\":[{\"hexSpaceId\":780,\"strength\":1,\"minimalCost\":1000,\"minimalDepth\":0,\"color\":\"SAND\",\"point\":{\"x\":4,\"y\":8},\"parentBlockade\":4,\"orientation\":1},{\"hexSpaceId\":781,\"strength\":1,\"minimalCost\":1000,\"minimalDepth\":0,\"color\":\"SAND\",\"point\":{\"x\":5,\"y\":7},\"parentBlockade\":4,\"orientation\":1},{\"hexSpaceId\":782,\"strength\":1,\"minimalCost\":1000,\"minimalDepth\":0,\"color\":\"SAND\",\"point\":{\"x\":6,\"y\":7},\"parentBlockade\":4,\"orientation\":1},{\"hexSpaceId\":783,\"strength\":1,\"minimalCost\":1000,\"minimalDepth\":0,\"color\":\"SAND\",\"point\":{\"x\":7,\"y\":6},\"parentBlockade\":4,\"orientation\":1},{\"hexSpaceId\":784,\"strength\":1,\"minimalCost\":1000,\"minimalDepth\":0,\"color\":\"SAND\",\"point\":{\"x\":8,\"y\":6},\"parentBlockade\":4,\"orientation\":1}],\"cost\":1}\n";

        mvc.perform(put(context+"/13/Blockade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void actionPlayer() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(12, "testplayer", game, "TESTTOKEN");
        playerRepository.save(player);
        System.out.println(player.getPlayerId());
        MovingCard card = (MovingCard) player.getHandPile().get(0);
        Card card2 = (Card) card;
        System.out.println((Card) card2);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(player.getCoins());
        game.setCurrent(player);
        playerService.sellCard(12, card, "TESTTOKEN");
        System.out.println(player.getCoins());
        //String cardString = mapper.writeValueAsBytes(card);
        String jsonString = "{\"type\":\"RemoveMoveCard\",\"id\":152,\"name\":\"Schatztruhe\",\"coinValue\":4,\"coinCost\":3,\"strength\":4,\"depth\":99,\"colors\":[\"JUNGLE\",\"ENDFIELDJUNGLE\"]}\n";

        mvc.perform(put(context+"/12/Action")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void endRound() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(12, "testplayer", game, "TESTTOKEN");
        playerRepository.save(player);

        game.setCurrent(player);
        //String cardString = mapper.writeValueAsBytes(card);

        mvc.perform(put(context+"/12/End")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void findPath() throws Exception {
    }

    @Test
    public void draw() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(12, "testplayer", game, "TESTTOKEN");
        playerRepository.save(player);

        game.setCurrent(player);
        //String cardString = mapper.writeValueAsBytes(card);

        mvc.perform(put(context+"/12/Draw")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void resetSpacialActions() throws Exception {
        RoomEntity testRoom = new RoomEntity("testRoom");
        UserEntity testUser2 = new UserEntity("testUser2", 2, testRoom);
        Game game = new Game(0,100,"testgame");
        gameRepository.save(game);
        Player player = new Player(12, "testplayer", game, "TESTTOKEN");
        playerRepository.save(player);

        game.setCurrent(player);
        //String cardString = mapper.writeValueAsBytes(card);

        mvc.perform(put(context+"/12/EndAction")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token","TESTTOKEN"))
                .andExpect(status().isOk());
    }
}