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
	public final static String CRIDA_CONSULTA_ESCOLA = "CONSULTA ESCOLA";
	public final static String CRIDA_MODI_ESCOLA = "MODI ESCOLA";
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
	public final static String CRIDA_CONSULTA_EMPLEAT = "CONSULTA EMPLEAT";
	public final static String CRIDA_MODI_EMPLEAT = "MODI EMPLEAT";
	public final static String CRIDA_BAIXA_EMPLEAT = "BAIXA EMPLEAT";
	public final static String CRIDA_ALTA_ESTUDIANT = "ALTA ESTUDIANT";
	public final static String CRIDA_LLISTA_ESTUDIANTS = "LLISTA ESTUDIANTS";
	public final static String CRIDA_CONSULTA_ESTUDIANT = "CONSULTA ESTUDIANT";
	public final static String CRIDA_MODI_ESTUDIANT = "MODI ESTUDIANT";
	public final static String CRIDA_BAIXA_ESTUDIANT = "BAIXA ESTUDIANT";
	public final static String CRIDA_ALTA_BECA = "ALTA BECA";
	public final static String CRIDA_LLISTA_BEQUES = "LLISTA BEQUES";
	public final static String CRIDA_CONSULTA_BECA = "CONSULTA BECA";
	public final static String CRIDA_MODI_BECA = "MODI BECA";
	public final static String CRIDA_BAIXA_BECA = "BAIXA BECA";
	public final static String CRIDA_ALTA_SESSIO = "ALTA SESSIO";
	public final static String CRIDA_LLISTA_SESSIONS = "LLISTA SESSIONS";
	public final static String CRIDA_CONSULTA_SESSIO = "CONSULTA SESSIO";
	public final static String CRIDA_MODI_SESSIO = "MODI SESSIO";

	public final static int CODI_ESCOLA = 1;
	
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
	public final static String DADES_CODI_ESCOLA = "codiEscola";
	public final static String DADES_NOM_ESCOLA = "nomEscola";
	public final static String DADES_ADRECA_ESCOLA = "adreca";
	public final static String DADES_TELEFON_ESCOLA = "telefon";
	public final static String DADES_NOM_DEPARTAMENT = "nomDepartament";
	public final static String DADES_CODI_SERVEI = "codiServei";
	public final static String DADES_NOM_SERVEI = "nomServei";
	public final static String DADES_DURADA_SERVEI = "durada";
	public final static String DADES_COST_SERVEI = "cost";
	public final static String DADES_CODI_EMPLEAT = "codiEmpleat";
	public final static String DADES_DNI_EMPLEAT = "dni";
	public final static String DADES_NOM_EMPLEAT = "nom";
	public final static String DADES_COGNOMS_EMPLEAT = "cognoms";
	public final static String DADES_NOM_EMPLEAT_COMPLET = "nomEmpleat";
	public final static String DADES_COGNOMS_EMPLEAT_COMPLET = "cognomsEmpleat";
	public final static String DADES_DATA_NAIXEMENT_EMPLEAT = "dataNaixement";
	public final static String DADES_ADRECA_EMPLEAT = "adreca";
	public final static String DADES_TELEFON_EMPLEAT = "telefon";
	public final static String DADES_EMAIL_EMPLEAT = "email";
	public final static String DADES_ESTAT_EMPLEAT = "actiu";
	public final static String DADES_CODI_DEPARTAMENT = "codiDepartament";
	public final static String DADES_NOM_USUARI = "usuari";
	public final static String DADES_CONTRASENYA_USUARI = "contrasenya";
	public final static String DADES_PERMISOS_DEPARTAMENT = "permisos";
	public final static String DADES_CODI_ESTUDIANT = "codiEstudiant";
	public final static String DADES_DNI_ESTUDIANT = "dni";
	public final static String DADES_NOM_ESTUDIANT = "nomEstudiant";
	public final static String DADES_COGNOMS_ESTUDIANT = "cognoms";
	public final static String DADES_COGNOMS_ESTUDIANT_COMPLET = "cognomsEstudiant";
	public final static String DADES_DATA_NAIXEMENT_ESTUDIANT = "dataNaixement";
	public final static String DADES_ADRECA_ESTUDIANT = "adreca";
	public final static String DADES_TELEFON_ESTUDIANT = "telefon";
	public final static String DADES_EMAIL_ESTUDIANT = "email";
	public final static String DADES_ESTAT_ESTUDIANT = "registrat";
	public final static String DADES_IMPORT_IMPUTAT_ESTUDIANT = "importImputat";
	public final static String DADES_IMPORT_INICIAL_BECA = "importInicial";
	public final static String DADES_IMPORT_RESTANT_BECA = "importRestant";
	public final static String DADES_ESTAT_BECA = "finalitzada";
	public final static String DADES_ADJUDICANT_BECA = "adjudicant";
	public final static String DADES_CODI_BECA = "codiBeca";
	public final static String DADES_DATA_I_HORA = "dataIHora";
	
	public final static String[] PERMISOS_NOMS = {"escola","departament","empleat","estudiant","servei","beca","sessio","factura"};
	
	public final static String ERROR_GENERIC = "S'ha produit un error";
	public final static String ERROR_NO_AUTORITZAT = "L'usuari no està autoritzat per aquesta acció";
	public final static String ERROR_FALTEN_DADES = "Falten dades";
	public final static String ERROR_CAMP = "No existeix el valor indicat";
	public final static String ERROR_INEXISTENT_DEPARTAMENT = "No existeix el departament indicat";
    public final static String ERROR_DNI_INCORRECTE = "El DNI és incorrecte";
	public final static String ERROR_INEXISTENT_SERVEI = "No existeix el servei indicat";
    public final static String ERROR_INEXISTENT_EMPLEAT = "No existeix l'empleat indicat";
	public final static String ERROR_INEXISTENT_USUARI = "No existeix cap usuari amb les dades indicades";
    public final static String ERROR_INEXISTENT_ESTUDIANT = "No existeix l'estudiant indicat";
    public final static String ERROR_INEXISTENT_ESTUDIANT_SERVEI = "No existeix l'estudiant i/o servei indicats";
    public final static String ERROR_INEXISTENT_PROFESSOR_ESTUDIANT_SERVEI = "No existeix el professor i/o l'estudiant i/o servei indicats";
    public final static String ERROR_INEXISTENT_PROFESSOR = "L'empleat indicat no és professor";
    public final static String ERROR_INEXISTENT_RELACIO_PROFESSOR_SERVEI = "El professor indicat no imparteix el servei";
	public final static String ERROR_INEXISTENT_BECA = "No existeix la beca indicada";
	public final static String ERROR_INEXISTENT_SESSIO = "No existeix la sessió indicada";
	public final static String ERROR_EXISTEIX_DEPARTAMENT = "Ja existeix un departament amb el mateix nom";
	public final static String ERROR_EXISTEIX_SERVEI = "Ja existeix un servei amb el mateix nom";
	public final static String ERROR_EXISTEIX_USUARI = "Ja existeix un usuari amb el mateix nom d'usuari";
	public final static String ERROR_DUPLICAT_SERVEI = "Ja existeix un servei amb el mateix nom";
	public final static String ERROR_DUPLICAT_USUARI = "Ja existeix un usuari amb el mateix nom d'usuari";
	public final static String ERROR_DUPLICAT_EMPLEAT = "Ja existeix un empleat amb el mateix DNI";
	public final static String ERROR_DUPLICAT_ESTUDIANT = "Ja existeix un estudiant amb el mateix DNI";
	public final static String ERROR_DUPLICAT_DEPARTAMENT = "Ja existeix un departament amb el mateix nom";
	public final static String ERROR_ELEMENTS_RELACIONATS_SERVEI = "Existeixen altres elements relacionats amb el servei";
	public final static String ERROR_ELEMENTS_RELACIONATS_EMPLEAT = "Existeixen altres elements relacionats amb l'empleat";
    public final static String ERROR_ELEMENTS_RELACIONATS_ESTUDIANT = "Existeixen altres elements relacionats amb l'estudiant";
	public final static String ERROR_ELEMENTS_RELACIONATS_DEPARTAMENT = "Existeixen altres elements relacionats amb el departament";
}
