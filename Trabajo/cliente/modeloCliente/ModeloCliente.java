package modeloCliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cartas.Carta;
import cartas.Jugador;
import cartas.Mesa;
import cartas.Palo;

public class ModeloCliente {
	
	private Mesa mesa;
	private Jugador jugador;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private Scanner teclado;
	
	public ModeloCliente(String n,Socket s) {
		this.mesa=new Mesa();
		this.jugador=new Jugador(n);
		this.socket=s;
		
		try{
			this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.teclado=new Scanner(System.in);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void recibirMesa() {
		try{
			String s=this.in.readLine();
			List<Carta> cartas=this.traducirCartas(s);
		
			for(int i=0;i<cartas.size();i++) {
				this.mesa.place(cartas.get(i));
			}
			mesa.showMesa();
			System.out.println("\r\nTu turno");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void recibirMano() {
		try {
			String s=in.readLine();
			List<Carta> cartas=this.traducirCartas(s);
			
			for(int i=0;i<cartas.size();i++) {
				this.jugador.recibirCarta(cartas.get(i));
			}
			System.out.println(this.jugador.handToString());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enviarCarta() {
		System.out.println("Elige una carta: \r\n");
		System.out.println(this.jugador.handToString());
		//Carta c=this.jugador.elegirCarta(this.mesa);
		Carta c=this.jugador.hand.get(this.teclado.nextInt());
		try {
			this.out.write(c.toString()+"\r\n");
			this.out.flush();
			this.jugador.eliminarCartaMano(c);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return this.jugador.getName();
	}
	
	public Mesa getMesa() {
		return this.mesa;
	}
	
	public Jugador getMano(){
		return this.jugador;
	}
	
	private List<Carta> traducirCartas(String s){
		List<Carta> listaCartas=new ArrayList<>();
		
		s=s.replace("[", "");
		s=s.replace("]", "");
		
		String[]cartas=s.split(",");
		String[]stringCarta;
		
		Carta carta;
		int valor;
		
		for(int i=0;i<cartas.length;i++) {
			if(i!=0){
				cartas[i]=cartas[i].replaceFirst(" ", "");
			}
			stringCarta=cartas[i].split(" ");
			
			if(stringCarta[0].equals("S")) {
				valor=8;
			}
			else if(stringCarta[0].equals("C")) {
				valor=9;
			}
			else if(stringCarta[0].equals("R")) {
				valor=10;
			}
			else {
				valor=Integer.valueOf(stringCarta[0]);
			}
			
			if(stringCarta[1].equals("Oros")) {
				carta=new Carta(Palo.Oros,valor);
			}
			else if(stringCarta[1].equals("Espadas")) {
				carta=new Carta(Palo.Espadas,valor);
			}
			else if(stringCarta[1].equals("Bastos")) {
				carta=new Carta(Palo.Bastos,valor);
			}
			else {
				carta=new Carta(Palo.Copas,valor);
			}
			
			listaCartas.add(carta);
		}
		return listaCartas;
	}
}
