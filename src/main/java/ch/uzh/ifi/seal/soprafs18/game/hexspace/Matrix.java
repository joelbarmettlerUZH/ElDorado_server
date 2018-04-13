package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
public class Matrix implements Serializable{

    @Embedded
    @ElementCollection
    private List<HexSpace> matrixArray;

    private int xDim, yDim;

    public Matrix(HexSpace[][] boardMatrix){
        this();
        xDim = boardMatrix.length; //switched x and y (Marius)
        yDim = boardMatrix[0].length;
        matrixArray = new ArrayList<>();
        /*
        for(HexSpace[] row:boardMatrix){
            for(HexSpace hexSpace:row){
                this.matrixArray.add(hexSpace);
            }
        }
        */
        for (int i = 0; i<xDim; i++){
            for (int j = 0; j<yDim;j++){
                this.matrixArray.add(boardMatrix[i][j]);
            }
        }
    }

    public Matrix(){
        yDim = 0;
        xDim = 0;
        matrixArray = new ArrayList<>();
    }

    public HexSpace get(int x, int y){
        return matrixArray.get(x*yDim+y);
    }
}
