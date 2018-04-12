package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class HexSpaceTest {

    private Game game;

    @Before
    public void setUp() {
        System.out.println("Testing Hexspace Setup");
        this.game = new Game();

    }

    @Test
    public void getNeighbour() {
        System.out.println("Testing Hexspace getNeighbour");
        game.assemble();
        System.out.println("Testing Hexspace 111");
        HexSpace hexSpace = game.getPathMatrix().get(0,0);
        System.out.println("Testing Hexspace 222");
        System.out.println(hexSpace.getPoint().x);
        assertEquals("Placeholder",1, 1);
    }
    /*
    @Test
    public void getNeighbour1() {
    }

    @Test
    public void getStrength() {
    }

    @Test
    public void getMinimalCost() {
    }

    @Test
    public void getMinimalDepth() {
    }

    @Test
    public void getColor() {
    }

    @Test
    public void getPoint() {
    }

    @Test
    public void getPrevious() {
    }

    @Test
    public void getGame() {
    }
    */
}