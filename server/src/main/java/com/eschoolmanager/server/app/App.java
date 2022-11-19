package com.eschoolmanager.server.app;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.eschoolmanager.server.connexio.Servidor;
import com.eschoolmanager.server.gestors.GestorExcepcions;
import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Escola;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que executa l'aplicació servidor
 */
public class App 
{
	public static void main( String[] args ) {
    	
    	// Executa el servidor a espera de clients
    	Servidor servidor = new Servidor();
    	try {
    		// Alta de dades d'exemple
    		altaDades();
    		// Execució del servidor
			servidor.executa();
			
		} catch (GestorExcepcions e) {
			System.out.println(e.getMessage());
		}
    }
	
	/**
	 * Mètode que preregistra dades d'exemple
	 * @throws GestorExcepcions en cas d'error
	 */
	private static void altaDades() throws GestorExcepcions {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("eSchoolManager");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		// Creació de l'escola
		Escola escola = new Escola("Escola Prova", "c/Prova, 1", "934445556");		
		
		// Creació de permisos
		Permis permisAcces = new Permis("acces","LOGIN;LOGOUT");
		Permis permisEscola = new Permis("escola","MODI ESCOLA");
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

        // DADES D'EXEMPLE A ELIMINAR
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
		Empleat empleatDocent = new Empleat("45628915M", "Blas", "Roig", new Date(parsed.getTime()), "658656558", "b.roig@gmail.com", "c/Del Pino, 1");
		
		departamentAdministrador.altaEmpleat(empleatAdministrador);
		departamentAdministratiu.altaEmpleat(empleatAdministratiu);
		departamentDocent.altaEmpleat(empleatDocent);
		
        // Creació d'usuaris d'exemple
		Usuari usuariAdministrador = new Usuari("p.gomez", "passtest1");
		Usuari usuariAdministratiu = new Usuari("c.carrillo", "passtest2");
		Usuari usuariDocent = new Usuari("b.roig", "passtest3");
		empleatAdministrador.assignaUsuari(usuariAdministrador);
		empleatAdministratiu.assignaUsuari(usuariAdministratiu);
		empleatDocent.assignaUsuari(usuariDocent);

		escola.altaUsuari(usuariAdministrador);
		escola.altaUsuari(usuariAdministratiu);
		escola.altaUsuari(usuariDocent);
		
		// Creació de serveis d'exemple
		escola.altaServei("Psicologia", 25.00, 1);
		escola.altaServei("Psicoterapia", 35.00, 2);
		
		// Persistencia
		entityManager.getTransaction().begin();
        entityManager.persist(escola);
        entityManager.getTransaction().commit();
	}
}
