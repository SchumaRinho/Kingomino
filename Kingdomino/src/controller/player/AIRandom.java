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
public class AIRandom implements InterfacePlayer {
    Game game;
    
    public AIRandom(Game game){
        this.game = game;
    }
    
    public int chooseDomino(){
        int random = new Random().nextInt(4) + 1;
        return random;
    }
    
    public ArrayList<ArrayList<Integer>> choosePlacement(ArrayList<ArrayList<ArrayList<Integer>>> placementPossible){
        int borneSup = placementPossible.size();
        return placementPossible.get(new Random().nextInt(borneSup));
    }

     
}


