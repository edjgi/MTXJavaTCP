
package TCP;
import java.io.*;
import java.net.*;
public class TCPSend {
  public static String mtxip = "192.168.0.192";
  public static String toMTX(String message)
  {
      return tcpSend(mtxip, 5001, 5000, message);
  }  
  public static String tcpSend(String ip, int port, int timeout, String content)
{
     String ipaddress = ip;
     int portnumber = port;
     String modifiedSentence = "";
     Socket clientSocket;
     try
     {
         clientSocket = new Socket(ipaddress, portnumber);
         clientSocket.setSoTimeout(timeout);
         DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
         BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         outToServer.writeBytes(content);
         Boolean done= false;
         Long starttime = System.currentTimeMillis();
         while(!done)
         {
            modifiedSentence = inFromServer.readLine();
            if (modifiedSentence.length() < 23)
            {
                done=true;
            }
            if ((starttime-System.currentTimeMillis())>5000)
            {
                throw new Exception();
            }
        }
     }
     catch (Exception exc)
     {
         exc.printStackTrace();
     }
     return modifiedSentence;
}
}
