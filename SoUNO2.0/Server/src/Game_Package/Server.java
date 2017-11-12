package Game_Package;

import javafx.concurrent.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread{

    public void lancer_server() throws IOException {
        ServerSocket serverSocket = null;
        Partie partie = new Partie();
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


    }

    @Override
    public void run() {
        try {
            lancer_server();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }

    // Platform.runLater();
    //Pour modifier l'interface à partie du thread server

    public Server(){

        try{
            lancer_server();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }


    }

    //@Override
    //protected Integer call() throws Exception {
      //  return null;
    //}


}
