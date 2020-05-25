package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Ganancia;
import uniandes.isis2304.parranderos.negocio.Operador;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto OPERADOR de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLOperador{
	
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
	public SQLOperador(PersistenciaParranderos pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un OPERADOR a la base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del operador
	 * @param nombre - Nombre del operador
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarOperador (PersistenceManager pm, long id, String nombre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOperador() + "(id, nombre) values (?, ?)");
        q.setParameters(id, nombre);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar Operador de la base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la Operador
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarOperadorPorId (PersistenceManager pm, long idOperador)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOperador () + " WHERE id = ?");
        q.setParameters(idOperador);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN OPERADOR de la 
	 * base de datos de Alohandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El identificador de la Operador
	 * @return El objeto Operador que tiene el identificador dado
	 */
	public Operador darOperadorPorId (PersistenceManager pm, long idOperador) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOperador () + " WHERE id = ?");
		q.setResultClass(Operador.class);
		q.setParameters(idOperador);
		return (Operador) q.executeUnique();
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS Operadores de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Operador
	 */
	public List<Operador> darOperadores (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOperador());
		q.setResultClass(Operador.class);
		return (List<Operador>) q.executeList();
	}
	public List<Ganancia> darGanancias(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT ID,NOMBRE,GANANCIA_TOTAL AS GANANCIA, VECES\n" + 
				"    FROM OPERADOR JOIN (SELECT ID_OPERADOR, SUM(GANANCIA) AS GANANCIA_TOTAL, SUM(VECES) AS VECES\n" + 
				"    FROM (SELECT contrato.id AS CONTRATO_ID, contratohabhotel.id_hotel AS ID_OPERADOR\n" + 
				"    FROM CONTRATO JOIN CONTRATOHABHOTEL\n" + 
				"                    ON CONTRATO.ID=CONTRATOHABHOTEL.ID_CONTRATO\n" + 
				"          UNION                \n" + 
				"    SELECT contrato.id AS CONTRATO_ID, contratohabhostal.id_hostal AS ID_OPERADOR\n" + 
				"    FROM CONTRATO JOIN CONTRATOHABHOSTAL\n" + 
				"                    ON CONTRATO.ID=CONTRATOHABHOSTAL.ID_CONTRATO\n" + 
				"            UNION\n" + 
				"    SELECT CONTRATO.ID AS CONTRATO_ID, contratohabuniversitaria.id_vivienda AS ID_OPERADOR\n" + 
				"    FROM CONTRATO JOIN CONTRATOHABUNIVERSITARIA\n" + 
				"                  ON CONTRATO.ID=CONTRATOHABUNIVERSITARIA.ID_CONTRATO\n" + 
				"            UNION      \n" + 
				"    SELECT CONTRATO_ID, VIVIENDA_FAMILIAR.ID_PERSONA_NATURAL AS ID_OPERADOR\n" + 
				"    FROM VIVIENDA_FAMILIAR JOIN (SELECT CONTRATO.ID AS CONTRATO_ID , CONTRATO_HAB_VIVIENDA.ID_VIVIENDA AS ID_VIVIENDA\n" + 
				"                                FROM CONTRATO JOIN CONTRATO_HAB_VIVIENDA\n" + 
				"                                              ON CONTRATO.ID=CONTRATO_HAB_VIVIENDA.ID_CONTRATO)\n" + 
				"                          ON VIVIENDA_FAMILIAR.ID=ID_VIVIENDA\n" + 
				"            UNION\n" + 
				"    SELECT CONTRATO_ID, APARTAMENTO.ID_PERSONA_NATURAL AS ID_OPERADOR\n" + 
				"    FROM APARTAMENTO JOIN (SELECT CONTRATO.ID AS CONTRATO_ID , CONTRATO_APARTAMENTO.ID_APARTAMENTO AS ID_APARTAMENTO\n" + 
				"                           FROM CONTRATO JOIN CONTRATO_APARTAMENTO\n" + 
				"                                         ON CONTRATO.ID=CONTRATO_APARTAMENTO.ID_CONTRATO)\n" + 
				"                          ON APARTAMENTO.ID=ID_APARTAMENTO\n" + 
				"            UNION\n" + 
				"    SELECT CONTRATO_ID, VIVIENDA_FAMILIAR.ID_PERSONA_NATURAL AS ID_OPERADOR\n" + 
				"    FROM VIVIENDA_FAMILIAR JOIN (SELECT CONTRATO.ID AS CONTRATO_ID, CONTRATO_CLIENTE_ESPORADICO.ID_VIVIENDA AS ID_VIVIENDA\n" + 
				"                                FROM CONTRATO JOIN CONTRATO_CLIENTE_ESPORADICO\n" + 
				"                                              ON CONTRATO.ID=CONTRATO_CLIENTE_ESPORADICO.ID_CONTRATO)\n" + 
				"                          ON VIVIENDA_FAMILIAR.ID=ID_VIVIENDA) UNIONES LEFT JOIN (\n" + 
				"    SELECT ID_CONT, SUM(COSTO) AS GANANCIA, COUNT(ID_CONT) AS VECES\n" + 
				"    FROM ( SELECT RES.ID AS ID_RES,CTS.ID AS ID_CONT,CTS.COSTO AS COSTO,RES.FECHA_REALIZACIOM AS FECHA_REALIZACIO\n" + 
				"           FROM CONTRATO CTS JOIN (SELECT * \n" + 
				"                       FROM RESERVA\n" + 
				"                       WHERE FECHA_REALIZACIOM LIKE '%2020%') RES\n" + 
				"                     ON RES.ID_CONTRATO=CTS.ID) \n" + 
				"    GROUP BY ID_CONT) GANANCIAS\n" + 
				"    ON GANANCIAS.ID_CONT=uniones.contrato_id\n" + 
				"    GROUP BY ID_OPERADOR)\n" + 
				"                  ON OPERADOR.ID=ID_OPERADOR\n" + 
				"                  WHERE GANANCIA_TOTAL>0\n" + 
				"                  ORDER BY GANANCIA_TOTAL ASC");
		q.setResultClass(Ganancia.class);
		return (List<Ganancia>)q.executeList();
	}
	

	
}
