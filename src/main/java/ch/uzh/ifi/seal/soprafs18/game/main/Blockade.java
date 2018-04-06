package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;

import javax.persistence.*;
import java.io.Serializable;
import java.io.Serializable;
import java.util.List;


public class Blockade  implements Serializable {

    public Blockade(List<BlockadeSpace> blockadeSpaces){
        this.spaces = blockadeSpaces;
        this.cost = blockadeSpaces.get(0).getStrength();
    }
    /*
    List of BlockadeSpaces the Blockade consists of.
     */
    private List<BlockadeSpace> spaces;

    /*
    Cost to remove this blockade. The cost is the same for all
    BlockadSpaces in the same Blockade. The same cost is also set as
    “strength” in the BlockadeSpace. When a barricade is deactivated,
    the BlockadeSpace strength is set to 0, but not the Blockades cost since the
    Blockade gets assigned to the Player that removed it and its cost factor
    has to be remembered for the endgame.
     */
    private int cost;

    /*
    Deactivates a blockade by settings its BlockadeSpace strength to 0.
     */
    public void deactivate(){
        this.cost = 0;
    }

    /*
    Assings a new BlockadeSpace to the Blockade by appending it to the spaces array.
    NOT NEEDED I THINK (MARIUS)

    public void assign(Blockade blockade){

    }
    */
    public List<BlockadeSpace> getSpaces() {
        return spaces;
    }

    public int getCost() {
        return cost;
    }
}
