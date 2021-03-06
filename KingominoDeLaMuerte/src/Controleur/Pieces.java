/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author leovi
 */
public class Pieces {

    HashMap <String, Integer> Pdroite = new HashMap <String, Integer>();
    HashMap <String, Integer> Pgauche = new HashMap <String, Integer>();
    
    public Pieces(HashMap <String, Integer> Pgauche, HashMap <String, Integer> Pdroite){
        Pgauche = this.Pgauche;
        Pdroite = this.Pdroite;
    }
    
    public HashMap<String, Integer> getPdroite() {
        HashMap contenuPiece = new HashMap<String, Integer>();
        int tmpval = 0;
        String tmptype = "";
        for(Integer i : this.Pdroite.values()){
            tmpval = i;
        }
        Integer.toString(tmpval);
        for(String j : this.Pdroite.keySet()){
            tmptype = j;
        }
        contenuPiece.put(tmptype,tmpval);
        return contenuPiece;
    }

    public void setPdroite(HashMap <String,Integer> Pdroite) {
        this.Pdroite = Pdroite;
    }

    public HashMap<String, Integer> getPgauche() {
        HashMap contenuPiece = new HashMap<String, Integer>();
        int tmpval = 0;
        String tmptype = "";
        for(Integer i : this.Pgauche.values()){
            tmpval = i;
        }
        Integer.toString(tmpval);
        for(String j : this.Pgauche.keySet()){
            tmptype = j;
        }
        contenuPiece.put(tmptype,tmpval);
        return contenuPiece;
    }

    public void setPgauche(HashMap <String,Integer> Pgauche) {
        this.Pgauche = Pgauche;
    }
    
    public ArrayList<HashMap<String,Integer>> getPieces(){
        ArrayList<HashMap<String,Integer>> piece = new ArrayList<HashMap<String,Integer>>();
        piece.add(getPdroite());
        piece.add(getPgauche());
        return piece;

    }
}
