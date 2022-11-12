/**
 * 
 */
package com.eschoolmanager.server.model;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els usuaris de l'escola
 */
@Entity
@Table(
		name="Usuari",
		uniqueConstraints=@UniqueConstraint(columnNames={"nom_usuari"})
)
public class Usuari {
	
	private int codi;
	private Escola escola;
	private String nomUsuari;
	private String contrasenya;
	private String codiSessio;
	private Empleat empleat;
	
	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Usuari() {
	}

	/**
     * Constructor parametritzat: construeix un nou usuari amb els paràmetres especificats
     * @param escola a on està donat d'alta l'usuari
     * @param nomUsuari nom d'usuari
     * @param contrasenya contrasenya
     */
	public Usuari(Escola escola, String nomUsuari, String contrasenya) {
		this.setEscola(escola);
        this.setNomUsuari(nomUsuari);
        this.setContrasenya(contrasenya);
	}
	
	/**
     * Obté el codi que identifica l'usuari
     * @return codi que identifica l'usuari
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="codi")
    public int getCodi() {
        return codi;
    } 
    
    /**
     * Actualitza el codi de l'usuari
     * @param codi nou valor pel codi de l'usuari
     */
    public void setCodi(int codi) {
        this.codi = codi;
    }

	/**
	 * Obté l'escola a on està donat d'alta l'usuari
	 * @return escola a on està donat d'alta l'usuari
	 */
	@ManyToOne
    @JoinColumn(name="escola_codi", nullable=false)
	public Escola getEscola() {
		return escola;
	}

	/**
	 * Actualitza l'escola a on està donat d'alta l'usuari
	 * @param escola nou valor per l'escola a on està donat d'alta l'usuari
	 */
	public void setEscola(Escola escola) {
		this.escola = escola;
	}

    /**
     * Obté el nom d'usuari 
     * @return nomUsuari de l'usuari
     */
    @Column(name="nom_usuari", length=50) 
    public String getNomUsuari() {
        return nomUsuari;
    }
    
    /**
     * Actualitza el nom d'usuari
     * @param nomUsuari nou valor pel nom d'usuari
     */
    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    /**
     * Obté la contrasenya (només s'inclou pel correcte funcionament del mapeig ORM)
     * @return contrasenya de l'usuari
     */
    @Column(name="contrasenya")
    public String getContrasenya() {
        return contrasenya;
    }
    
    /**
     * Actualitza la contrasenya
     * @param contrasenya nou valor per la contrasenya
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
	
	/**
	 * Obté l'empleat a qui s'assigna l'usuari
	 * @return empleat a qui s'assigna l'usuari
	 */
    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="empleat_codi", unique=true, nullable=false, updatable=false)
	public Empleat getEmpleat() {
		return empleat;
	}
	
	/**
	 * Actualitza l'empleat a qui s'assigna l'usuari
	 * @param empleat nou valor d'empleat a qui s'assigna l'usuari
	 */
	public void setEmpleat(Empleat empleat) {
		this.empleat = empleat;
	}
    
    /**
     * Obté el codi de sessió (només s'inclou pel correcte funcionament del mapeig ORM)
     * @return codiSessio de l'usuari
     */
    @Column(name="codi_sessio")
    public String getCodiSessio() {
        return codiSessio;
    }
    
    /**
     * Actualitza el codi de sessio (només s'inclou pel correcte funcionament del mapeig ORM)
     * @param codi nou valor per el codi de sessio de l'usuari
     */
    public void setCodiSessio(String codi) {
        this.codiSessio = codi;
    }
    
    /**
     * Genera un codi de sessio
     * @return sessioUsuari iniciada
     */
    public SessioUsuari iniciaSessio() {
        this.setCodiSessio((this.nomUsuari + Timestamp.from(Instant.now())).replaceAll("\\s+",""));
        
        return new SessioUsuari(
        		this.codiSessio, 
        		this.empleat.getNom(), 
        		this.empleat.getDepartament().getNom(), 
        		this.empleat.getDepartament().getPermisos()
        		);
    }
    
    /**
     * Esborra el codi de sessio
     */
    public void tancaSessio() {
        this.setCodiSessio(null);
    }

}
