package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
//import com.sun.xml.internal.bind.v2.TODO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
//import java.awt.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@MappedSuperclass
@Data
public class HexSpace implements Serializable{

    /*
    CONSTRUCTOR
     */
    public HexSpace(HexSpaceEntity hexSpaceEntity, int posX, int posY, Game game){
        this.color = COLOR.valueOf(hexSpaceEntity.getColor());
        this.strength = hexSpaceEntity.getStrength();
        this.minimalCost = 1000;
        this.minimalDepth = 0;
        this.previous = new ArrayList<>();
        this.point = new Point(posX,posY);
        this.game = game;
    }

    public HexSpace(){
        this(COLOR.EMPTY, 10, 100, 1000, new Point(-1, -2), null);
    }

    public HexSpace(COLOR color, int strength, int minimalCost, int minimalDepth, Point point, Game game){
        this.color = color;
        this.strength = strength;
        this.minimalCost = minimalCost;
        this.minimalDepth = minimalDepth;
        this.previous = new ArrayList<>();
        this.point = point;
        this.game = game;
    }

    /*
    The strength of a field indicates how high the card-value has to be to make it accessible. The strength of
    non-playing fields such as Mountains, Empty-Fields is set to 1000, all the other field strength correspond to
    the card strength needed to enter the field.
     */
    private int strength;

    /*
    Costfactor assigned by the Pathfinding algorithm indicating how expensive it is to reach this field. This cost
    factor should by default be 1000 and be overwritten by the pathfinding whenever a way to the HexSpaceEntity is found.
    The value is reset to 1000 once the path algorithm finished.
     */
    private int minimalCost;

    /*
    Keeps track of how many moving-steps were needed by the player to move to this current field with the minimal costs.
     */
    private int minimalDepth;

    /*
    Enum of all possible colours a HexSpaceEntity can have. Each HexSpaceEntity has exactly one colour.
     */
    @Enumerated
    private COLOR color;

    /*
    The X/Y coordinates of the HexSpaceEntity in the GameEntity Matrix.
     */
    private Point point;

    /*
    The way our pathfinding-algorithm found to access this HexSpaceEntity. The field is usually an empty ArrayList and set by
    the pathfinding-algorithm. The length of the HexSpaces array corresponds to the depth / amount of steps it took the
    pathfinding-algorithm to reach the HexSpaceEntity. BlockadeSpaces are included in the Previous history.
     */
    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "hexid")
    @Column(name="PREVIOUS")*/
    @Transient
    @JsonIgnore
    private ArrayList<HexSpace> previous;

    /*
    HexSpaceEntity need to know to which GameEntity it belongs. Primarily used for the PathFinder.
     */
    @Transient
    @JsonIgnore
    protected Game game;

    /*
    Method that takes the previous HexSpaceEntity, the user came from, into consideration when calculating the neighbours.
    The Methods calculates the coordinates of the neighbouring fields by taking the GameEntity’s pathMatrix into account.
    From all found neighbours, the method looks whether BlockadeSpaces are among them. If this is the case and the blockade
    Spaces belong to the same active blockade, only one of the blockadeSpaces is returned. If the blockadeSpaces are inactive,
    the method asks it again for its neighbours by calling blockadeSpace.getNeighbours(this) and provides itself as the
    previous. This way the blockade can handle the neighbours with taking the previous direction into account.
     */
    @Transient
    public List<HexSpace> getNeighbour(){
        return null;
    }
    @Transient
    public List<HexSpace> getNeighbour(HexSpace previous){
        return null;
    }

    //TODO: DLEETETLERLE AGAIN
    public void printGamePls(){
        //System.out.println("***************"+this.game.getGameId());
    }
}
