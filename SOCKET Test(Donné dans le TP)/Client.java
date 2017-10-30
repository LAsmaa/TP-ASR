import java.net.*;
import java.io.*;

public class Client{
	public static void main (String args[])
	{
	int port = 8000;
	Socket s;
	try {
		s = new Socket ("localhost", port);
		String message = args [0];
		
		// flux sortant
		OutputStream os = s.getOutputStream();
		PrintStream ps = new PrintStream (os);
		ps.println(message);

		//flux entrant 
		InputStream is = s.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String chaine = br.readLine();
		System.out.println("Reçu : " + chaine);
		s.close();
	} catch (IOException e){
		System.out.println(e.getMessage());
	} 
	}
}
