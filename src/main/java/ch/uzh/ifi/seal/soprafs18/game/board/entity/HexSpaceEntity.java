package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "HEXSPACE")
@Inheritance (strategy = InheritanceType.JOINED)
public class HexSpaceEntity {

    public HexSpaceEntity(String id, String color, int strength){
        //System.out.println("constr");
        this.hexID = id;
        this.color = color;
        this.strength = strength;
    }

    @Id
    @Column(name = "ID", unique = true)
    private String hexID;

    @Column(name = "COLOR")
    //@Enumerated(EnumType.STRING)
    private String color;

    @Column(name = "STRENGTH")
    private int strength;

    /*
    public HexSpaceEntity(COLOR color, int strength){
        this.color = color;
        this.strength = strength;
    }
    */

    public HexSpaceEntity(){

    }

    public String getId() {
        return hexID;
    }

    public void setId(String id) {
        this.hexID = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStrength() { return  strength; }

    public void setStrength(int strength) { this.strength = strength; }
}
