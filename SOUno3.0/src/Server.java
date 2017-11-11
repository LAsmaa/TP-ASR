


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {

    public Server(){}

    public static void main(String[] args) {

        Server server = new Server();
        ServerSocket serverSocket = null;
        Partie partie = new Partie();

        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Donnez le nombre de joueurs de prttie");
            int Nbp_joueurs= sc.nextInt();
            System.out.println("Serveur connecté et accepte les joueurs");
            serverSocket = new ServerSocket(4445);
            for(int i=0; i<Nbp_joueurs; i++){
                //Pour première connexion
                Socket socket = null;
                socket = serverSocket.accept();

                Thread_Server T_S = new Thread_Server(socket, partie);
                T_S.start();
                System.out.println("New thread lancé");
            }
            System.out.println("Sortie d la création des threads");
            //partie.getListeJoueurs().get(0).setJoue(true);
            System.out.println("Joueur: " +
                    partie.getListeJoueurs().get(0).getName() +
                    "Est entrain de jouer: " +
                    partie.getListeJoueurs().get(0).getJoue());

            //Boucle de 4 threads à la fin set le premier joueur en vrai
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        // server.communicate();
    }
}
