/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
{
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
	public SQLUtil (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqParranderos () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos (PersistenceManager pm)
	{
        Query qContrato = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContrato());          
        Query qContratoApto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContrato_Apartamento());          
        Query qContratoEsp = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContrato_Cliente_Esporadico());
        Query qContratoViv = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContrato_Hab_Vivienda());
        Query qContratoHabHostal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContratoHabHostal());
        Query qContratoHabHotel = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContratoHabHotel());
        Query qContratoHabUniv = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContratoHabUniversitaria());
        Query qOperador = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOperador());
        Query qReserva = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva());



        long contratosEliminados = (long) qContrato.executeUnique ();
        long contratosAptoEliminados = (long) qContratoApto.executeUnique ();
        long contratosEspEliminados = (long) qContratoEsp.executeUnique ();
        long contratosVivEliminadas = (long) qContratoViv.executeUnique ();
        long contratosHabHostalEliminados = (long) qContratoHabHostal.executeUnique ();
        long contratosHabHotelEliminados = (long) qContratoHabHotel.executeUnique ();
        long contratosHabUnivEliminados = (long) qContratoHabUniv.executeUnique ();
        long operadoresEliminados = (long) qOperador.executeUnique ();
        long reservasEliminados = (long) qReserva.executeUnique ();


        return new long[] {contratosEliminados, contratosAptoEliminados, contratosEspEliminados, contratosVivEliminadas,
        		contratosHabHostalEliminados, contratosHabHotelEliminados, contratosHabUnivEliminados, operadoresEliminados, reservasEliminados};
	}
}
