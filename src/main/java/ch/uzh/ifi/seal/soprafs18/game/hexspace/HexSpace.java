package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import ch.uzh.ifi.seal.soprafs18.game.main.Game;

import java.awt.*;
import java.util.List;

public class HexSpace {
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
    private List<HexSpace> previous;

    /*
    HexSpaceEntity need to know to which GameEntity it belongs. Primarily used for the PathFinder.
     */
    protected Game game;

    /*
    Method that takes the previous HexSpaceEntity, the user came from, into consideration when calculating the neighbours.
    The Methods calculates the coordinates of the neighbouring fields by taking the GameEntity’s pathMatrix into account.
    From all found neighbours, the method looks whether BlockadeSpaces are among them. If this is the case and the blockade
    Spaces belong to the same active blockade, only one of the blockadeSpaces is returned. If the blockadeSpaces are inactive,
    the method asks it again for its neighbours by calling blockadeSpace.getNeighbours(this) and provides itself as the
    previous. This way the blockade can handle the neighbours with taking the previous direction into account.
     */
    public List<HexSpace> getNeighbour(){
        return null;
    }
    public List<HexSpace> getNeighbour(HexSpace previous){
        return null;
    }

    public int getStrength() {
        return strength;
    }

    public int getMinimalCost() {
        return minimalCost;
    }

    public int getMinimalDepth() {
        return minimalDepth;
    }

    public COLOR getColor() {
        return color;
    }

    public Point getPoint() {
        return point;
    }

    public List<HexSpace> getPrevious() {
        return previous;
    }

    public Game getGame() {
        return game;
    }
}
