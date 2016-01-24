package fibonacci;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import fibonacci.ClientFibonacci;

public class ServeurFibonacci extends Thread {

	static int port1;
	static int port2;
	int port;
	InetAddress ip;
	ServerSocket sSocket;
	Map<Integer, Long> cache;
	
	ServeurFibonacci(int port, InetAddress ip){
		this.port = port;
		this.ip = ip;
		try {
			this.sSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cache = new HashMap<Integer, Long>();
	}
	
	private class ClientThread extends Thread {
		int port1;
		int port2;
		InetAddress ip;
		Socket socket;
		
		public ClientThread(int port1, int port2, InetAddress ip, Socket socket){
			this.port1 = port1;
			this.port2 = port2;
			this.ip = ip;
			this.socket = socket;
		}
		
		public void run(){
			int val;
			long res;
			InputStream input;
			OutputStream output;
			try {
				input = socket.getInputStream();
				Scanner sc = new Scanner(input);
		        if (sc.hasNext()) {
		            String msg = sc.nextLine();
		            val = Integer.parseInt(msg);
		            if (val < 2){
		            	res = 1;
		            }
		            else if(getCache().containsKey(val)){
		            	res = getCache().get(val);		            	
		            }
		            		            
		            else {
		            	ClientFibonacci cf1 = new ClientFibonacci(port1, ip, val-1);
		            	ClientFibonacci cf2 = new ClientFibonacci(port2, ip, val-2);
		            	res = cf1.demanderCalcul(this.port1, this.ip)  +  cf2.demanderCalcul(this.port2, this.ip);
		            	getCache().put(val, res);
		            }
		            PrintStream outputPrint = new PrintStream(socket.getOutputStream());
			        outputPrint.println(res);
		        }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run(){
		Socket socket;
		while(true){
			try {
				socket = sSocket.accept();
			
			ClientThread clientThread = new ClientThread(port1, port2, ip, socket);
			clientThread.run();
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Map<Integer, Long> getCache(){
		return this.cache;
	}
	
	public static void main(String[] argv) throws IOException{
		port1  = Integer.parseInt(argv[0]); // port du premier serveur
		port2  = Integer.parseInt(argv[1]); // port du second serveur
		InetAddress ip = InetAddress.getLocalHost(); // IP du premier serveur
		ServeurFibonacci sf1 = new ServeurFibonacci(port1, ip);
		ServeurFibonacci sf2 = new ServeurFibonacci(port2, ip);
		sf1.start();
		sf2.start();
	}
	
	
}
