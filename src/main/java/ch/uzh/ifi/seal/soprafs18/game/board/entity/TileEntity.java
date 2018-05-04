package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
@Table(name = "TILE_ENTITY")
public class TileEntity implements Serializable {
    @Id
    @Column(name = "ID",columnDefinition="CHAR", length=1,nullable = false)
    private Character tileID;

    @Embedded
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "hexid")
    @Column(name="HEXSPACES")
    //@OrderColumn
    private List<HexSpaceEntity> hexSpaceEntities;

    public TileEntity(Character tileID, List<HexSpaceEntity> hexSpaceEntities){
        this.tileID = tileID;
        this.hexSpaceEntities=hexSpaceEntities;
    }

    public TileEntity(){

    }

    public Character getTileID() {
        return tileID;
    }

    public void setTileID(Character tileID) {
        this.tileID = tileID;
    }

    public List<HexSpaceEntity> getHexSpaceEntities() {
        return hexSpaceEntities;
    }

    public void setHexSpaceEntities(List<HexSpaceEntity> hexSpaceEntities) {
        this.hexSpaceEntities = hexSpaceEntities;
    }
}
