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
public class Contrato implements VOContrato{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la bebida
	 */
	private long id;
	
	/**
	 * El nombre de la bebida
	 */
	private int capacidad;
	
	/**
	 * El identificador del tipo de bebida de la bebida. Debe existir en la tabla de tipoBebida
	 */
	private int costo;
	
	private String habilitada;
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Contrato() 
	{
		this.id = 0;
		this.capacidad = 0;
		this.costo = 0;
		this.habilitada="";
		
	}


	public Contrato(long id, int capacidad, int costo,String habilitada) {
		super();
		this.id = id;
		this.capacidad = capacidad;
		this.costo = costo;
		this.habilitada=habilitada;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public int getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public void setHabilitada(String habilitada) {
		this.habilitada=habilitada;
	}
	public String getHabilitada() {
		return this.habilitada;
	}

	@Override
	public String toString() {
		return "Contrato [id=" + id + ", capacidad=" + capacidad + ", costo=" + costo + "]";
	}


	public int getCosto() {
		return costo;
	}


	public void setCosto(int costo) {
		this.costo = costo;
	}

}