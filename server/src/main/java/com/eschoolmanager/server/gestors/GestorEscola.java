package com.eschoolmanager.server.gestors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.eschoolmanager.server.model.Escola;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes d'objectes de l'escola
 */
public class GestorEscola {

    protected EntityManager entityManager = null;
    protected Escola escola;
    private final static int CODI_ESCOLA = 1;
    
    /**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     * @throws GestorExcepcions 
     */
	public GestorEscola(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	/**
	 * Troba l'objecte de l'escola registrat
	 * @return escola amb les seves dades
	 * @throws GestorExcepcions en cas d'error
	 */
	public Escola trobaEscola() throws GestorExcepcions {
		try {
    		entityManager.getTransaction().begin();        
    		Escola escola = (Escola) entityManager.find(Escola.class, CODI_ESCOLA);       
            entityManager.getTransaction().commit();
            
            return escola;
            
        } catch (NoResultException ex) {
        	throw new GestorExcepcions("S'ha produït un error");
        }
	}
}
