package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "HEXSPACE_ENTITY")
@Inheritance (strategy = InheritanceType.JOINED)
@Embeddable
public class HexSpaceEntity implements Serializable {

    public HexSpaceEntity(){

    }

    public HexSpaceEntity(String id, String color, int strength){
        //System.out.println("constr");
        this.hexID = id;
        this.color = color;
        this.strength = strength;
    }

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "hexSpaceEntities")
    @Id
    @Column(name = "ID",columnDefinition="VARCHAR(4)")
    private String hexID;

    @Column(name = "COLOR")
    //@Enumerated(EnumType.STRING)
    private String color;

    @Column(name = "STRENGTH")
    private int strength;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "tileID")

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
