package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class pruebas {

	public static void main(String[] args) {
		try{
			Socket socket = new Socket("localhost", 6666);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Scanner teclado=new Scanner(System.in);

			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
