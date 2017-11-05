import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;


public class Server {

  private ObjectInputStream inStream = null;
  private ObjectOutputStream outputStream = null;
  private boolean isConnected = false;



  public Server(){}

  /*public void communicate(){
    try {
      serverSocket = new ServerSocket(4445);
      while (true) {
        socket = serverSocket.accept();
        System.out.println("Connected");
        inStream = new ObjectInputStream(socket.getInputStream());
        Joueur joueur_client = (Joueur) inStream.readObject();
        System.out.println("Joueur reçu : " + joueur_client.getName() );
        socket.close();
      }
    } catch (SocketException se) {
        System.exit(0);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException cn) {
        cn.printStackTrace();
    }

  }*/

  public Joueur recevoirJoueur(ServerSocket serverSocket, Socket socket){
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

  /*public void recevoir_Carte(Carte main_joueur ){
    try {
      System.out.println("Connected recevoir carte ");
      inStream = new ObjectInputStream(socket.getInputStream());
      Carte carte_recue = (Carte) inStream.readObject();
      main_joueur.add(carte_recue);
      socket.close();
    } catch (SocketException se) {
        System.exit(0);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException cn) {
        cn.printStackTrace();
    }
  }*/

  public void envoyer_carte(Carte carte, ServerSocket serverSocket, Socket socket){
    isConnected = false;
    while (!isConnected) {
      try {
        outputStream = null;
        System.out.println("Connected");
        isConnected = true;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Object to be written = " + carte );
        outputStream.writeObject(carte);
      } catch (SocketException se) {
        se.printStackTrace();
        // System.exit(0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void envoyer_main_joueur(ServerSocket serverSocket, Socket socket, Partie partie){
    for(int i = 0 ; i < 8 ; i ++){
      System.out.println("Envoie de carte n°" + i);
      this.envoyer_carte(partie.donner_carte(), serverSocket, socket);
    }
  }

  public void envoyer_carte_table(ServerSocket serverSocket, Socket socket, Partie partie){
    System.out.println("Envoie de la carte de la table");
    this.envoyer_carte(partie.getCarteSurTable(), serverSocket, socket);
  }

  public static void main(String[] args) {

    Server server = new Server();
    ServerSocket serverSocket = null;
    Socket socket = null;

    try{
      serverSocket = new ServerSocket(4445);
      while(true){
        socket = serverSocket.accept();
        System.out.println("Connected");
        Partie partie = new Partie();
        partie.addJoueur(server.recevoirJoueur(serverSocket, socket));
        ArrayList<Carte> main_a_envoyer = partie.donner_main_joueur();
        server.envoyer_main_joueur(serverSocket, socket, partie);
        server.envoyer_carte_table(serverSocket, socket, partie);
        socket.close();
      }
    }catch (IOException e){
    System.out.println(e.getMessage());
    }

    // server.communicate();
  }
}
