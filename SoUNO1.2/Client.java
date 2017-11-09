import java.io.IOException;
import java.io.*;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;


public class Client {
  private ObjectInputStream inputStream = null;
  private ObjectOutputStream outputStream = null;
  private boolean isConnected = false;
  private ObjectInputStream inStream = null;
  private DataInputStream  inputBoolean= null;
  private DataOutputStream outputBoolean = null;



  public Client() { }

  public void envoyer_Joueur(Socket socket, Joueur joueur) {
      while (!isConnected) {
        try {
          System.out.println("Connected");
          isConnected = true;
          outputStream = new ObjectOutputStream(socket.getOutputStream());
          outputStream.writeObject(joueur);
        } catch (SocketException se) {
          se.printStackTrace();
          // System.exit(0);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }

  public Carte recevoir_Carte(Socket socket){
    try {
      inStream = new ObjectInputStream(socket.getInputStream());
      Carte carte_recue = (Carte) inStream.readObject();
      return carte_recue;
    } catch (SocketException se) {
        System.exit(0);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException cn) {
        cn.printStackTrace();
    }return null;
  }

  public void envoyer_carte(Carte carte, Socket socket){
    isConnected = false;
    while (!isConnected) {
      try {
        outputStream = null;
        isConnected = true;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Envoie de la carte: " + carte );
        outputStream.writeObject(carte);
      } catch (SocketException se) {
        se.printStackTrace();
        // System.exit(0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void envoyer_en_cour(boolean en_cour, Socket socket){
    isConnected = false;
    while (!isConnected) {
      try {
        isConnected = true;
        outputBoolean = new DataOutputStream(socket.getOutputStream());
        outputBoolean.writeBoolean(en_cour);
      } catch (SocketException se) {
        se.printStackTrace();
        // System.exit(0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean recevoir_joue(Socket socket){
    try {
      inputBoolean = new DataInputStream(socket.getInputStream());
      boolean boolean_recue = inputBoolean.readBoolean();
      System.out.println("Reception de joue");
      return boolean_recue;
    } catch (SocketException se) {
        System.exit(0);
        return false;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
  }

  public ArrayList<Carte> recevoir_Main_Joueur(Socket socket){
    System.out.println("Reception de vos cartes ....\n");
    ArrayList<Carte> main_joueur = new ArrayList<Carte>();
    for (int i = 0; i < 8; i++){
      main_joueur.add(recevoir_Carte(socket));
    }
    return main_joueur;
  }

  public static void main(String[] args) {

    Client client = new Client();
    Socket socket = null;
    Carte carte_sur_table = null;
    try{

      //Connexion
      socket = new Socket("localHost", 4445);

      //Envoie du joueur
      Scanner sc = new Scanner(System.in);
      String name;
      System.out.println("veuillez introduir votre nom : ");
      name = sc.nextLine();
      Joueur joueur_client = new Joueur(name);
      client.envoyer_Joueur(socket, joueur_client);

      //Reception de la main du joueur
      joueur_client.setMainJoueur(client.recevoir_Main_Joueur(socket));
      joueur_client.print_main_joueur();

      //Reception de la carte sur table
      //carte_sur_table = client.recevoir_Carte(socket);
      //joueur_client.setCarteSurTable(carte_sur_table);
      //System.out.println(" \n** Sur la table: " + joueur_client.getCarteSurTable());


      Carte choisie = null;
      boolean en_cours = true;
      boolean joue;
      //Routine pour chaque tour
      do{

        //Reception de joue
        joue = client.recevoir_joue(socket);

        System.out.println("Joue reçu"+joue);
        joueur_client.setJoue(joue);
        if(joue){

          System.out.println("Joueur jouer = " + joueur_client.getJoue());

          //Reception de la carte sur table
          carte_sur_table = client.recevoir_Carte(socket);
          if(carte_sur_table == null){
            carte_sur_table = client.recevoir_Carte(socket);
          }
          joueur_client.setCarteSurTable(carte_sur_table);

          //CHoix du joueur
          System.out.println(" \n** Sur la table: " + joueur_client.getCarteSurTable());
          joueur_client.print_main_joueur();
          choisie = joueur_client.choix_carte();

          //envoie du choix du joueur
          System.out.println("Envoie du choix du joueur");
          client.envoyer_carte(choisie, socket);
          System.out.println("Choix envoyé");

          //reception de carte si il envoie 0
          if(choisie == null){
            joueur_client.add_carte_main_joueur(client.recevoir_Carte(socket));
          }
          if(joueur_client.getMainJoueur().isEmpty()){
            en_cours = false;
            client.envoyer_en_cour(false, socket);
          }else {
            client.envoyer_en_cour(true, socket);
          }
        }
      }while(en_cours);



      socket.close();

    }catch (IOException e){
    System.out.println(e.getMessage());
    }





    /*recevoir_Carte va recevoir les cartes de la main du joueur
    que la partie doit lui envoyer */
    //client.recevoir_Carte();

    //client.communicate();
  }
}
