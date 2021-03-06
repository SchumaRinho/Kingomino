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
        while(!pGauche){
            int sum = pioche.size();
            int random = new Random().nextInt(sum+1)+1;
            int random2 = new Random().nextInt(sum+1)+1;
            if(pDroite){
                System.out.println("test1.5");
                System.out.println(pioche.get(random2));
                newPieces = update(pioche.get(random2),pDroite);
                pioche.remove(random2);
                pGauche = true;
            }
            while(!pDroite){
                System.out.println("test0");
                newPieces = update(pioche.get(random),pDroite);
                pioche.remove(random);
                pDroite = true;
            }
        }
        return newPieces;
    }
    
    private Pieces update(String type, boolean pDroite){
        Pieces newPieces = new Pieces(new HashMap<String,Integer>(),new HashMap<String,Integer>());
        HashMap infoPieces = new HashMap<String,Integer>();
        int nombre = Integer.parseInt(type.substring(type.indexOf(' ')+1));
        infoPieces.put(type,nombre);
        if(!pDroite){
            System.out.println("test1");
            newPieces.setPdroite(infoPieces);
        }
        else{
            System.out.println("test2");
            newPieces.setPgauche(infoPieces);           
        }
        for (String j : listePiece.keySet()){
            if(j==type)
                listePiece.put(j,listePiece.get(j)-1);
        }
        return newPieces;
    }
}
