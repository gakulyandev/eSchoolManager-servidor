/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de departaments
 */
public class GestorDepartament extends GestorEscola {

	private final static String NOM_DEPARTAMENT= "nomDepartament";    
    
	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorDepartament(EntityManager entityManager) {
		super(entityManager);
	}
	
	/**
     * Afeigeix un nou departament a la base de dades
     * @param departament el departament que s'ha de desar a la base de dades
     * @throws GestorExcepcions
     */
	public void afegeix(String nom, HashMap<String,Boolean>permisosPeticio) throws GestorExcepcions {
        if(existeix(nom)) {
            throw new GestorExcepcions("Ja existeix un departament amb el mateix nom");
        }

        Departament departament = new Departament(nom);
        departament.setEscola(this.trobaEscola());

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT p FROM Permis p ORDER BY p.nom ASC");
        List<Permis> permisos = query.getResultList();
        entityManager.getTransaction().commit();
        
        List<Permis> permisosDepartament = new ArrayList();
        for (String permisNom: permisosPeticio.keySet()) {
            if (permisosPeticio.get(permisNom) == true) {
            	for(Permis permis : permisos) {
                	if(permis.getNom().equals("acces") || permis.getNom().equals(permisNom)) {
                		permisosDepartament.add(permis);
                	}
                }
            }
        }
        
        
        departament.setPermisos(permisosDepartament);
     
        entityManager.getTransaction().begin();
        entityManager.persist(departament);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Comprova si existeix un departament amb un nom determinat
     * @param nom del departament a comprovar
     * @return true o false en cas d'haver-hi o no algun departament d'alta amb aquest nom
     */
    private boolean existeix(String nom) {
    	entityManager.getTransaction().begin();
        
    	int numeroDepartaments = ((Long) entityManager
    					.createQuery("SELECT COUNT(d) FROM Departament d WHERE d.nom = :" + NOM_DEPARTAMENT)
    					.setParameter(NOM_DEPARTAMENT, nom).getSingleResult()).intValue();
        
        entityManager.getTransaction().commit();
    	
    	if (numeroDepartaments > 0) {
            return true;
    	}
        
        return false;
    }
}
