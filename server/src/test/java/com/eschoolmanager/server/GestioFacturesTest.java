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
 * Classe per comprovar el funcionament de les gestions de factures
 */
public class GestioFacturesTest extends BaseTest {

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
     * Mètode que prova generar una factura amb un usuari autoritzat i estudiant existent
     */
    @Test
    public void provaGeneraFacturaAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_GENERA_FACTURA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_MES_FACTURA, CODI_EXEMPLE_MES);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Psicologia", dadesResposta.getJSONObject(DADES_SESSIONS_FACTURA).getJSONObject("0").get(DADES_NOM_SERVEI));
    }
    
    /**
     * Mètode que prova generar una factura amb un usuari no autoritzat
     */
    @Test
    public void provaGeneraFacturaNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_GENERA_FACTURA);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_MES_FACTURA, CODI_EXEMPLE_MES);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova generar una factura amb un usuari autoritzat i estudiant inexistent
     */
    @Test
    public void provaGeneraFacturaAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_GENERA_FACTURA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_INEXISTENT);
    	dadesPeticio.put(DADES_MES_FACTURA, CODI_EXEMPLE_MES);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_ESTUDIANT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova generar una factura amb dades incompletes
     */
    @Test
    public void provaGeneraFacturaDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_GENERA_FACTURA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }

}
