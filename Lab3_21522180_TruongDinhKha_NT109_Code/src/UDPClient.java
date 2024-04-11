import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UDPClient {

    private static int SERVER_PORT = 9090;
	private static int CLIENT_PORT = 5001;
	private static DatagramSocket socket;


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
	public static void main(String[] args) throws IOException
	{


		System.out.print("Connected to server on port "+ SERVER_PORT);
		socket = new DatagramSocket(CLIENT_PORT);
                log("21522180_UDPClient: Connected to server on port "+ SERVER_PORT);
		InetAddress serverAddress = InetAddress.getByName("localhost");

		new Thread(() -> {
			while (true) {
				try {
					byte[] buf = new byte[1024];
					DatagramPacket packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet);
					
					String received = new String(packet.getData(), 0, packet.getLength());
					System.out.println("Server: "+ received);
                                       log("21522180_UDPClient: Server send to client a message: "+ received);
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		
		Scanner scanner = new Scanner(System.in);
		//
		new Thread(() -> {
			while (true) {
				String message = scanner.nextLine();
				byte[] buf = message.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddress, SERVER_PORT);
				try {
					socket.send(packet);
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
