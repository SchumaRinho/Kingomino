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
    
    public PlayerTerminal(Game game){
        this.game = game;
    }

    public int chooseDomino(){
        View.printDominoChoice();
        return this.boundaryChoice(1,4);
    }
    
    public ArrayList<ArrayList<Integer>> choosePlacement(Domino domino){
        // Not done yet
        return null;
    }

    public boolean aiChoice(){
        View.printAiChoice();
        return (this.boundaryChoice(0,1)==1);
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
     
}