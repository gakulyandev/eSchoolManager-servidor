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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els informes generats pels administratius de l'escola
 */
@Entity
@Table(name="Informe")
public class Informe {
	
	private int codi;
	private String dades;
	private Date dataGeneracio;
	private Administratiu administratiu;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Informe() {
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Constructor parametritzat: construeix un nou informe amb els paràmetres especificats
     * @param dades de l'informe 
     * @param administratiu que genera l'informe
     */
	public Informe(String dades, Administratiu administratiu) {
        this.setDades(dades);
        this.setAdministratiu(administratiu);
        this.setDataGeneracio(new Date(Calendar.LONG));
	}
	
	/**
	 * Obté el codi identificador de l'informe
	 * @return codi identificador de l'informe
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="codi")
	public int getCodi() {
		return codi;
	}

	/**
	 * Actualitza el codi identificador de l'informe
	 * @param codi nou valor pel codi identificador de l'informe
	 */
	public void setCodi(int codi) {
		this.codi = codi;
	}

	/**
	 * Obté les dades de l'informe
	 * @return dades de l'informe
	 */
	@Column(name="dades")
	public String getDades() {
		return dades;
	}

	/**
	 * Actualitza les dades de l'informe
	 * @param dades nou valor per les dades de l'informe
	 */
	public void setDades(String dades) {
		this.dades = dades;
	}

	/**
	 * Obté la data en que es genera l'informe
	 * @return dataGeneracio de l'informe
	 */
	@Column(name="data_generacio")
	public Date getDataGeneracio() {
		return dataGeneracio;
	}

	/**
	 * Actualitza la data de generació de l'informe
	 * @param dataGeneracio nou valor per la data en que es genera l'informe
	 */
	public void setDataGeneracio(Date dataGeneracio) {
		this.dataGeneracio = dataGeneracio;
	}

	/**
	 * Obté l'administratiu que ha generat l'informe
	 * @return administratiu que ha generat l'informe
	 */
	@ManyToOne
    @JoinColumn(name="administratiu_codi", nullable=false)
	public Administratiu getAdministratiu() {
		return administratiu;
	}

	/**
	 * Actualitza l'administratiu que ha generat l'informe
	 * @param administratiu nou valor per l'administratiu que ha generat l'informe
	 */
	public void setAdministratiu(Administratiu administratiu) {
		this.administratiu = administratiu;
	}

}
