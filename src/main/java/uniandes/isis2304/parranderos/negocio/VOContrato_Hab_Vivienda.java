package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRATO_HAB_VIVIENDA.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOContrato_Hab_Vivienda {

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El idContrato
	 */
	public long getId_contrato();

	/**
	 * 
	 * @return El id de la vivienda
	 */
	public long getId_vivienda() ;

	/**
	 * 
	 * @return Meses contratados de la habitación.
	 */
	public int getMeses_contratados() ;

	/**
	 * @return Una cadena de caracteres con la información del contrato
	 */
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 
}
