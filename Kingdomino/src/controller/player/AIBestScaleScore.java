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
public class AIBestScaleScore implements InterfacePlayer{
    
    private final Board plateau1;
    private final Board plateau2;
    private Board nextPlateau1;
    private Board nextPlateau2;
    private boolean round = true;
    private final Game game;
    private int player;
    private int opponent;
    
    
    /**
     * Constructeur AIScore.
     * @param game
     * @param player
     */
    public AIBestScaleScore(Game game, int player){
        this.game = game;
        this.player = player;
        if(player == 1)
            this.opponent = 2;
        else
            this.opponent = 1;
        this.plateau1 = this.game.getBoard(this.player);
        this.plateau2 = this.game.getBoard(this.opponent);
        this.nextPlateau1 = this.game.getBoard(this.player);
        this.nextPlateau2 = this.game.getBoard(this.opponent);
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
    private void resetNextPlateau1(){
        this.nextPlateau1=this.plateau1;
    }
    
    private void resetNextPlateau2(){
        this.nextPlateau2=this.plateau2;
    }

    /**
     * Permet de choisir un Domino dans la liste des dominos a choisir, de sorte que le prochain placement soit celui qui augmente au mieux l'écart de score
     * @return
     */
    @Override
    public int chooseDomino(){
        resetNextPlateau1(); resetNextPlateau2();
        Board currentBestBoard = new Board();
        int tmp1=-1; int tmp2=-1; int tmp3=-100; int choix=-1;
        for(int n=0; n<this.game.getToChoose().size();n++){
            if(this.game.getToChoose().get(n).getPlayer()==null){
                ArrayList<ArrayList<ArrayList<Integer>>> possiblePlacement1 = this.game.possiblePlacement(this.game.getToChoose().get(n), nextPlateau1);
                ArrayList<ArrayList<ArrayList<Integer>>> possiblePlacement2 = this.game.possiblePlacement(this.game.getToChoose().get(n), nextPlateau2);
                if(!possiblePlacement1.isEmpty()&&!possiblePlacement2.isEmpty()){
                    for(int i=0; i<possiblePlacement1.size();i++){
                        Board plateauCopy = new Board(); plateauCopy.cloneFrom(nextPlateau1); plateauCopy.addDomino(this.game.getToChoose().get(n), possiblePlacement1.get(i));
                        if(tmp1<this.game.getScoreAI(plateauCopy)){
                            currentBestBoard.cloneFrom(plateauCopy);        /*Bug quand il doit choisir une pièce qui ne peut pas placer*/
                            tmp1=this.game.getScoreAI(plateauCopy);
                            
                        }
                    }
                    for(int i=0; i<possiblePlacement1.size();i++){
                        Board plateauCopy = new Board(); plateauCopy.cloneFrom(nextPlateau1); plateauCopy.addDomino(this.game.getToChoose().get(n), possiblePlacement1.get(i));
                        if(tmp2<this.game.getScoreAI(plateauCopy)){
                            currentBestBoard.cloneFrom(plateauCopy);        /*Bug quand il doit choisir une pièce qui ne peut pas placer*/
                            tmp2=this.game.getScoreAI(plateauCopy);
                            
                        }
                    }
                    if(tmp3<tmp1-tmp2){
                        tmp3 = tmp1-tmp2;
                        choix = n;
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