/**
 * 
 */
package com.eschoolmanager.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els serveis prestats a l'escola
 */
@Entity
@Table(name="Servei")
public class Servei {
	
	private int codi;
	private Escola escola;
	private String nom;
	private double cost;
	private int durada;
	private List<Beca> beques;
	private List<Professor> professors;
	private List<Sessio> sessions;
	
	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Servei() {
	}
	
	/**
     * Constructor parametritzat: construeix una nova factura amb els paràmetres especificats
     * @param escola a on es presta el servei
     * @param nom del servei que es presta
     * @param cost del servei que es presta
     * @param durada del servei que es presta
     */
	public Servei(String nom, double cost, int durada) {
		this.setNom(nom);
		this.setCost(cost);
		this.setDurada(durada);
		this.setBeques(new ArrayList<Beca>());
		this.setProfessors(new ArrayList<Professor>());
		this.setSessions(new ArrayList<Sessio>());
	}

	/**
	 * Obté el codi identificador del servei
	 * @return the codi identificador del servei
	 */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codi")
	public int getCodi() {
		return codi;
	}

	/**
	 * Actualitza el codi identificador del servei
	 * @param codi actualitzat del servei
	 */
	public void setCodi(int codi) {
		this.codi = codi;
	}
	
	/**
	 * Obté l'escola a on es presta el servei
	 * @return escola a on es presta el servei
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="escola_codi", nullable=false)
	public Escola getEscola() {
		return escola;
	}

	/**
	 * Actualitza l'escola a on es presta el servei
	 * @param escola actualitzada del servei
	 */
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	
	/**
	 * Obté el nom del servei prestat a l'escola
	 * @return nom del servei prestat a l'escola
	 */
	@Column(name="nom")
	public String getNom() {
		return nom;
	}

	/**
	 * Actualitza el nom del servei prestat a l'escola
	 * @param nom actualitzat del servei
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Obté el cost del servei prestat a l'escola
	 * @return cost del servei prestat a l'escola
	 */
	@Column(name="cost")
	public double getCost() {
		return cost;
	}

	/**
	 * Actualitza el cost del servei prestat a l'escola
	 * @param cost actualitzat del servei
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Obté la durada del servei prestat a l'escola
	 * @return durada del servei prestat a l'escola
	 */
	@Column(name="durada")
	public int getDurada() {
		return durada;
	}

	/**
	 * Actualitza la durada del servei prestat a l'escola
	 * @param durada actualitzada del servei
	 */
	public void setDurada(int durada) {
		this.durada = durada;
	}

	/**
	 * Llista les beques adjudicades per el servei
	 * @return beques per el servei
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="servei")
	public List<Beca> getBeques() {
		return this.beques;
	}
	
	/**
	 * Actualitza les beques adjudicades per el servei (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param beques actualitzades del servei
	 */
	public void setBeques(List<Beca> beques) {
		this.beques = beques;
	}

	/**
	 * Llista els professors que presten el servei
	 * @return professors que presten el servei
	 */
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name="serveis_professors",
			joinColumns=@JoinColumn(name="professor"),
			inverseJoinColumns=@JoinColumn(name="servei")
	)
	public List<Professor> getProfessors() {
		return this.professors;
	}
	
	/**
	 * Actualitza professors que presten el servei (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param professors actualitzats del servei
	 */
	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}
	
	/**
	 * Llista les sessions realitzades del servei
	 * @return sessions realitzades del servei
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="servei")
	public List<Sessio> getSessions() {
		return this.sessions;
	}
	
	/**
	 * Actualitza les sessions realitzades del servei (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param sessions actualitzades del servei
	 */
	public void setSessions(List<Sessio> sessions) {
		this.sessions = sessions;
	}
}
