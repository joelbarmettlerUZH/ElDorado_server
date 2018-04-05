package ch.uzh.ifi.seal.soprafs18.game.player;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;

import javax.persistence.Embeddable;
import java.io.Serializable;

public class PlayingPiece  implements Serializable {
    /*
    unique id for the playing piece
     */
    private Integer playingPieceId;

    /*
    Current HexSpace the PLayingPiece is standing on
     */
    private HexSpace standsOn;

    /*
    function to move itself to a different HexSpace
     */
    public void move(HexSpace moveTo){}
}
