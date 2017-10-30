import java.net.*;
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.io.Serializable;



public class Client{
	public static void main (String args[])
	{

	try {
		//Création de la Socket
		int port = 8000;
		Socket client_Socket;
		client_Socket = new Socket ("localhost", port);

		//Création des input et output stream
		ObjectOutputStream outToServer =
			new ObjectOutputStream(client_Socket.getOutputStream());
		ObjectInputStream inFromServer =
			new ObjectInputStream(client_Socket.getInputStream());


		//Message a envoyer
		//Carte a_Jouer = new Carte();

		//Envoyer le message

		//outToServer.writeObject(Carte);


		//Recevoir un message
		//LinkedList<Carte> inFromServerList = new LinkedList<>();
		Carte inFromServerCarte = null;
		inFromServerCarte = (Carte)inFromServer.readObject();
		//carte_Reçue = inFromServer.pop();

		//Imprimer la carte
		System.out.println("Carte :"+ inFromServerCarte.getPouvoir());
  	client_Socket.close();

    }


 	catch (IOException e){
		System.out.println(e.getMessage());
	}
	}
}
