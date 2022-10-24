/**
 * 
 */
package com.eschoolmanager.server.gestors;

import javax.persistence.EntityManager;

import org.json.JSONException;
import org.json.JSONObject;

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
	private final static String CODI_SESSIO = "codiSessio";
	private final static String RESPOSTA = "resposta";
	private final static String RESPOSTA_OK = "OK";
	private final static String RESPOSTA_NOK = "NOK";
	private final static String MISSATGE = "missatge";
	private final static String DADES = "dades";
	private final static String DADES_NOM_USUARI = "usuari";
	private final static String DADES_CONTRASENYA = "contrasenya";
	private final static String DADES_CODI_DEPARTAMENT = "codiDepartament";
	private final static String DADES_CODI_SESSIO = "codiSessio";
	private final static String DADES_NOM = "nom";
	
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
		JSONObject peticio = new JSONObject(peticioString);
		JSONObject dadesPeticio = new JSONObject();

		try {
			String crida = peticio.getString(CRIDA);
		
			//Valida codi de sessió per l'usuari amb la sessió iniciada
			
			if (!crida.equals(CRIDA_LOGIN)) {
				gestorSessioUsuari.validaCodi(peticio.getString(CODI_SESSIO));
			}
			
			//Gestió de la petició segons tipus de crida
			switch (crida) {
			
				case CRIDA_LOGIN:
					dadesPeticio = peticio.getJSONObject(DADES);
					SessioUsuari sessio = gestorSessioUsuari.iniciaSessio(
							dadesPeticio.getString(DADES_NOM_USUARI), 
							dadesPeticio.getString(DADES_CONTRASENYA)
					);
					
					JSONObject dadesResposta = new JSONObject();
					dadesResposta.put(DADES_CODI_SESSIO, sessio.getCodi());
					dadesResposta.put(DADES_NOM, sessio.getNomEmpleat());
					dadesResposta.put(DADES_CODI_DEPARTAMENT, sessio.getCodiDepartament());

					return generaRespostaOK(dadesResposta);	
				
				case CRIDA_LOGOUT:
					gestorSessioUsuari.tancaSessio(peticio.getString(CODI_SESSIO));

					return generaRespostaOK(null);
					
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
