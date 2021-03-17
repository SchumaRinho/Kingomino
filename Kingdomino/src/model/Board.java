/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;


public class Board {
    
    private ArrayList<Tile> plateau = new ArrayList<Tile>();
    public static HashMap<String,String> color;

    public Board(){
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
        plateau.set(0*9+0, new Tile("champs",0));
        plateau.set(1*9+0, new Tile("forêt",0));
        plateau.set(0*9+1, new Tile("marécage",2));
        plateau.set(0*9+2, new Tile("marécage",0));
        plateau.set(0*9+3, new Tile("mine",2));
        plateau.set(2*9+0, new Tile("forêt",0));
        plateau.set(3*9+0, new Tile("champs",0));
        plateau.set(4*9+0, new Tile("champs",1));
        plateau.set(5*9+0, new Tile("champs",1));
        plateau.set(3*9+1, new Tile("plaine",0));
        plateau.set(4*9+1, new Tile("plaine",0));
        plateau.set(5*9+1, new Tile("mine",0));

    }
    
        public void plateauTest1(){
        plateau.set(0*9+0, new Tile("mer",0));
        plateau.set(1*9+0, new Tile("champs",0));
        plateau.set(0*9+1, new Tile("mer",0));
        plateau.set(0*9+2, new Tile("plaine",0));
        plateau.set(0*9+3, new Tile("marécage",1));
        plateau.set(1*9+1, new Tile("champs",0));
        plateau.set(1*9+2, new Tile("plaine",0));
        plateau.set(1*9+3, new Tile("marécage",0));
        plateau.set(1*9+4, new Tile("marécage",0));
        plateau.set(2*9+0, new Tile("mer",0));
        plateau.set(2*9+1, new Tile("plaine",1));
        plateau.set(2*9+2, new Tile("plaine",0));

    }
    
    public void addDomino(Domino d, ArrayList<ArrayList<Integer>> coo){
        this.plateau.set(coo.get(0).get(0)*9+coo.get(0).get(1), d.getPgauche());
        this.plateau.set(coo.get(1).get(0)*9+coo.get(1).get(1), d.getPdroite());
    }
        
    public String getFieldType(Integer x, Integer y){
        return plateau.get(x*9 + y).getType();
    }
    
    
    public Integer getCrown(Integer x, Integer y){
        return this.plateau.get(x*9 + y).getCrown();
    }

    public Tile getTile(Integer x, Integer y){
        return this.plateau.get(x*9 + y);
    }
    
}