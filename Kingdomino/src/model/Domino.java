/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author leovi
 */
public class Domino implements Comparable<Domino>{

    private Tile tileR;
    private Tile tileL;
    private int value;
    private Integer player = null;

    public Domino(Tile tileR, Tile tileL, int value){
        this.tileL = tileL;
        this.tileR = tileR;
        this.value = value;
    }
    
    public Tile getTileR() {
        return this.tileR;
    }

    public Tile getTileL() {
        return this.tileL;
    }

    public int getValue(){
        return this.value;
    }

    public Integer getPlayer(){
        return this.player;
    }

    public void setPlayer(Integer player){
        this.player = player;
    }

    public void resetPlayer(){
        this.player = null;
    }
  
    public ArrayList<Tile> getAsList(){
        ArrayList<Tile> tile = new ArrayList<>();
        tile.add(this.tileL);
        tile.add(this.tileR);
        return tile;
    }
    
    @Override
        public int compareTo(Domino domino) {
               return (this.value - domino.getValue());
        }
}
