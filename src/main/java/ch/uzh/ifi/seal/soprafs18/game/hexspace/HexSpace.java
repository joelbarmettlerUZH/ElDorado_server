package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
//import com.sun.xml.internal.bind.v2.TODO;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;
import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.sun.xml.internal.bind.v2.TODO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
//import java.awt.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance
public class HexSpace implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hexSpaceId;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore
    protected List<HexSpace> previous;

    /**
     Function to calculata all six neighbors of a hexspace without any postprocessing
     */
    //@Transient
    @JsonIgnore
    public List<HexSpace> getNeighbour(Game game){
        List<HexSpace> neighbours = new ArrayList<>();
        //int x = this.point.x;
        neighbours.add(game.getHexSpace(new Point(this.point.x+1,this.point.y)));
        neighbours.add(game.getHexSpace(new Point(this.point.x-1,this.point.y)));
        neighbours.add(game.getHexSpace(new Point(this.point.x,this.point.y+1)));
        neighbours.add(game.getHexSpace(new Point(this.point.x,this.point.y-1)));
        if(this.point.x%2==0){
            //even Column
            neighbours.add(game.getHexSpace(new Point(this.point.x-1,this.point.y-1)));
            neighbours.add(game.getHexSpace(new Point(this.point.x+1,this.point.y-1)));
        } else {
            //odd Column
            neighbours.add(game.getHexSpace(new Point(this.point.x-1,this.point.y+1)));
            neighbours.add(game.getHexSpace(new Point(this.point.x+1,this.point.y+1)));
        }
        //System.out.println("neighbours of "+this.point.x+"/"+this.point.y+" = " + neighbours);

        List<HexSpace> occupied = removeOccupied(game, neighbours);
        neighbours.removeAll(occupied);
        return neighbours;
    }

    protected List<HexSpace> removeOccupied(Game game, List<HexSpace> neighbours) {
        List<HexSpace> occupied = new ArrayList<>();
        for(HexSpace neighbor: neighbours){
            for(Player player: game.getPlayers()){
                for(PlayingPiece playingPiece: player.getPlayingPieces()){
                    System.out.println(playingPiece.getStandsOn().toString());
                    if(playingPiece.getStandsOn() == neighbor){
                        System.out.println("remove: "+ neighbor.toString());
                        occupied.add(neighbor);
                    }
                }
            }
        }
        return occupied;
    }

    /*
    Method that takes the previous HexSpaceEntity, the user came from, into consideration when calculating the neighbours.
    The Methods calculates the coordinates of the neighbouring fields by taking the GameEntity’s pathMatrix into account.
    From all found neighbours, the method looks whether BlockadeSpaces are among them. If this is the case and the blockade
    Spaces belong to the same active blockade, only one of the blockadeSpaces is returned. If the blockadeSpaces are inactive,
    the method asks it again for its neighbours by calling blockadeSpace.getNeighbours(this) and provides itself as the
    previous. This way the blockade can handle the neighbours with taking the previous direction into account.
     */


    @Override
    public String toString(){
        return color.toString()+"-Space at Point ("+point.x+","+point.y+"), Strength: "+strength+", minimalCost: "+
                minimalCost+", minimalDepth: "+ minimalDepth+", previoussize: " +previous.size();
    }

    public int getHexSpaceId() {
        return hexSpaceId;
    }

    public void setHexSpaceId(int hexSpaceId) {
        this.hexSpaceId = hexSpaceId;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getMinimalCost() {
        return minimalCost;
    }

    public void setMinimalCost(int minimalCost) {
        this.minimalCost = minimalCost;
    }

    public int getMinimalDepth() {
        return minimalDepth;
    }

    public void setMinimalDepth(int minimalDepth) {
        this.minimalDepth = minimalDepth;
    }

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public List<HexSpace> getPrevious() {
        return previous;
    }

    public void setPrevious(List<HexSpace> previous) {
        this.previous = previous;
    }
}
