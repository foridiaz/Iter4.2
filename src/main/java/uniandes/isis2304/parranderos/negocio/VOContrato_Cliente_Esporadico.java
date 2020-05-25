package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRATO_CLIENTE_ESPORADICO.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOContrato_Cliente_Esporadico {

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El idContrato
	 */
	public long getId_contrato();

	/**
	 * 
	 * @return La capacidad del alojamiento contratado
	 */
	public long getId_apartamento() ;
	
	/**
	 * 
	 * @return Costo del contrato
	 */
	public long getId_vivienda() ;
	
	/**
	 * 
	 * @return Costo del contrato
	 */
	public int getCantidad_noches() ;
	
	/**
	 * 
	 * @return Costo del contrato
	 */
	public int getCosto_base() ;
	
	/**
	 * 
	 * @return Costo del contrato
	 */
	public int getCosto_seguro() ;
	
	/**
	 * 
	 * @return Costo del contrato
	 */
	public int getNum_habitaciones() ;
	
	/**
	 * 
	 * @return Costo del contrato
	 */
	public String getUbicacion() ;
	
	
	/**
	 * @return Una cadena de caracteres con la información del contrato
	 */
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 
}
