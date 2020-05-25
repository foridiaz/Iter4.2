package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto OPERADOR del negocio de AlohAndes
 *
 */
public class Operador implements VOOperador {

	/**
	 * Id del operador
	 */
	private long id;

	/**
	 * Nombre del operador (Vendría siendo el tipo: Hotel, Hostal, ViviendaUniversitaria o PersonaNatural)
	 */
	private String nombre;


	/**
	 * Constructor sin valores
	 */
	public Operador() {

		this.id=0;
		this.nombre="";
	}

	/**
	 * Constructor con valores
	 * @param id -  Id del operador
	 * @param nombre - Nombre o tipo del operador
	 */
	public Operador(long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}


	/**
	 * @return El id del operador
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return El id del operador
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Nombre del operador
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return Nombre del operador
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
