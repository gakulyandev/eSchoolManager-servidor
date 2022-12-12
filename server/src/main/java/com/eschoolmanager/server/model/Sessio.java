
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
 * Classe persistent per emmagatzemar les sessions dels serveis prestats pels professors
 */
@Entity
@Table(name="Sessio")
public class Sessio {

	private int codi;
	private Professor professor;
	private Estudiant estudiant;
	private Servei servei;
	private Date dataIHora;
	private Escola escola;
	private boolean facturada;
	
	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Sessio() {
	}
	
	/**
     * Constructor parametritzat: construeix una nova factura amb els paràmetres especificats
     * @param professor que presta el servei a la sessió
     * @param estudiant a qui es presta el servei a la sessió
     * @param servei que es presta a la sessió
     * @param dataIHora de la sessió realitzada
     */
	public Sessio(Professor professor, Estudiant estudiant, Servei servei, Date dataIHora) {
		this.setProfessor(professor);
		this.setEstudiant(estudiant);
		this.setServei(servei);
		this.setDataIHora(dataIHora);
		this.setFacturada(false);
		professor.assignaSessio(this);
		estudiant.assignaSessio(this);
		servei.assignaSessio(this);
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
	 * Actualitza el codi identificador de la sessió
	 * @param codi actualitzat de la sessió
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
	 * Obté el professor que presta el servei a la sessió
	 * @return professor que presta el servei a la sessió
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="professor_codi", nullable=false)
	public Professor getProfessor() {
		return professor;
	}

	/**
	 * Actualitza el professor que presta el servei a la sessió
	 * @param professor actualitzat de la sessió
	 */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	/**
	 * Obté l'estudiant a qui es presta el servei a la sessió
	 * @return estudiant a qui es presta el servei a la sessió
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="estudiant_codi", nullable=false)
	public Estudiant getEstudiant() {
		return estudiant;
	}

	/**
	 * Actualitza l'estudiant a qui es presta el servei a la sessió
	 * @param estudiant actualitzat de la sessió
	 */
	public void setEstudiant(Estudiant estudiant) {
		this.estudiant = estudiant;
	}

	/**
	 * Obté el servei que es presta a la sessió
	 * @return servei que es presta a la sessió
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="servei_codi", nullable=false)
	public Servei getServei() {
		return servei;
	}

	/**
	 * Actualitza el servei que es presta a la sessió
	 * @param servei actualitzat de la sessió
	 */
	public void setServei(Servei servei) {
		this.servei = servei;
	}

	/**
	 * Obté la data i hora a la que es realitza la sessió
	 * @return dataIHora a la que es realitza la sessió
	 */
	@Column(name="data_i_hora")
	public Date getDataIHora() {
		return dataIHora;
	}

	/**
	 * Actualitza la data i hora a la que es realitza la sessió
	 * @param dataIHora actualitzada de la sessió
	 */
	public void setDataIHora(Date dataIHora) {
		this.dataIHora = dataIHora;
	}

	/**
	 * Obté l'estat de facturació
	 * @return true o false segons si la sessió ha sigut o no facturada
	 */
	public boolean isFacturada() {
		return facturada;
	}

	/**
	 * @param facturada the facturada to set
	 */
	public void setFacturada(boolean facturada) {
		this.facturada = facturada;
	}

	/**
	 * Actualitza les dades del servei
     * @param professor a actualitzar
     * @param l'estudiant a actualitzar
     * @param servei a actualitzar
     * @param data i hora a actualitzar
	 */
	public void actualitza(Professor professor, Estudiant estudiant, Servei servei, Date dataIHora) {
		this.setProfessor(professor);
		this.setEstudiant(estudiant);
		this.setServei(servei);
		this.setDataIHora(dataIHora);
		professor.assignaSessio(this);
		estudiant.assignaSessio(this);
		servei.assignaSessio(this);
	}

}
