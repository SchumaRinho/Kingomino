/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant un domino, implémentant Comparable.
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public class Domino implements Comparable<Domino>{
    
    private Tile tileR;
    private Tile tileL;
    private int value;
    private Integer player = null;

    /**
     * Constructeur Domino.
     * 
     * @param tileR
     * @param tileL
     * @param value
     */
    public Domino(Tile tileR, Tile tileL, int value){
        this.tileL = tileL;
        this.tileR = tileR;
        this.value = value;
    }
    
    /**
     * récupère la partie droite du domino.
     * @return le Tile droit
     */
    public Tile getTileR() {
        return this.tileR;
    }

    /**
     * récupère la partie gauche du domino.
     * @return le Tile gauche
     */
    public Tile getTileL() {
        return this.tileL;
    }

    /**
     * récupère la valeur du domino.
     * @return valeur du domino
     */
    public int getValue(){
        return this.value;
    }

    /**
     * récupère le joueur qui possède le domino.
     * @return joueur auquel appartient le domino.
     * @see Domino#setPlayer(Integer)
     * @see Domino#resetPlayer()
     */
    public Integer getPlayer(){
        return this.player;
    }

    /**
     * Met à jour le joueur qui possède le domino.
     * @param player
     * @see Domino#resetPlayer()
     * @see Domino#getPlayer()
     */
    public void setPlayer(Integer player){
        this.player = player;
    }

    /**
     * supprime le joueur qui possède le domino.
     * @see Domino#setPlayer(Integer)
     * @see Domino#getPlayer()
     */
    public void resetPlayer(){
        this.player = null;
    }
  
    
    /**
     * Permet de pouvoir comparer deux dominos.
     * 
     * @param domino
     * @return la difference des valeurs des deux dominos.
     */
    @Override
        public int compareTo(Domino domino) {
               return (this.value - domino.getValue());
        }
}
