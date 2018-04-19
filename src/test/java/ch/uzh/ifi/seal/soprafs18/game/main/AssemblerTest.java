package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.Application;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.TileEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.StripEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.HexSpaceRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.StripRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.TileRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.service.BlockadeSpaceService;
import ch.uzh.ifi.seal.soprafs18.game.board.service.BoardService;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AssemblerTest {

    //private Assembler assembler = new Assembler();
    private List<TileEntity> tileEntitylist;
    private List<Integer> posX;
    private List<Integer> posY;
    private List<Integer> Rotation;
    private List<Integer> Rotation2;
    private List<StripEntity> stripEntitylist;
    private List<BlockadeSpaceEntity> BlockadeSpaceList;
    private List<HexSpaceEntity> endingSpaces;


    //@MockBean
    @Autowired
    private TileRepository tileRepository;

    @Autowired
    private StripRepository stripRepository;

    @Autowired
    private HexSpaceRepository hexSpaceRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BlockadeSpaceService blockadeSpaceService;


    @Before
    public void setUp(){

        //Create lists (with one entry) for positions and rotation.
        this.posX = new ArrayList<>();
        this.posX.add(10);
        this.posY = new ArrayList<>();
        this.posY.add(10);
        this.Rotation = new ArrayList<>();
        this.Rotation.add(0);
        this.Rotation2 = new ArrayList<>();
        this.Rotation2.add(3);

        //Create List of HexSpacesEntities representing a Tile
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

        //Create List of HexSpacesEntities representing a Strip
        List<HexSpaceEntity> HexSpaceListStrip = new ArrayList<>();
        HexSpaceListStrip.add(new HexSpaceEntity("J2","JUNGLE",2));
        HexSpaceListStrip.add(new HexSpaceEntity("R1","RUBBLE",1));
        HexSpaceListStrip.add(new HexSpaceEntity("S1","SAND",1));
        HexSpaceListStrip.add(new HexSpaceEntity("S1","SAND",1));
        HexSpaceListStrip.add(new HexSpaceEntity("W1","RIVER",1));
        HexSpaceListStrip.add(new HexSpaceEntity("J3","JUNGLE",3));
        HexSpaceListStrip.add(new HexSpaceEntity("J2","JUNGLE",2));
        HexSpaceListStrip.add(new HexSpaceEntity("W2","RIVER",2));
        HexSpaceListStrip.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceListStrip.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceListStrip.add(new HexSpaceEntity("R3","RUBBLE",3));
        HexSpaceListStrip.add(new HexSpaceEntity("J1","JUNGLE",1));
        HexSpaceListStrip.add(new HexSpaceEntity("R1","RUBBLE",1));
        HexSpaceListStrip.add(new HexSpaceEntity("J2","JUNGLE",2));
        HexSpaceListStrip.add(new HexSpaceEntity("S3","SAND",3));
        HexSpaceListStrip.add(new HexSpaceEntity("W1","RIVER",1));

        StripEntity stripEntity = new StripEntity('Y',HexSpaceListStrip);

        this.stripEntitylist = new ArrayList<>();
        this.stripEntitylist.add(stripEntity);

        this.BlockadeSpaceList = new ArrayList<>();
        this.BlockadeSpaceList.add(new BlockadeSpaceEntity("BJ1","JUNGLE",1,1));
        this.BlockadeSpaceList.add(new BlockadeSpaceEntity("BR1","RUBBLE",1,2));

        this.endingSpaces = new ArrayList<>();
        this.endingSpaces.add(new HexSpaceEntity("EJ","ENDFIELDJUNGLE",1));
        this.endingSpaces.add(new HexSpaceEntity("EJ","ENDFIELDJUNGLE",1));
        this.endingSpaces.add(new HexSpaceEntity("EW","ENDFIELDRIVER",1));

        //  ('BJ1','JUNGLE',1,1),
        //  ('BJ3','JUNGLE',3,2),


    }

    @Test
    public void createEmptyMatrix() {
        Assembler assembler = new Assembler();
        HexSpaceEntity[][] emptyMatrix = assembler.createEmptyMatrix();
        assertEquals("Dimension One is 100",100, emptyMatrix.length);
        assertEquals("Dimension Two is 100",100, emptyMatrix[0].length);
    }


    @Test
    public void assembleTiles() {
        Assembler assembler = new Assembler();
        HexSpaceEntity[][] boardMatrix = assembler.createEmptyMatrix();
        HexSpaceEntity[][] newMatrix = assembler.assembleTiles(boardMatrix, this.tileEntitylist,
                                                                this.posX, this.posY, this.Rotation);
        assertEquals("Dimension One is 100",100, newMatrix.length);
        assertEquals("Dimension Two is 100",100, newMatrix[0].length);
        assertEquals("Centerpiece correct","RIVER", newMatrix[10][10].getColor());
        assertEquals("offcenter piece correct","JUNGLE", newMatrix[10][11].getColor());

        HexSpaceEntity[][] newMatrix2 = assembler.assembleTiles(boardMatrix, this.tileEntitylist,
                this.posX, this.posY, this.Rotation2);
        assertEquals("offcenter piece correct with rotation","JUNGLE", newMatrix2[10][11].getColor());

    }

    @Test
    public void assembleStrips() {
        Assembler assembler = new Assembler();
        HexSpaceEntity[][] boardMatrix = assembler.createEmptyMatrix();
        HexSpaceEntity[][] newMatrix = assembler.assembleStrips(boardMatrix, this.stripEntitylist,
                this.posX, this.posY, this.Rotation);
        assertEquals("Dimension One is 100",100, newMatrix.length);
        assertEquals("Dimension Two is 100",100, newMatrix[0].length);
        assertEquals("Centerpiece Color correct","JUNGLE", newMatrix[10][10].getColor());
        assertEquals("Centerpiece Strength correct",2, newMatrix[10][10].getStrength());
        assertEquals("offcenter piece correct","RUBBLE", newMatrix[10][11].getColor());
        HexSpaceEntity[][] newMatrix2 = assembler.assembleStrips(boardMatrix, this.stripEntitylist,
                this.posX, this.posY, this.Rotation2);
        assertEquals("offcenter piece correct with rotation","JUNGLE", newMatrix[10][11].getColor());
    }


    @Test
    public void getRandomBlockades(){
        Assembler assembler = new Assembler();
        List<Integer>randomList = assembler.getRandomBlockades(5);
        int min = Collections.min(randomList);
        int max = Collections.max(randomList);
        assertEquals("min correct",1, min);
        assertEquals("min correct",5, max);
    }

    @Test
    public void getBlockadesCount() {
    }

    @Test
    public void assembleEndingSpaces() {
        Assembler assembler = new Assembler();
        List<Integer> EndingPosX = new ArrayList<>();
        EndingPosX.add(5);
        EndingPosX.add(5);
        EndingPosX.add(6);
        List<Integer> EndingPosY = new ArrayList<>();
        EndingPosY.add(5);
        EndingPosY.add(6);
        EndingPosY.add(6);
        HexSpaceEntity[][] boardMatrix = assembler.createEmptyMatrix();
        HexSpaceEntity[][] newMatrix = assembler.assembleEndingSpaces(boardMatrix,this.endingSpaces,EndingPosX,EndingPosY);
        assertEquals("correct Ending space 1","EJ", newMatrix[5][5].getId());
        assertEquals("correct Ending space 2","EJ", newMatrix[5][6].getId());
        assertEquals("correct Ending space 3","EW", newMatrix[6][6].getId());
    }

    @Test
    public void getBlockades() {
    }

    @Test
    public void getStartingFields() {
    }

    @Test
    public void getEndingFields() {
        //Assemble Ending Spaces
        Assembler assembler = new Assembler();
        List<HexSpace> endingSpaces= new ArrayList<>();
        endingSpaces.addAll(assembler.getEndingFields(0));
        System.out.println(endingSpaces);
        // expected points
        List<Point> expectedPoints= new ArrayList<>();
        expectedPoints.add(new Point(26,26));
        expectedPoints.add(new Point(27,27));
        expectedPoints.add(new Point(28,28));
        for (int i = 0; i<endingSpaces.size(); i++){
            assertEquals("correct Ending space type 1","ENDFIELDRIVER", endingSpaces.get(i).getColor().toString());
            assertEquals("correct Ending space pos "+i, expectedPoints.get(i), endingSpaces.get(i).getPoint());
        }
    }

    @Test
    public void getBoard() {
    }
}