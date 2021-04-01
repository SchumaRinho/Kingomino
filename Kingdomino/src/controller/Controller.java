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
 * Classe représentant le Controleur de notre projet.
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public class Controller  {

    private final Game game;
    private final View view;

    private ArrayList<InterfacePlayer> players = new ArrayList<InterfacePlayer>(2);
    public Integer currentPlayer;

    /**
     * Constructeur Controller.
     * @param game
     * @param view
     */
    public Controller (Game game, View view){
        this.game = game;
        this.view = view;

        main();
    }
    
    /**
     * Méthode principal qui gère le déroulement d'une partie.
     */
    private void main(){
        // choice of AI or not
        PlayerTerminal player = new PlayerTerminal(this.game, this.game.getBoard(1));
        if(player.aiGame()){
            for(int i=1;i<=2;i++){
                if(player.choiceAiVsAi(i)==2)
                    this.players.add(new AIScore(this.game,this.game.getBoard(i)));
                else
                    this.players.add(new AIRandom(this.game));
            }
        }
        else{
            this.players.add(player);
            if(player.aiChoice()){
                if(player.choiceAiVsAi(2)==2)
                    this.players.add(new AIScore(this.game,this.game.getBoard(2)));
                else
                    this.players.add(new AIRandom(this.game));                }
            else
                this.players.add(new PlayerTerminal(this.game, this.game.getBoard(2)));
        }
        // Game playing
        this.doFirstTurn();
        for(int i = 1; i < 6 ; i++){
            this.game.getDominosFromDeck();
            for(Domino d : this.game.getToPlay()){
                this.view.printGame();
                this.view.printPlayerTurn(currentPlayer);
                this.view.printDeck();
                this.currentPlayer = d.getPlayer();

                this.doPlacement(d);
                
                this.doChoice();

            }
            this.game.setToPlay(new ArrayList<Domino>(this.game.getToChoose()));
        }
        this.doLastTurn();
        this.view.printGame();
        this.view.printScore();
    } 

    /**
     * déroulement du dernier tour
     */
    private void doLastTurn(){
        this.game.resetToChoose();
        for(Domino d : this.game.getToPlay()){
            this.view.printGame();
            this.view.printPlayerTurn(currentPlayer);
            this.view.printDeck();
            this.currentPlayer = d.getPlayer();

            this.doPlacement(d);
        }
    }

    /**
     * déroulement du premier tour
     */
    private void doFirstTurn(){
        this.game.getDominosFromDeck();
        this.view.printGame();

            // Initialisation de variables permettant de l'aléatoire pour le premier tour.  
        ArrayList<Integer> kings = new ArrayList<Integer>();
        kings.add(1);kings.add(1);kings.add(2);kings.add(2); // Deux rois par joueurs
        int n;

        int choice; 
        Domino domino;

        while(!kings.isEmpty()){
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

    /**
     * déroulement d'un choix
     */
    private void doChoice(){
            int choice;
            Domino domino;

            //this.view.printPlayerTurn(currentPlayer);
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

    /**
     * déroulement d'un placement
    */
    private void doPlacement(Domino domino){
        //this.view.printPlayerTurn(currentPlayer);
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
}