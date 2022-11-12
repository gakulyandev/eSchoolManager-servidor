/**
 * 
 */
package com.eschoolmanager.server.gestors;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.eschoolmanager.server.model.Escola;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.SessioUsuari;


/**
 * @author Gayané Akulyan Akulyan
 * Classe que processa les diferents peticions dels clients i genera la resposta corresponent
 */
public class GestorPeticions {

	private EntityManager entityManager = null;
	private final static String CRIDA = "crida";
	private final static String CRIDA_ALTA_EMPLEAT = "ALTA EMPLEAT";
	private final static String CRIDA_LOGIN = "LOGIN";
	private final static String CRIDA_LOGOUT = "LOGOUT";
	private final static String CRIDA_ALTA_DEPARTAMENT = "ALTA DEPARTAMENT";
	private final static String CODI_SESSIO = "codiSessio";
	private final static String RESPOSTA = "resposta";
	private final static String RESPOSTA_OK = "OK";
	private final static String RESPOSTA_NOK = "NOK";
	private final static String MISSATGE = "missatge";
	private final static String DADES = "dades";
	private final static String DADES_NOM_USUARI = "usuari";
	private final static String DADES_CONTRASENYA = "contrasenya";
	private final static String DADES_CODI_DEPARTAMENT = "codiDepartament";
	private final static String DADES_NOM_DEPARTAMENT = "nomDepartament";
	private final static String DADES_CODI_SESSIO = "codiSessio";
	private final static String DADES_NOM = "nom";
	private final static String DADES_PERMISOS = "permisos";
	
	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorPeticions(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
     * Processa la petició del client i genera una resposta del servidor
     * @param peticio que envia el client
     * @return resposta que envia el servidor una vegada processada la petició
     */
	public String generaResposta(String peticioString) {
		GestorSessioUsuari gestorSessioUsuari = new GestorSessioUsuari(entityManager);
		GestorDepartament gestorDepartament = new GestorDepartament(entityManager);
				
		JSONObject peticio = new JSONObject(peticioString);
		JSONObject dadesPeticio = new JSONObject();
		JSONObject permisosGenerals = new JSONObject();
		permisosGenerals.put("escola", false);
		permisosGenerals.put("departament", false);
		permisosGenerals.put("empleat", false);
		permisosGenerals.put("estudiant", false);
		permisosGenerals.put("servei", false);
		permisosGenerals.put("beca", false);
		permisosGenerals.put("sessio", false);
		permisosGenerals.put("informe", false);

		try {
			String crida = peticio.getString(CRIDA);
		
			//Valida codi de sessió i permis de crida per l'usuari amb la sessió iniciada
			if (!crida.equals(CRIDA_LOGIN)) {
				gestorSessioUsuari.validaSessio(peticio.getString(CODI_SESSIO), crida);
			}
			
			//Gestió de la petició segons tipus de crida
			JSONObject dadesResposta = new JSONObject();
			
			switch (crida) {
			
				case CRIDA_LOGIN:
					dadesPeticio = peticio.getJSONObject(DADES);
					SessioUsuari sessio = gestorSessioUsuari.iniciaSessio(
							dadesPeticio.getString(DADES_NOM_USUARI), 
							dadesPeticio.getString(DADES_CONTRASENYA)
					);
					
					dadesResposta.put(DADES_CODI_SESSIO, sessio.getCodi());
					dadesResposta.put(DADES_NOM, sessio.getNomEmpleat());
					dadesResposta.put(DADES_NOM_DEPARTAMENT, sessio.getNomDepartament());
					JSONObject dadesRespostaPermisos = new JSONObject();
					Iterator keysPermisos = permisosGenerals.keys();
					while(keysPermisos.hasNext()) {
					    String permisNom = (String) keysPermisos.next();
						dadesRespostaPermisos.put(permisNom, permisosGenerals.get(permisNom));
						for (Permis permisDepartament : sessio.getPermisos()) {
							if(permisDepartament.getNom().equals(permisNom)) {
								dadesRespostaPermisos.put(permisNom, true);
							}
						}
					}
					dadesResposta.put(DADES_PERMISOS, dadesRespostaPermisos);

					return generaRespostaOK(dadesResposta);	
				
				case CRIDA_LOGOUT:
					gestorSessioUsuari.tancaSessio(peticio.getString(CODI_SESSIO));

					return generaRespostaOK(null);
					
				case CRIDA_ALTA_DEPARTAMENT:
					dadesPeticio = peticio.getJSONObject(DADES);
					

				    HashMap<String, Boolean> permisos = new HashMap();
					JSONObject dadesPeticioPermisos = dadesPeticio.getJSONObject(DADES_PERMISOS);
					Iterator keysPermisosPeticio = dadesPeticioPermisos.keys();
					while(keysPermisosPeticio.hasNext()) {
					    String key = (String) keysPermisosPeticio.next();
					    permisos.put(key, dadesPeticioPermisos.getBoolean(key));
					}
					
					gestorDepartament.afegeix(dadesPeticio.getString(DADES_NOM_DEPARTAMENT), permisos);

					return generaRespostaOK(dadesResposta);	
					
				default:
					return generaRespostaNOK("S'ha produït un error");	
			}
		
		} catch (JSONException ex) {
			return generaRespostaNOK("Falten dades");
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
