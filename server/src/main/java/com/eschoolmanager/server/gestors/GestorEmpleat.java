/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.Servei;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes d'empleats
 */
public class GestorEmpleat extends GestorEscola {

	private final static String[] DADES_CAMPS = {"nom","codi","cognoms","dataNaixement","telefon","email","adreca","codiDepartament"};
	
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
	
	/**
     * Obté llistat dels empleats de l'escola
     * @param camp de dades per ordenar
     * @param ordre que ha de mostrar
     * @return llista de departaments
     * @throws GestorExcepcions
     */
	public HashMap<Integer, Object> llista(String camp, String ordre) throws GestorExcepcions {
                
        // Llista els permisos del departament
		if ((Arrays.asList(DADES_CAMPS).contains(camp) && Arrays.asList(DADES_ORDENACIONS).contains(ordre)) || 
			 camp.length() == 0 && ordre.length() == 0) {
			List<Empleat> empleats;
			
			if ((camp.equals(DADES_CAMP_CODI) && ordre.equals(DADES_ORDRE_ASC)) || camp.length() == 0 && ordre.length() == 0) {
				empleats = escola.getEmpleats();
			} else {
				String consulta = "SELECT e FROM Empleat e ORDER BY e." + camp + " " + ordre;
	
				entityManager.getTransaction().begin();   
				
	    		Query query = (Query) entityManager.createQuery(consulta);
	    		empleats = query.getResultList();  
	    		
	            entityManager.getTransaction().commit();
			}
	
			
	        HashMap<Integer, Object> dadesEmpleats = new HashMap<Integer, Object>();
	        
			int i = 0;
	        for (Empleat empleat : empleats) {
	        	HashMap<String,Object> dadesEmpleat = new HashMap<String, Object>();
	        	dadesEmpleat.put(DADES_CODI_EMPLEAT, empleat.getCodi());
	        	dadesEmpleat.put(DADES_NOM_EMPLEAT, empleat.getNom());
	        	dadesEmpleat.put(DADES_COGNOMS_EMPLEAT, empleat.getCognoms());
	        	dadesEmpleat.put(DADES_CODI_DEPARTAMENT, empleat.getDepartament().getCodi());
	        	dadesEmpleat.put(DADES_NOM_DEPARTAMENT, empleat.getDepartament().getNom());
	        	dadesEmpleats.put(i, dadesEmpleat);
	        	i++;
	    	}
	        
	        return dadesEmpleats;		
		}
		
		throw new GestorExcepcions(ERROR_CAMP);
    }
	
	/**
     * Obté les dades d'un empleat de l'escola
     * @param codi de l'empleat a cercar
     * @return dades de l'empleat
     * @throws GestorExcepcions
     */
	public HashMap<String, Object> consulta(int codi) throws GestorExcepcions {
        
		// Troba el servei
        Empleat empleat = escola.trobaEmpleat(codi);
        if (empleat == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_EMPLEAT);
		}
        
        HashMap<String, Object> dadesEmpleat = new HashMap<String, Object>();
        dadesEmpleat.put(DADES_CODI_EMPLEAT, empleat.getCodi());
        dadesEmpleat.put(DADES_DNI_EMPLEAT, empleat.getDni());
        dadesEmpleat.put(DADES_NOM_EMPLEAT, empleat.getNom());
        dadesEmpleat.put(DADES_COGNOMS_EMPLEAT, empleat.getCognoms());
        dadesEmpleat.put(DADES_DATA_NAIXEMENT_EMPLEAT, empleat.getDataNaixement());
        dadesEmpleat.put(DADES_ADRECA_EMPLEAT, empleat.getAdreca());
        dadesEmpleat.put(DADES_TELEFON_EMPLEAT, empleat.getTelefon());
        dadesEmpleat.put(DADES_EMAIL_EMPLEAT, empleat.getEmail());
        dadesEmpleat.put(DADES_CODI_DEPARTAMENT, empleat.getDepartament().getCodi());
        dadesEmpleat.put(DADES_NOM_DEPARTAMENT, empleat.getDepartament().getNom());
        dadesEmpleat.put(DADES_NOM_USUARI, empleat.getUsuari().getNomUsuari());
        dadesEmpleat.put(DADES_ESTAT_EMPLEAT, empleat.isActiu());
        
        return dadesEmpleat;
    }

}
