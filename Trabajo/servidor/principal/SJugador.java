package principal;

import scartas.SCarta;

public class SJugador {
	private java.util.List<SCarta> mano;
	private String nombre;
	
	public SJugador(String n) {
		//Pre: Constructor of Jugador. Receives a String.
		//Post: Creates a Jugador with the given parameter(Name).
		this.nombre=n;
		this.mano=new java.util.ArrayList<SCarta>();
	}
	
	public int numeroCartas() {
		//Pre: 
		//Post: Returns the number of cards in the player's hand.
		return this.mano.size();
	}
	
	public String getNombre() {
		//Pre:
		//Post: Returns the name of the player.
		return this.nombre;
	}
	
	public void setNombre(String n) {
		this.nombre=n;
	}
	
	public String getStringMano() {
		//Pre: Redefinition of the toString method.
		//Post: Returns a string representation of the player.
		return this.mano.toString();
	}
	
	public void recibirCarta(SCarta c) {
		//Pre: Receive a Carta.
		//Post: Adds the given card to the player's hand. 
		this.mano.add(c);
	}
	public void eliminarCarta(SCarta c) {
		this.mano.remove(c);
	}
}
