package servidor;


import java.util.List;

import cartas.Carta;

public class Jugador {
	private List<Carta> mano;
	private String nombre;
	
	public Jugador() {
		//Pre: Constructor of Jugador. Receives a String.
		//Post: Creates a Jugador with the given parameter(Name).
		this.nombre="jugador";
		this.mano=new java.util.ArrayList<Carta>();
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
	
	public List<Carta> getMano() {
		//Pre: Redefinition of the toString method.
		//Post: Returns a string representation of the player.
		return this.mano;
	}
	
	public void recibirCarta(Carta c) {
		//Pre: Receive a Carta.
		//Post: Adds the given card to the player's hand. 
		this.mano.add(c);
	}
	public void eliminarCarta(Carta c) {
		this.mano.remove(c);
	}
}
