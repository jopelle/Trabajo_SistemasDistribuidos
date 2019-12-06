package scartas;
import java.util.ArrayDeque;

public class SMesa {
	private java.util.Deque<SCarta> oro;
	private java.util.Deque<SCarta> copa;
	private java.util.Deque<SCarta> espada;
	private java.util.Deque<SCarta> basto;
	
	public SMesa() {
		//Pre: Constructor of Mesa.
		//Post: Creates a Deque for each card suit.
		this.oro=new ArrayDeque<SCarta>();
		this.copa=new ArrayDeque<SCarta>();
		this.espada=new ArrayDeque<SCarta>();
		this.basto=new ArrayDeque<SCarta>();
	}
	public void place(SCarta c) {
		//Pre: Receives a Carta.
		//Post: Places the given  card on the right place.
		if(c.getPalo()==SPalo.Oros) {
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
		if(c.getPalo()==SPalo.Bastos) {
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
		if(c.getPalo()==SPalo.Espadas) {
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
		if(c.getPalo()==SPalo.Copas) {
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
	
	public boolean placeable(SCarta c) {
		//Pre: Receives a Carta.
		//Post: Returns true if the given card can be placed.
		if(c.getValor()==5) {
			return true;
		}
		else{
			if(c.getPalo().equals(SPalo.Oros)) {
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
			else if(c.getPalo().equals(SPalo.Bastos)) {
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
			else if(c.getPalo().equals(SPalo.Copas)) {
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
	
	public String toString() {
		return this.oro.toString()+this.copa.toString()+this.basto.toString()+this.espada.toString();
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
