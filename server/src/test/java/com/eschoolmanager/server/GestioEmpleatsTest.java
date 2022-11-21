package com.eschoolmanager.server;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gayané Akulyan Akulyan
 * Classe per comprovar el funcionament de les gestions d'empleats
 */
public class GestioEmpleatsTest extends BaseTest {
	
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
     * Mètode que prova donar d'alta un empleat amb un usuari autoritzat i empleat o usuari inexistent
     */
    @Test
    public void provaAltaEmpleatAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "99988877A");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest4");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova donar d'alta un empleat amb un usuari autoritzat i dades incorrectes
     */
    @Test
    public void provaAltaEmpleatAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "99988877AA");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest4");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DNI_INCORRECTE, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un empleat amb un usuari no autoritzat
     */
    @Test
    public void provaAltaEmpleatNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "99988877A");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest4");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un empleat amb un usuari autoritzat i empleat existent
     */
    @Test
    public void provaAltaEmpleatAutoritzatDadesDuplicadesEmpleat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "22233344N");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest4");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DUPLICAT_EMPLEAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un empleat amb un usuari autoritzat i usuari existent
     */
    @Test
    public void provaAltaEmpleatAutoritzatDadesDuplicadesUsuari() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "99988877A");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "p.gomez");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest4");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DUPLICAT_USUARI, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un empleat amb dades incompletes
     */
    @Test
    public void provaAltaEmpleatDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz Mata");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest4");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar empleats amb un usuari autoritzat
     */
    @Test
    public void provaLlistaEmpleatsAutoritzat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_EMPLEATS);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CAMP, "nom");
    	dadesPeticio.put(DADES_ORDRE, "ASC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Blas", dadesResposta.getJSONObject("0").get(DADES_NOM_EMPLEAT_COMPLET));
    }
    
    /**
     * Mètode que prova llistar empleats amb un usuari no autoritzat
     */
    @Test
    public void provaLlistaEmpleatsNoAutoritzat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_EMPLEATS);
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
     * Mètode que prova llistar empleats amb un usuari autoritzat i dades incorrectes
     */
    @Test
    public void provaLlistaEmpleatsAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_EMPLEATS);
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
     * Mètode que prova llistar empleats amb dades incompletes
     */
    @Test
    public void provaLlistaEmpleatsDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_EMPLEATS);
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
     * Mètode que prova consultar un empleat amb un usuari autoritzat i empleat existent
     */
    @Test
    public void provaConsultaEmpleatAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 10);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Pedro", dadesResposta.get(DADES_NOM_EMPLEAT));
    }
    
    /**
     * Mètode que prova consultar un empleat amb un usuari no autoritzat i empleat existent
     */
    @Test
    public void provaConsultaEmpleatNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 10);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un empleat amb un usuari autoritzat i empleat inexistent
     */
    @Test
    public void provaConsultaEmpleatAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 20);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_EMPLEAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un empleat amb dades incompletes
     */
    @Test
    public void provaConsultaEmpleatDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un empleat amb un usuari autoritzat i empleat existent
     */
    @Test
    public void provaModiEmpleatAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "99988877A");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest5");
    	dadesPeticio.put(DADES_ESTAT_EMPLEAT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova actualitzar un empleat amb un usuari autoritzat i dades incorrectes
     */
    @Test
    public void provaModiEmpleatAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "99988877Addd");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest5");
    	dadesPeticio.put(DADES_ESTAT_EMPLEAT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DNI_INCORRECTE, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un empleat amb un usuari autoritzat i empleat no existent
     */
    @Test
    public void provaModiEmpleatAutoritzatDadesIncorrectesEmpleat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 30);
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "99988877A");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest5");
    	dadesPeticio.put(DADES_ESTAT_EMPLEAT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_EMPLEAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un empleat amb un usuari no autoritzat
     */
    @Test
    public void provaModiEmpleatNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "99988877A");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest5");
    	dadesPeticio.put(DADES_ESTAT_EMPLEAT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un empleat amb un usuari autoritzat i empleat duplicat
     */
    @Test
    public void provaModiEmpleatAutoritzatDadesDuplicadesEmpleat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "22233344N");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest5");
    	dadesPeticio.put(DADES_ESTAT_EMPLEAT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DUPLICAT_EMPLEAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un empleat amb un usuari autoritzat i usuari duplicat
     */
    @Test
    public void provaModiEmpleatAutoritzatDadesDuplicadesUsuari() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	dadesPeticio.put(DADES_DNI_EMPLEAT, "99988877A");
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "p.gomez");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest5");
    	dadesPeticio.put(DADES_ESTAT_EMPLEAT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DUPLICAT_USUARI, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova actualitzar un empleat amb dades incompletes
     */
    @Test
    public void provaModiEmpleatDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	dadesPeticio.put(DADES_NOM_EMPLEAT, "Sara");
    	dadesPeticio.put(DADES_COGNOMS_EMPLEAT, "Ruiz");
    	dadesPeticio.put(DADES_DATA_NAIXEMENT_EMPLEAT, "1991-10-28");
    	dadesPeticio.put(DADES_ADRECA_EMPLEAT, "C/De l'aigua 1");
    	dadesPeticio.put(DADES_TELEFON_EMPLEAT, "666888999");
    	dadesPeticio.put(DADES_EMAIL_EMPLEAT, "s.ruiz@gmail.com");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "2");
    	dadesPeticio.put(DADES_NOM_USUARI, "s.ruiz");
    	dadesPeticio.put(DADES_CONTRASENYA_USUARI, "passtest5");
    	dadesPeticio.put(DADES_ESTAT_EMPLEAT, false);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
	/**
     * Mètode que donar de baixa un empleat amb un usuari autoritzat i empleat existent
     */
    @Test
    public void provaBaixaEmpleatAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 13);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que donar de baixa un empleat amb un usuari no autoritzat i empleat existent
     */
    @Test
    public void provaBaixaEmpleatNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 13);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que donar de baixa un empleat amb un usuari autoritzat i empleat inexistent
     */
    @Test
    public void provaBaixaEmpleatAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 30);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_DEPARTAMENT, resposta.get(MISSATGE));
    }

    /**
     * Mètode que donar de baixa un empleat amb un usuari autoritzat i altres entitats relacionades a l'empleat
     */
    @Test
    public void provaBaixaEmpleatAutoritzatDadesRelacionades() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_ELEMENTS_RELACIONATS_EMPLEAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que donar de baixa un empleat amb dades incompletes
     */
    @Test
    public void provaBaixaEmpleatDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_EMPLEAT);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
}
