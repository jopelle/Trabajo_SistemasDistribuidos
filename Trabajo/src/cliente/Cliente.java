package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cartas.Carta;
import cartas.Mesa;

public class Cliente extends Thread{
	private Mesa mesa;
	private List<Carta> mano;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private InterfazJuego interfaz;
	private Carta paraColocar;
	
	public Cliente(InterfazJuego ij){
		this.mesa=new Mesa();
		this.mano=new ArrayList<>();
		this.interfaz=ij;
		this.paraColocar=null;
		try{
			this.socket=new Socket("localhost",6666);
			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.ois = new ObjectInputStream(this.socket.getInputStream());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		boolean fin=false;
		String mensaje;
		
		//Enviar nombre
		this.elegirNombre();
		
		//Recibe la mano
		this.recibirMano();
		
		while(!fin) {
			/*Recibe un mensaje, "continua" (tu turno), "actualizar"(se actualiza la mesa)
			o "fin"(la partida se acabó)*/		
			mensaje=this.recibirMensaje();
			
			if(mensaje.equals("actualizar")) {
				this.actualizarTurno();
				this.recibirMesa();
			}
			else if(mensaje.equals("mesa")) {
				//Elegir una carta, si no se puede elegir (null), se roba y si tampoco se pasa
				this.recibirMesa();
				
				Carta c=this.elegirCarta();
				if(c==null) {
					boolean b=this.robar();
					if(b==true) {
						c=this.elegirCarta();
						if(c!=null) {
							this.enviarCarta(c);
						}
						else { this.pasar();}
					}
				}
				else {
					this.enviarCarta(c);
				}
			}
			else {
				fin=true;
			}
		}
		this.fin();
	}
	
	public void elegirNombre() {
		try {
			String s=interfaz.getNombre();
			oos.writeObject(s);

			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String recibirMensaje() {
		try{	
			String s=(String)this.ois.readObject();
			return s;

		}catch(IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void actualizarTurno() {
		try{	
			String s=(String)this.ois.readObject();
			interfaz.setMensaje("Turno de "+s);
			
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void recibirMesa() {
		try{	
			this.mesa=(Mesa)this.ois.readObject();
			interfaz.colocarMesa(this.mesa);

		}catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*Recibe y traduce la mano*/
	public void recibirMano() {
		try {

			this.mano=(List<Carta>)ois.readObject();
			for(Carta c:this.mano) {
				interfaz.aniadirrCarta(c);
			}

		}catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Carta elegirCarta() {
		
		List<Carta> colocables=new ArrayList<>();
		for(int i=0;i<this.mano.size();i++) {
			if(this.mesa.placeable(this.mano.get(i))) {
				colocables.add(this.mano.get(i));
				interfaz.colocable(this.mano.get(i));
			}
		}
		
		if(colocables.size()==0) {
			return null;
		}
		else {
			while(paraColocar==null) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Carta c=paraColocar;
				
			interfaz.descolocables();
			paraColocar=null;
			return c;
		}
	}
	
	public void setParaColocar(Carta c) {
		this.paraColocar=c;
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

				this.mano.add(c);
				interfaz.aniadirrCarta(c);
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
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fin() {
		try {
			String s=(String)ois.readObject();
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
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}