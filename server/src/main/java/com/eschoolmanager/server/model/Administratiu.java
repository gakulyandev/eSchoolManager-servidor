/**
 * 
 */
package com.eschoolmanager.server.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.eschoolmanager.server.gestors.GestorExcepcions;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els administratius de l'escola
 */
@Entity
@Table(name="Administratiu")
@DiscriminatorValue("AD")
public class Administratiu extends Empleat {
	
	private List<Informe> informes;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Administratiu() {
	}
	
	/**
     * Constructor parametritzat: construeix un nou empleat amb els paràmetres especificats
     * @param dni identificador de l'administratiu
     * @param nom de l'administratiu
     * @param cognoms de l'administratiu
     * @param dataNaixement de l'administratiu
     * @param telefon de contacte de l'administratiu
     * @param email de contacte de l'administratiu
     * @param adreca de l'administratiu
	 * @throws GestorExcepcions 
     */
	public Administratiu(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) throws GestorExcepcions {
		super(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
		this.setInformes(new ArrayList<Informe>());
	}
	
	/**
	 * Llista els informes creats per l'administratiu
	 * @return informes creats per l'administratiu
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="administratiu")
	public List<Informe> getInformes() {
		return this.informes;
	}
	
	/**
	 * Actualitza els informes creats per l'administratiu (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param informes actualitzats de l'administratiu
	 */
	public void setInformes(List<Informe> informes) {
		this.informes = informes;
	}

}
