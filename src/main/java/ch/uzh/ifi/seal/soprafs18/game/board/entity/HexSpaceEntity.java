package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HEXSPACE")
public class HexSpaceEntity {
    @Id
    @Column(name = "COLOR")
    private COLOR color;

    @Column(name = "Hexspace")
    private HexSpace hexSpace;

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public HexSpace getHexSpace() {
        return hexSpace;
    }

    public void setHexSpaceEntity(HexSpace hexSpace) {
        this.hexSpace = hexSpace;
    }
}
