/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes d'empleats
 */
public class GestorEmpleat extends GestorEscola {
	
	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorEmpleat(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Dona d'alta un nou empleat al departament
     * @param dni del nou empleat
     * @param nom del nou empleat
     * @param cognoms del nou empleat
     * @param dataNaixement del nou empleat
     * @param telefon del nou empleat
     * @param email del nou empleat
     * @param adreça del nou empleat
     * @param codi del departament del nou empleat
     * @param nom d'usuari del nou empleat
     * @param contrasenya d'usuari del nou empleat
     * @throws GestorExcepcions
     */
	public void alta(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca, int codiDepartament, String nomUsuari, String contrasenya) throws GestorExcepcions {
		
		// Troba el departament
        Departament departament = escola.trobaDepartament(codiDepartament);
        
        // Dona d'alta el departament a l'escola
        Empleat empleat = escola.altaEmpleat(dni, nom, cognoms, dataNaixement, telefon, email, adreca, departament);
        Usuari usuari = escola.altaUsuari(nomUsuari, contrasenya);
        empleat.assignaUsuari(usuari);
        
        // Persisteix el departament
        entityManager.getTransaction().begin();
        entityManager.merge(empleat);
        entityManager.getTransaction().commit();
    }

}
