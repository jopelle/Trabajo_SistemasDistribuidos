package cartas;
import java.util.List;

import cartas.Carta;
import cartas.Mesa;

public class Jugador {
	private List<Carta> hand;
	private String name;
	
	public Jugador(String n) {
		//Pre: Constructor of Jugador. Receives a String.
		//Post: Creates a Jugador with the given parameter(Name).
		this.name=n;
		this.hand=new java.util.ArrayList<Carta>();
	}
	
	public int cardsInHand() {
		//Pre: 
		//Post: Returns the number of cards in the player's hand.
		return this.hand.size();
	}
	
	public String getName() {
		//Pre:
		//Post: Returns the name of the player.
		return this.name;
	}
	
	public String toString() {
		//Pre: Redefinition of the toString method.
		//Post: Returns a string representation of the player.
		return ("Jugador: "+this.name+"\nhand: "+this.hand);
	}
	
	public String handToString(){
		return this.hand.toString();
	}
	
	public void eliminarCartaMano(Carta c) {
		this.hand.remove(c);
	}
	
	public Carta elegirCarta(Mesa m) {
		return null;
	}
 
	public void recibirCarta(Carta c) {
		this.hand.add(c);
	}
}
