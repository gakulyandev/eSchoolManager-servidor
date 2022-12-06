/**
 * 
 */
package com.eschoolmanager.server.gestors;

import com.eschoolmanager.server.model.SessioUsuari;
import com.eschoolmanager.server.utilitats.Constants;
import javax.persistence.EntityManager;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que processa les diferents peticions dels clients i genera la resposta corresponent
 */
public class GestorPeticions implements Constants {

	private GestorEscola gestorEscola;
	private GestorSessioUsuari gestorSessioUsuari;
	private GestorDepartament gestorDepartament;
	private GestorServei gestorServei;
	private GestorEmpleat gestorEmpleat;
	private GestorEstudiant gestorEstudiant;
	private GestorBeca gestorBeca;
	private GestorSessio gestorSessio;
	
	/**
     * Constructor que associa i inicialitza els diferents gestors
     * @param entityManager EntityManager al qual s'associa el gestor
	 * @throws GestorExcepcions 
     */
	public GestorPeticions(EntityManager entityManager, GestorSessionsUsuari gestorSessionsUsuari) throws GestorExcepcions {
		this.gestorEscola = new GestorEscola(entityManager);
		this.gestorSessioUsuari = new GestorSessioUsuari(gestorSessionsUsuari, entityManager);
		this.gestorDepartament = new GestorDepartament(entityManager);
		this.gestorServei = new GestorServei(entityManager);
		this.gestorEmpleat = new GestorEmpleat(entityManager);
		this.gestorEstudiant = new GestorEstudiant(entityManager);
		this.gestorBeca = new GestorBeca(entityManager);
		this.gestorSessio = new GestorSessio(entityManager);
	}

	/**
     * Processa la petició del client i genera una resposta del servidor
     * @param peticio que envia el client
     * @return resposta que envia el servidor una vegada processada la petició
     */
	public String generaResposta(String peticioString) {
				
		JSONObject peticio = new JSONObject(peticioString);
		JSONObject dadesPeticio = new JSONObject();

		try {			
			
			String crida = peticio.getString(CRIDA);
		
			// Necessari per validar permisos del propi empleat per consultar i modificar les seves dades
			int codiEmpleat = -1;
			if (crida.equals(CRIDA_CONSULTA_EMPLEAT) || crida.equals(CRIDA_MODI_EMPLEAT)) {
				codiEmpleat = peticio.getJSONObject(DADES).getInt(DADES_CODI_EMPLEAT);
			}
			
			// Troba la sessió i confirma el permis de crida per l'usuari amb la sessió iniciada
			if (!crida.equals(CRIDA_LOGIN)) {
				SessioUsuari sessio = gestorSessioUsuari.trobaSessio(peticio.getString(CODI_SESSIO));
				if (!gestorSessioUsuari.confirmaPermis(sessio, crida, codiEmpleat)) {
					throw new GestorExcepcions(ERROR_NO_AUTORITZAT);
				}
			}
			
			// Gestió de la petició segons tipus de crida
			JSONObject dadesResposta = new JSONObject();
			
			switch (crida) {
				case CRIDA_LOGIN: {
					// Processa la petició i obté una sessió d'usuari
					dadesPeticio = peticio.getJSONObject(DADES);
					HashMap<String, Object> dadesSessio = gestorSessioUsuari.iniciaSessio(
							dadesPeticio.getString(DADES_NOM_USUARI), 
							dadesPeticio.getString(DADES_CONTRASENYA)
					);
					// Genera resposta
					dadesResposta.put(DADES_CODI_SESSIO, dadesSessio.get(DADES_CODI_SESSIO));
					dadesResposta.put(DADES_CODI_EMPLEAT, dadesSessio.get(DADES_CODI_EMPLEAT));
					dadesResposta.put(DADES_NOM_EMPLEAT, dadesSessio.get(DADES_NOM_EMPLEAT));
					dadesResposta.put(DADES_NOM_DEPARTAMENT, dadesSessio.get(DADES_NOM_DEPARTAMENT));
					
					JSONObject dadesRespostaPermisos = new JSONObject();
					List<String> permisosDepartament = (List<String>) dadesSessio.get(DADES_PERMISOS_DEPARTAMENT);
					for (String permisNom : PERMISOS_NOMS) {
						dadesRespostaPermisos.put(permisNom, false);
						for (String permisDepartament : permisosDepartament) {
							if(permisDepartament.equals(permisNom)) {
								dadesRespostaPermisos.put(permisNom, true);
							}
						}
					}
					dadesResposta.put(DADES_PERMISOS_DEPARTAMENT, dadesRespostaPermisos);

					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_LOGOUT: {
					// Processa la petició
					gestorSessioUsuari.tancaSessio(peticio.getString(CODI_SESSIO));
					
					// Genera resposta
					return generaRespostaOK(null);
				}

				case CRIDA_CONSULTA_ESCOLA: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);

					HashMap<String, Object> dadesServei = gestorEscola.consulta();
					
					// Genera resposta
					dadesResposta.put(DADES_NOM_ESCOLA, dadesServei.get(DADES_NOM_ESCOLA));
					dadesResposta.put(DADES_ADRECA_ESCOLA, dadesServei.get(DADES_ADRECA_ESCOLA));
					dadesResposta.put(DADES_TELEFON_ESCOLA, dadesServei.get(DADES_TELEFON_ESCOLA));
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_MODI_ESCOLA: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);

					gestorEscola.actualitza(
							dadesPeticio.getString(DADES_NOM_ESCOLA), 
							dadesPeticio.getString(DADES_ADRECA_ESCOLA), 
							dadesPeticio.getString(DADES_TELEFON_ESCOLA)
							);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_ALTA_DEPARTAMENT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
				    HashMap<String, Boolean> permisos = new HashMap<String, Boolean>();
					JSONObject dadesPeticioPermisos = dadesPeticio.getJSONObject(DADES_PERMISOS_DEPARTAMENT);
					Iterator<?> keysPermisosPeticio = dadesPeticioPermisos.keys();
					while(keysPermisosPeticio.hasNext()) {
					    String key = (String) keysPermisosPeticio.next();
					    permisos.put(key, dadesPeticioPermisos.getBoolean(key));
					}
					
					gestorDepartament.alta(dadesPeticio.getString(DADES_NOM_DEPARTAMENT), permisos);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_LLISTA_DEPARTAMENTS: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					HashMap<Integer, Object> dadesDepartaments = gestorDepartament.llista(dadesPeticio.getString(DADES_CAMP), dadesPeticio.getString(DADES_ORDRE));

					// Genera resposta
					dadesResposta = new JSONObject();
					
					for (Integer key : dadesDepartaments.keySet()) {
						JSONObject dadesRespostaDepartament = new JSONObject();
						dadesRespostaDepartament.put(DADES_CODI_DEPARTAMENT, ((HashMap<String,Object>) dadesDepartaments.get(key)).get(DADES_CODI_DEPARTAMENT));
						dadesRespostaDepartament.put(DADES_NOM_DEPARTAMENT, ((HashMap<String,Object>) dadesDepartaments.get(key)).get(DADES_NOM_DEPARTAMENT));
						dadesResposta.put(String.valueOf(key), dadesRespostaDepartament);
					}
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_CONSULTA_DEPARTAMENT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					HashMap<String, Object> dadesDepartament = gestorDepartament.consulta(dadesPeticio.getInt(DADES_CODI_DEPARTAMENT));

					// Genera resposta
					dadesResposta.put(DADES_NOM_DEPARTAMENT, dadesDepartament.get(DADES_NOM_DEPARTAMENT));
					dadesResposta.put(DADES_CODI_DEPARTAMENT, dadesDepartament.get(DADES_CODI_DEPARTAMENT));
					
					JSONObject dadesRespostaPermisos = new JSONObject();
					List<String> permisosDepartament = (List<String>) dadesDepartament.get(DADES_PERMISOS_DEPARTAMENT);
					for (String permisNom : PERMISOS_NOMS) {
						dadesRespostaPermisos.put(permisNom, false);
						for (String permisDepartament : permisosDepartament) {
							if(permisDepartament.equals(permisNom)) {
								dadesRespostaPermisos.put(permisNom, true);
							}
						}
					}
					dadesResposta.put(DADES_PERMISOS_DEPARTAMENT, dadesRespostaPermisos);
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_MODI_DEPARTAMENT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
				    HashMap<String, Boolean> permisos = new HashMap<String, Boolean>();
					JSONObject dadesPeticioPermisos = dadesPeticio.getJSONObject(DADES_PERMISOS_DEPARTAMENT);
					Iterator<?> keysPermisosPeticio = dadesPeticioPermisos.keys();
					while(keysPermisosPeticio.hasNext()) {
					    String key = (String) keysPermisosPeticio.next();
					    permisos.put(key, dadesPeticioPermisos.getBoolean(key));
					}

					gestorDepartament.actualitza(
							dadesPeticio.getInt(DADES_CODI_DEPARTAMENT), 
							dadesPeticio.getString(DADES_NOM_DEPARTAMENT), 
							permisos);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_BAIXA_DEPARTAMENT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					gestorDepartament.baixa(dadesPeticio.getInt(DADES_CODI_DEPARTAMENT));

					// Genera resposta					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_ALTA_SERVEI: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					gestorServei.alta(
							dadesPeticio.getString(DADES_NOM_SERVEI),
							dadesPeticio.getDouble(DADES_COST_SERVEI),
							dadesPeticio.getInt(DADES_DURADA_SERVEI)
					);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_LLISTA_SERVEIS: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					HashMap<Integer, Object> dadesServeis = gestorServei.llista(dadesPeticio.getString(DADES_CAMP), dadesPeticio.getString(DADES_ORDRE));

					// Genera resposta
					dadesResposta = new JSONObject();
					
					for (Integer key : dadesServeis.keySet()) {
						JSONObject dadesRespostaServei = new JSONObject();
						dadesRespostaServei.put(DADES_CODI_SERVEI, ((HashMap<String,Object>) dadesServeis.get(key)).get(DADES_CODI_SERVEI));
						dadesRespostaServei.put(DADES_NOM_SERVEI, ((HashMap<String,Object>) dadesServeis.get(key)).get(DADES_NOM_SERVEI));
						dadesRespostaServei.put(DADES_DURADA_SERVEI, ((HashMap<String,Object>) dadesServeis.get(key)).get(DADES_DURADA_SERVEI));
						dadesRespostaServei.put(DADES_COST_SERVEI, ((HashMap<String,Object>) dadesServeis.get(key)).get(DADES_COST_SERVEI));
						dadesResposta.put(String.valueOf(key), dadesRespostaServei);
					}
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_CONSULTA_SERVEI: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);

					HashMap<String, Object> dadesServei = gestorServei.consulta(dadesPeticio.getInt(DADES_CODI_SERVEI));
					
					// Genera resposta
					dadesResposta.put(DADES_CODI_SERVEI, dadesServei.get(DADES_CODI_SERVEI));
					dadesResposta.put(DADES_NOM_SERVEI, dadesServei.get(DADES_NOM_SERVEI));
					dadesResposta.put(DADES_DURADA_SERVEI, dadesServei.get(DADES_DURADA_SERVEI));
					dadesResposta.put(DADES_COST_SERVEI, dadesServei.get(DADES_COST_SERVEI));
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_MODI_SERVEI: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);

					gestorServei.actualitza(
							dadesPeticio.getInt(DADES_CODI_SERVEI), 
							dadesPeticio.getString(DADES_NOM_SERVEI), 
							dadesPeticio.getDouble(DADES_COST_SERVEI), 
							dadesPeticio.getInt(DADES_DURADA_SERVEI)
							);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_BAIXA_SERVEI: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					gestorServei.baixa(dadesPeticio.getInt(DADES_CODI_SERVEI));

					// Genera resposta					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_ALTA_EMPLEAT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			        java.util.Date parsedDate = null;
					try {
						parsedDate = format.parse(dadesPeticio.getString(DADES_DATA_NAIXEMENT_EMPLEAT));
					} catch (ParseException e) {
						System.out.println(ERROR_GENERIC);
					}
					
					gestorEmpleat.alta(
							dadesPeticio.getString(DADES_DNI_EMPLEAT),
							dadesPeticio.getString(DADES_NOM_EMPLEAT),
							dadesPeticio.getString(DADES_COGNOMS_EMPLEAT),
							new Date(parsedDate.getTime()),
							dadesPeticio.getString(DADES_TELEFON_EMPLEAT),
							dadesPeticio.getString(DADES_EMAIL_EMPLEAT),
							dadesPeticio.getString(DADES_ADRECA_EMPLEAT),
							dadesPeticio.getInt(DADES_CODI_DEPARTAMENT),
							dadesPeticio.getString(DADES_NOM_USUARI),
							dadesPeticio.getString(DADES_CONTRASENYA_USUARI)
					);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_LLISTA_EMPLEATS: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					HashMap<Integer, Object> dadesEmpleats = gestorEmpleat.llista(dadesPeticio.getString(DADES_CAMP), dadesPeticio.getString(DADES_ORDRE));

					// Genera resposta
					dadesResposta = new JSONObject();
					
					for (Integer key : dadesEmpleats.keySet()) {
						JSONObject dadesRespostaEmpelat = new JSONObject();
						dadesRespostaEmpelat.put(DADES_CODI_EMPLEAT, ((HashMap<String,Object>) dadesEmpleats.get(key)).get(DADES_CODI_EMPLEAT));
						dadesRespostaEmpelat.put(DADES_NOM_EMPLEAT_COMPLET, ((HashMap<String,Object>) dadesEmpleats.get(key)).get(DADES_NOM_EMPLEAT));
						dadesRespostaEmpelat.put(DADES_COGNOMS_EMPLEAT_COMPLET, ((HashMap<String,Object>) dadesEmpleats.get(key)).get(DADES_COGNOMS_EMPLEAT));
						dadesRespostaEmpelat.put(DADES_CODI_DEPARTAMENT, ((HashMap<String,Object>) dadesEmpleats.get(key)).get(DADES_CODI_DEPARTAMENT));
						dadesRespostaEmpelat.put(DADES_NOM_DEPARTAMENT, ((HashMap<String,Object>) dadesEmpleats.get(key)).get(DADES_NOM_DEPARTAMENT));
						dadesResposta.put(String.valueOf(key), dadesRespostaEmpelat);
					}
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_CONSULTA_EMPLEAT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);

					HashMap<String, Object> dadesEmpleat = gestorEmpleat.consulta(dadesPeticio.getInt(DADES_CODI_EMPLEAT));
					
					// Genera resposta
					dadesResposta.put(DADES_CODI_EMPLEAT, dadesEmpleat.get(DADES_CODI_EMPLEAT));
					dadesResposta.put(DADES_DNI_EMPLEAT, dadesEmpleat.get(DADES_DNI_EMPLEAT));
					dadesResposta.put(DADES_NOM_EMPLEAT, dadesEmpleat.get(DADES_NOM_EMPLEAT));
					dadesResposta.put(DADES_COGNOMS_EMPLEAT, dadesEmpleat.get(DADES_COGNOMS_EMPLEAT));
					dadesResposta.put(DADES_DATA_NAIXEMENT_EMPLEAT, dadesEmpleat.get(DADES_DATA_NAIXEMENT_EMPLEAT));
					dadesResposta.put(DADES_ADRECA_EMPLEAT, dadesEmpleat.get(DADES_ADRECA_EMPLEAT));
					dadesResposta.put(DADES_TELEFON_EMPLEAT, dadesEmpleat.get(DADES_TELEFON_EMPLEAT));
					dadesResposta.put(DADES_EMAIL_EMPLEAT, dadesEmpleat.get(DADES_EMAIL_EMPLEAT));
					dadesResposta.put(DADES_CODI_DEPARTAMENT, dadesEmpleat.get(DADES_CODI_DEPARTAMENT));
					dadesResposta.put(DADES_NOM_DEPARTAMENT, dadesEmpleat.get(DADES_NOM_DEPARTAMENT));
					dadesResposta.put(DADES_NOM_USUARI, dadesEmpleat.get(DADES_NOM_USUARI));
					dadesResposta.put(DADES_ESTAT_EMPLEAT, dadesEmpleat.get(DADES_ESTAT_EMPLEAT));
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_MODI_EMPLEAT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			        java.util.Date parsedDate = null;
					try {
						parsedDate = format.parse(dadesPeticio.getString(DADES_DATA_NAIXEMENT_EMPLEAT));
					} catch (ParseException e) {
						System.out.println(ERROR_GENERIC);
					}
					
					gestorEmpleat.actualitza(
							dadesPeticio.getInt(DADES_CODI_EMPLEAT),
							dadesPeticio.getString(DADES_DNI_EMPLEAT),
							dadesPeticio.getString(DADES_NOM_EMPLEAT),
							dadesPeticio.getString(DADES_COGNOMS_EMPLEAT),
							new Date(parsedDate.getTime()),
							dadesPeticio.getString(DADES_TELEFON_EMPLEAT),
							dadesPeticio.getString(DADES_EMAIL_EMPLEAT),
							dadesPeticio.getString(DADES_ADRECA_EMPLEAT),
							dadesPeticio.getInt(DADES_CODI_DEPARTAMENT),
							dadesPeticio.getString(DADES_NOM_USUARI),
							dadesPeticio.getString(DADES_CONTRASENYA_USUARI),
							dadesPeticio.getBoolean(DADES_ESTAT_EMPLEAT)
					);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_BAIXA_EMPLEAT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					gestorEmpleat.baixa(dadesPeticio.getInt(DADES_CODI_EMPLEAT));

					// Genera resposta					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_ALTA_ESTUDIANT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			        java.util.Date parsedDate = null;
					try {
						parsedDate = format.parse(dadesPeticio.getString(DADES_DATA_NAIXEMENT_ESTUDIANT));
					} catch (ParseException e) {
						System.out.println(ERROR_GENERIC);
					}
					
					gestorEstudiant.alta(
							dadesPeticio.getString(DADES_DNI_ESTUDIANT),
							dadesPeticio.getString(DADES_NOM_ESTUDIANT),
							dadesPeticio.getString(DADES_COGNOMS_ESTUDIANT),
							new Date(parsedDate.getTime()),
							dadesPeticio.getString(DADES_TELEFON_ESTUDIANT),
							dadesPeticio.getString(DADES_EMAIL_ESTUDIANT),
							dadesPeticio.getString(DADES_ADRECA_ESTUDIANT)
					);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_LLISTA_ESTUDIANTS: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					HashMap<Integer, Object> dadesEstudiants = gestorEstudiant.llista(dadesPeticio.getString(DADES_CAMP), dadesPeticio.getString(DADES_ORDRE));

					// Genera resposta
					dadesResposta = new JSONObject();
					
					for (Integer key : dadesEstudiants.keySet()) {
						JSONObject dadesRespostaEstudiant = new JSONObject();
						dadesRespostaEstudiant.put(DADES_CODI_ESTUDIANT, ((HashMap<String,Object>) dadesEstudiants.get(key)).get(DADES_CODI_ESTUDIANT));
						dadesRespostaEstudiant.put(DADES_NOM_ESTUDIANT, ((HashMap<String,Object>) dadesEstudiants.get(key)).get(DADES_NOM_ESTUDIANT));
						dadesRespostaEstudiant.put(DADES_COGNOMS_ESTUDIANT, ((HashMap<String,Object>) dadesEstudiants.get(key)).get(DADES_COGNOMS_ESTUDIANT));
						dadesResposta.put(String.valueOf(key), dadesRespostaEstudiant);
					}
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_CONSULTA_ESTUDIANT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);

					HashMap<String, Object> dadesEstudiant = gestorEstudiant.consulta(dadesPeticio.getInt(DADES_CODI_ESTUDIANT));
					
					// Genera resposta
					dadesResposta.put(DADES_CODI_ESTUDIANT, dadesEstudiant.get(DADES_CODI_ESTUDIANT));
					dadesResposta.put(DADES_DNI_ESTUDIANT, dadesEstudiant.get(DADES_DNI_ESTUDIANT));
					dadesResposta.put(DADES_NOM_ESTUDIANT, dadesEstudiant.get(DADES_NOM_ESTUDIANT));
					dadesResposta.put(DADES_COGNOMS_ESTUDIANT, dadesEstudiant.get(DADES_COGNOMS_ESTUDIANT));
					dadesResposta.put(DADES_DATA_NAIXEMENT_ESTUDIANT, dadesEstudiant.get(DADES_DATA_NAIXEMENT_ESTUDIANT));
					dadesResposta.put(DADES_ADRECA_ESTUDIANT, dadesEstudiant.get(DADES_ADRECA_ESTUDIANT));
					dadesResposta.put(DADES_TELEFON_ESTUDIANT, dadesEstudiant.get(DADES_TELEFON_ESTUDIANT));
					dadesResposta.put(DADES_EMAIL_ESTUDIANT, dadesEstudiant.get(DADES_EMAIL_ESTUDIANT));
					dadesResposta.put(DADES_ESTAT_ESTUDIANT, dadesEstudiant.get(DADES_ESTAT_ESTUDIANT));
					dadesResposta.put(DADES_IMPORT_IMPUTAT_ESTUDIANT, dadesEstudiant.get(DADES_IMPORT_IMPUTAT_ESTUDIANT));
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_MODI_ESTUDIANT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			        java.util.Date parsedDate = null;
					try {
						parsedDate = format.parse(dadesPeticio.getString(DADES_DATA_NAIXEMENT_ESTUDIANT));
					} catch (ParseException e) {
						System.out.println(ERROR_GENERIC);
					}
					
					gestorEstudiant.actualitza(
							dadesPeticio.getInt(DADES_CODI_ESTUDIANT),
							dadesPeticio.getString(DADES_DNI_ESTUDIANT),
							dadesPeticio.getString(DADES_NOM_ESTUDIANT),
							dadesPeticio.getString(DADES_COGNOMS_ESTUDIANT),
							new Date(parsedDate.getTime()),
							dadesPeticio.getString(DADES_TELEFON_ESTUDIANT),
							dadesPeticio.getString(DADES_EMAIL_ESTUDIANT),
							dadesPeticio.getString(DADES_ADRECA_ESTUDIANT),
							dadesPeticio.getBoolean(DADES_ESTAT_ESTUDIANT)
					);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_BAIXA_ESTUDIANT: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					gestorEstudiant.baixa(dadesPeticio.getInt(DADES_CODI_ESTUDIANT));

					// Genera resposta					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_ALTA_BECA: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					gestorBeca.alta(
							dadesPeticio.getString(DADES_ADJUDICANT_BECA),
							dadesPeticio.getDouble(DADES_IMPORT_INICIAL_BECA),
							dadesPeticio.getInt(DADES_CODI_ESTUDIANT),
							dadesPeticio.getInt(DADES_CODI_SERVEI)
					);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_LLISTA_BEQUES: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					HashMap<Integer, Object> dadesBeques = gestorBeca.llista(dadesPeticio.getString(DADES_CAMP), dadesPeticio.getString(DADES_ORDRE));

					// Genera resposta
					dadesResposta = new JSONObject();
					
					for (Integer key : dadesBeques.keySet()) {
						JSONObject dadesRespostaBeca = new JSONObject();
						dadesRespostaBeca.put(DADES_CODI_BECA, ((HashMap<String,Object>) dadesBeques.get(key)).get(DADES_CODI_BECA));
						dadesRespostaBeca.put(DADES_ADJUDICANT_BECA, ((HashMap<String,Object>) dadesBeques.get(key)).get(DADES_ADJUDICANT_BECA));
						dadesRespostaBeca.put(DADES_IMPORT_INICIAL_BECA, ((HashMap<String,Object>) dadesBeques.get(key)).get(DADES_IMPORT_INICIAL_BECA));
						dadesRespostaBeca.put(DADES_IMPORT_RESTANT_BECA, ((HashMap<String,Object>) dadesBeques.get(key)).get(DADES_IMPORT_RESTANT_BECA));
						dadesRespostaBeca.put(DADES_NOM_ESTUDIANT, ((HashMap<String,Object>) dadesBeques.get(key)).get(DADES_NOM_ESTUDIANT));
						dadesRespostaBeca.put(DADES_COGNOMS_ESTUDIANT_COMPLET, ((HashMap<String,Object>) dadesBeques.get(key)).get(DADES_COGNOMS_ESTUDIANT_COMPLET));
						dadesRespostaBeca.put(DADES_NOM_SERVEI, ((HashMap<String,Object>) dadesBeques.get(key)).get(DADES_NOM_SERVEI));
						dadesRespostaBeca.put(DADES_ESTAT_BECA, ((HashMap<String,Object>) dadesBeques.get(key)).get(DADES_ESTAT_BECA));
						dadesResposta.put(String.valueOf(key), dadesRespostaBeca);
					}
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_CONSULTA_BECA: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);

					HashMap<String, Object> dadesBeca = gestorBeca.consulta(dadesPeticio.getInt(DADES_CODI_BECA));
					
					// Genera resposta
					dadesResposta.put(DADES_CODI_BECA, dadesBeca.get(DADES_CODI_BECA));
					dadesResposta.put(DADES_ADJUDICANT_BECA, dadesBeca.get(DADES_ADJUDICANT_BECA));
					dadesResposta.put(DADES_IMPORT_INICIAL_BECA, dadesBeca.get(DADES_IMPORT_INICIAL_BECA));
					dadesResposta.put(DADES_IMPORT_RESTANT_BECA, dadesBeca.get(DADES_IMPORT_RESTANT_BECA));
					dadesResposta.put(DADES_ESTAT_BECA, dadesBeca.get(DADES_ESTAT_BECA));
					dadesResposta.put(DADES_CODI_ESTUDIANT, dadesBeca.get(DADES_CODI_ESTUDIANT));
					dadesResposta.put(DADES_NOM_ESTUDIANT, dadesBeca.get(DADES_NOM_ESTUDIANT));
					dadesResposta.put(DADES_COGNOMS_ESTUDIANT_COMPLET, dadesBeca.get(DADES_COGNOMS_ESTUDIANT_COMPLET));
					dadesResposta.put(DADES_CODI_SERVEI, dadesBeca.get(DADES_CODI_SERVEI));
					dadesResposta.put(DADES_NOM_SERVEI, dadesBeca.get(DADES_NOM_SERVEI));
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_MODI_BECA: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					gestorBeca.actualitza(
							dadesPeticio.getInt(DADES_CODI_BECA),
							dadesPeticio.getString(DADES_ADJUDICANT_BECA),
							dadesPeticio.getDouble(DADES_IMPORT_INICIAL_BECA),
							dadesPeticio.getInt(DADES_CODI_ESTUDIANT),
							dadesPeticio.getInt(DADES_CODI_SERVEI),
							dadesPeticio.getBoolean(DADES_ESTAT_BECA)
					);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_BAIXA_BECA: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					gestorBeca.baixa(dadesPeticio.getInt(DADES_CODI_BECA));

					// Genera resposta					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_ALTA_SESSIO: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        java.util.Date parsedDate = null;
					try {
						parsedDate = format.parse(dadesPeticio.getString(DADES_DATA_I_HORA));
					} catch (ParseException e) {
						System.out.println(ERROR_GENERIC);
					}
					
					gestorSessio.alta(
							dadesPeticio.getInt(DADES_CODI_EMPLEAT),
							dadesPeticio.getInt(DADES_CODI_ESTUDIANT),
							dadesPeticio.getInt(DADES_CODI_SERVEI),
							new Date(parsedDate.getTime())
					);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_LLISTA_SESSIONS: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					HashMap<Integer, Object> dadesSessions = gestorSessio.llista(dadesPeticio.getString(DADES_CAMP), dadesPeticio.getString(DADES_ORDRE));

					// Genera resposta
					dadesResposta = new JSONObject();
					
					for (Integer key : dadesSessions.keySet()) {
						JSONObject dadesRespostaSessio = new JSONObject();
						dadesRespostaSessio.put(DADES_CODI_SESSIO, ((HashMap<String,Object>) dadesSessions.get(key)).get(DADES_CODI_SESSIO));
						dadesRespostaSessio.put(DADES_NOM_PROFESSOR, ((HashMap<String,Object>) dadesSessions.get(key)).get(DADES_NOM_PROFESSOR));
						dadesRespostaSessio.put(DADES_COGNOMS_PROFESSOR, ((HashMap<String,Object>) dadesSessions.get(key)).get(DADES_COGNOMS_PROFESSOR));
						dadesRespostaSessio.put(DADES_NOM_ESTUDIANT, ((HashMap<String,Object>) dadesSessions.get(key)).get(DADES_NOM_ESTUDIANT));
						dadesRespostaSessio.put(DADES_COGNOMS_ESTUDIANT_COMPLET, ((HashMap<String,Object>) dadesSessions.get(key)).get(DADES_COGNOMS_ESTUDIANT_COMPLET));
						dadesRespostaSessio.put(DADES_NOM_SERVEI, ((HashMap<String,Object>) dadesSessions.get(key)).get(DADES_NOM_SERVEI));
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
						String strDate = dateFormat.format(((HashMap<String,Object>) dadesSessions.get(key)).get(DADES_DATA_I_HORA));  
						dadesRespostaSessio.put(DADES_DATA_I_HORA, strDate);
						dadesResposta.put(String.valueOf(key), dadesRespostaSessio);
					}
					
					return generaRespostaOK(dadesResposta);	
				}
				case CRIDA_CONSULTA_SESSIO: {
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);

					HashMap<String, Object> dadesSessio = gestorSessio.consulta(dadesPeticio.getInt(DADES_CODI_SESSIO));
					
					// Genera resposta
					dadesResposta.put(DADES_CODI_SESSIO, dadesSessio.get(DADES_CODI_SESSIO));
					dadesResposta.put(DADES_CODI_PROFESSOR, dadesSessio.get(DADES_CODI_PROFESSOR));
					dadesResposta.put(DADES_NOM_PROFESSOR, dadesSessio.get(DADES_NOM_PROFESSOR));
					dadesResposta.put(DADES_COGNOMS_PROFESSOR, dadesSessio.get(DADES_COGNOMS_PROFESSOR));
					dadesResposta.put(DADES_CODI_ESTUDIANT, dadesSessio.get(DADES_CODI_ESTUDIANT));
					dadesResposta.put(DADES_NOM_ESTUDIANT, dadesSessio.get(DADES_NOM_ESTUDIANT));
					dadesResposta.put(DADES_COGNOMS_ESTUDIANT_COMPLET, dadesSessio.get(DADES_COGNOMS_ESTUDIANT_COMPLET));
					dadesResposta.put(DADES_CODI_SERVEI, dadesSessio.get(DADES_CODI_SERVEI));
					dadesResposta.put(DADES_NOM_SERVEI, dadesSessio.get(DADES_NOM_SERVEI));
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
					String strDate = dateFormat.format(dadesSessio.get(DADES_DATA_I_HORA));  
					dadesResposta.put(DADES_DATA_I_HORA, strDate);
					
					return generaRespostaOK(dadesResposta);	
				}
				default: {
					// Genera resposta
					return generaRespostaNOK(ERROR_GENERIC);
				}
			}
		
		} catch (JSONException ex) {
			return generaRespostaNOK(ERROR_FALTEN_DADES);
		} catch (GestorExcepcions ex) {
			return generaRespostaNOK(ex.getMessage());
		}
	}
	
	/**
     * Genera una resposta ok amb o sense dades
     * @param dades de la resposta a enviar
     * @return resposta que envia el servidor una vegada processada la petició
     */
	public static String generaRespostaOK(JSONObject dades) {
		JSONObject resposta = new JSONObject();
		resposta.put(RESPOSTA, RESPOSTA_OK);
		resposta.put(DADES, dades);
		
		return resposta.toString();
	}
	
	/**
     * Genera una resposta no ok amb un missatge
     * @param missatge de l'error a comunicar
     * @return resposta que envia el servidor una vegada processada la petició
     */
	public static String generaRespostaNOK(String missatge) {
		JSONObject resposta = new JSONObject();
		resposta.put(RESPOSTA, RESPOSTA_NOK);
		resposta.put(MISSATGE, missatge);
		
		return resposta.toString();
	}
}
