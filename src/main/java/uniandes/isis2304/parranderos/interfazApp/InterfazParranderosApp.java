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

package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Contrato;
import uniandes.isis2304.parranderos.negocio.Parranderos;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.VOCliente;
import uniandes.isis2304.parranderos.negocio.VOContrato;
import uniandes.isis2304.parranderos.negocio.VOGanancia;
import uniandes.isis2304.parranderos.negocio.VOIndice;
import uniandes.isis2304.parranderos.negocio.VOOperador;
import uniandes.isis2304.parranderos.negocio.VOReserva;
import uniandes.isis2304.parranderos.negocio.VOUsosVinculo;


/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazParranderosApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazParranderosApp.class.getName());

	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * Asociación a la clase principal del negocio.
	 */
	private Parranderos parranderos;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/**
	 * Objeto JSON con la configuración de interfaz de la app.
	 */
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacción para los requerimientos
	 */
	private PanelDatos panelDatos;

	/**
	 * Menú de la aplicación
	 */
	private JMenuBar menuBar;

	private List<String> caracteristicas;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazParranderosApp( )
	{
		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		System.out.println(tableConfig);
		parranderos = new Parranderos (tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );        
	}

	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * Método para configurar el frame principal de la aplicación
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * Método para crear el menú de la aplicación con base em el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menùs deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menús
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}

	/* ****************************************************************
	 * 			CRUD de TipoBebida
	 *****************************************************************/
	/**
	 * Adiciona un tipo de bebida con la información dada por el usuario
	 * Se crea una nueva tupla de tipoBebida en la base de datos, si un tipo de bebida con ese nombre no existía
	 */
	//    public void adicionarTipoBebida( )
	//    {
	//    	try 
	//    	{
	//    		String nombreTipo = JOptionPane.showInputDialog (this, "Nombre del tipo de bedida?", "Adicionar tipo de bebida", JOptionPane.QUESTION_MESSAGE);
	//    		if (nombreTipo != null)
	//    		{
	//        		VOTipoBebida tb = parranderos.adicionarTipoBebida (nombreTipo);
	//        		if (tb == null)
	//        		{
	//        			throw new Exception ("No se pudo crear un tipo de bebida con nombre: " + nombreTipo);
	//        		}
	//        		String resultado = "En adicionarTipoBebida\n\n";
	//        		resultado += "Tipo de bebida adicionado exitosamente: " + tb;
	//    			resultado += "\n Operación terminada";
	//    			panelDatos.actualizarInterfaz(resultado);
	//    		}
	//    		else
	//    		{
	//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
	//    		}
	//		} 
	//    	catch (Exception e) 
	//    	{
	//			e.printStackTrace();
	//			String resultado = generarMensajeError(e);
	//			panelDatos.actualizarInterfaz(resultado);
	//		}
	//    }

	/**
	 * Consulta en la base de datos los tipos de bebida existentes y los muestra en el panel de datos de la aplicación
	 */
	//    public void listarTipoBebida( )
	//    {
	//    	try 
	//    	{
	//			List <VOTipoBebida> lista = parranderos.darVOTiposBebida();
	//
	//			String resultado = "En listarTipoBebida";
	//			resultado +=  "\n" + listarTiposBebida (lista);
	//			panelDatos.actualizarInterfaz(resultado);
	//			resultado += "\n Operación terminada";
	//		} 
	//    	catch (Exception e) 
	//    	{
	//			e.printStackTrace();
	//			String resultado = generarMensajeError(e);
	//			panelDatos.actualizarInterfaz(resultado);
	//		}
	//    }

	//    /**
	//     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
	//     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
	//     */
	//    public void eliminarTipoBebidaPorId( )
	//    {
	//    	try 
	//    	{
	//    		String idTipoStr = JOptionPane.showInputDialog (this, "Id del tipo de bedida?", "Borrar tipo de bebida por Id", JOptionPane.QUESTION_MESSAGE);
	//    		if (idTipoStr != null)
	//    		{
	//    			long idTipo = Long.valueOf (idTipoStr);
	//    			long tbEliminados = parranderos.eliminarTipoBebidaPorId (idTipo);
	//
	//    			String resultado = "En eliminar TipoBebida\n\n";
	//    			resultado += tbEliminados + " Tipos de bebida eliminados\n";
	//    			resultado += "\n Operación terminada";
	//    			panelDatos.actualizarInterfaz(resultado);
	//    		}
	//    		else
	//    		{
	//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
	//    		}
	//		} 
	//    	catch (Exception e) 
	//    	{
	////			e.printStackTrace();
	//			String resultado = generarMensajeError(e);
	//			panelDatos.actualizarInterfaz(resultado);
	//		}
	//    }
	//
	//    /**
	//     * Busca el tipo de bebida con el nombre indicado por el usuario y lo muestra en el panel de datos
	//     */
	//    public void buscarTipoBebidaPorNombre( )
	//    {
	//    	try 
	//    	{
	//    		String nombreTb = JOptionPane.showInputDialog (this, "Nombre del tipo de bedida?", "Buscar tipo de bebida por nombre", JOptionPane.QUESTION_MESSAGE);
	//    		if (nombreTb != null)
	//    		{
	//    			VOTipoBebida tipoBebida = parranderos.darTipoBebidaPorNombre (nombreTb);
	//    			String resultado = "En buscar Tipo Bebida por nombre\n\n";
	//    			if (tipoBebida != null)
	//    			{
	//        			resultado += "El tipo de bebida es: " + tipoBebida;
	//    			}
	//    			else
	//    			{
	//        			resultado += "Un tipo de bebida con nombre: " + nombreTb + " NO EXISTE\n";    				
	//    			}
	//    			resultado += "\n Operación terminada";
	//    			panelDatos.actualizarInterfaz(resultado);
	//    		}
	//    		else
	//    		{
	//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
	//    		}
	//		} 
	//    	catch (Exception e) 
	//    	{
	//			e.printStackTrace();
	//			String resultado = generarMensajeError(e);
	//			panelDatos.actualizarInterfaz(resultado);
	//		}
	//    }


	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		//		try 
		//		{
		//    		// Ejecución de la demo y recolección de los resultados
		//			long eliminados [] = parranderos.limpiarParranderos();
		//			
		//			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		//			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
		//			resultado += eliminados [0] + " Gustan eliminados\n";
		//			resultado += eliminados [1] + " Sirven eliminados\n";
		//			resultado += eliminados [2] + " Visitan eliminados\n";
		//			resultado += eliminados [3] + " Bebidas eliminadas\n";
		//			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
		//			resultado += eliminados [5] + " Bebedores eliminados\n";
		//			resultado += eliminados [6] + " Bares eliminados\n";
		//			resultado += "\nLimpieza terminada";
		//   
		//			panelDatos.actualizarInterfaz(resultado);
		//		} 
		//		catch (Exception e) 
		//		{
		////			e.printStackTrace();
		//			String resultado = generarMensajeError(e);
		//			panelDatos.actualizarInterfaz(resultado);
		//		}
	}

	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}

	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}

	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}

	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}

	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}

	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}

	/**
	 * Muestra la información acerca del desarrollo de esta apicación
	 */
	public void acercaDe ()
	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Parranderos Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Germán Bravo\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia Jiménez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}


	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
	/**
	 * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
	 * @param lista - La lista con los tipos de bebida
	 * @return La cadena con una líea para cada tipo de bebida recibido
	 */
	//    private String listarTiposBebida(List<VOTipoBebida> lista) 
	//    {
	//    	String resp = "Los tipos de bebida existentes son:\n";
	//    	int i = 1;
	//        for (VOTipoBebida tb : lista)
	//        {
	//        	resp += i++ + ". " + tb.toString() + "\n";
	//        }
	//        return resp;
	//	}

	/**
	 * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
	 * @param e - La excepción recibida
	 * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
			//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
	/**
	 * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
	 * Invoca al método correspondiente según el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );	
		System.out.println(pEvento);
		try 
		{
			Method req = InterfazParranderosApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}


	/* ****************************************************************
	 * 			CRUD de RESERVA
	 *****************************************************************/
	/**
	 * Adiciona un tipo de bebida con la información dada por el usuario
	 * Se crea una nueva tupla de tipoBebida en la base de datos, si un tipo de bebida con ese nombre no existía
	 */
	public void adicionarReserva( )
	{
		try 
		{	
			VentanaChecks check=new VentanaChecks(this);
			List<VOContrato> contratos=parranderos.mostrarOfertasConCaracteristicas(caracteristicas);
			String resultado1 = "Mostrar Contratos que cumplen con las características";
			resultado1 +=  "\n" + listarContratos(contratos);
			panelDatos.actualizarInterfaz(resultado1);
			resultado1 += "\n Operación terminada";
			listarContratos(contratos);
			String persona=JOptionPane.showInputDialog(this, "Número de personas", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
			String fecha_inicio= JOptionPane.showInputDialog(this, "Fecha de Inicio(dd/MM/YYYY hh:mm:ss PM/AM)", "Adicionar Reserva" ,JOptionPane.QUESTION_MESSAGE);
			String fecha_fin=JOptionPane.showInputDialog(this, "Fecha de Finalización(dd/MM/YYYY hh:mm:ss PM/AM)", "Adicionar Reserva" ,JOptionPane.QUESTION_MESSAGE);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");  
			LocalDateTime actual = LocalDateTime.now();
			String fecha_realizacion=dtf.format(actual);
			String fecha_limite=fecha_realizacion; 
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Calendar c=Calendar.getInstance(); 
			c.setTime(sdf.parse(fecha_limite));
			c.add(Calendar.DATE,3);
			fecha_limite=sdf.format(c.getTime());
			String tipostr=JOptionPane.showInputDialog(this,"Tipo de oferta", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
			String tipo=null;
			if(tipostr.equalsIgnoreCase("hotel")) {
				tipo="HOTEL"; 
			}
			else if (tipostr.equalsIgnoreCase("hostal")) {
				tipo="HOSTAL";
			}
			else if (tipostr.equalsIgnoreCase("vivienda_universitaria")) {
				tipo="VIVIENDA_UNIVERSITARIA";
			}
			else if (tipostr.equalsIgnoreCase("vivienda_familiar")) {
				tipo="VIVIENDA_FAMILIAR";
			}
			else if (tipostr.equalsIgnoreCase("apartamento")) {
				tipo="APARTAMENTO";
			}
			else if (tipostr.equalsIgnoreCase("cliente_esporádico")) {
				tipo="CLIENTE_ESPORADICO";
			}
			String cliente=JOptionPane.showInputDialog(this,"ID cliente", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
			String contrato = JOptionPane.showInputDialog (this, "Identificador del contrato?", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
			if(contrato!=null && fecha_inicio!=null&&fecha_fin!=null&&persona!=null&&tipo!=null && cliente!=null) {
				VOReserva tb = parranderos.adicionarReserva(Integer.parseInt(contrato), Integer.parseInt(persona),fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, Integer.parseInt(cliente));
				if (tb == null)
				{
					panelDatos.actualizarInterfaz("No se pudo crear la reserva");
				}
				else {
					String resultado = "En adicionarReserva\n\n";
					resultado += "Reserva adicionada exitosamente: " + tb;
					resultado += "\n Operación terminada";
					panelDatos.actualizarInterfaz(resultado);
				}
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void eliminarReservaPorId( )
	{
		try 
		{
			String id = JOptionPane.showInputDialog (this, "Id de la reserva", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
			if (id != null)
			{
				long idRes = Long.valueOf (id);
				long tbEliminados = parranderos.eliminarReservaPorId(idRes);

				String resultado = "En eliminar Reserva por ID\n\n";
				resultado += tbEliminados + " Reserva Eliminada \n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/* ****************************************************************
	 * 			CRUD de OFERTA
	 *****************************************************************/
	public void eliminarOfertaPorId( )
	{
		try 
		{
			String id = JOptionPane.showInputDialog (this, "Id de la Oferta", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
			if (id != null)
			{
				long idCont = Long.valueOf (id);
				long tbEliminados = parranderos.eliminarContratoPorId(idCont);
				String resultado = "En eliminar Oferta por Id\n\n";
				resultado += tbEliminados + " Oferta eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void mostrarGanancias() {
		try 
		{
			List <VOGanancia> lista = parranderos.mostrarGanancias();

			String resultado = "Mostrar Ganancias Operadores";
			resultado +=  "\n" + listarGanancias (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarGanancias(List<VOGanancia> lista) 
	{
		String resp = "Los tipos de bebida existentes son:\n";
		int i = 1;
		for (VOGanancia tb : lista)
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	public void mostrarPopulares() {
		try 
		{
			List <VOContrato> lista = parranderos.mostrarPopulares();

			String resultado = "Mostrar Ofertas más populares";
			resultado +=  "\n" + listarContratos (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	private String listarContratos(List<VOContrato> lista) 
	{
		String resp = "Los Contratos con las características son:\n";
		int i = 1;
		for (VOContrato tb : lista)
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}





	public void mostrarPorVinculo() {
		try 
		{

			List <VOUsosVinculo> lista = parranderos.mostrarUsosVinculos();

			String resultado = "Mostrar Ganancias Operadores";
			resultado +=  "\n" + listarVinculos(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarVinculos(List<VOUsosVinculo> lista) 
	{
		String resp = "Los usos por tipos de usuario son:\n";
		int i = 1;
		for (VOUsosVinculo tb : lista)
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	public void mostrarIndices() {
		try 
		{
			List <VOIndice> lista = parranderos.mostrarIndices();

			String resultado = "Mostrar Ganancias Operadores";
			resultado +=  "\n" + listarIndices(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	private String listarIndices(List<VOIndice> lista) 
	{
		String resp = "Los usos por tipos de usuario son:\n";
		int i = 1;
		for (VOIndice tb : lista)
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	public void deshabilitarOferta() {
		try 
		{
			String id = JOptionPane.showInputDialog (this, "Id de la Oferta a deshabilitar", "Deshabilitar Oferta", JOptionPane.QUESTION_MESSAGE);
			if (id != null)
			{
				long idCont = Long.valueOf (id);
				long tbDeshabilitada = parranderos.deshabilitarOferta(idCont);
				String resultado = "Deshabilitando Oferta por Id\n\n";
				resultado += tbDeshabilitada + " Oferta Deshabilitada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}




	public void habilitarOferta() {
		try 
		{
			String id = JOptionPane.showInputDialog (this, "Id de la Oferta a Habilitar", "Habilitar Oferta", JOptionPane.QUESTION_MESSAGE);
			if (id != null)
			{
				long idCont = Long.valueOf (id);
				long tbHabilitada = parranderos.habilitarOferta(idCont);
				String resultado = "Hhabilitando Oferta por Id\n\n";
				if (tbHabilitada!=0) {
					resultado += tbHabilitada + " Oferta Habilitada\n";
					resultado += "\n Operación terminada";
					panelDatos.actualizarInterfaz(resultado);
				}else {
					resultado+=" La Oferta no se pudo habilitar";
					resultado+=" La Oferta ya estaba habilitada";
					panelDatos.actualizarInterfaz(resultado);
				}
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	public void adicionarReservaColectiva( )
	{
		try 
		{
			VentanaChecks check=new VentanaChecks(this);
			List<VOContrato> contratos=parranderos.mostrarOfertasConCaracteristicas(caracteristicas);
			String resultado1 = "Mostrar Contratos que cumplen con las características";
			resultado1 +=  "\n" + listarContratos(contratos);
			panelDatos.actualizarInterfaz(resultado1);
			resultado1 += "\n Operación terminada";
			listarContratos(contratos);
			String contrato = JOptionPane.showInputDialog (this, "Identificador del contrato?", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
			String persona=JOptionPane.showInputDialog(this, "Número de personas", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
			String fecha_inicio= JOptionPane.showInputDialog(this, "Fecha de Inicio(dd/MM/YYYY hh:mm:ss PM/AM)", "Adicionar Reserva" ,JOptionPane.QUESTION_MESSAGE);
			String fecha_fin=JOptionPane.showInputDialog(this, "Fecha de Finalización(dd/MM/YYYY hh:mm:ss PM/AM)", "Adicionar Reserva" ,JOptionPane.QUESTION_MESSAGE);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");  
			LocalDateTime actual = LocalDateTime.now();
			String fecha_realizacion=dtf.format(actual);
			String fecha_limite=fecha_realizacion; 
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Calendar c=Calendar.getInstance(); 
			c.setTime(sdf.parse(fecha_limite));
			c.add(Calendar.DATE,3);
			fecha_limite=sdf.format(c.getTime());
			String tipostr=JOptionPane.showInputDialog(this,"Tipo de oferta", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
			String tipo=null;
			if(tipostr.equalsIgnoreCase("hotel")) {
				tipo="HOTEL"; 
			}
			else if (tipostr.equalsIgnoreCase("hostal")) {
				tipo="HOSTAL";
			}
			else if (tipostr.equalsIgnoreCase("vivienda_universitaria")) {
				tipo="VIVIENDA_UNIVERSITARIA";
			}
			else if (tipostr.equalsIgnoreCase("vivienda_familiar")) {
				tipo="VIVIENDA_FAMILIAR";
			}
			else if (tipostr.equalsIgnoreCase("apartamento")) {
				tipo="APARTAMENTO";
			}
			else if (tipostr.equalsIgnoreCase("cliente_esporádico")) {
				tipo="CLIENTE_ESPORADICO";
			}
			String cliente=JOptionPane.showInputDialog(this,"ID cliente", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
			if(contrato!=null && fecha_inicio!=null&&fecha_fin!=null&&persona!=null&&tipo!=null && cliente!=null) {
				VOReserva tb = parranderos.adicionarReserva(Integer.parseInt(contrato), Integer.parseInt(persona),fecha_inicio, fecha_fin, fecha_limite, fecha_realizacion, tipo, Integer.parseInt(cliente));
				if (tb == null)
				{
					throw new Exception ("No se pudo crear la Reserva: ");
				}
				String resultado = "En adicionarReserva\n\n";
				resultado += "Reserva adicionada exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void eliminarReservaColectivaPorId( )
	{
		try 
		{
			String id = JOptionPane.showInputDialog (this, "Id de la reserva colectiva", "Borrar reserva colectiva por Id", JOptionPane.QUESTION_MESSAGE);
			if (id != null)
			{
				long idRes = Long.valueOf (id);
				long tbEliminados = parranderos.eliminarReservaColectivaPorId(idRes);

				String resultado = "En eliminar Reserva por ID\n\n";
				resultado += tbEliminados + " Reserva colectiva eliminada \n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void consultarConsumo1()
	{
		try 
		{
			String tipoCliente = JOptionPane.showInputDialog (this, "Ingrese Administrador o Proveedor", "Tipo de cliente", JOptionPane.QUESTION_MESSAGE);
			String fecha_inicio= JOptionPane.showInputDialog(this, "Fecha de Inicio(dd/MM/YYYY hh:mm:ss PM/AM)", "Adicionar Reserva" ,JOptionPane.QUESTION_MESSAGE);
			String fecha_fin=JOptionPane.showInputDialog(this, "Fecha de Finalización(dd/MM/YYYY hh:mm:ss PM/AM)", "Adicionar Reserva" ,JOptionPane.QUESTION_MESSAGE);
			String clasificacion=JOptionPane.showInputDialog (this, "Ingrese clasificación: Cliente, Oferta o Tipo", "Clasificación", JOptionPane.QUESTION_MESSAGE);
			if (fecha_fin==null || fecha_inicio==null||tipoCliente==null||clasificacion==null) {
				throw new Exception("Hubo un error con los datos ingresados"); 
			}
			if (tipoCliente.equalsIgnoreCase("Administrador")) {
				if (clasificacion.equalsIgnoreCase("Oferta")) {
					List<Contrato> contratos=parranderos.darContratos();
					String resultado="";
					for (int i=0;i<contratos.size();i++) {
						long IdOf=contratos.get(i).getId();
						List<VOCliente> lista_clientes=parranderos.consultarConsumoOferta1(IdOf,fecha_inicio, fecha_fin);
						if (lista_clientes.size()>0) {
							resultado+="ID OFERTA:"+contratos.get(i).getId()+"\n";
							resultado+=listarConsumos(lista_clientes);
						}
					}
					panelDatos.actualizarInterfaz(resultado);
				}
				else if (clasificacion.equalsIgnoreCase("Cliente")) {
					List<Cliente> clientes =parranderos.darClientes();
					String resultado=""; 
					for (int i=0;i<clientes.size();i++) {
						long IdCli=clientes.get(i).getId();
						List<VOContrato> reservas_cliente=parranderos.consultarConsumoCliente1(IdCli, fecha_inicio, fecha_fin);
						if (reservas_cliente.size()>0) {
							resultado+=clientes.get(i).toString()+"\n"; 
							resultado+=listarContratos(reservas_cliente);
						}
					}
					panelDatos.actualizarInterfaz(resultado);
				}
				else if (clasificacion.equalsIgnoreCase("Tipo")) {
					String resultado="";
					ArrayList<String> alojamientos=new ArrayList<>();
					alojamientos.add("Hotel"); 
					alojamientos.add("Hostal"); 
					alojamientos.add("Hab Vivienda"); 
					alojamientos.add("Apartamento"); 
					alojamientos.add("Cliente esporádico"); 
					alojamientos.add("Vivienda Universitaria"); 
					for (int i=0; i<alojamientos.size();i++) {
						List<VOCliente> lista_clientes=parranderos.consultarConsumoTipo1(fecha_inicio, fecha_fin,alojamientos.get(i)); 
						if (lista_clientes.size()>0) {
							resultado+=alojamientos.get(i)+"\n";
							resultado+=listarConsumos(lista_clientes);
						}
					}
					panelDatos.actualizarInterfaz(resultado);
				}
			}
			else if (tipoCliente.equals("Proveedor")) {
				String Id=JOptionPane.showInputDialog (this, "Ingrese el Id del Operador", "Id Operador", JOptionPane.QUESTION_MESSAGE);
				long IdOp=Long.valueOf(Id);
				String tipo=JOptionPane.showInputDialog (this, "Ingrese el tipo de Alojamiento que ofrece\n (HOTEL,HOSTAL,HAB_VIVIENDA,APARTAMENTO,VIVIENDA_UNIVERSITARIA,ESPORADICO)", "Id Operador", JOptionPane.QUESTION_MESSAGE);
				if (clasificacion.equals("Oferta")||clasificacion.equals("Tipo")) {
					List<Contrato> contratos=parranderos.darContratosProveedor(IdOp,tipo); 
					String resultado="";
					for (int i=0; i<contratos.size();i++) {
						long IdOf=contratos.get(i).getId();
						List<VOCliente> lista_clientes=parranderos.consultarConsumoOferta1(IdOf,fecha_inicio, fecha_fin);
						if (lista_clientes.size()>0) {
							resultado+="ID OFERTA:"+contratos.get(i).getId()+"\n";
							resultado+=listarConsumos(lista_clientes);
						}
					}
					panelDatos.actualizarInterfaz(resultado);
				}
				else if (clasificacion.equals("Cliente")) {
					List<Cliente> clientes =parranderos.darClientes();
					List<Contrato> contratos=parranderos.darContratosProveedor(IdOp, tipo);
					String resultado=""; 
					for (int i=0;i<clientes.size();i++) {
						long IdCli=clientes.get(i).getId();
						for (int j=0;j<contratos.size();j++) {
							List<VOContrato> reservas_cliente=parranderos.consultarConsumoCliente11(IdCli,contratos.get(j).getId(), fecha_inicio, fecha_fin);
							if (reservas_cliente.size()>0){
								resultado+=clientes.get(i).toString()+"\n"; 
								resultado+=listarContratos(reservas_cliente);}
						}
					}
					panelDatos.actualizarInterfaz(resultado);
				}
			}

		}
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void consultarConsumo2() {
		try {
			String tipoCliente = JOptionPane.showInputDialog (this, "Ingrese Administrador o Proveedor", "Tipo de cliente", JOptionPane.QUESTION_MESSAGE);
			String fecha_inicio= JOptionPane.showInputDialog(this, "Fecha de Inicio(dd/MM/YYYY hh:mm:ss PM/AM)", "Adicionar Reserva" ,JOptionPane.QUESTION_MESSAGE);
			String fecha_fin=JOptionPane.showInputDialog(this, "Fecha de Finalización(dd/MM/YYYY hh:mm:ss PM/AM)", "Adicionar Reserva" ,JOptionPane.QUESTION_MESSAGE);
			String clasificacion=JOptionPane.showInputDialog (this, "Ingrese clasificación: Cliente, Oferta o Tipo", "Clasificación", JOptionPane.QUESTION_MESSAGE);
			if (fecha_fin==null || fecha_inicio==null||tipoCliente==null||clasificacion==null) {
				throw new Exception("Hubo un error con los datos ingresados"); 
			}
			if (tipoCliente.equalsIgnoreCase("Administrador")) {
				if (clasificacion.equalsIgnoreCase("Oferta")) {
					List<Contrato> contratos=parranderos.darContratos();
					String resultado="";
					panelDatos.actualizarInterfaz("Realizando Transacción");
					for (int i=0;i<contratos.size();i++) {
						long IdOf=contratos.get(i).getId();
						List<VOCliente> lista_clientes=parranderos.consultarConsumoOferta2(IdOf,fecha_inicio, fecha_fin);
						if (lista_clientes.size()>0) {
							resultado+="ID OFERTA:"+contratos.get(i).getId()+"\n";
							resultado+=listarConsumos(lista_clientes);
						}
					}
					panelDatos.actualizarInterfaz(resultado);
				}
				else if (clasificacion.equals("Cliente")) {
					List<Cliente> clientes =parranderos.darClientes();
					String resultado=""; 
					for (int i=0;i<clientes.size();i++) {
						long IdCli=clientes.get(i).getId();
						List<VOContrato> reservas_cliente=parranderos.consultarConsumoCliente2(IdCli, fecha_inicio, fecha_fin);
						if (reservas_cliente.size()>0) {
							resultado+=clientes.get(i).toString()+"\n"; 
							resultado+=listarContratos(reservas_cliente);
						}
					}
					panelDatos.actualizarInterfaz(resultado);
				}
				else if (clasificacion.equalsIgnoreCase("Tipo")) {
					String resultado="";
					ArrayList<String> alojamientos=new ArrayList<>();
					alojamientos.add("Hotel"); 
					alojamientos.add("Hostal"); 
					alojamientos.add("Hab Vivienda"); 
					alojamientos.add("Apartamento"); 
					alojamientos.add("Cliente esporádico"); 
					alojamientos.add("Vivienda Universitaria"); 
					for (int i=0; i<alojamientos.size();i++) {
						List<VOCliente> lista_clientes=parranderos.consultarConsumoTipo2(fecha_inicio, fecha_fin,alojamientos.get(i)); 
						if (lista_clientes.size()>0) {
							resultado+=alojamientos.get(i)+"\n";
							resultado+=listarConsumos(lista_clientes);
						}
					}
					panelDatos.actualizarInterfaz(resultado);
				}

			}
			else if (tipoCliente.equalsIgnoreCase("Proveedor")) {
				String Id=JOptionPane.showInputDialog (this, "Ingrese el Id del Operador", "Id Operador", JOptionPane.QUESTION_MESSAGE);
				long IdOp=Long.valueOf(Id);
				String tipo=JOptionPane.showInputDialog (this, "Ingrese el tipo de Alojamiento que ofrece\n (HOTEL,HOSTAL,HAB_VIVIENDA,APARTAMENTO,VIVIENDA_UNIVERSITARIA,ESPORADICO)", "Id Operador", JOptionPane.QUESTION_MESSAGE);
				if (clasificacion.equals("Oferta")||clasificacion.equals("Tipo")||clasificacion.equals("Cliente")) {
					List<Contrato> contratos=parranderos.darContratosProveedor(IdOp,tipo); 
					String resultado="";
					for (int i=0; i<contratos.size();i++) {
						long IdOf=contratos.get(i).getId();
						List<VOCliente> lista_clientes=parranderos.consultarConsumoOfertaP(IdOf, fecha_inicio, fecha_fin);
						if (lista_clientes.size()>0) {
							resultado+="ID OFERTA:"+contratos.get(i).getId()+"\n";
							resultado+=listarConsumos(lista_clientes);
						}else {
							resultado+="ID OFERTA:"+contratos.get(i).getId()+"\n";
							resultado+="No se encontró ningún cliente \n";
						}
					}
					panelDatos.actualizarInterfaz(resultado);
				}
			}

		}catch(Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarConsumos(List<VOCliente> lista) 
	{
		String resp = "Los consumos de los clientes son:\n";
		int i = 1;
		for (VOCliente tb : lista)
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarReservas(List<VOReserva> lista) {
		String resp="Las reservas de los clientes son:\n"; 
		int i=1;
		for (VOReserva tb : lista)
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	public void buenosClientes() {
		try {
			String strPrecio=JOptionPane.showInputDialog (this, "Ingrese Precio Costoso", "Precio Costoso", JOptionPane.QUESTION_MESSAGE);
			int costoso=Integer.parseInt(strPrecio);
			String resultado=""; 
			resultado+="Una vez al mes: \n";
			List<VOCliente> lista_una_vez=parranderos.darBuenosClientesUnaVez();
			if (lista_una_vez.size()>0) {
				resultado+=listarClientes(lista_una_vez);
			}else {
				resultado+="No hay ningún buen cliente por este criterio \n";
			}
			resultado+="Alojamientos costosos: \n";
			
			List<VOCliente> lista_costosos=parranderos.darBuenosClientesCostosos(costoso);
			if (lista_costosos.size()>0) {
				resultado+=listarClientes(lista_costosos);
			}else {
				resultado+="No hay ningún buen cliente por este criterio \n";
			}
			resultado+="Siempre reservan Suite: \n"; 
			List<VOCliente> lista_suites=parranderos.darBuenosClientesSuite();
			if (lista_suites.size()>0) {
				resultado+=listarClientes(lista_suites);
			}else {
				resultado+="No hay ningún buen cliente por este criterio \n";
			}
			panelDatos.actualizarInterfaz(resultado);
		}catch(Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	private String listarClientes(List<VOCliente> lista) 
	{
		String resp = "Los clientes son \n";
		int i = 1;
		for (VOCliente tb : lista)
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}


	//    public void mostrarUsoUsuario() {
	//    	try 
	//    	{
	//    		String id_cliente = JOptionPane.showInputDialog (this, "Id del cliente", "Mostrar Uso de Alohandes", JOptionPane.QUESTION_MESSAGE);
	//    		if (id_cliente!=null){
	//    			List <VOIndice> lista = parranderos.mostrarUsoUsuario();
	//
	//    			String resultado = "Mostrar Uso del Operador";
	//    			resultado +=  "\n" + listarIndices(lista);
	//    			panelDatos.actualizarInterfaz(resultado);
	//    			resultado += "\n Operación terminada";	
	//    		}
	//    		else
	//    		{
	//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
	//    		}
	//		} 
	//    	catch (Exception e) 
	//    	{
	////			e.printStackTrace();
	//			String resultado = generarMensajeError(e);
	//			panelDatos.actualizarInterfaz(resultado);
	//		}
	//    }


	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{
			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazParranderosApp interfaz = new InterfazParranderosApp( );
			interfaz.setVisible( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
	public void setCaracteristicas(ArrayList<String> car) {
		caracteristicas=(List<String>)car;
	}
}
