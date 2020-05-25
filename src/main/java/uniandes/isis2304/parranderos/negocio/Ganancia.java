/** Universidad	de	los	Andes	(Bogotá	- Colombia)
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

/**
 * Clase para modelar el concepto BEBIDA del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Ganancia implements VOGanancia{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la bebida
	 */
	private long id;
	
	private String nombre; 
	/**
	 * El nombre de la bebida
	 */
	private int ganancia;
	
	/**
	 * El identificador del tipo de bebida de la bebida. Debe existir en la tabla de tipoBebida
	 */
	private int veces;
	

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Ganancia() 
	{
		super();
		System.out.println("creo");
		this.id = 0;
		this.veces = 0;
		this.nombre = "";
		this.ganancia=0;
	}


	public Ganancia(long id,String nombre,int ganancia, int veces) {
		super();		
		this.id = id;
		this.nombre = nombre;
		this.ganancia = ganancia;
		this.veces= veces; 
	}


	public long getId() {
		System.out.println(id);
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	
	public void setGanancia(int pGanancia) {
		this.ganancia = pGanancia;
	}
	public void setNombre (String pNombre) {
		this.nombre=pNombre;
	}
	public void setVeces (int pVeces) {
		this.veces=pVeces;
	}
	public String toString() {
		return "Operador [id=" + id + ", Nombre=" + nombre + ", ganancias=" + ganancia +", veces="+veces+"]";
	}

	public String getNombre() {
		return nombre;
	}
	public double getGanancia() {
		return ganancia;
	}
	public int getVeces() {
		return veces;
	}

}