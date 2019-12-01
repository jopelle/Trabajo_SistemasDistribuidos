package scartas;
import java.util.ArrayList;
import java.util.List;

import principal.SJugador;

public class Maquina extends SJugador{
	 public Maquina(String n) {
		 super(n);
	 }
	public SCarta elegirCarta(SMesa m) {
		//Pre: Receive a Mesa.
		//Post: Returns a placeable card from the hand and remove it. 
		//Se elige la primera carta que puede colocarse,la "estrategia inteligente" está en la forma de recibir las cartas
		SCarta c;
		if(this.hand.contains(Baraja.cincoOro)) {
			System.out.println("El jugador "+this.name+" empieza con el "+Baraja.cincoOro);
			this.hand.remove(Baraja.cincoOro);
			return Baraja.cincoOro;
		}
		else {
			int n=this.cardsInHand();
			for(int i=0;i<n;i++) {
				c=this.hand.get(i);
				if(m.placeable(c)) {
					this.hand.remove(c);
					return c;
				}
			}
			System.out.println("No tiene para colocar\n");
			return null;
		}
	}
	public void recibirCarta(SCarta c) {
		//Pre: Receive a Carta.
		//Post: Adds the given card to the player's hand.
		/*La "estrategia inteligente" consiste en, estas en ordenar de mayor
		a menor las cartas por valor, y además colocar los cincos al final 
		para mantenerlos el mayor tiempo posible*/
		List<SCarta> cincos=new ArrayList<SCarta>();
		int i=0;
		if(this.cardsInHand()==0) {
			this.hand.add(c);
		}
		else {
			if(c.getValor()==5) {
				cincos.add(c);
			}
			else if(c.getValor()>this.hand.get(0).getValor()) {
				this.hand.add(0,c);
			}
			else {
				while(i<this.cardsInHand() && this.hand.get(i).getValor()>c.getValor()) {
					i++;
				}
				if(i==this.cardsInHand()) {
					this.hand.add(c);
				}
				else {
					this.hand.add(i,c);
				}
			}
			for(int j=0;j<cincos.size();j++) {
				this.hand.add(cincos.get(j));
			}
		}
	}
}
