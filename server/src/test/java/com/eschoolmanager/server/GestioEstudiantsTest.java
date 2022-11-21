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
 * Classe per comprovar el funcionament de les gestions d'estudiants
 */
public class GestioEstudiantsTest extends BaseTest {

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
     * Mètode que prova donar d'alta un estudiant amb un usuari autoritzat i estudiant o usuari inexistent
     */
    @Test
    public void provaAltaEstudiantAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_DNI_ESTUDIANT, "99988877A");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova donar d'alta un estudiant amb un usuari autoritzat i dades incorrectes
     */
    @Test
    public void provaAltaEstudiantAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_DNI_ESTUDIANT, "99988877AA");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DNI_INCORRECTE, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un estudiant amb un usuari no autoritzat
     */
    @Test
    public void provaAltaEstudiantNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_DNI_ESTUDIANT, "99988877A");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un estudiant amb un usuari autoritzat i estudiant existent
     */
    @Test
    public void provaAltaEstudiantAutoritzatDadesDuplicades() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_DNI_ESTUDIANT, "22233344N");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DUPLICAT_ESTUDIANT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un estudiant amb dades incompletes
     */
    @Test
    public void provaAltaEstudiantDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
}
