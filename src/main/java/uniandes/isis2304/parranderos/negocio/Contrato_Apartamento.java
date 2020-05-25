package uniandes.isis2304.parranderos.negocio;

public class Contrato_Apartamento implements VOContrato_Apartamento {

	public long id_contrato;

	public long id_apartamento;

	public int meses_contratados;


	public Contrato_Apartamento() {

		this.id_contrato = 0;
		this.id_apartamento=0;
		this.meses_contratados=0;

	}

	public Contrato_Apartamento(long id_contrato, long id_apartamento, int meses_contratados) {
		super();
		this.id_contrato = id_contrato;
		this.id_apartamento = id_apartamento;
		this.meses_contratados = meses_contratados;
	}

	public long getId_contrato() {
		return id_contrato;
	}

	public void setId_contrato(long id_contrato) {
		this.id_contrato = id_contrato;
	}

	public long getId_apartamento() {
		return id_apartamento;
	}

	public void setId_apartamento(long id_apartamento) {
		this.id_apartamento = id_apartamento;
	}

	public int getMeses_contratados() {
		return meses_contratados;
	}

	public void setMeses_contratados(int meses_contratados) {
		this.meses_contratados = meses_contratados;
	}

	@Override
	public String toString() {
		return "Contrato_Apartamento [id_contrato=" + id_contrato + ", id_apartamento=" + id_apartamento
				+ ", meses_contratados=" + meses_contratados + "]";
	}

}
