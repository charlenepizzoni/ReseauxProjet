package factorielle;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientFactorielle {
	int port;
	InetAddress ip;
	int valeur;
	
	
	public ClientFactorielle(int port, InetAddress ip, int valeur) {
		this.port = port;
		this.ip = ip;
		this.valeur = valeur;
		
	}
	
	public int demanderCalcul () throws IOException{
		int res;
		Socket s = new Socket(this.ip, this.port);
		
		
		s.close();
		return res;
	}
	
	/**
	 * on doit mettre le port en premier argument, l'ip où on veut se connecter en deuxieme, puis la valeur à calculer
	 * @param argv
	 * @throws UnknownHostException 
	 */
	public static void main(String[] argv) throws UnknownHostException{
		int port  = Integer.parseInt(argv[0]);
		InetAddress ip = InetAddress.getByName(argv[1]);
		int valeur = Integer.parseInt(argv[2]);
				
		ClientFactorielle cf = new ClientFactorielle(port, ip, valeur);
		
		
	}
	
}
