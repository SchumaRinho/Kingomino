/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.Controleur;
import model.*;
import java.util.ArrayList;
import static model.Plateau.color;
/**
 *
 * @author Clémentine
 */
public class Vue {

    public static final String ANSI_RESET = "\u001B[0m";
    
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private Plateau plateau1, plateau2;
    private Controleur controleur;
    
    public Vue (Plateau plateau1,Plateau plateau2){
        this.plateau1 = plateau1;
        this.plateau2 = plateau2;
    }
    
    public void setPlateau(Plateau plateau1,Plateau plateau2){
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
    }

    public void affichePiece(Domino domino){
        System.out.print(color.get(domino.getPgauche().getType())+ANSI_WHITE+" "+domino.getPgauche().getCrown()+" "+ANSI_RESET+"|");
        System.out.println(color.get(domino.getPdroite().getType())+ANSI_WHITE+" "+domino.getPdroite().getCrown()+" "+ANSI_RESET);
    }

    public void choixPiece(int joueur, int n){
        System.out.println("Le joueur "+joueur+" doit choisir un domino [ entrez un nombre entre 1 et "+n+" ]");
    }

    public void affichePlateau(Plateau plateau){
        String tmp = "";
        for(int i = 0; i<9; i++){
            tmp+= ANSI_WHITE+ "|"+ANSI_RESET;
            for(int j = 0; j<9; j++){
                if(plateau.getPlateau().get(i*9+j) == null){
                    tmp+="   ";
                }else{
                    String key = plateau.getType(i*9+j);
                    if(key=="chateau"){
                        tmp+=ANSI_WHITE_BACKGROUND + " C " + ANSI_RESET;
                    }else{
                        tmp+=color.get(key);
                        if(plateau.getCrown(i*9+j, key) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=" "+ANSI_WHITE+plateau.getCrown(i*9+j, key)+" ";
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