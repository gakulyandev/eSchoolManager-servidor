/**
 * 
 */
package com.eschoolmanager.server.gestors;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.SessioUsuari;


/**
 * @author Gayané Akulyan Akulyan
 * Classe que processa les diferents peticions dels clients i genera la resposta corresponent
 */
public class GestorPeticions {

	private GestorSessioUsuari gestorSessioUsuari;
	private GestorDepartament gestorDepartament;
	private GestorServei gestorServei;
	
	private final static String CRIDA = "crida";
	private final static String CRIDA_LOGIN = "LOGIN";
	private final static String CRIDA_LOGOUT = "LOGOUT";
	private final static String CRIDA_ALTA_DEPARTAMENT = "ALTA DEPARTAMENT";
	private final static String CRIDA_ALTA_SERVEI = "ALTA SERVEI";
	private final static String CRIDA_CONSULTA_DEPARTAMENT = "CONSULTA DEPARTAMENT";
	private final static String CODI_SESSIO = "codiSessio";
	private final static String RESPOSTA = "resposta";
	private final static String RESPOSTA_OK = "OK";
	private final static String RESPOSTA_NOK = "NOK";
	private final static String MISSATGE = "missatge";
	private final static String DADES = "dades";
	private final static String DADES_NOM_USUARI = "usuari";
	private final static String DADES_CONTRASENYA = "contrasenya";
	private final static String DADES_CODI_SESSIO = "codiSessio";
	private final static String DADES_CODI_DEPARTAMENT = "codiDepartament";
	private final static String DADES_NOM_DEPARTAMENT = "nomDepartament";
	private final static String DADES_NOM_SERVEI = "nom";
	private final static String DADES_DURADA_SERVEI = "durada";
	private final static String DADES_COST_SERVEI = "cost";
	private final static String DADES_NOM_EMPLEAT = "nom";
	private final static String DADES_PERMISOS_DEPARTAMENT = "permisos";
	private final static String[] PERMISOS_NOMS = {"escola","departament","empleat","estudiant","servei","beca","sessio","informe"};
	private final static String ERROR_GENERIC = "S'ha produit un error";
	private final static String ERROR_DADES = "Falten dades";
	
	/**
     * Constructor que associa i inicialitza els diferents gestors
     * @param entityManager EntityManager al qual s'associa el gestor
	 * @throws GestorExcepcions 
     */
	public GestorPeticions(EntityManager entityManager, GestorSessionsUsuari gestorSessionsUsuari) throws GestorExcepcions {
		this.gestorSessioUsuari = new GestorSessioUsuari(gestorSessionsUsuari, entityManager);
		this.gestorDepartament = new GestorDepartament(entityManager);
		this.gestorServei = new GestorServei(entityManager);
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
		
			// Valida codi de sessió i permis de crida per l'usuari amb la sessió iniciada
			if (!crida.equals(CRIDA_LOGIN)) {
				gestorSessioUsuari.validaSessio(peticio.getString(CODI_SESSIO), crida);
			}
			
			// Gestió de la petició segons tipus de crida
			JSONObject dadesResposta = new JSONObject();
			
			switch (crida) {
				case CRIDA_LOGIN:
					// Processa la petició i obté una sessió d'usuari
					dadesPeticio = peticio.getJSONObject(DADES);
					SessioUsuari sessio = gestorSessioUsuari.iniciaSessio(
							dadesPeticio.getString(DADES_NOM_USUARI), 
							dadesPeticio.getString(DADES_CONTRASENYA)
					);
					
					// Genera resposta
					dadesResposta.put(DADES_CODI_SESSIO, sessio.getCodi());
					dadesResposta.put(DADES_NOM_EMPLEAT, sessio.getNomEmpleat());
					dadesResposta.put(DADES_NOM_DEPARTAMENT, sessio.getNomDepartament());
					
					JSONObject dadesRespostaPermisos = new JSONObject();
					
					for (String permisNom : PERMISOS_NOMS) {
						dadesRespostaPermisos.put(permisNom, false);
						for (Permis permisDepartament : sessio.getPermisos()) {
							if(permisDepartament.getNom().equals(permisNom)) {
								dadesRespostaPermisos.put(permisNom, true);
							}
						}
					}
					
					dadesResposta.put(DADES_PERMISOS_DEPARTAMENT, dadesRespostaPermisos);

					return generaRespostaOK(dadesResposta);	
				
				case CRIDA_LOGOUT:
					// Processa la petició
					gestorSessioUsuari.tancaSessio(peticio.getString(CODI_SESSIO));
					
					// Genera resposta
					return generaRespostaOK(null);
					
				case CRIDA_ALTA_DEPARTAMENT:
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
					
				case CRIDA_CONSULTA_DEPARTAMENT:
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					HashMap<String, Object> dadesDepartament = gestorDepartament.consulta(dadesPeticio.getInt(DADES_CODI_DEPARTAMENT));

					// Genera resposta
					dadesResposta.put(DADES_NOM_DEPARTAMENT, dadesDepartament.get(DADES_NOM_DEPARTAMENT));
					
					dadesRespostaPermisos = new JSONObject();
					
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
					
				case CRIDA_ALTA_SERVEI:
					// Processa la petició
					dadesPeticio = peticio.getJSONObject(DADES);
					
					gestorServei.alta(
							dadesPeticio.getString(DADES_NOM_SERVEI),
							dadesPeticio.getDouble(DADES_COST_SERVEI),
							dadesPeticio.getInt(DADES_DURADA_SERVEI)
					);

					// Genera resposta
					return generaRespostaOK(dadesResposta);	
					
				default:
					// Genera resposta
					return generaRespostaNOK(ERROR_GENERIC);	
			}
		
		} catch (JSONException ex) {
			return generaRespostaNOK(ERROR_DADES);
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
