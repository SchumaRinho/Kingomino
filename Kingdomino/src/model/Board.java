/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;

/**
 * Classe représentant un plateau, implémentant Cloneable.
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public class Board implements Cloneable{
    
    private ArrayList<Tile> board = new ArrayList<Tile>();

    /**
     * Constructeur Board.
     */
    public Board(){
        for(int i = 0; i<81; i++){
            board.add(null);
        }
        Tile castle = new Tile("chateau",0);
        this.board.set(4*9+4,castle);
    }
    
    /**
     * méthode de plateau "test" qui créer un plateau d'une partie en cours.
     */
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
    
    /**
     * méthode de plateau "test qui créer un plateau d'une partie en cours.
     */
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
        
    /**
     * Verifie si un Tile peut etre placer sur le plateau
     * @param coo
     * @return vrai si il n'y a rien aux coordonnées donner en parametres 
     */
    public boolean verifTile(ArrayList<Integer> coo){
        if(coo.get(0)>=0 && coo.get(0)<=8 && coo.get(1)>=0 && coo.get(1)<=8 && getTile(coo.get(0),coo.get(1))==null){
            return true;
        }
        return false;
    }
    
    /**
     * Verifie la rotation du domino sur le plateau
     * @param coo
     * @return Le résultat de verifAround
     * @see Board#verifAround(ArrayList, int)
     */
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
    
    /**
     * Verifie si un Tile peut etre placer dans le tableau en fonction de ce qui se trouve autour de lui.
     * @param coo
     * @param direction
     * @return vrai si les Tile autour des coordonnées permette le placement
     * @see Board#verifCoordDomino(ArrayList)
     */
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
    
    /**
     * Verifie si un Tile peut etre placer en respectant les normes de tailles du plateau.
     * @return vrai si il respectent la taille 5x5 du plateau
     */
    public boolean verifPlacement(){
        int iMini=100,iMax=0,jMini=100,jMax=0;  //pour vérifier qu'on soit dans un tableau de 5x5
            for(int i = 0; i<9; i++){           //On récupere le plus petit i et j, et on les soustrait au plus grand i et j 
                for(int j = 0; j<9; j++){
                    if(this.getTile(i,j)!=null){
                        if(iMini==100)
                            iMini=i;
                        if(i>iMax)
                            iMax=i;
                        if(j<jMini)
                            jMini=j;
                        if(j>jMax)
                            jMax=j;
                    }
                }
            }
        if((iMax-iMini)+1>5)
            return false;
        if((jMax-jMini)+1>5)
            return false;
        return true;
    }
    
    /**
     * Permet d'ajouter un domino dans le plateau.
     * @param d
     * @param coo
     */
    public void addDomino(Domino d, ArrayList<ArrayList<Integer>> coo){
        this.board.set(coo.get(0).get(0)*9+coo.get(0).get(1), d.getTileL());
        this.board.set(coo.get(1).get(0)*9+coo.get(1).get(1), d.getTileR());
    }
    
    /**
     * Permet d'ajouter un Tile dans le plateau.
     * @param tile
     * @param coo
     */
    public void addTile(Tile tile, ArrayList<Integer> coo){
        this.board.set(coo.get(0)*9+coo.get(1), tile);
    }
    
    /**
     * récupère le plateau.
     * @return le plateau
     */
    public ArrayList<Tile> getBoard(){
        return this.board;
    }
     
    /**
     * créer un plateau
     * @param newBoard
     */
    public void setPlateau(ArrayList<Tile> newBoard){
        this.board = newBoard;
    }

    /**
     * récupère le type d'un Tile dans le plateau
     * @param x
     * @param y
     * @return type du Tile se trouvant aux coordonnées x,y
     */
    public String getFieldType(Integer x, Integer y){
        return this.getTile(x, y).getType();
    }
    
    /**
     * récupère le nombre de couronnes d'un Tile dans le plateau
     * @param x
     * @param y
     * @return crown du Tile se trouvant aux coordonnées x,y
     */
    public Integer getCrown(Integer x, Integer y){
        return this.getTile(x, y).getCrown();
    }

    /**
     * récupère un Tile dans le plateau
     * @param x
     * @param y
     * @return un Tile aux coordonnées x,y
     */
    public Tile getTile(Integer x, Integer y){
        return this.board.get(x*9 + y);
    }
    
    /**
     * créer une copie du plateau
     * @param plateau
     */
    public void cloneFrom(Board plateau){
        this.setPlateau((ArrayList<Tile>)plateau.getBoard().clone());
    }
}
