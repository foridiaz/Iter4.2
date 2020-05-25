package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Contrato_Hab_Vivienda;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CONTRATO_Hab_Vivienda de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLContrato_Hab_Vivienda {
	
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
	public SQLContrato_Hab_Vivienda(PersistenciaParranderos pp) {
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
	public long adicionarContratoHabVivienda(PersistenceManager pm, long id_contrato, long id_vivienda, int meses_contratados) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContrato_Hab_Vivienda() + "(ID_CONTRATO,ID_VIVIENDA,MESES_CONTRATADOS) VALUES (?, ?, ?)");
        q.setParameters(id_contrato, id_vivienda, meses_contratados);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar RESERVAS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la reserva
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContratoHabViviendaPorId (PersistenceManager pm, long idContratoHabVivienda)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContrato_Hab_Vivienda() + " WHERE id = ?");
        q.setParameters(idContratoHabVivienda);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA RESERVA de la 
	 * base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El identificador de la reserva
	 * @return El objeto RESERVA que tiene el identificador dado
	 */
	public Contrato_Hab_Vivienda darContratoHabViviendaPorId (PersistenceManager pm, long idContratoHabVivienda) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato_Hab_Vivienda() + " WHERE id = ?");
		q.setResultClass(Contrato_Hab_Vivienda.class);
		q.setParameters(idContratoHabVivienda);
		return (Contrato_Hab_Vivienda) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS Contratos_Hab_Viviendaes de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Contrato_Hab_Vivienda
	 */
	public List<Contrato_Hab_Vivienda> darContratosHabVivienda (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato_Hab_Vivienda());
		q.setResultClass(Contrato_Hab_Vivienda.class);
		return (List<Contrato_Hab_Vivienda>) q.executeList();
	}
	
	
}