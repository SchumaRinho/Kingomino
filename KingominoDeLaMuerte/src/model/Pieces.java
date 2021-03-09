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
public class Pieces {

    HashMap <String, Integer> Pdroite = new HashMap <String, Integer>();
    HashMap <String, Integer> Pgauche = new HashMap <String, Integer>();
    int valeurPiece;
    
    public Pieces(HashMap <String, Integer> Pgauche, HashMap <String, Integer> Pdroite, int valeur){
        this.Pgauche = Pgauche;
        this.Pdroite = Pdroite;
            this.valeurPiece = valeur;
    }
    
    public HashMap<String, Integer> getPdroite() {
        return this.Pdroite;
    }

    public HashMap<String, Integer> getPgauche() {
        return this.Pgauche;
    }
  
    public ArrayList<HashMap<String,Integer>> getPieces(){
        ArrayList<HashMap<String,Integer>> piece = new ArrayList<>();
        piece.add(getPgauche());
        piece.add(getPdroite());
        return piece;
    }
}
