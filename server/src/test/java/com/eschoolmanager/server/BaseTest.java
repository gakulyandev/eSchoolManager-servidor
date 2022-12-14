/**
 * 
 */
package com.eschoolmanager.server;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;

import com.eschoolmanager.server.gestors.GestorExcepcions;
import com.eschoolmanager.server.gestors.GestorPeticions;
import com.eschoolmanager.server.gestors.GestorSessionsUsuari;
import com.eschoolmanager.server.model.Beca;
import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Escola;
import com.eschoolmanager.server.model.Estudiant;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.Professor;
import com.eschoolmanager.server.model.SessioUsuari;
import com.eschoolmanager.server.model.Usuari;
import com.eschoolmanager.server.utilitats.Constants;
import com.eschoolmanager.server.model.Servei;

/**
 * @author Gayané Akulyan Akulyan
 * Classe base que extenen els tests amb funcionalitats genèriques
 */
public class BaseTest implements Constants {
	
	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;
	protected GestorPeticions gestorPeticions;
	protected GestorSessionsUsuari gestorSessionsUsuari;
	protected JSONObject peticio, dadesPeticio, resposta, dadesResposta;

	protected final static String PERSISTENCE_UNIT = "eSchoolManager";
    
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
        
		// Creació de permisos
		Permis permisAcces = new Permis("acces","LOGIN;LOGOUT");
		Permis permisEscola = new Permis("escola","CONSULTA ESCOLA;MODI ESCOLA");
		Permis permisDepartament = new Permis("departament","ALTA DEPARTAMENT;BAIXA DEPARTAMENT;MODI DEPARTAMENT;LLISTA DEPARTAMENTS;CONSULTA DEPARTAMENT");
		Permis permisEmpleat = new Permis("empleat","ALTA EMPLEAT;BAIXA EMPLEAT;MODI EMPLEAT;LLISTA EMPLEATS;CONSULTA EMPLEAT");
		Permis permisServei = new Permis("servei","ALTA SERVEI;BAIXA SERVEI;MODI SERVEI;LLISTA SERVEIS;CONSULTA SERVEI");
		Permis permisEstudiant = new Permis("estudiant","ALTA ESTUDIANT;BAIXA ESTUDIANT;MODI ESTUDIANT;LLISTA ESTUDIANTS;CONSULTA ESTUDIANT");
		Permis permisBeca = new Permis("beca","ALTA BECA;BAIXA BECA;MODI BECA;LLISTA BEQUES;CONSULTA BECA");
		Permis permisSessio = new Permis("sessio","ALTA SESSIO;BAIXA SESSIO;MODI SESSIO;LLISTA SESSIONS;CONSULTA SESSIO");
		Permis permisInforme = new Permis("informe","LLISTA DADES");
		
		List<Permis> permisosAdministrador = new ArrayList<Permis>();
		List<Permis> permisosAdministratiu = new ArrayList<Permis>();
		List<Permis> permisosDocent = new ArrayList<Permis>();
		
		permisosAdministrador.add(permisAcces);
		permisosAdministrador.add(permisEscola);
		permisosAdministrador.add(permisDepartament);
		permisosAdministrador.add(permisEmpleat);
		permisosAdministrador.add(permisServei);
		permisosAdministrador.add(permisEstudiant);
		permisosAdministrador.add(permisBeca);
		permisosAdministrador.add(permisSessio);
		permisosAdministrador.add(permisInforme);
		
		permisosAdministratiu.add(permisAcces);
		permisosAdministratiu.add(permisEmpleat);
		permisosAdministratiu.add(permisServei);
		permisosAdministratiu.add(permisEstudiant);
		permisosAdministratiu.add(permisBeca);
		permisosAdministratiu.add(permisSessio);
		permisosAdministratiu.add(permisInforme);
		
		permisosDocent.add(permisAcces);
		permisosDocent.add(permisSessio);

		// Creació dels departaments bàsics
		Departament departamentAdministrador = escola.altaDepartament("Administrador", permisosAdministrador);
		Departament departamentAdministratiu = escola.altaDepartament("Administratiu", permisosAdministratiu);
		Departament departamentDocent = escola.altaDepartament("Docent", permisosDocent);
		Departament departamentDocentBuit = escola.altaDepartament("DocentBuit", permisosDocent);
		
		departamentAdministrador.setCodi(1);
		departamentAdministratiu.setCodi(2);
		departamentDocent.setCodi(3);
		departamentDocentBuit.setCodi(4);
		
        // Creació d'empleats d'exemple
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = null;
		try {
			parsed = format.parse("1990-10-28");
		} catch (ParseException e) {
			System.out.println("S'ha produït un error");
		}
		Empleat empleatAdministrador = escola.altaEmpleat("22233344N", "Pedro", "Gomez", new Date(parsed.getTime()), "622555222", "p.gomez@gmail.com", "c/S/N, 4", departamentAdministrador);
		Empleat empleatAdministratiu = escola.altaEmpleat("55533344N", "Clara", "Carrillo", new Date(parsed.getTime()), "655666558", "c.carrillo@icloud.com", "c/Del Mar, 5", departamentAdministratiu);
		Professor empleatDocent = (Professor) escola.altaEmpleat("45628915M", "Blas", "Roig", new Date(parsed.getTime()), "658656558", "b.roig@gmail.com", "c/Del Pino, 1", departamentDocent);
		Professor empleatDocent2 = (Professor) escola.altaEmpleat("45628915L", "Blas", "Roig", new Date(parsed.getTime()), "658656558", "b.roig@gmail.com", "c/Del Pino, 1", departamentDocent);
		empleatAdministrador.setCodi(10);
		empleatAdministratiu.setCodi(11);
		empleatDocent.setCodi(12);
		empleatDocent2.setCodi(13);
		
        // Creació d'usuaris d'exemple
		Usuari usuariAdministrador = escola.altaUsuari("p.gomez", "passtest1");
		Usuari usuariAdministratiu = escola.altaUsuari("c.carrillo", "passtest2");
		Usuari usuariDocent = escola.altaUsuari("b.roig", "passtest3");
		Usuari usuariDocent2 = escola.altaUsuari("b.roig2", "passtest3");

		empleatAdministrador.assignaUsuari(usuariAdministrador);
		empleatAdministratiu.assignaUsuari(usuariAdministratiu);
		empleatDocent.assignaUsuari(usuariDocent);
		empleatDocent2.assignaUsuari(usuariDocent2);
		
		gestorSessionsUsuari.desaSessio(
				new SessioUsuari(
						"codiProvaAdministrador", 
						usuariAdministrador, 
						usuariAdministrador.getEmpleat().getCodi(), 
						usuariAdministrador.getEmpleat().getNom(), 
						usuariAdministrador.getEmpleat().getDepartament().getNom(), 
						usuariAdministrador.getEmpleat().getDepartament().getPermisos()
				)
		);
		gestorSessionsUsuari.desaSessio(
				new SessioUsuari(
						"codiProvaAdministratiu", 
						usuariAdministratiu, 
						usuariAdministratiu.getEmpleat().getCodi(), 
						usuariAdministratiu.getEmpleat().getNom(), 
						usuariAdministratiu.getEmpleat().getDepartament().getNom(), 
						usuariAdministratiu.getEmpleat().getDepartament().getPermisos()
				)
		);
		gestorSessionsUsuari.desaSessio(
				new SessioUsuari(
						"codiProvaDocent", 
						usuariDocent, 
						usuariDocent.getEmpleat().getCodi(), 
						usuariDocent.getEmpleat().getNom(), 
						usuariDocent.getEmpleat().getDepartament().getNom(), 
						usuariDocent.getEmpleat().getDepartament().getPermisos()
				)
		);

        // Creació de serveis d'exemple
		Servei serveiPsicologia = escola.altaServei("Psicologia", 25.00, 1);
		Servei serveiPsicopedagogia = escola.altaServei("Psicopedagogia", 35.00, 2);
		
		serveiPsicologia.setCodi(5);
		serveiPsicopedagogia.setCodi(6);
		serveiPsicologia.assignaProfessor(empleatDocent);
		
		// Creació d'estudiants d'exemple
		Estudiant estudiant1 = escola.altaEstudiant("55533344N", "Clara", "Carrillo", new Date(parsed.getTime()), "655666558", "c.carrillo@icloud.com", "c/Del Mar, 5");
		estudiant1.setCodi(20);
		Estudiant estudiant2 = escola.altaEstudiant("22233344N", "Pedro", "Gomez", new Date(parsed.getTime()), "622555222", "p.gomez@gmail.com", "c/S/N, 4");
		estudiant2.setCodi(21);
		
		// Creació de beques d'exemple
		Beca beca = new Beca("Entitat Prova", 1000.00, estudiant1, serveiPsicologia);
		
		insertaDades(escola);
    }
}
