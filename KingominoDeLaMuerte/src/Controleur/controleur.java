/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import model.*;
import Vue.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author leovi
 */
public class Controleur {
     HashMap<String,Integer> listePiece= new HashMap<String,Integer>();
     Plateau plateau = new Plateau();
     Vue vue = new Vue(plateau);
    
    public Controleur (Plateau plateau, Vue vue){
        this.plateau = plateau;
        this.vue = vue;
        generatePiecePioche();
        main();
    }
    
    private void main(){
        vue.affichage(this);
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
            listePiece.put("marécage 0", 6);
            listePiece.put("marécage 1", 2);
            listePiece.put("marécage 2", 2);
            listePiece.put("forêt 0", 16);
            listePiece.put("forêt 1", 6);
            listePiece.put("mine 0", 1);
            listePiece.put("mine 1", 1);
            listePiece.put("mine 2", 3);
            listePiece.put("mine 3", 1);
        }
        return listePiece;
    }
    public Pieces generatorPieces(){
        Pieces newPieces = new Pieces(new HashMap<>(),new HashMap<>());
        boolean pGauche = false;
        boolean pDroite = false;
        ArrayList<String> pioche = new ArrayList<>();
        for(String i : listePiece.keySet())
            for(int j=1; j<=listePiece.get(i);j++)
                pioche.add(i);
        while(!pDroite){
            int sum = pioche.size();
            int random = new Random().nextInt(sum);
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
        HashMap <String,Integer> infoPieces = new HashMap<>();
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
    
        public int choixValide(int borneInf, int borneSup, String texte){
		int choix = -1;
		while(choix == -1){
			Scanner choixScan = new Scanner(System.in);
			try{
				choix = choixScan.nextInt();
				while(choix < borneInf || choix > borneSup){
					System.out.println(texte);
					choix = choixScan.nextInt();
				}
			}
			catch(Exception e){
				System.out.println("Choix invalide");
			}
		}
		return choix;
    }
    public HashMap<String,Integer> getListePiece(){
        return this.listePiece;
    }
}
