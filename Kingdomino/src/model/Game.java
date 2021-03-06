/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.*;
import view.*;
/**
 * Classe représentant le jeu.
 * @author Vincent Léo, Leroy Clémentine, Besnehard Pierre, Bellebon Alexandre
 */
public class Game {

// Attributs

    private final Board board1 = new Board();
    private final Board board2 = new Board();
    private Domino selectedDomino;
    private final int sizeToPlay = 4;

    private ArrayList<Domino> deck = new ArrayList<Domino>();
    private ArrayList<Domino> toPlay = new ArrayList<Domino>(sizeToPlay);
    private ArrayList<Domino> toChoose = new ArrayList<Domino>(sizeToPlay);

    private final ArrayList<ArrayList<ArrayList<Integer>>> possiblePlacement = new ArrayList<ArrayList<ArrayList<Integer>>>();
 
    /**
     * déclaration d'un Hashmap qui permettra de relier une couleur au type d'une tuile
     */
    public static HashMap<String,String> color;

    private ArrayList<Integer> tileTraveled = new ArrayList<>();
    
    /**
     * Constructeur Game.
     */
    public Game(){
        this.deck = generateDeck();
        for (int i = 0; i < sizeToPlay; i++) {
            this.toPlay.add(null);
        }
        this.color = new HashMap<String,String>();
        this.color.put("marécage","\u001B[46m");
        this.color.put("mer","\u001B[44m");
        this.color.put("plaine","\u001B[42m");
        this.color.put("champs","\u001B[43m");
        this.color.put("forêt","\u001B[45m");
        this.color.put("mine","\u001B[41m");
        
    }

    /**
     * Permet de récuperer un plateau en fonction du joueur qui joue.
     * @param playerNumber
     * @return plateau du joueur
     */
    public Board getBoard(int playerNumber){
        //board1.testBoard1();
        if (playerNumber==1)
            return this.board1;
        return this.board2;
    }

    /**
     * Permet de récupèrer le score d'un plateau.
     * @param boardNumber
     * @return score du plateau
     */
    public int getScore(int boardNumber){
        this.tileTraveled.clear();
        int score = 0;
        Board board;
        if(boardNumber == 1)
            board = this.board1;
        else
            board = this.board2;
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                if(board.getTile(i,j)!=null && board.getCrown(i,j)!=0 && !this.tileTraveled.contains(i*9+j))
                    score += scoreField(board,board.getTile(i,j),i,j);
            }
        }
        return score;
    }

    /**
     * Permet de récupérer le score du plateau de l'ia
     * @param aiBoard
     * @return score du plateau de l'ia
     */
    public int getScoreAI(Board aiBoard){
        this.tileTraveled.clear();
        int score = 0;
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                if(aiBoard.getTile(i,j)!=null && aiBoard.getCrown(i,j)!=0 && !this.tileTraveled.contains(i*9+j))
                    score += scoreField(aiBoard,aiBoard.getTile(i,j),i,j);
            }
        }
        return score;
    }

    /**
     * Permet a un joueur de selectionner un domino dans la pioche
     */
    public void getDominosFromDeck(){
        ArrayList<Domino> picked = new ArrayList<Domino>();
        for(int i=0;i<sizeToPlay;i++){
            int sum = this.deck.size();
            int random = new Random().nextInt(sum);
            picked.add(deck.get(random));
            this.deck.remove(deck.get(random));
        }
        Collections.sort(picked);
        this.toChoose = picked;
    }
    
    /**
     * demande des coordonnées, attend une réponse de l'utilisateur. Vérifie si la réponse attendu est correcte.
     * @param board
     * @return 
     * @throws InputMismatchException 
     */
    public ArrayList validCoo(Board board) throws InputMismatchException{
        int cooX =-1;
        int cooY =-1;
        boolean valid = false;

        while(!valid) {
            Scanner scan = new Scanner(System.in);

            try{
                String cooScan = scan.next();

                Scanner scanComma = new Scanner(cooScan).useDelimiter(",");
                cooX = scanComma.nextInt()-1;
                cooY = scanComma.nextInt()-1;
                scanComma.close();
                ArrayList<Integer>cooTile = new ArrayList<Integer>();
                cooTile.add(cooX);cooTile.add(cooY);
                if(((cooX < 0) || (cooX > 8)) || ((cooY < 0) || (cooY > 8) || (!board.verifTile(cooTile))))
                    View.printNotAvailable();
                else
                    valid = true;
            }
            catch (Exception e) {
                View.printFailed();
                continue;
            }
        }
        return new ArrayList<Integer>(Arrays.asList((Integer)cooX, (Integer)cooY));
    }
    
    /**
     * Enregistre la liste total des placement possible pour un domino et pour un plateau
     * @param d
     * @param plateau
     * @return Liste des placements possibles pour un domino
     */
    public ArrayList<ArrayList<ArrayList<Integer>>> possiblePlacement(Domino d, Board plateau){
        this.selectedDomino=d;
        possiblePlacement.clear();
        Board plateauCopy = new Board();
        for(int i=0;i<=8;i++){
            for(int j=0;j<=8;j++){
                plateauCopy.cloneFrom(plateau);
                ArrayList<Integer> coo = new ArrayList<Integer>(Arrays.asList((Integer)i, (Integer)j));
                if(plateauCopy.verifTile(coo)){
                    canBePlaced(d,coo,plateauCopy);
                }
            }
        }
        return possiblePlacement;
    }
   
    /**
     * Permet de Calculer le score pour un type de terrain sur un plateau
     * @param board
     * @param tile
     * @param i
     * @param j
     * @return Le combre de couronne * le nombre de Tile 
     */
    private int scoreField(Board board, Tile tile, int i, int j){
        ArrayList<Integer> infoField = new ArrayList<>();
        infoField.add(0);infoField.add(0);
        infoField= nextTile(board,tile,i,j, infoField);  // calcul.get(0) -> nombre de tuile identique
        return infoField.get(0)*infoField.get(1);            // calcul.get(1) -> nombre de couronne sur la zonne 
    }
    
    /**
     * Parcours récursif des Tile de même type et de récupérer les infos utiles pour le scores
     * @param board
     * @param tile
     * @param i
     * @param j
     * @param infoField
     * @return listes des informations pour un type de terrain : nombre de terrain et nombre de couronnes
     */
    private ArrayList<Integer> nextTile(Board board, Tile tile, int i,int j, ArrayList<Integer> infoField){
        this.tileTraveled.add(i*9+j);
        infoField.set(0, infoField.get(0)+1);                                                   //On ajoute 1 au compteur de tuile identique
        if(tile.getCrown()!=0)                                                                  // si il y a des couronnes sur la tuile, on l'ajoute dans le total des couronnes
            infoField.set(1, infoField.get(1)+tile.getCrown());
        if(i!=0 && board.getTile(i-1,j)!=null && tile.getType() == board.getFieldType(i-1,j) && !this.tileTraveled.contains((i-1)*9+j)){ //On veut parcourir l'ensemble des tile composant le terrain à calculer
            infoField = nextTile(board, board.getTile(i-1,j), i-1,j,infoField);                            //(ici on monte avec i-1), 
        }                                                                                                        //prevMove permet d'indiquer le mouvement qu'on a fait pour éviter de calculer 2 fois une même tuile 
        if(j!=8 && board.getTile(i,j+1)!=null && tile.getType() == board.getFieldType(i,j+1) && !this.tileTraveled.contains(i*9+(j+1)) ){
            infoField = nextTile(board, board.getTile(i,j+1),i,j+1, infoField);
        }
        if(i!=8 && board.getTile(i+1,j)!=null && tile.getType() == board.getFieldType(i+1,j) && !this.tileTraveled.contains((i+1)*9+j)){
            infoField = nextTile(board, board.getTile(i+1,j), i+1,j,infoField);
        }
        if(j!=0 && board.getTile(i,j-1)!=null && tile.getType() == board.getFieldType(i,j-1) && !this.tileTraveled.contains(i*9+(j-1))){
            infoField = nextTile(board, board.getTile(i,j-1),i,j-1,infoField);
        }
        return infoField;
    }
    

    /**
     * Créer la liste de touts les dominos
     * @return pioche de jeu
     */
    private ArrayList<Domino> generateDeck(){
        generatorTile(new Tile("champs",0),new Tile("champs",0),2);
        generatorTile(new Tile("forêt",0),new Tile("forêt",0),4);
        generatorTile(new Tile("mer",0),new Tile("mer",0),3);                
        generatorTile(new Tile("plaine",0),new Tile("plaine",0),2);                
        generatorTile(new Tile("marécage",0),new Tile("marécage",0),1);                
        generatorTile(new Tile("champs",0),new Tile("forêt",0),1);                
        generatorTile(new Tile("champs",0),new Tile("mer",0),1);                
        generatorTile(new Tile("champs",0),new Tile("plaine",0),1);                
        generatorTile(new Tile("champs",0),new Tile("marécage",0),1);                
        generatorTile(new Tile("forêt",0),new Tile("mer",0),1);                
        generatorTile(new Tile("forêt",0),new Tile("plaine",0),1);                
        generatorTile(new Tile("champs",1),new Tile("forêt",0),1);                
        generatorTile(new Tile("champs",1),new Tile("mer",0),1);                
        generatorTile(new Tile("champs",1),new Tile("plaine",0),1);                
        generatorTile(new Tile("champs",1),new Tile("marécage",0),1);                
        generatorTile(new Tile("champs",1),new Tile("mine",0),1);                
        generatorTile(new Tile("forêt",1),new Tile("champs",0),4);               
        generatorTile(new Tile("forêt",1),new Tile("mer",0),1);                
        generatorTile(new Tile("forêt",1),new Tile("plaine",0),1);                
        generatorTile(new Tile("mer",1),new Tile("champs",0),2);                
        generatorTile(new Tile("mer",1),new Tile("forêt",0),4);                
        generatorTile(new Tile("champs",0),new Tile("plaine",1),1);                
        generatorTile(new Tile("mer",0),new Tile("plaine",1),1);                    
        generatorTile(new Tile("champs",0),new Tile("marécage",1),1);                
        generatorTile(new Tile("plaine",0),new Tile("marécage",1),1);                
        generatorTile(new Tile("mine",1),new Tile("champs",0),1);                
        generatorTile(new Tile("champs",0),new Tile("plaine",2),1);                
        generatorTile(new Tile("mer",0),new Tile("plaine",2),1);                
        generatorTile(new Tile("champs",0),new Tile("marécage",2),1);                
        generatorTile(new Tile("plaine",0),new Tile("marécage",2),1);                
        generatorTile(new Tile("mine",2),new Tile("champs",0),1);                
        generatorTile(new Tile("marécage",0),new Tile("mine",2),2);                
        generatorTile(new Tile("champs",0),new Tile("mine",3),1);                
        return deck;
    }

    /**
     * Permet de créer un Domino 
     * @param tileL
     * @param tileR
     * @param nb 
     */
    private void generatorTile(Tile tileL, Tile tileR, int nb){
        int currentNumber = this.deck.size();
        for(int i=0;i<nb;i++){
            Domino domino = new Domino(tileL,tileR,currentNumber++);
            deck.add(domino);
        }
    }
    
    /**
     * Vérifie si le placement d'un domino est possible
     * @param d
     * @param coo
     * @param plateau 
     */
    private void canBePlaced(Domino d, ArrayList<Integer> coo,Board plateau){
        Board plateauCopy = new Board();
        plateau.addTile(d.getTileL(), coo);
        ArrayList<ArrayList<Integer>> tmp = new ArrayList<ArrayList<Integer>>();

        tmp.add(new ArrayList (Arrays.asList(coo.get(0), coo.get(1)-1)));
        tmp.add(new ArrayList (Arrays.asList(coo.get(0), coo.get(1)+1)));
        tmp.add(new ArrayList (Arrays.asList(coo.get(0)-1, coo.get(1))));
        tmp.add(new ArrayList (Arrays.asList(coo.get(0)+1, coo.get(1))));

        for(int i = 0; i < 4 ; i++) {
            if(plateau.verifTile(tmp.get(i))){
                ArrayList<ArrayList<Integer>> cooDomino = new ArrayList<ArrayList<Integer>>();
                cooDomino.add(coo);
                cooDomino.add(tmp.get(i));
                plateauCopy.cloneFrom(plateau); plateauCopy.addTile(d.getTileR(), tmp.get(i));
                if(plateauCopy.verifCoordDomino(cooDomino) && plateauCopy.verifPlacement()){
                    if(!possiblePlacement.contains(cooDomino)){
                        possiblePlacement.add(cooDomino);
                    }
                }
            }
        }
    }
    
    /**
     * Récupère la pioche du jeu
     * @return Pioche
     */
    public ArrayList<Domino> getDeck() {
        return deck;
    }

    /**
     * récupère la liste des dominos a joué
     * @return les dominos a joué
     */
    public ArrayList<Domino> getToPlay() {
        return toPlay;
    }
    
    /**
     * récupère la liste des dominos a choisir
     * @return les dominos a choisir
     */
    public ArrayList<Domino> getToChoose() {
        return toChoose;
    }

    /**
     * remise à zéro de la liste des dominos a choisir
     */
    public void resetToChoose(){
        this.toChoose = new ArrayList<Domino>(sizeToPlay);
        for (int i = 0; i < sizeToPlay; i++) {
            this.toChoose.add(null);
        }
    }
    
    /**
     * Récupère un domino séléctionner par le joueur
     * @return domino sélectionner
     */
    public Domino getSelectedDomino() {
        return selectedDomino;
    }
    
    /**
     * Met a jour la liste des dominos a jouer 
     * @param toPlay
     */
    public void setToPlay(ArrayList<Domino> toPlay) {
        this.toPlay = toPlay;
    }

    /**
     * accésseur pour possiblePlacement 
     * @return possiblePlacement
     */
    public ArrayList<ArrayList<ArrayList<Integer>>> getPossiblePlacement() {
        return possiblePlacement;
    }
}
