/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.View;
import model.*;
import java.util.*;

/**
 *
 * @author leovi
 */
public class Controller {
    ArrayList<Domino> pioche= new ArrayList<Domino>();
    Board plateau1;
    Board plateau2;
    View vue;
    
    public Controller (Board plateau1, Board plateau2, View vue){
        this.plateau1 = plateau1;
        this.plateau2 = plateau2;
        this.vue = vue;
        this.pioche = generatePiecePioche();
        main();
    }
    
    private void main(){
        vue.affichePlateaux();
        Queue domJ1 = new LinkedList<Integer>();
        Queue domJ2 = new LinkedList<Integer>();
        for(int i = 1; i < 2; i++){
            ArrayList pioche = getPieceFromPioche();
            if(i==1){
                int joueur = 1;
                Integer jActuel;
                int n;
                ArrayList rois = new ArrayList<Integer>();
                rois.add(1);rois.add(1);rois.add(2);rois.add(2);
                while(rois.size() !=0){
                    vue.affichePioche(pioche);
                    n = new Random().nextInt(rois.size());
                    jActuel =  Integer.parseInt(rois.get(n).toString()); 
                    vue.choixPiece(jActuel,pioche.size());
                    pioche.remove(choixPiece(jActuel,pioche.size())-1);
                    rois.remove(n);
                }
            }else{
                
            }
        }
    }

    private Integer choixPiece(int joueur,int n){
        return choixValide(1,n,"Choisissez un nombre entre 1 et " + n + " !");
    }
    
    private ArrayList<Domino> generatePiecePioche(){
        int numPieces=1;
        generatorPiece(new Tile("champs",0),new Tile("champs",0),2);
        generatorPiece(new Tile("forêt",0),new Tile("forêt",0),4);
        generatorPiece(new Tile("mer",0),new Tile("mer",0),3);                
        generatorPiece(new Tile("plaine",0),new Tile("plaine",0),2);                
        generatorPiece(new Tile("marécage",0),new Tile("marécage",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("forêt",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("mer",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("plaine",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("marécage",0),1);                
        generatorPiece(new Tile("forêt",0),new Tile("mer",0),1);                
        generatorPiece(new Tile("forêt",0),new Tile("plaine",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("forêt",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("mer",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("plaine",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("marécage",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("mine",0),1);                
        generatorPiece(new Tile("forêt",1),new Tile("champs",0),4);               
        generatorPiece(new Tile("forêt",1),new Tile("mer",0),1);                
        generatorPiece(new Tile("forêt",1),new Tile("plaine",0),1);                
        generatorPiece(new Tile("mer",1),new Tile("champs",0),2);                
        generatorPiece(new Tile("mer",1),new Tile("forêt",0),4);                
        generatorPiece(new Tile("champs",0),new Tile("plaine",1),1);                
        generatorPiece(new Tile("mer",0),new Tile("plaine",1),1);                    
        generatorPiece(new Tile("champs",0),new Tile("marécage",1),1);                
        generatorPiece(new Tile("plaine",0),new Tile("marécage",1),1);                
        generatorPiece(new Tile("mine",1),new Tile("champs",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("plaine",2),1);                
        generatorPiece(new Tile("mer",0),new Tile("plaine",2),1);                
        generatorPiece(new Tile("champs",0),new Tile("marécage",2),1);                
        generatorPiece(new Tile("plaine",0),new Tile("marécage",2),1);                
        generatorPiece(new Tile("mine",2),new Tile("champs",0),1);                
        generatorPiece(new Tile("marécage",0),new Tile("mine",2),2);                
        generatorPiece(new Tile("champs",0),new Tile("mine",3),1);                
        return pioche;
    }
    
    private void generatorPiece(Tile Gauche, Tile Droite, int nb){
        int num_courant = pioche.size();
        for(int i=0;i<nb;i++){
            Domino p = new Domino(Gauche,Droite,num_courant++);
            pioche.add(p);
        }
    }
    
    public ArrayList<Domino> getPieceFromPioche(){
        ArrayList<Domino> tirage = new ArrayList<Domino>();
        for(int i=0;i<4;i++){
            int sum = pioche.size();
            int random = new Random().nextInt(sum);
            tirage.add(pioche.get(random));
        }
        Collections.sort(tirage);
        return tirage;
    }
    /* Obsolete
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
    public ArrayList<Domino> getPioche(){
        return this.pioche;
    }
}
