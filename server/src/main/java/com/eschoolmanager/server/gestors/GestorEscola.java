package com.eschoolmanager.server.gestors;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Escola;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes d'objectes de l'escola
 */
public class GestorEscola {

    protected EntityManager entityManager = null;
    protected Escola escola;
    
    private final static int CODI_ESCOLA = 1;
	protected final static String PERMIS_ACCES= "acces";  
	
	protected final static String[] DADES_ORDENACIONS = {"ASC","DESC"};

	private final static String ERROR_GENERIC = "S'ha produit un error";
	protected final static String ERROR_CAMP = "No existeix el valor indicat";
	
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
