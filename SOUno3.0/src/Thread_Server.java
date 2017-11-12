import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

class Thread_Server extends Thread{

    Socket socket;
    Partie partie;
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
            System.out.println("Joueur reçu : " + joueur_client.getName());
            return joueur_client;
        } catch (SocketException se) {
            System.exit(0);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Carte recevoir_Carte(Socket socket){
        try {
            inStream = new ObjectInputStream(socket.getInputStream());
            Carte carte_recue = (Carte) inStream.readObject();
            return carte_recue;
        } catch (SocketException se) {
            System.exit(0);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void envoyer_carte(Carte carte, Socket socket){
        isConnected = false;
        while (!isConnected) {
            try {
                outputStream = null;
                isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                //System.out.println("Envoie de la carte: " + carte );
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
            //System.out.println("Envoie de carte n°" + i);
            this.envoyer_carte(partie.donner_carte(), socket);
        }
    }

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
                System.out.println("Envoie de joue");
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
            Joueur joueur = this.recevoirJoueur(socket);
            if(partie.getListeJoueurs().isEmpty()){
                joueur.setJoue(true);
                System.out.println("Joueur premier lancé");
            }
            partie.addJoueur(joueur);


            //Envoie de la main du joueur
            ArrayList<Carte> main_a_envoyer = partie.donner_main_joueur();
            this.envoyer_main_joueur(socket, partie);

            //routine pour chaque tour
            do{

                if(joueur.getJoue()){
                    this.envoyer_joue(joueur.getJoue(), socket);

                    //Envoie de carte sur table
                    Carte carte_table = partie.getCarteSurTable();
                    System.out.println("Envoie carte sur table:"+carte_table);
                    this.envoyer_carte(carte_table, socket);

                    //Envoie des cartes bous si carte sur table a un pouvoir ON
                    if(carte_table.getPouvoirON()){
                        ArrayList<Carte> Cartes_Recu_Pouvoir = partie.appliquer_pouvoir_PLUS(carte_table);
                        if(Cartes_Recu_Pouvoir != null){
                            for(Carte temp: Cartes_Recu_Pouvoir){
                                System.out.println(temp);
                                this.envoyer_carte(temp, socket);
                            }
                            joueur.setJoue(false);
                            partie.tour_Suivant(joueur, partie.getDirecton());
                        }
                        carte_table.setPouvoirOn(false);
                    }else{
                        //Reception du choix du joueur
                        System.out.println("Receptio du choix du joueur");
                        Carte carte_recue ;
                        carte_recue = this.recevoir_Carte(socket);
                        //partie.addCarte(carte_recue);
                        System.out.println("Chioix reçu");
                        System.out.println(carte_recue);


                        //Reception de carte si reçu 0

                        if(carte_recue == null){
                            this.envoyer_carte(partie.donner_carte(), socket);
                            joueur.setJoue(false);
                            partie.tour_Suivant(joueur, partie.getDirecton());
                        }else{
                            partie.setCarteSurTable(carte_recue);
                            System.out.println("Table MISE A JOUE");
                            if(carte_recue.getPouvoirON() && !(carte_recue.getPouvoir().equals("PLUS DEUX")) && !(carte_recue.getPouvoir().equals("PLUS QUATRE")) ){
                                joueur.setJoue(false);
                                if(carte_recue.getPouvoir().equals("INVERSEMENT DE SENS")){
                                    int direction = partie.appliquer_pouvoir_INVERS(carte_recue, partie.getDirecton());
                                    partie.setDirecton(direction);
                                    joueur.setJoue(false);
                                    partie.tour_Suivant(joueur, partie.getDirecton());
                                }else if(carte_recue.getPouvoir().equals("PASSE TON TOUR")){
                                    partie.appliquer_pouvoir_PASS(carte_recue,joueur, partie.getDirecton());
                                }
                            }else{
                                joueur.setJoue(false);
                                partie.tour_Suivant(joueur, partie.getDirecton());
                            }
                        }
                        en_cours = this.recevoir_en_cour(socket);
                    }
                }
            }while(en_cours);
            System.out.println("Joueur à gagné");
            socket.close();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
