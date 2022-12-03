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
 * Classe per comprovar el funcionament de les gestions de beques
 */
public class GestioBequesTest extends BaseTest {

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
     * Mètode que prova donar d'alta una beca amb un usuari autoritzat i servei i estudiant existents
     */
    @Test
    public void provaAltaBecaAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_BECA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_IMPORT_INICIAL_BECA, 2000.00);
    	dadesPeticio.put(DADES_ADJUDICANT_BECA, "Generalitat");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova donar d'alta una beca amb un usuari no autoritzat
     */
    @Test
    public void provaAltaBecaNoAutoritzatDadesCorrectes() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_BECA);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_IMPORT_INICIAL_BECA, 2000.00);
    	dadesPeticio.put(DADES_ADJUDICANT_BECA, "Generalitat");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta una beca amb un usuari autoritzat i servei i/o estudiant inexistents
     */
    @Test
    public void provaAltaBecaAutoritzatDadesIncorrectes() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_BECA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_IMPORT_INICIAL_BECA, 2000.00);
    	dadesPeticio.put(DADES_ADJUDICANT_BECA, "Generalitat");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 40);
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_ESTUDIANT_SERVEI, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta una beca amb dades incompletes
     */
    @Test
    public void provaAltaBecaDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_BECA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_IMPORT_INICIAL_BECA, 2000.00);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar beques amb un usuari autoritzat
     */
    //@Test
    public void provaLlistaBequesAutoritzat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_BEQUES);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CAMP, "nom");
    	dadesPeticio.put(DADES_ORDRE, "ASC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Clara", dadesResposta.getJSONObject("0").get(DADES_NOM_ESTUDIANT));
    }
    
    /**
     * Mètode que prova llistar beques amb un usuari no autoritzat
     */
    //@Test
    public void provaLlistaBequesNoAutoritzat() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_BEQUES);
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
     * Mètode que prova llistar beques amb un usuari autoritzat i dades incorrectes
     */
    //@Test
    public void provaLlistaBequesAutoritzatDadesIncorrectes() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_BEQUES);
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
     * Mètode que prova llistar beques amb dades incompletes
     */
    //@Test
    public void provaLlistaBequesDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_BEQUES);
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
     * Mètode que prova consultar una beca amb un usuari autoritzat i beca existent
     */
    @Test
    public void provaConsultaBecaAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_BECA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_BECA, 30);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Clara", dadesResposta.get(DADES_NOM_ESTUDIANT));
    }
    
    /**
     * Mètode que prova consultar una beca amb un usuari no autoritzat i beca existent
     */
    @Test
    public void provaConsultaBecaNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_BECA);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_BECA, 30);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar una beca amb un usuari autoritzat i beca inexistent
     */
    @Test
    public void provaConsultaBecaAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_BECA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_BECA, 50);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_BECA, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar una beca amb dades incompletes
     */
    @Test
    public void provaConsultaBecaDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_BECA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }

}
