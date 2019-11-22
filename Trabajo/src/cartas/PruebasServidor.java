package cartas;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class PruebasServidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		java.util.Deque<Carta> oro=new ArrayDeque<>();
		java.util.Deque<Carta> basto=new ArrayDeque<>();
		java.util.List<Carta> mano=new ArrayList<>();;

		
		for(int a=1;a<=10;a++) {
			Carta e=new Carta(Palo.Bastos,a);
			basto.add(e);
			e=new Carta(Palo.Oros,a);
			oro.add(e);
			mano.add(e);
		}
		
		String cartas=oro.toString()+"-"+basto.toString();
		
		/*System.out.println(cartas);
		
		String[]split=cartas.split("-");
		
		System.out.println(split[0]);*/
		
		try (ServerSocket server=new ServerSocket(6666);
			Socket cliente=server.accept();
			BufferedWriter out= new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));){
			
			out.write(cartas+"\r\n");
			out.write(mano+"\r\n");
				
		}catch(IOException e) {e.printStackTrace();}
	}

}
