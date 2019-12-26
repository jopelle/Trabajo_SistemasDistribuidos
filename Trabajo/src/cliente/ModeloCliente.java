package cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cartas.*;

public class ModeloCliente {
	
	private Mesa mesa;
	private List<Carta> mano;
	private Socket socket;
	private Scanner teclado;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public ModeloCliente(Socket s) {
		this.mesa=new Mesa();
		this.mano=new ArrayList<>();
		this.socket=s;
		
		try{
			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.ois = new ObjectInputStream(this.socket.getInputStream());
			this.teclado=new Scanner(System.in);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Carta> getMano() {
		return this.mano;
	}
	
	public void elegirNombre() {
		try {
			System.out.print("Tu nombre: ");
			String s=this.teclado.next();
			System.out.println();

			oos.writeObject(s);

			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String recibirMensaje() {
		try{	
			String s=(String)this.ois.readObject();
			System.out.println(s);
			if(s.equals("actualizar")) {
				this.mesa=(Mesa)this.ois.readObject();
				this.mesa.showMesa();
				return "actualizar";
			}
			else if(s.equals("fin")) {
				this.fin();
				return "fin";
			}
			else {	
				Mesa m=(Mesa)this.ois.readObject();
				m.showMesa();
				this.mesa=m;//(Mesa)this.ois.readObject();
				System.out.println("\r\nTu turno");
				this.mesa.showMesa();
				return "continua";
			}
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*Recibe y traduce la mano*/
	public void recibirMano() {
		try {

			this.mano=(List<Carta>)ois.readObject();
			System.out.println(this.mano);
			
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Carta elegirCarta() {
		System.out.print("Tu mano: ");
		System.out.println(this.mano.toString());
		
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
			System.out.print("Puedes colocar: ");
			System.out.println(colocables.toString());
			System.out.print("Elige una carta: ");
			Carta c=colocables.get(this.teclado.nextInt());
			return c;
		}
	}
	
	public void enviarCarta(Carta c) {
		try {
			String s="carta";
			oos.writeObject(s);

			oos.writeObject(c);
			oos.reset();
			this.mano.remove(c);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean robar() {
		try {
			String s="robar";
			oos.writeObject(s);
			
			s=(String)ois.readObject();

			if(s.equals("vacio")) {
				return false;
			}
			else {
				Carta c=(Carta)ois.readObject();
				System.out.println("Robada: "+c);

				this.mano.add(c);
				return true;
			}			
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void pasar() {
		try {
			String s="pasar";
			oos.writeObject(s);

			System.out.println("No puedes colocar,pasas turno");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fin() {
		try {
			String s=(String)ois.readObject();
			System.out.println(s);
			this.cerrarCosas();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cerrarCosas() {
		try {
			this.teclado.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
