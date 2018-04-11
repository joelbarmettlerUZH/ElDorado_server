package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
@Table(name = "TILE")
public class TileEntity implements Serializable {
    @Id
    @Column(name = "ID",unique = true)
    private char tileID;

    //@Column(name = "HEXSPACES")
    //@ElementCollection
    //@PrimaryKeyJoinColumn
    //@OneToMany(cascade=CascadeType.ALL, mappedBy = "hexID", fetch = FetchType.EAGER)
    //@ElementCollection
    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "hexID")
    //@OneToMany(cascade=CascadeType.ALL, mappedBy = "hexID", fetch = FetchType.EAGER)
    //@JoinColumn(name="hexID")$
    //@OneToMany
    //@JoinColumn(name = "HEXSPACES",referencedColumnName = "ID")
    //@ElementCollection
    //@Embedded
    //@Column(name="HEXSPACES")
    //@CollectionTable
    //@OneToMany
    //@JoinTable(name="HEXSPACES",joinColumns = @JoinColumn(name="TILE_ID"),inverseJoinColumns = @JoinColumn(name="HEXSPACE_ID"))
    //@CollectionTable(name="HEXSPACES", joinColumns = @JoinColumn(name="hexID"))
    //@ElementCollection
    //@Column(name="HEXSPACES")
    //@OneToMany
    //@JoinTable(name="HEXSPACES",joinColumns = @JoinColumn(name="TILE_ID"),inverseJoinColumns = @JoinColumn(name="HEXSPACE_HEXID"))

    //@OneToMany(mappedBy = "hexID",fetch=FetchType.LAZY)
    @Embedded
    @ElementCollection
    //
    //@ManyToMany(targetEntity = HexSpaceEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JoinTable(name="HEXSPACES",joinColumns = @JoinColumn(name="TILE_ID"),inverseJoinColumns = @JoinColumn(name="HEXSPACE_ID"))
    @Column(name="HEXSPACES")
    private List<HexSpaceEntity> hexSpaceEntities;

    public TileEntity(char tileID, List<HexSpaceEntity> hexSpaceEntities){
        this.tileID = tileID;
        this.hexSpaceEntities=hexSpaceEntities;
    }

    public TileEntity(){

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
