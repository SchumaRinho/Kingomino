/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdomino;

import view.View;
import controller.Controller;
import model.*;
/**
 *
 * @author leovi
 */
public class Kingdomino {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board plateau1 = new Board();
        Board plateau2 = new Board();
        //plateau2.plateauTest();
        View vue = new View(plateau1,plateau2);
        Controller lol = new Controller(plateau1,plateau2,vue);
    }   
}
