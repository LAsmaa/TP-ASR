import java.io.IOException;
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


  public Client() { }

  /*public void communicate() {
      while (!isConnected) {
        try {
          socket = new Socket("localHost", 4445);
          System.out.println("Connected");
          isConnected = true;
          outputStream = new ObjectOutputStream(socket.getOutputStream());
          //Crééer l'objet à enoyer !

          //System.out.println("Object to be written = " + joueur_client.getName());
          outputStream.writeObject(joueur_client);
        } catch (SocketException se) {
          se.printStackTrace();
          // System.exit(0);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }*/

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
    try{
      socket = new Socket("localHost", 4445);
      Scanner sc = new Scanner(System.in);
      String name;
      System.out.println("veuillez introduir votre nom : ");
      name = sc.nextLine();
      Joueur joueur_client = new Joueur(name);
      client.envoyer_Joueur(socket, joueur_client);
      joueur_client.setMainJoueur(client.recevoir_Main_Joueur(socket));
      joueur_client.setCarteSurTable(client.recevoir_Carte(socket));
      joueur_client.print_main_joueur();
      System.out.println(" \n** Sur la table: " + joueur_client.getCarteSurTable());


      Carte choisie = joueur_client.choix_carte();
      System.out.println("Carte choisir: " + choisie );
      client.envoyer_carte(choisie, socket);

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
