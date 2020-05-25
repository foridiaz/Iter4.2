package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRATO.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOIndice {
	
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El idContrato
	 */
	public long getId_oferta();

	/**
	 * 
	 * @return La capacidad del alojamiento contratado
	 */
	public int getCapacidad();
	
	/**
	 * 
	 * @return Ganancia del Operador
	 */
	public int getAlojadas();
	
	/**
	 * 
	 * @return Múmero de veces que se ha utilizado el servicio
	 */
	public double getIndice();
	
	/**
	 * @return Una cadena de caracteres con la información del contrato
	 */
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 
}