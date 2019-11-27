package scartas;

public class SJugador {
	protected java.util.List<SCarta> hand;
	protected String name;
	
	public SJugador(String n) {
		//Pre: Constructor of Jugador. Receives a String.
		//Post: Creates a Jugador with the given parameter(Name).
		this.name=n;
		this.hand=new java.util.ArrayList<SCarta>();
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
	
	public String handToString() {
		//Pre: Redefinition of the toString method.
		//Post: Returns a string representation of the player.
		return this.hand.toString();
	}
	
	public void recibirCarta(SCarta c) {
		//Pre: Receive a Carta.
		//Post: Adds the given card to the player's hand. 
		this.hand.add(c);
	}
	
	public SCarta elegirCarta(SMesa m) {
		//enviar mesa al cliente
		
		return null;
	}

}
