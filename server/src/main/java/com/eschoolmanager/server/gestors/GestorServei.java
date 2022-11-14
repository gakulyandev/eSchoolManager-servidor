/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Permis;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de departaments
 */
public class GestorServei extends GestorEscola {

	private final static String DEPARTAMENT_ADMINISTRADOR= "Administrador";   
	private final static String PERMIS_ACCES= "acces";  
    
	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorServei(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Afeigeix un nou departament a la base de dades
     * @param departament el departament que s'ha de desar a la base de dades
     * @throws GestorExcepcions
     */
	public void alta(String nom, HashMap<String,Boolean>permisosPeticio) throws GestorExcepcions {
        
		// Inicialitza el departament
        Departament departament = new Departament(nom);
        
        // Adjudica permisos al departament a partir dels permisos d'administrador, els màxims
        List<Permis> permisos = escola.trobaDepartament(DEPARTAMENT_ADMINISTRADOR).getPermisos();
        for(Permis permis : permisos) {
        	if(permis.getNom().equals(PERMIS_ACCES)) {
        		departament.adjudicaPermis(permis);
        	}
        	
        	for (String permisNom: permisosPeticio.keySet()) {
            	if(permis.getNom().equals(permisNom)) {
            		departament.adjudicaPermis(permis);
            	}        		
        	}
        }       

        // Dona d'alta el departament a l'escola
        escola.altaDepartament(departament);

        // Persisteix el departament
        entityManager.getTransaction().begin();
        entityManager.merge(departament);
        entityManager.getTransaction().commit();
    }
}
