package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRATO.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOContrato {
	
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El idContrato
	 */
	public long getId();

	/**
	 * 
	 * @return La capacidad del alojamiento contratado
	 */
	public int getCapacidad() ;
	
	/**
	 * 
	 * @return Costo del contrato
	 */
	public int getCosto() ;
	
	/**
	 * @return Una cadena de caracteres con la información del contrato
	 */
	public String getHabilitada();
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 
}