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
    @ElementCollection
    private List<TileEntity> tiles;

    @Column(name = "TILESROTATION")
    @ElementCollection
    private List<Integer> tilesRotatoin;

    @Column(name = "STRIP")
    @ElementCollection
    private List<StripEntity> strip;

    @Column(name = "STRIPROTATION")
    @ElementCollection
    private List<Integer> stripRotation;

    @Column(name = "STRIPPOSITION")
    @ElementCollection
    private List<Point> stripPosition;

    /*
    @Column(name = "BLOCKADE")
    private Point[][] blockade;
    */

    @Column(name = "STARTINGSPACES")
    @ElementCollection
    private List<Point> startingSpaces;

    @Column(name = "ENDINGSPACES")
    @ElementCollection
    private List<Point> endingSpaces;

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

    public List<Integer> getTilesRotatoin() {
        return tilesRotatoin;
    }

    public void setTilesRotatoin(List<Integer> tilesRotatoin) {
        this.tilesRotatoin = tilesRotatoin;
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

    /*
    public Point[][] getBlockade() {
        return blockade;
    }

    public void setBlockade(Point[][] blockade) {
        this.blockade = blockade;
    }
    */

    public List<Point> getStartingSpaces() {
        return startingSpaces;
    }

    public void setStartingSpaces(List<Point> startingSpaces) {
        this.startingSpaces = startingSpaces;
    }

    public List<Point> getEndingSpaces() {
        return endingSpaces;
    }

    public void setEndingSpaces(List<Point> endingSpaces) {
        this.endingSpaces = endingSpaces;
    }
}
