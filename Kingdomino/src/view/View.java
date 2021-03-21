/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import model.*;
import java.util.ArrayList;
import static model.Board.color;
/**
 *
 * @author Clémentine
 */
public class View {

    public static final String ANSI_RESET = "\u001B[0m";
    
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private Board plateau1, plateau2;
    private Controller controleur;
    
    public View (Board plateau1,Board plateau2){
        this.plateau1 = plateau1;
        this.plateau2 = plateau2;
    }
    
    public void setPlateau(Board plateau1,Board plateau2){
        this.plateau1 = plateau1;
        this.plateau2 = plateau2;
    }

    public void affichePlateaux(){
        System.out.println("Plateau joueur 1");
        affichePlateau(this.plateau1);
        System.out.println("Plateau joueur 2");
        affichePlateau(this.plateau2);
    }

    public void affichePioche(ArrayList<Domino> pieceAJouer, ArrayList<Domino> pioche){
        if(pieceAJouer != null){
            for(int i = 0;i < pioche.size(); i++){
                affichePiece(pieceAJouer.get(i));
                System.out.print("   ");
                affichePiece(pioche.get(i));
                System.out.println("");
            }
        }
        else{
            for(Domino p : pioche){
                affichePiece(p);
                System.out.println("");
            }
        }
    }

    public void choixPlacement(int joueur){
        System.out.println("Le joueur "+joueur+" doit choisir les coordonnées de la pièce y,x (exemple : 1,2) [abscisse et ordonées entre 1 et 9]");
    }
    
    public void indicPlacement(int n){
        if(n==0)
            System.out.println("Choisissez les coordonées de la tuile gauche");
        else
            System.out.println("Choisissez les coordonées de la tuile droite"); 
    }

    public void choixPiece(int joueur, int n){
        System.out.println("Le joueur "+joueur+" doit choisir un domino [ entrez un nombre entre 1 et "+n+" ]");
    }

    public void choixJeuIA(){
        System.out.println("Voulez-vous jouer contre une ia ?    [0: Non / 1: Oui]");
    }

    public void choixIA(int choixia){
        System.out.println("L'ia a choisi la pièce n° " + choixia);
    }

    public void invalidDomino(){
        System.out.println("Domino invalide");
    }

    public void invalidPlacement(String err){
        System.out.println("Le domino ne peut être placé ici : "+err);
    }
    
    public void impossiblePlacement(){
        System.out.println("Le domino n'a aucun placement possible, il est défaussé");
    }

    public void finPartie(int score1, int score2){
        System.out.println("Score du joueur : " + score1 + "\nScore de l'IA : "+score2);
        if(score1>score2){
            System.out.println("Victoire du joueur");
        }else{
            System.out.println("Défaite du joueur");
        }
    }

    public void affichePiece(Domino domino){
        System.out.print(color.get(domino.getPgauche().getType())+ANSI_WHITE+" "+domino.getPgauche().getCrown()+" "+ANSI_RESET+"|");
        System.out.print(color.get(domino.getPdroite().getType())+ANSI_WHITE+" "+domino.getPdroite().getCrown()+" "+ANSI_RESET);
        if (domino.getPlayer()==null){
            System.out.print("    ");
        }else{
            System.out.print(" ["+domino.getPlayer()+"]");
        }
    }
            
    public void affichePlateau(Board plateau){
        String tmp = "";
        for(int z = 1; z<10; z++){
                System.out.print("   "+z);
        }
        System.out.println("");
        for(int i = 0; i<9; i++){
            tmp+=i+1 + ANSI_WHITE+ "|"+ANSI_RESET;
            for(int j = 0; j<9; j++){
                if(plateau.getTile(i,j) == null){
                    tmp+="   ";
                }else{
                    String key = plateau.getFieldType(i,j);
                    if(key=="chateau"){
                        tmp+=ANSI_WHITE_BACKGROUND + " C " + ANSI_RESET;
                    }else{
                        tmp+=color.get(key);
                        if(plateau.getCrown(i,j) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=" "+ANSI_WHITE+plateau.getCrown(i,j)+" ";
                        }
                        tmp+=ANSI_RESET;
                    }
                }
                tmp+=ANSI_WHITE+"|"+ANSI_RESET;
            }
            tmp+="\n\n";
        }
        System.out.println(tmp);
             
	}
}
