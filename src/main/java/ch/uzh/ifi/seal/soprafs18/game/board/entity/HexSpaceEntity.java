package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;

import javax.persistence.*;

@Entity
@Table(name = "HEXSPACE")
public class HexSpaceEntity {
    @Id
    @Column(name = "ID", unique = true)
    private String id;

    @Column(name = "COLOR")
    @Enumerated(EnumType.STRING)
    private COLOR color;

    @Column(name = "STRENGTH")
    private int strength;

    public HexSpaceEntity(COLOR color, int strength){
        this.color = color;
        this.strength = strength;
    }

    public HexSpaceEntity(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public int getStrength() { return  strength; }

    public void setStrength(int strength) { this.strength = strength; }
}