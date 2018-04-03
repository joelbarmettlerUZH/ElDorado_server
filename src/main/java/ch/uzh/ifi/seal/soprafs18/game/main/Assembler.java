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
    private int[] outerRingDislocY = {3,3,2,2,1,0,-1,-2,-2,-3,-2,-2,-1,0,1,2,2,3};

    /*
    compute relative positions for MidRing
    */
    private int[] midRingDislocX = {0,1,2,2,2,1,0,-1,-2,-2,-2,-1};
    private int[] midRingDislocY = {2,1,1,0,-1,-1,-2,-1,-1,0,1,1};
    /*

    /*
    compute relative positions for InnerRing
    */
    private int[] innerRingDislocX = {0,1,1,0,-1,-1};
    private int[] innerRingDislocY = {1,1,0,-1,0,1};
    /*
    The assembleBoard creates a Matrix consisting of all the elements from the GameEntity
    with ID = boardNumber and returns the matrix with the well prepared GameEntity. The assembler
    starts with a matrix consisting of only HexSpaces with infinite cost and colour EMPTY
    and replace them with the right HexSpaces according to the values he reads out of the Database.
     */
    public HexSpaceEntity[][] assembleBoard(int boardID){
        HexSpaceEntity[][] boardMatrix = new HexSpaceEntity[100][100];
        board = boardRepository.findByBoardID(boardID);
        List<TileEntity> Tile = board.getTiles();
        List<Integer> TilePositionX = board.getTilesPositionX();
        List<Integer> TilePositionY = board.getTilesPositionY();
        List<Integer> TileRotation = board.getStripRotation();
        for(int i = 0; i < Tile.size(); i++){
            int currentTileRotation = TileRotation.get(i);
            List<HexSpaceEntity> currentTileHexSpaces = Tile.get(i).getHexSpaceEntities();
            for(int j = 0; j < currentTileHexSpaces.size();j++){
                if (j<18) {
                    boardMatrix[TilePositionX.get(i) + outerRingDislocX[j]][TilePositionY.get(i) + outerRingDislocY[j]]
                            = currentTileHexSpaces.get((j + (currentTileRotation * 3))%18);
                }
                else if (j>=18 && j<30){
                    boardMatrix[TilePositionX.get(i) + midRingDislocX[j]][TilePositionY.get(i) + midRingDislocY[j]]
                            = currentTileHexSpaces.get((j + (currentTileRotation * 2))%30);
                }
                else if (j>=30 && j<36){
                    boardMatrix[TilePositionX.get(i) + innerRingDislocX[j]][TilePositionY.get(i) + innerRingDislocY[j]]
                            = currentTileHexSpaces.get((j + (currentTileRotation))%36);
                }
                else {
                    boardMatrix[TilePositionX.get(i)][TilePositionY.get(i)]
                            = currentTileHexSpaces.get((j + (currentTileRotation))%36);
                }
            }


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
