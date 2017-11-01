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


  public void jouer (boolean joue){
    int choix = this.choix_jeux();
    if (choix==1){
      ArrayList<Carte> main_a_jouer = this.get_main_joueur();
      choix= -1;
      while (choix != 0){
        System.out.println("Votre main est :\n");
        int i = 1;
        for (Carte temp: main_a_jouer){
          System.out.println(i + " " + temp);
        }
        choix = 0;
      }

    }


  }





}
