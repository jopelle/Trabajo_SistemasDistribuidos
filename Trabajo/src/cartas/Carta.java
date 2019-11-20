package cartas;

public class Carta {
	private Palo palo;
	private int valor;
	
	public Carta(Palo p,int v) {
		this.palo=p;
		this.valor=v;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public Palo getPalo() {
		return this.palo;
	}
	
	public String toString() {
		String v;
		if(this.valor==10) {
			v="R";
		}
		else if(this.valor==9) {
			v="C";
		}
		else if(this.valor==8) {
			v="S";
		}
		else {
			v=String.valueOf(this.valor);
		}
		return("["+v+" "+this.palo+"]");
	}
	public boolean equals(Object c) {
		if(c instanceof Carta) {
			Carta carta=(Carta) c;
			if(this.palo==carta.getPalo() && this.valor==carta.getValor()){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
