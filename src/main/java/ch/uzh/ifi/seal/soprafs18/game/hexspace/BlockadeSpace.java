package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import java.awt.*;
import java.io.Serializable;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;
@Embeddable
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
        List<HexSpace> neighbours = getAllNeighbour();
        //now handle blockades
        for (int i = 0; i<neighbours.size();i++) {
            if (HexSpace.class.isAssignableFrom(neighbours.get(i).getClass())) {
                //current is BlockadeSpace, thus remove it.
                neighbours.remove(i);
            }
        }
        return neighbours;
    }

    @Override
    public List<HexSpace> getNeighbour(HexSpace previous){
        return this.getNeighbour();
    }

    public int getBlockadeId() {
        return blockadeId;
    }

    public void setBlockadeId(int blockadeId) {
        this.blockadeId = blockadeId;
    }
}
