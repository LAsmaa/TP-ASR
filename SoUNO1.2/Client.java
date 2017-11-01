import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;


public class Client {
  private Socket socket = null;
  private ObjectInputStream inputStream = null;
  private ObjectOutputStream outputStream = null;
  private boolean isConnected = false;

  public Client() { }

  public void communicate() {
      while (!isConnected) {
        try {
          socket = new Socket("localHost", 4445);
          System.out.println("Connected");
          isConnected = true;
          outputStream = new ObjectOutputStream(socket.getOutputStream());
          //Crééer l'objet à enoyer !

          System.out.println("Object to be written = " + joueur_client.getName());
          outputStream.writeObject(joueur_client);
        } catch (SocketException se) {
          se.printStackTrace();
          // System.exit(0);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }

  public void envoyerJoueur(Joueur joueur) {
      while (!isConnected) {
        try {
          socket = new Socket("localHost", 4445);
          System.out.println("Connected");
          isConnected = true;
          outputStream = new ObjectOutputStream(socket.getOutputStream());
          System.out.println("Object to be written = " + joueur.getName());
          outputStream.writeObject(joueur);
        } catch (SocketException se) {
          se.printStackTrace();
          // System.exit(0);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }

  public static void main(String[] args) {
    Client client = new Client();
    Scanner sc = new Scanner(System.in);
    String name;
    System.out.println("veuillez introduir votre nom : ");
    name = sc.nextLine();
    Joueur joueur_client = new Joueur(name);
    client.envoyerJoueur(joueur_client);


    /*recevoir_Carte va recevoir les cartes de la main du joueur
    que la partie doit lui envoyer */
    //client.recevoir_Carte();

    //client.communicate();
  }
}
