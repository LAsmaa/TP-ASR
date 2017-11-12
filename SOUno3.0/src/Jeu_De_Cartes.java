import java.util.*;

public class Jeu_De_Cartes {
    public ArrayList<Carte> Jeu;

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
                    Carte carte_couleur = new Carte(j, couleur, null, false);
                    add(carte_couleur);
                }

                //Ajout des cartes spéciales avec couleur
                for (int k= 0; k< 2; k++){
                    Carte carte_plus_deux = new Carte(-1, couleur, "PLUS DEUX", true);
                    add(carte_plus_deux);
                    Carte carte_inversement = new Carte(-1, couleur, "INVERSEMENT DE SENS", true);
                    add(carte_inversement);
                    Carte carte_pass = new Carte(-1, couleur, "PASSE TON TOUR", true);
                    add(carte_pass);
                }

                //Ajout des cartes pouvoir sans couleur
                Carte carte_joker = new Carte(-2, null, "JOKER", false);
                add(carte_joker);
                Carte carte_Plus_Quantre = new Carte(-3, null, "PLUS QUATRE", true);
                add(carte_Plus_Quantre);
            }
        }};
    }

    public void addCarte (Carte carte){
        if((carte.getPouvoir().equals("PLUS DEUX"))||
                (carte.getPouvoir().equals("INVERSEMENT DE SENS"))||
                (carte.getPouvoir().equals("PASSE TON TOUR"))||
                (carte.getPouvoir().equals("PLUS QUATRE"))){
            carte.setPouvoirOn(true);
        }
        this.Jeu.add(carte);
    }

    public Carte removeCarte(){
        int random = (int) ((Math.random() * Jeu.size()));
        Carte removed_carte = Jeu.get(random);
        Jeu.remove(random);
        return removed_carte;
    }


}
