/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.*;
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
    private Boolean aiVsAi = false;

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
            aiVsAi = true;
            for(int i=1;i<=2;i++){
                int j = player.choiceAiVsAi(i);
                switch (j) {
                    case 2:
                        this.players.add(new AIScore(this.game,this.game.getBoard(i)));
                        break;
                    case 3:
                        this.players.add(new AIOpponentScore(this.game,i));
                        break;
                    case 4:
                        this.players.add(new AIHighDomino(this.game,this.game.getBoard(i)));
                        break;
                    case 5:
                        this.players.add(new AILowDomino(this.game,this.game.getBoard(i)));
                        break;
                    case 6:
                        this.players.add(new AIBestScaleScore(this.game,i));
                        break;
                    default:
                        this.players.add(new AIRandom(this.game));
                        break;
                }
            }
        }
        else{
            this.players.add(player);
            if(player.aiChoice()){
                int j = player.choiceAiVsAi(2);
                switch (j) {
                    case 2:
                        this.players.add(new AIScore(this.game,this.game.getBoard(2)));
                        break;
                    case 3:
                        this.players.add(new AIOpponentScore(this.game,2));
                        break;
                    case 4:
                        this.players.add(new AIHighDomino(this.game,this.game.getBoard(2)));
                        break;
                    case 5:
                        this.players.add(new AILowDomino(this.game,this.game.getBoard(2)));
                        break;
                    case 6:
                        this.players.add(new AIBestScaleScore(this.game,2));
                        break;
                    default:
                        this.players.add(new AIRandom(this.game));
                        break;
                }
            }
            else
                this.players.add(new PlayerTerminal(this.game, this.game.getBoard(2)));
        }
        // choice of GraphicView or not
        if(player.viewChoice()){
            this.view.setView(true);
            this.view.printWindowView();
        }
        // Game playing
        this.doFirstTurn();
        for(int i = 1; i < 6 ; i++){
            this.game.getDominosFromDeck();
            if(this.view.getView()){
                this.view.update(currentPlayer);
                this.view.printDeckView();
            }
            for(Domino d : this.game.getToPlay()){
                this.currentPlayer = d.getPlayer();
                if(!this.view.getView()){ //  /.!.\ [Il y a plein de if de ce genre, ils permettent d'afficher le jeu en mode console uniquement, si il y a une fenetre graphique, alors les seuls choses indiquer seront le tour du joueur, et les questions posé par la console ]
                    this.view.printGame();
                    this.view.printDeck();
                }else{
                    this.view.update(currentPlayer);
                }
                aiVsAi();
                this.view.printPlayerTurn(currentPlayer);
                
                this.doPlacement(d);
                if(this.view.getView()){
                    this.view.update(currentPlayer);
                }
                aiVsAi();
                this.doChoice();

            }
            this.game.setToPlay(new ArrayList<Domino>(this.game.getToChoose()));
        }
        this.doLastTurn();
        if(!this.view.getView()){
            this.view.printGame();
            this.view.printScore();
        }else{
            this.view.update(0);
            this.view.printScoreView();
        }
        
    } 

    /**
     * déroulement du dernier tour
     */
    private void doLastTurn(){
        this.game.resetToChoose();
        this.view.unprintDeckView();
        for(Domino d : this.game.getToPlay()){
            this.currentPlayer = d.getPlayer();
            if(!this.view.getView()){
                this.view.printGame();
                this.view.printDeck();
            }else{
                this.view.update(currentPlayer);
            }
            aiVsAi();
            this.view.printPlayerTurn(currentPlayer);
            this.doPlacement(d);
        }
    }

    /**
     * déroulement du premier tour
     */
    private void doFirstTurn(){
        this.game.getDominosFromDeck();
        if(!this.view.getView()){
            this.view.printGame();
        }

            // Initialisation de variables permettant de l'aléatoire pour le premier tour.  
        ArrayList<Integer> kings = new ArrayList<Integer>();
        kings.add(1);kings.add(1);kings.add(2);kings.add(2); // Deux rois par joueurs
        int n;

        int choice; 
        Domino domino;

        while(!kings.isEmpty()){
            n = new Random().nextInt(kings.size());
            this.currentPlayer = kings.get(n);
            kings.remove(n);
            
            if(!this.view.getView()){
                this.view.printDeck();
            }else{
                this.view.update(currentPlayer);
            }
            aiVsAi();
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
    
    /**
     * vérifie si le joueur souhaite observer une partie IA vs IA, si oui fait une pause au jeu d'une seconde.
    */
    private void aiVsAi(){
        if(aiVsAi){
            this.view.viewWait();
        }
    }
}