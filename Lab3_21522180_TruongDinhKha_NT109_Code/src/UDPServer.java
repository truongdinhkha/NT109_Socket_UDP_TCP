import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UDPServer {
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
	
	
	private static DatagramSocket socket;
	private static int PORT = 9090;
	public static InetAddress address;
	public static int port;

	public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub

		socket = new DatagramSocket(PORT);
		System.out.println("Server started on port "+ PORT);
                log("21522180_UDPServer: Server started on port "+ PORT);
		Scanner scanner = new Scanner(System.in);
	
	new Thread(() -> {
		while (true) {
			try {
				byte[] buf = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				address = packet.getAddress();
				port = packet.getPort();
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Client: "+ received);
                                log("21522180_UDPServer: Client send a message: "+received);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}).start();
	
	new Thread(() -> {
		while (true) {
			String message = scanner.nextLine();
			byte[] buf = message.getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
			try {
				socket.send(packet);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}).start();
	}
}
