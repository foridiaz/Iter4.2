package uniandes.isis2304.parranderos.negocio;

public class Contrato_Hab_Vivienda implements VOContrato_Hab_Vivienda{

	public long id_contrato;
	
	public long id_vivienda;
	
	public int meses_contratados;
	
	
	public Contrato_Hab_Vivienda() {
		this.id_contrato =0;
		this.id_vivienda=0;
		this.meses_contratados=0;
	}

	public Contrato_Hab_Vivienda(long id_contrato, long id_vivienda, int meses_contratados) {
		super();
		this.id_contrato = id_contrato;
		this.id_vivienda = id_vivienda;
		this.meses_contratados = meses_contratados;
	}

	public long getId_contrato() {
		return id_contrato;
	}

	public void setId_contrato(long id_contrato) {
		this.id_contrato = id_contrato;
	}

	public long getId_vivienda() {
		return id_vivienda;
	}

	public void setId_vivienda(long id_vivienda) {
		this.id_vivienda = id_vivienda;
	}

	public int getMeses_contratados() {
		return meses_contratados;
	}

	public void setMeses_contratados(int meses_contratados) {
		this.meses_contratados = meses_contratados;
	}

	@Override
	public String toString() {
		return "Contrato_Hab_Vivienda [id_contrato=" + id_contrato + ", id_vivienda=" + id_vivienda
				+ ", meses_contratados=" + meses_contratados + "]";
	}
	


}
