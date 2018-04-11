package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TILE")
public class TileEntity {
    @Id
    @Column(unique = true)
    private int tileID;

    @Column(name = "HEXSPACES")
    @ElementCollection
    private List<HexSpaceEntity> hexSpaceEntities;

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public List<HexSpaceEntity> getHexSpaceEntities() {
        return hexSpaceEntities;
    }

    public void setHexSpaceEntities(List<HexSpaceEntity> hexSpaceEntities) {
        this.hexSpaceEntities = hexSpaceEntities;
    }
}
