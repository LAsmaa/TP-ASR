import java.util.*;

public class Partie{
  ArrayList<Joueur> joueurs_de_partie;
  int tour;
  Carte carte_sur_table;
  Jeu_De_Cartes jeu_de_partie;


  public Partie(){
    this.joueurs_de_partie = new ArrayList<Joueur>();
    this.jeu_de_partie = new Jeu_De_Cartes();
    this.tour = 0;
    this.carte_sur_table = this.jeu_de_partie.removeCarte();
  }

  public void addJoueur(Joueur joueur){
    this.joueurs_de_partie.add(joueur);
  }




}
