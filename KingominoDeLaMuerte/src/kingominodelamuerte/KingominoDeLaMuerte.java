/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingominodelamuerte;

import Controleur.*;
import model.*;
import Vue.*;
/**
 *
 * @author leovi
 */
public class KingominoDeLaMuerte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Plateau model = new Plateau();
        Vue vue = new Vue(model);
        Controleur lol = new Controleur(model,vue);
    }   
}
