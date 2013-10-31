
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author pc01
 */
@Named("dominioService")
@TransactionAttribute
public class DominioService implements IDominioService{
    
    private final DominioRepository dominioRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public DominioService(DominioRepository dominioRepository) {
        this.dominioRepository = dominioRepository;
    }

//    @Override
    public List<ParDominio> getAllDominios() {
        List<ParDominio> allModulos;
        allModulos = dominioRepository.findAll();
        return allModulos;
    }

    @Override
    public ParDominio save(ParDominio dominio) {
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("=========CREANDO LLAVE COMPUESTA========");
        System.out.println("========================================");
        System.out.println("========================================");
        //
        ParDominioPK parDominioPK=new ParDominioPK();
        parDominioPK.setIdDominio("");
        parDominioPK.setValor("");
        dominio.setParDominioPK(parDominioPK);
        //
        dominio.setFechaBitacora(new Date());
        dominio.setRegistroBitacora("ROE");
        System.out.println("==============GUARDANDO================");
        System.out.println("========================================");
        ParDominio parDominio = dominioRepository.save(dominio);
        return parDominio;
    }

//    @Override
    public boolean delete(ParDominio dominio) {
        boolean deleted = false;
        dominioRepository.delete(dominio);
        return deleted;
    }

//    @Override
    public ParDominio findById(ParDominioPK id) {
        ParDominio parDominio;
        parDominio = dominioRepository.findOne(id);
        return parDominio;
    }

   @Override
    public  List<ParDominio> obtenerItemsDominio(String dominio){
        try {
            List<ParDominio> dominios=new ArrayList<ParDominio>();
            dominios=dominioRepository.obtenerDominioPorNombre(dominio);
            return dominios;
        }catch (Exception ex){
           ex.printStackTrace();
            return  null;
        }
    }
}
