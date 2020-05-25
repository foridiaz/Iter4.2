package uniandes.isis2304.parranderos.negocio;

public class Contrato_Cliente_Esporadico implements VOContrato_Cliente_Esporadico {

	public long id_contrato;
	
	public long id_apartamento;
	
	public long id_vivienda;
	
	public int cantidad_noches;
	
	public int costo_base;
	
	public int costo_seguro;
	
	public int num_habitaciones;
	
	public String ubicacion;
	
	public Contrato_Cliente_Esporadico() {
		this.id_contrato=0;
		this.id_apartamento=0;
		this.id_vivienda=0;
		this.cantidad_noches=0;
		this.costo_base=0;
		this.costo_seguro=0;
		this.num_habitaciones=0;
		this.ubicacion="";
	}

	public Contrato_Cliente_Esporadico(long id_contrato, long id_apartamento, long id_vivienda, int cantidad_noches,
			int costo_base, int costo_seguro, int num_habitaciones, String ubicacion) {
		super();
		this.id_contrato = id_contrato;
		this.id_apartamento = id_apartamento;
		this.id_vivienda = id_vivienda;
		this.cantidad_noches = cantidad_noches;
		this.costo_base = costo_base;
		this.costo_seguro = costo_seguro;
		this.num_habitaciones = num_habitaciones;
		this.ubicacion = ubicacion;
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

	public long getId_vivienda() {
		return id_vivienda;
	}

	public void setId_vivienda(long id_vivienda) {
		this.id_vivienda = id_vivienda;
	}

	public int getCantidad_noches() {
		return cantidad_noches;
	}

	public void setCantidad_noches(int cantidad_noches) {
		this.cantidad_noches = cantidad_noches;
	}

	public int getCosto_base() {
		return costo_base;
	}

	public void setCosto_base(int costo_base) {
		this.costo_base = costo_base;
	}

	public int getCosto_seguro() {
		return costo_seguro;
	}

	public void setCosto_seguro(int costo_seguro) {
		this.costo_seguro = costo_seguro;
	}

	public int getNum_habitaciones() {
		return num_habitaciones;
	}

	public void setNum_habitaciones(int num_habitaciones) {
		this.num_habitaciones = num_habitaciones;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	@Override
	public String toString() {
		return "Contrato_Cliente_Esporadico [id_contrato=" + id_contrato + ", id_apartamento=" + id_apartamento
				+ ", id_vivienda=" + id_vivienda + ", cantidad_noches=" + cantidad_noches + ", costo_base=" + costo_base
				+ ", costo_seguro=" + costo_seguro + ", num_habitaciones=" + num_habitaciones + ", ubicacion="
				+ ubicacion + "]";
	}
	
	

	
	

}
