package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ContratoHabHotel;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CONTRATOHABHOTEL de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLContratoHabHotel {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaParranderos.SQL;
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaParranderos pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLContratoHabHotel(PersistenciaParranderos pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una ContratoHabHotel a la base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param id - Id de la ContratoHabHotel
	 * @param id_contrato - Id del contrato
	 * @param personas - Cantidad de persona para la ContratoHabHotel
	 * @param fecha_inicio - Fecha de inicio de la ContratoHabHotel
	 * @param fecha_fin - Fecha de fin de la ContratoHabHotel
	 * @param fecha_limite - Fecha de limite para entregar con descuento
	 * @param fecha_realizacion - Fecha de realizacion de la ContratoHabHotel
	 * @param tipo -  Tipo de alojamiento a ContratoHabHotelr
	 * @param id_cliente -  Id de cliente 
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarContratoHabHotel(PersistenceManager pm, long id_contrato, long id_hotel, int categoria, int tamanio, String tipo_habitacion, int ubicacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContratoHabHotel() + "(ID_CONTRATO,ID_HOTEL,CATEGORIA, TAMANIO, TIPO_HABITACION, UBICACION) VALUES (?, ?, ?, ?, ?)");
        q.setParameters(id_contrato, id_hotel, categoria, tamanio, tipo_habitacion, ubicacion);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un ContratoHabHotel de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador del ContratoHabHotel
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContratoHabHotelPorId (PersistenceManager pm, long idContratoHabHotel)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContratoHabHotel () + " WHERE id = ?");
        q.setParameters(idContratoHabHotel);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA ContratoHabHotel de la 
	 * base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idContratoHabHotel - El identificador del ContratoHabHotel
	 * @return El objeto ContratoHabHotel que tiene el identificador dado
	 */
	public ContratoHabHotel darContratoHabHotelPorId (PersistenceManager pm, long idContratoHabHotel) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContratoHabHotel () + " WHERE id = ?");
		q.setResultClass(ContratoHabHotel.class);
		q.setParameters(idContratoHabHotel);
		return (ContratoHabHotel) q.executeUnique();
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ContratosHabHotel de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ContratoHabHotel
	 */
	public List<ContratoHabHotel> darContratosHabHotel (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContratoHabHotel());
		q.setResultClass(ContratoHabHotel.class);
		return (List<ContratoHabHotel>) q.executeList();
	}
	
}