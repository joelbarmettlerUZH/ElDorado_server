package ch.uzh.ifi.seal.soprafs18.game.board.entity;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

@Entity
@Table(name = "BOARD")
public class BoardEntity {
    @Id
    @Column(unique = true, name = "BOARDID")
    private int boardID;

    @Column(name = "TILES")
    private List<TileEntity> tiles;

    @Column(name = "TILESROTATION")
    private List<Integer> tilesRotation;

    @Column(name = "TILESPOSITIONX")

    private List<Integer> tilesPositionX;

    @Column(name = "TILESPOSITIONY")
    private List<Integer> tilesPositionY;

    @Column(name = "STRIP")
    private List<StripEntity> strip;

    @Column(name = "STRIPROTATION")
    private List<Integer> stripRotation;

    @Column(name = "STRIPPOSITIONX")
    private List<Integer> stripPositionX;

    @Column(name = "STRIPPOSITIONY")
    private List<Integer> stripPositionY;

    @Column(name = "BLOCKADE")
    private List<List<List<Integer>>> blockade;

    @Column(name = "STARTINGSPACESX")
    private List<Integer> startingSpacesX;

    @Column(name = "STARTINGSPACESY")
    private List<Integer> startingSpacesY;

    @Column(name = "ENDINGSPACES")
    private List<HexSpaceEntity> endingSpaces;

    @Column(name = "ENDINGSPACEPOSITIONX")
    private List<Integer> endingSpacePositionX;

    @Column(name = "ENDINGSPACEPOSITIONY")
    private List<Integer> endingSpacePositionY;

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
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

    public List<List<List<Integer>>> getBlockade() {
        return blockade;
    }

    public void setBlockade(List<List<List<Integer>>> blockade) {
        this.blockade = blockade;
    }

    public List<Integer> getStartingSpacesX() {
        return startingSpacesX;
    }

    public void setStartingSpacesX(List<Integer> startingSpacesX) {
        this.startingSpacesX = startingSpacesX;
    }

    public List<Integer> getStartingSpacesY() {
        return startingSpacesY;
    }

    public void setStartingSpacesY(List<Integer> startingSpacesY) {
        this.startingSpacesY = startingSpacesY;
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
}
