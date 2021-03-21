/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import model.*;
import java.util.*;
import static model.Game.color;

/**
 *
 * @author Clémentine
 */
public class View {

    public static final String ANSI_RESET = "\u001B[0m";
    
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private Game game;
    private Controller controller;
    
    public View(Game game){
        this.game = game;
    }

    // Print of state

    public void printGame(){
        System.out.println("Plateau joueur 1");
        printBoard(game.getBoard(1));
        System.out.println("Plateau joueur 1");
        printBoard(game.getBoard(2));
    }

    public void printBoard(Board board){
        String tmp = "";
        for(int z = 1; z<10; z++){
                System.out.print("   "+z);
        }
        System.out.println("");
        for(int i = 0; i<9; i++){
            tmp+=i+1 + ANSI_WHITE+ "|"+ANSI_RESET;
            for(int j = 0; j<9; j++){
                if(board.getTile(i,j) == null){
                    tmp+="   ";
                }else{
                    String key = board.getFieldType(i,j);
                    if(key=="chateau"){
                        tmp+=ANSI_WHITE_BACKGROUND + " C " + ANSI_RESET;
                    }else{
                        tmp+=color.get(key);
                        if(board.getCrown(i,j) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=" "+ANSI_WHITE+board.getCrown(i,j)+" ";
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

    public void printDeck(){
        ArrayList<Domino> toPlay = this.game.toPlay;
        ArrayList<Domino> toChoose = this.game.toChoose;
        if(toPlay.get(0) != null && toPlay.get(1) != null && toPlay.get(2) != null && toPlay.get(3) != null){
            for(int i = 0;i < toPlay.size(); i++){
                printDomino(toPlay.get(i));
                System.out.print("   ");
                printDomino(toChoose.get(i));
                System.out.println("");
            }
        }
        else{
            for(Domino domino : toChoose){
                printDomino(domino);
                System.out.println("");
            }
        }
    }

    public void printDomino(Domino domino){
        System.out.print(color.get(domino.getTileL().getType())+ANSI_WHITE+" "+domino.getTileL().getCrown()+" "+ANSI_RESET+"|");
        System.out.print(color.get(domino.getTileR().getType())+ANSI_WHITE+" "+domino.getTileR().getCrown()+" "+ANSI_RESET);
        if (domino.getPlayer()==null){
            System.out.print("    ");
        }else{
            System.out.print(" ["+domino.getPlayer()+"]");
        }
    }

    public void printPlayerTurn(Integer currentPlayer){
        System.out.println("C'est le tour du joueur " + currentPlayer);
    }

    public void printScore(){
        System.out.println("Fin de la partie  !");
        System.out.println("Scores : ");
        System.out.println("Joueur 1 : " + this.game.getScore(1));
        System.out.println("Joueur 2 : " + this.game.getScore(2));
    }

    // Print from controller [static]

    public static void printAiChoice(){
        System.out.println("Voulez-vous jouer contre une ia ?    [0: Non / 1: Oui]");
    }

    public static void printDominoChoice(){
        System.out.println("Entrez un nombre entre 1 et 4 pour choisir un domino");
    }

    public static void printOutOfBoundary(int inf, int sup){
        System.out.println("Veuillez donner une valeur entre " + inf + " et " + sup);
    }

    public static void printNotAvailable(){
        System.out.println("Ce choix n'est pas possible");
    }

}