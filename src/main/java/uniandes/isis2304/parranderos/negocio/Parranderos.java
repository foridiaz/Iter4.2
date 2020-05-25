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

package uniandes.isis2304.parranderos.negocio;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import jdk.internal.org.jline.utils.Log;
import uniandes.isis2304.parranderos.persistencia.PersistenciaParranderos;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 */
public class Parranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Parranderos.class.getName());

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaParranderos pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Parranderos ()
	{
		pp = PersistenciaParranderos.getInstance ();
	}

	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Parranderos (JsonObject tableConfig)
	{
		pp = PersistenciaParranderos.getInstance (tableConfig);
	}

	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}


	/* ****************************************************************
	 * 			Métodos para manejar las RESERVAS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una reserva 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida de la bebida - Debe existir un TIPOBEBIDA con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Reserva adicionarReserva (long id_contrato, int personas, String fecha_inicio, String fecha_fin, String fecha_limite, String fecha_realizacion, String tipo, long id_cliente)
	{
		log.info ("Adicionando reserva");
		Reserva reserva = pp.adicionarReserva(id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
		log.info ("Adicionando reserva: " + reserva);
		return reserva;
	}


	/**
	 * Elimina una bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarReservaPorId (long idReserva)
	{
		log.info ("Eliminando Reserva por id: " + idReserva);
		long resp = pp.eliminarReservaPorId (idReserva);
		log.info ("Eliminando Reserva por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<Reserva> darReservas()
	{
		log.info ("Consultando Reservas");
		List<Reserva> reservas = pp.darReservas();	
		log.info ("Consultando Bebidas: " + reservas.size() + " reservas existentes");
		return reservas;
	}


	public List<VOReserva> darVOReserva ()
	{
		log.info ("Generando los VO de Reserva");        
		List<VOReserva> voReservas = new LinkedList<VOReserva> ();
		for (Reserva tb : pp.darReservas())
		{
			voReservas.add (tb);
		}
		log.info ("Generando los VO de Reserva " + voReservas.size() + " existentes");
		return voReservas;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los CONTRATOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un contrato
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Contrato adicionarContrato (int capacidad , int costo)
	{
		log.info ("Adicionando contrato");
		Contrato contrato = pp.adicionarContrato(capacidad, costo);
		log.info ("Adicionando bebida: " + contrato);
		return contrato;
	}


	/**
	 * Elimina un contrato por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarContratoPorId (long idContrato)
	{
		log.info ("Eliminando bebida por id: " + idContrato);
		long resp = pp.eliminarContratoPorId(idContrato);
		log.info ("Eliminando contrato por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<Contrato> darContratos()
	{
		log.info ("Consultando Contratos");
		List<Contrato> contratos = pp.darContratos();	
		log.info ("Consultando Contratos: " + contratos.size() + " reservas existentes");
		return contratos;
	}


	public List<VOContrato> darVOContrato ()
	{
		log.info ("Generando los VO de Contrato");        
		List<VOContrato> voContratos = new LinkedList<VOContrato> ();
		for (Contrato tb : pp.darContratos())
		{
			voContratos.add (tb);
		}
		log.info ("Generando los VO de Reserva " + voContratos.size() + " existentes");
		return voContratos;
	}
	public List<VOContrato> mostrarPopulares ()
	{
		log.info ("Generando los VO de las Reservas Populares");        
        List<VOContrato> voContrato = new LinkedList<VOContrato> ();
        for (Contrato tb : pp.darPopulares())
        {
        	voContrato.add(tb);
        }
        log.info ("Generando los VO de las Ganancias: " + voContrato.size());
        return voContrato;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los OPERADORES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un operador
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Operador adicionarOperador (String nombre)
	{
		log.info ("Adicionando operador");
		Operador operador = pp.adicionarOperador(nombre);
		log.info ("Adicionando operador: " + operador);
		return operador;
	}


	/**
	 * Elimina un operador por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarOperadorPorId (long idOperador)
	{
		log.info ("Eliminando bebida por id: " + idOperador);
		long resp = pp.eliminarOperadorPorId (idOperador);
		log.info ("Eliminando operador por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<Operador> darOperadores()
	{
		log.info ("Consultando Operadores");
		List<Operador> operadores = pp.darOperadores();	
		log.info ("Consultando Operadores: " + operadores.size() + " reservas existentes");
		return operadores;
	}


	public List<VOOperador> darVOOperador()
	{
		log.info ("Generando los VO de Operador");        
		List<VOOperador> voOperadores = new LinkedList<VOOperador> ();
		for (Operador tb : pp.darOperadores())
		{
			voOperadores.add (tb);
		}
		log.info ("Generando los VO de Operador " + voOperadores.size() + " existentes");
		return voOperadores;
	}
	
	public List<VOGanancia> mostrarGanancias ()
	{
		log.info ("Generando los VO de las Ganancias");        
        List<VOGanancia> voGanancias = new LinkedList<VOGanancia> ();
        for (Ganancia tb : pp.darGanancias ())
        {
        	voGanancias.add(tb);
        }
        log.info ("Generando los VO de las Ganancias: " + voGanancias.size());
        return voGanancias;
	}
	/* ****************************************************************
	 * 			Métodos para manejar los Contrato_Apartamento
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un Contrato_Apartamento
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Contrato_Apartamento adicionarContratoApartamento (long id_apartamento, int meses_contratados)
	{
		log.info ("Adicionando Contrato_Apartamento");
		Contrato_Apartamento contratoApartamento = pp.adicionarContratoApartamento(id_apartamento, meses_contratados);
		log.info ("Adicionando Contrato_Apartamento: " + contratoApartamento);
		return contratoApartamento;
	}


	/**
	 * Elimina un Contrato_Apartamento por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarContratoApartamentoPorId (long idContratoApartamento)
	{
		log.info ("Eliminando Contrato_Apartamento por id: " + idContratoApartamento);
		long resp = pp.eliminarContratoApartamentoPorId (idContratoApartamento);
		log.info ("Eliminando Contrato_Apartamento por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<Contrato_Apartamento> darContratoApartamentos()
	{
		log.info ("Consultando Contrato_Apartamento");
		List<Contrato_Apartamento> contratosApartamento = pp.darContratosApartamento();	
		log.info ("Consultando Contrato_Apartamento: " + contratosApartamento.size() + " contratosApartamento existentes");
		return contratosApartamento;
	}


	public List<VOContrato_Apartamento> darVOContratoApartamento()
	{
		log.info ("Generando los VO de Contrato_Apartamento");        
		List<VOContrato_Apartamento> voContratoApartamento = new LinkedList<VOContrato_Apartamento> ();
		for (Contrato_Apartamento tb : pp.darContratosApartamento())
		{
			voContratoApartamento.add (tb);
		}
		log.info ("Generando los VO de Contrato_Apartamento " + voContratoApartamento.size() + " existentes");
		return voContratoApartamento;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los Contrato_Cliente_Esporadico
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un Contrato_Cliente_Esporadico
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Contrato_Cliente_Esporadico adicionarContratoClienteEsporadico (long id_apartamento, long id_vivienda, int cantidad_noches,
			int costo_base, int costo_seguro, int num_habitaciones, String ubicacion)
	{
		log.info ("Adicionando Contrato_Cliente_Esporadico");
		Contrato_Cliente_Esporadico contratoClienteEsporadico = pp.adicionarContratoClienteEsporadico(id_apartamento, id_vivienda, cantidad_noches, costo_base, costo_seguro, num_habitaciones, ubicacion);
		log.info ("Adicionando Contrato_Cliente_Esporadico: " + contratoClienteEsporadico);
		return contratoClienteEsporadico;
	}


	/**
	 * Elimina un Contrato_Cliente_Esporadico por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarContratoClienteEsporadicoPorId (long idContratoClienteEsporadico)
	{
		log.info ("Eliminando Contrato_Cliente_Esporadico por id: " + idContratoClienteEsporadico);
		long resp = pp.eliminarContratoClienteEsporadicoPorId (idContratoClienteEsporadico);
		log.info ("Eliminando Contrato_Cliente_Esporadico por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<Contrato_Cliente_Esporadico> darContratosClienteEsporadico()
	{
		log.info ("Consultando Contrato_Cliente_Esporadico");
		List<Contrato_Cliente_Esporadico> contratosClienteEsporadico = pp.darContratosClienteEsporadico();	
		log.info ("Consultando Contrato_Cliente_Esporadico: " + contratosClienteEsporadico.size() + " contratosClienteEsporadico existentes");
		return contratosClienteEsporadico;
	}


	public List<VOContrato_Cliente_Esporadico> darVOContratoClienteEsporadico()
	{
		log.info ("Generando los VO de Contrato_Cliente_Esporadico");        
		List<VOContrato_Cliente_Esporadico> voContratoClienteEsporadico = new LinkedList<VOContrato_Cliente_Esporadico> ();
		for (Contrato_Cliente_Esporadico tb : pp.darContratosClienteEsporadico())
		{
			voContratoClienteEsporadico.add (tb);
		}
		log.info ("Generando los VO de Contrato_Cliente_Esporadico " + voContratoClienteEsporadico.size() + " existentes");
		return voContratoClienteEsporadico;
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar los Contrato_Hab_Vivienda
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un Contrato_Hab_Vivienda
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Contrato_Hab_Vivienda adicionarContratoHabVivienda (long id_vivienda, int meses_contratados)
	{
		log.info ("Adicionando Contrato_Hab_Vivienda");
		Contrato_Hab_Vivienda contratoHabVivienda = pp.adicionarContratoHabVivienda(id_vivienda, meses_contratados);
		log.info ("Adicionando Contrato_Hab_Vivienda: " + contratoHabVivienda);
		return contratoHabVivienda;
	}


	/**
	 * Elimina un Contrato_Hab_Vivienda por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarContrato_Hab_ViviendaPorId (long idContratoHabVivienda)
	{
		log.info ("Eliminando Contrato_Hab_Vivienda por id: " + idContratoHabVivienda);
		long resp = pp.eliminarContratoHabViviendaPorId (idContratoHabVivienda);
		log.info ("Eliminando Contrato_Hab_Vivienda por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<Contrato_Hab_Vivienda> darContratosHabVivienda()
	{
		log.info ("Consultando Contrato_Hab_Vivienda");
		List<Contrato_Hab_Vivienda> contratosHabVivienda = pp.darContratosHabVivienda();	
		log.info ("Consultando Contrato_Hab_Vivienda: " + contratosHabVivienda.size() + " contratosHabVivienda existentes");
		return contratosHabVivienda;
	}


	public List<VOContrato_Hab_Vivienda> darVOContratoHabVivienda()
	{
		log.info ("Generando los VO de Contrato_Hab_Vivienda");        
		List<VOContrato_Hab_Vivienda> voContratoHabVivienda = new LinkedList<VOContrato_Hab_Vivienda> ();
		for (Contrato_Hab_Vivienda tb : pp.darContratosHabVivienda())
		{
			voContratoHabVivienda.add (tb);
		}
		log.info ("Generando los VO de Contrato_Hab_Vivienda " + voContratoHabVivienda.size() + " existentes");
		return voContratoHabVivienda;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los ContratoHabHostal
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un ContratoHabHostal
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public ContratoHabHostal adicionarContratoHabHostal (long id_hostal)
	{
		log.info ("Adicionando ContratoHabHostal");
		ContratoHabHostal contratoHabHostal = pp.adicionarContratoHabHostal(id_hostal);
		log.info ("Adicionando ContratoHabHostal: " + contratoHabHostal);
		return contratoHabHostal;
	}


	/**
	 * Elimina un ContratoHabHostal por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idContratoHabHostal- El identificador del contrato a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarContratoHabHostalPorId (long idContratoHabHostal)
	{
		log.info ("Eliminando contratoHabHostal por id: " + idContratoHabHostal);
		long resp = pp.eliminarContratoHabHostalPorId (idContratoHabHostal);
		log.info ("Eliminando ContratoHabHostal por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<ContratoHabHostal> darContratosHabHostal()
	{
		log.info ("Consultando ContratoHab_Viviendas");
		List<ContratoHabHostal> contratosHabHostal = pp.darContratosHabHostal();	
		log.info ("Consultando ContratoHabHostales: " + contratosHabHostal.size() + " contratosHabHostal existentes");
		return contratosHabHostal;
	}


	public List<VOContratoHabHostal> darVOContratoHabHostal()
	{
		log.info ("Generando los VO de ContratoHabHostal");        
		List<VOContratoHabHostal> voContratoHabHostal = new LinkedList<VOContratoHabHostal> ();
		for (ContratoHabHostal tb : pp.darContratosHabHostal())
		{
			voContratoHabHostal.add (tb);
		}
		log.info ("Generando los VO de ContratoHabHostal " + voContratoHabHostal.size() + " existentes");
		return voContratoHabHostal;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los ContratoHabHotel
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un ContratoHabHotel
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public ContratoHabHotel adicionarContratoHabHotel (long id_hotel, int categoria, int tamanio, String tipo_habitacion,
			int ubicacion)
	{
		log.info ("Adicionando ContratoHabHotel");
		ContratoHabHotel contratoHabHotel = pp.adicionarContratoHabHotel(id_hotel, categoria, tamanio, tipo_habitacion, ubicacion);
		log.info ("Adicionando ContratoHabHotel: " + contratoHabHotel);
		return contratoHabHotel;
	}


	/**
	 * Elimina un ContratoHabHotel por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarContratoHabHotelPorId (long idContratoHabHotel)
	{
		log.info ("Eliminando contratoHabHotel por id: " + idContratoHabHotel);
		long resp = pp.eliminarContratoHabHotelPorId (idContratoHabHotel);
		log.info ("Eliminando ContratoHabHotel por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<ContratoHabHotel> darContratosHabHotel()
	{
		log.info ("Consultando ContratoHab_Viviendas");
		List<ContratoHabHotel> contratosHabHotel = pp.darContratosHabHotel();	
		log.info ("Consultando ContratoHabHoteles: " + contratosHabHotel.size() + " contratosHabHotel existentes");
		return contratosHabHotel;
	}


	public List<VOContratoHabHotel> darVOContratoHabHotel()
	{
		log.info ("Generando los VO de ContratoHabHotel");        
		List<VOContratoHabHotel> voContratoHabHotel = new LinkedList<VOContratoHabHotel> ();
		for (ContratoHabHotel tb : pp.darContratosHabHotel())
		{
			voContratoHabHotel.add (tb);
		}
		log.info ("Generando los VO de ContratoHabHotel " + voContratoHabHotel.size() + " existentes");
		return voContratoHabHotel;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los ContratoHabUniversitaria
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un ContratoHabUniversitaria
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public ContratoHabUniversitaria adicionarContratoHabUniversitaria (long id_vivienda, int meses_contratados, String individual,
			int ubicacion, String gimnasio, String restaurante, String sala_esparcimiento, String sala_estudio)
	{
		log.info ("Adicionando ContratoHabUniversitaria");
		ContratoHabUniversitaria contratoHabUniversitaria = pp.adicionarContratoHabUniversitaria(id_vivienda, meses_contratados, individual, ubicacion, gimnasio, restaurante, sala_esparcimiento, sala_estudio);
		log.info ("Adicionando ContratoHabUniversitaria: " + contratoHabUniversitaria);
		return contratoHabUniversitaria;
	}


	/**
	 * Elimina un ContratoHabUniversitaria por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarContratoHabUniversitariaPorId (long idContratoHabUniversitaria)
	{
		log.info ("Eliminando contratoHabUniversitaria por id: " + idContratoHabUniversitaria);
		long resp = pp.eliminarContratoHabUniversitariaPorId (idContratoHabUniversitaria);
		log.info ("Eliminando ContratoHabUniversitaria por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	public List<ContratoHabUniversitaria> darContratosHabUniversitaria()
	{
		log.info ("Consultando ContratoHab_Viviendas");
		List<ContratoHabUniversitaria> contratosHabUniversitaria = pp.darContratosHabUniversitaria();	
		log.info ("Consultando ContratoHabUniversitariaes: " + contratosHabUniversitaria.size() + " contratosHabUniversitaria existentes");
		return contratosHabUniversitaria;
	}


	public List<VOContratoHabUniversitaria> darVOContratoHabUniversitaria()
	{
		log.info ("Generando los VO de ContratoHabUniversitaria");        
		List<VOContratoHabUniversitaria> voContratoHabUniversitaria = new LinkedList<VOContratoHabUniversitaria> ();
		for (ContratoHabUniversitaria tb : pp.darContratosHabUniversitaria())
		{
			voContratoHabUniversitaria.add (tb);
		}
		log.info ("Generando los VO de ContratoHabUniversitaria " + voContratoHabUniversitaria.size() + " existentes");
		return voContratoHabUniversitaria;
	}
	
	/* ****************************************************************
	 * 			Tipos de Usuarios
	 *****************************************************************/
	public List<VOUsosVinculo> mostrarUsosVinculos(){
		log.info ("Generando los VO de los usos por vínculo");        
        List<VOUsosVinculo> voUsos = new LinkedList<VOUsosVinculo>();
        for (UsosVinculo tb : pp.darUsosPorVinculo())
        {
        	voUsos.add(tb);
        }
        log.info ("Generando los VO de los Usos por vínculo: " + voUsos.size());
        return voUsos;
	}
	
	public List<VOIndice> mostrarIndices(){
		log.info ("Generando los VO de los Indices de Ocupación");        
        List<VOIndice> voIndices = new LinkedList<VOIndice>();
        for (Indice tb : pp.darIndices())
        {
        	voIndices.add(tb);
        }
        log.info ("Generando los VO de los indices de Ocupación: " + voIndices.size());
        return voIndices;
	}
	public List<VOContrato> mostrarOfertasConCaracteristicas(List<String> car){
		log.info("Generando las ofertas que cumplen con las características");
		List<VOContrato> ofertas=new LinkedList<>();
		for (Contrato i: pp.darOfertasCar(car)) {
			ofertas.add(i);
		}
		log.info("Se generaron las ofertas con las características");
		return ofertas;
	}
	
	public long deshabilitarOferta(long idCont) {
		log.info ("Deshabilitando Reserva por id: " + idCont);
		long resp = pp.deshabilitarOferta(idCont);
		log.info ("Deshabilitando Reserva por id: " + resp + " Oferta Deshabilitada");
		return resp;
	}
	
	public long habilitarOferta(long idCont) {
		log.info("Habilitando Reserva por id:"+ idCont);
		long resp=pp.habilitarOferta(idCont);
		return resp;
		
	}
	
	public Reserva adicionarReservaColectiva (long id_contrato, int personas, String fecha_inicio, String fecha_fin, String fecha_limite, String fecha_realizacion, String tipo, long id_cliente)
	{
		log.info ("Adicionando reserva colectiva");
		Reserva reservaColectiva = pp.adicionarReserva(id_contrato, personas, fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, id_cliente);
		log.info ("Adicionando reserva colectiva: " + reservaColectiva);
		return reservaColectiva;
	}


	/**
	 * Elimina una bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarReservaColectivaPorId (long idReservaColectiva)
	{
		log.info ("Eliminando Reserva colectiva por id: " + idReservaColectiva);
		long resp = pp.eliminarReservaColectivaPorId (idReservaColectiva);
		log.info ("Eliminando Reserva colectiva por id: " + resp + " tuplas eliminadas");
		return resp;
	}
	/* ****************************************************************
	 * 			Métodos para Consumos 
	 *****************************************************************/
	public List<VOCliente> consultarConsumo1(){
		
	}
	
	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
		log.info ("Limpiando la BD de Parranderos");
		long [] borrrados = pp.limpiarParranderos();	
		log.info ("Limpiando la BD de Parranderos: Listo!");
		return borrrados;
	}
}
