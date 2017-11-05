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

  public void envoyer_Joueur(Joueur joueur) {
      while (!isConnected) {
        try {
          socket = new Socket("localHost", 4445);
          System.out.println("Connected");
          isConnected = true;
          outputStream = new ObjectOutputStream(socket.getOutputStream());
          System.out.println("Object to be written = " + joueur.getName());
          outputStream.writeObject(joueur);
          socket.close();
        } catch (SocketException se) {
          se.printStackTrace();
          // System.exit(0);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }

  public void recevoir_Carte(ArrayList<Carte> main_joueur ){
    try {
      System.out.println("Connected recevoir carte ");
      socket = new Socket("localHost", 4445);
      inStream = new ObjectInputStream(socket.getInputStream());
      Carte carte_recue = (Carte) inStream.readObject();
      System.out.println("Care reçue : " + carte_recue);
      main_joueur.add(carte_recue);
      socket.close();
    } catch (SocketException se) {
        System.exit(0);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException cn) {
        cn.printStackTrace();
    }
  }

  public void recevoir_Main_Joueur(Joueur joueur){
    ArrayList<Carte> main_joueur = new ArrayList<Carte>();
    for (int i = 0; i < 8; i++){
      recevoir_Carte(main_joueur);
    }
    joueur.setMainJoueur(main_joueur);


  }

  public static void main(String[] args) {
    Client client = new Client();
    Scanner sc = new Scanner(System.in);
    String name;
    System.out.println("veuillez introduir votre nom : ");
    name = sc.nextLine();
    Joueur joueur_client = new Joueur(name);
    client.envoyer_Joueur(joueur_client);
    client.recevoir_Main_Joueur(joueur_client);


    /*recevoir_Carte va recevoir les cartes de la main du joueur
    que la partie doit lui envoyer */
    //client.recevoir_Carte();

    //client.communicate();
  }
}
