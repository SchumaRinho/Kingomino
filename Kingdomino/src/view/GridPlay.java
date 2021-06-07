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
 * Classe, extension de JPanel, représentant une grille de jeu sur l'interface graphique.
 * @author Pierre Besnehard, Alexandre Bellebon
 */
public class GridPlay extends JPanel{
    private final JPanel[][] tab = new JPanel[10][10];
    private final Border border = BorderFactory.createLineBorder(Color.black,2);
    private final String p;
    
    /**
     * Constructeur de GridPlay.
     * @param grid
     * @param player
     */
    public GridPlay(JPanel grid, String player){
        this.p = player;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = new JPanel(new GridLayout(1, 2));
                if(i==0 && j!=0){
                    JLabel columns = new JLabel(Integer.toString(j),SwingConstants.CENTER);
                    tab[i][j].add(columns);
                }
                if(i!=0 && j==0){
                    JLabel lines = new JLabel(Integer.toString(i),SwingConstants.CENTER);
                    tab[i][j].add(lines);
                }
                if(i>=1 && j>=1){
                    tab[i][j].setName("empty");
                    tab[i][j].setBorder(border);
                }
                grid.add(tab[i][j]);
            }
        }
    }

    /**
     * permet de récuperer une case de la grille de jeu.
     * @param x
     * @param y
     * @return une case de la grille.
     */
    public JPanel getPanel(int x, int y){
        return this.tab[x+1][y+1];
    }
    
    /**
     * permet de récuperer le joueur à qui appartient le plateau.
     * @return le joueur qui possède le plateau.
     */
    public String getPlayer(){
        return this.p;
    }
    
    /**
     * permet de récuperer le nom d'une case dans la grille de jeu.
     * @param x
     * @param y
     * @return le nom de la case.
     */
    public String getName(int x, int y){
        return tab[x][y].getName();
    }
    
    /**
     * permet de changer le nom d'une case dans la grille de jeu.
     * @param x
     * @param y
     * @param name
     */
    public void setName(int x, int y, String name){
        tab[x][y].setName(name);
    }
    
}