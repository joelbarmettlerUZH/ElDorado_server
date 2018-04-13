package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Embeddable
@Table(name = "STRIP_ENTITY")
public class StripEntity {
    @Id
    @Column(name = "ID", unique = true)
    private char stripID;

    @Column(name = "HEXSPACES")
    @ElementCollection
    private List<HexSpaceEntity> hexSpaceEntities;

    public StripEntity(char stripID, List<HexSpaceEntity> hexSpaceEntities){
        this.stripID = stripID;
        this.hexSpaceEntities=hexSpaceEntities;
    }

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
