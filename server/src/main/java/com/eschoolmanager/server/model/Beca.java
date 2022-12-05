/**
 * 
 */
package com.eschoolmanager.server.model;

import java.sql.Date;
import java.util.Calendar;

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
 * Classe persistent per emmagatzemar les beques dels estudiants
 */
@Entity
@Table(name="Beca")
public class Beca {

	private int codi;
	private String adjudicant;
	private double importInicial;
	private double importRestant;
	private Estudiant estudiant;
	private Servei servei;
	private Date dataAdjudicacio;
	private Escola escola;
	private boolean finalitzada = false;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Beca() {
	}
	
	/**
     * Constructor parametritzat: construeix una nova beca amb els paràmetres especificats
	 * @param adjudicant de la beca
	 * @param importInicial de la beca
	 * @param estudiant a qui s'adjudica la beca
	 * @param servei sobre el que s'adjudica la beca
	 */
	public Beca(String adjudicant, double importInicial, Estudiant estudiant, Servei servei) {
		this.setAdjudicant(adjudicant);
		this.setImportInicial(importInicial);
		this.setImportRestant(importInicial);
		this.setEstudiant(estudiant);
		estudiant.adjudicaBeca(this);
		this.setServei(servei);
		servei.assignaBeca(this);
		this.setFinalitzada(false);
		this.setDataAdjudicacio(new Date(Calendar.LONG));
	}

	/**
	 * Obté el codi identificador de la beca
	 * @return the codi identificador de la beca
	 */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codi")
	public int getCodi() {
		return codi;
	}

	/**Actualitza el codi identificador de la beca
	 * @param codi actualitzat de la beca
	 */
	public void setCodi(int codi) {
		this.codi = codi;
	}

	/**
	 * Obté l'escola a on és el departament
	 * @return escola a on és el departament
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="escola_codi")
	public Escola getEscola() {
		return escola;
	}

	/**
	 * Actualitza l'escola a on és el departament
	 * @param escola actualitzada del departament
	 */
	public void setEscola(Escola escola) {
		this.escola = escola;
	}

	/**
	 * Obté el nom de l'adjudicant de la beca
	 * @return adjudicant de la beca
	 */
	@Column(name="adjudicant")
	public String getAdjudicant() {
		return adjudicant;
	}

	/**
	 * Actualitza el nom de l'adjudicant de la beca
	 * @param adjudicant actualitzat de la beca
	 */
	public void setAdjudicant(String adjudicant) {
		this.adjudicant = adjudicant;
	}

	/**
	 * Obté l'import inicial de la beca
	 * @return importInicial de la beca
	 */
	@Column(name="import_inicial")
	public double getImportInicial() {
		return importInicial;
	}

	/**
	 * Actualitza l'import inicial de la beca
	 * @param importInicial actualitzat de la beca
	 */
	public void setImportInicial(double importInicial) {
		this.importInicial = importInicial;
	}

	/**
	 * Obté l'import pendent de consumir de la beca
	 * @return importRestant de la beca
	 */
	@Column(name="import_restant")
	public double getImportRestant() {
		return importRestant;
	}

	/**
	 * Actualitza l'import pendent de consumir de la beca
	 * @param importRestant actualitzat de la beca
	 */
	public void setImportRestant(double importRestant) {
		this.importRestant = importRestant;
	}

	/**
	 * Obté l'estudiant a qui s'adjudica la beca
	 * @return estudiant a qui s'adjudica la beca
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="estudiant_codi", nullable=false)
	public Estudiant getEstudiant() {
		return estudiant;
	}

	/**
	 * Actualitza l'estudiant a qui s'adjudica la beca
	 * @param estudiant actualitzat de la beca
	 */
	public void setEstudiant(Estudiant estudiant) {
		this.estudiant = estudiant;
	}

	/**
	 * Obté el servei sobre el que s'adjudica la beca
	 * @return servei sobre el que s'adjudica la beca
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="servei_codi", nullable=false)
	public Servei getServei() {
		return servei;
	}

	/**
	 * Actualitza el servei sobre el que s'adjudica la beca
	 * @param servei actualitzat de la beca
	 */
	public void setServei(Servei servei) {
		this.servei = servei;
	}

	/**
	 * Obté la data en que s'adjudica la beca
	 * @return dataAdjudicacio de la beca
	 */
	@Column(name="data_adjudicacio")
	public Date getDataAdjudicacio() {
		return dataAdjudicacio;
	}

	/**
	 * Actualitza la data d'adjudicació de la beca
	 * @param dataAdjudicacio actualitzada de la beca
	 */
	public void setDataAdjudicacio(Date dataAdjudicacio) {
		this.dataAdjudicacio = dataAdjudicacio;
	}

	/**
	 * Obté l'estat actual de la beca
	 * @return finalitzada true | false segons si està finalitzada o no la beca
	 */
	@Column(name="finalitzada")
	public boolean isFinalitzada() {
		return finalitzada;
	}

	/**
	 * Obté l'estat actual de la beca
	 * @param estat actualitzat de la beca
	 */
	public void setFinalitzada(boolean finalitzada) {
		this.finalitzada = finalitzada;
	}

	/**
	 * Actualitza les dades de la beca
     * @param adjudicant actualitzat de la beca
     * @param import inicial actualitzat de la beca
     * @param l'estudiant actualitzat
     * @param servei actualitzat
	 */
	public void actualitza(String adjudicant, Double importInicial, Estudiant estudiant, Servei servei) {
		this.setAdjudicant(adjudicant);
		if (this.importRestant == this.importInicial) {
			this.setImportRestant(importInicial);
		}
		this.setImportInicial(importInicial);
			
		this.setEstudiant(estudiant);
		estudiant.adjudicaBeca(this);
		this.setServei(servei);
		servei.assignaBeca(this);
	}
	
	/**
	 * Imputa el servei impartit a la beca una vegada es crea una sessió
	 */
	public void imputaImport(Double importAImputar) {
		this.setImportRestant(this.getImportRestant() - importAImputar);
		if (this.getImportRestant() == 0) {
			this.setFinalitzada(true);
		}
	}

}
