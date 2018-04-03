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

    @Column(name = "TILESPOSITION")
    private List<Integer> tilesPosition;

    @Column(name = "STRIP")
    private List<StripEntity> strip;

    @Column(name = "STRIPROTATION")
    private List<Integer> stripRotation;

    @Column(name = "STRIPPOSITION")
    private List<Point> stripPosition;

    @Column(name = "BLOCKADE")
    private Point[][] blockade;

    @Column(name = "STARTINGSPACES")
    private List<Point> startingSpaces;

    @Column(name = "ENDINGSPACES")
    private List<HexSpaceEntity> endingSpaces;

    @Column(name = "ENDINGSPACEPOSITION")
    private List<Point> endingSpacePosition;

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

    public List<Integer> getTilesPosition() {
        return tilesPosition;
    }

    public void setTilesPosition(List<Integer> tilesPosition) {
        this.tilesPosition = tilesPosition;
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

    public List<Point> getStripPosition() {
        return stripPosition;
    }

    public void setStripPosition(List<Point> stripPosition) {
        this.stripPosition = stripPosition;
    }

    public Point[][] getBlockade() {
        return blockade;
    }

    public void setBlockade(Point[][] blockade) {
        this.blockade = blockade;
    }

    public List<Point> getStartingSpaces() {
        return startingSpaces;
    }

    public void setStartingSpaces(List<Point> startingSpaces) {
        this.startingSpaces = startingSpaces;
    }

    public List<HexSpaceEntity> getEndingSpaces() {
        return endingSpaces;
    }

    public void setEndingSpaces(List<HexSpaceEntity> endingSpaces) {
        this.endingSpaces = endingSpaces;
    }

    public List<Point> getEndingSpacePosition() {
        return endingSpacePosition;
    }

    public void setEndingSpacePosition(List<Point> endingSpacePosition) {
        this.endingSpacePosition = endingSpacePosition;
    }
}
