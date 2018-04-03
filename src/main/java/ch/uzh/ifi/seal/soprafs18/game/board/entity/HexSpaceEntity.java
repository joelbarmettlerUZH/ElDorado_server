package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;

import javax.persistence.*;

@Entity
@Table(name = "HEXSPACE")
public class HexSpaceEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @Column(name = "HEXSPACE")
    private HexSpace hexSpace;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HexSpace getHexSpace() {
        return hexSpace;
    }

    public void setHexSpaceEntity(HexSpace hexSpace) {
        this.hexSpace = hexSpace;
    }
}
