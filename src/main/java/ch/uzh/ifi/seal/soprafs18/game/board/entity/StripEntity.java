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
    @Column(name = "ID", unique = true)
    private char stripID;

    @Column(name = "HEXSPACES")
    private List<HexSpaceEntity> hexSpaceEntities;

    public char getStripID() {
        return stripID;
    }

    public void setStripID(char stripID) {
        this.stripID = stripID;
    }

    public List<HexSpaceEntity> getHexSpaceEntities() {
        return hexSpaceEntities;
    }

    public void setHexSpaceEntities(List<HexSpaceEntity> hexSpaceEntities) {
        this.hexSpaceEntities = hexSpaceEntities;
    }
}
