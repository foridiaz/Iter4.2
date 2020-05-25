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
public class Indice implements VOIndice{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la bebida
	 */
	private long id_oferta;
	
	private int capacidad; 
	/**
	 * El nombre de la bebida
	 */
	private int alojadas;
	
	/**
	 * El identificador del tipo de bebida de la bebida. Debe existir en la tabla de tipoBebida
	 */
	private double indice;
	

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Indice() 
	{
		super();
		this.id_oferta = 0;
		this.capacidad = 0;
		this.alojadas = 0;
		this.indice=0;
	}


	public Indice(long id,int pCapacidad,int alojadas, double indice) {
		super();		
		this.id_oferta = id;
		this.capacidad = pCapacidad;
		this.alojadas = alojadas;
		this.indice= indice; 
	}


	
	public String toString() {
		return "Oferta [id_oferta=" + id_oferta + ", capacidad=" + capacidad + ", alojadas=" + alojadas +", indice="+indice+"]";
	}

	public long getId_oferta() {
		return id_oferta;
	}
	public int getCapacidad() {
		return capacidad;
	}

	public int getAlojadas() {
		return alojadas;
	}

	public double getIndice() {
		return indice;
	}
	public void setId_oferta(long pId) {
		this.id_oferta=pId;
	}
	public void setCapacidad(int pCapacidad) {
		this.capacidad=pCapacidad;
	}

	public void setAlojadas(int pAlojadas) {
		this.alojadas=pAlojadas;
	}

	public void setIndice(double pIndice) {
		this.indice=pIndice;
	}

}