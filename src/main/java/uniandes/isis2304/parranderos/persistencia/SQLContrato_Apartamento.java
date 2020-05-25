package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Contrato_Apartamento;


/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CONTRATO_APARTAMENTO de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLContrato_Apartamento {

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
	public SQLContrato_Apartamento(PersistenciaParranderos pp) {
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
	public long adicionarContratoApartamento(PersistenceManager pm, long id_contrato, long id_apartamento, int meses_contratados) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContrato_Apartamento() + "(ID_CONTRATO,ID_APARTAMENTO,MESES_CONTRATADOS) VALUES (?, ?, ?)");
		q.setParameters(id_contrato, id_apartamento, meses_contratados);
		return (long) q.executeUnique();             
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar RESERVAS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la reserva
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContratoApartamentoPorId (PersistenceManager pm, long idReserva)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContrato_Apartamento() + " WHERE id = ?");
		q.setParameters(idReserva);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA RESERVA de la 
	 * base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El identificador de la reserva
	 * @return El objeto RESERVA que tiene el identificador dado
	 */
	public Contrato_Apartamento darContratoApartamentoPorId (PersistenceManager pm, long idReserva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato_Apartamento() + " WHERE id = ?");
		q.setResultClass(Contrato_Apartamento.class);
		q.setParameters(idReserva);
		return (Contrato_Apartamento) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS Contratos_Apartamentoes de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Contrato_Apartamento
	 */
	public List<Contrato_Apartamento> darContratosApartamento (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato_Apartamento());
		q.setResultClass(Contrato_Apartamento.class);
		return (List<Contrato_Apartamento>) q.executeList();
	}


}