package factorielle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import factorielle.ServeurFactorielle;

public class ClientFactorielle {
	int port;
	InetAddress ip;
	int valeur;
	
	
	public ClientFactorielle(int port, InetAddress ip, int valeur) {
		this.port = port;
		this.ip = ip;
		this.valeur = valeur;
		
	}
	
	/**
	 * 
	 * @return la valeur de la factorielle de l'entier this.valeur
	 * @throws IOException
	 */
	public long demanderCalcul () throws IOException{
        InetAddress addresse = this.ip;
        Socket socket = new Socket(addresse, this.port);
 
        Scanner input = new Scanner(socket.getInputStream());
        PrintStream output = new PrintStream(socket.getOutputStream());
 
        output.println(this.valeur);
 
        Long result = input.nextLong();
 
        input.close();
        socket.close();
 
        return result;
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
		long resultat = -1;
		ClientFactorielle cf = new ClientFactorielle(port, ip, valeur);
		
		try {
			resultat = cf.demanderCalcul();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("(" + valeur + ")! = " + resultat);		
	}
	
}
