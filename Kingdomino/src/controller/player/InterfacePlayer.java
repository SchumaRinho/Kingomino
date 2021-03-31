/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.player;


import model.*;
import java.util.*;
import view.*;
/**
 * Interface représentant tout les joueurs, que ce soit écrivant en terminal, ou IA suivant un algorithme, pour unifié les méthodes de choix
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public interface InterfacePlayer {

    /**
     * Choix d'un domino dans la liste des dominos a choisir
     * @return chiffre pouvant etre de 0 à taille de la liste - 1
     */
    public int chooseDomino();
    
    /**
     * Choix d'un placement dans la liste des placements possible
     * @param placementPossible
     * @return un placement
     */
    public ArrayList<ArrayList<Integer>> choosePlacement(ArrayList<ArrayList<ArrayList<Integer>>> placementPossible);
}
