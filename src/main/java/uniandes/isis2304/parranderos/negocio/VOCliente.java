package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de Operador
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOCliente
{
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El idBebedor
	 */
	public long getId();

	/**
	 * @return El idBebida
	 */
	public String getNombre();
	
	public String getUsuario();
	
	public String getVinculo();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
	
	@Override
	public boolean equals (Object tb); 
}
