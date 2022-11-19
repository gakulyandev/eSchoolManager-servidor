/**
 * 
 */
package com.eschoolmanager.server.model;

import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar les factures generades pels estudiants de l'escola
 */
@Entity
@Table(name="Factura")
public class Factura {
	
	private int codi;
	private String dades;
	private Date dataGeneracio;
	private Estudiant estudiant;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Factura() {
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Constructor parametritzat: construeix una nova factura amb els paràmetres especificats
     * @param dades de l'factura 
     * @param estudiant per qui es genera la factura
     */
	public Factura(String dades, Estudiant estudiant) {
        this.setDades(dades);
        this.setEstudiant(estudiant);
        this.setDataGeneracio(new Date(Calendar.LONG));
	}
	
	/**
	 * Obté el codi identificador de l'factura
	 * @return codi identificador de l'factura
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="codi")
	public int getCodi() {
		return codi;
	}

	/**
	 * Actualitza el codi identificador de la factura
	 * @param codi actualitzat de la factura
	 */
	public void setCodi(int codi) {
		this.codi = codi;
	}

	/**
	 * Obté les dades de l'factura
	 * @return dades de l'factura
	 */
	@Column(name="dades")
	public String getDades() {
		return dades;
	}

	/**
	 * Actualitza les dades de la factura
	 * @param dades actualitzades de la factura
	 */
	public void setDades(String dades) {
		this.dades = dades;
	}

	/**
	 * Obté la data en que es genera la factura
	 * @return dataGeneracio de la factura
	 */
	@Column(name="data_generacio")
	public Date getDataGeneracio() {
		return dataGeneracio;
	}

	/**
	 * Actualitza la data de generació de la factura
	 * @param dataGeneracio actualitzada de la factura
	 */
	public void setDataGeneracio(Date dataGeneracio) {
		this.dataGeneracio = dataGeneracio;
	}

	/**
	 * Obté l'estudiant per qui s'ha generat la factura
	 * @return estudiant per qui s'ha generat la factura
	 */
	@ManyToOne
    @JoinColumn(name="estudiant_codi", nullable=false)
	public Estudiant getEstudiant() {
		return estudiant;
	}

	/**
	 * Actualitza l'estudiant per qui s'ha generat la factura
	 * @param estudiant actualitzat de la factura
	 */
	public void setEstudiant(Estudiant estudiant) {
		this.estudiant = estudiant;
	}

}
