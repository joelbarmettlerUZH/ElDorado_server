package ch.uzh.ifi.seal.soprafs18.game.main;

import antlr.collections.impl.LList;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.*;
import ch.uzh.ifi.seal.soprafs18.game.board.service.BlockadeSpaceService;
import ch.uzh.ifi.seal.soprafs18.game.board.service.BoardService;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.Matrix;
import ch.uzh.ifi.seal.soprafs18.utils.SpringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Assembler implements Serializable {

    //private BoardService boardService;
    private BoardService boardService = SpringUtils.getBean(BoardService.class);
    private BlockadeSpaceService blockadeSpaceService = SpringUtils.getBean(BlockadeSpaceService.class);

    /*
    @Autowired
    private BoardService boardService;


    @Autowired
    private BlockadeSpaceService blockadeSpaceService;
    */
    /*
    public Assembler(int boardId){
        this.boardId=boardId;
    }
    */

    public Assembler() {
    }

    //private int boardId;

    /**
     * compute relative positions for OuterRing
     */
    private int[] outerRingDislocX = {-3, -2, -1, 0, 1, 2, 3, 3, 3, 3, 2, 1, 0, -1, -2, -3, -3, -3};
    private int[] outerRingDislocYEven = {1, 2, 2, 3, 2, 2, 1, 0, -1, -2, -2, -3, -3, -3, -2, -2, -1, 0};
    private int[] outerRingDislocYOdd = {2, 2, 3, 3, 3, 2, 2, 1, 0, -1, -2, -2, -3, -2, -2, -1, 0, 1};

    /**
     * compute relative positions for MidRing
     **/
    private int[] midRingDislocX = {-2, -1, 0, 1, 2, 2, 2, 1, 0, -1, -2, -2};
    private int[] midRingDislocYEven = {1, 1, 2, 1, 1, 0, -1, -2, -2, -2, -1, 0};
    private int[] midRingDislocYOdd = {1, 2, 2, 2, 1, 0, -1, -1, -2, -1, -1, 0};


    /**
     * compute relative positions for InnerRing
     **/
    private int[] innerRingDislocX = {-1, 0, 1, 1, 0, -1};
    private int[] innerRingDislocYEven = {0, 1, 0, -1, -1, -1};
    private int[] innerRingDislocYOdd = {1, 1, 1, 0, -1, 0};


    /**
     * Terrain-Strips Dislocation for the six different rotations
     **/
    private int[] Rot0DislocX = {0, -1, -2, -3, -4, -5, -5, -4, -3, -2, -1, -0, -1, -2, -3, -4};
    private int[] Rot0EvenDislocY = {0, -1, 0, 0, 1, 1, 2, 3, 2, 2, 1, 1, 0, 1, 1, 2};
    private int[] Rot0OddDislocY = {0, 0, 0, 1, 1, 2, 3, 3, 3, 2, 2, 1, 1, 1, 2, 2};

    private int[] Rot1DislocX = {0, -1, -1, -1, -1, -1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0};
    private int[] Rot1EvenDislocY = {0, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4};
    private int[] Rot1OddDislocY = {0, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4};

    private int[] Rot2DislocX = {0, 0, 1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 1, 2, 3, 4};
    private int[] Rot2EvenDislocY = {0, 1, 2, 2, 3, 3, 3, 2, 1, 1, 0, 0, 1, 1, 2, 2};
    private int[] Rot2OddDislocY = {0, 1, 1, 2, 2, 3, 2, 1, 1, 0, 0, -1, 0, 1, 1, 2};

    private int[] Rot3DislocX = {0, 1, 1, 1, 1, 1, 0, -1, -1, -1, -1, -1, 0, 0, 0, 0};
    private int[] Rot3EvenDislocY = {0, 0, -1, -2, -3, -4, -5, -4, -3, -2, -1, 0, -1, -2, -3, -4};
    private int[] Rot3OddDislocY = {0, -1, -2, -3, -4, -5, -5, -5, -4, -3, -2, -1, -1, -2, -3, -4};

    private int[] Rot4DislocX = {0, 1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4};
    private int[] Rot4EvenDislocY = {0, 0, 0, -1, -1, -2, -3, -3, -3, -2, -2, -1, -1, -1, -2, -2};
    private int[] Rot4OddDislocY = {0, 1, 0, 0, -1, -1, -2, -3, -2, -2, -1, -1, 0, -1, -1, -2};

    private int[] Rot5DislocX = {0, 0, -1, -2, -3, -4, -5, -5, -4, -3, -2, -1, -1, -2, -3, -4};
    private int[] Rot5EvenDislocY = {0, -1, -1, -2, -2, -3, -2, -1, -1, 0, 0, 1, 0, -1, -1, -2};
    private int[] Rot5OddDislocY = {0, -1, -2, -2, -3, -3, -3, -2, -1, -1, 0, 0, -1, -1, -2, -2};

    /**
     * Function used to assemble the strips into the matrix. We have 12 cases, 6  rotation-dependent each, even and odd
     * position of the "center Hexspace" (it's actually not in the center. it's the Hexspace which is at the first
     * position of the HexSpaceEntity list
     **/
    private void fillStripEntryInMatrix(HexSpaceEntity[][] boardMatrix, int posX, int posY, int[] disLocX,
                                        int[] disLocEvenY, int[] disLocOddY, int j, HexSpaceEntity hexSpaceEntity) {
        if (posX % 2 == 0) {
            boardMatrix[posX + disLocX[j]][posY + disLocEvenY[j]] = hexSpaceEntity;
        } else {
            boardMatrix[posX + disLocX[j]][posY + disLocOddY[j]] = hexSpaceEntity;
        }
    }

    /**
     * Converts the temporary Matrix of entities to matrix of HexSpaces
     * For the correct mapping of the blockade orienation (/,\ or _) mapping the orientation
     * fom the path definition over the blockade id.
     **/
    protected HexSpace[][] convertMatrix(HexSpaceEntity[][] entityMarix) {
        HexSpace[][] hexSpaceMatrix = new HexSpace[entityMarix.length][entityMarix[0].length];
        for (int i = 0; i < entityMarix.length; i++) {
            for (int j = 0; j < entityMarix[0].length; j++) {
                if (entityMarix[i][j] == null) {
                    //convert null entires to HexSpaces with EMPTY as entry
                    hexSpaceMatrix[i][j] = new HexSpace(i, j);
                } else {
                    HexSpaceEntity hexSpaceEntity = entityMarix[i][j];
                    if (hexSpaceEntity instanceof BlockadeSpaceEntity) {
                        // setting temporarely blockadeorientation to 0
                        hexSpaceMatrix[i][j] = new BlockadeSpace((BlockadeSpaceEntity) entityMarix[i][j], i, j, 0);
                    } else {
                        hexSpaceMatrix[i][j] = new HexSpace(entityMarix[i][j], i, j);
                    }
                }
            }
        }
        return hexSpaceMatrix;
    }

    /**
     * Assembles to board by calling different functions which fill in tiles, stripes blockades etc.
     * This it broken into different functions to be able to test all these functions separately.
     **/
    public HexSpace[][] assembleBoard(int boardId) {
        HexSpaceEntity[][] boardMatrix = this.createEmptyMatrix();
        BoardEntity board = boardService.getBoard(boardId);
        //System.out.println(board.toString());
        //System.out.println(board.getBlockadeId());
        boardMatrix = this.assembleBlockades(boardMatrix, board.getBlockadeId(), board.getBlockadePositionX(),
                board.getBlockadePositionY(),
                getRandomBlockades(this.getBlockadesCount()));
        boardMatrix = this.assembleTiles(boardMatrix, board.getTiles(), board.getTilesPositionX(),
                board.getTilesPositionY(), board.getTilesRotation());
        boardMatrix = this.assembleStrips(boardMatrix, board.getStrip(), board.getStripPositionX(),
                board.getStripPositionY(), board.getStripRotation());
        boardMatrix = this.assembleEndingSpaces(boardMatrix, board.getEndingSpaces(),
                board.getEndingSpacePositionX(),
                board.getEndingSpacePositionY());
        boardMatrix = this.assembleElDorado(boardMatrix, board.getEldoradoSpace(),
                board.getEldoradoSpacePositionX(),
                board.getEldoradoSpacePositionY());
        return convertMatrix(cropMatrix(boardMatrix));
    }

    /**
     * function to create an empty HexSpaceMatrix to start with
     * default Size can be edited here.
     *
     * @return empty 100x100 HexSpaceEntity-Matrix
     */
    protected HexSpaceEntity[][] createEmptyMatrix() {
        return new HexSpaceEntity[100][100];
    }


    /**
     * Function to resize the HexSpaceEntity-Matrix to a smaller one. reducing the size to a minimal size.
     * Still some access empty spaces, since the matrix is rectangular, the path is not. And there is a border
     * with width of one hexspace around the path to make sure all "real" hexspaces have six neighbours.
     **/
    protected HexSpaceEntity[][] cropMatrix(HexSpaceEntity[][] boardMatrix) {
        //Get Row-Domension
        int maxRow = boardMatrix.length;
        for (int row = boardMatrix.length - 1; row > 1; row--) {
            for (int col = boardMatrix[0].length - 1; col > 1; col--) {
                if (boardMatrix[row][col] != null) {
                    maxRow = row + 1; //one empty row/col around path
                    break;
                }
            }
            if (maxRow != boardMatrix.length) {
                break;
            }
        }
        //Get Column-Dimension
        int maxCol = boardMatrix[0].length;
        for (int col = boardMatrix[0].length - 1; col > 1; col--) {
            for (int row = maxRow - 1; row > 1; row--) {
                if (boardMatrix[row][col] != null) {
                    maxCol = col + 1; //one empty row/col around path
                    break;
                }
            }
            if (maxCol != boardMatrix[0].length) {
                break;
            }
        }
        //Copy data to new smaller matrix
        HexSpaceEntity[][] newBoardMatrix = new HexSpaceEntity[maxRow + 1][maxCol + 1]; //+1 since dimension does start at 1
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                newBoardMatrix[row][col] = boardMatrix[row][col];
            }
        }
        return newBoardMatrix;
    }

    /**
     * function that takes the matrix and enters the given tiles at the given position with the give rotation
     **/
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
                } else if (j < 30) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + midRingDislocX[j - 18]][TilePositionY.get(i) + midRingDislocYEven[j - 18]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 12));
                    } else {
                        boardMatrix[TilePositionX.get(i) + midRingDislocX[j - 18]][TilePositionY.get(i) + midRingDislocYOdd[j - 18]]
                                = currentTileHexSpaces.get(18 + (((j - 18) + (currentTileRotation * 2)) % 12));
                    }
                } else if (j < 36) {
                    if (TilePositionX.get(i) % 2 == 0) {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocX[j - 30]][TilePositionY.get(i) + innerRingDislocYEven[j - 30]]
                                = currentTileHexSpaces.get(30 + (((j - 30) + (currentTileRotation)) % 6));
                    } else {
                        boardMatrix[TilePositionX.get(i) + innerRingDislocX[j - 30]][TilePositionY.get(i) + innerRingDislocYOdd[j - 30]]
                                = currentTileHexSpaces.get(30 + (((j - 30) + (currentTileRotation)) % 6));
                    }
                } else {
                    boardMatrix[TilePositionX.get(i)][TilePositionY.get(i)]
                            = currentTileHexSpaces.get((j));
                }
            }
        }
        return boardMatrix;
    }

    /**
     * function that takes the matrix and enters the given stripes at the given position with the give rotation
     **/
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

    /**
     * function to count how many differen blockade types are stored in the DB
     * COMMENT: rather query this than hardcode for better extendability. Just add new blockade types
     *
     * @return int of how many blockadetypes are stored in the DB
     */
    protected int getBlockadesCount() {
        return blockadeSpaceService.getBlockadeCount();
    }

    /**
     * @param blockadesCount: number of different blockadetypes in the DB
     * @return a random ordered list of integers from 0 to BlockadesCount
     */
    protected List<Integer> getRandomBlockades(int blockadesCount) {
        List<Integer> blockadeIds = new ArrayList<>();
        for (int i = 1; i <= blockadesCount; i++) {
            blockadeIds.add(i);
        }
        Collections.shuffle(blockadeIds);
        return blockadeIds;
    }

    /**
     * Fills in all blockadeSpaces into the matrix. Chack which blockadeSpaces belon together and fill the with the same
     * Blockade parameters.
     *
     * @param boardMatrix:       the current boardMatrix of HexSpaceEntities the blockades should be filled in
     * @param blockadeId:        List of all blockade ids stored in the DB for the path, used to match which blockades spaces
     *                           belong together
     * @param blockadePositionX: list of X positions of the blockadeSpaces
     * @param blockadePositionY: list of Y positions of the blockadeSpaces
     * @param randomBlockadeIds: rondom ordered blockadeIds, used to assign blockades from the DB randomly.
     * @return: boradMatrix with filled in BlockadeSpaceEntities.
     */
    private HexSpaceEntity[][] assembleBlockades(HexSpaceEntity[][] boardMatrix, List<Integer> blockadeId,
                                                 List<Integer> blockadePositionX, List<Integer> blockadePositionY,
                                                 List<Integer> randomBlockadeIds) {
        System.out.println("Bordo blockados assembloror failos notos");
        for (int i = 0; i < blockadeId.size(); i++) {
            int j = blockadeId.get(i) % this.getBlockadesCount();
            //j defines which position of the random ordered blockade directory
            // use % to make sure that we do not encounter a problem if the board has more blockades than in our DB
            // in ths case we just use the same blockades multiple times
            boardMatrix[blockadePositionX.get(i)][blockadePositionY.get(i)] =
                    blockadeSpaceService.getBlockadeSpaceEntity(randomBlockadeIds.get(j));
        }
        return boardMatrix;
    }

    /**
     * @param boardMatrix:           the current boardMatrix of EndingSpaces the blockades should be filled in
     * @param endingSpaces:          HexSpaceEntites for the endingspaces, either with River or with Jungle
     * @param endingSpacesPositionX: list of X positions of the endingSpaces
     * @param endingSpacesPositionY: list of Y positions of the endingSpaces
     * @return: boradMatrix with filled in EndingSpaces as HesSpaceEntities.
     */
    public HexSpaceEntity[][] assembleEndingSpaces(HexSpaceEntity[][] boardMatrix, List<HexSpaceEntity> endingSpaces,
                                                   List<Integer> endingSpacesPositionX, List<Integer> endingSpacesPositionY) {
        for (int i = 0; i < endingSpaces.size(); i++) {
            boardMatrix[endingSpacesPositionX.get(i)][endingSpacesPositionY.get(i)] = endingSpaces.get(i);
        }
        return boardMatrix;
    }

    public HexSpaceEntity[][] assembleElDorado(HexSpaceEntity[][] boardMatrix, List<HexSpaceEntity> eldoradoSpaces,
                                               List<Integer> eldoradoPositionX, List<Integer> eldoradoPositionY) {
        for (int i = 0; i < eldoradoSpaces.size(); i++) {
            boardMatrix[eldoradoPositionX.get(i)][eldoradoPositionY.get(i)] = eldoradoSpaces.get(i);
        }
        return boardMatrix;
    }

    /*
    The assembleBoard creates a Matrix consisting of all the elements from the GameEntity
    with ID = boardNumber and returns the matrix with the well prepared GameEntity. The assembler
    starts with a matrix consisting of only HexSpaces with infinite cost and colour EMPTY
    and replace them with the right HexSpaces according to the values he reads out of the Database.
    */

    /**
     * Used by the GameBoard and returns an ArrayList of Arrays with the coordinates of the blockades
     * in the pathMatrix. This is needed so that the GameEntity can assign blockade instances to them.
     * We consider this more efficient than parsing the pathMatrix, since the assembler has
     * the information abouts these positions already.
     **/
    public List<Blockade> getBlockades(Game game) {
        BoardEntity board = boardService.getBoard(game.getBoardId());
        Matrix hexSpaceMatrix = game.getPathMatrix();
        List<Integer> blockadeX = board.getBlockadePositionX(); //all x Positions of the blockadeSpaces
        List<Integer> blockadeY = board.getBlockadePositionY(); //all y Positions of the blockadeSpaces
        List<Integer> blockadeIds = board.getBlockadeId(); //all blockade ids to match with the positions to groups for each blockade
        List<Integer> orientations = board.getBlockadeOrientation();
        List<Blockade> allBlockades = new ArrayList<>();
        List<Integer> alreadyDone = new ArrayList<>(); //stores which blockade id has been put together already
        for (int i = 0; i < blockadeIds.size(); i++) {
            System.out.println("Assemblos getthos Blockadododos");
            if (!alreadyDone.contains(blockadeIds.get(i))) { //was this id searched and done already?
                alreadyDone.add(blockadeIds.get(i)); //adds the id to the ones which are doe already
                List<BlockadeSpace> blockadeSpace = new ArrayList<>(); //create new list of Hexspaces which stores all spaces belonging to same blockade
                for (int j = 0; j < blockadeIds.size(); j++) { //loop over all ids again
                    if (blockadeIds.get(j).equals(blockadeIds.get(i))) {
                        BlockadeSpace tempBlockadeSpace = (BlockadeSpace) hexSpaceMatrix.get(blockadeX.get(j), blockadeY.get(j));
                        //assign the blockade orientation
                        tempBlockadeSpace.specifyOrientation(orientations.get(blockadeIds.get(j)));
                        ((BlockadeSpace) hexSpaceMatrix.get(blockadeX.get(j), blockadeY.get(j))).specifyOrientation(orientations.get(blockadeIds.get(j)));
                        blockadeSpace.add((BlockadeSpace) hexSpaceMatrix.get(blockadeX.get(j), blockadeY.get(j)));//if they match the first one store the according Spaces in the list

                    }
                }
                allBlockades.add(new Blockade(blockadeSpace));
            }
        }
        System.out.println("gethhos blockados inguiltos");
        return allBlockades;
    }

    /**
     * Used by the GameBorad and returns an Arrays with the HexSpaces of the starting-fields.
     * The GameEntity needs these information to place the playing Pieces. We rather request these
     * informations from the assembler than parsing the matrix.
     **/
    public List<HexSpace> getStartingFields(int boardId) {
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
                for (int j = 9; j >= 6; j--) {
                    System.out.println("startos fieldos J counteros" + j);
                    if (posX % 2 == 0) {
                        StartingSpaces.add(new HexSpace(containedTiles.get(i).getHexSpaceEntities().get(j),
                                posX + outerRingDislocX[(j - (3 * rotation) + 18) % 18],
                                posY + outerRingDislocYEven[(j - (3 * rotation) + 18) % 18]));
                    } else {
                        StartingSpaces.add(new HexSpace(containedTiles.get(i).getHexSpaceEntities().get(j),
                                posX + outerRingDislocX[(j - 3 * rotation + 18) % 18],
                                posY + outerRingDislocYOdd[(j - 3 * rotation + 18) % 18]));
                    }
                }
            }
        }
        return StartingSpaces;
    }

    /**
     * Used by the GameBorad and returns an Arrays with the HexSpaces of the ending-fields.
     * The GameEntity needs these information to place the playing Pieces. We rather request these
     * informations from the Assembler than parsing the matrix.
     **/
    public List<HexSpace> getEndingFields(int boardId) {
        BoardEntity board = boardService.getBoard(boardId);
        List<HexSpace> EndingSpaces = new ArrayList<>();
        for (int i = 0; i < board.getEndingSpaces().size(); i++)
            EndingSpaces.add(new HexSpace(board.getEndingSpaces().get(i), board.getEndingSpacePositionX().get(i),
                    board.getEndingSpacePositionX().get(i)));
        return EndingSpaces;
    }

    /**
     * Used by the GameBorad and returns an Arrays with the HexSpaces of the ending-fields.
     * The GameEntity needs these information to place the playing Pieces. We rather request these
     * informations from the Assembler than parsing the matrix.
     **/
    public List<HexSpace> getElDoradoFields(int boardId) {
        BoardEntity board = boardService.getBoard(boardId);
        List<HexSpace> ElDoradoSpaces = new ArrayList<>();
        for (int i = 0; i < board.getEldoradoSpace().size(); i++)
            ElDoradoSpaces.add(new HexSpace(board.getEldoradoSpace().get(i), board.getEldoradoSpacePositionX().get(i),
                    board.getEldoradoSpacePositionY().get(i)));
        return ElDoradoSpaces;
    }

    public BoardEntity getBoard(int boardId) {
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
