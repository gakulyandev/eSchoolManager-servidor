/**
 * 
 */
package com.eschoolmanager.server;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.json.JSONObject;

/**
 * @author Gayané Akulyan Akulyan
 * Classe per comprovar el funcionament de les gestions de serveis
 */
public class GestioServeisTest extends BaseTest {

	private final static String CRIDA_ALTA = "ALTA SERVEI";
	private final static String DADES_NOM_SERVEI = "nom";
	private final static String DADES_DURADA_SERVEI = "durada";
	private final static String DADES_COST_SERVEI = "cost";
	
	private final static String ERROR_NO_AUTORITZAT = "L'usuari no està autoritzat per aquesta acció";
	private final static String ERROR_DUPLICAT = "Ja existeix un servei amb el mateix nom";
	private final static String ERROR_DADES = "Falten dades";
	
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
     * Mètode que prova donar d'alta un servei amb un usuari autoritzat i servei inexistent
     */
    @Test
    public void provaAltaServeiAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProva2");
    	dadesPeticio.put(DADES_NOM_SERVEI, "Logopedia");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 1);
    	dadesPeticio.put(DADES_COST_SERVEI, 30.00);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova donar d'alta un servei amb un usuari no autoritzat
     */
    @Test
    public void provaAltaServeiNoAutoritzatDadesCorrectes() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProva3");
    	dadesPeticio.put(DADES_NOM_SERVEI, "Logopedia");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 1);
    	dadesPeticio.put(DADES_COST_SERVEI, 30.00);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un servei amb un usuari autoritzat i servei existent
     */
    @Test
    public void provaAltaServeiAutoritzatDadesIncorrectes() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProva2");
    	dadesPeticio.put(DADES_NOM_SERVEI, "Psicologia");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 1);
    	dadesPeticio.put(DADES_COST_SERVEI, 30.00);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DUPLICAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un servei amb amb dades incompletes
     */
    @Test
    public void provaAltaServeiDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProva2");
    	dadesPeticio.put(DADES_NOM_SERVEI, "Logopedia");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 1);
    	dadesPeticio.put(DADES_COST_SERVEI, 30.00);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DADES, resposta.get(MISSATGE));
    }

}
