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
     ArrayList<Pieces> pioche= new ArrayList<Pieces>();
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
    
    private ArrayList<Pieces> generatePiecePioche(){
        int numPieces=1;
        while(numPieces<49){
            if(numPieces<=2){
                pioche.add(generatorPiece("champs 0","champs 0",numPieces));
            }
            else if(numPieces<=6)
                pioche.add(generatorPiece("forêt 0","forêt 0",numPieces));                
            else if(numPieces<=9)
                pioche.add(generatorPiece("mer 0","mer 0",numPieces));                
            else if(numPieces<=11)
                pioche.add(generatorPiece("plaine 0","plaine 0",numPieces));
            else if(numPieces<=12)
                pioche.add(generatorPiece("marécage 0","marécage 0",numPieces));
            else if(numPieces<=13){
                pioche.add(generatorPiece("champs 0","forêt 0",numPieces));
                pioche.add(generatorPiece("champs 0","mer 0",numPieces++));
                pioche.add(generatorPiece("champs 0","plaine 0",numPieces++));
                pioche.add(generatorPiece("champs 0","marécage 0",numPieces++));
                pioche.add(generatorPiece("forêt 0","mer 0",numPieces++));
                pioche.add(generatorPiece("forêt 0","plaine 0",numPieces++));
                pioche.add(generatorPiece("champs 1","forêt 0",numPieces++));
                pioche.add(generatorPiece("champs 1","mer 0",numPieces++));
                pioche.add(generatorPiece("champs 1","plaine 0",numPieces++));
                pioche.add(generatorPiece("champs 1","marécage 0",numPieces++));
                pioche.add(generatorPiece("champs 1","mine 0",numPieces++));
            }
            else if(numPieces<=27)
                pioche.add(generatorPiece("forêt 1","champs 0",numPieces));
            else if(numPieces<=31){
                pioche.add(generatorPiece("forêt 1","mer 0",numPieces));
                pioche.add(generatorPiece("forêt 1","plaine 0",numPieces++));
                pioche.add(generatorPiece("mer 1","champs 0",numPieces++));
                pioche.add(generatorPiece("mer 1","champs 0",numPieces++));
            }
            else if(numPieces<=35)
                pioche.add(generatorPiece("mer 1","forêt 0",numPieces));
            else if(numPieces<=48){
                pioche.add(generatorPiece("forêt 1","mer 0",numPieces));
                pioche.add(generatorPiece("champs 0","plaine 1",numPieces++));
                pioche.add(generatorPiece("plaine 1","marécage 1",numPieces++));
                pioche.add(generatorPiece("mine 1","champs 0",numPieces++));
                pioche.add(generatorPiece("champs 0","marécage 2",numPieces++));
                pioche.add(generatorPiece("mer 0","plaine 1",numPieces++));
                pioche.add(generatorPiece("champs 0","marécage 1",numPieces++));
                pioche.add(generatorPiece("champs 0","plaine 2",numPieces++));
                pioche.add(generatorPiece("mer 0","plaine 2",numPieces++));
                pioche.add(generatorPiece("plaine 0","marécage 2",numPieces++));
                pioche.add(generatorPiece("mine 2","champs 0",numPieces++));
                pioche.add(generatorPiece("marécage 0","mine 2",numPieces++));
                pioche.add(generatorPiece("marécage 0","mine 2",numPieces++));
                pioche.add(generatorPiece("champs 0","mine 3",numPieces++));
            }
            numPieces++;
        }
        return pioche;
    }
    
    private Pieces generatorPiece(String typeG, String typeD, int val){
        HashMap<String,Integer> pGauche = new HashMap<String,Integer>();
        pGauche.put(typeG.substring(0,typeG.indexOf(' ')), Integer.parseInt(typeG.substring(typeG.indexOf(' ')+1))); //typeG = "champs 0"
        HashMap<String,Integer> pDroite = new HashMap<String,Integer>();
        pDroite.put(typeD.substring(0,typeD.indexOf(' ')), Integer.parseInt(typeG.substring(typeG.indexOf(' ')+1)));
        return new Pieces(pGauche,pDroite,0);
    }
    public ArrayList<Pieces> getPieceFromPioche(){
        ArrayList<Pieces> tirage = new ArrayList<Pieces>();
        int i=0;
        while(i<4){
            int sum = pioche.size();
            System.out.println(sum);
            int random = new Random().nextInt(sum);
            System.out.println(random);
            tirage.add(pioche.get(random));
            pioche.remove(random);
            i++;
        }
        return tirage;
    }
    /*
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
        for (String j : pioche.keySet()){
            if(j==type)
                pioche.put(j,pioche.get(j)-1);
        }
        return piecesToUpdate;
    }
    */
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
    public ArrayList<Pieces> getPioche(){
        return this.pioche;
    }
}
