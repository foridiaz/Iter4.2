package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRATOHABUNIVERSITARIA.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOContratoHabUniversitaria {


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
	 * @return Meses contratados del apartamento.
	 */
	public int getMeses_contratados() ;
	
	
	public String getIndividual();
	
	public int getUbicacion();
	
	public String getGimnasio();
	
	public String getRestaurante();
	
	public String getSala_esparcimiento();
	
	public String getSala_estudio();
	

	/**
	 * @return Una cadena de caracteres con la información del contrato
	 */
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 
}
