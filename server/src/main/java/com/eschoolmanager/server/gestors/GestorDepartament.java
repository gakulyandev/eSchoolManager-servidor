/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Permis;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de departaments
 */
public class GestorDepartament extends GestorEscola {

	private final static String DEPARTAMENT_ADMINISTRADOR= "Administrador";  
	private final static String DADES_CODI_DEPARTAMENT= "codiDepartament";  
	private final static String DADES_NOM_DEPARTAMENT= "nomDepartament";     
	private final static String DADES_PERMISOS_DEPARTAMENT = "permisos";   
	private final static String PERMIS_ACCES= "acces";  

	private final static String DEPARTAMENT_INEXISTENT = "No existeix el departament indicat";
    
	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorDepartament(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Dona d'alta un nou departament a l'escola
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
	
	/**
     * Obté les dades d'un departament de l'escola
     * @param codi del departament a cercar
     * @throws GestorExcepcions
     */
	public HashMap<String, Object> consulta(int codi) throws GestorExcepcions {
        
		// Troba el departament
        Departament departament = escola.trobaDepartament(codi);
        if (departament == null) {
			throw new GestorExcepcions(DEPARTAMENT_INEXISTENT);
		}
        
        // Llista els permisos del departament
        List<String> permisos = new ArrayList<String>();   
        for (Permis permisDepartament : departament.getPermisos()) {
        	if(!permisDepartament.getNom().equals(PERMIS_ACCES)) {
        		permisos.add(permisDepartament.getNom());
        	}		
    	}
        
        HashMap<String, Object> dadesDepartament = new HashMap<String, Object>();
        dadesDepartament.put(DADES_CODI_DEPARTAMENT, departament.getCodi());
        dadesDepartament.put(DADES_NOM_DEPARTAMENT, departament.getNom());
        dadesDepartament.put(DADES_PERMISOS_DEPARTAMENT, permisos);
        
        return dadesDepartament;
    }
}
