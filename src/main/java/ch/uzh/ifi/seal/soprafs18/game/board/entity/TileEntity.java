package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "TILE")
public class TileEntity {
    @Id
    @Column(name = "ID",unique = true)
    private char tileID;

    @Column(name = "HEXSPACES")
    private List<HexSpaceEntity> hexSpaceEntities;

    public TileEntity(char tileID, List<HexSpaceEntity> hexSpaceEntities){
        this.tileID = tileID;
        this.hexSpaceEntities=hexSpaceEntities;
    }

    public int getTileID() {
        return tileID;
    }

    public void setTileID(char tileID) {
        this.tileID = tileID;
    }

    public List<HexSpaceEntity> getHexSpaceEntities() {
        return hexSpaceEntities;
    }

    public void setHexSpaceEntities(List<HexSpaceEntity> hexSpaceEntities) {
        this.hexSpaceEntities = hexSpaceEntities;
    }
}
