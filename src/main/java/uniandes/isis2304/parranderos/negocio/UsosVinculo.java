package uniandes.isis2304.parranderos.negocio;

public class UsosVinculo implements VOUsosVinculo{

	private String vinculo; 
	
	private int veces;
	
	public UsosVinculo() {
		this.veces=0; 
		this.vinculo="";
	}
	public UsosVinculo(String pVinculo, int pVeces) {
		this.veces=pVeces; 
		this.vinculo=pVinculo; 
	}
	public void setVeces(int pVeces) {
		this.veces=pVeces;
	}
	
	public void setVinculo(String pVinculo) {
		this.vinculo=pVinculo;				
	}
	@Override
	public String getVinculo() {
		return this.vinculo;
	}

	@Override
	public int getVeces() {
		return this.veces;
	}
	public String toString() {
		return "Uso Vinculo [Vinculo=" + this.vinculo + ", Veces=" + this.veces+"]";
	}

}
