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

    @Column(name = "COLOR")
    @Enumerated(EnumType.STRING)
    private COLOR color;

    @Column(name = "STRENGTH")
    private int strength;

    public HexSpaceEntity(COLOR color, int strength){
        this.color = color;
        this.strength = strength;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public COLOR getCOLOR() {
        return color;
    }

    public void setCOLOR(COLOR color) {
        this.color = color;
    }

    public int getStrength() { return  strength; }

    public void setStrength(int strength) { this.strength = strength; }
}
