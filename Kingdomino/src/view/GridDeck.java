/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.border.Border;

/**
 * Classe, extension de JPanel, représentant une liste de pièces sur l'interface graphique.
 * @author Pierre Besnehard, Alexandre Bellebon
 */
public class GridDeck extends JPanel{
    private final JPanel[][] tab = new JPanel[4][3];
    private final Border border = BorderFactory.createLineBorder(Color.black,2);
    
    /**
     * constructeur de GridDeck.
     * @param grid
     */
    public GridDeck(JPanel grid){
        for (JPanel[] tab1 : tab) {
            for (int j = 0; j < tab1.length; j++) {
                tab1[j] = new JPanel(new GridLayout(1, 0));
                if (j!=2) {
                    tab1[j].setBorder(border);
                }
                grid.add(tab1[j]);
            }
        }
    }

    /**
     * permet de récupérer une case dans la liste.
     * @param x
     * @param y
     * @return une case de la liste.
     */
    public JPanel getPanel(int x, int y){
        return this.tab[x][y];
    }
    
}
