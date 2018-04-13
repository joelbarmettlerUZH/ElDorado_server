package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.awt.*;
import java.awt.*;
import java.io.Serializable;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import lombok.Data;

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
        this.parentBlockade = blockadeSpaceEntity.getBlockadeId();
    }

    public BlockadeSpace(COLOR color, int strength, int minimalCost, int minimalDepth, Point point, Game game, int parentBlockade){
        super(color, strength, minimalCost, minimalDepth, point, game);
        this.parentBlockade = parentBlockade;
    }

    public BlockadeSpace(){
    }

    /*
    stores to which blockade it belongs to
     */
    private int parentBlockade;

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
        return parentBlockade;
    }

    public void setBlockadeId(int blockadeId) {
        this.parentBlockade = blockadeId;
    }

    public int getParentBlockade(){
        return this.parentBlockade;
    }

    public void setParentBlockade(int parentBlockade){
        this.parentBlockade = parentBlockade;
    }
}
