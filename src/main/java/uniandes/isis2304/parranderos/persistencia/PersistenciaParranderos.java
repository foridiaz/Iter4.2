/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Alohandes
 * 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;


import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Contrato;
import uniandes.isis2304.parranderos.negocio.ContratoHabHostal;
import uniandes.isis2304.parranderos.negocio.ContratoHabHotel;
import uniandes.isis2304.parranderos.negocio.ContratoHabUniversitaria;
import uniandes.isis2304.parranderos.negocio.Contrato_Apartamento;
import uniandes.isis2304.parranderos.negocio.Contrato_Cliente_Esporadico;
import uniandes.isis2304.parranderos.negocio.Contrato_Hab_Vivienda;
import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.UsosVinculo;
import uniandes.isis2304.parranderos.negocio.VOUsosVinculo;
import uniandes.isis2304.parranderos.negocio.Ganancia;
import uniandes.isis2304.parranderos.negocio.Id;
import uniandes.isis2304.parranderos.negocio.Indice;
/**
 * Clase para el manejador de persistencia del proyecto Alohandes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLContrato_Apartamento, SQLContrato_Cliente_Esporadico, SQLContrato_Hab_Vivienda, 
 * SQLContrato, SQLContratoHabHostal, SQLContratoHabHotel, SQLContratoHabUniversitaria, SQLOperador y SQLReserva que son 
 * las que realizan el acceso a la base de datos
 * 
 */
public class PersistenciaParranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaParranderos.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaParranderos instance;

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, Contrato_Apartamento, Contrato_Cliente_Esporadico, Contrato_Hab_Vivienda, 
	 * Contrato, ContratoHabHostal, ContratoHabHotel, ContratoHabUniversitaria, Operador y Reserva
	 */
	private List <String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaAlohandes
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a la tabla CONTRATO de la base de datos
	 */
	private SQLContrato sqlContrato;

	/**
	 * Atributo para el acceso a la tabla CONTRATO_APARTAMENTO de la base de datos
	 */
	private SQLContrato_Apartamento sqlContratoApartamento;

	/**
	 * Atributo para el acceso a la tabla CONTRATO_CLIENTE_ESPORADICO de la base de datos
	 */
	private SQLContrato_Cliente_Esporadico sqlContratoClienteEsporadico;

	/**
	 * Atributo para el acceso a la tabla CONTRATO_HAB_VIVIENDA de la base de datos
	 */
	private SQLContrato_Hab_Vivienda sqlContratoHabVivienda;

	/**
	 * Atributo para el acceso a la tabla CONTRATOHABHOSTAL de la base de datos
	 */
	private SQLContratoHabHostal sqlContratoHabHostal;

	/**
	 * Atributo para el acceso a la tabla CONTRATOHABHOTEL de la base de datos
	 */
	private SQLContratoHabHotel sqlContratoHabHotel;

	/**
	 * Atributo para el acceso a la tabla CONTRATOHABUNIVERSITARIA de la base de datos
	 */
	private SQLContratoHabUniversitaria sqlContratoHabUniversitaria;

	/**
	 * Atributo para el acceso a la tabla OPERADOR de la base de datos
	 */
	private SQLOperador sqlOperador;

	/**
	 * Atributo para el acceso a la tabla RESERVAN de la base de datos
	 */
	private SQLReserva sqlReserva; 
	
	private SQLReservaColectiva sqlReservaColectiva; 

	private SQLCliente sqlCliente;

	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaParranderos ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
		tablas.add("CONTRATO");
		tablas.add("CONTRATO_APARTAMENTO");
		tablas.add("CONTRATO_CLIENTE_ESPORADICO");
		tablas.add("CONTRATO_HAB_VIVIENDA");
		tablas.add("CONTRATOHABHOSTAL");
		tablas.add("CONTRATOHABHOTEL");
		tablas.add("CONTRATOHABUNIVERSITARIA");
		tablas.add("OPERADOR");
		tablas.add("RESERVA");
		tablas.add("RESERVA_COLECTIVA");

	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaParranderos (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		System.out.println(unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaAlohandes existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlUtil = new SQLUtil(this);
		sqlContrato = new SQLContrato(this);
		sqlContratoApartamento = new SQLContrato_Apartamento(this);
		sqlContratoClienteEsporadico = new SQLContrato_Cliente_Esporadico(this);
		sqlContratoHabVivienda = new SQLContrato_Hab_Vivienda(this);
		sqlContratoHabHostal = new SQLContratoHabHostal(this);
		sqlContratoHabHotel = new SQLContratoHabHotel(this);
		sqlContratoHabUniversitaria = new SQLContratoHabUniversitaria(this);
		sqlOperador = new SQLOperador(this);
		sqlReserva = new SQLReserva(this);
		sqlReservaColectiva = new SQLReservaColectiva(this);
		sqlCliente= new SQLCliente(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqParranderos ()
	{
		return tablas.get (0);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaContrato()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaContrato_Apartamento ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaContrato_Cliente_Esporadico ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaContrato_Hab_Vivienda ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaContratoHabHostal() 
	{
		return tablas.get(7);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaContratoHabHotel() 
	{
		return tablas.get(8);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaContratoHabUniversitaria() 
	{
		return tablas.get(9);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaOperador() {
		return tablas.get(12);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Alohandes
	 */
	public String darTablaReserva() {
		return tablas.get(14);
	}
	

	public String darTablaReservaColectiva() {
		return tablas.get(15);
	}



	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación RESERVA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla TipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @param id_contrato - El id del contrato
	 * @param personas - numero de personas a ingresar
	 * @param fecha_inicio - fecha de inicio de la reserva
	 * @param fecha_fin - fecha de fin de la reserva
	 * @param fecha_limite - fecha limite a entregar el alojamiento con descuento
	 * @param fecha_realizacion -  fecha de realizacion de la reserva
	 * @param tipo - tipo de alojamiento 
	 * @param id_cliente - El id del cliente
	 * @return El objeto Reserva adicionado. null si ocurre alguna Excepción	
	 */
	public Reserva adicionarReserva(long id_contrato, int personas, String fecha_inicio, String fecha_fin, String fecha_limite, String fecha_realizacion, String tipo, long id_cliente) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idReserva = nextval ();
			long tuplasInsertadas = sqlReserva.adicionarReserva(pm, idReserva,id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
			tx.commit();

			log.trace ("Inserción reserva: " + idReserva+ ": " + tuplasInsertadas + " tuplas insertadas");
			if (tuplasInsertadas!=0){
			return new Reserva (idReserva,id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
			}else {
				return null;
			}
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla RESERVA, dado el identificador de la reserva
	 * Adiciona entradas al log de la aplicación
	 * @param idReserva - El identificador de la reserva
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarReservaPorId (long idReserva) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlReserva.eliminarReservaPorId(pm, idReserva);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla RESERVA
	 * @return La lista de objetos Reserva, construidos con base en las tuplas de la tabla RESERVA
	 */
	public List<Reserva> darReservas()
	{
		return sqlReserva.darReservas(pmf.getPersistenceManager());
	}


	/* ****************************************************************
	 * 			Métodos para manejar la relación CONTRATO
	 *****************************************************************/

	/**

	 * Método que inserta, de manera transaccional, una tupla en la tabla CONTRATO
	 * Adiciona entradas al log de la aplicación
	 * @param capacidad - capacidad de la oferta de alojamiento
	 * @param costo - costo del contrato
	 * @return El objeto Contrato adicionado. null si ocurre alguna Excepción	
	 */
	public Contrato adicionarContrato(int capacidad, int costo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idContrato = nextval ();
			long tuplasInsertadas = sqlContrato.adicionarContrato(pm, idContrato ,capacidad, costo);
			tx.commit();

			log.trace ("Inserción contrato: " + idContrato+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new Contrato (idContrato, capacidad, costo,"YES");
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla CONTRATO, dado el identificador del contrato
	 * Adiciona entradas al log de la aplicación
	 * @param idContrato - El identificador del contrato
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContratoPorId (long idContrato) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContrato.eliminarContratoPorId(pm, idContrato);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla CONTRATO
	 * @return La lista de objetos Contrato, construidos con base en las tuplas de la tabla CONTRATO
	 */
	public List<Contrato> darContratos ()
	{
		return sqlContrato.darContratos(pmf.getPersistenceManager());
	}

	public List<Contrato> darPopulares() {
		return sqlContrato.darPopulares(pmf.getPersistenceManager());
	}
	/* ****************************************************************
	 * 			Métodos para manejar la relación OPERADOR
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla OPERADOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - nombre del operador
	 * @return El objeto Operador adicionado. null si ocurre alguna Excepción	
	 */
	public Operador adicionarOperador(String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idOperador = nextval ();
			long tuplasInsertadas = sqlOperador.adicionarOperador(pm, idOperador, nombre);
			tx.commit();

			log.trace ("Inserción contrato: " + idOperador+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new Operador (idOperador, nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla OPERADOR, dado el identificador del operador
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador- El identificador del operador
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOperadorPorId (long idOperador) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContrato.eliminarContratoPorId(pm, idOperador);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla OPERADOR
	 * @return La lista de objetos Operador, construidos con base en las tuplas de la tabla OPERADOR
	 */
	public List<Operador> darOperadores ()
	{
		return sqlOperador.darOperadores(pmf.getPersistenceManager());
	}

	public List<Ganancia> darGanancias() {
		return sqlOperador.darGanancias(pmf.getPersistenceManager());
	}
	/* ****************************************************************
	 * 			Métodos para manejar la relación CONTRATO_APARTAMENTO
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla CONTRATO_APARTAMENTO
	 * Adiciona entradas al log de la aplicación
	 * @param id_apartamento - El id del apartamento
	 * @param meses_contratados - meses contratados al apartamento
	 * @return El objeto Contrato_Apartamento adicionado. null si ocurre alguna Excepción	
	 */
	public Contrato_Apartamento adicionarContratoApartamento(long id_apartamento, int meses_contratados) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idContrato_Apartamento = nextval ();
			long tuplasInsertadas = sqlContratoApartamento.adicionarContratoApartamento(pm, idContrato_Apartamento, id_apartamento, meses_contratados);
			tx.commit();

			log.trace ("Inserción contrato: " + idContrato_Apartamento+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new Contrato_Apartamento(idContrato_Apartamento, id_apartamento, meses_contratados);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla CONTRATO_APARTAMENTO, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idContrato_Apartamento - El identificador del contrato del apartamento
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContratoApartamentoPorId (long idContrato_Apartamento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContratoApartamento.eliminarContratoApartamentoPorId(pm, idContrato_Apartamento);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla CONTRATO_APARTAMENTO
	 * @return La lista de objetos Contrato_Apartamento, construidos con base en las tuplas de la tabla CONTRATO_APARTAMENTO
	 */
	public List<Contrato_Apartamento> darContratosApartamento()
	{
		return sqlContratoApartamento.darContratosApartamento(pmf.getPersistenceManager());
	}

	/* *************************************************************************
	 * 			Métodos para manejar la relación CONTRATO_CLIENTE_ESPORADICO
	 ***************************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla CONTRATO_CLIENTE_ESPORADICO
	 * Adiciona entradas al log de la aplicación
	 * @param id_apartamento - El id del apartamento
	 * @param id_vivienda - El id de la vivienda
	 * @param cantidad_noches - Cantidad de noches reservadas
	 * @param costo_base - Costo base del contrato
	 * @param costo_seguro - Costo del seguro
	 * @param num_habitaciones - Numero de habitaciones contratadas
	 * @param ubicacion - Ubicacion del alojamiento
	 * @return El objeto Contrato_Cliente_Esporadico adicionado. null si ocurre alguna Excepción	
	 */
	public Contrato_Cliente_Esporadico adicionarContratoClienteEsporadico(long id_apartamento, long id_vivienda, int cantidad_noches,
			int costo_base, int costo_seguro, int num_habitaciones, String ubicacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idContrato_Cliente_Esporadico = nextval ();
			long tuplasInsertadas = sqlContratoClienteEsporadico.adicionarContrato_Cliente_Esporadico(pm, idContrato_Cliente_Esporadico, id_apartamento, id_vivienda, cantidad_noches, costo_base, costo_seguro, num_habitaciones, ubicacion);
			tx.commit();

			log.trace ("Inserción contrato: " + idContrato_Cliente_Esporadico+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new Contrato_Cliente_Esporadico(idContrato_Cliente_Esporadico, id_apartamento, id_vivienda, cantidad_noches, costo_base, costo_seguro, num_habitaciones, ubicacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla CONTRATO_CLIENTE_ESPORADICO, dado el identificador del contrato del cliente esporadico
	 * Adiciona entradas al log de la aplicación
	 * @param idContrato_Cliente_Esporadico - El identificador del contrato del cliente esporadico
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContratoClienteEsporadicoPorId (long idContrato_Cliente_Esporadico) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContratoClienteEsporadico.eliminarContrato_Cliente_EsporadicoPorId(pm, idContrato_Cliente_Esporadico);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla CONTRATO_CLIENTE_ESPORADICO
	 * @return La lista de objetos Contrato_Cliente_Esporadico, construidos con base en las tuplas de la tabla CONTRATO_CLIENTE_ESPORADICO
	 */
	public List<Contrato_Cliente_Esporadico> darContratosClienteEsporadico()
	{
		return sqlContratoClienteEsporadico.darContratosClienteEsporadico(pmf.getPersistenceManager());
	}


	/* ****************************************************************
	 * 			Métodos para manejar la relación CONTRATO_HAB_VIVIENDA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla CONTRATO_HAB_VIVIENDA
	 * Adiciona entradas al log de la aplicación
	 * @param id_vivienda - El id de la vivienda
	 * @param meses_contratados - meses contratados a la vivienda
	 * @return El objeto Contrato_Hab_Vivienda adicionado. null si ocurre alguna Excepción	
	 */
	public Contrato_Hab_Vivienda adicionarContratoHabVivienda(long id_vivienda, int meses_contratados) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idContrato_Hab_Vivienda = nextval ();
			long tuplasInsertadas = sqlContratoHabVivienda.adicionarContratoHabVivienda(pm, idContrato_Hab_Vivienda, id_vivienda, meses_contratados);
			tx.commit();

			log.trace ("Inserción contrato: " + idContrato_Hab_Vivienda+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new Contrato_Hab_Vivienda(idContrato_Hab_Vivienda, id_vivienda, meses_contratados);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla CONTRATO_HAB_VIVIENDA, dado el identificador del contrato de la habitacion de la vivienda
	 * Adiciona entradas al log de la aplicación
	 * @param idContrato_Hab_Vivienda - El identificador del contrato de la habitacion vivienda
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContratoHabViviendaPorId (long idContrato_Hab_Vivienda) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContratoHabVivienda.eliminarContratoHabViviendaPorId(pm, idContrato_Hab_Vivienda);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla CONTRATO_HAB_VIVIENDA
	 * @return La lista de objetos Contrato_Hab_Vivienda, construidos con base en las tuplas de la tabla CONTRATO_HAB_VIVIENDA
	 */
	public List<Contrato_Hab_Vivienda> darContratosHabVivienda()
	{
		return sqlContratoHabVivienda.darContratosHabVivienda(pmf.getPersistenceManager());
	}



	/* ****************************************************************
	 * 			Métodos para manejar la relación CONTRATOHABHOSTAL
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla CONTRATOHABHOSTAL
	 * Adiciona entradas al log de la aplicación
	 * @param id_hostal - El id del hostal
	 * @return El objeto ContratoHabHostal adicionado. null si ocurre alguna Excepción	
	 */
	public ContratoHabHostal adicionarContratoHabHostal(long id_hostal) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idContratoHabHostal = nextval ();
			long tuplasInsertadas = sqlContratoHabHostal.adicionarContratoHabHostal(pm, idContratoHabHostal, id_hostal);
			tx.commit();

			log.trace ("Inserción contrato: " + idContratoHabHostal+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new ContratoHabHostal(idContratoHabHostal, id_hostal);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla CONTRATOHABHOSTAL, dado el identificador del contrato de la habitacion del hostal
	 * Adiciona entradas al log de la aplicación
	 * @param idContratoHabHostal - El identificador del contrato de la habitacion del hostal
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContratoHabHostalPorId (long idContratoHabHostal) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContratoHabHostal.eliminarContratoHabHostalPorId(pm, idContratoHabHostal);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla CONTRATOHABHOSTAL
	 * @return La lista de objetos ContratoHabHostal, construidos con base en las tuplas de la tabla CONTRATOHABHOSTAL
	 */
	public List<ContratoHabHostal> darContratosHabHostal()
	{
		return sqlContratoHabHostal.darContratosHabHostal(pmf.getPersistenceManager());
	}


	/* ****************************************************************
	 * 			Métodos para manejar la relación CONTRATOHABHOTEL
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla CONTRATOHABHOTEL
	 * Adiciona entradas al log de la aplicación
	 * @param id_hotel - El id del hotel
	 * @param categoria - categoria de la habitacion del hotel
	 * @param tamanio - tamaño de la habitacion
	 * @param tipo_habitacion - tipo de habitacion del hotel
	 * @param ubicacion - ubicacion de la habitacion dentro del hotel
	 * @return El objeto ContratoHabHotel adicionado. null si ocurre alguna Excepción	
	 */
	public ContratoHabHotel adicionarContratoHabHotel(long id_hotel, int categoria, int tamanio, String tipo_habitacion,
			int ubicacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idContratoHabHotel = nextval ();
			long tuplasInsertadas = sqlContratoHabHotel.adicionarContratoHabHotel(pm, idContratoHabHotel, id_hotel, categoria, tamanio, tipo_habitacion, ubicacion);
			tx.commit();

			log.trace ("Inserción contrato: " + idContratoHabHotel+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new ContratoHabHotel(idContratoHabHotel, id_hotel, categoria, tamanio, tipo_habitacion, ubicacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla CONTRATOHABHOTEL, dado el identificador del contrato de la habitacion del hotel
	 * @param idContratoHabHotel - El identificador del contrato de la habitacion del hotel
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContratoHabHotelPorId (long idContratoHabHotel) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContratoHabHotel.eliminarContratoHabHotelPorId(pm, idContratoHabHotel);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Contrato> darOfertasCar(List<String> car){

		return sqlContrato.darContratosPorCar(car,pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla CONTRATOHABHOTEL
	 * @return La lista de objetos ContratoHabHotel, construidos con base en las tuplas de la tabla CONTRATOHABHOTEL
	 */
	public List<ContratoHabHotel> darContratosHabHotel()
	{
		return sqlContratoHabHotel.darContratosHabHotel(pmf.getPersistenceManager());
	}


	/* ************************************************************************
	 * 			Métodos para manejar la relación CONTRATOHABUNIVERSITARIA
	 **************************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla CONTRATOHABUNIVERSITARIA
	 * Adiciona entradas al log de la aplicación
	 * @param id_vivienda - el id de la vivienda universitaria
	 * @param meses_contratados - meses contratados a la habitacion
	 * @param individual - es individual o no
	 * @param ubicacion - ubicacion de la habitacion universitaria
	 * @param gimnasio - tiene gimnasio o no
	 * @param restaurante - tiene restaurante o no
	 * @param sala_esparcimiento - tiene salas de espacio o no
	 * @param sala_estudio - tiene salas de estudio o no
	 * @return El objeto ContratoHabUniveristaria adicionado. null si ocurre alguna Excepción	
	 */
	public ContratoHabUniversitaria adicionarContratoHabUniversitaria(long id_vivienda, int meses_contratados, String individual,
			int ubicacion, String gimnasio, String restaurante, String sala_esparcimiento, String sala_estudio) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idContratoHabUniversitaria = nextval ();
			long tuplasInsertadas = sqlContratoHabUniversitaria.adicionarContratoHabUniversitaria(pm, idContratoHabUniversitaria, id_vivienda, individual, ubicacion, gimnasio, restaurante, sala_esparcimiento, sala_estudio);
			tx.commit();

			log.trace ("Inserción contrato: " + idContratoHabUniversitaria+ ": " + tuplasInsertadas + " tuplas insertadas");
			return new ContratoHabUniversitaria(idContratoHabUniversitaria, id_vivienda, meses_contratados, individual, ubicacion, gimnasio, restaurante, sala_esparcimiento, sala_estudio);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla CONTRATOHABUNIVERSITARIA, dado el identificador del contrato de la habitacion universitaria
	 * Adiciona entradas al log de la aplicación
	 * @param idContratoHabUniversitaria - El identificador del contrato de la habitación universitaria
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContratoHabUniversitariaPorId (long idContratoHabUniversitaria) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContratoHabUniversitaria.eliminarContratoHabUniversitariaPorId(pm, idContratoHabUniversitaria);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que consulta todas las tuplas en la tabla CONTRATOHABUNIVERSITARIA
	 * @return La lista de objetos ContratoHabUniversitaria, construidos con base en las tuplas de la tabla CONTRATOHABUNIVERSITARIA
	 */
	public List<ContratoHabUniversitaria> darContratosHabUniversitaria() 
	{
		return sqlContratoHabUniversitaria.darContratosHabUniversitaria(pmf.getPersistenceManager());
	}

	public List<UsosVinculo> darUsosPorVinculo() {
		return sqlReserva.darUsosPorVinculo(pmf.getPersistenceManager());
	}

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Alohandes
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarParranderos (pm);
			tx.commit ();
			log.info ("Borrada la base de datos");
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}
	public long deshabilitarOferta(long idCont) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContrato.deshabilitarContrato(pm, idCont);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	public long habilitarOferta(long idCont){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			long resp = sqlContrato.habilitarContrato(pm, idCont);
			if (resp==0) {
				log.info("La oferta ya se encontraba habilitada");
			}else {
				log.info("la oferta "+ idCont+" fue habilitada");
			}
			tx.commit();
			return resp;
		}catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public List<Indice>darIndices() {
		return sqlContrato.darIndices(pmf.getPersistenceManager());
	}
	
	
	
	public Reserva adicionarReservaColectiva(long id_contrato, int personas, String fecha_inicio, String fecha_fin, String fecha_limite, String fecha_realizacion, String tipo, long id_cliente) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idReserva = nextval ();
			long tuplasInsertadas = sqlReservaColectiva.adicionarReservaColectiva(pm, idReserva,id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
			tx.commit();

			log.trace ("Inserción reserva: " + idReserva+ ": " + tuplasInsertadas + " tuplas insertadas");
			if (tuplasInsertadas!=0){
			return new Reserva (idReserva,id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
			}else {
				return null;
			}
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	public long eliminarReservaColectivaPorId(long idCont) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlReservaColectiva.eliminarReservaColectivaPorId(pm, idCont);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public List<Cliente> consultarConsumoOferta1( long IdOf, String fecha_inicio,String fecha_fin){
		return sqlCliente.consultarConsumoOferta1(pmf.getPersistenceManager(),IdOf,fecha_inicio,fecha_fin);
	
	}

}