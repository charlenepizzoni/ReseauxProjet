package factorielle;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServeurFactorielle {
	int port;
	InetAddress ip;
	ServerSocket sSocket;
	Map<Integer, Integer> cache;
	
	ServeurFactorielle(int port, InetAddress ip){
		this.port = port;
		this.ip = ip;
		try {
			this.sSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cache = new HashMap<Integer, Integer>();
	}
	
	private class ClientThread extends Thread {
		int port;
		InetAddress ip;
		Socket socket;
		
		public ClientThread(int port, InetAddress ip, Socket socket){
			this.port = port;
			this.ip = ip;
			this.socket = socket;
		}
		
		public void run(){
			
		}
	}
	
	public void run() throws IOException{
		Socket socket;
		while(true){
			socket = sSocket.accept();
			ClientThread clientThread = new ClientThread(port, ip, socket);
			clientThread.run();
		}
	}
	
	public void main(String[] argv) throws IOException{
		int port  = Integer.parseInt(argv[0]);
		InetAddress ip;
		ip = InetAddress.getLocalHost();
		ServeurFactorielle sf = new ServeurFactorielle(port, ip);
		sf.run();
		
		/*InputStream input = socket.getInputStream();
		Scanner sc = new Scanner(input);
        while (true) {
            if (sc.hasNext()) {
                String msg = sc.nextLine();
                System.out.println(msg);
            }//if
        }//while */
	}
	
}
