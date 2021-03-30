/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import model.*;

/**
 *
 * @author leovi
 */
public class AIScore implements InterfacePlayer{
    
    private Board plateau;
    private Board nextPlateau;
    private ArrayList<ArrayList<Integer>>  cooBestScoreDomino1;
    private ArrayList<ArrayList<Integer>>  cooBestScoreDomino2;
    private boolean round = true;
    private HashMap<ArrayList<ArrayList<Integer>>,Integer> test;
    private Game game;
    
    public AIScore(Game game, Board plateau){
        this.game = game;
        this.plateau = plateau;
        this.nextPlateau = plateau;
    }
    
    private  ArrayList<ArrayList<ArrayList<Integer>>> listeBestScore( Domino d , ArrayList<ArrayList<ArrayList<Integer>>> possiblePlacement){
        int tmp=-1;
        ArrayList<ArrayList<ArrayList<Integer>>> listeBestScore= new ArrayList<ArrayList<ArrayList<Integer>>>();        
        for(int i=0; i<possiblePlacement.size();i++){
            Board plateauCopy = new Board(); plateauCopy.cloneFrom(plateau); plateauCopy.addDomino(d, possiblePlacement.get(i));
            if(tmp<this.game.getScoreAI(plateauCopy)){
                tmp=this.game.getScoreAI(plateauCopy);
                listeBestScore.clear();;
                listeBestScore.add(possiblePlacement.get(i));
            }
            else if(tmp==this.game.getScoreAI(plateauCopy))
                listeBestScore.add(possiblePlacement.get(i));
        }
        return listeBestScore;
    }
    
    private void resetNextPlateau(Board plateau, boolean round){
        if(round)
            this.nextPlateau=this.plateau;
    }
    @Override
    public int chooseDomino(){
        resetNextPlateau(nextPlateau,round);
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
    @Override
    public ArrayList<ArrayList<Integer>> choosePlacement(ArrayList<ArrayList<ArrayList<Integer>>> placementPossible){
       ArrayList<ArrayList<ArrayList<Integer>>> coo = this.listeBestScore(this.game.getSelectedDomino(), placementPossible);
       return coo.get(new Random().nextInt(coo.size()));
    }

}
    