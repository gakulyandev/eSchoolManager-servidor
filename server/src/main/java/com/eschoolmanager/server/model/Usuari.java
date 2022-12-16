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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.eschoolmanager.server.seguretat.Seguretat;

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
	private boolean actiu = false;
	private Escola escola;
	private String nomUsuari;
	private String contrasenya;
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
	public Usuari(String nomUsuari, String contrasenya) {
        this.setNomUsuari(nomUsuari);
        this.setContrasenya(Seguretat.encriptaContrasenya(contrasenya));
		this.setActiu(true);
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
     * @param codi actualitzat de l'usuari
     */
    public void setCodi(int codi) {
        this.codi = codi;
    }

	/**
	 * Obté l'estat de l'usuari
	 * @return actiu true | false segons l'estat actual de l'usuari
	 */
    @Column(name="actiu")
	public boolean isActiu() {
		return actiu;
	}

	/**
	 * Actualitza l'estat de l'usuari
	 * @param estat actualitzat de l'usuari
	 */
	public void setActiu(boolean actiu) {
		this.actiu = actiu;
	}

	/**
	 * Obté l'escola a on està donat d'alta l'usuari
	 * @return escola a on està donat d'alta l'usuari
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="escola_codi")
	public Escola getEscola() {
		return escola;
	}

	/**
	 * Actualitza l'escola a on està donat d'alta l'usuari
	 * @param escola actualitzatda de l'usuari
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
     * @param nomUsuari actualitzat de l'usuari
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
     * @param contrasenya actualitzada de l'usuari
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
	 * @param empleat actualitzat de l'usuari
	 */
	public void setEmpleat(Empleat empleat) {
		this.empleat = empleat;
	}
    
    /**
     * Genera un codi de sessio
     * @return sessioUsuari iniciada
     */
    public SessioUsuari generaSessio() {
        return new SessioUsuari(
        		(this.nomUsuari + Timestamp.from(Instant.now())).replaceAll("\\s+",""), 
        		this,
        		this.empleat.getCodi(), 
        		this.empleat.getNom(), 
        		this.empleat.getDepartament().getNom(), 
        		this.empleat.getDepartament().getPermisos()
        );
    }
    
	/**
	 * Actualitza l'usuari
     * @param nom d'usuari de l'empleat
     * @param contrasenya d'usuari de l'empleat
	 */
	public void actualitza(String nomUsuari, String contrasenya) {
		this.setNomUsuari(nomUsuari);
		if (contrasenya != null && contrasenya.length() > 0) {
			this.setContrasenya(contrasenya);
		}
	}
}
