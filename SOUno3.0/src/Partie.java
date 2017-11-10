import java.util.*;

public class Partie{
    private ArrayList<Joueur> joueurs_de_partie;
    private int tour;
    private Carte carte_sur_table;
    public Jeu_De_Cartes jeu_de_partie;
    private boolean partie_en_cour;
    private int directon;


    public Partie(){
        this.joueurs_de_partie = new ArrayList<Joueur>();
        this.jeu_de_partie = new Jeu_De_Cartes();
        this.tour = 0;
        Carte carte = this.jeu_de_partie.removeCarte();
        while (carte.getPouvoir()!=null){
            this.jeu_de_partie.addCarte(carte);
            carte = this.jeu_de_partie.removeCarte();
        }
        this.carte_sur_table = carte;
        this.partie_en_cour = true;
        this.directon = 1;
    }

    public synchronized ArrayList<Joueur> getListeJoueurs(){
        return this.joueurs_de_partie;
    }

    public synchronized ArrayList<Carte> donner_main_joueur(){
        ArrayList<Carte> main_joueur = new ArrayList<Carte>();
        for(int i = 0 ; i < 8 ; i ++){
            main_joueur.add(jeu_de_partie.removeCarte());
        }
        return main_joueur;
    }

    public synchronized Carte donner_carte(){
        return jeu_de_partie.removeCarte();
    }

    public synchronized void addJoueur(Joueur joueur){
        this.joueurs_de_partie.add(joueur);
    }

    public synchronized Carte getCarteSurTable(){
        return this.carte_sur_table;
    }

    public synchronized boolean getEnCour(){
        return this.partie_en_cour;
    }



    //Boolean direction
    //Si vrai on avance normale
    //Si faix on change de direction
    public synchronized void setCarteSurTable(Carte carte){
        this.carte_sur_table = carte;
    }

    public synchronized ArrayList<Carte> appliquer_pouvoir_PLUS(Carte carte){
        ArrayList<Carte> a_envoyer = new ArrayList<Carte>();
        if(carte.getPouvoirON()){
            if(carte.getPouvoir().equals("PLUS DEUX")){
                carte.setPouvoirOn(false);
                for(int i=0; i<2 ; i++){
                    a_envoyer.add(this.donner_carte());
                }
            }else if(carte.getPouvoir().equals("PLUS QUATRE")){
                carte.setPouvoirOn(false);
                for(int i=0; i<4 ; i++){
                    a_envoyer.add(this.donner_carte());
                }
            }else if(carte.getPouvoir().equals("JOKER")){
                carte.setPouvoirOn(false);
                a_envoyer = null;
            }
        }return a_envoyer;
    }

    public synchronized void appliquer_pouvoir_PASS(Carte carte, Joueur joueur, int direction ){
        if(carte.getPouvoirON()){
            carte.setPouvoirOn(false);
            int pos_Actuel = joueurs_de_partie.indexOf(joueur);
            System.out.println("Pos joueur actuel: " + pos_Actuel);
            joueurs_de_partie.get(pos_Actuel).setJoue(false);
            int pos_Suivant = (pos_Actuel+ (2 * direction )) % joueurs_de_partie.size();
            System.out.println("Pos joueur suivant: " + pos_Suivant);
            joueurs_de_partie.get(pos_Suivant).setJoue(true);
        }
    }

    public synchronized int appliquer_pouvoir_INVERS(Carte carte, int direction){
        if(carte.getPouvoirON()){
            carte.setPouvoirOn(false);
            if(direction == 1){
                direction= -1;
            }else{
                direction = 1;
            }
            return direction;
        }
        return direction;
    }

    public synchronized void tour_Suivant(Joueur joueur_Actuel, int direction){
        int pos_Actuel = joueurs_de_partie.indexOf(joueur_Actuel);
        joueur_Actuel.setJoue(false);
        joueurs_de_partie.get(pos_Actuel).setJoue(false);
        System.out.println("Pos joueur actuel: " + pos_Actuel);
        int pos_Suivant = (pos_Actuel+ direction ) % joueurs_de_partie.size();
        System.out.println("Pos joueur suivant: " + pos_Suivant);
        joueurs_de_partie.get(pos_Suivant).setJoue(true);
    }


    public int getDirecton() {
        return directon;
    }

    public void setDirecton(int directon) {
        this.directon = directon;
    }
}
