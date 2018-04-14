package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
//import com.sun.xml.internal.bind.v2.TODO;
import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.sun.xml.internal.bind.v2.TODO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
//import java.awt.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@MappedSuperclass
@Data
public class HexSpace implements Serializable{

    /*
    CONSTRUCTOR
     */
    public HexSpace(HexSpaceEntity hexSpaceEntity, int posX, int posY){
        this.color = COLOR.valueOf(hexSpaceEntity.getColor());
        this.strength = hexSpaceEntity.getStrength();
        this.minimalCost = 1000;
        this.minimalDepth = 0;
        this.previous = new ArrayList<>();
        this.point = new Point(posX,posY);
    }


    public HexSpace(){
        this(COLOR.EMPTY, 10, 100, 1000, new Point(-1, -2));
    }


    public HexSpace(COLOR color, int strength, int minimalCost, int minimalDepth, Point point){
        this.color = color;
        this.strength = strength;
        this.minimalCost = minimalCost;
        this.minimalDepth = minimalDepth;
        this.previous = new ArrayList<>();
        this.point = point;
    }

    // for creating empty spaces instead of null
    public HexSpace(int x,int y){
        this.color = COLOR.EMPTY;
        this.strength = 1000;
        this.minimalCost = 1000;
        this.minimalDepth = 0;
        this.previous = new ArrayList<>();
        this.point = new Point(x,y);
    }

    /*
    The strength of a field indicates how high the card-value has to be to make it accessible. The strength of
    non-playing fields such as Mountains, Empty-Fields is set to 1000, all the other field strength correspond to
    the card strength needed to enter the field.
     */
    protected int strength;

    /*
    Costfactor assigned by the Pathfinding algorithm indicating how expensive it is to reach this field. This cost
    factor should by default be 1000 and be overwritten by the pathfinding whenever a way to the HexSpaceEntity is found.
    The value is reset to 1000 once the path algorithm finished.
     */
    protected int minimalCost;

    /*
    Keeps track of how many moving-steps were needed by the player to move to this current field with the minimal costs.
     */
    protected int minimalDepth;

    /*
    Enum of all possible colours a HexSpaceEntity can have. Each HexSpaceEntity has exactly one colour.
     */
    @Enumerated
    protected COLOR color;

    /*
    The X/Y coordinates of the HexSpaceEntity in the GameEntity Matrix.
     */
    protected Point point;


    /*
    The way our pathfinding-algorithm found to access this HexSpaceEntity. The field is usually an empty ArrayList and set by
    the pathfinding-algorithm. The length of the HexSpaces array corresponds to the depth / amount of steps it took the
    pathfinding-algorithm to reach the HexSpaceEntity. BlockadeSpaces are included in the Previous history.
     */
    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "hexid")
    @Column(name="PREVIOUS")*/

    protected ArrayList<HexSpace> previous;

    /**
     Function to calculata all six neighbors of a hexspace without any postprocessing
     */
    @Transient
    @JsonIgnore
    protected List<HexSpace> getAllNeighbour(Game game){
        List<HexSpace> neighbours = new ArrayList<>();
        int x = this.point.x;
        neighbours.add(game.getHexSpace(new Point(this.point.x+1,this.point.y)));
        neighbours.add(game.getHexSpace(new Point(this.point.x-1,this.point.y)));
        neighbours.add(game.getHexSpace(new Point(this.point.x,this.point.y+1)));
        neighbours.add(game.getHexSpace(new Point(this.point.x,this.point.y-1)));
        if(this.point.x%2==0){
            //even Column
            neighbours.add(game.getHexSpace(new Point(this.point.x+1,this.point.y+1)));
            neighbours.add(game.getHexSpace(new Point(this.point.x+1,this.point.y-1)));
        } else {
            //odd Column
            neighbours.add(game.getHexSpace(new Point(this.point.x-1,this.point.y+1)));
            neighbours.add(game.getHexSpace(new Point(this.point.x-1,this.point.y-1)));
        }
        return neighbours;
    }

    /*
    Method that takes the previous HexSpaceEntity, the user came from, into consideration when calculating the neighbours.
    The Methods calculates the coordinates of the neighbouring fields by taking the GameEntity’s pathMatrix into account.
    From all found neighbours, the method looks whether BlockadeSpaces are among them. If this is the case and the blockade
    Spaces belong to the same active blockade, only one of the blockadeSpaces is returned. If the blockadeSpaces are inactive,
    the method asks it again for its neighbours by calling blockadeSpace.getNeighbours(this) and provides itself as the
    previous. This way the blockade can handle the neighbours with taking the previous direction into account.
     */
    @JsonIgnore
    @Transient
    public List<HexSpace> getNeighbour(Game game){
        List<HexSpace> neighbours = getAllNeighbour(game);
        //now handle blockades
        System.out.println(neighbours.iterator().next().getClass());
        for (HexSpace current:neighbours){
            System.out.println(current.getClass().getCanonicalName());
            if (current.getClass()==BlockadeSpace.class){
                System.out.println(current.getClass().getCanonicalName());
                //current is BlockadeSpace
                BlockadeSpace currentBlockadeSpace = (BlockadeSpace) current;
                int blockade = currentBlockadeSpace.getBlockadeId();  //not used yet (Why do we need to only keep one blockade in the neighbors? - makes it complicated)
                if (currentBlockadeSpace.getStrength()==0){
                    //blockade is inactive
                    neighbours.addAll(currentBlockadeSpace.getNeighbour(game));
                }

            }

        }
        return neighbours;
    }

}
