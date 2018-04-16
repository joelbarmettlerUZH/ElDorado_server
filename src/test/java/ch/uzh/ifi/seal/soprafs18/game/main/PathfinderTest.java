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
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PathfinderTest {

    private Game game;

    @Before
    public void setUp() {
        System.out.println("Testing Hexspace Setup");
        this.game = new Game();
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Testplayer1", game, "TESTTOKEN"));
        //players.add(new Player(2, "Testplayer2", game, "TESTTOKEN"));
        game.setPlayers(players);
    }

    @Test
    public void getWay() {
        game.assemble();
        Player player = game.getCurrent();
        player.getPlayingPieces().get(0).setStandsOn(game.getPathMatrix().get(3,12));
        game.getPathMatrix().printMatrix(0,0,game.getPathMatrix().getXDim(),game.getPathMatrix().getYDim());
        System.out.println("3,3 = "+game.getPathMatrix().get(3,3));
        System.out.println(game.getPlayers().get(0).getPlayingPieces().get(0).getStandsOn().getPoint().toString());
        List<Card> cards =  new ArrayList<>();
        cards.add(new MovingCard("testJungle",1,0,3,20, new COLOR[]{COLOR.JUNGLE}));
        for(HexSpace hexspace :Pathfinder.getWay(game, cards, game.getPlayers().get(0).getPlayingPieces().get(0))){
            System.out.println(hexspace.toString());
        }
    }
}