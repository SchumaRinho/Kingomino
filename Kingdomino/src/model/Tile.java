/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Classe représentant la partie droite ou la partie gauche d'un domino.
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public class Tile {
    
    private final String type;
    private final int crown;
    
    /**
     * Constructeur Tile.
     * 
     * @param type
     * @param crown
     */
    public Tile(String type, int crown){
        this.type = type;
        this.crown = crown;
    }
    
    /**
     * récupère le type du Tile.
     * @return type de terrain du Tyle
     */
    public String getType() {
        return type;
    }

    /**
     * récupère le nombre de couronne sur le Tile.
     * @return nombre de crown sur un Tile
     */
    public int getCrown() {
        return crown;
    }
}
