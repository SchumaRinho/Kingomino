/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
/**
 *
 * @author Junior
 */
public class Move {
    private final Domino d;
    private final ArrayList<Integer> coo;
    
    public Move(Domino domino, ArrayList<Integer> coordonnees){
        this.d = domino;
        this.coo = coordonnees;        
    }

    public Domino getDomino() {
        return d;
    }

    public ArrayList<Integer> getCoo() {
        return coo;
    }
}
