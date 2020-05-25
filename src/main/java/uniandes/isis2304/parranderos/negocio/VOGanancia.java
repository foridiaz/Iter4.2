package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRATO.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOGanancia {
	
	
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
	public String getNombre() ;
	
	/**
	 * 
	 * @return Ganancia del Operador
	 */
	public double getGanancia();
	
	/**
	 * 
	 * @return Múmero de veces que se ha utilizado el servicio
	 */
	public int getVeces() ;
	
	/**
	 * @return Una cadena de caracteres con la información del contrato
	 */
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 
}