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
	private List<Carta> mano;
	private String nombre;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private Scanner teclado;
	
	public ModeloCliente(String n,Socket s) {
		this.mesa=new Mesa();
		this.mano=new ArrayList<>();
		this.nombre=n;
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
			if(cartas.size()!=0) {
				for(int i=0;i<cartas.size();i++) {
					this.mesa.place(cartas.get(i));
				}
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
			System.out.println(s);

			List<Carta> cartas=this.traducirCartas(s);
			System.out.println(cartas.size());
			
			for(int i=0;i<cartas.size();i++) {
				this.mano.add(cartas.get(i));
			}
			System.out.println(this.mano.toString());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Carta elegirCarta() {
		System.out.println("Tu mano: \r\n");
		System.out.print(this.mano.toString());
		
		List<Carta> colocables=new ArrayList<>();
		for(int i=0;i<this.mano.size();i++) {
			if(this.mesa.placeable(this.mano.get(i))) {
				colocables.add(this.mano.get(i));
			}
		}
		
		if(colocables.size()==0) {
			return null;
		}
		else {
			System.out.print("Elige una carta: ");
			Carta c=colocables.get(this.teclado.nextInt());
			return c;
		}
	}
	
	public void enviarCarta(Carta c) {
		try {
			this.out.write(c.toString()+"\r\n");
			this.out.flush();
			this.mano.remove(c);
			this.mesa.place(c);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean robar() {
		try {
			this.out.write("robar\r\n");
			this.out.flush();
			
			String s=this.in.readLine();
			if(s.equals("vacio")) {
				return false;
			}
			else {
				Carta c=this.traducirCartas(this.in.readLine()).get(0);
				this.mano.add(c);
				return true;
			}			
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void pasar() {
		try {
			this.out.write("pasar\r\n");
			this.out.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean continua() {
		try {
			return in.readLine().equals("continua");
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getName() {
		return this.nombre;
	}
	
	public Mesa getMesa() {
		return this.mesa;
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
