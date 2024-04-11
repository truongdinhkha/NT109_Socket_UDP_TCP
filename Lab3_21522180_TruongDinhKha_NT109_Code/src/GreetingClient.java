import java.net.*;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GreetingClient
{
     public static void log(Object obj) {

            String logFilePath = "log.txt";
    

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String timestamp = dtf.format(now);

            String logContent = timestamp + " - " + obj.toString();
    
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
                writer.write(logContent);
                writer.newLine();
                System.out.println("Đã ghi log thành công: " + obj.toString());
            } catch (IOException e) {
                System.err.println("Lỗi khi ghi log: " + e.getMessage());
            }
            
        }
        
       
   public static void main(String [] args)
   {
      String tenServer = args[0];
      int port = Integer.parseInt(args[1]);
      try
      {
         System.out.println("Ket noi toi " + tenServer
                             + " on port " + port);
         log("21522180_GreetingClient: Ket noi toi " + tenServer +" on port " + port);
         Socket client = new Socket(tenServer, port);
         System.out.println(" Just connected to " + client.getRemoteSocketAddress());
         log("21522180_GreetingClient: Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out =
                       new DataOutputStream(outToServer);

         out.writeUTF("Hello from " + client.getLocalSocketAddress());
         log("21522180_GreetingClient: Hello from " + client.getLocalSocketAddress());
         InputStream inFromServer = client.getInputStream();
         DataInputStream in =
                        new DataInputStream(inFromServer);
         String s= in.readUTF();
         System.out.println("Server says " + s);
         log("21522180_GreetingClient: Server says " + s);
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}
