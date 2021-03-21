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
            domino = this.game.toChoose.get(choice-1);
            while(this.game.toPlay.contains(domino)){
                if(this.players.get(currentPlayer-1).getClass() == PlayerTerminal.class){
                    View.printNotAvailable();
                }
                choice = this.players.get(currentPlayer-1).chooseDomino();
                domino = this.game.toChoose.get(choice-1);
            }

            domino.setPlayer(currentPlayer);

            this.game.toPlay.set(choice-1,domino);
        }
    }

    private void doChoice(){
            int choice;
            Domino domino;

            this.view.printPlayerTurn(currentPlayer);
            choice = this.players.get(currentPlayer-1).chooseDomino();
            domino = this.game.toChoose.get(choice-1);
            while(domino.getPlayer()!=null){
                if(this.players.get(currentPlayer-1).getClass() == PlayerTerminal.class){
                    View.printNotAvailable();
                }
                choice = this.players.get(currentPlayer-1).chooseDomino();
                domino = this.game.toChoose.get(choice-1);
            }

            domino.setPlayer(currentPlayer);
    }

    private void doPlacement(Domino domino){
        domino.resetPlayer();
                                                                        // Non implémenté
    }
    
    // a remonté plus haut !!!
    private void main(){
        // choice of AI or not
        PlayerTerminal player = new PlayerTerminal(this.game);
        this.players.add(player);
        if(player.aiChoice())
            this.players.add(new AIRandom(this.game));
        else
            this.players.add(new PlayerTerminal(this.game));
        // Game playing
        this.doFirstTurn();
        for(int turn = 2; turn <=12; turn ++){
            this.game.getDominosFromDeck();
            for(Domino d : this.game.toPlay){
                this.view.printGame();
                this.view.printDeck();
                this.currentPlayer = d.getPlayer();

                this.doPlacement(d);
                
                this.doChoice();

            }
            this.game.toPlay = new ArrayList<Domino>(this.game.toChoose);
        }
        this.view.printScore();
    }
}