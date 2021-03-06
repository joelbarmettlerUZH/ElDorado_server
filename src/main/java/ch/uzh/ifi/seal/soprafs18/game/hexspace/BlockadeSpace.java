package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.awt.*;
import java.awt.*;
import java.io.Serializable;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BlockadeSpace extends HexSpace implements Serializable {
    /*
    CONSTRUCTOR
    */
    public BlockadeSpace(BlockadeSpaceEntity blockadeSpaceEntity, int posX, int posY, int blockadeOrientation){
        super(blockadeSpaceEntity,posX,posY);
        this.parentBlockade = blockadeSpaceEntity.getBlockadeID();
        this.orientation = blockadeOrientation;
        //this.parentBlockade = -1;
    }

    public BlockadeSpace(BlockadeSpaceEntity blockadeSpaceEntity, int posX, int posY, int parentBlockade, int blockadeOrientation){
        super(blockadeSpaceEntity,posX,posY);
        this.parentBlockade = parentBlockade;
        this.orientation = blockadeOrientation;
        //this.parentBlockade = -1;
    }
    /*
    public BlockadeSpace(COLOR color, int strength, int minimalCost, int minimalDepth, Point point, int parentBlockade){
        super(color, strength, minimalCost, minimalDepth, point);
        this.parentBlockade = parentBlockade;
        //this.parentBlockade = -1;
    }
    */


    public BlockadeSpace(){
        super();
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

    private int orientation;

    @Override
    public List<HexSpace> getNeighbour(Game game){

        List<HexSpace> neighbours = new ArrayList<>();
        // if the tiles are below another
        if (game.getHexSpace(new Point(this.point.x,this.point.y - 1)).getClass() == BlockadeSpace.class ||
                game.getHexSpace(new Point(this.point.x,this.point.y + 1)).getClass() == BlockadeSpace.class) {
            if (this.point.x % 2 == 0) {
                System.out.println("*********************************HORIZONTAL-BLOCKADE*************************************");
                //even x
                neighbours.add(game.getHexSpace(new Point(this.point.x - 1, this.point.y - 1)));
                neighbours.add(game.getHexSpace(new Point(this.point.x + 1, this.point.y)));
            } else {
                System.out.println("*********************************HORIZONTAL-BLOCKADE*************************************");
                //odd x
                neighbours.add(game.getHexSpace(new Point(this.point.x - 1, this.point.y)));
                neighbours.add(game.getHexSpace(new Point(this.point.x + 1, this.point.y + 1)));
            }
        } else {
            // tiles are diagonally arranged
            neighbours.add(game.getHexSpace(new Point(this.point.x, this.point.y - 1)));
            neighbours.add(game.getHexSpace(new Point(this.point.x, this.point.y + 1)));
        }
        List<HexSpace> occupied = removeOccupied(game, neighbours);
        neighbours.removeAll(occupied);
        return neighbours;
    }

    @Override
    public String toString(){
        return color.toString()+"-BlockSpace at Point ("+point.x+","+point.y+"), Strength: "+strength+", minimalCost: "+
                minimalCost+", minimalDepth: "+ minimalDepth+", previoussize: " +previous.size();
    }

    public int getParentBlockade() {
        return parentBlockade;
    }

    public void setParentBlockade(int parentBlockade) {
        this.parentBlockade = parentBlockade;
    }

    public int getOrientation() {
        return orientation;
    }

    public void specifyOrientation(int orientation) {
        this.orientation = orientation;
    }
}
