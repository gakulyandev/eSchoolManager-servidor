/**
 * 
 */
package com.eschoolmanager.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	private final static String CRIDA_LLISTA = "LLISTA SERVEIS";
	private final static String CRIDA_CONSULTA = "CONSULTA SERVEI";
	private final static String CRIDA_MODI = "MODI SERVEI";
	private final static String CRIDA_BAIXA = "BAIXA SERVEI";
	private final static String DADES_CODI_SERVEI = "codiServei";
	private final static String DADES_NOM_SERVEI = "nomServei";
	private final static String DADES_DURADA_SERVEI = "durada";
	private final static String DADES_COST_SERVEI = "cost";
	
	private final static String ERROR_DUPLICAT = "Ja existeix un servei amb el mateix nom";
	private final static String ERROR_INEXISTENT = "No existeix el servei indicat";
	private final static String ERROR_ELEMENTS_RELACIONATS = "Existeixen altres elements relacionats amb el servei";
	
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
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
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
        peticio.put(CODI_SESSIO, "codiProvaDocent");
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
    public void provaAltaServeiAutoritzatDadesDuplicades() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
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
     * Mètode que prova donar d'alta un servei amb dades incompletes
     */
    @Test
    public void provaAltaServeiDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_NOM_SERVEI, "Logopedia");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 1);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar serveis amb un usuari autoritzat
     */
    @Test
    public void provaLlistaServeisAutoritzat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CAMP, "nom");
    	dadesPeticio.put(DADES_ORDRE, "ASC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Psicologia", dadesResposta.getJSONObject("0").get(DADES_NOM_SERVEI));
    }
    
    /**
     * Mètode que prova llistar serveis amb un usuari no autoritzat
     */
    @Test
    public void provaLlistaServeisNoAutoritzat() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CAMP, "nom");
    	dadesPeticio.put(DADES_ORDRE, "ASC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar serveis amb un usuari autoritzat i dades incorrectes
     */
    @Test
    public void provaLlistaServeisAutoritzatDadesIncorrectes() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CAMP, "nomS");
    	dadesPeticio.put(DADES_ORDRE, "DESC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_CAMP, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar serveis amb dades incompletes
     */
    @Test
    public void provaLlistaServeisDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_ORDRE, "DESC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un servei amb un usuari autoritzat i servei existent
     */
    @Test
    public void provaConsultaServeiAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Psicologia", dadesResposta.get(DADES_NOM_SERVEI));
    }
    
    /**
     * Mètode que prova consultar un servei amb un usuari no autoritzat i servei existent
     */
    @Test
    public void provaConsultaServeiNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
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
    public void provaConsultaServeiAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_SERVEI, 10);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un servei amb dades incompletes
     */
    @Test
    public void provaConsultaServeiDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova modificar un servei amb un usuari autoritzat i servei existent
     */
    @Test
    public void provaModiServeiAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_SERVEI, "5");
    	dadesPeticio.put(DADES_NOM_SERVEI, "PsicologiaModificat");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 2);
    	dadesPeticio.put(DADES_COST_SERVEI, 60.00);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova modificar un servei amb un usuari no autoritzat
     */
    @Test
    public void provaModiServeiNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SERVEI, "5");
    	dadesPeticio.put(DADES_NOM_SERVEI, "PsicologiaModificat");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 2);
    	dadesPeticio.put(DADES_COST_SERVEI, 60.00);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova modificar un servei amb un usuari autoritzat i servei inexistent
     */
    @Test
    public void provaModiServeiAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_SERVEI, "10");
    	dadesPeticio.put(DADES_NOM_SERVEI, "PsicologiaModificat");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 2);
    	dadesPeticio.put(DADES_COST_SERVEI, 60.00);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova modificar un servei amb un usuari autoritzat i servei existent amb el mateix nom
     */
    @Test
    public void provaModiServeiAutoritzatDadesDuplicades() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_SERVEI, "5");
    	dadesPeticio.put(DADES_NOM_SERVEI, "Psicopedagogia");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 2);
    	dadesPeticio.put(DADES_COST_SERVEI, 60.00);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DUPLICAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova modificar un servei amb dades incompletes
     */
    @Test
    public void provaModiServeiDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_SERVEI, "5");
    	dadesPeticio.put(DADES_DURADA_SERVEI, 2);
    	dadesPeticio.put(DADES_COST_SERVEI, 60.00);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
	/**
     * Mètode que donar de baixa un servei amb un usuari autoritzat i servei existent
     */
    @Test
    public void provaBaixaServeiAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_SERVEI, 6);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que donar de baixa un servei amb un usuari no autoritzat i servei existent
     */
    @Test
    public void provaBaixaServeiNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SERVEI, 6);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que donar de baixa un servei amb un usuari autoritzat i servei inexistent
     */
    @Test
    public void provaBaixaServeiAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_SERVEI, 10);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT, resposta.get(MISSATGE));
    }

    /**
     * Mètode que donar de baixa un servei amb un usuari autoritzat i altres entitats relacionades al servei
     */
    @Test
    public void provaBaixaServeiAutoritzatDadesRelacionades() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_ELEMENTS_RELACIONATS, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que donar de baixa un servei amb dades incompletes
     */
    @Test
    public void provaBaixaServeiDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }

}
