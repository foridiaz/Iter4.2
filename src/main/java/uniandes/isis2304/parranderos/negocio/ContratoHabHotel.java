package uniandes.isis2304.parranderos.negocio;

public class ContratoHabHotel implements VOContratoHabHotel {
	
	public long id_contrato;
	
	public long id_hotel;
	
	public int categoria;
	
	public int tamanio;
	
	public String tipo_habitacion;
	
	public int ubicacion;
	
	public ContratoHabHotel() {
		this.id_contrato=0;
		this.id_hotel=0;
		this.categoria=0;
		this.tamanio=0;
		this.tipo_habitacion="";
		this.ubicacion=0;
	}

	public ContratoHabHotel(long id_contrato, long id_hotel, int categoria, int tamanio, String tipo_habitacion,
			int ubicacion) {
		super();
		this.id_contrato = id_contrato;
		this.id_hotel = id_hotel;
		this.categoria = categoria;
		this.tamanio = tamanio;
		this.tipo_habitacion = tipo_habitacion;
		this.ubicacion = ubicacion;
	}

	public long getId_contrato() {
		return id_contrato;
	}

	public void setId_contrato(long id_contrato) {
		this.id_contrato = id_contrato;
	}

	public long getId_hotel() {
		return id_hotel;
	}

	public void setId_hotel(long id_hotel) {
		this.id_hotel = id_hotel;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	public String getTipo_habitacion() {
		return tipo_habitacion;
	}

	public void setTipo_habitacion(String tipo_habitacion) {
		this.tipo_habitacion = tipo_habitacion;
	}

	public int getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(int ubicacion) {
		this.ubicacion = ubicacion;
	}

	@Override
	public String toString() {
		return "ContratoHabHotel [id_contrato=" + id_contrato + ", id_hotel=" + id_hotel + ", categoria=" + categoria
				+ ", tamanio=" + tamanio + ", tipo_habitacion=" + tipo_habitacion + ", ubicacion=" + ubicacion + "]";
	}

	
}
