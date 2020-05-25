package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRATO_APARTAMENTO.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOUsosVinculo {
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El idContrato
	 */
	public String getVinculo();

	/**
	 * 
	 * @return El id del apartamento
	 */
	public int getVeces(); 
	
	/**
	 * @return Una cadena de caracteres con la información del contrato
	 */
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 
}
