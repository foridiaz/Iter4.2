package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Ganancia;
import uniandes.isis2304.parranderos.negocio.Operador;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto OPERADOR de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLCliente{

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
	public SQLCliente(PersistenciaParranderos pp) {
		this.pp = pp;
	}
	public List<Cliente> darOperadores (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOperador());
		q.setResultClass(Operador.class);
		return (List<Cliente>) q.executeList();
	}
	public List<Cliente> consultarConsumoOferta1(PersistenceManager pm,long IdOf,String fecha_inicio, String fecha_fin){

		Query q=pm.newQuery(SQL,"SELECT CLI.ID,CLI.NOMBRE,CLI.USUARIO,CLI.VINCULO FROM (SELECT * FROM CLIENTE) CLI JOIN (SELECT * FROM RESERVA WHERE ID_CONTRATO="+IdOf+" AND \r\n" + 
				"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')<=TO_DATE('"+fecha_fin+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
				"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')>=TO_DATE('"+fecha_inicio+"','DD/MM/YYYY HH24;MI:SS') )A ON CLI.ID=A.ID_CLIENTE\r\n" + 
				"                            ORDER BY CLI.ID");
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();

	}


}