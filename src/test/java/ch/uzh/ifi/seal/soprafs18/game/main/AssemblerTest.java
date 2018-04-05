package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.TileEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.TileRepository;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AssemblerTest {

    private Assembler assembler = new Assembler();
    private List<TileEntity> tileEntitylist;
    private List<Integer> posX;
    private List<Integer> posY;
    private List<Integer> Rotation;
    private List<Integer> Rotation2;

    @MockBean
    private TileRepository tileRepository;

    @Before
    public void setUp(){
        List<HexSpaceEntity> HexSpaceList = new ArrayList<>();
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("S1","SAND",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("ST","STARTFIELD",1000));
        HexSpaceList.add(new HexSpaceEntity("ST","STARTFIELD",1000));
        HexSpaceList.add(new HexSpaceEntity("ST","STARTFIELD",1000));
        HexSpaceList.add(new HexSpaceEntity("ST","STARTFIELD",1000));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("W1","RIVER",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("B1","BASECAMP",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));

        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("S1","SAND",1));
        HexSpaceList.add(new HexSpaceEntity("W1","RIVER",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("S1","SAND",1));
        HexSpaceList.add(new HexSpaceEntity("M","MOUNTAIN",1000));
        HexSpaceList.add(new HexSpaceEntity("M","MOUNTAIN",1000));

        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("S1","SAND",1));
        HexSpaceList.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceList.add(new HexSpaceEntity("S1","SAND",1));

        HexSpaceList.add(new HexSpaceEntity("W1","RIVER",1));

        TileEntity tileEntity = new TileEntity('Z',HexSpaceList);

        this.tileEntitylist = new ArrayList<>();
        this.tileEntitylist.add(tileEntity);

        this.posX = new ArrayList<>();
        this.posX.add(4);
        this.posY = new ArrayList<>();
        this.posY.add(4);
        this.Rotation = new ArrayList<>();
        this.Rotation.add(0);
        this.Rotation2 = new ArrayList<>();
        this.Rotation2.add(3);




        /*
          ('A',('J1','S1','J1','J1','J1','J1','ST','ST','ST','ST','J1','J1','J1','J1','W1','J1','B1','J1',
        'J1','J1','S1','W1','J1','J1','J1','J1','J1','S1','M','M',
        'J1','J1','J1','S1','J1','S1',
        'W1')),
         */
    }

    @Test
    public void createEmptyMatrix() {
        HexSpaceEntity[][] emptyMatrix = assembler.createEmptyMatrix();
        assertEquals("Dimension One is 100",100, emptyMatrix.length);
        assertEquals("Dimension Two is 100",100, emptyMatrix[0].length);
    }

    @Test
    public void getTiles() {
    }

    @Test
    public void getTilePositionX() {
    }

    @Test
    public void getTilePositionY() {
    }

    @Test
    public void getTilesRotation() {
    }

    @Test
    public void assembleTiles() {
        HexSpaceEntity[][] boardMatrix = assembler.createEmptyMatrix();
        //List<Integer> TilePositionX = assembler.getTilePositionX();
        //List<Integer> TilePositionY = assembler.getStripPositionY();
        //List<Integer> TileRotation = assembler.getTilesRotation();
        HexSpaceEntity[][] newMatrix = assembler.assembleTiles(boardMatrix, this.tileEntitylist,
                                                                this.posX, this.posY, this.Rotation);
        assertEquals("Dimension One is 100",100, newMatrix.length);
        assertEquals("Dimension Two is 100",100, newMatrix[0].length);
        assertEquals("Centerpiece correct","RIVER", newMatrix[4][4].getColor());
        assertEquals("Dimension Two is 100","JUNGLE", newMatrix[4][5].getColor());

        HexSpaceEntity[][] newMatrix2 = assembler.assembleTiles(boardMatrix, this.tileEntitylist,
                this.posX, this.posY, this.Rotation2);
        assertEquals("Dimension Two is 100","SAND", newMatrix2[4][5].getColor());
    }

    @Test
    public void getStrips() {
    }

    @Test
    public void getStripPositionX() {
    }

    @Test
    public void getStripPositionY() {
    }

    @Test
    public void getStripRotation() {
    }

    @Test
    public void assembleStrips() {
    }

    @Test
    public void blockades() {
    }

    @Test
    public void getBlockadesCount() {
    }

    @Test
    public void getBlockadeSpace() {
    }

    @Test
    public void assembleBlockades() {
    }

    @Test
    public void getEndingSpaces() {
    }

    @Test
    public void getEndingSpacesPositionX() {
    }

    @Test
    public void getEndingSpacesPositionY() {
    }

    @Test
    public void assembleEndingSpaces() {
    }

    @Test
    public void getBlockades() {
    }

    @Test
    public void getStartingFields() {
    }

    @Test
    public void getEndingFields() {
    }

    @Test
    public void getBoard() {
    }
}