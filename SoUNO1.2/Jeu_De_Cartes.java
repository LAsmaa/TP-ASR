import java.util.*;

public class Jeu_De_Cartes {
  public ArrayList <Carte> Jeu;

  public Jeu_De_Cartes (){
    Jeu = new ArrayList <Carte> (){{
      //Ajout des cartes de couleur
      for (int i = 0; i< 4; i++){

        String couleur;

        //Pour chaque itération la couleur change et on remplie la boucle
        if (i==0) couleur = "BLEU";
        else if (i==1) couleur = "ROUGE";
        else if (i==2) couleur = "JAUNE";
        else couleur = "VERT";

        //Ajout des cartes de couleurs numerotés
        for (int j=0; j< 10; j++){
          Carte carte_couleur = new Carte(j, couleur);
          add(carte_couleur);
        }

        //Ajout des cartes spéciales avec couleur
        for (int k= 0; k< 2; k++){
          Carte carte_plus_deux = new Carte("PLUS 2", couleur);
          add(carte_plus_deux);
          Carte carte_inversement = new Carte("INVERSEMENT DE SENS", couleur);
          add(carte_inversement);
          Carte carte_pass = new Carte("PASSE TON TOUR", couleur);
          add(carte_pass);
        }

        //Ajout des cartes pouvoir sans couleur
        Carte carte_joker = new Carte("JOKER", null);
        add(carte_joker);
        Carte carte_Plus_Quantre = new Carte("PLUS QUATRE", null);
        add(carte_Plus_Quantre);
      }
    }};
  }


  public void addCarte (Carte carte){
    this.Jeu.add(carte);

  }
}
