package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ContratoHabUniversitaria;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CONTRATOHABUNIVERSITARIA de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLContratoHabUniversitaria {

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
	public SQLContratoHabUniversitaria(PersistenciaParranderos pp) {
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un ContratoHabUniversitaria a la base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param id_contrato - Id del contrato
	 * @param id_vivienda - Id de la vivienda
	 * @param individual - Si es individual o no la hab
	 * @param ubicacion - Numero de la hab
	 * @param gimnasio - Tiene gimnsaio o no
	 * @param restaurante - Tiene restaurante o no
	 * @param sala_esparcimiento - Tiene sala de esparcimiento o no
	 * @param sala_estudio - Tiene salas de estudio o no
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarContratoHabUniversitaria(PersistenceManager pm, long id_contrato, long id_vivienda, String individual, int ubicacion, String gimnasio, String restaurante, String sala_esparcimiento, String sala_estudio) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContratoHabUniversitaria() + "(ID_CONTRATO,ID_VIVIENDA,UBIACCION,GIMNSAIO,RESTAURANTE,SALA_ESPARCIMIENTO,SALA_ESTUDIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(id_contrato, id_vivienda, individual, ubicacion, gimnasio, restaurante, sala_esparcimiento, sala_estudio);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un ContratoHabUniversitaria de la base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idContratoHabUniversitaria - El identificador del ContratoHabUniversitaria
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContratoHabUniversitariaPorId (PersistenceManager pm, long idContratoHabUniversitaria)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContratoHabUniversitaria () + " WHERE id = ?");
		q.setParameters(idContratoHabUniversitaria);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN ContratoHabUniversitaria de la 
	 * base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idContratoHabUniversitaria - El identificador de la ContratoHabUniversitaria
	 * @return El objeto ContratoHabUniversitaria que tiene el identificador dado
	 */
	public ContratoHabUniversitaria darContratoHabUniversitariaPorId (PersistenceManager pm, long idContratoHabUniversitaria) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContratoHabUniversitaria () + " WHERE id = ?");
		q.setResultClass(ContratoHabUniversitaria.class);
		q.setParameters(idContratoHabUniversitaria);
		return (ContratoHabUniversitaria) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ContratosHabUniversitariaes de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ContratoHabUniversitaria
	 */
	public List<ContratoHabUniversitaria> darContratosHabUniversitaria (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContratoHabUniversitaria());
		q.setResultClass(ContratoHabUniversitaria.class);
		return (List<ContratoHabUniversitaria>) q.executeList();
	}

}