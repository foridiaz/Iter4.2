package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto OPERADOR del negocio de AlohAndes
 *
 */
public class Cliente implements VOCliente {

	/**
	 * Id del operador
	 */
	private long id;

	/**
	 * Nombre del operador (Vendría siendo el tipo: Hotel, Hostal, ViviendaUniversitaria o PersonaNatural)
	 */
	private String nombre;

	private String usuario; 
	
	private String vinculo;
	/**
	 * Constructor sin valores
	 */
	public Cliente() {

		this.id=0;
		this.nombre="";
		this.usuario="";
		this.vinculo="";
	}

	/**
	 * Constructor con valores
	 * @param id -  Id del operador
	 * @param nombre - Nombre o tipo del operador
	 */
	public Cliente(long id, String nombre, String pUsuario,String pVinculo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.usuario=pUsuario; 
		this.vinculo=pVinculo;
		
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
	
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String pUsuario) {
		this.usuario=pUsuario;
	}
	public String getVinculo() {
		return vinculo;
	}
	public void setVinculo(String pVinculo) {
		this.vinculo=pVinculo;	
	}
}
