package ch.uzh.ifi.seal.soprafs18.game.hexspace;

import ch.uzh.ifi.seal.soprafs18.game.main.Assembler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Matrix implements Serializable{

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
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

    public void printMatrix(int fromX, int fromY, int toX, int toY){
        for(int i = fromX; i<toX; i++){
            for(int j = fromY; j<toY; j++){
                HexSpace hexSpace = get(i, j);
                if(hexSpace.getColor() == COLOR.EMPTY){
                    System.out.print("   ");
                    continue;
                }
                if(hexSpace instanceof BlockadeSpace){
                    System.out.print("|"+hexSpace.getColor().toString().charAt(0)+"|");
                    continue;
                }
                String s = hexSpace.getColor().toString();
                System.out.print(s.charAt(0)+""+s.charAt(1)+" ");
            }
            System.out.println("");
        }
    }

    public HexSpace get(int x, int y){
        return matrixArray.get(x*yDim+y);
    }

    public List<HexSpace> getMatrixArray() {
        return matrixArray;
    }

    public void setMatrixArray(List<HexSpace> matrixArray) {
        this.matrixArray = matrixArray;
    }

    public int getXDim() {
        return xDim;
    }

    public void setXDim(int xDim) {
        this.xDim = xDim;
    }

    public int getYDim() {
        return yDim;
    }

    public void setYDim(int yDim) {
        this.yDim = yDim;
    }
}
