import java.util.*;

public class Partie{
  private ArrayList<Joueur> joueurs_de_partie;
  private int tour;
  private Carte carte_sur_table;
  private Jeu_De_Cartes jeu_de_partie;
  private boolean partie_en_cour;


  public Partie(){
    this.joueurs_de_partie = new ArrayList<Joueur>();
    this.jeu_de_partie = new Jeu_De_Cartes();
    this.tour = 0;
    this.carte_sur_table = this.jeu_de_partie.removeCarte();
    this.partie_en_cour = true;
  }

  public ArrayList<Joueur> getListeJoueurs(){
      return this.joueurs_de_partie;
  }

  public ArrayList<Carte> donner_main_joueur(){
    ArrayList<Carte> main_joueur = new ArrayList<Carte>();
    for(int i = 0 ; i < 8 ; i ++){
      main_joueur.add(jeu_de_partie.removeCarte());
    }
    return main_joueur;
  }

  public void addJoueur(Joueur joueur){
    this.joueurs_de_partie.add(joueur);
  }

  public Carte getCarteSurTable(){
    return this.carte_sur_table;
  }

  public void setCarteSurTable(Carte carte){
    this.carte_sur_table = carte;
  }

  public void jouer_partie(){
    //Boucle pour jouer tant qu'il n'y a pas de gagnant
    //Fire boucler plusieurs fois sur la liste des joueurs
    do {
      //Boucle fait boucler une seule fois sur la liste des joueurs
      for(Joueur temp: this.getListeJoueurs()){
          Carte carte_deposee = temp.jouer_joueur(this.getCarteSurTable());
          if (carte_deposee == null){
            Carte carte_piochee = jeu_de_partie.removeCarte();
            temp.add_carte_main_joueur(carte_piochee);
          }else{
            jeu_de_partie.addCarte(this.getCarteSurTable());
            setCarteSurTable(carte_deposee);
          }
          if (temp.getGagne()){
            partie_en_cour= false;
            break;
          }
      }
    }while (partie_en_cour);
  }

}
