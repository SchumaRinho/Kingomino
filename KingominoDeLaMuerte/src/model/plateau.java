/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Plateau {
    
    private ArrayList<Tile> plateau = new ArrayList<Tile>();
    public static HashMap<String,String> color;

    public Plateau(){
        ArrayList tmp = null;
        for(int i = 0; i<81; i++){
            plateau.add(null);
        }
        Tile chateau = new Tile("chateau",0);
        this.plateau.set(4*9+4,chateau);

        this.color = new HashMap<String,String>();
        this.color.put("marécage","\u001B[46m");
        this.color.put("mer","\u001B[44m");
        this.color.put("plaine","\u001B[42m");
        this.color.put("champs","\u001B[43m");
        this.color.put("forêt","\u001B[45m");
        this.color.put("mine","\u001B[41m");
    }
    
    public void plateauTest(){
        Tile piece = new Tile("mine", 1);
        plateau.set(6*9+8, piece);
        
        piece = new Tile("mer",0);
        plateau.set(3*9+4, piece);
        plateau.set(3*9+5, piece);
        plateau.set(3*9+7, piece);
        piece = new Tile("mer",1);
        plateau.set(3*9+6, piece);
        plateau.set(4*9+5, piece);
        plateau.set(3*9+8, piece);
        
        piece = new Tile("forêt",0);
        plateau.set(5*9+5, piece);
        plateau.set(4*9+6, piece);
        plateau.set(4*9+7, piece);
        plateau.set(4*9+8, piece);
        
        piece = new Tile("champs",0);
        plateau.set(5*9+4, piece);
        plateau.set(5*9+7, piece);
        plateau.set(5*9+8, piece);
        plateau.set(7*9+5, piece);
        piece = new Tile("champs",1);
        plateau.set(7*9+6, piece);
        
        piece = new Tile("marécage",1);
        plateau.set(6*9+6, piece);
        piece = new Tile("marécage",2);
        plateau.set(5*9+6, piece);
        
        piece = new Tile("plaine",0);
        plateau.set(6*9+5, piece);
        piece = new Tile("plaine",1);
        plateau.set(6*9+4, piece);
        piece = new Tile("plaine",2);
        plateau.set(7*9+4, piece);
    }
    
    public void test(){
        Tile a = new Tile("mine",3);
        Tile b = new Tile("champs",0);
        
        Domino p = new Domino(a,b,48);
        
        addTile(p,6,7,7,7);
    }
    
    public void addTile(Domino p, Integer xa, Integer ya, Integer xb, Integer yb){
        this.plateau.set(xa*9+ya, p.getPieces().get(0));
        this.plateau.set(xb*9+yb, p.getPieces().get(1));
    }
        
    public String getType(Integer coo){
        return plateau.get(coo).getType();
    }
    
    
    public Integer getCrown(Integer coo, String key){
        return this.plateau.get(coo).getCrown();
    }
    
    public ArrayList<Tile> getPlateau(){
        return this.plateau;
    }

    
}