/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author clemLeroy
 */

public class Plateau {
    
    public static final String ANSI_RESET = "\u001B[0m";
    
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    
    private ArrayList<HashMap<String,Integer>> plateau = new ArrayList<HashMap<String,Integer>>();
    
    public Plateau(){
        ArrayList tmp = null;
        for(int i = 0; i<81; i++){
            plateau.add(null);
        }
        HashMap chateau = new HashMap<String,Integer>();
        chateau.put("chateau",0);
        this.plateau.set(4*9+4,chateau);
        /*plateauTest();
        test();*/
    }
    
    public void plateauTest(){
        HashMap piece = new HashMap<String, Integer>();
        piece.put("mine", 1);
        plateau.set(6*9+8, piece);
        
        piece = new HashMap<String, Integer>();piece.put("mer",0);
        plateau.set(3*9+4, piece);
        plateau.set(3*9+5, piece);
        plateau.set(3*9+7, piece);
        piece = new HashMap<String, Integer>();piece.put("mer",1);
        plateau.set(3*9+6, piece);
        plateau.set(4*9+5, piece);
        plateau.set(3*9+8, piece);
        
        piece = new HashMap<String, Integer>();piece.put("forêt",0);
        plateau.set(5*9+5, piece);
        plateau.set(4*9+6, piece);
        plateau.set(4*9+7, piece);
        plateau.set(4*9+8, piece);
        
        piece = new HashMap<String, Integer>();piece.put("champs",0);
        plateau.set(5*9+4, piece);
        plateau.set(5*9+7, piece);
        plateau.set(5*9+8, piece);
        plateau.set(7*9+5, piece);
        piece = new HashMap<String, Integer>();piece.put("champs",1);
        plateau.set(7*9+6, piece);
        
        piece = new HashMap<String, Integer>();piece.put("marécage",1);
        plateau.set(6*9+6, piece);
        piece = new HashMap<String, Integer>();piece.put("marécage",2);
        plateau.set(5*9+6, piece);
        
        piece = new HashMap<String, Integer>();piece.put("plaine",0);
        plateau.set(6*9+5, piece);
        piece = new HashMap<String, Integer>();piece.put("plaine",1);
        plateau.set(6*9+4, piece);
        piece = new HashMap<String, Integer>();piece.put("plaine",2);
        plateau.set(7*9+4, piece);
    }
    
    public void test(){
        HashMap a = new HashMap<String, Integer>();
        HashMap b = new HashMap<String, Integer>();
        
        a.put("mine",3);
        b.put("forêt",1);
        Pieces p = new Pieces(a,b);
        
        addTile(p,6,7,7,7);
    }
    
    public void addTile(Pieces p, Integer xa, Integer ya, Integer xb, Integer yb){
        this.plateau.set(xa*9+ya, p.getPieces().get(0));
        this.plateau.set(xb*9+yb, p.getPieces().get(1));
    }
        
    public String getKey(Integer coo){
        return plateau.get(coo).keySet().toArray()[0].toString();
    }
    
    
    public Integer getCrown(Integer coo, String key){
        return this.plateau.get(coo).get(key);
    }
    
    public ArrayList<HashMap<String,Integer>> getPlateau(){
        return this.plateau;
    }

    
}
//voilà, la visite est terminé ☺