/**
 * 
 */
package com.eschoolmanager.server.gestors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes d'usuaris
 */
public class GestorUsuari {
	
    private EntityManager entityManager = null;
	private final static String NOM_USUARI= "usuari";
	private final static String CONTRASENYA = "contrasenya";
	private final static String CODI_SESSIO = "codiSessio";

	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorUsuari(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
     * Obte l'usuari de la base de dades amb un identificador determinat
     * @param codi identificador de l'usuari a obtenir
     * @return usuari en cas d'haver-hi un usuari amb aquest codi
	 * @throws GestorExcepcions en cas que no s'hagi trobat cap usuari amb dades indicades
     */
    public Usuari troba(int codi) throws GestorExcepcions {
    	
		try {
    		entityManager.getTransaction().begin();        
    		Usuari usuari = (Usuari) entityManager.find(Usuari.class, codi);       
            entityManager.getTransaction().commit();
            
            return usuari;
            
        } catch (NoResultException ex) {
        	throw new GestorExcepcions("No existeix cap usuari amb les dades indicades");
        }
    }
	
	/**
     * Obte l'usuari de la base de dades amb un codi de sessiñi determinat
     * @param codiSessio de l'usuari a obtenir
     * @return usuari en cas d'haver-hi un usuari amb aquest codi
	 * @throws GestorExcepcions en cas que no s'hagi trobat cap usuari amb dades indicades
     */
    public Usuari troba(String codiSessio) throws GestorExcepcions {
    	
		try {
    		entityManager.getTransaction().begin();        
    		Usuari  usuari = (Usuari) entityManager
        					.createQuery("SELECT u FROM Usuari u WHERE u.codiSessio = :" + CODI_SESSIO)
        					.setParameter(CODI_SESSIO, codiSessio)
        					.getSingleResult();        
            entityManager.getTransaction().commit();
            
            return usuari;
            
        } catch (NoResultException ex) {
        	throw new GestorExcepcions("No existeix cap usuari amb les dades indicades");
        }
    }

    /**
     * Obte l'usuari de la base de dades amb un usuari i contrasenya determinats
     * @param nomUsuari identificador de l'usuari a obtenir
     * @param contrasenya de l'usuari a obtenir
     * @return usuari en cas d'haver-hi un usuari amb aquest nom d'usuari i contrasenya
     * @throws GestorExcepcions en cas que no s'hagi trobat cap usuari amb dades indicades
     */
	public Usuari troba(String nomUsuari, String contrasenya) throws GestorExcepcions {
		
		try {
    		entityManager.getTransaction().begin();        
    		Usuari usuari = (Usuari) entityManager
        					.createQuery("SELECT u FROM Usuari u WHERE u.nomUsuari = :" + NOM_USUARI + " AND u.contrasenya = :" + CONTRASENYA)
        					.setParameter(NOM_USUARI, nomUsuari)
        					.setParameter(CONTRASENYA, contrasenya)
        					.setHint("eclipselink.left-join-fetch", "u.empleat")
        					.getSingleResult();        
            entityManager.getTransaction().commit();
            
        	return usuari;
        	
        } catch (NoResultException ex) {
        	throw new GestorExcepcions("No existeix cap usuari amb les dades indicades");
        }
	}

}
