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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar les factures generades pels estudiants de l'escola
 */
@Entity
@Table(name="Factura")
public class Factura {
	
	private int codi;
	private int mesFacturat;
	private boolean pagat;
	private Estudiant estudiant;
	private List<FacturaLinia> linies;
	private Escola escola;

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
	public Factura(List<Sessio> sessions, Estudiant estudiant, int mes) {
        this.setEstudiant(estudiant);
        this.setMesFacturat(mes);
        this.generaLinies(sessions);
        this.setPagat(false);
	}
	
	/**
	 * Obté el codi identificador de la factura
	 * @return codi identificador de la factura
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
	 * Obté el mes facturat
	 * @return mesFacturat
	 */
	public int getMesFacturat() {
		return mesFacturat;
	}

	/**
	 * Actualitza el mes facturat
	 * @param mesFacturat actualitzat
	 */
	public void setMesFacturat(int mesFacturat) {
		this.mesFacturat = mesFacturat;
	}

	/**
	 * Obté l'estat de la factura
	 * @return true o false segons si esta o no pagada
	 */
	public boolean isPagat() {
		return pagat;
	}

	/**
	 * Actualitza l'estat de la factura
	 * @param true o false segons si esta o no pagada
	 */
	public void setPagat(boolean pagat) {
		this.pagat = pagat;
	}

	/**
	 * Obté l'estudiant per qui s'ha generat la factura
	 * @return estudiant per qui s'ha generat la factura
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
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

	/**
	 * Llista les línies generades de la factura
	 * @return línies generades de la factura
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="factura")
	public List<FacturaLinia> getLinies() {
		return linies;
	}

	/**
	 * Actualitza les línies generades de la factura
	 * @param línies actualitzades de la factura
	 */
	public void setLinies(List<FacturaLinia> linies) {
		this.linies = linies;
	}
		
	/**
	 * Obté l'escola
	 * @return escola
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="escola_codi", nullable=false)
	public Escola getEscola() {
		return escola;
	}
	
	/**
	 * Actualitza l'escola
	 * @param escola actualitzat
	 */
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	
	/**
	 * Genera les linies de Factura
	 * @param sessions per les que s'han de generar les linies
	 */
	public void generaLinies(List<Sessio> sessions) {
		
		List<FacturaLinia> linies = new ArrayList<FacturaLinia>();

		// Genera una línia de factura per cada sessió a facturar
        for (Sessio sessio : sessions) {

        	// Imputa el cost de la sessió
    		Servei serveiFacturat = sessio.getServei();
    		Double costServeiFacturat = serveiFacturat.getCost();
    		
        	Double importBeca = 0.00;
        	Double importEstudiant = 0.00;
        	
        	for (Beca beca : this.getEstudiant().getBeques()) {
        		
        		if ((beca.getServei() == serveiFacturat) && !beca.isFinalitzada() && (costServeiFacturat > 0)) {
        			
        			Double importRestantBeca = beca.getImportRestant();
        			if (importRestantBeca >= costServeiFacturat) {
        				importBeca = importBeca + costServeiFacturat;
        				beca.setImportRestant(importRestantBeca - costServeiFacturat);
        			} else {
        				importBeca = importBeca + importRestantBeca;
        				beca.setImportRestant(0.00);
        			}
        			
        			costServeiFacturat = costServeiFacturat - importBeca;
        		}
        	}

        	importEstudiant = costServeiFacturat;
        	
        	// Crea linia de factura
        	linies.add(new FacturaLinia(sessio, importBeca, importEstudiant, this));
        	
        	// Marca sessió com a facturada
        	sessio.setFacturada(true);
        }
        
		this.setLinies(linies);
	}

}
