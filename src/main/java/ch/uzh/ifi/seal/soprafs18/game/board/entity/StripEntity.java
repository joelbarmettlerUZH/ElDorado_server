package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
@Table(name = "STRIP_ENTITY")
public class StripEntity implements Serializable {
    @Id
    @Column(name = "ID",columnDefinition="CHAR", length=1 ,nullable = false)
    private Character stripID;

    @Embedded
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "hexid")
    @Column(name="HEXSPACES")
    private List<HexSpaceEntity> hexSpaceEntities;

    public StripEntity(Character stripID, List<HexSpaceEntity> hexSpaceEntities){
        this.stripID = stripID;
        this.hexSpaceEntities=hexSpaceEntities;
    }

    public StripEntity(){

    }

    public Character getStripID() {
        return stripID;
    }

    public void setStripID(Character stripID) {
        this.stripID = stripID;
    }

    public List<HexSpaceEntity> getHexSpaceEntities() {
        return hexSpaceEntities;
    }

    public void setHexSpaceEntities(List<HexSpaceEntity> hexSpaceEntities) {
        this.hexSpaceEntities = hexSpaceEntities;
    }
}
