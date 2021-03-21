/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;


public class Board implements Cloneable{
    
    private ArrayList<Tile> board = new ArrayList<Tile>();

    public Board(){
        for(int i = 0; i<81; i++){
            board.add(null);
        }
        Tile castle = new Tile("chateau",0);
        this.board.set(4*9+4,castle);
    }
    
    public void testBoard1(){
        board.set(0*9+0, new Tile("champs",0));
        board.set(1*9+0, new Tile("forêt",0));
        board.set(0*9+1, new Tile("marécage",2));
        board.set(0*9+2, new Tile("marécage",0));
        board.set(0*9+3, new Tile("mine",2));
        board.set(2*9+0, new Tile("forêt",0));
        board.set(3*9+0, new Tile("champs",0));
        board.set(4*9+0, new Tile("champs",1));
        board.set(5*9+0, new Tile("champs",1));
        board.set(3*9+1, new Tile("plaine",0));
        board.set(4*9+1, new Tile("plaine",0));
        board.set(5*9+1, new Tile("mine",0));

    }
    
        public void testBoard2(){
        board.set(0*9+0, new Tile("mer",0));
        board.set(1*9+0, new Tile("champs",0));
        board.set(0*9+1, new Tile("mer",0));
        board.set(0*9+2, new Tile("plaine",0));
        board.set(0*9+3, new Tile("marécage",1));
        board.set(1*9+1, new Tile("champs",0));
        board.set(1*9+2, new Tile("plaine",0));
        board.set(1*9+3, new Tile("marécage",0));
        board.set(1*9+4, new Tile("marécage",0));
        board.set(2*9+0, new Tile("mer",0));
        board.set(2*9+1, new Tile("plaine",1));
        board.set(2*9+2, new Tile("plaine",0));

    }
        
    public boolean verifTile(ArrayList<Integer> coo){
        if(getTile(coo.get(0),coo.get(1))==null){
            return true;
        }
        return false;
    }
    
    public boolean verifCoordDomino(ArrayList<ArrayList<Integer>> coo){
        if(coo.get(0).get(1)>coo.get(1).get(1)){
            if(verifAround(coo.get(0),1) || verifAround(coo.get(1),2))
                return true;
            else
                return false;
        }
        else if(coo.get(0).get(1)<coo.get(1).get(1)){
            if(verifAround(coo.get(1),1) || verifAround(coo.get(0),2))
                return true;
            else
                return false;
        }   
        else if(coo.get(0).get(0)>coo.get(1).get(0)){
            return (verifAround(coo.get(0),3) || verifAround(coo.get(1),4)); 
        }
        else{
             return (verifAround(coo.get(0),4) || verifAround(coo.get(1),3));
        }
    }
    
    private boolean verifAround(ArrayList<Integer> coo, int direction){
        if(coo.get(1)!=0 && getTile(coo.get(0),coo.get(1)-1)!=null && direction != 1 && ((getTile(coo.get(0),coo.get(1)).getType()==getTile(coo.get(0),coo.get(1)-1).getType()) || (getTile(coo.get(0),coo.get(1)-1).getType()=="chateau"))){
            return true;
        }
        if(coo.get(1)!=8 && getTile(coo.get(0),coo.get(1)+1)!=null && direction != 2 && ((getTile(coo.get(0),coo.get(1)).getType()==getTile(coo.get(0),coo.get(1)+1).getType()) || (getTile(coo.get(0),coo.get(1)+1).getType()=="chateau"))){
            return true;
        }
        if(coo.get(0)!=0 && getTile(coo.get(0)-1,coo.get(1))!=null && direction != 3 && ((getTile(coo.get(0),coo.get(1)).getType()==getTile(coo.get(0)-1,coo.get(1)).getType()) || (getTile(coo.get(0)-1,coo.get(1)).getType()=="chateau"))){
            return true;
        }
        if(coo.get(0)!=8 && getTile(coo.get(0)+1,coo.get(1))!=null && direction != 4 && ((getTile(coo.get(0),coo.get(1)).getType()==getTile(coo.get(0)+1,coo.get(1)).getType()) || (getTile(coo.get(0)+1,coo.get(1)).getType()=="chateau"))){
            return true;
        }
        return false;
    }
    
    public void addDomino(Domino d, ArrayList<ArrayList<Integer>> coo){
        this.board.set(coo.get(0).get(0)*9+coo.get(0).get(1), d.getTileL());
        this.board.set(coo.get(1).get(0)*9+coo.get(1).get(1), d.getTileR());
    }
    
    public void addTile(Tile tile, ArrayList<Integer> coo){
        this.board.set(coo.get(0)*9+coo.get(1), tile);
    }
    
    public ArrayList<Tile> getBoard(){
        return this.board;
    }
     
    public void setPlateau(ArrayList<Tile> newBoard){
        this.board = newBoard;
    }
    public String getFieldType(Integer x, Integer y){
        return board.get(x*9 + y).getType();
    }
    
    
    public Integer getCrown(Integer x, Integer y){
        return this.board.get(x*9 + y).getCrown();
    }

    public Tile getTile(Integer x, Integer y){
        return this.board.get(x*9 + y);
    }
}