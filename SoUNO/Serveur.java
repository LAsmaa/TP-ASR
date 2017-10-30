import java.net.*;
import java.io.*;

public class Serveur{
	public static void main (String args[])
	{

		Carte C = new Carte(true, null, Pouvoirs.INVERSEMENT);
	int port = 8000;
	Socket sock_service;
	ServerSocket sock_ecoute;
	try {
		sock_ecoute = new ServerSocket (port);
		while (true) {
		//flux entrant (String)
		sock_service = sock_ecoute.accept();
		BufferedReader br =new BufferedReader(new
				InputStreamReader( sock_service.getInputStream()));
		String chaine = br.readLine();
		System.out.println("Reçu : " + chaine);


		//Objet entrant
		ObjectInputStream inFromClient = new ObjectInputStream(
		sock_service.getInputStream());

		// flux sortant
		PrintStream ps = new PrintStream (
		sock_service.getOutputStream());
    //chaine va être envoyée
    chaine =  C.getPouvoir() ;
		ps.println(chaine+" du serveur N " + args[0]);

		//Objet sortant
		ObjectOutputStream outToClient =
		new ObjectOutputStream(sock_service.getOutputStream());



		outToClient.writeObject(C);

		sock_service.close();
		}
		}
		catch (IOException e){
		System.out.println(e.getMessage());
		}
	}
}
