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
    ArrayList<Domino> pioche = new ArrayList<Domino>();
    ArrayList<Domino> aJouer = new ArrayList<Domino>(4);
    ArrayList<Domino> aPiocher = new ArrayList<Domino>(4);
    Board plateau1;
    Board plateau2;
    View vue;
    
    public Controller (Board plateau1, Board plateau2, View vue){
        this.plateau1 = plateau1;
        this.plateau2 = plateau2;
        this.vue = vue;
        this.pioche = generatePiecePioche();
        for (int i = 0; i < 4; i++) {
            this.aJouer.add(null);
        }
        main();
    }
    
    private void main(){
        Integer jActuel;
        for(int i = 1; i < 3; i++){
            this.aPiocher = getPieceFromPioche();
            if(i==1){
                vue.affichePlateaux();
                int n;
                ArrayList<Integer> rois = new ArrayList<Integer>();
                rois.add(1);rois.add(1);rois.add(2);rois.add(2);
                while(rois.size() !=0){
                    vue.affichePioche(null,aPiocher);
                    n = new Random().nextInt(rois.size());
                    jActuel = rois.get(n);
                    
                    Integer choix = choixPiece(jActuel,aPiocher.size())-1;
                    while(aJouer.contains(aPiocher.get(choix))){
                        vue.invalidDomino();
                        choix = choixPiece(jActuel,aPiocher.size())-1;
                    }
                    Domino d = aPiocher.get(choix);
                    d.setPlayer(jActuel);
                    this.aJouer.set(choix,d);

                    rois.remove(n);
                }
            }else{
                for(Domino d : aJouer){
                    vue.affichePlateaux();
                    vue.affichePioche(this.aJouer,this.aPiocher);
                    jActuel = d.getPlayer();
                    ArrayList coo = choixPlacement(jActuel);
                    if(jActuel==1){
                        this.plateau1.addDomino(d,coo);
                    }else{
                        this.plateau2.addDomino(d,coo);
                    }
                    d.setPlayer(null);
                }
            }
        }
    }

    private ArrayList<Integer> choixPlacement(Integer player){
        vue.choixPlacement(player);
        ArrayList choix = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++){ 
            choix.add(choixCoordonnées());
        }
        return choix;
    }

    private Integer choixCoordonnées(){
        return choixValide(1,9)-1;
    }
    
    private Integer choixPiece(int joueur,int n){
        vue.choixPiece(joueur,n);
        return choixValide(1,n);
    }
    
    private ArrayList<Domino> generatePiecePioche(){
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
        public int choixValide(int borneInf, int borneSup){
		int choix = -1;
		while(choix == -1){
			Scanner choixScan = new Scanner(System.in);
			try{
				choix = choixScan.nextInt();
				while(choix < borneInf || choix > borneSup){
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
