package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import ch.uzh.ifi.seal.soprafs18.game.main.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockadeSpace extends HexSpace{

    public BlockadeSpace(int strenght, int minimalCost, int minimalDepht, COLOR color, Point point,
                         ArrayList<HexSpace> previous, Game game){
        super(strenght, minimalCost, minimalDepht, color, point, previous, game);
    }
    /*
    This method needs to be overwritten in order to determine the neighbours of a special HexSpaceEntity of type BlockadeSpace,
    since other BlockadeSpaces shall not count as its neighbours. The overwritten method considers where the player was
    coming from and therefore computes the neighbours ignoring inactive barricades.
     */

    @Override
    public List<HexSpace> getNeighbour(){
        return null;
    }
}
