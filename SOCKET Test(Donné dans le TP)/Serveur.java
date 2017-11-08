
import java.io.*;
import java.net.*;
public class Serveur {

  public static void main(String[] args) throws Exception{
    ServerSocket SS = new ServerSocket(8000);
    while(true){
      Socket clientSocket = SS.accept();
      Thread_Serveurd T_S = new Thread_Serveurd(clientSocket);
      T_S.start();
    }
  }
}
