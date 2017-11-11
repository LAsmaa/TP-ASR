import java.util.*;
import java.io.Serializable;


public class Joueur implements Serializable {

    public String name;
    private ArrayList<Carte> main_joueur;
    private Carte carte_sur_table;
    public boolean joue;
    private boolean gagne;

    public Joueur(String name){
        this.name=name;
        this.main_joueur = new ArrayList<Carte>();
        this.carte_sur_table = null;
        this.joue= false;
        this.gagne= false;
    }

    public synchronized void setName(String name){
        this.name=name;
    }

    public synchronized String getName(){
        return this.name;
    }

    public synchronized void setCarteSurTable(Carte carte_sur_table){
        this.carte_sur_table=carte_sur_table;
    }

    public synchronized Carte getCarteSurTable(){
        return this.carte_sur_table;
    }

    public synchronized void setJoue(boolean joue){
        this.joue=joue;
    }

    public synchronized boolean getJoue(){
        return this.joue;
    }

    public synchronized boolean getGagne(){
        return this.gagne;
    }

    public synchronized void setGagne(boolean gagne){
        this.gagne=gagne;
    }

    public synchronized void add_carte_main_joueur (Carte carte){
        this.main_joueur.add(carte);
    }

    public synchronized ArrayList<Carte> getMainJoueur(){
        return this.main_joueur;
    }

    public synchronized void setMainJoueur (ArrayList<Carte> main_joueur){
        this.main_joueur = main_joueur;
    }

    public synchronized void print_main_joueur (){
        System.out.println("Votre main: \n");
        int i = 1;
        for(Carte temp: main_joueur){
            System.out.println(i + "- " + temp);
            i++;
        }
    }


    // Dans l'inerface demander au joueur d'inserrer le numero de la position de la carte à déposer
    // Exemple la main est 1- PLUS DEUX ROUGE , 2- 9 VERT , 3- INVERSEMENT BLEU
    // Le joueur insert 3 -> la carte INVERSEMENT BLEU sera déposéé
    public synchronized void depose_Carte(Carte carte_deposee){
        boolean temps = main_joueur.remove(carte_deposee);
    }

    public synchronized int choix_jeux(){
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

    public synchronized boolean verifie_choix_carte(Carte carte_choisie, Carte carte_table){
        if (( carte_choisie.getPouvoir()==null)&&(carte_table.getPouvoir()==null)){
            if (carte_choisie.getNumero()  ==  carte_table.getNumero()){
                return true;
            }else if(carte_choisie.getCouleur().equals(carte_table.getCouleur())){
                return true;
            }
        }

        else if (carte_choisie.getPouvoir()==null){
            if (carte_table.getPouvoir()!=null){
                if(carte_choisie.getCouleur().equals(carte_table.getCouleur())){
                    return true;
                }
            }
        }else if (carte_choisie.getPouvoir()!=null){
            if (carte_choisie.getPouvoir().equals("PLUS QUATRE")){
                return true;
            }else if (carte_choisie.getPouvoir().equals("JOKER")){
                return true;
            }else if(carte_table.getPouvoir()!=null){
                if(carte_choisie.getCouleur().equals(carte_table.getCouleur())){
                    return true;
                }else if (carte_choisie.getPouvoir().equals(carte_table.getPouvoir())){
                    return true;
                }
            }else {
                if(carte_choisie.getCouleur().equals(carte_table.getCouleur())){
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized Carte choix_carte(){
        Carte carte_choisie;
        //A faire:
        //Verifier si la carte déposéé est bonne...... --"
        Scanner sc = new Scanner(System.in);
        int choix;
        do{
            do{
                System.out.println("\nQuel carte voulez vous jouer ? \n**(Inserrez le numero a gauche de la carte ou 0 pour passer vore tour): ");
                choix= sc.nextInt();
                if (choix == 0){
                    return null;
                }
            }while ((choix < 0 ) || (choix > main_joueur.size()));
            carte_choisie = main_joueur.get(choix-1);
        }while(!verifie_choix_carte(carte_choisie, this.getCarteSurTable()));
        this.depose_Carte(carte_choisie);
        if (carte_choisie.getPouvoir() != null){
            if ((carte_choisie.getPouvoir().equals("PLUS QUATRE"))
                    ||(carte_choisie.getPouvoir().equals("JOKER"))){
                return this.carte_suivante(carte_choisie);
            }
        }
        return carte_choisie;
    }

    //Si la carte est joker ou plus quatre le joueur demandeune couleur
    public synchronized Carte carte_suivante(Carte carte){
        Scanner sc = new Scanner(System.in);
        int i;
        do{
            System.out.println("\nQuel couleur voulez vous ? \n1- BLEU \n2- ROUGE \n3- JAUNE \n4- VERT");
            i= sc.nextInt()-1;
        }while((i < 1)||(i > 4));

        if (i==1) carte.setCouleur("BLEU");
        else if (i==2) carte.setCouleur("ROUGE");
        else if (i==3) carte.setCouleur("JAUNE");
        else carte.setCouleur("VERT");

        return carte;
    }


}
