package uniandes.isis2304.parranderos.negocio;

public class ContratoHabHostal implements VOContratoHabHostal{

	public long id_contrato;
	
	public long id_hostal;
	
	public ContratoHabHostal() {
		this.id_contrato=0;
		this.id_hostal=0;
	}

	public ContratoHabHostal(long id_contrato, long id_hostal) {
		super();
		this.id_contrato = id_contrato;
		this.id_hostal = id_hostal;
	}

	public long getId_contrato() {
		return id_contrato;
	}

	public void setId_contrato(long id_contrato) {
		this.id_contrato = id_contrato;
	}

	public long getId_hostal() {
		return id_hostal;
	}

	public void setId_hostal(long id_hostal) {
		this.id_hostal = id_hostal;
	}

	@Override
	public String toString() {
		return "ContratoHabHostal [id_contrato=" + id_contrato + ", id_hostal=" + id_hostal + "]";
	}
	
}
