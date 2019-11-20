package cartas;
import java.util.ArrayDeque;

public class Mesa {
	private java.util.Deque<Carta> oro;
	private java.util.Deque<Carta> copa;
	private java.util.Deque<Carta> espada;
	private java.util.Deque<Carta> basto;
	
	public Mesa() {
		//Pre: Constructor of Mesa.
		//Post: Creates a Deque for each card suit.
		this.oro=new ArrayDeque<Carta>();
		this.copa=new ArrayDeque<Carta>();
		this.espada=new ArrayDeque<Carta>();
		this.basto=new ArrayDeque<Carta>();
	}
	public void place(Carta c) {
		//Pre: Receives a Carta.
		//Post: Places the given  card on the right place.
		if(c.getPalo()==Palo.Oros) {
			if(c.getValor()==5) {
				this.oro.add(c);
			}
			else if(c.getValor()>this.oro.getLast().getValor()) {
				this.oro.addLast(c);
			}
			else {
				this.oro.addFirst(c);
			}
		}
		if(c.getPalo()==Palo.Bastos) {
			if(c.getValor()==5) {
				this.basto.add(c);
			}
			else if(c.getValor()>this.basto.getLast().getValor()) {
				this.basto.addLast(c);
			}
			else {
				this.basto.addFirst(c);
			}
		}
		if(c.getPalo()==Palo.Espadas) {
			if(c.getValor()==5) {
				this.espada.add(c);
			}
			else if(c.getValor()>this.espada.getLast().getValor()) {
					this.espada.addLast(c);
			}
			else {
				this.espada.addFirst(c);
			}
		}
		if(c.getPalo()==Palo.Copas) {
			if(c.getValor()==5) {
				this.copa.add(c);
			}
			else if(c.getValor()>this.copa.getLast().getValor()) {
				this.copa.addLast(c);
			}
			else {
				this.copa.addFirst(c);
			}
		}
	}
	public boolean placeable(Carta c) {
		//Pre: Receives a Carta.
		//Post: Returns true if the given card can be placed.
		if(c.getValor()==5) {
			return true;
		}
		else{
			if(c.getPalo().equals(Palo.Oros)) {
				if(this.oro.isEmpty()) {
					return false;
				}
				else if((c.getValor()-this.oro.getLast().getValor())==1 || (this.oro.getFirst().getValor()-c.getValor())==1 ) {
					return true;
				}
				else {
					return false;
				}
			}
			else if(c.getPalo().equals(Palo.Bastos)) {
				if(this.basto.isEmpty()) {
					return false;
				}
				else if((c.getValor()-this.basto.getLast().getValor())==1 || (this.basto.getFirst().getValor()-c.getValor())==1 ) {
					return true;
				}
				else {
					return false;
				}
			}
			else if(c.getPalo().equals(Palo.Copas)) {
				if(this.copa.isEmpty()) {
					return false;
				}
				else if((c.getValor()-this.copa.getLast().getValor())==1 || (this.copa.getFirst().getValor()-c.getValor())==1 ) {
					return true;
				}
				else {
					return false;
				}
			}
			else{
				if(this.espada.isEmpty()) {
					return false;
				}
				else if((c.getValor()-this.espada.getLast().getValor())==1 || (this.espada.getFirst().getValor()-c.getValor())==1 ) {
					return true;
				}
				else {
					return false;
				}
			}
		}
	}
	public void showMesa() {
		//Pre:
		//Post: Shows the state of the table.
		System.out.println("Mesa\n"+this.oro);
		System.out.println(this.basto);
		System.out.println(this.copa);
		System.out.println(this.espada+"\n");
	}
}
