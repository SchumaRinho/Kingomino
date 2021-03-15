/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.util.Random;
import model.*;
/**
 *
 * @author leovi
 */
public class AIRandom {
    Board plateau;
    
    public AIRandom(Board plateau){
        this.plateau = plateau;
    }
    
    public int getChoix(int borneInf, int borneSup){
        if(borneSup==borneInf)
            return borneInf;
        return borneInf+ new Random().nextInt(borneSup-borneInf);
    }
    
    public Board getPlacement(){
        return plateau;
    }
     
}


