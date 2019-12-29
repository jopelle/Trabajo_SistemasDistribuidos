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

/*Representa al jugador y se encarga recibir y enviar los datos necesarios para el
 * funcionamiento de la partida y de actualizar la interfaz*/
public class Cliente extends Thread{
	private Mesa mesa;
	private List<Carta> mano;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private InterfazJuego interfaz;
	
	/*Esta carta representa la carta que se quiera colocar, obtendra un valor 
	 * cuando sea el turno del jugador*/
	private Carta paraColocar;
	
	public Cliente(InterfazJuego ij,String ip){
		this.mesa=new Mesa();
		this.mano=new ArrayList<>();
		this.interfaz=ij;
		this.paraColocar=null;
		try{
			this.socket=new Socket(ip,6666);
			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.ois = new ObjectInputStream(this.socket.getInputStream());
		}catch(IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
		}
	}

	//Durante la ejecucion el cliente ira recibiendo y enviando los datos de la partida y actualizandolos en la interfaz
	public void run() {
		boolean fin=false;
		String mensaje;
		
		//Enviar nombre
		this.elegirNombre();
		
		//Recibe la mano
		this.recibirMano();
		
		//Recibir turno inicial
		interfaz.setMensaje(this.recibirMensaje());
		
		while(!fin) {
			/*Recibe un mensaje, "mesa" (tu turno), "actualizar"(se actualiza la mesa)
			o "fin"(la partida se acabó)*/		
			mensaje=this.recibirMensaje();
			
			if(mensaje.equals("actualizar")) {
				this.actualizarTurno();
				this.recibirMesa();
			}
			else if(mensaje.equals("mesa")) {
				this.recibirMesa();
				
				/* El jugador elige una carta si no tiene para colocar (null) roba una vez, si sigue
				 * sin tener para colocar pasa el turno*/
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
	
	//Envia al servidor el nombre del jugador
	public void elegirNombre() {
		try {
			String s=interfaz.getNombre();
			oos.writeObject(s);

			
		}catch(IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
		}
	}
	
	//Recibe un string del servidor
	public String recibirMensaje() {
		try{	
			String s=(String)this.ois.readObject();
			return s;

		}catch(IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*recibe del servidor el nombre del jugador al que le toca jugar
	 * y lo actualiza en la interfaz*/
	public void actualizarTurno() {
		try{	
			String s=(String)this.ois.readObject();
			interfaz.setMensaje("Turno de "+s);
			
		}catch(IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*Recibe la mesa del servidor y la actualiza en la interfaz*/
	public void recibirMesa() {
		try{	
			this.mesa=(Mesa)this.ois.readObject();
			interfaz.colocarMesa(this.mesa);

		}catch(IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*Recibe la mano y la añade a la interfaz*/
	public void recibirMano() {
		try {

			this.mano=(List<Carta>)ois.readObject();
			for(Carta c:this.mano) {
				interfaz.aniadirrCarta(c);
			}

		}catch(IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*Se espera a que el jugador elija una carta (paraColocar!=null)
	 * pero antes se habilitan en la interfaz las cartas que se puedan colocar
	 * Si no puede colocar (numero de cartas colocables 0) se devuelve null 
	 * y si puede se devuelve la carta seleccionada*/
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
	
	/*Da el valor dado a paraColocar*/
	public void setParaColocar(Carta c) {
		this.paraColocar=c;
	}
	
	//Envia una carta al servidor
	public void enviarCarta(Carta c) {
		try {
			String s="carta";
			oos.writeObject(s);

			oos.writeObject(c);
			oos.reset();
			this.mano.remove(c);
		}catch(IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
		}
	}
	
	/*Roba una carta del servidor
	 * Envia el mensaje de "robar" y luego espera a recibir una carta, 
	 * la cual añade a la mano y a la interfaz, si no hay mas cartas para robar,
	 * se recibirá "vacio" y no se hará nada
	 * Se devuelve true si se ha robado y false si no*/
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
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*Pasa el truno del jugador, enviando al servidor "pasar"*/
	public void pasar() {
		try {
			String s="pasar";
			oos.writeObject(s);
		}catch(IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
		}
	}
	
	/*Finaliza la partida, recibe del servidor el ganador 
	 * y cierra el socket*/
	public void fin() {
		try {
			String s=(String)ois.readObject();
			interfaz.setMensaje(s);
			this.cerrarCosas();
		}catch (IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Cierra el socket
	public void cerrarCosas() {
		try {
			this.socket.close();
		} catch (IOException e) {
			interfaz.setMensaje("Fallo en el servidor");
			e.printStackTrace();
		}
	}
}