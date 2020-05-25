package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Contrato_Cliente_Esporadico;
import uniandes.isis2304.parranderos.negocio.Reserva;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CONTRATO_CLIENTE_ESPORADICO de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLContrato_Cliente_Esporadico {
	
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
	public SQLContrato_Cliente_Esporadico(PersistenciaParranderos pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una RESERVA a la base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param id_apartamento - Id de la reserva
	 * @param id_apartamento - Id del contrato
	 * @param id_vivienda - Cantidad de persona para la reserva
	 * @param cantidad_noches - Fecha de inicio de la reserva
	 * @param costo_base - Fecha de fin de la reserva
	 * @param costo_seguro - Fecha de limite para entregar con descuento
	 * @param num_habitaciones - Fecha de realizacion de la reserva
	 * @param tipo -  Tipo de alojamiento a reservar
	 * @param ubicacion -  Id de cliente 
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarContrato_Cliente_Esporadico(PersistenceManager pm, long id_contrato, long id_apartamento, long id_vivienda, int cantidad_noches, int costo_base, int costo_seguro, int num_habitaciones, String ubicacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContrato_Cliente_Esporadico() + "(ID_CONTRATO,ID_APARTAMENTO,ID_VIVIENDA,CANTIDAD_NOCHES,COSTO_BASE,COSTO_SEGURO,NUM_HABITACIONES,UBICACION) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id_contrato, id_apartamento, id_vivienda, cantidad_noches, costo_base, costo_seguro, num_habitaciones, ubicacion);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar RESERVAS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la reserva
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContrato_Cliente_EsporadicoPorId (PersistenceManager pm, long idContratoClienteEsporadico)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContrato_Cliente_Esporadico() + " WHERE id = ?");
        q.setParameters(idContratoClienteEsporadico);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA RESERVA de la 
	 * base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idContratoClienteEsporadico - El identificador de la reserva
	 * @return El objeto RESERVA que tiene el identificador dado
	 */
	public Contrato_Cliente_Esporadico darContrato_Cliente_EsporadicoPorId (PersistenceManager pm, long idContratoClienteEsporadico) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato_Cliente_Esporadico() + " WHERE id = ?");
		q.setResultClass(Contrato_Cliente_Esporadico.class);
		q.setParameters(idContratoClienteEsporadico);
		return (Contrato_Cliente_Esporadico) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS Contrato_Cliente_Esporadicoes de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Contrato_Cliente_Esporadico
	 */
	public List<Contrato_Cliente_Esporadico> darContratosClienteEsporadico (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato_Cliente_Esporadico());
		q.setResultClass(Contrato_Cliente_Esporadico.class);
		return (List<Contrato_Cliente_Esporadico>) q.executeList();
	}
	
	
}