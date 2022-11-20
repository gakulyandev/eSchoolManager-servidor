/**
 * 
 */
package com.eschoolmanager.server.utilitats;

/**
 * @author Gayané Akulyan Akulyan
 *
 */
public interface Constants {

	public final static String CRIDA = "crida";
	public final static String CRIDA_LOGIN = "LOGIN";
	public final static String CRIDA_LOGOUT = "LOGOUT";
	public final static String CRIDA_ALTA_DEPARTAMENT = "ALTA DEPARTAMENT";
	public final static String CRIDA_LLISTA_DEPARTAMENTS = "LLISTA DEPARTAMENTS";
	public final static String CRIDA_CONSULTA_DEPARTAMENT = "CONSULTA DEPARTAMENT";
	public final static String CRIDA_MODI_DEPARTAMENT = "MODI DEPARTAMENT";
	public final static String CRIDA_BAIXA_DEPARTAMENT = "BAIXA DEPARTAMENT";
	public final static String CRIDA_ALTA_SERVEI = "ALTA SERVEI";
	public final static String CRIDA_LLISTA_SERVEIS = "LLISTA SERVEIS";
	public final static String CRIDA_CONSULTA_SERVEI = "CONSULTA SERVEI";
	public final static String CRIDA_MODI_SERVEI = "MODI SERVEI";
	public final static String CRIDA_BAIXA_SERVEI = "BAIXA SERVEI";
	public final static String CRIDA_ALTA_EMPLEAT = "ALTA EMPLEAT";
	public final static String CRIDA_LLISTA_EMPLEATS = "LLISTA EMPLEATS";
	
	public final static String CODI_SESSIO = "codiSessio";
	public final static String RESPOSTA = "resposta";
	public final static String RESPOSTA_OK = "OK";
	public final static String RESPOSTA_NOK = "NOK";
	public final static String MISSATGE = "missatge";
	
	public final static String DADES = "dades";
	public final static String DADES_CAMP = "camp";
	public final static String DADES_ORDRE = "ordre";
	public final static String DADES_CONTRASENYA = "contrasenya";
	public final static String DADES_CODI_SESSIO = "codiSessio";
	public final static String DADES_NOM_DEPARTAMENT = "nomDepartament";
	public final static String DADES_CODI_SERVEI = "codiServei";
	public final static String DADES_NOM_SERVEI = "nomServei";
	public final static String DADES_DURADA_SERVEI = "durada";
	public final static String DADES_COST_SERVEI = "cost";
	public final static String DADES_CODI_EMPLEAT = "codiEmpleat";
	public final static String DADES_DNI_EMPLEAT = "dni";
	public final static String DADES_NOM_EMPLEAT = "nom";
	public final static String DADES_COGNOMS_EMPLEAT = "cognoms";
	public final static String DADES_DATA_NAIXEMENT_EMPLEAT = "dataNaixement";
	public final static String DADES_ADRECA_EMPLEAT = "adreca";
	public final static String DADES_TELEFON_EMPLEAT = "telefon";
	public final static String DADES_EMAIL_EMPLEAT = "email";
	public final static String DADES_CODI_DEPARTAMENT = "codiDepartament";
	public final static String DADES_NOM_USUARI = "usuari";
	public final static String DADES_CONTRASENYA_USUARI = "contrasenya";
	public final static String DADES_PERMISOS_DEPARTAMENT = "permisos";
	
	public final static String[] PERMISOS_NOMS = {"escola","departament","empleat","estudiant","servei","beca","sessio","informe"};
	
	public final static String ERROR_GENERIC = "S'ha produit un error";
	public final static String ERROR_NO_AUTORITZAT = "L'usuari no està autoritzat per aquesta acció";
	public final static String ERROR_FALTEN_DADES = "Falten dades";
	public final static String ERROR_CAMP = "No existeix el valor indicat";
	public final static String ERROR_DUPLICAT_DEPARTAMENT = "Ja existeix un departament amb el mateix nom";
	public final static String ERROR_INEXISTENT_DEPARTAMENT = "No existeix el departament indicat";
	public final static String ERROR_ELEMENTS_RELACIONATS_DEPARTAMENT = "Existeixen altres elements relacionats amb el departament";
	public final static String ERROR_DUPLICAT_EMPLEAT = "Ja existeix un empleat amb el mateix DNI";
	public final static String ERROR_DUPLICAT_USUARI = "Ja existeix un usuari amb el mateix nom d'usuari";
	public final static String ERROR_USUARI_INEXISTENT = "No existeix cap usuari amb les dades indicades";
    public final static String ERROR_DNI_INCORRECTE = "El DNI és incorrecte";
	public final static String ERROR_DUPLICAT_SERVEI = "Ja existeix un servei amb el mateix nom";
	public final static String ERROR_INEXISTENT_SERVEI = "No existeix el servei indicat";
	public final static String ERROR_ELEMENTS_RELACIONATS_SERVEI = "Existeixen altres elements relacionats amb el servei";
	
}