package uniandes.isis2304.parranderos.persistencia;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;

import uniandes.isis2304.parranderos.negocio.Contrato;
import uniandes.isis2304.parranderos.negocio.Parranderos;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.ReservaColectiva;
import uniandes.isis2304.parranderos.negocio.UsosVinculo;

public class SQLReservaColectiva {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	private static Logger log = Logger.getLogger(Parranderos.class.getName());

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
	public SQLReservaColectiva(PersistenciaParranderos pp) {
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una RESERVA a la base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param id - Id de la reserva
	 * @param id_contrato - Id del contrato
	 * @param personas - Cantidad de persona para la reserva
	 * @param fecha_inicio - Fecha de inicio de la reserva
	 * @param fecha_fin - Fecha de fin de la reserva
	 * @param fecha_limite - Fecha de limite para entregar con descuento
	 * @param fecha_realizacion - Fecha de realizacion de la reserva
	 * @param tipo -  Tipo de alojamiento a reservar
	 * @param id_cliente -  Id de cliente 
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarReservaColectiva (PersistenceManager pm, long id, long id_contrato, int personas, String fecha_inicio, String fecha_fin, String fecha_limite, String fecha_realizacion, String tipo, long id_cliente) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReservaColectiva() + "(id, id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
        return (long) q.executeUnique();            
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar RESERVAS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la reserva
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarReservaColectivaPorId (PersistenceManager pm, long idReservaColectiva)
	{	
		Reserva eliminada=darReservaColectivaPorId(pm, idReservaColectiva);
		log.info ("Eliminando Reserva Colectiva: " + eliminada.toString());
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReservaColectiva () + " WHERE id = ?");
		q.setParameters(idReservaColectiva);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA RESERVA de la 
	 * base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El identificador de la reserva
	 * @return El objeto RESERVA que tiene el identificador dado
	 */
	public Reserva darReservaColectivaPorId (PersistenceManager pm, long idReservaColectiva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservaColectiva () + " WHERE id = ?");
		q.setResultClass(ReservaColectiva.class);
		q.setParameters(idReservaColectiva);
		return (Reserva) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS RESERVAS de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos RESERVA
	 */
	public List<ReservaColectiva> darReservasColectivas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservaColectiva ());
		q.setResultClass(ReservaColectiva.class);
		return (List<ReservaColectiva>) q.executeList();
	}


}


