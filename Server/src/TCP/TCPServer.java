package TCP;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

  public final static int port = 9876;  //port
  public final static String fisierTrimis = "C:/Users/CRISTINA/troian1.jar";  //folder to send

  public static void main (String [] args ) throws IOException {
	  
    FileInputStream fisierInput = null;
    
    BufferedInputStream bufferFisier = null;
    
    OutputStream output = null;
    
    ServerSocket servSocket = null;
    
    Socket socket = null;
    
    try {
    	servSocket = new ServerSocket(port);
    	
      while (true) {
    	  
        System.out.println("Asteapta...");
        
        try {
          socket = servSocket.accept();
          
          System.out.println("Conexiune acceptata : " + socket);
          
          // send file
          
          File fisier = new File (fisierTrimis);
          
          byte [] vectorBiti  = new byte [(int)fisier.length()];
          
          fisierInput = new FileInputStream(fisier);
          
          bufferFisier = new BufferedInputStream(fisierInput);
          
          bufferFisier.read(vectorBiti,0,vectorBiti.length);
          
          output = socket.getOutputStream();
          
          System.out.println("Trimite  " + fisierTrimis + "(" + vectorBiti.length + " bytes)");
          
          output.write(vectorBiti,0,vectorBiti.length);
          
          output.flush();
          
          System.out.println("Trimis catre client!");
          
        }
        finally {
        	
          if (bufferFisier != null) 
        	  bufferFisier.close();
          try {
             
            }
            catch (Exception err) {
              err.printStackTrace();
            }
         
          
          if (output != null) 
        	  output.close();
          
          if (socket!=null) 
        	  socket.close();
          
        }
        socket = servSocket.accept();
        
        
      }
    }
    finally {
    	
      if (servSocket != null) 
    	  servSocket.close();
    }
  }
}