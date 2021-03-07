/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author leovi
 */
public class Plateau {
    
    /*private void construcPlateau(int x, int y){
        for(int i = 0; i <= x-1 ; i++){
			for(int j = 0; j <= y-1 ; j++){
				this.plateau.put(new ArrayList<Integer>(Arrays.asList((Integer)i, (Integer)j)), null);
			}
        }
	}*/
    
    @Override
	public String toString(){
            for(int i = 0; i<9; i++){
		for(int j = 0; j<9; j++){
                    /*if(this.plateau.get(new ArrayList<Integer>(Arrays.asList(i,j))) != null){
                       System.out.print("  ■ ");*/
                    if(i == 4 && j == 4){
                       System.out.print(" ❶ ❷ ❸ ");
                    }else{
                       System.out.print(" + ");
                    }
                }
		System.out.println();
            }
            return "";
	}
}
