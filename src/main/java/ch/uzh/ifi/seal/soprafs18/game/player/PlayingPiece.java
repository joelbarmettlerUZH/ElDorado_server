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

    public PlayingPiece(HexSpace hexSpace, int id){
        this.playingPieceId = id;
        this.standsOn = hexSpace;
    }

    private Integer playingPieceId;

    public PlayingPiece(){}

    /*
    Current HexSpace the PLayingPiece is standing on
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private HexSpace standsOn;

    @JsonIgnore
    public Point getPosition(){
        return standsOn.getPoint();
    }

    /*
    function to move itself to a different HexSpace
     */
    public void move(HexSpace moveTo){}

}
