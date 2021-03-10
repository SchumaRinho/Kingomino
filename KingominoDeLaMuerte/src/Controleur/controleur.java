/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import model.*;
import Vue.*;
import java.util.*;

/**
 *
 * @author leovi
 */
public class Controleur {
    ArrayList<Pieces> pioche= new ArrayList<Pieces>();
    Plateau plateau1;
    Plateau plateau2;
    Vue vue;
    
    public Controleur (Plateau plateau1, Plateau plateau2, Vue vue){
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
    
    private ArrayList<Pieces> generatePiecePioche(){
        int numPieces=1;
        ArrayList pioche = new ArrayList<Pieces>();
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
                pioche.add(generatorPiece("champs 0","forêt 0",numPieces++));
                pioche.add(generatorPiece("champs 0","mer 0",numPieces++));
                pioche.add(generatorPiece("champs 0","plaine 0",numPieces++));
                pioche.add(generatorPiece("champs 0","marécage 0",numPieces++));
                pioche.add(generatorPiece("forêt 0","mer 0",numPieces++));
                pioche.add(generatorPiece("forêt 0","plaine 0",numPieces++));
                pioche.add(generatorPiece("champs 1","forêt 0",numPieces++));
                pioche.add(generatorPiece("champs 1","mer 0",numPieces++));
                pioche.add(generatorPiece("champs 1","plaine 0",numPieces++));
                pioche.add(generatorPiece("champs 1","marécage 0",numPieces++));
                pioche.add(generatorPiece("champs 1","mine 0",numPieces));
            }
            else if(numPieces<=27)
                pioche.add(generatorPiece("forêt 1","champs 0",numPieces));
            else if(numPieces<=31){
                pioche.add(generatorPiece("forêt 1","mer 0",numPieces++));
                pioche.add(generatorPiece("forêt 1","plaine 0",numPieces++));
                pioche.add(generatorPiece("mer 1","champs 0",numPieces++));
                pioche.add(generatorPiece("mer 1","champs 0",numPieces));
            }
            else if(numPieces<=35)
                pioche.add(generatorPiece("mer 1","forêt 0",numPieces));
            else if(numPieces<=48){
                pioche.add(generatorPiece("champs 0","plaine 1",numPieces++));
                pioche.add(generatorPiece("mer 0","plaine 1",numPieces++));
                pioche.add(generatorPiece("champs 0","marécage 1",numPieces++));
                pioche.add(generatorPiece("plaine 0","marécage 1",numPieces++));
                pioche.add(generatorPiece("mine 1","champs 0",numPieces++));
                pioche.add(generatorPiece("champs 0","plaine 2",numPieces++));
                pioche.add(generatorPiece("mer 0","plaine 2",numPieces++));
                pioche.add(generatorPiece("champs 0","marécage 2",numPieces++));
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
        pDroite.put(typeD.substring(0,typeD.indexOf(' ')), Integer.parseInt(typeD.substring(typeD.indexOf(' ')+1)));
        return new Pieces(pGauche,pDroite,val);
    }
    public ArrayList<Pieces> getPieceFromPioche(){
        ArrayList<Pieces> tirage = new ArrayList<Pieces>();
        int i=0;
        while(i<4){
            int sum = pioche.size();
            int random = new Random().nextInt(sum);
            tirage.add(pioche.get(random));
            pioche.remove(random);
            i++;
        }
        // AJOUT D'UN TRI CROISSANT NECESSAIRE /!\
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
    public ArrayList<Pieces> getPioche(){
        return this.pioche;
    }
}
