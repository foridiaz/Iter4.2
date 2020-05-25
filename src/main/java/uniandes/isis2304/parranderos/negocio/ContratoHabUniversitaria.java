package uniandes.isis2304.parranderos.negocio;

public class ContratoHabUniversitaria implements VOContratoHabUniversitaria {

	public long id_contrato;
	
	public long id_vivienda;
	
	public int meses_contratados;
	
	public String individual;
	
	public int ubicacion;
	
	public String gimnasio;
	
	public String restaurante;
	
	public String sala_esparcimiento;
	
	public String sala_estudio;
	
	public ContratoHabUniversitaria() {
		this.id_contrato=0;
		this.id_vivienda=0;
		this.meses_contratados=0;
		this.individual="";
		this.ubicacion=0;
		this.gimnasio="";
		this.restaurante="";
		this.sala_esparcimiento="";
		this.sala_estudio="";	
	}

	public ContratoHabUniversitaria(long id_contrato, long id_vivienda, int meses_contratados, String individual,
			int ubicacion, String gimnasio, String restaurante, String sala_esparcimiento, String sala_estudio) {
		super();
		this.id_contrato = id_contrato;
		this.id_vivienda = id_vivienda;
		this.meses_contratados = meses_contratados;
		this.individual = individual;
		this.ubicacion = ubicacion;
		this.gimnasio = gimnasio;
		this.restaurante = restaurante;
		this.sala_esparcimiento = sala_esparcimiento;
		this.sala_estudio = sala_estudio;
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

	public String getIndividual() {
		return individual;
	}

	public void setIndividual(String individual) {
		this.individual = individual;
	}

	public int getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(int ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getGimnasio() {
		return gimnasio;
	}

	public void setGimnasio(String gimnasio) {
		this.gimnasio = gimnasio;
	}

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}

	public String getSala_esparcimiento() {
		return sala_esparcimiento;
	}

	public void setSala_esparcimiento(String sala_esparcimiento) {
		this.sala_esparcimiento = sala_esparcimiento;
	}

	public String getSala_estudio() {
		return sala_estudio;
	}

	public void setSala_estudio(String sala_estudio) {
		this.sala_estudio = sala_estudio;
	}

	@Override
	public String toString() {
		return "ContratoHabUniversitaria [id_contrato=" + id_contrato + ", id_vivienda=" + id_vivienda
				+ ", meses_contratados=" + meses_contratados + ", individual=" + individual + ", ubicacion=" + ubicacion
				+ ", gimnasio=" + gimnasio + ", restaurante=" + restaurante + ", sala_esparcimiento="
				+ sala_esparcimiento + ", sala_estudio=" + sala_estudio + "]";
	}
	
	
	
	
}
