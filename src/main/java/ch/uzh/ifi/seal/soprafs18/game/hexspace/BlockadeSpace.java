package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import java.io.Serializable;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;

import java.util.List;

public class BlockadeSpace extends HexSpace implements Serializable {
    /*
    CONSTRUCTOR
    */
    public BlockadeSpace(BlockadeSpaceEntity blockadeSpaceEntity, int posX, int posY, Game game){
        super(blockadeSpaceEntity,posX,posY,game);
        this.blockadeId = blockadeSpaceEntity.getBlockadeId();
    }

    /*
    stores to which blockade it belongs to
     */
    private int blockadeId;

    /*
    This method needs to be overwritten in order to determine the neighbours of a special HexSpaceEntity of type BlockadeSpace,
    since other BlockadeSpaces shall not count as its neighbours. The overwritten method considers where the player was
    coming from and therefore computes the neighbours ignoring inactive barricades.
     */

    @Override
    public List<HexSpace> getNeighbour(){
        return null;
    }
}
