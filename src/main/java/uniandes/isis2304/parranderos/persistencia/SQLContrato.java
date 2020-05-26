package uniandes.isis2304.parranderos.persistencia;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;

import uniandes.isis2304.parranderos.negocio.Contrato;
import uniandes.isis2304.parranderos.negocio.ContratoHabHostal;
import uniandes.isis2304.parranderos.negocio.ContratoHabHotel;
import uniandes.isis2304.parranderos.negocio.ContratoHabUniversitaria;
import uniandes.isis2304.parranderos.negocio.Contrato_Apartamento;
import uniandes.isis2304.parranderos.negocio.Contrato_Cliente_Esporadico;
import uniandes.isis2304.parranderos.negocio.Contrato_Hab_Vivienda;
import uniandes.isis2304.parranderos.negocio.Id;
import uniandes.isis2304.parranderos.negocio.Indice;
import uniandes.isis2304.parranderos.negocio.Parranderos;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.persistencia.*;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CONTRATO de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Kevin Becerra - Christian Forigua
 */
public class SQLContrato{

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
	private SQLReserva sqlreserva;
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLContrato(PersistenciaParranderos pp) {
		this.pp = pp;
		sqlreserva= new SQLReserva(pp);
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una BEBIDA a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la bebida
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida de la bebida
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarContrato (PersistenceManager pm, long id, int capacidad, int costo) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContrato() + "(id, capacidad, costo,habiliatada) values (?, ?, ?,'YES')");
		q.setParameters(id, capacidad, costo);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ContratoS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la Contrato
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContratoPorId (PersistenceManager pm, long idContrato) throws Exception
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");  
		LocalDateTime actual = LocalDateTime.now();
		String fecha_actual=dtf.format(actual);
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva()+ " WHERE ID_CONTRATO =? AND "
				+ "TO_DATE(FECHA_FIN,'DD/MM/YYYY HH24;MI:SS')<TO_DATE('"+fecha_actual+"','DD/MM/YYYY HH24;MI:SS')");                                             
		q.setResultClass(Reserva.class);
		q.setParameters(idContrato);

		List<Reserva> reservas=q.executeList();
		if(reservas.size()>0) {
			System.out.println("HAY RESERVAS");
			throw new Exception("La oferta que quiere retirar tiene reservas vigente");

		}
		else {
			List<String> tablas=new ArrayList<>();
			tablas.add(pp.darTablaContrato_Apartamento());
			tablas.add(pp.darTablaContrato_Cliente_Esporadico());
			tablas.add(pp.darTablaContrato_Hab_Vivienda());
			tablas.add(pp.darTablaContratoHabHostal()); 
			tablas.add(pp.darTablaContratoHabHotel()); 
			tablas.add(pp.darTablaContratoHabUniversitaria());
			boolean termino=false;
			for (int i=0;i<tablas.size() && !termino;i++){
				if (tablas.get(i).equals(pp.darTablaContrato_Apartamento())) {
					Query q2 = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato_Apartamento() + " WHERE ID_CONTRATO=?");
					q2.setResultClass(Contrato_Apartamento.class);
					q2.setParameters(idContrato);
					Contrato_Apartamento contrato =(Contrato_Apartamento) q2.executeUnique();
					if (contrato!=null){log.info("Eliminando:"+contrato);}
				}else if (tablas.get(i).equals(pp.darTablaContrato_Cliente_Esporadico())) {
					Query q2 = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato_Cliente_Esporadico() + " WHERE ID_CONTRATO=?");
					q2.setResultClass(Contrato_Cliente_Esporadico.class);
					q2.setParameters(idContrato);
					Contrato_Cliente_Esporadico contrato= (Contrato_Cliente_Esporadico) q2.executeUnique();
					if (contrato!=null){log.info("Eliminando:"+contrato);}
				}else if(tablas.get(i).equals(pp.darTablaContrato_Hab_Vivienda())){
					Query q2 = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato_Hab_Vivienda() + " WHERE ID_CONTRATO=?");
					q2.setResultClass(Contrato_Hab_Vivienda.class);
					q2.setParameters(idContrato);
					Contrato_Hab_Vivienda contrato=(Contrato_Hab_Vivienda) q2.executeUnique();
					if (contrato!=null){log.info("Eliminando:"+contrato);}
				}
				else if (tablas.get(i).equals(pp.darTablaContratoHabHostal())) {
					Query q2 = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContratoHabHostal () + " WHERE ID_CONTRATO=?");
					q2.setResultClass(ContratoHabHostal.class);
					q2.setParameters(idContrato);
					ContratoHabHostal contrato = (ContratoHabHostal) q2.executeUnique();
					if (contrato!=null){log.info("Eliminando:"+contrato);}
				}else if (tablas.get(i).equals(pp.darTablaContratoHabHotel())) {
					Query q2 = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContratoHabHotel () + " WHERE ID_CONTRATO=?");
					q2.setResultClass(ContratoHabHotel.class);
					q2.setParameters(idContrato);
					ContratoHabHotel contrato= (ContratoHabHotel) q2.executeUnique();
					if (contrato!=null){log.info("Eliminando:"+contrato);}
				}else {
					Query q2 = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContratoHabUniversitaria () + " WHERE ID_CONTRATO=?");
					q2.setResultClass(ContratoHabUniversitaria.class);
					q2.setParameters(idContrato);
					ContratoHabUniversitaria contrato=(ContratoHabUniversitaria) q2.executeUnique();
					if (contrato!=null){log.info("Eliminando:"+contrato);}
				}

				Query qdel=pm.newQuery(SQL,"DELETE FROM " +tablas.get(i)+ " WHERE ID_CONTRATO=?");
				qdel.setParameters(idContrato);
				qdel.executeUnique();
			}
			Contrato eliminar=darContratoPorId(pm, idContrato);
			log.info ("Eliminando: " + eliminar);
			Query qCt=pm.newQuery(SQL, "DELETE FROM " +pp.darTablaContrato()+ " WHERE ID=?");
			qCt.setParameters(idContrato); 
			return (long) qCt.executeUnique();
		}             
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idContrato - El identificador de la Contrato
	 * @return El objeto Contrato que tiene el identificador dado
	 */
	public Contrato darContratoPorId (PersistenceManager pm, long idContrato) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM CONTRATO WHERE id = ?");
		q.setResultClass(Contrato.class);
		q.setParameters(idContrato);
		return (Contrato) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS ContratoS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Contrato
	 */
	public List<Contrato> darContratos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM CONTRATO");
		q.setResultClass(Contrato.class);
		return (List<Contrato>) q.executeList();
	}

	public List<Contrato> darPopulares (PersistenceManager pm){
		Query q=pm.newQuery(SQL,"SELECT ID, CAPACIDAD,COSTO\n" + 
				"FROM CONTRATO CTS JOIN (SELECT ID_CONTRATO,VECES\n" + 
				"		    FROM (SELECT ID_CONTRATO, COUNT(ID_CONTRATO) AS VECES\n" + 
				"		    FROM RESERVA\n" + 
				"	            GROUP BY ID_CONTRATO\n" + 
				"	            ORDER BY VECES DESC)\n" + 
				"	            WHERE ROWNUM<=20) POP\n" + 
				"	            ON CTS.ID=POP.ID_CONTRATO"); 
		q.setResultClass(Contrato.class);
		return (List<Contrato>)q.executeList(); 
	}

	public List<Indice>darIndices(PersistenceManager pm){
		Query q= pm.newQuery(SQL,"SELECT CONT.ID AS ID_OFERTA, CONT.CAPACIDAD, TOTAL.ALOJADAS, TOTAL.ALOJADAS/CONT.CAPACIDAD AS INDICE\n" + 
				"FROM CONTRATO CONT JOIN (SELECT ID_CONTRATO, SUM(PERSONAS) AS ALOJADAS\n" + 
				"                    FROM RESERVA\n" + 
				"                    GROUP BY ID_CONTRATO) TOTAL\n" + 
				"              ON CONT.ID=TOTAL.ID_CONTRATO\n" + 
				"ORDER BY INDICE DESC");
		q.setResultClass(Indice.class);
		return (List<Indice>) q.executeList();
	}

	public long deshabilitarContrato(PersistenceManager pm, long idCont) throws Exception{
		System.out.println("ENTRO A LA SENTENCIA");
		Query q1=pm.newQuery(SQL,"SELECT * FROM RESERVA WHERE ID_CONTRATO=? "); 
		q1.setResultClass(Reserva.class);
		q1.setParameters(idCont); 
		List<Reserva> reservas=(List<Reserva>)q1.executeList();
		System.out.println("Buscó las reservas");
		System.out.println(reservas);
		System.out.println(reservas.size());
		//Borrar las reservas de la oferta deshabilitadas de la base de datos
		for (int i=0; i<reservas.size();i++) {
			sqlreserva.eliminarReservaPorId(pm,reservas.get(i).getId());
		}
		System.out.println("Se eliminaron todas las reservas");
		if (reservas.size()>0){
			System.out.println("Hay reservas");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");  
			LocalDateTime actual = LocalDateTime.now();
			String fecha_actual_str=dtf.format(actual);
			System.out.println(fecha_actual_str);
			List<Reserva> vigentes=new LinkedList<>();
			List<Reserva> no_vigentes=new LinkedList<>();
			for (int i=0; i<reservas.size();i++) {
				Reserva reserva_actual=reservas.get(i);
				DateFormat fechaHora = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				Date fecha_fin = fechaHora.parse(reserva_actual.getFecha_fin());
				Date fecha_actual=fechaHora.parse(fecha_actual_str);
				if (fecha_fin.before(fecha_actual)) {
					vigentes.add(reserva_actual);
				}else {
					no_vigentes.add(reserva_actual);
				}
			}
			//Se obtienen las características de la oferta deshabilitada
			List<String> caracteristicas=darCaracteristicas(pm,idCont);
			//Se obtienen los contratos con las caracteristicas
			List<Contrato> contratos_con_car=darContratosPorCar(caracteristicas, pm);
			//Se elimina el contrato deshabilitado
			boolean eliminado=false;
			for (int i=0; i<contratos_con_car.size() && !eliminado;i++) {
				if (contratos_con_car.get(i).getId()==idCont){
					eliminado=true;
					contratos_con_car.remove(i);
				}
			}
			List<Reserva> faltantes= new LinkedList<Reserva>();
			// Se añaden las vigentes
			for (int i=0;i<vigentes.size();i++) {
				Reserva res=vigentes.get(i);
				boolean agendo=false;
				for (int j=0;j<contratos_con_car.size() && !agendo;j++) {
					long rta=sqlreserva.adicionarReserva(pm,res.getId(),contratos_con_car.get(j).getId(),res.getPersonas(),res.getFecha_inicio(), res.getFecha_fin(),res.getFecha_limite(),res.getFecha_realizaciom(),res.getTipo(), res.getId_cliente());
					if (rta!=0) {
						log.info("Se reagendo la reserva:"+res.getId()+" en la oferta"+ contratos_con_car.get(j).getId());
						res.setId_contrato(contratos_con_car.get(j).getId());
						log.info("Reserva reagendada:"+ res.toString());
						agendo=true;
					}
				}
				if (!agendo){
					faltantes.add(res);
				}    			
			}
			//se ordenan las que no son vigentes por orden de realización 
			Collections.sort(no_vigentes);
			// se miran las que no están vigentes
			for (int i=0;i<no_vigentes.size();i++) {
				Reserva res=no_vigentes.get(i);
				boolean agendo=false;
				for (int j=0;j<contratos_con_car.size() && !agendo;j++) {
					long rta=sqlreserva.adicionarReserva(pm,res.getId(),contratos_con_car.get(j).getId(),res.getPersonas(),res.getFecha_inicio(), res.getFecha_fin(),res.getFecha_limite(),res.getFecha_realizaciom(),res.getTipo(), res.getId_cliente());
					if (rta!=0) {
						log.info("Se reagendo la reserva:"+res.getId()+" en la oferta"+ contratos_con_car.get(j).getId());
						res.setId_contrato(contratos_con_car.get(j).getId());
						log.info("Reserva reagendada:"+ res.toString());
						agendo=true;
					}
				}
				if (!agendo){
					faltantes.add(res);
				}    			
			}
			//Se informa de las reservas que no pudieron ser reagendadas 
			log.info("No se pudieron reagendar las reservas:");
			for(int i=0;i<faltantes.size();i++) {
				log.info(faltantes.get(i).toString());
			}
		}else {
			log.info("No se reagendo ninguna reserva. El contrato no tenía ninguna reserva agendada");
		}
		Query q2=pm.newQuery(SQL,"UPDATE CONTRATO\n" + 
				"SET HABILITADA='NO'\n" + 
				"WHERE ID=?"); 
		q2.setParameters(idCont);
		log.info("Se deshabilitó la oferta:"+idCont);
		return (long)q2.executeUnique();
	}


	public List<String> darCaracteristicas(PersistenceManager pm, long IdCont){
		Query q=pm.newQuery(SQL,"SELECT NOMBRE_SERVICIO FROM SERVICIO WHERE ID=?");
		q.setResultClass(String.class);
		q.setParameters(IdCont); 
		return (List<String>)q.executeList();
	}

	public long habilitarContrato(PersistenceManager pm, long idCont) {
		Query q1=pm.newQuery(SQL,"SELECT * FROM CONTRATO WHERE ID=?");
		q1.setResultClass(Contrato.class);
		q1.setParameters(idCont); 
		Contrato ct=(Contrato)q1.executeUnique();
		if (ct.getHabilitada().equals("YES")) {
			return 0;
		}else {
			Query q=pm.newQuery(SQL,"UPDATE CONTRATO\n" + 
					"SET HABILITADA='YES'\n" + 
					"WHERE ID=?");
			q.setParameters(idCont);
			return (long)q.executeUnique();
		}
	}

	public List<Contrato> darContratosProveedor(PersistenceManager pm ,long IdOp, String tipo){
		List<Contrato> lista=null;
		if (tipo.equals("HOTEL")) {
			Query q=pm.newQuery(SQL,"SELECT CTT.* FROM (SELECT * FROM CONTRATO)CTT JOIN (SELECT CT.* FROM HOTEL JOIN (SELECT * FROM CONTRATOHABHOTEL) CT ON hotel.id=CT.ID_HOTEL WHERE HOTEL.ID="+IdOp+") HT\n" + 
					"                                        ON CTT.ID=HT.ID_CONTRATO");
			q.setResultClass(Contrato.class);
			lista=(List<Contrato>) q.executeList();
		}
		else if (tipo.equals("HOSTAL")) {
			Query q=pm.newQuery(SQL,"SELECT CTT.* FROM (SELECT * FROM CONTRATO)CTT JOIN (SELECT CT.* FROM HOSTAL JOIN (SELECT * FROM CONTRATOHABHOSTAL) CT ON hostal.id=CT.ID_HOSTAL WHERE HOSTAL.ID="+IdOp+") HT\n" + 
					"                                        ON CTT.ID=HT.ID_CONTRATO");
			q.setResultClass(Contrato.class);
			lista=(List<Contrato>) q.executeList(); 
		}
		else if (tipo.equals("HAB_VIVIENDA")) {
			Query q=pm.newQuery(SQL,"SELECT CTT.* FROM (SELECT * FROM CONTRATO)CTT JOIN (SELECT * FROM VIVIENDA_FAMILIAR JOIN (SELECT * FROM CONTRATO_HAB_VIVIENDA)CT ON vivienda_familiar.id=CT.ID_VIVIENDA WHERE ID_PERSONA_NATURAL="+IdOp+") C\n" + 
					"                                          ON CTT.ID=C.ID_CONTRATO"); 
			q.setResultClass(Contrato.class);
			lista=(List<Contrato>) q.executeList();
		}
		else if (tipo.equals("APARTAMENTO")) {
			Query q=pm.newQuery(SQL, "SELECT CTT.* FROM (SELECT * FROM CONTRATO)CTT JOIN (SELECT * FROM APARTAMENTO JOIN (SELECT * FROM CONTRATO_APARTAMENTO)CT ON APARTAMENTO.id=CT.ID_APARTAMENTO WHERE ID_PERSONA_NATURAL="+IdOp+") C\n" + 
					"                                          ON CTT.ID=C.ID_CONTRATO");
			q.setResultClass(Contrato.class);
			lista=(List<Contrato>) q.executeList();
		}
		else if (tipo.equals("VIVIENDA_UNIVERSITARIA")) {
			Query q=pm.newQuery(SQL,"SELECT CTT.* FROM (SELECT * FROM CONTRATO)CTT JOIN (SELECT * FROM VIVIENDAUNIVERSITARIA JOIN (SELECT * FROM CONTRATOHABUNIVERSITARIA)CT ON VIVIENDAUNIVERSITARIA.id=CT.ID_VIVIENDA WHERE ID_VIVIENDA="+IdOp+") C\n" + 
					"                                          ON CTT.ID=C.ID_CONTRATO"); 
			q.setResultClass(Contrato.class);
			lista=(List<Contrato>) q.executeList();
		}
		else if (tipo.equals("ESPORADICO")) {
			Query q=pm.newQuery(SQL,"SELECT CTT.* FROM (SELECT * FROM CONTRATO)CTT JOIN (SELECT * FROM APARTAMENTO JOIN (SELECT * FROM CONTRATO_CLIENTE_ESPORADICO)CT ON APARTAMENTO.id=CT.ID_APARTAMENTO WHERE ID_PERSONA_NATURAL="+IdOp+") C\n" + 
					"                                          ON CTT.ID=C.ID_CONTRATO"); 
			q.setResultClass(Contrato.class);
			lista=(List<Contrato>)q.executeList();
		}

		return lista; 
	}


	public List<Contrato> darContratosPorCar(List<String> car, PersistenceManager pm){
		List<Contrato> retornar = new LinkedList<>();
		List<Integer> todos= new LinkedList<>();
		for (int i=0; i<car.size();i++) {
			Query q=pm.newQuery(SQL, "SELECT ID FROM SERVICIO WHERE NOMBRE_SERVICIO = ?");
			q.setResultClass(Integer.class);
			q.setParameters(car.get(i));
			List<Integer>actual=((List<Integer>)q.executeList());
			for (int j=0; j<actual.size();j++) {
				todos.add(actual.get(j));
			}
		}
		System.out.println(todos);
		ArrayList<Integer> revisados= new ArrayList<>(); 
		for (int i=0; i<todos.size(); i++) {
			if (!revisados.contains(todos.get(i))){
				revisados.add(todos.get(i));
				int veces = Collections.frequency(todos,todos.get(i)); 
				if (veces==car.size()) {
					Contrato ct=darContratoPorId(pm, todos.get(i));
					retornar.add(ct);
				}
			}
		}
		System.out.println(retornar);
		return retornar;
	}	
}
