/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author leovi
 */
public class Tile {

    private final String type;
    private final int crown;
    
    public Tile(String type, int crown){
        this.type = type;
        this.crown = crown;
    }
    
    public String getType() {
        return type;
    }

    public int getCrown() {
        return crown;
    }
}
