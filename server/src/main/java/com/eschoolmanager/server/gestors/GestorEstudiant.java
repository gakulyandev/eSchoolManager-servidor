/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Estudiant;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes d'estudiants
 */
public class GestorEstudiant extends GestorEscola {
	
	private final static String[] DADES_CAMPS = {"dni","nom","codi","cognoms","dataNaixement","telefon","email","adreca"};

	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorEstudiant(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}

	/**
     * Dona d'alta un nou estudiant a l'escola
     * @param dni del nou estudiant
     * @param nom del nou estudiant
     * @param cognoms del nou estudiant
     * @param dataNaixement del nou estudiant
     * @param telefon del nou estudiant
     * @param email del nou estudiant
     * @param adreça del nou estudiant
     * @throws GestorExcepcions
     */
	public void alta(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) throws GestorExcepcions {
		
		// Dona d'alta l'estudiant a l'escola
        Estudiant estudiant = escola.altaEstudiant(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
        
        // Persisteix l'estudiant
        entityManager.getTransaction().begin();
        entityManager.merge(estudiant);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Obté llistat dels estudiants de l'escola
     * @param camp de dades per ordenar
     * @param ordre que ha de mostrar
     * @return llista d'estudiants
     * @throws GestorExcepcions
     */
	public HashMap<Integer, Object> llista(String camp, String ordre) throws GestorExcepcions {
		     
		if ((Arrays.asList(DADES_CAMPS).contains(camp) && Arrays.asList(DADES_ORDENACIONS).contains(ordre)) || 
			 camp.length() == 0 && ordre.length() == 0) {
			List<Estudiant> estudiants;
			
			if ((camp.equals(DADES_CAMP_CODI) && ordre.equals(DADES_ORDRE_ASC)) || camp.length() == 0 && ordre.length() == 0) {
				estudiants = escola.getEstudiants();//Llista els estudiants amb l'ordre per defecte
			} else {
				//Llista els estudiants segons la petició
				String consulta = "SELECT e FROM Estudiant e ORDER BY e." + camp + " " + ordre;
	
				entityManager.getTransaction().begin();   
				
	    		Query query = (Query) entityManager.createQuery(consulta);
	    		estudiants = query.getResultList();  
	    		
	            entityManager.getTransaction().commit();
			}
	
			
	        HashMap<Integer, Object> dadesEstudiants = new HashMap<Integer, Object>();
	        
			int i = 0;
	        for (Estudiant estudiant : estudiants) {
	        	HashMap<String,Object> dadesEstudiant = new HashMap<String, Object>();
	        	dadesEstudiant.put(DADES_CODI_ESTUDIANT, estudiant.getCodi());
	        	dadesEstudiant.put(DADES_NOM_ESTUDIANT, estudiant.getNom());
	        	dadesEstudiant.put(DADES_COGNOMS_ESTUDIANT, estudiant.getCognoms());
	        	dadesEstudiants.put(i, dadesEstudiant);
	        	i++;
	    	}
	        
	        return dadesEstudiants;		
		}
		
		throw new GestorExcepcions(ERROR_CAMP);
    }
	
	/**
     * Obté les dades d'un estudiant de l'escola
     * @param codi de l'estudiant a cercar
     * @return dades de l'estudiant
     * @throws GestorExcepcions
     */
	public HashMap<String, Object> consulta(int codi) throws GestorExcepcions {
        
		Estudiant estudiant = escola.trobaEstudiant(codi);
        if (estudiant == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_ESTUDIANT);
		}
        
        HashMap<String, Object> dadesEstudiant = new HashMap<String, Object>();
        dadesEstudiant.put(DADES_CODI_ESTUDIANT, estudiant.getCodi());
        dadesEstudiant.put(DADES_DNI_ESTUDIANT, estudiant.getDni());
        dadesEstudiant.put(DADES_NOM_ESTUDIANT, estudiant.getNom());
        dadesEstudiant.put(DADES_COGNOMS_ESTUDIANT, estudiant.getCognoms());
        dadesEstudiant.put(DADES_DATA_NAIXEMENT_ESTUDIANT, estudiant.getDataNaixement());
        dadesEstudiant.put(DADES_ADRECA_ESTUDIANT, estudiant.getAdreca());
        dadesEstudiant.put(DADES_TELEFON_ESTUDIANT, estudiant.getTelefon());
        dadesEstudiant.put(DADES_EMAIL_ESTUDIANT, estudiant.getEmail());
        dadesEstudiant.put(DADES_ESTAT_ESTUDIANT, estudiant.isRegistrat());
        
        return dadesEstudiant;
    }
	
	/**
     * Actualitza estudiant de l'escola
     * @param codi de l'estudiant
     * @param dni de l'estudiant
     * @param nom de l'estudiant
     * @param cognoms de l'estudiant
     * @param dataNaixement de l'estudiant
     * @param telefon de l'estudiant
     * @param email de l'estudiantt
     * @param adreça de l'estudiant
     * @param estat de l'estudiant
     * @throws GestorExcepcions
     */
	public void actualitza(int codiEstudiant, String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca, Boolean registrat) throws GestorExcepcions {

        // Troba l'estudiant
        Estudiant estudiant = escola.trobaEstudiant(codiEstudiant);
        if (estudiant == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_ESTUDIANT);
		}
        
        // Actualitza l'estudiant
        escola.actualitzaEstudiant(estudiant, dni, nom, cognoms, dataNaixement, telefon, email, adreca, registrat);
       
        // Persisteix el departament
        entityManager.getTransaction().begin();
        entityManager.merge(estudiant);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Dona de baixa un estudiant de l'escola
     * @param codi de l'estudiant a donar de baixa
     * @throws GestorExcepcions
     */
	public void baixa(int codi) throws GestorExcepcions {
        
		// Troba el departament
        Estudiant estudiant = escola.trobaEstudiant(codi);
        if (estudiant == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_ESTUDIANT);
		}
        escola.baixaEstudiant(estudiant);
        
        // Actualitza el departament
        entityManager.getTransaction().begin();
        entityManager.merge(escola);
        entityManager.remove(estudiant);
        entityManager.getTransaction().commit();
    }

}
