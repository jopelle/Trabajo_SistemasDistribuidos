package scartas;

public class SCarta {
	private SPalo sPalo;
	private int valor;
	
	public SCarta(SPalo p,int v) {
		this.sPalo=p;
		this.valor=v;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public SPalo getPalo() {
		return this.sPalo;
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
		return(v+" "+this.sPalo);
	}
	
	public boolean equals(Object c) {
		if(c instanceof SCarta) {
			SCarta sCarta=(SCarta) c;
			if(this.sPalo==sCarta.getPalo() && this.valor==sCarta.getValor()){
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
