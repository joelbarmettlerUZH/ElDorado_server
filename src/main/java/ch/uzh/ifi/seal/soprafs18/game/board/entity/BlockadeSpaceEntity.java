package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BLOCKADE")
public class BlockadeSpaceEntity extends HexSpaceEntity {

    public BlockadeSpaceEntity(String id, String color, int strength, int blockadeId){
        super(id,color,strength);
        this.blockadeId = blockadeId;
    }


    @Column(name="BLOCKADEID")
    private int blockadeId;

    public int getBlockadeId() {
        return blockadeId;
    }

    public void setBlockadeId(int id) {
        this.blockadeId = blockadeId;
    }
}
