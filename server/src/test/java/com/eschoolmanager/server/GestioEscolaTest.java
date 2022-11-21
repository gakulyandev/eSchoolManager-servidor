/**
 * 
 */
package com.eschoolmanager.server;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gayané Akulyan Akulyan
 * Classe per comprovar el funcionament de les gestions d'escola
 */
public class GestioEscolaTest extends BaseTest {
	
	/**
     * Neteja la base de dades i l'omple amb dades de prova
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	super.setUp();
    }
    
    /**
     * Tanca l'entityManager i la factoria d'entitats en acabar els tests
     */
    @After()
    public void classEnds() {
    	super.classEnds();
    }
    
    /**
     * Mètode que prova consultar les dades de l'escola amb un usuari autoritzat i escola existent
     */
    @Test
    public void provaConsultaEscolaAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_ESCOLA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_CODI_ESCOLA, 1);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Psicologia", dadesResposta.get(DADES_NOM_ESCOLA));
    }
    
    /**
     * Mètode que prova consultar un servei amb un usuari no autoritzat i servei existent
     */
    @Test
    public void provaConsultaEscolaNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_ESCOLA);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_ESCOLA, 1);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un servei amb un usuari autoritzat i servei inexistent
     */
    @Test
    public void provaConsultaEscolaAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_ESCOLA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_CODI_ESCOLA, 10);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_ESCOLA, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un servei amb dades incompletes
     */
    @Test
    public void provaConsultaEscolaDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_ESCOLA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
}
