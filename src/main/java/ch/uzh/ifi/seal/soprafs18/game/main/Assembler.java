package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.*;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BlockadeSpaceRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BoardRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.service.BlockadeSpaceService;
import ch.uzh.ifi.seal.soprafs18.game.board.service.BoardService;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

@Component
public class Assembler {

    @Autowired
    private static BoardService boardService;
    @Autowired
    private static BlockadeSpaceService blockadeSpaceService;


    /*
    compute relative positions for OuterRing
     */
    private static int[] outerRingDislocY = {0, 1, 2, 3, 3, 3, 3, 2, 1, 0, -1, -2, -3, -3, -3, -3, -2, -1};
    private static int[] outerRingDislocXEven = {-3, -3, -2, -2, -1, 0, 1, 2, 2, 3, 2, 2, 1, 0, -1, -2, -2, -3};
    private static int[] outerRingDislocXOdd = {-3, -2, -2, -1, 0, 1, 2, 2, 3, 3, 3, 2, 2, 1, 0, -1, -2, -2};

    /*
    compute relative positions for MidRing
    */
    private static int[] midRingDislocY = {0, 1, 2, 2, 2, 1, 0, -1, -2, -2, -2, -1};
    private static int[] midRingDislocXEven = {-2, -2, -1, -0, 1, 1, 2, 1, 1, 0, -1, -2};
    private static int[] midRingDislocXOdd = {-2, -1, -1, 0, 1, 2, 2, 2, 1, 0, -1, -1};
    /*

    /*
    compute relative positions for InnerRing
    */
    private static int[] innerRingDislocY = {0, 1, 1, 0, -1, -1};
    private static int[] innerRingDislocXEven = {-1, -1, 0, 1, 0, 1};
    private static int[] innerRingDislocXOdd = {-1, 0, 1, 1, 1, 0};


    /*
    Terrain-Strips Dislocation
     */
    private static int[] Rot0DislocY = {0, 1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4};
    private static int[] Rot0EvenDislocX = {0, -1, 0, 0, 1, 1, 2, 3, 2, 2, 1, 1, 0, 1, 1, 2};
    private static int[] Rot0OddDislocX = {0, 0, 0, 1, 1, 2, 3, 3, 3, 2, 2, 1, 1, 1, 2, 2};

    private static int[] Rot1DislocY = {0, 1, 1, 1, 1, 1, 0, -1, -1, -1, -1, -1, 0, 0, 0, 0};
    private static int[] Rot1EvenDislocX = {0, 1, 2, 3, 4, 5, 5, 5, 4, 3, 2, 1, 1, 2, 3, 4};
    private static int[] Rot1OddDislocX = {0, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4};

    private static int[] Rot2DislocY = {0, 0, -1, -2, -3, -4, -5, -5, -4, -3, -2, -1, -1, -2, -3, -4};
    private static int[] Rot2EvenDislocX = {0, 1, 2, 2, 3, 3, 3, 2, 1, 1, 0, 0, 1, 1, 2, 2};
    private static int[] Rot2OddDislocX = {0, 1, 1, 2, 2, 3, 2, 1, 1, 0, 0, -1, 0, 1, 1, 2};

    private static int[] Rot3DislocY = {0, -1, -1, -1, -1, -1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0};
    private static int[] Rot3EvenDislocX = {0, 0, -1, -2, -3, -4, -5, -4, -3, -2, -1, 0, -1, -2, -3, -4};
    private static int[] Rot3OddDislocX = {0, -1, -2, -3, -4, -5, -5, -5, -4, -3, -2, -1, -1, -2, -3, -4};

    private static int[] Rot4DislocY = {0, -1, -2, -3, -4, -5, -5, -4, -3, -2, -1, 0, -1, -2, -3, -4};
    private static int[] Rot4EvenDislocX = {0, 0, 0, -1, -1, -2, -3, -3, -3, -2, -2, -1, -1, -1, -2, -2};
    private static int[] Rot4OddDislocX = {0, 1, 0, 0, -1, -1, -2, -3, -2, -2, -1, -1, 0, -1, -1, -2};

    private static int[] Rot5DislocY = {0, 0, 1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 1, 2, 3, 4};
    private static int[] Rot5EvenDislocX = {0, -1, -1, -2, -2, -3, -2, -1, -1, 0, 0, 1, 0, -1, -1, -2};
    private static int[] Rot5OddDislocX = {0, -1, -2, -2, -3, -3, -3, -2, -1, -1, 0, 0, -1, -1, -2, -2};

    /*
    Function used to assemble the strips into the matrix. We have 12 cases, 6  rotation-dependent each, even and odd
    position of the "center Hexspace" (it's actually not in the center. it's the Hexspace which is at the first
    position of the HexSpaceEntity list
     */
    private static void fillStripEntryInMatrix(HexSpaceEntity[][] boardMatrix, int posX, int posY, int[] disLocY,
                                        int[] disLocEvenX, int[] disLocOddX, int j, HexSpaceEntity hexSpaceEntity) {
        if (posY % 2 == 0) {
            boardMatrix[posX + disLocEvenX[j]][posY + disLocY[j]] = hexSpaceEntity;
        } else {
            boardMatrix[posX + disLocOddX[j]][posY + disLocY[j]] = hexSpaceEntity;
        }
    }

    //Converts the temporary Matrix of entities to matrix of HexSpaces
    private static HexSpace[][] convertMatrix(HexSpaceEntity[][] entityMarix, Game game){
        HexSpace[][] hexSpaceMatrix = new HexSpace[100][100];
        for (int i = 0; i<entityMarix.length;i++){
            for (int j = 0; j<entityMarix[0].length;j++){
                hexSpaceMatrix[i][j]=new HexSpace(entityMarix[i][j], i, j, game);
            }
        }
        return hexSpaceMatrix;
    }

    public static HexSpace[][] assembleBoard (int boardId,Game game){
        HexSpaceEntity[][] boardMatrix = Assembler.createEmptyMatrix();
        BoardEntity board = boardService.getBoard(boardId);
        boardMatrix = Assembler.assembleTiles(boardMatrix,board.getTiles(),board.getTilesPositionX(),
                                                board.getTilesPositionY(),board.getTilesRotation());
        boardMatrix = Assembler.assembleStrips(boardMatrix,board.getStrip(),board.getStripPositionX(),
                                                board.getStripPositionY(),board.getStripRotation());
        boardMatrix = Assembler.assembleBlockades(boardMatrix,board.getBlockadeId(),board.getBlockandePositionX(),
                                                board.getBlockandePositionY(),
                                                getRandomBlockades(Assembler.getBlockadesCount()));
        boardMatrix = Assembler.assembleEndingSpaces(boardMatrix,board.getEndingSpaces(),
                                                    board.getEndingSpacePositionX(),
                                                    board.getEndingSpacePositionY());
        return convertMatrix(boardMatrix, game);
    }

    protected static HexSpaceEntity[][] createEmptyMatrix() {
        return new HexSpaceEntity[100][100];
    }

    protected static HexSpaceEntity[][] assembleTiles(HexSpaceEntity[][] boardMatrix, List<TileEntity> Tile,
                                            List<Integer> TilePositionX, List<Integer> TilePositionY,
                                            List<Integer> TileRotation) {
        for (int i = 0; i < Tile.size(); i++) {
            int currentTileRotation = TileRotation.get(i);
            List<HexSpaceEntity> currentTileHexSpaces = Tile.get(i).getHexSpaceEntities();
            for (int j = 0; j < currentTileHexSpaces.size(); j++) {
                if (j < 18) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + outerRingDislocXEven[j]][TilePositionY.get(i) +
                                outerRingDislocY[j]] = currentTileHexSpaces.get((j + (currentTileRotation * 3)) % 18);
                    } else {
                        boardMatrix[TilePositionX.get(i) + outerRingDislocXOdd[j]][TilePositionY.get(i) +
                                outerRingDislocY[j]] = currentTileHexSpaces.get((j + (currentTileRotation * 3)) % 18);
                    }
                } else if (j < 30) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + midRingDislocXEven[j-18]][TilePositionY.get(i) + midRingDislocY[j-18]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 12));
                    } else {
                        boardMatrix[TilePositionX.get(i) + midRingDislocXOdd[j-18]][TilePositionY.get(i) + midRingDislocY[j-18]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 12));
                    }
                } else if (j < 36) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocXEven[j-30]][TilePositionY.get(i) + innerRingDislocY[j-30]]
                                = currentTileHexSpaces.get(30 + (((j - 30) + (currentTileRotation)) % 6));
                    } else {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocXOdd[j-30]][TilePositionY.get(i) + innerRingDislocY[j-30]]
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


    protected static HexSpaceEntity[][] assembleStrips(HexSpaceEntity[][] boardMatrix, List<StripEntity> Strips,
                                             List<Integer> StripPositionX, List<Integer> StripPositionY,
                                             List<Integer> StripRotation) {
        for (int i = 0; i < Strips.size(); i++) {
            int currentStripRotation = StripRotation.get(i);
            List<HexSpaceEntity> currentStripHexSpaces = Strips.get(i).getHexSpaceEntities();
            for (int j = 0; j < currentStripHexSpaces.size(); j++) {
                switch (currentStripRotation) {
                    case 0:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot0DislocY, Rot0EvenDislocX, Rot0OddDislocX, j, currentStripHexSpaces.get(j));
                        break;
                    case 1:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot1DislocY, Rot1EvenDislocX, Rot1OddDislocX, j, currentStripHexSpaces.get(j));
                        break;
                    case 2:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot2DislocY, Rot2EvenDislocX, Rot2OddDislocX, j, currentStripHexSpaces.get(j));
                        break;
                    case 3:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot3DislocY, Rot3EvenDislocX, Rot3OddDislocX, j, currentStripHexSpaces.get(j));
                        break;
                    case 4:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot4DislocY, Rot4EvenDislocX, Rot4OddDislocX, j, currentStripHexSpaces.get(j));
                        break;
                    case 5:
                        fillStripEntryInMatrix(boardMatrix, StripPositionX.get(i), StripPositionY.get(i),
                                Rot5DislocY, Rot5EvenDislocX, Rot5OddDislocX, j, currentStripHexSpaces.get(j));
                        break;
                }
            }
        }
        return boardMatrix;
    }

    private static int getBlockadesCount(){
        return blockadeSpaceService.getBlockadeCount();
    }

    protected static List<Integer>getRandomBlockades(int blockadesCount){
        List<Integer> blockadeIds = new ArrayList<>();
        for(int i = 1; i <= blockadesCount; i++) {
            blockadeIds.add(i);
        }
        Collections.shuffle(blockadeIds);
        return blockadeIds;
    }

    private static HexSpaceEntity[][] assembleBlockades(HexSpaceEntity[][] boardMatrix, List<Integer> blockadeId,
                                                        List<Integer> blockadePositionX, List<Integer> blockadePositionY,
                                                        List<Integer> randomBlockadeIds) {
        for(int i = 0; i<blockadeId.size();i++) {
            int j = blockadeId.get(i)%Assembler.getBlockadesCount();
            //j defines which position of the random ordered blockade directory
            // use % to make sure that we do not encounter a problem if the board has more blockades than in our DB
            // in ths case we just use the same blockades multiple times
            boardMatrix[blockadePositionX.get(i)][blockadePositionY.get(i)] =
                    blockadeSpaceService.getBlockadeSpaceEntity(randomBlockadeIds.get(j));
        }
        return boardMatrix;
    }

    public static HexSpaceEntity[][] assembleEndingSpaces(HexSpaceEntity[][] boardMatrix, List<HexSpaceEntity> endingSpaces,
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
    public List<Blockade> getBlockades(char boardId) {
        return null;
    }

    /*
    Used by the GameBorad and returns an Arrays with the HexSpaces of the starting-fields.
    The GameEntity needs these information to place the playing Pieces. We rather request these
    informations from the assembler than parsing the matrix.
     */
    public List<HexSpace> getStartingFields(char boardId, Game game) {
        List<HexSpace> StartingSpaces = new ArrayList<>();
        BoardEntity board = boardService.getBoard(boardId);
        List<TileEntity> containedTiles = board.getTiles();
        List<Integer> containedTilesPosX = board.getTilesPositionX();
        List<Integer> containedTilesPosY = board.getTilesPositionY();
        List<Integer> containedTilesRot = board.getTilesRotation();
        for (int i = 0; i < containedTiles.size(); i++) {
            if (containedTiles.get(i).getTileID() == 'A' || containedTiles.get(i).getTileID() == 'B') {
                int rotation = containedTilesRot.get(i);
                int posX = containedTilesPosX.get(i);
                int posY = containedTilesPosY.get(i);
                for (int j = 9; j>=6; j--) {
                    if (rotation % 2 == 0) {
                        StartingSpaces.add(new HexSpace(containedTiles.get(i).getHexSpaceEntities().get(j),
                                posX + outerRingDislocXEven[(i + (3 * rotation)) % 18],
                                posY + outerRingDislocY[(i + (3 * rotation)) % 18], game));
                    } else {
                        StartingSpaces.add(new HexSpace(containedTiles.get(i).getHexSpaceEntities().get(j),
                                posX + outerRingDislocXOdd[(i + 3 * rotation) % 18],
                                posY + outerRingDislocY[(i + 3 * rotation) % 18], game));
                    }
                }
            }
        }
        return StartingSpaces;
    }

    /*
    Used by the GameBorad and returns an Arrays with the HexSpaces of the ending-fields.
    The GameEntity needs these information to place the playing Pieces. We rather request these
    informations from the Assembler than parsing the matrix.
     */
    public List<HexSpace> getEndingFields(char boardId, Game game) {
        BoardEntity board = boardService.getBoard(boardId);
        List<HexSpace> EndingSpaces = new ArrayList<>();
        for (int i = 0; i <board.getEndingSpaces().size(); i++)
            EndingSpaces.add(new HexSpace(board.getEndingSpaces().get(i), board.getEndingSpacePositionX().get(i),
                            board.getEndingSpacePositionX().get(i), game));
        return EndingSpaces;
    }

    public BoardEntity getBoard(char boardId) {
        return boardService.getBoard(boardId);
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
