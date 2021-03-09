/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import model.*;
import Controleur.*;
import static model.Plateau.ANSI_BLACK_BACKGROUND;
import static model.Plateau.ANSI_BLUE_BACKGROUND;
import static model.Plateau.ANSI_CYAN_BACKGROUND;
import static model.Plateau.ANSI_GREEN_BACKGROUND;
import static model.Plateau.ANSI_PURPLE_BACKGROUND;
import static model.Plateau.ANSI_RED_BACKGROUND;
import static model.Plateau.ANSI_RESET;
import static model.Plateau.ANSI_WHITE;
import static model.Plateau.ANSI_WHITE_BACKGROUND;
import static model.Plateau.ANSI_YELLOW_BACKGROUND;

/**
 *
 * @author Clémentine
 */
public class Vue {

    private Plateau plateau;
    private Controleur controleur;
    
    public Vue (Plateau plateau){
        this.plateau = plateau;
    }
    
    public void setPlateau(Plateau plateau){
        this.plateau = plateau;
    }
    
    public void affichage(Controleur controleur){
        this.controleur = controleur;
        System.out.println("Si vous voulez faire un choix , tapez 1");
        if(controleur.choixValide(1,1,"Tapez 1 c'est un ordre !")==1){
            System.out.println(controleur.getPieceFromPioche());
            System.out.println(controleur.getPioche());
            plateau.plateauTest();
            affichePlateau();
        }   
    }
    
    public void affichePlateau(){
        String tmp = "";
        for(int i = 0; i<9; i++){
            tmp+= ANSI_WHITE+ "|"+ANSI_RESET;
            for(int j = 0; j<9; j++){
                if(plateau.getPlateau().get(i*9+j) == null){
                    tmp+="   ";
                }else{
                    String key = plateau.getKey(i*9+j);
                    if(key=="chateau"){
                        tmp+=ANSI_WHITE_BACKGROUND + " C " + ANSI_RESET;
                    }else if(key=="marécage"){
                        tmp+=ANSI_CYAN_BACKGROUND;
                        if(plateau.getCrown(i*9+j, key) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=" "+plateau.getCrown(i*9+j, key)+" ";
                        }
                        tmp+=ANSI_RESET;
                    }else if(key=="mer"){
                        tmp+=ANSI_BLUE_BACKGROUND;
                        if(plateau.getCrown(i*9+j, key) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=ANSI_WHITE + " "+plateau.getCrown(i*9+j, key)+" ";
                        }
                        tmp+=ANSI_RESET;
                    }else if(key=="plaine"){
                        tmp+=ANSI_GREEN_BACKGROUND;
                        if(plateau.getCrown(i*9+j, key) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=" "+plateau.getCrown(i*9+j, key)+" ";
                        }
                        tmp+=ANSI_RESET;
                    }else if(key=="champs"){
                        tmp+=ANSI_YELLOW_BACKGROUND;
                        if(plateau.getCrown(i*9+j, key) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=" "+plateau.getCrown(i*9+j, key)+" ";
                        }
                        tmp+=ANSI_RESET;
                    }else if(key=="forêt"){
                        tmp+=ANSI_PURPLE_BACKGROUND;
                        if(plateau.getCrown(i*9+j, key) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=ANSI_WHITE + " "+plateau.getCrown(i*9+j, key)+" ";
                        }
                        tmp+=ANSI_RESET;
                    }else if(key=="mine"){
                        tmp+=ANSI_RED_BACKGROUND;
                        if(plateau.getCrown(i*9+j, key) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=ANSI_WHITE + " "+plateau.getCrown(i*9+j, key)+" ";
                        }
                        tmp+=ANSI_RESET;
                    }else{
                        tmp+=ANSI_BLACK_BACKGROUND+" "+plateau.getCrown(i*9+j, key)+" "+ANSI_RESET;
                    }
                }
                tmp+=ANSI_WHITE+"|"+ANSI_RESET;
            }
            tmp+="\n\n";
        }
        System.out.println(tmp);
             
	}
}
