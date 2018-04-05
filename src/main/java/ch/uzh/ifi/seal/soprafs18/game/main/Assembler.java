package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.*;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BlockadeSpaceRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BoardRepository;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import jdk.nashorn.internal.ir.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assembler {
    /*
    Instance of Database containing all the GameEntity
     */
    @Autowired
    private BoardEntity board;
    //private StripEntity strip;
    //private TileEntity tile;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BlockadeSpaceRepository blockadeSpaceRepository;

    /*
    compute relative positions for OuterRing
     */
    private int[] outerRingDislocX = {0, 1, 2, 3, 3, 3, 3, 2, 1, 0, -1, -2, -3, -3, -3, -3, -2, -1};
    private int[] outerRingDislocYEven = {3, 3, 2, 2, 1, 0, -1, -2, -2, -3, -2, -2, -1, 0, 1, 2, 2, 3};
    private int[] outerRingDislocYOdd = {3, 2, 2, 1, 0, -1, -2, -2, -3, -3, -3, -2, -2, -1, 0, 1, 2, 2};

    /*
    compute relative positions for MidRing
    */
    private int[] midRingDislocX = {0, 1, 2, 2, 2, 1, 0, -1, -2, -2, -2, -1};
    private int[] midRingDislocYEven = {2, 2, 1, 0, -1, -1, -2, -1, -1, 0, 1, 2};
    private int[] midRingDislocYOdd = {2, 1, 1, 0, -1, -2, -2, -2, -1, 0, 1, 1};
    /*

    /*
    compute relative positions for InnerRing
    */
    private int[] innerRingDislocX = {0, 1, 1, 0, -1, -1};
    private int[] innerRingDislocYEven = {1, 1, 0, -1, 0, 1};
    private int[] innerRingDislocYOdd = {1, 0, -1, -1, -1, 0};


    /*
    Terrain-Strips Dislocation
     */
    private int[] Rot0DislocX = {0, 1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4};
    private int[] Rot0EvenDislocY = {0, 1, 0, 0, -1, -1, -2, -3, -2, -2, -1, -1, 0, -1, -1, -2};
    private int[] Rot0OddDislocY = {0, 0, 0, -1, -1, -2, -3, -3, -3, -2, -2, -1, -1, -1, -2, -2};

    private int[] Rot1DislocX = {0, 1, 1, 1, 1, 1, 0, -1, -1, -1, -1, -1, 0, 0, 0, 0};
    private int[] Rot1EvenDislocY = {0, -1, -2, -3, -4, -5, -5, -5, -4, -3, -2, -1, -1, -2, -3, -4};
    private int[] Rot1OddDislocY = {0, 0, -1, -2, -3, -4, -5, -4, -3, -2, -1, 0, -1, -2, -3, -4};

    private int[] Rot2DislocX = {0, 0, -1, -2, -3, -4, -5, -5, -4, -3, -2, -1, -1, -2, -3, -4};
    private int[] Rot2EvenDislocY = {0, -1, -2, -2, -3, -3, -3, -2, -1, -1, 0, 0, -1, -1, -2, -2};
    private int[] Rot2OddDislocY = {0, -1, -1, -2, -2, -3, -2, -1, -1, 0, 0, 1, 0, -1, -1, -2};

    private int[] Rot3DislocX = {0, -1, -1, -1, -1, -1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0};
    private int[] Rot3EvenDislocY = {0, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4};
    private int[] Rot3OddDislocY = {0, 1, 2, 3, 4, 5, 5, 5, 4, 3, 2, 1, 1, 2, 3, 4};

    private int[] Rot4DislocX = {0, -1, -2, -3, -4, -5, -5, -4, -3, -2, -1, 0, -1, -2, -3, -4};
    private int[] Rot4EvenDislocY = {0, 0, 0, 1, 1, 2, 3, 3, 3, 2, 2, 1, 1, 1, 2, 2};
    private int[] Rot4OddDislocY = {0, -1, 0, 0, 1, 1, 2, 3, 2, 2, 1, 1, 0, 1, 1, 2};

    private int[] Rot5DislocX = {0, 0, 1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 1, 2, 3, 4};
    private int[] Rot5EvenDislocY = {0, 1, 1, 2, 2, 3, 2, 1, 1, 0, 0, -1, 0, 1, 1, 2};
    private int[] Rot5OddDislocY = {0, 1, 2, 2, 3, 3, 3, 2, 1, 1, 0, 0, 1, 1, 2, 2};

    /*
    Function used to assemble the strips into the matrix. We have 12 cases, 6  rotation-dependent each, even and odd
    position of the "center Hexspace" (it's actually not in the center. it's the Hexspace which is at the first
    position of the HexSpaceEntity list
     */
    private void fillStripEntryInMatrix(HexSpaceEntity[][] boardMatrix, int posX, int posY, int[] disLocX,
                                        int[] disLocEvenY, int[] disLocOddY, int j, HexSpaceEntity hexSpaceEntity) {
        if (posX % 2 == 0) {
            boardMatrix[posX + disLocX[j]][posY + disLocEvenY[j]] = hexSpaceEntity;
        } else {
            boardMatrix[posX + disLocX[j]][posY + disLocOddY[j]] = hexSpaceEntity;
        }
    }

    public HexSpaceEntity[][] assembleBoard (int boardId){
        HexSpaceEntity[][] boardMatrix = this.createEmptyMatrix();
        this.board = boardRepository.findByBoardID(boardId);
        boardMatrix = this.assembleTiles(boardMatrix,this.getTiles(),this.getTilePositionX(),
                                        this.getTilePositionY(),this.getTilesRotation());
        boardMatrix = this.assembleStrips(boardMatrix,this.getStrips(),this.getStripPositionX(),
                                        this.getStripPositionY(),this.getStripRotation());
        boardMatrix = this.assembleAllBlockades(boardMatrix,this.blockades(),
                                                this.getRandomBlockades(this.getBlockadesCount()));
        boardMatrix = this.assembleEndingSpaces(boardMatrix,this.getEndingSpaces(),this.getEndingSpacesPositionX(),
                                                this.getEndingSpacesPositionY());
        return boardMatrix;
    }

    protected HexSpaceEntity[][] createEmptyMatrix() {
        HexSpaceEntity[][] boardMatrix = new HexSpaceEntity[100][100];
        return boardMatrix;
    }


    private List<TileEntity> getTiles() {
        List<TileEntity> Tiles = this.board.getTiles();
        return Tiles;
    }

    private List<Integer> getTilePositionX() {
        List<Integer> TilePositionX = this.board.getTilesPositionX();
        return TilePositionX;
    }

    private List<Integer> getTilePositionY() {
        List<Integer> TilePositionY = this.board.getTilesPositionY();
        return TilePositionY;
    }

    private List<Integer> getTilesRotation() {
        List<Integer> TileRotation = this.board.getTilesRotation();
        return TileRotation;
    }



    protected HexSpaceEntity[][] assembleTiles(HexSpaceEntity[][] boardMatrix, List<TileEntity> Tile,
                                            List<Integer> TilePositionX, List<Integer> TilePositionY,
                                            List<Integer> TileRotation) {
        for (int i = 0; i < Tile.size(); i++) {
            int currentTileRotation = TileRotation.get(i);
            List<HexSpaceEntity> currentTileHexSpaces = Tile.get(i).getHexSpaceEntities();
            for (int j = 0; j < currentTileHexSpaces.size(); j++) {
                if (j < 18) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + outerRingDislocX[j]][TilePositionY.get(i) +
                                outerRingDislocYEven[j]] = currentTileHexSpaces.get((j + (currentTileRotation * 3)) % 18);
                    } else {
                        boardMatrix[TilePositionX.get(i) + outerRingDislocX[j]][TilePositionY.get(i) +
                                outerRingDislocYOdd[j]] = currentTileHexSpaces.get((j + (currentTileRotation * 3)) % 18);
                    }
                } else if (j >= 18 && j < 30) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + midRingDislocX[j-18]][TilePositionY.get(i) + midRingDislocYEven[j-18]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 12));
                    } else {
                        boardMatrix[TilePositionX.get(i) + midRingDislocX[j-18]][TilePositionY.get(i) + midRingDislocYOdd[j-18]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 12));
                    }
                } else if (j >= 30 && j < 36) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocX[j-30]][TilePositionY.get(i) + innerRingDislocYEven[j-30]]
                                = currentTileHexSpaces.get(30 + (((j - 30) + (currentTileRotation)) % 6));
                    } else {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocX[j-30]][TilePositionY.get(i) + innerRingDislocYOdd[j-30]]
                                = currentTileHexSpaces.get(30 + (((j - 30) + (currentTileRotation)) % 6));
                    }
                } else {
                    System.out.println(j);
                    boardMatrix[TilePositionX.get(i)][TilePositionY.get(i)]
                            = currentTileHexSpaces.get((j));
                }
            }
        }
        return boardMatrix;
    }

    private List<StripEntity> getStrips() {
        List<StripEntity> Strips = this.board.getStrip();
        return Strips;
    }

    private List<Integer> getStripPositionX() {
        List<Integer> StripPositionX = this.board.getStripPositionX();
        return StripPositionX;
    }

    private List<Integer> getStripPositionY() {
        List<Integer> StripPositionY = this.board.getStripPositionY();
        return StripPositionY;
    }

    private List<Integer> getStripRotation() {
        List<Integer> StripRotation = this.board.getStripRotation();
        return StripRotation;
    }

    protected HexSpaceEntity[][] assembleStrips(HexSpaceEntity[][] boardMatrix, List<StripEntity> Strips,
                                             List<Integer> StripPositionX, List<Integer> StripPositionY,
                                             List<Integer> StripRotation) {
        for (int i = 0; i < Strips.size(); i++) {
            int currentStripRotation = StripRotation.get(i);
            List<HexSpaceEntity> currentStripHexSpaces = Strips.get(i).getHexSpaceEntities();
            for (int j = 0; j < currentStripHexSpaces.size(); j++) {
                switch (currentStripRotation) {
                    case 0:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot0DislocX, Rot0EvenDislocY, Rot0OddDislocY, j, currentStripHexSpaces.get(j));
                        break;
                    case 1:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot1DislocX, Rot1EvenDislocY, Rot1OddDislocY, j, currentStripHexSpaces.get(j));
                        break;
                    case 2:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot2DislocX, Rot2EvenDislocY, Rot2OddDislocY, j, currentStripHexSpaces.get(j));
                        break;
                    case 3:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot3DislocX, Rot3EvenDislocY, Rot3OddDislocY, j, currentStripHexSpaces.get(j));
                        break;
                    case 4:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot4DislocX, Rot4EvenDislocY, Rot4OddDislocY, j, currentStripHexSpaces.get(j));
                        break;
                    case 5:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot5DislocX, Rot5EvenDislocY, Rot5OddDislocY, j, currentStripHexSpaces.get(j));
                        break;
                }
            }
        }
        return boardMatrix;
    }

    private List<List<List<Integer>>> blockades(){
        List<List<List<Integer>>> blockades = this.board.getBlockade();
        return blockades;
    }

    protected int getBlockadesCount(){
        return (int) blockadeSpaceRepository.count();
    }

    protected BlockadeSpaceEntity getBlockadeSpace(int id){
        BlockadeSpaceEntity blockadeSpace = blockadeSpaceRepository.findByBlockadeID(id);
        return blockadeSpace;
    }

    protected List<Integer>getRandomBlockades(int blockadesCount){
        List<Integer> blockadeIds = new ArrayList<>();
        for(int i = 1; i <= blockadesCount; i++) {
            blockadeIds.add(i);
        }
        Collections.shuffle(blockadeIds);
        return blockadeIds;
    }

    protected HexSpaceEntity[][] assembleOneBlockade(HexSpaceEntity[][] boardMatrix, List<Integer> positionsX,
                                                     List<Integer> positionsY, BlockadeSpaceEntity blockadeSpace) {
            for (int j = 0; j < positionsX.size(); j++) {
            //assign blockadeSpaceEntities according to the random order of blockades.
            boardMatrix[positionsX.get(j)][positionsY.get(j)] = blockadeSpace;
            }
        return boardMatrix;
    }

    private HexSpaceEntity[][] assembleAllBlockades(HexSpaceEntity[][] boardMatrix,List<List<List<Integer>>> blockades,
                                                   List<Integer>blockadeIds) {
        for(int i = 0; i<blockades.size();i++) {
            List<Integer> positionsX = blockades.get(i).get(0);
            List<Integer> positionsY = blockades.get(i).get(1);
            assembleOneBlockade(boardMatrix, positionsX, positionsY, getBlockadeSpace(blockadeIds.get(i)));
        }
        return boardMatrix;
    }

    public List<HexSpaceEntity> getEndingSpaces(){
        List<HexSpaceEntity> endingSpaces = this.board.getEndingSpaces();
        return endingSpaces;
    }

    public List<Integer> getEndingSpacesPositionX(){
        List<Integer> endingSpacesPositionX = this.board.getEndingSpacePositionX();
        return endingSpacesPositionX;
    }

    public List<Integer> getEndingSpacesPositionY(){
        List<Integer> endingSpacesPositionY = this.board.getEndingSpacePositionY();
        return endingSpacesPositionY;
    }

    public HexSpaceEntity[][] assembleEndingSpaces(HexSpaceEntity[][] boardMatrix, List<HexSpaceEntity> endingSpaces,
                                                   List<Integer> endingSpacesPositionX,List<Integer> endingSpacesPositionY){
        for (int i = 0; i< endingSpaces.size(); i++) {
            boardMatrix[endingSpacesPositionX.get(i)][endingSpacesPositionY.get(i)] = endingSpaces.get(i);
        }
        return boardMatrix;
    }

    /*
    The assembleBoard creates a Matrix consisting of all the elements from the GameEntity
    with ID = boardNumber and returns the matrix with the well prepared GameEntity. The assembler
    starts with a matrix consisting of only HexSpaces with infinite cost and colour EMPTY
    and replace them with the right HexSpaces according to the values he reads out of the Database.
    */

    /*
    Used by the GameBoard and returns an ArrayList of Arrays with the coordinates of the blockades
    in the pathMatrix. This is needed so that the GameEntity can assign blockade instances to them.
    We consider this more efficient than parsing the pathMatrix, since the assembler has
    the information abouts these positions already.
     */
    public List<Blockade> getBlockades(int boardID) {
        return null;
    }

    /*
    Used by the GameBorad and returns an Arrays with the HexSpaces of the starting-fields.
    The GameEntity needs these information to place the playing Pieces. We rather request these
    informations from the assembler than parsing the matrix.
     */
    public List<HexSpace> getStartingFields(int boardID) {
        return null;
    }

    /*
    Used by the GameBorad and returns an Arrays with the HexSpaces of the ending-fields.
    The GameEntity needs these information to place the playing Pieces. We rather request these
    informations from the Assembler than parsing the matrix.
     */
    public List<HexSpace> getEndingFields(int boardID) {
        return null;
    }

    public BoardEntity getBoard() {
        return board;
    }
    /*
    public StripEntity getStrip() {
        return strip;
    }

    public TileEntity getTile() {
        return tile;
    }
    */
}
