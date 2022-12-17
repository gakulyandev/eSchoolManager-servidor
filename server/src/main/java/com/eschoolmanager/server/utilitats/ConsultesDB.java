package com.eschoolmanager.server.utilitats;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Departament;

/**
 * @author Gayané Akulyan Akulyan
 * Clase que conté utilitats per les consultes a la base de dades
 * 
 */
public class ConsultesDB {

	/**
	 * Verifica si les dades del filtre son correctes
	 * @param camp a verificar
	 * @param ordre a verificar
	 * @param valor a verificar
	 * @param camps del gestor necessaris per comprovar si el camp a verificar és correcte
	 * @param ordenacions del gestor necessaris per comprovar si l'ordre a verificar és correcte
	 * @return true o false segons si el filtre és o no vàlid
	 */
	public static boolean verificaFiltre(String camp, String ordre, String valor, String[] camps, String[] ordenacions) {
		
		return (Arrays.asList(camps).contains(camp) && Arrays.asList(ordenacions).contains(ordre)) ||
				camp.length() == 0 && ordre.length() == 0 && valor.length() == 0 ||
				camp.length() > 0 && ordre.length() > 0 && valor.length() == 0 ||
				camp.length() > 0 && ordre.length() == 0 && valor.length() > 0;
	}
	
	/**
	 * Comprova si és un filtre buit o amb dades per defecte
	 * @param camp a verificar
	 * @param ordre a verificar
	 * @param valor a verificar
	 * @return true o false segons si el filtre és o no un filtre per defecte
	 */
	public static boolean isFiltreDefecte(String camp, String ordre, String valor) {
		
		return (camp.equals(Constants.DADES_CAMP_CODI) && ordre.equals(Constants.DADES_ORDRE_ASC) && valor.length() == 0) || 
				camp.length() == 0 && ordre.length() == 0 && valor.length() == 0;
	}
	
	/**
	 * Crea consulta a la base de dades
	 * @param entityManager per generar la query
	 * @param entitat a consultar
	 * @param camp a consultar
	 * @param ordre a consultar
	 * @param valor a consultar
	 * @return query generada
	 */
	public static Query creaConsulta(EntityManager entityManager, String entitat, String camp, String ordre, Object valor) {
		
		// Crea la consulta
		String consulta = "SELECT e FROM " + entitat + " e ";
		if (valor != null) {
			consulta += "WHERE e." + camp + " = :" + Constants.DADES_VALOR + " ORDER BY e." + camp + " " + ordre;
		} else {
			consulta += "ORDER BY e." + camp + " " + ordre;
		}
		  
		// Afegeix els paràmetres a la query
		Query query = (Query) entityManager.createQuery(consulta);
		if (valor != null) {
			query.setParameter(Constants.DADES_VALOR, valor);
		}
		
		return query;
	}
}
