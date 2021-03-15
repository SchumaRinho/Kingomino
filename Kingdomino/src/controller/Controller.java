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
    private Board plateau1;
    private Board plateau2;
    private View vue;
    private int score = 0;
    private ArrayList<Integer> tileTraveled = new ArrayList<>();
    private AIRandom aiRandom;
    private boolean ai;
    
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
        System.out.println("Voulez-vous jouer contre une ia ?");
        if(choixValide(0,1,"0: Non / 1: Oui")==1){
            aiRandom = new AIRandom(plateau2);
            ai=true;
        }
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
                    if(!ai){
                        vue.choixPiece(jActuel,pioche.size());
                        pioche.remove(choixPiece(jActuel,pioche.size())-1);
                    }
                    else{
                        if(jActuel==2){
                            int choixia = aiRandom.getChoix(1,pioche.size());
                            pioche.remove(choixia-1);
                            System.out.println("L'ia a choisi la pièce n° " + choixia);
                        }   
                        else{
                            vue.choixPiece(jActuel,pioche.size());
                            pioche.remove(choixPiece(jActuel,pioche.size())-1);
                        }
                    }
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
    
    private int calcScore(Board plateau){
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                if(plateau.getTile(i,j)!=null && plateau.getCrown(i,j)!=0 && !this.tileTraveled.contains(i*9+j))
                    score += scoreField(plateau,plateau.getTile(i,j),i,j);
            }
        }
        return score;
    }
    
    private int scoreField(Board plateau, Tile tile, int i, int j){
        ArrayList<Integer> infoField = new ArrayList<>();
        infoField.add(0);infoField.add(0);
        infoField= nextTile(plateau,tile,i,j,0, infoField);  // calcul.get(0) -> nombre de tuile identique
        return infoField.get(0)*infoField.get(1);            // calcul.get(1) -> nombre de couronne sur la zonne
        
    }
    
    private ArrayList<Integer>nextTile(Board plateau, Tile tile, int i,int j, int prevMove, ArrayList<Integer> infoField){
        tileTraveled.add(i*9+j);                                                                //historique des tuiles parcourues
        infoField.set(0, infoField.get(0)+1);                                                   //On ajoute 1 au compteur de tuile identique
        if(tile.getCrown()!=0)                                                                  // si il y a des couronnes sur la tuile, on l'ajoute dans le total des couronnes
            infoField.set(1, infoField.get(1)+tile.getCrown());
        if(i>0 && plateau.getTile(i-1,j)!=null && tile.getType() == plateau.getFieldType(i-1,j) && prevMove!=1){ //On veut parcourir l'ensemble des tile composant le terrain à calculer
            infoField = nextTile(plateau, plateau.getTile(i-1,j), i-1,j,3,infoField);                            //(ici on monte avec i-1), 
        }                                                                                                        //prevMove permet d'indiquer le mouvement qu'on a fait pour éviter de calculer 2 fois une même tuile 
        if(j!=8 && plateau.getTile(i,j+1)!=null && tile.getType() == plateau.getFieldType(i,j+1) && prevMove!=2){
            infoField = nextTile(plateau, plateau.getTile(i,j+1),i,j+1,4, infoField);
        }
        if(i<9 && plateau.getTile(i+1,j)!=null && tile.getType() == plateau.getFieldType(i+1,j) && prevMove!=3){
            infoField = nextTile(plateau, plateau.getTile(i+1,j), i+1,j,1,infoField);
        }
        if(j!=0 && plateau.getTile(i,j-1)!=null && tile.getType() == plateau.getFieldType(i,j-1) && prevMove!=4){
            infoField = nextTile(plateau, plateau.getTile(i,j-1),i,j-1,2,infoField);
        }
        return infoField;
    }
    /*
    private ArrayList<Integer> listPlacement(Domino newDomino){
        ArrayList<Integer> listPlacement = new ArrayList<Integer>();
    }*/
    
    private boolean verifPlacement(Board plateau){
        int iMini=100,iMax=0,jMini=100,jMax=0;  //pour vérifier qu'on soit dans un tableau de 5x5
            for(int i = 0; i<9; i++){           //On récupere le plus petit i et j, et on les soustrait au plus grand i et j 
                for(int j = 0; j<9; j++){
                    if(plateau.getTile(i,j)!=null){
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
