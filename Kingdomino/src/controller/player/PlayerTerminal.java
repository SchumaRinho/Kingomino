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
 *
 * @author Clémentine
 */
public class PlayerTerminal implements InterfacePlayer {
    Game game;
    Board board;
    
    public PlayerTerminal(Game game, Board board){
        this.game = game;
        this.board = board;
    }
    
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
        
    public int chooseDomino(){
        View.printDominoChoice();
        return this.boundaryChoice(1,4);
    }
    
    public ArrayList<ArrayList<Integer>> choosePlacement(ArrayList<ArrayList<ArrayList<Integer>>> placementPossible){
        boolean validatePlacement=false;
        Board boardCopy = new Board();
        ArrayList<ArrayList<Integer>> choix = new ArrayList<ArrayList<Integer>>();
        while(!validatePlacement){
            boardCopy.cloneFrom(this.board);
            for(int i = 0; i < 2; i++){
                View.printDominoPlacement(i);
                choix.add(game.validCoo(boardCopy));
//                if(i==1 && ((choix.get(0).get(0)>(choix.get(1).get(0))+1) || (choix.get(0).get(0)<(choix.get(1).get(0))-1) || (choix.get(0).get(1)>(choix.get(1).get(1))+1) || (choix.get(0).get(1)<(choix.get(1).get(1))-1))){
//                    View.printNotAvailable();
//                    choix.remove(1);
//                    i--;
//                }
//                if(i==0){
//                    boardCopy.addTile(domino.getTileL(),choix.get(0));
//                }
//                else
//                    boardCopy.addTile(domino.getTileR(),choix.get(1));
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

    public boolean aiGame(){
        View.printAiVsAi();
        return (this.boundaryChoice(0,1)==1);
    }
    
    public int choiceAiVsAi(int player){
        View.printChooseAI(player);
        return (this.boundaryChoice(1,2));
    }
    
    public boolean aiChoice(){
        View.printAiChoice();
        return (this.boundaryChoice(0,1)==1);
    }
     
}