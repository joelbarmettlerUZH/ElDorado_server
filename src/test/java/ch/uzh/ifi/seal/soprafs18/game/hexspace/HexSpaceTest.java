package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        /* test for printing out the matrix
        Matrix matrix = game.getPathMatrix();
        System.out.println(matrix.getxDim());
        System.out.println(matrix.getyDim());
        for (int i = 0; i< matrix.getxDim();i++){
            for (int j=0;j< matrix.getyDim();j++){
                System.out.println(matrix.get(i,j).getColor());
            }
        }
        */
        HexSpace hexSpace = game.getPathMatrix().get(4,4);

        System.out.println(hexSpace.getColor().name());
        System.out.println("Testing Hexspace 222");
        System.out.println(hexSpace.getPoint().x);
        System.out.println(hexSpace.getPoint().y);
        List<HexSpace> neighbours = hexSpace.getNeighbour(game);
        List<COLOR> colors = new ArrayList<>();

        for (HexSpace neighborSpace:neighbours){
            colors.add(neighborSpace.getColor());
            System.out.println(neighborSpace.getColor().name());
        }

        COLOR[] expectedColors = {COLOR.JUNGLE,COLOR.JUNGLE,COLOR.JUNGLE,COLOR.SAND,COLOR.SAND,COLOR.RIVER};
        List<COLOR> expectedColorList = new ArrayList<>();
        for (COLOR col:expectedColors){
            expectedColorList.add(col);
        }

        assertEquals("Placeholder",true, colors.containsAll(expectedColorList));
    }

    @Test
    public void toStringTest(){
        game.assemble();
        HexSpace hexSpace = game.getPathMatrix().get(4,4);
        System.out.println(hexSpace.toString());
        assertEquals("Correct String",
                "JUNGLE-Space at Point (4,4), Strength: 1, minimalCost: 1000, minimalDepth: 0, previoussize: 0",
                hexSpace.toString());
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