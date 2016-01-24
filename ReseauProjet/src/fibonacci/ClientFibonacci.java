package fibonacci;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import factorielle.ClientFactorielle;

public class ClientFibonacci {

	
	int port;
	InetAddress ip;
	int valeur;
	
	
	public ClientFibonacci(int port, InetAddress ip, int valeur) {
		this.port = port;
		this.ip = ip;
		this.valeur = valeur;
		
	}
	
	/**
	 * 
	 * @return la valeur de la factorielle de l'entier this.valeur
	 * @throws IOException
	 */
	public int demanderCalcul (int port, InetAddress ip) throws IOException{
		int res;
		Socket s = new Socket(ip, port);
		InputStream input = s.getInputStream();
		OutputStream output = s.getOutputStream();
		output.write(this.valeur);
		res = input.read();		
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
		int resultat = -1;
		ClientFibonacci cf = new ClientFibonacci(port, ip, valeur);
		try {
			resultat = cf.demanderCalcul(port, ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("(" + valeur + ")! = " + resultat);		
	}
}
