package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "STRIP")
public class StripEntity {
    @Id
    @Column(unique = true)
    private int stripID;

    @Column(name = "HEXSPACES")
    private List<HexSpaceEntity> hexSpaceEntities;

    public int getStripID() {
        return stripID;
    }

    public void setStripID(int stripID) {
        this.stripID = stripID;
    }

    public List<HexSpaceEntity> getHexSpaceEntities() {
        return hexSpaceEntities;
    }

    public void setHexSpaceEntities(List<HexSpaceEntity> hexSpaceEntities) {
        this.hexSpaceEntities = hexSpaceEntities;
    }
}
