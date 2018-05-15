package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.MovingCard;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@WebAppConfiguration
@DirtiesContext
public class PathfinderTest {

    private Game game;

    @Before
    public void setUp() {
        System.out.println("Testing Hexspace Setup");
        this.game = new Game(5,0,"testgame");
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Testplayer1", game, "TESTTOKEN"));
        //players.add(new Player(2, "Testplayer2", game, "TESTTOKEN"));
        game.setPlayers(players);
        game.assemble();
    }

    @Test
    public void getWay() {
        Player player = game.getCurrent();
        player.getPlayingPieces().get(0).setStandsOn(game.getPathMatrix().get(4,4));
        game.getPathMatrix().printMatrix(0,0,game.getPathMatrix().getXDim(),game.getPathMatrix().getYDim());
        System.out.println("3,3 = "+game.getPathMatrix().get(3,3));
        System.out.println(game.getPlayers().get(0).getPlayingPieces().get(0).getStandsOn().getPoint().toString());
        List<Card> cards =  new ArrayList<>();
        cards.add(new MovingCard("testJungle",1,0,1,20, new COLOR[]{COLOR.JUNGLE}));
        List<Point> reachables = new ArrayList<>();
        reachables.add(new Point(5,4));
        reachables.add(new Point(4,4));
        reachables.add(new Point(3,3));
        reachables.add(new Point(5,3));
        for(HexSpace hexspace :Pathfinder.getWay(game, cards, game.getPlayers().get(0).getPlayingPieces().get(0))){
            System.out.println("reachables" + hexspace.toString());
            assertEquals("hexspace in expected",true, reachables.contains(hexspace.getPoint()));
        }
    }


}