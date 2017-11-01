import java.util.*;

public class Joueur{

  public String name;
  private ArrayList<Carte> main_joueur;
  private boolean joue;
  private boolean gagne;

  public Joueur(String name){
    this.name=name;
    this.main_joueur = new ArrayList<Carte>();
    this.joue= false;
    this.gagne= false;
  }

  public void setName(String name){
    this.name=name;
  }

  public String getName(){
    return this.name;
  }

  public void setJoue(boolean joue){
    this.joue=joue;
  }

  public boolean getGagne(){
    return this.gagne;
  }

  public void setGagne(boolean gagne){
    this.gagne=gagne;
  }

  public void add_carte_main_joueur(Carte carte){
    this.main_joueur.add(carte);
  }

  public ArrayList<Carte> get_main_joueur(){
    return this.main_joueur;
  }



  // Dans l'inerface demander au joueur d'inserrer le numero de la position de la carte à déposer
  // Exemple la main est 1- PLUS DEUX ROUGE , 2- 9 VERT , 3- INVERSEMENT BLEU
  // Le joueur insert 3 -> la carte INVERSEMENT BLEU sera déposéé
  public Carte depose_Carte(int position_carte_deposee){
    Carte carte_deposee = main_joueur.get(position_carte_deposee);
    main_joueur.remove(position_carte_deposee);
    return carte_deposee;
  }

  public int choix_jeux(){
    Scanner sc = new Scanner(System.in);
    int choix=-1;
    while((choix!=1)&&(choix!=0)){
      System.out.println("Vous voulez \n - Déposer une carte (Tapez 1) \n - Piocher une carte (Tapez 0)");
      choix = sc.nextInt();
      if ((choix !=1)&&(choix!=0)){
        System.out.println("La valeur entrée est incorrecte veuillez recommencer \n\n");
      }
    }return choix;
  }

  public boolean verifie_choix_carte(Carte carte_choisie, Carte carte_table){
    if ((carte_choisie.getCouleur() == carte_table.getCouleur()) ||
      (carte_choisie.getNumero() == carte_table.getNumero())||
      (carte_choisie.getPouvoir() == carte_table.getPouvoir()) ||
      (carte_choisie.getPouvoir()=="JOKER") ||
      (carte_choisie.getPouvoir()=="PLUS QUATRE")){
        return true;
      }
      else return false;
  }

  public Carte choix_carte(Carte carte_table){
    Carte carte_choisie;
    //A faire:
    //Verifier si la carte déposéé est bonne...... --"
    Scanner sc = new Scanner(System.in);
    int choix;
    do{
      for (Carte temp: main_joueur){
        int i = 0;
        System.out.println("- " + i + " " + temp + "\n");
        i++;
      }
      System.out.println("Que voulez vous jouer ? (Inserrez le numero a gauche de la carte) ");
      choix= sc.nextInt();
      carte_choisie = main_joueur.get(choix);
    }while ((choix < 0 ) && (choix > main_joueur.size()) && (verifie_choix_carte(carte_choisie, carte_table)));
    return carte_choisie;
  }



  //retourner la carte poséé Si
  // Une carte normale IZI la game continue
  // Une carte NULL eut dire qu'il pioche une carte
  // IZI GAME IZI LIFE BABY :^)
  public Carte jouer_joueur (Carte carte_table){
    Carte carte_deposee ;
    int choix_j = this.choix_jeux();
    if (choix_j==1)/*deposer carte*/{
      ArrayList<Carte> main_a_jouer = this.get_main_joueur();
      carte_deposee  = this.choix_carte(carte_table);
    }else{
      carte_deposee = null;
    }
    return carte_deposee;
  }
}
