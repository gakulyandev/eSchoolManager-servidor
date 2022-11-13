/**
 * 
 */
package com.eschoolmanager.server;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;

import com.eschoolmanager.server.gestors.GestorExcepcions;
import com.eschoolmanager.server.gestors.GestorPeticions;
import com.eschoolmanager.server.gestors.GestorSessionsUsuari;
import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Escola;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.SessioUsuari;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe base que extenen els tests amb funcionalitats genèriques
 */
public class BaseTest {
	
	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;
	protected GestorPeticions gestorPeticions;
	protected GestorSessionsUsuari gestorSessionsUsuari;
	protected JSONObject peticio, dadesPeticio, resposta, dadesResposta;
	protected final static String CRIDA = "crida";
	protected final static String PERSISTENCE_UNIT = "eSchoolManager";
	
	protected final static String CODI_SESSIO = "codiSessio";
	protected final static String DADES = "dades";
	protected final static String RESPOSTA = "resposta";
	protected final static String RESPOSTA_OK = "OK";
	protected final static String RESPOSTA_NOK = "NOK";
	protected final static String MISSATGE = "missatge";
    
	/**
     * Neteja la base de dades i l'omple amb dades de prova
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	gestorSessionsUsuari = new GestorSessionsUsuari();
    	obreGestor();
    	generaDades();
        
        gestorPeticions = new GestorPeticions(entityManager, gestorSessionsUsuari);
        peticio = new JSONObject();
        dadesPeticio = new JSONObject();
        dadesResposta = new JSONObject();
    }
    
    /**
     * Tanca l'entityManager i la factoria d'entitats en acabar els tests
     */
    @After()
    public void classEnds() {
    	peticio = null;
        dadesPeticio = null;
        dadesResposta = null;
    	tancaGestor();
    }
    
    /**
     * Inserta dades d'exemple a la base de dades
     */
    private void insertaDades(Object objecte) {
		entityManager.getTransaction().begin();
        entityManager.persist(objecte);
        entityManager.getTransaction().commit();
        entityManager.detach(objecte);    
    }
    
    /**
     * Obre l'entityManager
     */
    private void obreGestor() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            entityManager = entityManagerFactory.createEntityManager();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tanca l'entityManager
     */
    private void tancaGestor() {
    	entityManager.clear();
    	entityManager.close();
        entityManagerFactory.close();
    }
    
    /**
     * Genera dades d'exemple a la base de dades
     * @throws GestorExcepcions en cas d'error
     */
    private void generaDades() throws GestorExcepcions {
    	
    	// Creació de l'escola
		Escola escola = new Escola("Escola Prova", "c/Prova, 1", "934445556");		
        
		// Creació dels departaments bàsics
        Departament departamentAdministrador = new Departament("Administrador");
		Departament departamentAdministratiu = new Departament("Administratiu");
		escola.altaDepartament(departamentAdministrador);
		escola.altaDepartament(departamentAdministratiu);
		
		// Adjudicació de permisos als departaments bàsics
		Permis permisAcces = new Permis("acces","LOGIN;LOGOUT");
		Permis permisEscola = new Permis("escola","MODI ESCOLA");
		Permis permisDepartament = new Permis("departament","ALTA DEPARTAMENT;BAIXA DEPARTAMENT;MODI DEPARTAMENT;LLISTA DEPARTAMENTS;CONSULTA DEPARTAMENT");
		Permis permisEmpleat = new Permis("empleat","ALTA EMPLEAT;BAIXA EMPLEAT;MODI EMPLEAT;LLISTA EMPLEATS;CONSULTA EMPLEAT");
		Permis permisServei = new Permis("servei","ALTA SERVEI;BAIXA SERVEI;MODI SERVEI;LLISTA SERVEIS;CONSULTA SERVEI");
		Permis permisEstudiant = new Permis("estudiant","ALTA ESTUDIANT;BAIXA ESTUDIANT;MODI ESTUDIANT;LLISTA ESTUDIANTS;CONSULTA ESTUDIANT");
		Permis permisBeca = new Permis("beca","ALTA BECA;BAIXA BECA;MODI BECA;LLISTA BEQUES;CONSULTA BECA");
		Permis permisSessio = new Permis("sessio","ALTA SESSIO;BAIXA SESSIO;MODI SESSIO;LLISTA SESSIONS;CONSULTA SESSIO");
		Permis permisInforme = new Permis("informe","LLISTA DADES");
		
		departamentAdministrador.adjudicaPermis(permisAcces);
		departamentAdministratiu.adjudicaPermis(permisAcces);
		
		departamentAdministrador.adjudicaPermis(permisEscola);
		departamentAdministrador.adjudicaPermis(permisDepartament);
		departamentAdministrador.adjudicaPermis(permisEmpleat);
		departamentAdministrador.adjudicaPermis(permisServei);
		departamentAdministrador.adjudicaPermis(permisEstudiant);
		departamentAdministrador.adjudicaPermis(permisBeca);
		departamentAdministrador.adjudicaPermis(permisSessio);
		departamentAdministrador.adjudicaPermis(permisInforme);
		
		departamentAdministratiu.adjudicaPermis(permisEmpleat);
		departamentAdministratiu.adjudicaPermis(permisServei);
		departamentAdministratiu.adjudicaPermis(permisEstudiant);
		departamentAdministratiu.adjudicaPermis(permisBeca);
		departamentAdministratiu.adjudicaPermis(permisSessio);
		departamentAdministratiu.adjudicaPermis(permisInforme);
		
        // Creació d'empleats d'exemple
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = null;
		try {
			parsed = format.parse("1990-10-28");
		} catch (ParseException e) {
			System.out.println("S'ha produït un error");
		}
		Empleat empleatAdministrador = new Empleat("22233344N", "Pedro", "Gomez", new Date(parsed.getTime()), "622555222", "p.gomez@gmail.com", "c/S/N, 4");
		Empleat empleatAdministratiu = new Empleat("55533344N", "Clara", "Carrillo", new Date(parsed.getTime()), "655666558", "c.carrillo@icloud.com", "c/Del Mar, 5");
		
		departamentAdministrador.altaEmpleat(empleatAdministrador);
		departamentAdministratiu.altaEmpleat(empleatAdministratiu);
		
        // Creació d'usuaris d'exemple
		Usuari usuariAdministrador = new Usuari("p.gomez", "passtest1");
		Usuari usuariAdministratiu = new Usuari("c.carrillo", "passtest2");
		empleatAdministrador.assignaUsuari(usuariAdministrador);
		empleatAdministratiu.assignaUsuari(usuariAdministratiu);
		
		gestorSessionsUsuari.desaSessio(
				new SessioUsuari(
						"codiProva1", 
						usuariAdministrador, 
						usuariAdministrador.getEmpleat().getNom(), 
						usuariAdministrador.getEmpleat().getDepartament().getNom(), 
						usuariAdministrador.getEmpleat().getDepartament().getPermisos()
				)
		);
		gestorSessionsUsuari.desaSessio(
				new SessioUsuari(
						"codiProva2", 
						usuariAdministratiu, 
						usuariAdministratiu.getEmpleat().getNom(), 
						usuariAdministratiu.getEmpleat().getDepartament().getNom(), 
						usuariAdministratiu.getEmpleat().getDepartament().getPermisos()
				)
		);

		escola.altaUsuari(usuariAdministrador);
		escola.altaUsuari(usuariAdministratiu);
    	
		
		insertaDades(escola);
    }
}
