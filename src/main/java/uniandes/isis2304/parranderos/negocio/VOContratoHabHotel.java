package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRATOHABHOTEL.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOContratoHabHotel {

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El idContrato
	 */
	public long getId_contrato();

	/**
	 * 
	 * @return El id del hotel
	 */
	public long getId_hotel() ;

	/**
	 * 
	 * @return Categoria de la habitación.
	 */
	public int getCategoria();
	
	/**
	 * 
	 * @return Tamaño de la habitación.
	 */
	public int getTamanio();
	
	/**
	 * 
	 * @return Tipo de la habitación.
	 */
	public String getTipo_habitacion();
	
	/**
	 * 
	 * @return Ubicacion de la habitación.
	 */
	public int getUbicacion();

	/**
	 * @return Una cadena de caracteres con la información del contrato
	 */
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 
	
}
