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

    public void affichePioche(ArrayList<Domino> pioche){
        for(Domino p : pioche){
            affichePiece(p);
            System.out.println("");
        }
        System.out.println("");
    }

    public void affichePiece(Domino domino){
        System.out.print(color.get(domino.getPgauche().getType())+ANSI_WHITE+" "+domino.getPgauche().getCrown()+" "+ANSI_RESET+"|");
        System.out.println(color.get(domino.getPdroite().getType())+ANSI_WHITE+" "+domino.getPdroite().getCrown()+" "+ANSI_RESET);
    }

    public void choixPiece(int joueur, int n){
        System.out.println("Le joueur "+joueur+" doit choisir un domino [ entrez un nombre entre 1 et "+n+" ]");
    }
            
    public void affichePlateau(Board plateau){
        String tmp = "";
        for(int i = 0; i<9; i++){
            tmp+= ANSI_WHITE+ "|"+ANSI_RESET;
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
