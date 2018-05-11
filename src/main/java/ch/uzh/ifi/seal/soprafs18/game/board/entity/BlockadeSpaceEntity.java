package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "BLOCKADE_ENTITY")
@Embeddable
public class BlockadeSpaceEntity extends HexSpaceEntity {

    public BlockadeSpaceEntity(String id, String color, int strength, int blockadeId){
        super(id,color,strength);
        this.blockadeID = blockadeId;
    }

    public BlockadeSpaceEntity(){
        
    }

    @Column(name="BLOCKADEID")
    private int blockadeID;
}
