package cartas;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class PruebasCliente {
	public static void main(String[] args) {
		
		try(Socket c = new Socket("localhost", 6666);
			DataInputStream in=new DataInputStream(c.getInputStream());){
			
			String s=in.readLine();
			String m=in.readLine();
			
			System.out.println(s);
			System.out.println(m);
						
		}catch(IOException e) {e.printStackTrace();}
	}
}
