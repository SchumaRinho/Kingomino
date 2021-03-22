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
    
    ArrayList<ArrayList<ArrayList<Integer>>> possiblePlacement = new ArrayList<ArrayList<ArrayList<Integer>>>();


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
        this.view.printPlayerTurn(currentPlayer);
        possiblePlacement(domino, game.getBoard(currentPlayer));
        System.out.println(possiblePlacement);
        if(!possiblePlacement.isEmpty()){
            ArrayList<ArrayList<Integer>> coo = new ArrayList<ArrayList<Integer>>();
            coo = this.players.get(currentPlayer-1).choosePlacement(possiblePlacement);
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
            this.players.add(new AIRandom(this.game));
        else
            this.players.add(new PlayerTerminal(this.game, this.game.getBoard(2)));
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
    
    private ArrayList<ArrayList<ArrayList<Integer>>> possiblePlacement(Domino d, Board plateau){
        possiblePlacement.clear();
        Board plateauCopy = new Board();
        for(int i=0;i<=8;i++){
            for(int j=0;j<=8;j++){
                plateauCopy.cloneFrom(plateau);
                ArrayList<Integer> coo = new ArrayList<Integer>(Arrays.asList((Integer)i, (Integer)j));
                if(plateauCopy.verifTile(coo)){
                    canBePlaced(d,coo,plateauCopy);
                }
            }
        }
        System.out.println(possiblePlacement);
        return possiblePlacement;
    }
    
    private void canBePlaced(Domino d, ArrayList<Integer> coo,Board plateau){
        Board plateauCopy = new Board();
        plateau.addTile(d.getTileL(), coo);
        ArrayList<ArrayList<Integer>> tmp = new ArrayList<ArrayList<Integer>>();

        tmp.add(new ArrayList (Arrays.asList(coo.get(0), coo.get(1)-1)));
        tmp.add(new ArrayList (Arrays.asList(coo.get(0), coo.get(1)+1)));
        tmp.add(new ArrayList (Arrays.asList(coo.get(0)-1, coo.get(1))));
        tmp.add(new ArrayList (Arrays.asList(coo.get(0)+1, coo.get(1))));

        for(int i = 0; i < 4 ; i++) {
            if(plateau.verifTile(tmp.get(i))){
                ArrayList<ArrayList<Integer>> cooDomino = new ArrayList<ArrayList<Integer>>();
                cooDomino.add(coo);
                cooDomino.add(tmp.get(i));
                plateauCopy.cloneFrom(plateau); plateauCopy.addTile(d.getTileR(), tmp.get(i));
                if(plateauCopy.verifCoordDomino(cooDomino) && plateauCopy.verifPlacement()){
                    if(!possiblePlacement.contains(cooDomino)){
                        possiblePlacement.add(cooDomino);
                    }
                }
            }
        }
    }
    
    
}