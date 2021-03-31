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
 * Classe Principale du Projet Kingdomino
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre

 */
public class Kingdomino {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        View view = new View(game);
        Controller controller = new Controller(game,view);
    }   
}
