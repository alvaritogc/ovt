package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ILocalidadService;
import bo.gob.mintrabajo.ovt.entities.ParLocalidad;
import bo.gob.mintrabajo.ovt.repositories.LocalidadRepository;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: aquiroz Date: 10/28/13 Time: 6:12 PM To
 * change this template use File | Settings | File Templates.
 */
@Named("localidadService")
@TransactionAttribute
public class LocalidadService implements ILocalidadService {

    private static final Logger logger = LoggerFactory.getLogger(LocalidadService.class);
    private final LocalidadRepository localidadRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public LocalidadService(LocalidadRepository localidadRepository) {
        this.localidadRepository = localidadRepository;
    }

    public List<ParLocalidad> getAllLocalidades() {

        return localidadRepository.findAll();
    }

    @Override
    public Long localidadSecuencia(String nombreSecuencia) {
        BigDecimal rtn;
        rtn = (BigDecimal) entityManager.createNativeQuery("SELECT " + nombreSecuencia + ".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }

    @Override
    public ParLocalidad findById(String idLocalidad) {
        return localidadRepository.findOne(idLocalidad);
    }

    public List<ParLocalidad> listarDepartamentos() {
        return localidadRepository.listarDepartamentos();
    }

    @Override
    public boolean guardaLocalidad(ParLocalidad parLocalidad, String tipoLocalidad,
            String REGISTRO_BITACORA, String tipoNodo, String idPadre, String idHijo) {
        boolean guardado = false;
        System.out.println("entra a guardar");

        ParLocalidad pLocalidad = new ParLocalidad();
        try {
            if (idHijo.equals("")) {
                //Nuevo
                pLocalidad.setCodLocalidad(parLocalidad.getCodLocalidad());
            } else {
                pLocalidad = localidadRepository.findOne(idHijo);
            }

            System.out.println("==> codigo " + pLocalidad.getCodLocalidad());
            pLocalidad.setDescripcion(parLocalidad.getDescripcion());
            System.out.println("==> descrip " + pLocalidad.getDescripcion());
            pLocalidad.setTipoLocalidad(tipoLocalidad);
            System.out.println("==> tipoloc " + pLocalidad.getTipoLocalidad());
            pLocalidad.setCodigoOtr(parLocalidad.getCodigoOtr());
            System.out.println("==> cod1 " + pLocalidad.getCodigoOtr());
            pLocalidad.setCodigoRef(parLocalidad.getCodigoRef());
            System.out.println("==> cod2 " + pLocalidad.getCodigoRef());

            if (tipoNodo.equals("Hijo")) {
                System.out.println("es hijo");
                System.out.println(" id padre " + idPadre);
                ParLocalidad pLocalidadP = new ParLocalidad();
                pLocalidadP = localidadRepository.findOne(idPadre);
                System.out.println(" etiq " + pLocalidadP.getDescripcion());
                pLocalidad.setCodLocalidadPadre(pLocalidadP);
                System.out.println("==> codpadre " + pLocalidad.getCodLocalidadPadre().getCodLocalidad());
            } else {
                System.out.println("no es hijo");
            }

            ///System.out.println("=>5 "+ aEconimica.getEstado());
            pLocalidad.setFechaBitacora(new Date());
            pLocalidad.setRegistroBitacora(REGISTRO_BITACORA);
            ///System.out.println("preparando para guardar");
            localidadRepository.save(pLocalidad);
            ///System.out.println("guardardado");
            guardado = true;
        } catch (Exception e) {
            e.printStackTrace();
            guardado = false;
        }
        return guardado;
    }

    @Override
    public boolean eliminarLocalidad(String idLoc) {
        boolean estado = false;
        try {
            localidadRepository.delete(idLoc);
            estado=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estado;
    }

}
