package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "BOARD_ENTITY")
public class BoardEntity implements Serializable {

    public BoardEntity(int boardID, String name, List<TileEntity> tiles, List<Integer> tilesRotation, List<Integer> tilesPositionX,
                       List<Integer> tilesPositionY,List<StripEntity> strip, List<Integer> stripRotation,
                       List<Integer> stripPositionX,List<Integer> stripPositionY,List<Integer> blockadeId, List<Integer> blockadeOrientation,
                       List<Integer> blockadePositionX, List<Integer> blockadePositionY,List<HexSpaceEntity> endingSpaces,
                       List<Integer> endingSpacePositionX, List<Integer> endingSpacePositionY, List<HexSpaceEntity> eldoradoSpace,
                       List<Integer> eldoradoSpacePositionX, List<Integer> eldoradoSpacePositionY)  {
        //System.out.println("constr");
        this.name = name;
        this.boardID = boardID;
        this.tiles = tiles;
        this.tilesRotation = tilesRotation;
        this.tilesPositionX = tilesPositionX;
        this.tilesPositionY = tilesPositionY;
        this.strip = strip;
        this.stripRotation = stripRotation;
        this.stripPositionX = stripPositionX;
        this.stripPositionY = stripPositionY;
        this.blockadeId = blockadeId;
        this.blockadeOrientation = blockadeOrientation;
        this.blockadePositionX = blockadePositionX;
        this.blockadePositionY = blockadePositionY;
        this.endingSpaces = endingSpaces;
        this.endingSpacePositionX = endingSpacePositionX;
        this.endingSpacePositionY = endingSpacePositionY;
        this.eldoradoSpacePositionX = eldoradoSpacePositionX;
        this.eldoradoSpacePositionY = eldoradoSpacePositionY;
        this.eldoradoSpace = eldoradoSpace;
    }

    public BoardEntity(){

    }

    @Id
    @Column(unique = true, name = "BOARDID")
    private int boardID;

    @Column(name = "PATHNAME")
    private String name;

    @JsonIgnore
    @Embedded
    @ManyToMany
    @JoinColumn(name = "tileID")
    @Column(name = "TILES",nullable = true)
    //@OneToMany(targetEntity = TileEntity.class, fetch = FetchType.EAGER)
    //@JoinTable(name="TILES",joinColumns = @JoinColumn(name="BOARD_BOARDID"),inverseJoinColumns = @JoinColumn(name="TILE_ID"))
    private List<TileEntity> tiles;

    @JsonIgnore
    @Column(name = "TILESROTATION",nullable = true)
    @ElementCollection
    private List<Integer> tilesRotation;

    @JsonIgnore
    @Column(name = "TILESPOSITIONX",nullable = true)
    @ElementCollection
    private List<Integer> tilesPositionX;

    @JsonIgnore
    @Column(name = "TILESPOSITIONY",nullable = true)
    @ElementCollection
    private List<Integer> tilesPositionY;

    @JsonIgnore
    @Embedded
    @ManyToMany
    @JoinColumn(name = "stripID")
    @Column(name = "STRIP",nullable = true)
    //@ElementCollection
    private List<StripEntity> strip;

    @JsonIgnore
    @Column(name = "STRIPROTATION",nullable = true)
    @ElementCollection
    private List<Integer> stripRotation;

    @JsonIgnore
    @Column(name = "STRIPPOSITIONX",nullable = true)
    @ElementCollection
    private List<Integer> stripPositionX;

    @JsonIgnore
    @Column(name = "STRIPPOSITIONY",nullable = true)
    @ElementCollection
    private List<Integer> stripPositionY;

    @JsonIgnore
    @Column(name = "BLOCKADEID",nullable = true)
    @ElementCollection
    private List<Integer> blockadeId;

    @JsonIgnore
    @Column(name = "BLOCKADEORIENTATION",nullable = true)
    @ElementCollection
    private List<Integer> blockadeOrientation;

    @JsonIgnore
    @Column(name = "BLOCKADEPOSITIONX",nullable = true)
    @ElementCollection
    private List<Integer> blockadePositionX;

    @JsonIgnore
    @Column(name = "BLOCKADEPOSITIONY",nullable = true)
    @ElementCollection
    private List<Integer> blockadePositionY;

    @JsonIgnore
    @Embedded
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "hexid")
    @Column(name = "ENDINGSPACES",nullable = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<HexSpaceEntity> endingSpaces;

    @JsonIgnore
    @Column(name = "ENDINGSPACEPOSITIONX",nullable = true)
    @ElementCollection
    private List<Integer> endingSpacePositionX;

    @JsonIgnore
    @Column(name = "ENDINGSPACEPOSITIONY",nullable = true)
    @ElementCollection
    private List<Integer> endingSpacePositionY;

    @JsonIgnore
    @Embedded
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "hexid")
    @Column(name = "ELDORADO",nullable = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<HexSpaceEntity> eldoradoSpace;

    @JsonIgnore
    @Column(name = "ELDORADOPOSITIONX",nullable = true)
    @ElementCollection
    private List<Integer> eldoradoSpacePositionX;

    @JsonIgnore
    @Column(name = "ELDORADOPOSITIONY",nullable = true)
    @ElementCollection
    private List<Integer> eldoradoSpacePositionY;

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TileEntity> getTiles() {
        return tiles;
    }

    public void setTiles(List<TileEntity> tiles) {
        this.tiles = tiles;
    }

    public List<Integer> getTilesRotation() {
        return tilesRotation;
    }

    public void setTilesRotation(List<Integer> tilesRotation) {
        this.tilesRotation = tilesRotation;
    }

    public List<Integer> getTilesPositionX() {
        return tilesPositionX;
    }

    public void setTilesPositionX(List<Integer> tilesPositionX) {
        this.tilesPositionX = tilesPositionX;
    }

    public List<Integer> getTilesPositionY() {
        return tilesPositionY;
    }

    public void setTilesPositionY(List<Integer> tilesPositionY) {
        this.tilesPositionY = tilesPositionY;
    }

    public List<StripEntity> getStrip() {
        return strip;
    }

    public void setStrip(List<StripEntity> strip) {
        this.strip = strip;
    }

    public List<Integer> getStripRotation() {
        return stripRotation;
    }

    public void setStripRotation(List<Integer> stripRotation) {
        this.stripRotation = stripRotation;
    }

    public List<Integer> getStripPositionX() {
        return stripPositionX;
    }

    public void setStripPositionX(List<Integer> stripPositionX) {
        this.stripPositionX = stripPositionX;
    }

    public List<Integer> getStripPositionY() {
        return stripPositionY;
    }

    public void setStripPositionY(List<Integer> stripPositionY) {
        this.stripPositionY = stripPositionY;
    }

    public List<Integer> getBlockadeId() {
        return blockadeId;
    }

    public void setBlockadeId(List<Integer> blockadeId) {
        this.blockadeId = blockadeId;
    }

    public List<Integer> getBlockadeOrientation() {
        return blockadeOrientation;
    }

    public void setBlockadeOrientation(List<Integer> blockadeOrientation) {
        this.blockadeOrientation = blockadeOrientation;
    }

    public List<Integer> getBlockadePositionX() {
        return blockadePositionX;
    }

    public void setBlockadePositionX(List<Integer> blockadePositionX) {
        this.blockadePositionX = blockadePositionX;
    }

    public List<Integer> getBlockadePositionY() {
        return blockadePositionY;
    }

    public void setBlockadePositionY(List<Integer> blockadePositionY) {
        this.blockadePositionY = blockadePositionY;
    }

    public List<HexSpaceEntity> getEndingSpaces() {
        return endingSpaces;
    }

    public void setEndingSpaces(List<HexSpaceEntity> endingSpaces) {
        this.endingSpaces = endingSpaces;
    }

    public List<Integer> getEndingSpacePositionX() {
        return endingSpacePositionX;
    }

    public void setEndingSpacePositionX(List<Integer> endingSpacePositionX) {
        this.endingSpacePositionX = endingSpacePositionX;
    }

    public List<Integer> getEndingSpacePositionY() {
        return endingSpacePositionY;
    }

    public void setEndingSpacePositionY(List<Integer> endingSpacePositionY) {
        this.endingSpacePositionY = endingSpacePositionY;
    }

    public List<HexSpaceEntity> getEldoradoSpace() {
        return eldoradoSpace;
    }

    public void setEldoradoSpace(List<HexSpaceEntity> eldoradoSpace) {
        this.eldoradoSpace = eldoradoSpace;
    }

    public List<Integer> getEldoradoSpacePositionX() {
        return eldoradoSpacePositionX;
    }

    public void setEldoradoSpacePositionX(List<Integer> eldoradoSpacePositionX) {
        this.eldoradoSpacePositionX = eldoradoSpacePositionX;
    }

    public List<Integer> getEldoradoSpacePositionY() {
        return eldoradoSpacePositionY;
    }

    public void setEldoradoSpacePositionY(List<Integer> eldoradoSpacePositionY) {
        this.eldoradoSpacePositionY = eldoradoSpacePositionY;
    }
}
