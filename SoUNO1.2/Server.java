import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
  private ServerSocket serverSocket = null;
  private Socket socket = null;
  private ObjectInputStream inStream = null;

  public Server(){}

  public void communicate(){
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

  }

  public Joueur recevoirJoueur(){
    try {
      serverSocket = new ServerSocket(4445);
      socket = serverSocket.accept();
      System.out.println("Connected");
      inStream = new ObjectInputStream(socket.getInputStream());
      Joueur joueur_client = (Joueur) inStream.readObject();
      System.out.println("Joueur reçu : " + joueur_client.getName() );
      socket.close();
      return joueur_client;
    } catch (SocketException se) {
        System.exit(0);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException cn) {
        cn.printStackTrace();
    } return null;
  }

  public static void main(String[] args) {
    Server server = new Server();
    Partie partie = new Partie();
    partie.addJoueur(server.recevoirJoueur());
    partie.jouer_partie();


    // server.communicate();
  }
}
