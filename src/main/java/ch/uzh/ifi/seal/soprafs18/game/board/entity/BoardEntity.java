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
    private List<Integer> tilesRotation;

    @Column(name = "TILESPOSITIONX")
    @ElementCollection
    private List<Integer> tilesPositionX;

    @Column(name = "TILESPOSITIONY")
    @ElementCollection
    private List<Integer> tilesPositionY;

    @Column(name = "STRIP")
    @ElementCollection
    private List<StripEntity> strip;

    @Column(name = "STRIPROTATION")
    @ElementCollection
    private List<Integer> stripRotation;

    @Column(name = "STRIPPOSITIONX")
    @ElementCollection
    private List<Integer> stripPositionX;

    @Column(name = "STRIPPOSITIONY")
    @ElementCollection
    private List<Integer> stripPositionY;

    @Column(name = "BLOCKADEID")
    @ElementCollection
    private List<Integer> blockadeId;

    @Column(name = "BLOCKADEPOSITIONX")
    @ElementCollection
    private List<Integer> blockadePositionX;

    @Column(name = "BLOCKADEPOSITIONY")
    @ElementCollection
    private List<Integer> blockadePositionY;

    @Column(name = "ENDINGSPACES")
    @ElementCollection
    private List<HexSpaceEntity> endingSpaces;

    @Column(name = "ENDINGSPACEPOSITIONX")
    @ElementCollection
    private List<Integer> endingSpacePositionX;

    @Column(name = "ENDINGSPACEPOSITIONY")
    @ElementCollection
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

    public  List<Integer> getBlockadeId(){
        return blockadeId;
    }

    public void setBlockadeId(List<Integer> blockadeId){
        this.blockadeId = blockadeId;
    }

    public List<Integer> getBlockandePositionX(){
        return blockadePositionX;
    }

    public void setBlockadePositionX(List<Integer> blockadePositionX){
        this.blockadePositionX = blockadePositionX;
    }

    public List<Integer> getBlockandePositionY(){
        return blockadePositionY;
    }

    public void setBlockadePositionY(List<Integer> blockadePositionY){
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
}
