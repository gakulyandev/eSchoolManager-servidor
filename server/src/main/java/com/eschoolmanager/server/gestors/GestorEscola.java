package com.eschoolmanager.server.gestors;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Escola;
import com.eschoolmanager.server.utilitats.Constants;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes d'objectes de l'escola
 */
public class GestorEscola implements Constants {

    protected EntityManager entityManager = null;
    protected Escola escola;
    
    private final static int CODI_ESCOLA = 1;
	protected final static String PERMIS_ACCES= "acces";  

	protected final static String DADES_CAMP_CODI = "codi";  
	protected final static String DADES_ORDRE_ASC = "ASC";  
	protected final static String[] DADES_ORDENACIONS = {"ASC","DESC"};
	
    /**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     * @throws GestorExcepcions 
     */
	public GestorEscola(EntityManager entityManager) throws GestorExcepcions {
		this.entityManager = entityManager;
		this.escola = this.trobaEscola();
	} 
	
	/**
	 * Troba l'objecte de l'escola registrat
	 * @return escola amb les seves dades
	 * @throws GestorExcepcions en cas d'error
	 */
	private Escola trobaEscola() throws GestorExcepcions {

		try {
    		entityManager.getTransaction().begin();        
    		Escola escola = (Escola) entityManager.find(Escola.class, CODI_ESCOLA);       
            entityManager.getTransaction().commit();
            
            return escola;
        } catch (NoResultException ex) {
        	throw new GestorExcepcions(ERROR_GENERIC);
        }
	}
}
