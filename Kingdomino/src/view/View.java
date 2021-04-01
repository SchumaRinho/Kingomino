/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.*;
import java.util.*;
import static model.Game.color;

/**
 * Classe représentant la Vue de notre projet.
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public class View {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private final Game game;
    
    /**
     * Constructeur View.
     * @param game
     */
    public View(Game game){
        this.game = game;
    }


    /**
     * Permet d'afficher les deux plateaux du jeu
     */
    public void printGame(){
        System.out.println("Plateau joueur 1");
        printBoard(game.getBoard(1));
        System.out.println("Plateau joueur 2");
        printBoard(game.getBoard(2));
    }

    /**
     * Permet d'afficher un plateau
     * @param board
     */
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

    /**
     * permet d'afficher la liste des dominos a jouer, et des dominos a choisir
     */
    public void printDeck(){
        ArrayList<Domino> toPlay = this.game.getToPlay();
        ArrayList<Domino> toChoose = this.game.getToChoose();

        for(int i = 0; i < toPlay.size(); i++){
            if(toPlay.get(0) != null && toPlay.get(1) != null && toPlay.get(2) != null && toPlay.get(3) != null){
                printDomino(toPlay.get(i));
                if(toChoose.get(0) != null && toChoose.get(1) != null && toChoose.get(2) != null && toChoose.get(3) != null){
                    System.out.print("   ");
                    printDomino(toChoose.get(i));
                }
                
                System.out.println("");
            }else{
                printDomino(toChoose.get(i));
                System.out.println("");
            }
        }

    }

    /**
     * permet d'afficher un domino
     * @param domino
     */
    public void printDomino(Domino domino){
        System.out.print(color.get(domino.getTileL().getType())+ANSI_WHITE+" "+domino.getTileL().getCrown()+" "+ANSI_RESET+"|");
        System.out.print(color.get(domino.getTileR().getType())+ANSI_WHITE+" "+domino.getTileR().getCrown()+" "+ANSI_RESET);
        if (domino.getPlayer()==null){
            System.out.print("    ");
        }else{
            System.out.print(" ["+domino.getPlayer()+"]");
        }
    }

    /**
     * Permet d'afficher le tour du joueur
     * @param currentPlayer
     */
    public void printPlayerTurn(Integer currentPlayer){
        System.out.println("C'est le tour du joueur " + currentPlayer);
    }

    /**
     * Permet d'afficher les scores en fin de partie
     */
    public void printScore(){
        System.out.println("Fin de la partie  !");
        System.out.println("Scores : ");
        System.out.println("Joueur 1 : " + this.game.getScore(1));
        System.out.println("Joueur 2 : " + this.game.getScore(2));
        if(this.game.getScore(1)>this.game.getScore(2))
            System.out.println("Victoire du Joueur 1 !");
        else
            System.out.println("Victoire du Joueur 2 !");
    }
    
    /**
     * permet d'afficher une impossibilité pour posé une carte
     */
    public void printNoPossibleChoice(){
        System.out.println("aucun choix possible: pièce défaussée");
    }
    
    /**
     * permet d'afficher les demandes de coordonnées
     * @param n
     */
    public static void printDominoPlacement(int n){
        if(n==0)
            System.out.println("Choisissez les coordonées de la tuile gauche");
        else
            System.out.println("Choisissez les coordonées de la tuile droite"); 
    } 

    /**
     * permet d'afficher la demande d'observer une partie IA contre IA
     */
    public static void printAiVsAi(){
        System.out.println("Voulez-vous voir une partie ia vs ia ?    [0: Non / 1: Oui]");
    }
    
    /**
     * permet d'afficher la demande de difficulté de l'IA
     * @param player
     */
    public static void printChooseAI(int player){
        System.out.println("Choisissez l'ia du joueur " + player + " : 1- AIRandom 2- AIScore");
    }
    
    /**
     * permet d'afficher la demande de jouabilité contre une IA
     */
    public static void printAiChoice(){
        System.out.println("Voulez-vous jouer contre une ia ?    [0: Non / 1: Oui]");
    }

    /**
     * permet d'afficher la demande de choix de domino
     */
    public static void printDominoChoice(){
        System.out.println("Entrez un nombre entre 1 et 4 pour choisir un domino");
    }

    /**
     * permet d'afficher la demande de selection d'une valeur
     * @param inf
     * @param sup
     */
    public static void printOutOfBoundary(int inf, int sup){
        System.out.println("Veuillez donner une valeur entre " + inf + " et " + sup);
    }

    /**
     * permet d'afficher l'invalidité d'un choix
     */
    public static void printNotAvailable(){
        System.out.println("Ce choix n'est pas possible");
    }
    
    /**
     * permet d'afficher l'invalidité d'une coordonnée
     */
    public static void printFailed(){
        System.out.println("Vous devez saisir sous le format y,x !");
    }
}