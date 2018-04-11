package ch.uzh.ifi.seal.soprafs18.game.player;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;

import javax.persistence.Embeddable;

public class PlayingPiece {
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
