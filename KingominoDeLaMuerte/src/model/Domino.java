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

    private Tile Pdroite;
    private Tile Pgauche;
    private int valeurPiece;
    private Integer joueur = null;
    
    public Domino(Tile Pgauche, Tile Pdroite, int valeur){
        this.Pgauche = Pgauche;
        this.Pdroite = Pdroite;
        this.valeurPiece = valeur;
    }

    public void setPlayer(Integer playerNumber){
        this.joueur = playerNumber;
    }

    public Integer getPlayer(){
        return this.joueur;
    }
    
    public Tile getPdroite() {
        return this.Pdroite;
    }

    public Tile getPgauche() {
        return this.Pgauche;
    }

    public int getValeur(){
        return this.valeurPiece;
    }
  
    public ArrayList<Tile> getPieces(){
        ArrayList<Tile> piece = new ArrayList<>();
        piece.add(getPgauche());
        piece.add(getPdroite());
        return piece;
    }
    
    @Override
        public int compareTo(Domino emp) {
         //trions les employés selon leur age dans l'ordre croiddant
         //retroune un entier négative, zéro ou positive si l'age 
         //de cet employé est moins que, égale à ou supérieur à l'objet comparé avec
               return (this.valeurPiece - emp.valeurPiece);
        }
}
