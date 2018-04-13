package ch.uzh.ifi.seal.soprafs18.game.player;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.io.Serializable;

@Embeddable
@Data
public class PlayingPiece  implements Serializable {

    public PlayingPiece(int id, HexSpace hexSpace){
        this.playingPieceId = id;
        this.standsOn = hexSpace;
    }

    public PlayingPiece(){}

    /*
    unique id for the playing piece
     */
    private Integer playingPieceId;

    /*
    Current HexSpace the PLayingPiece is standing on
     */
    @Embedded
    private HexSpace standsOn;

    public Point getPosition(){
        return standsOn.getPoint();
    }

    /*
    function to move itself to a different HexSpace
     */
    public void move(HexSpace moveTo){}

}
