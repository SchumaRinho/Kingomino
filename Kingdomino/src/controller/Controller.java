/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.View;
import model.*;
import java.util.*;

/**
 *
 * @author leovi
 */
public class Controller {


    private Board plateau1;
    private Board plateau2;
    private View vue;

    ArrayList<Domino> pioche = new ArrayList<Domino>();
    ArrayList<Domino> aJouer = new ArrayList<Domino>(4);
    ArrayList<Domino> aPiocher = new ArrayList<Domino>(4);

    private ArrayList<Integer> tileTraveled = new ArrayList<>();

    private AIRandom aiRandom;
    private boolean ai=false;
    

    public Controller (Board plateau1, Board plateau2, View vue){
        this.plateau1 = plateau1;
        this.plateau2 = plateau2;
        this.vue = vue;
        this.pioche = generatePiecePioche();
        for (int i = 0; i < 4; i++) {
            this.aJouer.add(null);
        }
        main();
    }
    
    private void main(){
        Integer jActuel;
        int choix;
        vue.choixJeuIA();
        if(choixValide(0,1)==1){
            aiRandom = new AIRandom(plateau2);
            ai=true;
        }

        for(int i = 1; i <= 4; i++){
            this.aPiocher = getPieceFromPioche();
            System.out.println("Début tour n°"+i);
            if(i==1){
                vue.affichePlateaux();
                int n;
                Domino d;
                ArrayList<Integer> rois = new ArrayList<Integer>();
                rois.add(1);rois.add(1);rois.add(2);rois.add(2);
                while(rois.size() !=0){
                    vue.affichePioche(null,aPiocher);
                    n = new Random().nextInt(rois.size());
                    jActuel =  rois.get(n);
                    
                    if((ai && jActuel==2)){
                        choix = aiRandom.getChoix(1,4);
                        System.out.println("choix = "+choix);
                        while(aJouer.contains(aPiocher.get(choix-1))){
                            System.out.println("choix = "+choix);
                            choix = aiRandom.getChoix(1,4);
                        }
                        vue.choixIA(choix);
                    }else{
                        choix = choixPiece(jActuel,aPiocher.size());
                        while(aJouer.contains(aPiocher.get(choix-1))){
                            vue.invalidDomino();
                            choix = choixPiece(jActuel,aPiocher.size());;
                        }
                        
                    }

                    d = aPiocher.get(choix-1);
                    d.setPlayer(jActuel);
                    this.aJouer.set(choix-1,d);
                    rois.remove(n);
                }
            }else{
                ArrayList<ArrayList<Integer>> coo;
                for(Domino d: aJouer){
                    vue.affichePlateaux();
                    vue.affichePioche(this.aJouer,this.aPiocher);
                    jActuel = d.getPlayer();
                    vue.choixPlacement(jActuel);
                    if(jActuel==1){
                        coo = choixPlacement(jActuel);
                        this.plateau1.addDomino(d,coo);
                    }else{
                        if(ai){
                            coo = choixPlacement(jActuel);
                        }else{
                            coo = choixPlacement(jActuel);
                        }
                        this.plateau2.addDomino(d,coo);
                    }
                    d.setPlayer(null);
                    vue.affichePlateaux();
                    vue.affichePioche(this.aJouer,this.aPiocher);
                    if(ai && jActuel==2){
                        choix = aiRandom.getChoix(1,4);
                        while(aPiocher.get(choix-1).getPlayer()!=null){
                            choix = aiRandom.getChoix(1,4);
                        }
                        vue.choixIA(choix);
                    }else{
                        choix = choixPiece(jActuel,aPiocher.size());
                        while(aPiocher.get(choix-1).getPlayer()!=null){
                            vue.invalidDomino();
                            choix = choixPiece(jActuel,aPiocher.size());;
                        }
                    }
                    d = aPiocher.get(choix-1);
                    d.setPlayer(jActuel);
                }
                this.aJouer = new ArrayList<Domino>(this.aPiocher);
            }
        }
        vue.finPartie(calcScore(plateau1),calcScore(plateau2));
    }

    private ArrayList<ArrayList<Integer>> choixPlacement(Integer player){
        ArrayList choix = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < 2; i++){ 
            choix.add(valideCoordonnees());
        }
        return choix;
    }

    private Integer choixCoordonnées(){
        return choixValide(1,9)-1;
    }

    private Integer choixPiece(int joueur,int n){
        vue.choixPiece(joueur,n);
        return choixValide(1,n);
    }
    
    private ArrayList<Domino> generatePiecePioche(){
        int numPieces=1;
        generatorPiece(new Tile("champs",0),new Tile("champs",0),2);
        generatorPiece(new Tile("forêt",0),new Tile("forêt",0),4);
        generatorPiece(new Tile("mer",0),new Tile("mer",0),3);                
        generatorPiece(new Tile("plaine",0),new Tile("plaine",0),2);                
        generatorPiece(new Tile("marécage",0),new Tile("marécage",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("forêt",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("mer",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("plaine",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("marécage",0),1);                
        generatorPiece(new Tile("forêt",0),new Tile("mer",0),1);                
        generatorPiece(new Tile("forêt",0),new Tile("plaine",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("forêt",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("mer",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("plaine",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("marécage",0),1);                
        generatorPiece(new Tile("champs",1),new Tile("mine",0),1);                
        generatorPiece(new Tile("forêt",1),new Tile("champs",0),4);               
        generatorPiece(new Tile("forêt",1),new Tile("mer",0),1);                
        generatorPiece(new Tile("forêt",1),new Tile("plaine",0),1);                
        generatorPiece(new Tile("mer",1),new Tile("champs",0),2);                
        generatorPiece(new Tile("mer",1),new Tile("forêt",0),4);                
        generatorPiece(new Tile("champs",0),new Tile("plaine",1),1);                
        generatorPiece(new Tile("mer",0),new Tile("plaine",1),1);                    
        generatorPiece(new Tile("champs",0),new Tile("marécage",1),1);                
        generatorPiece(new Tile("plaine",0),new Tile("marécage",1),1);                
        generatorPiece(new Tile("mine",1),new Tile("champs",0),1);                
        generatorPiece(new Tile("champs",0),new Tile("plaine",2),1);                
        generatorPiece(new Tile("mer",0),new Tile("plaine",2),1);                
        generatorPiece(new Tile("champs",0),new Tile("marécage",2),1);                
        generatorPiece(new Tile("plaine",0),new Tile("marécage",2),1);                
        generatorPiece(new Tile("mine",2),new Tile("champs",0),1);                
        generatorPiece(new Tile("marécage",0),new Tile("mine",2),2);                
        generatorPiece(new Tile("champs",0),new Tile("mine",3),1);                
        return pioche;
    }
    
    private void generatorPiece(Tile Gauche, Tile Droite, int nb){
        int num_courant = pioche.size();
        for(int i=0;i<nb;i++){
            Domino p = new Domino(Gauche,Droite,num_courant++);
            pioche.add(p);
        }
    }
    
    private int calcScore(Board plateau){
        this.tileTraveled.clear();
        int score = 0;
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                if(plateau.getTile(i,j)!=null && plateau.getCrown(i,j)!=0 && !this.tileTraveled.contains(i*9+j))
                    score += scoreField(plateau,plateau.getTile(i,j),i,j);
            }
        }
        return score;
    }
    
    private int scoreField(Board plateau, Tile tile, int i, int j){
        ArrayList<Integer> infoField = new ArrayList<>();
        infoField.add(0);infoField.add(0);
        infoField= nextTile(plateau,tile,i,j, infoField);  // calcul.get(0) -> nombre de tuile identique
        return infoField.get(0)*infoField.get(1);            // calcul.get(1) -> nombre de couronne sur la zonne
        
    }
    
    private ArrayList<Integer> nextTile(Board plateau, Tile tile, int i,int j, ArrayList<Integer> infoField){
        this.tileTraveled.add(i*9+j);
        infoField.set(0, infoField.get(0)+1);                                                   //On ajoute 1 au compteur de tuile identique
        if(tile.getCrown()!=0)                                                                  // si il y a des couronnes sur la tuile, on l'ajoute dans le total des couronnes
            infoField.set(1, infoField.get(1)+tile.getCrown());
        if(i>0 && plateau.getTile(i-1,j)!=null && tile.getType() == plateau.getFieldType(i-1,j) && !this.tileTraveled.contains((i-1)*9+j)){ //On veut parcourir l'ensemble des tile composant le terrain à calculer
            infoField = nextTile(plateau, plateau.getTile(i-1,j), i-1,j,infoField);                            //(ici on monte avec i-1), 
        }                                                                                                        //prevMove permet d'indiquer le mouvement qu'on a fait pour éviter de calculer 2 fois une même tuile 
        if(j!=8 && plateau.getTile(i,j+1)!=null && tile.getType() == plateau.getFieldType(i,j+1) && !this.tileTraveled.contains(i*9+(j+1)) ){
            infoField = nextTile(plateau, plateau.getTile(i,j+1),i,j+1, infoField);
        }
        if(i<9 && plateau.getTile(i+1,j)!=null && tile.getType() == plateau.getFieldType(i+1,j) && !this.tileTraveled.contains((i+1)*9+j)){
            infoField = nextTile(plateau, plateau.getTile(i+1,j), i+1,j,infoField);
        }
        if(j!=0 && plateau.getTile(i,j-1)!=null && tile.getType() == plateau.getFieldType(i,j-1) && !this.tileTraveled.contains(i*9+(j-1))){
            infoField = nextTile(plateau, plateau.getTile(i,j-1),i,j-1,infoField);
        }
        return infoField;
    }
    /*
    private ArrayList<Integer> listPlacement(Domino newDomino){
        ArrayList<Integer> listPlacement = new ArrayList<Integer>();
    }*/
    
    private boolean verifPlacement(Board plateau){
        int iMini=100,iMax=0,jMini=100,jMax=0;  //pour vérifier qu'on soit dans un tableau de 5x5
            for(int i = 0; i<9; i++){           //On récupere le plus petit i et j, et on les soustrait au plus grand i et j 
                for(int j = 0; j<9; j++){
                    if(plateau.getTile(i,j)!=null){
                        if(iMini==100)
                            iMini=i;
                        if(i>iMax)
                            iMax=i;
                        if(j<jMini)
                            jMini=j;
                        if(j>jMax)
                            jMax=j;
                    }
                }
            }
        if((iMax-iMini)+1>5)
            return false;
        if((jMax-jMini)+1>5)
            return false;
        return true;
    }
    
    private ArrayList valideCoordonnees() throws InputMismatchException{
		int cooPlaceX =-1;
		int cooPlaceY =-1;
		boolean valide = false;
		
		while(!valide) {
			Scanner sc = new Scanner(System.in);
			
			try{
				String cooScan = sc.next();

				Scanner scanVirgule = new Scanner(cooScan).useDelimiter(",");
				cooPlaceX = scanVirgule.nextInt()-1;
				cooPlaceY = scanVirgule.nextInt()-1;
				scanVirgule.close();
				
				if(((cooPlaceX < 0) || (cooPlaceX > 9)) || ((cooPlaceY < 0) || (cooPlaceY > 9)))
					System.out.println("Coordonnées non valide : vous avez entrée des coordonnées inférieur a zero ou supérieur a la grandeur du plateau");
				else
					valide = true;
			}
			catch (Exception e) {
				System.out.println("Vous devez saisir sous le format 2,3 !");
				continue;
            }

		}
		return new ArrayList<Integer>(Arrays.asList((Integer)cooPlaceX, (Integer)cooPlaceY));
	}
    
    public ArrayList<Domino> getPieceFromPioche(){
        ArrayList<Domino> tirage = new ArrayList<Domino>();
        for(int i=0;i<4;i++){
            int sum = pioche.size();
            int random = new Random().nextInt(sum);
            tirage.add(pioche.get(random));
            this.pioche.remove(pioche.get(random));
        }
        Collections.sort(tirage);
        return tirage;
    }
    /* Obsolete
    private Pieces update(String type, boolean pGauche, Pieces piecesToUpdate){
        HashMap <String,Integer> infoPieces = new HashMap<>();
        int nombre = Integer.parseInt(type.substring(type.indexOf(' ')+1));
        infoPieces.put(type.substring(0,type.indexOf(' ')),nombre);
        if(!pGauche){
            piecesToUpdate.setPgauche(infoPieces);
        }
        else{
            piecesToUpdate.setPdroite(infoPieces);   
        }
        for (String j : pioche.keySet()){
            if(j==type)
                pioche.put(j,pioche.get(j)-1);
        }
        return piecesToUpdate;
    }
    */
    public int choixValide(int borneInf, int borneSup){
        int choix = -1;
        while(choix == -1){
            Scanner choixScan = new Scanner(System.in);
            try{
                choix = choixScan.nextInt();
                while(choix < borneInf || choix > borneSup){
                        choix = choixScan.nextInt();
                }
            }
            catch(Exception e){
                System.out.println("Choix invalide");
            }
        }
        return choix;
    }
    
    public boolean cantBePlaced(Domino domino ,Board plateau, ArrayList<Integer> coo){
        if(plateau.getTile(coo.get(0),coo.get(1))!=null || plateau.getTile(coo.get(2),coo.get(3))!=null ){
            this.vue.invalidPlacement("Emplacement non disponible");
            return false;
        }
        return true;
    }
    
    public ArrayList<Domino> getPioche(){
        return this.pioche;
    }
}