package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;


@Named("utilsService")
@TransactionAttribute
public class UtilsService implements IUtilsService {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;
    private final DominioRepository dummy;

    @Inject
    public UtilsService(DominioRepository dummy){
        this.dummy = dummy;
    }

//    @Inject
//    public UtilsService(){
//    }

    @Override
    public Long valorSecuencia (String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }

    @Override
    public Integer planillaSecuencia (String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        return rtn.intValue();
    }


}