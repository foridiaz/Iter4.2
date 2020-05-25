package uniandes.isis2304.parranderos.negocio;


/**
 * Interfaz para los métodos get de RESERVA.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOReserva {


	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * @return  El id de la reserva
	 */
	public long getId();
	
	/**
	 * @return El id del Contrato de la reserva
	 */
	public long getId_contrato();


	/**
	 * 
	 * @return Cantidad de personas que ocupan la reserva
	 */
	public int getPersonas() ;

	/**
	 * 
	 * @return Fecha de inicio de la reserva
	 */
	public String getFecha_inicio() ;

	/**
	 * 
	 * @return Fecha de fin de la reserva
	 */
	public String getFecha_fin();

	/**
	 * 
	 * @return Fecha límite para entregarla con descuento
	 */
	public String getFecha_limite();

	/**
	 * 
	 * @return Fecha de realizacion de la reserva
	 */
	public String getFecha_realizaciom() ;

	/**
	 * 
	 * @return Tipo de alojamiento para la reserva actual
	 */
	public String getTipo();

	/**
	 * @return El id del cliente
	 */
	public long getId_cliente();

	public String getTipo_reserva();
	/**
	 * @return Una cadena de caracteres con la información de la reserva
	 */
	@Override
	public String toString(); 


	@Override
	public boolean equals (Object tb); 
}
