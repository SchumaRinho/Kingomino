/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.player;

import model.*;
import java.util.*;
import view.*;

/**
 * Classe gérant les réponse de l'utilisateur dans le terminal, implémentant InterfacePlayer.
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public class PlayerTerminal implements InterfacePlayer {
    Game game;
    Board board;
    
    /**
     * Constructeur PlayerTerminal.
     * @param game
     * @param board
     */
    public PlayerTerminal(Game game, Board board){
        this.game = game;
        this.board = board;
    }
    
    /**
     * Permet de recevoir la réponse du joueur.
     * @param inf
     * @param sup
     * @return la réponse de l'utilisateur
     */
    private int boundaryChoice(int inf, int sup){
        int choice = -1;
        while(choice == -1){
            Scanner scanChoice = new Scanner(System.in);
            try{
                choice = scanChoice.nextInt();
                while(choice < inf || choice > sup){
                    View.printOutOfBoundary(inf,sup);
                    choice = scanChoice.nextInt();
                }
            }
            catch(Exception e){
                System.err.println("Erreur : "+e);
            }
        }
        return choice;
    }
        
    /**
     * permet de recevoir la réponse de l'utilisateur, sur le choix d'un domino.
     * @return un integer
     */
    @Override
    public int chooseDomino(){
        View.printDominoChoice();
        return this.boundaryChoice(1,4);
    }
    
    /**
     * Permet de recevoir une réponse du joueur, sur le placement qu'il souhaite éffectuer.
     * @param placementPossible
     * @return un placement
     */
    @Override
    public ArrayList<ArrayList<Integer>> choosePlacement(ArrayList<ArrayList<ArrayList<Integer>>> placementPossible){
        boolean validatePlacement=false;
        Board boardCopy = new Board();
        ArrayList<ArrayList<Integer>> choix = new ArrayList<ArrayList<Integer>>();
        while(!validatePlacement){
            boardCopy.cloneFrom(this.board);
            for(int i = 0; i < 2; i++){
                View.printDominoPlacement(i);
                choix.add(game.validCoo(boardCopy));
            }
            if(placementPossible.contains(choix)){
                validatePlacement=true;
            }
            else{
                View.printNotAvailable();
                choix.clear();
            }
        }
        return choix;
    }

    /**
     * Permet de recevoir la réponse du joueur, si il souhaite ou non observer une partie ia contre ia.
     * @return un booléen
     */
    public boolean aiGame(){
        View.printAiVsAi();
        return (this.boundaryChoice(0,1)==1);
    }
    
    /**
     * Permet de recevoir la réponse du joueur, sur la difficultée de l'ia.
     * @param player
     * @return un booléen
     */
    public int choiceAiVsAi(int player){
        View.printChooseAI(player);
        return (this.boundaryChoice(1,2));
    }
    
    /**
     * Permet de recevoir la réponse du joueur, si il veux jouer contre une ia.
     * @return un booléen
     */
    public boolean aiChoice(){
        View.printAiChoice();
        return (this.boundaryChoice(0,1)==1);
    }
     
    public boolean viewChoice(){
        View.printViewChoice();
        return (this.boundaryChoice(0,1)==1);
    }
}