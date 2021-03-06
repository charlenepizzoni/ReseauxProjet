package factorielle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import factorielle.ClientFactorielle;

public class ServeurFactorielle {
	int port;
	InetAddress ip;
	ServerSocket sSocket;
	Map<Integer, Long> cache;
	
	ServeurFactorielle(int port, InetAddress ip){
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
		int port;
		InetAddress ip;
		Socket socket;
		
		public ClientThread(int port, InetAddress ip, Socket socket){
			this.port = port;
			this.ip = ip;
			this.socket = socket;
		}
		
		public void run(){
			int val;
			long res;
			InputStream input;
			//OutputStream output;
			try {
				input = socket.getInputStream();
				Scanner sc = new Scanner(input);
		        if (sc.hasNext()) {
		            val = sc.nextInt();
		            
		            if (val < 1){
		            	res = 1;
		            }
		            else if(getCache().containsKey(val)){
		            	res = getCache().get(val);		            	
		            }
		            		            
		            else {
		            	ClientFactorielle cf = new ClientFactorielle(port, ip, val-1);
		            	res = val * cf.demanderCalcul();
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
	
	public void runServeur() throws IOException{
		Socket socket;
		while(true){
			socket = sSocket.accept();
			ClientThread clientThread = new ClientThread(port, ip, socket);
			clientThread.start();
		}
	}
	
	synchronized public Map<Integer, Long> getCache(){
		return this.cache;
	}
	
	public static void main(String[] argv) throws IOException{
		int port  = Integer.parseInt(argv[0]);
		InetAddress ip;
		ip = InetAddress.getLocalHost();
		ServeurFactorielle sf = new ServeurFactorielle(port, ip);
		sf.runServeur();
	}
	
}
