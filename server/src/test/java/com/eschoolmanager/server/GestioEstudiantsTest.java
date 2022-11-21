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
    
    /**
     * Mètode que prova llistar estudiants amb un usuari autoritzat
     */
    @Test
    public void provaLlistaEstudiantsAutoritzat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_ESTUDIANTS);
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
     * Mètode que prova llistar estudiants amb un usuari no autoritzat
     */
    @Test
    public void provaLlistaEstudiantsNoAutoritzat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_ESTUDIANTS);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CAMP, "nom");
    	dadesPeticio.put(DADES_ORDRE, "DESC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar estudiants amb un usuari autoritzat i dades incorrectes
     */
    @Test
    public void provaLlistaEstudiantsAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_ESTUDIANTS);
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
     * Mètode que prova llistar estudiants amb dades incompletes
     */
    @Test
    public void provaLlistaEstudiantsDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_ESTUDIANTS);
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
     * Mètode que prova consultar un estudiant amb un usuari autoritzat i estudiant existent
     */
    @Test
    public void provaConsultaEstudiantAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Clara", dadesResposta.get(DADES_NOM_ESTUDIANT));
    }
    
    /**
     * Mètode que prova consultar un estudiant amb un usuari no autoritzat i estudiant existent
     */
    @Test
    public void provaConsultaEstudiantNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un estudiant amb un usuari autoritzat i estudiant inexistent
     */
    @Test
    public void provaConsultaEstudiantAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 30);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_ESTUDIANT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un estudiant amb dades incompletes
     */
    @Test
    public void provaConsultaEstudiantDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un estudiant amb un usuari autoritzat i estudiant existent
     */
    @Test
    public void provaModiEstudiantAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_DNI_ESTUDIANT, "99988877A");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_ESTAT_ESTUDIANT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova actualitzar un estudiant amb un usuari autoritzat i dades incorrectes
     */
    @Test
    public void provaModiEstudiantAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_DNI_ESTUDIANT, "99988877Addd");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_ESTAT_ESTUDIANT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DNI_INCORRECTE, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un estudiant amb un usuari autoritzat i estudiant no existent
     */
    @Test
    public void provaModiEstudiantAutoritzatDadesIncorrectesEstudiant() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 30);
    	dadesPeticio.put(DADES_DNI_ESTUDIANT, "99988877A");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_ESTAT_ESTUDIANT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_ESTUDIANT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un estudiant amb un usuari no autoritzat
     */
    @Test
    public void provaModiEstudiantNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_DNI_ESTUDIANT, "99988877A");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_ESTAT_ESTUDIANT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un estudiant amb un usuari autoritzat i estudiant duplicat
     */
    @Test
    public void provaModiEstudiantAutoritzatDadesDuplicades() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_DNI_ESTUDIANT, "22233344N");
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_ESTAT_ESTUDIANT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DUPLICAT_ESTUDIANT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un estudiant amb dades incompletes
     */
    @Test
    public void provaModiEstudiantDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_NOM_ESTUDIANT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_ESTUDIANT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_ESTUDIANT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_ESTUDIANT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_ESTUDIANT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_ESTUDIANT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_ESTAT_ESTUDIANT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
	/**
     * Mètode que donar de baixa un estudiant amb un usuari autoritzat i estudiant existent
     */
    @Test
    public void provaBaixaEstudiantAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 21);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que donar de baixa un estudiant amb un usuari no autoritzat i estudiant existent
     */
    @Test
    public void provaBaixaEstudiantNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 21);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que donar de baixa un estudiant amb un usuari autoritzat i estudiant inexistent
     */
    @Test
    public void provaBaixaEstudiantAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 30);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_ESTUDIANT, resposta.get(MISSATGE));
    }

    /**
     * Mètode que donar de baixa un estudiant amb un usuari autoritzat i altres entitats relacionades a l'estudiant
     */
    @Test
    public void provaBaixaEstudiantAutoritzatDadesRelacionades() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_ELEMENTS_RELACIONATS_ESTUDIANT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que donar de baixa un estudiant amb dades incompletes
     */
    @Test
    public void provaBaixaEstudiantDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_ESTUDIANT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
}
