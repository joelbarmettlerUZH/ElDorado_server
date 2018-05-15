package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.Block;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BLOCKADE")
public class Blockade  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hibernateBlockadeId;

    private int blockadeId;

    public Blockade(List<BlockadeSpace> blockadeSpaces){
        System.out.println("Constructorioalo si callodos magnificos "+blockadeSpaces.size());
        this.spaces = blockadeSpaces;
        this.cost = blockadeSpaces.get(0).getStrength();
        this.blockadeId = blockadeSpaces.get(0).getParentBlockade();
    }


    private Blockade(){
    }

    /*
    List of BlockadeSpaces/HexSpaces the Blockade consists of.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
        this.spaces.forEach(space -> space.setStrength(0));
    }

    public int getHibernateBlockadeId() {
        return hibernateBlockadeId;
    }

    public void setHibernateBlockadeId(int hibernateBlockadeId) {
        this.hibernateBlockadeId = hibernateBlockadeId;
    }

    public int getBlockadeId() {
        return blockadeId;
    }

    public void setBlockadeId(int blockadeId) {
        this.blockadeId = blockadeId;
    }

    public List<BlockadeSpace> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<BlockadeSpace> spaces) {
        this.spaces = spaces;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
