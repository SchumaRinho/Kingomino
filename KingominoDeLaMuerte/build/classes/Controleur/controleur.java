/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author leovi
 */
public class controleur {
     HashMap<String,Integer> listePiece= new HashMap<String,Integer>();
    
    public controleur (){
        this.listePiece = listePiece;
        main();
    }
    
    private void main(){
        System.out.println(generatePiecePioche());
        System.out.println(generatorPieces().getPieces());
        System.out.println(generatorPieces().getPieces());
        System.out.println(generatorPieces().getPieces());
        System.out.println(listePiece);
    }
    
    private HashMap<String,Integer> generatePiecePioche(){
        for(int i=1; i<=96; i++){
            listePiece.put("champs 0", 21);
            listePiece.put("champs 1", 5);
            listePiece.put("plaine 0", 10);
            listePiece.put("plaine 1", 2);
            listePiece.put("plaine 2", 2);
            listePiece.put("mer 0", 12);
            listePiece.put("mer 1", 6);
            listePiece.put("roche 0", 6);
            listePiece.put("roche 1", 2);
            listePiece.put("roche 2", 2);
            listePiece.put("foret 0", 16);
            listePiece.put("foret 1", 6);
            listePiece.put("mine 0", 1);
            listePiece.put("mine 1", 1);
            listePiece.put("mine 2", 3);
            listePiece.put("mine 3", 1);
        }
        return listePiece;
    }
    private Pieces generatorPieces(){
        Pieces newPieces = new Pieces(new HashMap<String,Integer>(),new HashMap<String,Integer>());
        boolean pGauche = false;
        boolean pDroite = false;
        ArrayList<String> pioche = new ArrayList<String>();
        for(String i : listePiece.keySet())
            for(int j=1; j<=listePiece.get(i);j++)
                pioche.add(i);
        while(!pDroite){
            int sum = pioche.size();
            int random = new Random().nextInt(sum+1)+1;
            if(pGauche){
                newPieces = update(pioche.get(random),pGauche,newPieces);
                pioche.remove(random);
                pDroite = true;
            }
            while(!pGauche){
                newPieces = update(pioche.get(random),pGauche,newPieces);
                pioche.remove(random);
                pGauche = true;
            }
        }
        return newPieces;
    }
    
    private Pieces update(String type, boolean pGauche, Pieces piecesToUpdate){
        HashMap <String,Integer> infoPieces = new HashMap<String,Integer>();
        int nombre = Integer.parseInt(type.substring(type.indexOf(' ')+1));
        infoPieces.put(type.substring(0,type.indexOf(' ')),nombre);
        if(!pGauche){
            piecesToUpdate.setPgauche(infoPieces);
        }
        else{
            piecesToUpdate.setPdroite(infoPieces);   
        }
        for (String j : listePiece.keySet()){
            if(j==type)
                listePiece.put(j,listePiece.get(j)-1);
        }
        return piecesToUpdate;
    }
}
