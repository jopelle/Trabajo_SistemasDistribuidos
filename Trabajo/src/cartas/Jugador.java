package cartas;
import cartas.*;

abstract public class Jugador {
	protected java.util.List<Carta> hand;
	protected String name;
	protected int handValue;
	
	public Jugador(String n) {
		//Pre: Constructor of Jugador. Receives a String.
		//Post: Creates a Jugador with the given parameter(Name).
		this.name=n;
		this.hand=new java.util.ArrayList<Carta>();
		this.handValue=0;
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
		return ("Jugador: "+this.name+"\nMano: "+this.hand);
	}
	
	public int getHandValue() {
		//Pre:
		//Post: Returns the sum of the values of the player's hand.
		return this.handValue;
	}
	
	abstract public Carta elegirCarta(Mesa m);
 
	abstract public void recibirCarta(Carta c);
}
