import java.io.*;
import java.net.*;

class Thread_Serveurd extends Thread{

  Socket clientSocket ;

  Thread_Serveurd(Socket s ){clientSocket = s ;}

  public void run(){
    try{
      BufferedReader in = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
      PrintWriter out = new PrintWriter(
        new OutputStreamWriter (clientSocket.getOutputStream()));
      while(true){
        String msg = in.readLine();
        System.out.println("Reçu: " + msg);
    }

    }catch (IOException e){
		System.out.println(e.getMessage());
		} 
  }
}
