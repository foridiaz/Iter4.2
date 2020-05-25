package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRATOHABHOSTAL.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOContratoHabHostal {

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El idContrato
	 */
	public long getId_contrato();

	/**
	 * 
	 * @return El id del apartamento
	 */
	public long getId_hostal() ;

	/**
	 * @return Una cadena de caracteres con la información del contrato
	 */
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 

}
