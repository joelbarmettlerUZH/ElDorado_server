package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BoardEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.StripEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.TileEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BoardRepository;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import jdk.nashorn.internal.ir.Block;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.sql.Array;
import java.util.List;

public class Assembler {
    /*
    Instance of Database containing all the GameEntity
     */
    protected BoardEntity board;
    private StripEntity strip;
    private TileEntity tile;

    @Autowired
    private BoardRepository boardRepository;

    /*
    compute relative positions for OuterRing
     */
    private int[] outerRingDislocX = {0,1,2,3,3,3,3,2,1,0,-1,-2,-3,-3,-3,-3,-2,-1};
    private int[] outerRingDislocYEven = {3,3,2,2,1,0,-1,-2,-2,-3,-2,-2,-1,0,1,2,2,3};
    private int[] outerRingDislocYOdd = {3,2,2,1,0,-1,-2,-2,-3,-3,-3,-2,-2,-1,0,1,2,2};

    /*
    compute relative positions for MidRing
    */
    private int[] midRingDislocX = {0,1,2,2,2,1,0,-1,-2,-2,-2,-1};
    private int[] midRingDislocYEven = {2,2,1,0,-1,-1,-2,-1,-1,0,1,2};
    private int[] midRingDislocYOdd = {2,1,1,0,-1,-2,-2,-2,-1,0,1,1};
    /*

    /*
    compute relative positions for InnerRing
    */
    private int[] innerRingDislocX = {0,1,1,0,-1,-1};
    private int[] innerRingDislocYEven = {1,1,0,-1,0,1};
    private int[] innerRingDislocYOdd = {1,0,-1,-1,-1,0};


    /*
    Terrain-Strips Dislocation
     */
    private int[] Rot0DislocX = {0,1,2,3,4,5,5,4,3,2,1,0,1,2,3,4};
    private int[] Rot0EvenDislocY = {0,1,0,0,-1,-1,-2,-3,-2,-2,-1,-1,0,-1,-1,-2};
    private int[] Rot0OddDislocY = {0,0,0,-1,-1,-2,-3,-3,-3,-2,-2,-1,-1,-1,-2,-2};

    private int[] Rot1DislocX = {0,1,1,1,1,1,0,-1,-1,-1,-1,-1,0,0,0,0};
    private int[] Rot1EvenDislocY = {0,-1,-2,-3,-4,-5,-5,-5,-4,-3,-2,-1,-1,-2,-3,-4};
    private int[] Rot1OddDislocY = {0,0,-1,-2,-3,-4,-5,-4,-3,-2,-1,0,-1,-2,-3,-4};

    private int[] Rot2DislocX = {0,0,-1,-2,-3,-4,-5,-5,-4,-3,-2,-1,-1,-2,-3,-4};
    private int[] Rot2EvenDislocY = {0,-1,-2,-2,-3,-3,-3,-2,-1,-1,0,0,-1,-1,-2,-2};
    private int[] Rot2OddDislocY = {0,-1,-1,-2,-2,-3,-2,-1,-1,0,0,1,0,-1,-1,-2};

    private int[] Rot3DislocX = {0,-1,-1,-1,-1,-1,0,1,1,1,1,1,0,0,0,0};
    private int[] Rot3EvenDislocY = {0,0,1,2,3,4,5,4,3,2,1,0,1,2,3,4};
    private int[] Rot3OddDislocY = {0,1,2,3,4,5,5,5,4,3,2,1,1,2,3,4};

    private int[] Rot4DislocX = {0,-1,-2,-3,-4,-5,-5,-4,-3,-2,-1,0,-1,-2,-3,-4};
    private int[] Rot4EvenDislocY = {0,0,0,1,1,2,3,3,3,2,2,1,1,1,2,2};
    private int[] Rot4OddDislocY = {0,-1,0,0,1,1,2,3,2,2,1,1,0,1,1,2};

    private int[] Rot5DislocX = {0,0,1,2,3,4,5,5,4,3,2,1,1,2,3,4};
    private int[] Rot5EvenDislocY = {0,1,1,2,2,3,2,1,1,0,0,-1,0,1,1,2};
    private int[] Rot5OddDislocY = {0,1,2,2,3,3,3,2,1,1,0,0,1,1,2,2};

    /*
    The assembleBoard creates a Matrix consisting of all the elements from the GameEntity
    with ID = boardNumber and returns the matrix with the well prepared GameEntity. The assembler
    starts with a matrix consisting of only HexSpaces with infinite cost and colour EMPTY
    and replace them with the right HexSpaces according to the values he reads out of the Database.
     */
    public HexSpaceEntity[][] assembleBoard(int boardID){
        HexSpaceEntity[][] boardMatrix = new HexSpaceEntity[100][100];
        board = boardRepository.findByBoardID(boardID);

        //Assemble Tiles to Matrix
        List<TileEntity> Tile = board.getTiles();
        List<Integer> TilePositionX = board.getTilesPositionX();
        List<Integer> TilePositionY = board.getTilesPositionY();
        List<Integer> TileRotation = board.getStripRotation();

        for(int i = 0; i < Tile.size(); i++){
            int currentTileRotation = TileRotation.get(i);
            List<HexSpaceEntity> currentTileHexSpaces = Tile.get(i).getHexSpaceEntities();
            for(int j = 0; j < currentTileHexSpaces.size();j++) {
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

        //Assemble Strips
        List<StripEntity> Strip = board.getStrip();
        List<Integer> StripPositionX = board.getStripPositionX();
        List<Integer> StripPositionY = board.getStripPositionY();
        List<Integer> StripRotation = board.getStripRotation();

        for(int i = 0; i < Tile.size(); i++) {
            int currentStripRotation = TileRotation.get(i);
            List<HexSpaceEntity> currentStripHexSpaces = Strip.get(i).getHexSpaceEntities();
        }
        return null;
    }

    /*
    Used by the GameBorad and returns an ArrayList of Arrays with the coordinates of the blockades
    in the pathMatrix. This is needed so that the GameEntity can assign blockade instances to them.
    We consider this more efficient than parsing the pathMatrix, since the assembler has
    the information abouts these positions already.
     */
    public List<Block> getBlockades(int boardID){
        return null;
    }

    /*
    Used by the GameBorad and returns an Arrays with the HexSpaces of the starting-fields.
    The GameEntity needs these information to place the playing Pieces. We rather request these
    informations from the assembler than parsing the matrix.
     */
    public List<HexSpace> getStartingFields(int boardID){
        return null;
    }

    /*
    Used by the GameBorad and returns an Arrays with the HexSpaces of the ending-fields.
    The GameEntity needs these information to place the playing Pieces. We rather request these
    informations from the Assembler than parsing the matrix.
     */
    public List<HexSpace> getEndingFields(int boardID){
        return null;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public StripEntity getStrip() {
        return strip;
    }

    public TileEntity getTile() {
        return tile;
    }
}
