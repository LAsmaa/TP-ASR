import java.io.*;
import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.io.DataInputStream;

class Thread_Server extends Thread{

  Socket socket;
  Partie partie;
  Joueur joueur;
  private ObjectInputStream inStream = null;
  private ObjectOutputStream outputStream = null;
  private DataInputStream  inputBoolean= null;
  private DataOutputStream outputBoolean = null;
  private boolean isConnected = false;


  Thread_Server(Socket s, Partie p ){
    this.socket = s ;
    this.partie = p ;
  }

  public Joueur recevoirJoueur(Socket socket){
    try {

      inStream = new ObjectInputStream(socket.getInputStream());
      Joueur joueur_client = (Joueur) inStream.readObject();
      System.out.println("Joueur reçu : " + joueur_client.getName() );
      return joueur_client;
    } catch (SocketException se) {
        System.exit(0);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException cn) {
        cn.printStackTrace();
    } return null;
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

  public void envoyer_main_joueur (Socket socket, Partie partie){
    for(int i = 0 ; i < 8 ; i ++){
      System.out.println("Envoie de carte n°" + i);
      this.envoyer_carte(partie.donner_carte(), socket);
    }
  }

  /*public void envoyer_carte_table(Socket socket, Partie partie){
    System.out.println("Envoie de la carte de la table");
    this.envoyer_carte(partie.getCarteSurTable(), socket);
  }*/

  public boolean recevoir_en_cour(Socket socket){
    try {
      inputBoolean = new DataInputStream(socket.getInputStream());
      boolean boolean_recue = inputBoolean.readBoolean();
      return boolean_recue;
    } catch (SocketException se) {
        System.exit(0);
        return false;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
  }

  public void envoyer_joue(boolean joue, Socket socket){
    isConnected = false;
    while (!isConnected) {
      try {
        isConnected = true;
        outputBoolean = new DataOutputStream(socket.getOutputStream());
        outputBoolean.writeBoolean(joue);
      } catch (SocketException se) {
        se.printStackTrace();
        // System.exit(0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void run(){
    try{

      boolean en_cours = true;

      //Connexion
      System.out.println("Connected");

      //Reception du joueur
      joueur = this.recevoirJoueur(socket);
      if(partie.getListeJoueurs().isEmpty()){
        joueur.setJoue(true);
      }
      partie.addJoueur(joueur);


      //Envoie de la main du joueur
      ArrayList<Carte> main_a_envoyer = partie.donner_main_joueur();
      this.envoyer_main_joueur(socket, partie);



      //Envoie de la carte sur table
      Carte carte_table = partie.getCarteSurTable();
      this.envoyer_carte(carte_table, socket);

      //routine pour chaque tour
      do{
        /*if (partie.getCarteSurTable() != carte_table){
          this.envoyer_carte_table(socket, partie);
          carte_table = partie.getCarteSurTable();
        }*/


        this.envoyer_joue(joueur.getJoue(), socket);

        if(joueur.getJoue()){

          //Envoie de carte sur table
          carte_table = partie.getCarteSurTable();
          this.envoyer_carte(carte_table, socket);

          //Reception du choix du joueur
          System.out.println("Receptio du choix du joueur");
          Carte carte_recue ;
          carte_recue = this.recevoir_Carte(socket);
          System.out.println(carte_recue);
          System.out.println("Chioix reçu");


          //Reception de carte si reçu 0

          if(carte_recue == null){
            this.envoyer_carte(partie.donner_carte(), socket);
          }else{
            partie.setCarteSurTable(carte_recue);
            System.out.println("Table MISE A JOUE");
            // ArrayList<Carte> Cartes_Recu_Pouvoir = partie.appliquer_pouvoir(carte_recue);
            //if(Cartes_Recu_Pouvoir != null){
              //appliquer_pouvoir
            //}
          }
          partie.tour_Suivant(joueur);
          en_cours = this.recevoir_en_cour(socket);
        }
      }while(en_cours);
      System.out.println("Joueur à gagné");
      socket.close();

    }catch (IOException e){
    System.out.println(e.getMessage());
    }
  }
}
