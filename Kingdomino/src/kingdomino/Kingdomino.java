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
        Board board1 = new Board();
        Board board2 = new Board();
        //plateau2.plateauTest();
        View view = new View(board1,board2);
        Controller controller = new Controller(board1,board2,view);
    }   
}
