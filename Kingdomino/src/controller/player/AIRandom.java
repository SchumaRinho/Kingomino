/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.player;

import model.*;
import java.util.*;
/**
 * Classe représentant l'IA Random, implémentant InterfacePlayer.
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public class AIRandom implements InterfacePlayer {
    private final Game game;
    
    /**
     * Constructeur AIRandom.
     * @param game
     */
    public AIRandom(Game game){
        this.game = game;
    }
    
    /**
     * Choix aléatoire d'un domino.
     * @return un chiffre aléatoire
     */
    @Override
    public int chooseDomino(){
        int random = new Random().nextInt(this.game.getToChoose().size()) + 1;
        return random;
    }
    
    /**
     * Choix aléatoire d'un placement parmis une liste de placement
     * @param placementPossible
     * @return Un placement depuis une liste
     */
    @Override
    public ArrayList<ArrayList<Integer>> choosePlacement(ArrayList<ArrayList<ArrayList<Integer>>> placementPossible){
        int borneSup = placementPossible.size();
        return placementPossible.get(new Random().nextInt(borneSup));
    }

     
}


