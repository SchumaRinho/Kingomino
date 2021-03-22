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
 *
 * @author Clémentine
 */
public interface InterfacePlayer {

    public int chooseDomino();
    
    public ArrayList<ArrayList<Integer>> choosePlacement(ArrayList<ArrayList<ArrayList<Integer>>> placementPossible);
}
