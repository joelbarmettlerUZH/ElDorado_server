package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import java.util.List;

public class BlockadeSpace extends HexSpace{


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
