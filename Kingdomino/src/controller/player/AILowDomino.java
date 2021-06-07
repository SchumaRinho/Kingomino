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
 * Classe Représentant L'IA LowDomino, implémentant InterfacePlayer.
 * @author Besnehard Pierre, Bellebon Alexandre
 */
public class AILowDomino implements InterfacePlayer{
    
    private final Board plateau;
    private final Game game;
    
    /**
     * Constructeur AILowDomino.
     * @param game
     * @param plateau
     */
    public AILowDomino(Game game, Board plateau){
        this.game = game;
        this.plateau = plateau;
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
            Board plateauCopy = new Board(); plateauCopy.cloneFrom(plateau); plateauCopy.addDomino(d, possiblePlacement.get(i));
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
     * Permet de choisir le domino étant au plus haut de la liste, c'est a dire celui avec la valeur la plus faible.
     * @return
     */
    @Override
    public int chooseDomino(){
        if(this.game.getToChoose().get(0).getPlayer()==null)
            return 1;
        else if(this.game.getToChoose().get(1).getPlayer()==null)
            return 2;
        else if(this.game.getToChoose().get(2).getPlayer()==null)
            return 3;
        else
            return 4;
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
