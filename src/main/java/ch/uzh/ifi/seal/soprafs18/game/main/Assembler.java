package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.Board;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.Strip;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.Tile;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import jdk.nashorn.internal.ir.Block;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Assembler {
    /*
    Instance of Database containing all the Game
     */
    @Autowired
    private Board board;
    private Strip strip;
    private Tile tile;

    /*
    The assembleBoard creates a Matrix consisting of all the elements from the Game
    with ID = boardNumber and returns the matrix with the well prepared Game. The assembler
    starts with a matrix consisting of only HexSpaces with infinite cost and colour EMPTY
    and replace them with the right HexSpaces according to the values he reads out of the Database.
     */
    public HexSpace[][] assembleBoard(int boardID){
        return null;
    }

    /*
    Used by the GameBorad and returns an ArrayList of Arrays with the coordinates of the blockades
    in the pathMatrix. This is needed so that the Game can assign blockade instances to them.
    We consider this more efficient than parsing the pathMatrix, since the assembler has
    the information abouts these positions already.
     */
    public List<Block> getBlockades(int boardID){
        return null;
    }

    /*
    Used by the GameBorad and returns an Arrays with the HexSpaces of the starting-fields.
    The Game needs these information to place the playing Pieces. We rather request these
    informations from the assembler than parsing the matrix.
     */
    public List<HexSpace> getStartingFields(int boardID){
        return null;
    }

    /*
    Used by the GameBorad and returns an Arrays with the HexSpaces of the ending-fields.
    The Game needs these information to place the playing Pieces. We rather request these
    informations from the Assembler than parsing the matrix.
     */
    public List<HexSpace> getEndingFields(int boardID){
        return null;
    }
}
