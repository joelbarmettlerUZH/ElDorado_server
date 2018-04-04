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

    public HexSpaceEntity[][] createEmptyMatrix(){
        HexSpaceEntity[][] boardMatrix = new HexSpaceEntity[100][100];
        return boardMatrix;
    }


    public List<TileEntity> getTiles(){
        List<TileEntity> Tiles = board.getTiles();
        return Tiles;
    }

    public List<Integer> getTilePositionX(){
        List<Integer> TilePositionX = board.getTilesPositionX();
        return TilePositionX;
    }

    public List<Integer> getTilePositionY(){
        List<Integer> TilePositionY = board.getTilesPositionY();
        return TilePositionY;
    }

    public List<Integer> getTilesRotation(){
        List<Integer> TileRotation = board.getTilesRotation();
        return TileRotation;
    }

    public HexSpaceEntity[][] assembleTiles(HexSpaceEntity[][] boardMatrix, List<TileEntity> Tile,
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
                        boardMatrix[TilePositionX.get(i) + midRingDislocX[j]][TilePositionY.get(i) + midRingDislocYEven[j]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 30));
                    } else {
                        boardMatrix[TilePositionX.get(i) + midRingDislocX[j]][TilePositionY.get(i) + midRingDislocYOdd[j]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 30));
                    }
                } else if (j >= 30 && j < 36) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocX[j]][TilePositionY.get(i) + innerRingDislocYEven[j]]
                                = currentTileHexSpaces.get(36 + (((j - 36) + (currentTileRotation)) % 36));
                    } else {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocX[j]][TilePositionY.get(i) + innerRingDislocYOdd[j]]
                                = currentTileHexSpaces.get(36 + (((j - 36) + (currentTileRotation)) % 36));
                    }
                } else {
                    boardMatrix[TilePositionX.get(i)][TilePositionY.get(i)]
                            = currentTileHexSpaces.get((j + (currentTileRotation)) % 36);
                }
            }
        }
        return boardMatrix;
    }

    public List<StripEntity> getStrips(){
        List<StripEntity> Strips = board.getStrip();
        return Strips;
    }

    public List<Integer> getStripPositionX(){
        List<Integer> StripPositionX = board.getStripPositionX();
        return StripPositionX;
    }

    public List<Integer> getStripPositionY(){
        List<Integer> StripPositionY = board.getStripPositionY();
        return StripPositionY;
    }

    public List<Integer> getStripRotation(){
        List<Integer> StripRotation = board.getStripRotation();
        return StripRotation;
    }

    public HexSpaceEntity[][] assembleStrips(HexSpaceEntity[][] boardMatrix, List<StripEntity> Strips,
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
    /*
    The assembleBoard creates a Matrix consisting of all the elements from the GameEntity
    with ID = boardNumber and returns the matrix with the well prepared GameEntity. The assembler
    starts with a matrix consisting of only HexSpaces with infinite cost and colour EMPTY
    and replace them with the right HexSpaces according to the values he reads out of the Database.

    public HexSpaceEntity[][] assembleBoard(int boardID) {
        //create empty matrix
        HexSpaceEntity[][] boardMatrix = new HexSpaceEntity[100][100];
        //get board that should be created
        board = boardRepository.findByBoardID(boardID);

        //Assemble Tiles into Matrix
        List<TileEntity> Tile = board.getTiles();
        List<Integer> TilePositionX = board.getTilesPositionX();
        List<Integer> TilePositionY = board.getTilesPositionY();
        List<Integer> TileRotation = board.getStripRotation();

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
                        boardMatrix[TilePositionX.get(i) + midRingDislocX[j]][TilePositionY.get(i) + midRingDislocYEven[j]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 30));
                    } else {
                        boardMatrix[TilePositionX.get(i) + midRingDislocX[j]][TilePositionY.get(i) + midRingDislocYOdd[j]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 30));
                    }
                } else if (j >= 30 && j < 36) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocX[j]][TilePositionY.get(i) + innerRingDislocYEven[j]]
                                = currentTileHexSpaces.get(36 + (((j - 36) + (currentTileRotation)) % 36));
                    } else {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocX[j]][TilePositionY.get(i) + innerRingDislocYOdd[j]]
                                = currentTileHexSpaces.get(36 + (((j - 36) + (currentTileRotation)) % 36));
                    }
                } else {
                    boardMatrix[TilePositionX.get(i)][TilePositionY.get(i)]
                            = currentTileHexSpaces.get((j + (currentTileRotation)) % 36);
                }
            }
        }

        //Assemble Strips into matrix
        List<StripEntity> Strip = board.getStrip();
        List<Integer> StripPositionX = board.getStripPositionX();
        List<Integer> StripPositionY = board.getStripPositionY();
        List<Integer> StripRotation = board.getStripRotation();

        for (int i = 0; i < Tile.size(); i++) {
            int currentStripRotation = StripRotation.get(i);
            List<HexSpaceEntity> currentStripHexSpaces = Strip.get(i).getHexSpaceEntities();
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

        //Assemble Blockades into matrix
            //create random order of blockadeId
        List<Integer> blockadeIds = new ArrayList<>();
        for (int i = 1; i <= blockadeSpaceRepository.count(); i++){
            blockadeIds.add(i);
        }
        Collections.shuffle(blockadeIds);

        //iterate though blockade of the DB representation of the Board.
        List<List<List<Integer>>> blockades = board.getBlockade();
        for (int i = 0; i < blockades.size(); i++){
            List<Integer> positionsX = blockades.get(i).get(0);
            List<Integer> positionsY = blockades.get(i).get(1);
            for (int j = 0; i < positionsX.size(); j++){
                //assign blockadeSpaceEntities according to the random order of blockades.
                boardMatrix[positionsX.get(j)][positionsY.get(j)] =
                        blockadeSpaceRepository.findByBlockadeID(blockadeIds.get(i));
            }
        }

        //Assemble EndingSpaces into matrix
        List<HexSpaceEntity> endingSpaces = board.getEndingSpaces();
        List<Integer> endingSpacesPositionX = board.getEndingSpacePositionX();
        List<Integer> endingSpacesPositionY = board.getEndingSpacePositionY();
        for (int i = 0; i< endingSpaces.size(); i++){
            boardMatrix[endingSpacesPositionX.get(i)][endingSpacesPositionY.get(i)]=endingSpaces.get(i);
        }
    return boardMatrix;
    }
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
