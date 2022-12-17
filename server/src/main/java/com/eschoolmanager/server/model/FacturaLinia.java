/**
 * 
 */
package com.eschoolmanager.server.model;

import java.sql.Date;

import javax.persistence.CascadeType;
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
 * Classe persistent per emmagatzemar les linies de factures generades pels estudiants de l'escola
 */
@Entity
@Table(name="FacturaLinia")
public class FacturaLinia {

	private int codi;
	private Factura factura;
	private Date dataIHora;
	private Servei servei;
	private Double importBeca;
	private Double importEstudiant;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public FacturaLinia() {
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Constructor parametritzat: construeix una nova linia de factura amb els paràmetres especificats
     * @param 
     */
	public FacturaLinia(Sessio sessio, Double importBeca, Double importEstudiant, Factura factura) {
		this.setDataIHora(sessio.getDataIHora());
		this.setServei(sessio.getServei());
		this.setFactura(factura);
		this.setImportBeca(importBeca);
		this.setImportEstudiant(importEstudiant);
	}
	
	/**
	 * Obté el codi identificador de la linia
	 * @return codi identificador de la linia
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="codi")
	public int getCodi() {
		return codi;
	}

	/**
	 * Actualitza el codi identificador de la linia
	 * @param codi actualitzat de la linia
	 */
	public void setCodi(int codi) {
		this.codi = codi;
	}

	/**
	 * Obté la factura a la que pertany la línia
	 * @return factura
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="factura_codi", nullable=false)
	public Factura getFactura() {
		return factura;
	}

	/**
	 * Actualitza la factura a la que pertany la línia
	 * @param factura actualitzada
	 */
	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	/**
	 * Obté la data i hora de la sessió facturada
	 * @return dataIHora de la sessió facturada
	 */
	public Date getDataIHora() {
		return dataIHora;
	}

	/**
	 * Actualitza la data i hora de la sessió facturada
	 * @param dataIHora actualitzada de la sessió facturada
	 */
	public void setDataIHora(Date dataIHora) {
		this.dataIHora = dataIHora;
	}

	/**
	 * Obté el servei de la sessió facturada
	 * @return servei de la sessió facturada
	 */
	public Servei getServei() {
		return servei;
	}

	/**
	 * Actualitza el servei de la sessió facturada
	 * @param servei actualitzat de la sessió facturada
	 */
	public void setServei(Servei servei) {
		this.servei = servei;
	}

	/**
	 * Obté l'import imputat a les beques
	 * @return importBeca
	 */
	public Double getImportBeca() {
		return importBeca;
	}

	/**
	 * Actualitza l'import imputat a les beques
	 * @param importBeca actualitzat
	 */
	public void setImportBeca(Double importBeca) {
		this.importBeca = importBeca;
	}

	/**
	 * Obté l'import imputat a l'estudiant
	 * @return importEstudiant
	 */
	public Double getImportEstudiant() {
		return importEstudiant;
	}

	/**
	 * Actualitza l'import imputat a l'estudiant
	 * @param importEstudiant actualitzat
	 */
	public void setImportEstudiant(Double importEstudiant) {
		this.importEstudiant = importEstudiant;
	}
}
