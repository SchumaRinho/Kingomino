/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.*;
import javax.swing.*;
import java.util.*;
import static model.Game.color;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Classe représentant la Vue de notre projet.
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public class View extends JFrame{
    private final JFrame window = new JFrame("Kingdomino, Interface Graphique");
    private final Border border = BorderFactory.createLineBorder(Color.BLACK,2);
    private final Border noBorder = BorderFactory.createLineBorder(Color.WHITE,0);
    private final Border p1Border = BorderFactory.createLineBorder(Color.BLUE,2);
    private final Border p2Border = BorderFactory.createLineBorder(Color.RED,2);
    private final JPanel gridP1 = new JPanel(new GridLayout(10,10));
    private final JPanel gridP2 = new JPanel(new GridLayout(10,10));
    GridPlay tab1 = new GridPlay(gridP1, "p1");
    GridPlay tab2 = new GridPlay(gridP2, "p2");
    JPanel p1 = new JPanel(new GridBagLayout());
    JPanel p2 = new JPanel(new GridBagLayout());
    JPanel tilesInDeck = new JPanel(new GridLayout(4, 3));
    JPanel playableTiles = new JPanel(new GridLayout(4, 3));
    GridDeck tileInDeck = new GridDeck(tilesInDeck);
    GridDeck playableTile = new GridDeck(playableTiles);
    JPanel tilesToDraw = new JPanel();
    JPanel tilesToPlay = new JPanel();
    JLabel scoreP1 = new JLabel("",SwingConstants.CENTER);
    JLabel scoreP2 = new JLabel("",SwingConstants.CENTER);
    boolean victory = false;
    Random rand = new Random();
    
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    
    private final Game game;
    private boolean view;
    
    /**
     * Constructeur View.
     * @param game
     * @param view
     */
    public View(Game game, boolean view){
        this.game = game;
        this.view = view;
    }

    public void setView(boolean i){
        this.view = i;
    }
    
    public boolean getView(){
        return this.view;
    }
    
    public void printWindowView(){
        buildContentPane();
        graphicView();
        paintGrid(game.getBoard(1), tab1);
        paintGrid(game.getBoard(2), tab2);
        window.setVisible(true);
    }
    
    public void graphicView(){
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1280,650);
        
        window.add(gridP1);
        window.add(gridP2);
        window.add(p1);
        window.add(p2);
        window.add(tilesToDraw);
        window.add(tilesToPlay);
        window.add(scoreP1);
        window.add(scoreP2);
        
        JPanel tmpPanel = new JPanel();
        window.add(tmpPanel);
    }
    
    public void update(int currentPlayer){
        updateGridPlay();
        updateGridDeck();
        updateGridPlay();
        updatePlayerTurn(currentPlayer);
    }
    
    public void updateGridPlay(){
        paintGrid(game.getBoard(1), tab1);
        paintGrid(game.getBoard(2), tab2);
    }
    
    public void updateGridDeck(){
        for(int i = 0; i<4; i++){
            for(int j = 0; j<3; j++){
                playableTile.getPanel(i,j).removeAll();
                tileInDeck.getPanel(i,j).removeAll();
            }
        }
        paintDecks(this.game.getToPlay(), playableTile);
        paintDecks(this.game.getToChoose(), tileInDeck);
    }
    
    public void updateScore(){
        scoreP1.setText("Score J1 : "+this.game.getScore(1));
        scoreP2.setText("Score J2 : "+this.game.getScore(2));
        scoreP1.updateUI();
        scoreP2.updateUI();
        if (this.game.getScore(1) > this.game.getScore(2)){
            scoreP1.setBorder(p1Border);
            paintVictory(scoreP1, Color.BLUE);
        }else if (this.game.getScore(1) < this.game.getScore(2)){
            scoreP2.setBorder(p2Border);
            paintVictory(scoreP2, Color.RED);
        }
    }
    
    public void updatePlayerTurn(int currentPlayer){
        if(currentPlayer==1){
            p1.setBackground(Color.decode("#505DE2"));
            p2.setBackground(Color.decode("#991A1A"));
        }else if(currentPlayer==2){
            p1.setBackground(Color.decode("#232A74"));
            p2.setBackground(Color.decode("#DC2727"));
        }else{
            p1.setBackground(Color.decode("#232A74"));
            p2.setBackground(Color.decode("#991A1A"));
        }
        p1.updateUI();
        p2.updateUI();
    }
    
    public void buildContentPane(){
        JLabel player1 = new JLabel("Joueur 1");
        player1.setFont(new Font("Serif", Font.BOLD, 30));
        player1.setForeground(Color.WHITE);
        p1.setBorder(border);
        
        JLabel player2 = new JLabel("Joueur 2");
        player2.setFont(new Font("Serif", Font.BOLD, 30));
        player2.setForeground(Color.WHITE);
        p2.setBorder(border);
        
        JLabel deckText = new JLabel("Pioche :");
        tilesToDraw.setBorder(border);
        JLabel playingText = new JLabel("Pièces à Jouer :");
        tilesToPlay.setBorder(border);
        
        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.insets = new Insets(2, 2, 2, 2);
        textConstraints.gridx = 0;
        textConstraints.gridy = 0;
        textConstraints.ipadx = 150;
        textConstraints.ipady = 10;
        textConstraints.ipadx = 100;
        textConstraints.ipadx = 5;
        p1.add(player1,textConstraints);
        p2.add(player2,textConstraints);
        
        tilesToDraw.add(deckText);
        tilesToPlay.add(playingText);
        tilesToDraw.add(tilesInDeck);
        tilesToPlay.add(playableTiles);
        
        scoreP1.setFont(new Font("Serif", Font.BOLD, 30));
        scoreP1.setForeground(Color.BLUE);
        scoreP1.setName("p1");
        scoreP2.setFont(new Font("Serif", Font.BOLD, 30));
        scoreP2.setForeground(Color.RED);
        scoreP2.setName("p2");
        
        
        gridP1.setBounds(0,0,500,500);
        gridP2.setBounds(500,0,500,500);
        p1.setBounds(50,510,450,65);
        p2.setBounds(550,510,450,65);
        tilesToDraw.setBounds(1010,50,220,260);
        tilesToPlay.setBounds(1010,315,220,260);
        tilesToPlay.setVisible(false);
        scoreP1.setBounds(1010,315,220,90);
        scoreP2.setBounds(1010,410,220,90);
        //scoreP1.setVisible(false);
        //scoreP2.setVisible(false);
    }
    
    private void paintDecks(ArrayList<Domino> deck, GridDeck grid){
        for(int i = 0; i < deck.size(); i++){
            for(int j=0; j < deck.size()-1; j++){
                if(deck.get(0) != null && deck.get(1) != null && deck.get(2) != null && deck.get(3) != null){
                    if(j==0){
                        String key = deck.get(i).getTileL().getType();
                        key = chooseImage(key, deck.get(i).getTileL().getCrown());
                        JLabel img = new JLabel(new ImageIcon("image/"+key+".png"));
                        grid.getPanel(i,j).add(img);
                    }if(j==1){
                        String key = deck.get(i).getTileR().getType();
                        key = chooseImage(key, deck.get(i).getTileR().getCrown());
                        JLabel img = new JLabel(new ImageIcon("image/"+key+".png"));
                        grid.getPanel(i,j).add(img);
                    }if(j==2){
                        if(deck.get(i).getPlayer() != null){
                            JLabel lab = new JLabel("J"+Integer.toString(deck.get(i).getPlayer()),SwingConstants.CENTER);
                            if(deck.get(i).getPlayer()==1){
                                lab.setForeground(Color.BLUE);
                            }else{
                                lab.setForeground(Color.RED);
                            }
                            lab.setFont(new Font("Serif", Font.BOLD, 16));
                            grid.getPanel(i,j).add(lab);
                        }
                    }
                    grid.getPanel(i,j).updateUI();
                }
            }
        }
    }
    
    private void paintGrid(Board board, GridPlay grid){
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                if(board.getTile(i,j) != null && "empty".equals(grid.getName(i,j))){
                    String key = board.getFieldType(i,j);
                    if("chateau".equals(key)){
                        if("p1".equals(grid.getPlayer())){
                            JLabel img = new JLabel(new ImageIcon("image/dominoDepartB.png"));
                            grid.getPanel(i,j).add(img);
                            grid.setName(i,j,"full");
                        }else{
                            JLabel img = new JLabel(new ImageIcon("image/dominoDepartR.png"));
                            grid.getPanel(i,j).add(img);
                            grid.setName(i,j,"full");
                        }
                    }else{
                        key = chooseImage(key, board.getCrown(i,j));
                        JLabel img = new JLabel(new ImageIcon("image/"+key+".png"));
                        grid.getPanel(i,j).add(img);
                        grid.setName(i,j,"full");
                    }
                    grid.getPanel(i,j).updateUI();
                }
            }
        }
    }
    
    private void paintVictory(JLabel score, Color mainColor){
        while(true){
            if(!victory){
                viewWait();
                score.setForeground(Color.WHITE);
                score.setBackground(mainColor);
                score.setOpaque(true);
                victory=true;
            }else{
                viewWait();
                score.setForeground(mainColor);
                score.setBackground(Color.WHITE);
                score.setOpaque(true);
                victory=false;
            }
            score.updateUI();
        }
    }
    
    private String chooseImage(String imageKey, int crownCount){
        if(crownCount != 0 ){
            imageKey+="-"+crownCount;
        }else{
            if("forêt".equals(imageKey)){
                imageKey+=Integer.toString(rand.nextInt(2));
            }
            else if(!"mine".equals(imageKey)){
                imageKey+=Integer.toString(rand.nextInt(3));
            }
        }
        return imageKey;
    }
    
    public void viewWait(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
        }
    }
    
    public void printDeckView(){
        tilesToPlay.setVisible(true);
    }
    
    public void unprintDeckView(){
        tilesToDraw.setVisible(false);
        tilesToPlay.setBounds(1010,50,220,260);
        tilesToDraw.updateUI();
        tilesToPlay.updateUI();
    }
    
    public void printScoreView(){
        scoreP1.setVisible(true);
        scoreP2.setVisible(true);
        updateScore();
    }
    
    /**
     * Permet d'afficher les deux plateaux du jeu
     */
    public void printGame(){
        System.out.println("Plateau joueur 1");
        printBoard(game.getBoard(1));
        System.out.println("Plateau joueur 2");
        printBoard(game.getBoard(2));
    }

    /**
     * Permet d'afficher un plateau
     * @param board
     */
    public void printBoard(Board board){
        String tmp = "";
        for(int z = 1; z<10; z++){
                System.out.print("   "+z);
        }
        System.out.println("");
        for(int i = 0; i<9; i++){
            tmp+=i+1 + ANSI_WHITE+ "|"+ANSI_RESET;
            for(int j = 0; j<9; j++){
                if(board.getTile(i,j) == null){
                    tmp+="   ";
                }else{
                    String key = board.getFieldType(i,j);
                    if("chateau".equals(key)){
                        tmp+=ANSI_WHITE_BACKGROUND + " C " + ANSI_RESET;
                    }else{
                        tmp+=color.get(key);
                        if(board.getCrown(i,j) == 0 ){
                            tmp+="   ";
                        }else{
                            tmp+=" "+ANSI_WHITE+board.getCrown(i,j)+" ";
                        }
                        tmp+=ANSI_RESET;
                    }
                }
                tmp+=ANSI_WHITE+"|"+ANSI_RESET;
            }
            tmp+="\n\n";
        }
        System.out.println(tmp);
    }

    /**
     * permet d'afficher la liste des dominos a jouer, et des dominos a choisir
     */
    public void printDeck(){
        ArrayList<Domino> toPlay = this.game.getToPlay();
        ArrayList<Domino> toChoose = this.game.getToChoose();

        for(int i = 0; i < toPlay.size(); i++){
            if(toPlay.get(0) != null && toPlay.get(1) != null && toPlay.get(2) != null && toPlay.get(3) != null){
                printDomino(toPlay.get(i));
                if(toChoose.get(0) != null && toChoose.get(1) != null && toChoose.get(2) != null && toChoose.get(3) != null){
                    System.out.print("   ");
                    printDomino(toChoose.get(i));
                }
                
                System.out.println("");
            }else{
                printDomino(toChoose.get(i));
                System.out.println("");
            }
        }

    }

    /**
     * permet d'afficher un domino
     * @param domino
     */
    public void printDomino(Domino domino){
        System.out.print(color.get(domino.getTileL().getType())+ANSI_WHITE+" "+domino.getTileL().getCrown()+" "+ANSI_RESET+"|");
        System.out.print(color.get(domino.getTileR().getType())+ANSI_WHITE+" "+domino.getTileR().getCrown()+" "+ANSI_RESET);
        if (domino.getPlayer()==null){
            System.out.print("    ");
        }else{
            System.out.print(" ["+domino.getPlayer()+"]");
        }
    }

    /**
     * Permet d'afficher le tour du joueur
     * @param currentPlayer
     */
    public void printPlayerTurn(Integer currentPlayer){
        System.out.println("C'est le tour du joueur " + currentPlayer);
    }

    /**
     * Permet d'afficher les scores en fin de partie
     */
    public void printScore(){
        System.out.println("Fin de la partie  !");
        System.out.println("Scores : ");
        System.out.println("Joueur 1 : " + this.game.getScore(1));
        System.out.println("Joueur 2 : " + this.game.getScore(2));
        if(this.game.getScore(1)>this.game.getScore(2))
            System.out.println("Victoire du Joueur 1 !");
        else if(this.game.getScore(1)<this.game.getScore(2))
            System.out.println("Victoire du Joueur 2 !");
        else
            System.out.println("Egalité !");
    }
    
    /**
     * permet d'afficher une impossibilité pour posé une carte
     */
    public void printNoPossibleChoice(){
        System.out.println("aucun choix possible: pièce défaussée");
    }
    
    /**
     * permet d'afficher les demandes de coordonnées
     * @param n
     */
    public static void printDominoPlacement(int n){
        if(n==0)
            System.out.println("Choisissez les coordonées de la tuile gauche");
        else
            System.out.println("Choisissez les coordonées de la tuile droite"); 
    } 

    /**
     * permet d'afficher la demande d'observer une partie IA contre IA
     */
    public static void printAiVsAi(){
        System.out.println("Voulez-vous voir une partie ia vs ia ?    [0: Non / 1: Oui]");
    }
    
    /**
     * permet d'afficher la demande de difficulté de l'IA
     * @param player
     */
    public static void printChooseAI(int player){
        System.out.println("Choisissez l'ia du joueur " + player + " : 1- AIRandom 2- AIScore");
    }
    
    /**
     * permet d'afficher la demande de jouabilité contre une IA
     */
    public static void printAiChoice(){
        System.out.println("Voulez-vous jouer contre une ia ?    [0: Non / 1: Oui]");
    }

    /**
     * permet d'afficher la demande de choix de domino
     */
    public static void printDominoChoice(){
        System.out.println("Entrez un nombre entre 1 et 4 pour choisir un domino");
    }

    /**
     * permet d'afficher la demande de selection d'une valeur
     * @param inf
     * @param sup
     */
    public static void printOutOfBoundary(int inf, int sup){
        System.out.println("Veuillez donner une valeur entre " + inf + " et " + sup);
    }

    /**
     * permet d'afficher l'invalidité d'un choix
     */
    public static void printNotAvailable(){
        System.out.println("Ce choix n'est pas possible");
    }
    
    /**
     * permet d'afficher l'invalidité d'une coordonnée
     */
    public static void printFailed(){
        System.out.println("Vous devez saisir sous le format y,x !");
    }
    
    public static void printViewChoice(){
        System.out.println("Voulez-vous jouer avec une fenetre pour observer la partie ?    [0: Non / 1: Oui]");
    }
}
