package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Contrato;
import uniandes.isis2304.parranderos.negocio.Ganancia;
import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.Reserva;

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

	public List<Cliente> consultarConsumoOferta1(PersistenceManager pm,long IdOf,String fecha_inicio, String fecha_fin){

		Query q=pm.newQuery(SQL,"SELECT CLI.ID,CLI.NOMBRE,CLI.USUARIO,CLI.VINCULO FROM (SELECT * FROM CLIENTE) CLI JOIN (SELECT * FROM RESERVA WHERE ID_CONTRATO="+IdOf+" AND \r\n" + 
				"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')<=TO_DATE('"+fecha_fin+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
				"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')>=TO_DATE('"+fecha_inicio+"','DD/MM/YYYY HH24;MI:SS') )A ON CLI.ID=A.ID_CLIENTE\r\n" + 
				"                            ORDER BY CLI.ID");
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();

	}
	public List<Contrato> consultarConsumoCliente1(PersistenceManager pm,long IdCli,String fecha_inicio, String fecha_fin){
		Query q=pm.newQuery(SQL,"SELECT CT.* FROM CONTRATO CT JOIN (SELECT * FROM (SELECT * FROM CLIENTE) CLI JOIN (SELECT * FROM RESERVA WHERE ID_CLIENTE="+IdCli+" AND \r\n" + 
				"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')<=TO_DATE('"+fecha_fin+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
				"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')>=TO_DATE('"+fecha_inicio+"','DD/MM/YYYY HH24;MI:SS') )A ON CLI.ID=A.ID_CLIENTE\r\n" + 
				"                            ORDER BY CLI.ID) X\r\n" + 
				"                            ON CT.ID=X.ID_CONTRATO");
		q.setResultClass(Contrato.class);
		return (List<Contrato>) q.executeList();
	}
	public List<Cliente> darClientes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM CLIENTE");
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}
	
	public List<Cliente> consultarConsumoOferta1(PersistenceManager pm,String fecha_inicio,String fecha_fin,String alojamientos){
		List<Cliente> lista=null;
		if (alojamientos.equals("Hotel")) {
			Query q=pm.newQuery(SQL,"SELECT CLI.* FROM (SELECT * FROM CLIENTE) CLI JOIN (SELECT * FROM RESERVA WHERE \r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')<=TO_DATE('"+fecha_fin+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')>=TO_DATE('"+fecha_inicio+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TIPO='HOTEL')A ON CLI.ID=A.ID_CLIENTE\r\n" + 
					"                            ORDER BY CLI.ID"); 
			q.setResultClass(Cliente.class);
			lista= (List<Cliente>) q.executeList(); 
		}
		else if (alojamientos.equals("Hostal")) {
			Query q=pm.newQuery(SQL,"SELECT CLI.* FROM (SELECT * FROM CLIENTE) CLI JOIN (SELECT * FROM RESERVA WHERE \r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')<=TO_DATE('"+fecha_fin+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')>=TO_DATE('"+fecha_inicio+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TIPO='HOSTAL')A ON CLI.ID=A.ID_CLIENTE\r\n" + 
					"                            ORDER BY CLI.ID"); 
			q.setResultClass(Cliente.class);
			lista= (List<Cliente>) q.executeList();
		}
		else if (alojamientos.equals("Hab Vivienda")) {
			Query q=pm.newQuery(SQL,"SELECT CLI.* FROM (SELECT * FROM CLIENTE) CLI JOIN (SELECT * FROM RESERVA WHERE \r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')<=TO_DATE('"+fecha_fin+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')>=TO_DATE('"+fecha_inicio+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TIPO='VIVIENDA_FAMILIAR')A ON CLI.ID=A.ID_CLIENTE\r\n" + 
					"                            ORDER BY CLI.ID"); 
			q.setResultClass(Cliente.class);
			lista= (List<Cliente>) q.executeList();
		}
		else if (alojamientos.equals("Apartamento")) {
			Query q=pm.newQuery(SQL,"SELECT CLI.* FROM (SELECT * FROM CLIENTE) CLI JOIN (SELECT * FROM RESERVA WHERE \r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')<=TO_DATE('"+fecha_fin+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')>=TO_DATE('"+fecha_inicio+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TIPO='APARTAMENTO')A ON CLI.ID=A.ID_CLIENTE\r\n" + 
					"                            ORDER BY CLI.ID"); 
			q.setResultClass(Cliente.class);
			lista= (List<Cliente>) q.executeList();
		}
		else if (alojamientos.equals("Cliente esporádico")) {
			Query q=pm.newQuery(SQL,"SELECT CLI.* FROM (SELECT * FROM CLIENTE) CLI JOIN (SELECT * FROM RESERVA WHERE \r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')<=TO_DATE('"+fecha_fin+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')>=TO_DATE('"+fecha_inicio+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TIPO='CLIENTE_ESPORADICO')A ON CLI.ID=A.ID_CLIENTE\r\n" + 
					"                            ORDER BY CLI.ID"); 
			q.setResultClass(Cliente.class);
			lista= (List<Cliente>) q.executeList();
		}
		else if (alojamientos.equals("Vivienda Universitaria")) {
			Query q=pm.newQuery(SQL,"SELECT CLI.* FROM (SELECT * FROM CLIENTE) CLI JOIN (SELECT * FROM RESERVA WHERE \r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')<=TO_DATE('"+fecha_fin+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TO_DATE(FECHA_INICIO,'DD/MM/YYYY HH24;MI:SS')>=TO_DATE('"+fecha_inicio+"','DD/MM/YYYY HH24;MI:SS') AND\r\n" + 
					"                            TIPO='VIVIENDA_UNIVERSITARIA')A ON CLI.ID=A.ID_CLIENTE\r\n" + 
					"                            ORDER BY CLI.ID"); 
			q.setResultClass(Cliente.class);
			lista= (List<Cliente>) q.executeList();
		}
		return lista;
	}

}