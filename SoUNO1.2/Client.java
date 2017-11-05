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

  public Carte recevoir_Carte(Socket socket){
    try {
      System.out.println("Connected recevoir carte ");
      inStream = new ObjectInputStream(socket.getInputStream());
      Carte carte_recue = (Carte) inStream.readObject();
      System.out.println("Care reçue : " + carte_recue);
      return carte_recue;
    } catch (SocketException se) {
        System.exit(0);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException cn) {
        cn.printStackTrace();
    }return null;
  }

  public ArrayList<Carte> recevoir_Main_Joueur(Socket socket){
    ArrayList<Carte> main_joueur = new ArrayList<Carte>();
    for (int i = 0; i < 8; i++){
      recevoir_Carte(socket);
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
