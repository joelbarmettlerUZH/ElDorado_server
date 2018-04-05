package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Embeddable
public class Matrix implements Serializable{

    @JsonIgnore
    @Transient
    private ArrayList<HexSpace> matrixArray;

    @Embedded
    private HexSpace first;

    private int xDim, yDim;

    public Matrix(HexSpace[][] boardMatrix){
        this();
        yDim = boardMatrix.length;
        xDim = boardMatrix[0].length;
        matrixArray = new ArrayList<>();
        for(HexSpace[] row:boardMatrix){
            for(HexSpace hexSpace:row){
                this.matrixArray.add(hexSpace);
            }
        }
    }

    public Matrix(){
        yDim = 0;
        xDim = 0;
        matrixArray = new ArrayList<>();
        matrixArray.add(new HexSpace());
        this.first = new HexSpace();
    }

    public HexSpace get(int x, int y){
        return matrixArray.get(x*yDim+y);
    }

    public ArrayList<HexSpace> getMatrixArray() {
        return matrixArray;
    }

    public int getxDim() {
        return xDim;
    }

    public int getyDim() {
        return yDim;
    }
}
