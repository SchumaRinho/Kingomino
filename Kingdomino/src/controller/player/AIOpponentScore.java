/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.player;

import java.util.ArrayList;
import java.util.Random;
import model.*;
/**
 *
 * @author Pierre Besnehard, Alexandre Bellebon
 */
public class AIOpponentScore implements InterfacePlayer{
    
    private final Board plateau1;
    private final Board plateau2;
    private Board nextPlateau;
    private boolean round = true;
    private final Game game;
    private int player;
    private int opponent;
    
    /**
     * Constructeur AIScore.
     * @param game
     * @param player
     */
    public AIOpponentScore(Game game, int player){
        this.game = game;
        this.player = player;
        if(player == 1)
            this.opponent = 2;
        else
            this.opponent = 1;
        this.plateau1 = this.game.getBoard(this.player);
        this.plateau2 = this.game.getBoard(this.opponent);
        this.nextPlateau = this.game.getBoard(this.opponent);
    }
    
    /**
     * Permet de créer une liste de placement qui donne le meilleurs score
     * @param d
     * @param possiblePlacement
     * @return l'ensemble des placements qui donne le meilleurs score
     */
    private  ArrayList<ArrayList<ArrayList<Integer>>> listeBestScore( Domino d , ArrayList<ArrayList<ArrayList<Integer>>> possiblePlacement){
        int tmp=-1;
        ArrayList<ArrayList<ArrayList<Integer>>> listeBestScore= new ArrayList<ArrayList<ArrayList<Integer>>>();        
        for(int i=0; i<possiblePlacement.size();i++){
            Board plateauCopy = new Board(); plateauCopy.cloneFrom(plateau1); plateauCopy.addDomino(d, possiblePlacement.get(i));
            if(tmp<this.game.getScoreAI(plateauCopy)){
                tmp=this.game.getScoreAI(plateauCopy);
                listeBestScore.clear();
                listeBestScore.add(possiblePlacement.get(i));
            }
            else if(tmp==this.game.getScoreAI(plateauCopy))
                listeBestScore.add(possiblePlacement.get(i));
        }
        return listeBestScore;
    }
    
    /**
     * Permet de remettre a zéro le plateau une fois que le second domino est choisi
     */
    private void resetNextPlateau(){
        this.nextPlateau=this.plateau2;
    }

    /**
     * Permet de choisir un Domino dans la liste des dominos a choisir, de sorte que le prochain placement soit celui qui empêche d'augmenter au mieux le score de l'adversaire
     * @return
     */
    @Override
    public int chooseDomino(){
        resetNextPlateau();
        Board currentBestBoard = new Board();
        int tmp=-1; int choix=-1;
        for(int n=0; n<this.game.getToChoose().size();n++){
            if(this.game.getToChoose().get(n).getPlayer()==null){
                ArrayList<ArrayList<ArrayList<Integer>>> possiblePlacement = this.game.possiblePlacement(this.game.getToChoose().get(n), nextPlateau);
                if(!possiblePlacement.isEmpty()){
                    for(int i=0; i<possiblePlacement.size();i++){
                        Board plateauCopy = new Board(); plateauCopy.cloneFrom(nextPlateau); plateauCopy.addDomino(this.game.getToChoose().get(n), possiblePlacement.get(i));
                        if(tmp<this.game.getScoreAI(plateauCopy)){
                            currentBestBoard.cloneFrom(plateauCopy);        /*Bug quand il doit choisir une pièce qui ne peut pas placer*/
                            tmp=this.game.getScoreAI(plateauCopy);
                            choix=n;
                        }
                    }
                }
                else{
                    if(choix==-1)
                        choix=n;
                }
            }
        }
        round = !round;
        return choix+1;
    }

    /**
     * Tire un placement aléatoire dans la liste de placement qui donne le meilleur score
     * @param placementPossible
     * @return un placement
     */
    @Override
    public ArrayList<ArrayList<Integer>> choosePlacement(ArrayList<ArrayList<ArrayList<Integer>>> placementPossible){
       ArrayList<ArrayList<ArrayList<Integer>>> coo = this.listeBestScore(this.game.getSelectedDomino(), placementPossible);
       return coo.get(new Random().nextInt(coo.size()));
    }
}
