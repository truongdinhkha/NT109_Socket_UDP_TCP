// import java.io.DataInputStream;
// import java.io.DataOutputStream;
// import java.io.IOException;

// import java.net.Socket;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.*;
import java.io.*;

public class GreetingServer extends Thread
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
  public static void log_int(int number) {
      log(Integer.valueOf(number)); 
  }
  public static void writeMatrixToFile(int[][] matrix) {
      String logFilePath = "log.txt";
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
          for (int i = 0; i < matrix.length; i++) {
              for (int j = 0; j < matrix[i].length; j++) {
                  writer.write(matrix[i][j] + "\t"); 
              }
              writer.newLine(); 
          }
          System.out.println("Matrix has been successfully written");
      } catch (IOException e) {
          System.err.println("Error writing matrix to file: " + e.getMessage());
      }
  }


  
   private ServerSocket serverSocket;

   public GreetingServer(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(1000000000);
   }

   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            log("21522180_GreetingServer: Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            log("21522180_GreetingServer: Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in =
                  new DataInputStream(server.getInputStream());
            System.out.println(in.readUTF());
            DataOutputStream out =
                 new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
            log("21522180_GreetingServer: Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            log("21522180_GreetingServer: Socket timed out!");

            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   public static void main(String [] args)
   {


        int port = Integer.parseInt(args[0]);
      try
      {
         Thread t = new GreetingServer(port);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
    }
   }
}
