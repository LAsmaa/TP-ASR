package Game_Package;

import javafx.concurrent.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Task<Integer>{

    public Server(){}

    @Override
    protected Integer call() throws Exception {
        return null;
    }

    public static void main(String[] args) {

        Server server = new Server();
        ServerSocket serverSocket = null;
        Partie partie = new Partie();

        try{
            serverSocket = new ServerSocket(4445);
            for(int i=0; i<3; i++){
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
