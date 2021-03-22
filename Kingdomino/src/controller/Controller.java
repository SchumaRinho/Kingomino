/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.View;
import model.*;
import controller.player.*;
import java.util.*;

/**
 *
 * @author leovi
 */
public class Controller  {

    private Game game;
    private View view;

    private ArrayList<InterfacePlayer> players = new ArrayList<InterfacePlayer>(2);
    public Integer currentPlayer;
    
    private AIScore aiScore;
    private AIRandom aiRandom;
    private boolean ai=false;

    public Controller (Game game, View view){
        this.game = game;
        this.view = view;

        main();
    }

    private void doFirstTurn(){
        this.game.getDominosFromDeck();
        this.view.printGame();

            // Initialisation de variables permettant de l'aléatoire pour le premier tour.  
        ArrayList<Integer> kings = new ArrayList<Integer>();
        kings.add(1);kings.add(1);kings.add(2);kings.add(2); // Deux rois par joueurs
        int n;

        int choice; 
        Domino domino;

        while(kings.size() != 0 ){

            this.view.printDeck();
            n = new Random().nextInt(kings.size());
            this.currentPlayer = kings.get(n);
            kings.remove(n);

            this.view.printPlayerTurn(currentPlayer);
            choice = this.players.get(currentPlayer-1).chooseDomino();
            domino = this.game.getToChoose().get(choice-1);
            while(this.game.getToPlay().contains(domino)){
                if(this.players.get(currentPlayer-1).getClass() == PlayerTerminal.class){
                    View.printNotAvailable();
                }
                choice = this.players.get(currentPlayer-1).chooseDomino();
                domino = this.game.getToChoose().get(choice-1);
            }

            domino.setPlayer(currentPlayer);

            this.game.getToPlay().set(choice-1,domino);
        }
    }

    private void doChoice(){
            int choice;
            Domino domino;

            this.view.printPlayerTurn(currentPlayer);
            choice = this.players.get(currentPlayer-1).chooseDomino();
            domino = this.game.getToChoose().get(choice-1);
            while(domino.getPlayer()!=null){
                if(this.players.get(currentPlayer-1).getClass() == PlayerTerminal.class){
                    View.printNotAvailable();
                }
                choice = this.players.get(currentPlayer-1).chooseDomino();
                domino = this.game.getToChoose().get(choice-1);
            }

            domino.setPlayer(currentPlayer);
    }

    private void doPlacement(Domino domino){
        this.view.printPlayerTurn(currentPlayer);
        this.game.possiblePlacement(domino, game.getBoard(currentPlayer));
        if(!this.game.getPossiblePlacement().isEmpty()){
            ArrayList<ArrayList<Integer>> coo = new ArrayList<ArrayList<Integer>>();
            coo = this.players.get(currentPlayer-1).choosePlacement(this.game.getPossiblePlacement());
            game.getBoard(currentPlayer).addDomino(domino, coo);
        }
        else{
            view.printNoPossibleChoice();
        }
        domino.resetPlayer();
        
    }
    
    // a remonté plus haut !!!
    private void main(){
        // choice of AI or not
        PlayerTerminal player = new PlayerTerminal(this.game, this.game.getBoard(1));
        this.players.add(player);
        if(player.aiChoice())
            this.players.add(new AIScore(this.game,this.game.getBoard(2)));
        else
            this.players.add(new PlayerTerminal(this.game, this.game.getBoard(2)));
        // Game playing
        this.doFirstTurn();
        for(int turn = 2; turn <=12; turn ++){
            this.game.getDominosFromDeck();
            for(Domino d : this.game.getToPlay()){
                this.view.printGame();
                this.view.printDeck();
                this.currentPlayer = d.getPlayer();

                this.doPlacement(d);
                
                this.doChoice();

            }
            this.game.setToPlay(new ArrayList<Domino>(this.game.getToChoose()));
        }
        this.view.printScore();
    } 
    
}