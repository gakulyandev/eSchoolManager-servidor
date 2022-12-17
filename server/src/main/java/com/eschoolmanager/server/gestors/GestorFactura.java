/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.util.HashMap;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Estudiant;
import com.eschoolmanager.server.model.Factura;
import com.eschoolmanager.server.model.FacturaLinia;

/**
 * @author Gayané Akulyan Akulyan
 *
 */
public class GestorFactura extends GestorEscola {

	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorFactura(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Genera una factura per l'estudiant
     * @param codi de l'estudiant
     * @return mes a facturar
     * @throws GestorExcepcions
     */
	public HashMap<String, Object> genera(int codiEstudiant, int mes) throws GestorExcepcions {
        
		Estudiant estudiant = escola.trobaEstudiant(codiEstudiant);
        if (estudiant == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_ESTUDIANT);
		}
        
        Factura factura = estudiant.generaFactura(mes);
        
        // Persisteix la factura
        entityManager.getTransaction().begin();
        entityManager.merge(factura);
        entityManager.getTransaction().commit();
        
        // Obté les dades per retornar
        factura = estudiant.obteFactura(mes);
        HashMap<String, Object> dadesFactura = new HashMap<String, Object>();
        dadesFactura.put(DADES_CODI_FACTURA, factura.getCodi());
        dadesFactura.put(DADES_ESTAT_FACTURA, factura.isPagat());
        dadesFactura.put(DADES_NOM_ESTUDIANT, factura.getEstudiant().getNom());
        dadesFactura.put(DADES_COGNOMS_ESTUDIANT_COMPLET, factura.getEstudiant().getCognoms());
        
        HashMap<Integer, Object> dadesLinies = new HashMap<Integer, Object>();
        
		int i = 0;
        for (FacturaLinia linia : factura.getLinies()) {
            HashMap<String, Object> dadesLinia = new HashMap<String, Object>();
        	dadesLinia.put(DADES_DATA_I_HORA, linia.getDataIHora());
        	dadesLinia.put(DADES_NOM_SERVEI, linia.getServei().getNom());
        	dadesLinia.put(DADES_IMPORT_BECA_FACTURA, linia.getImportBeca());
        	dadesLinia.put(DADES_IMPORT_ESTUDIANT_FACTURA, linia.getImportEstudiant());
        	dadesLinies.put(i, dadesLinia);
        	i++;
        }
        dadesFactura.put(DADES_SESSIONS_FACTURA, dadesLinies);
        
		System.out.println(dadesFactura.toString());
        
        // Persisteix la factura
        entityManager.getTransaction().begin();
        entityManager.merge(factura);
        entityManager.getTransaction().commit();
        
        return dadesFactura;
    }

}
