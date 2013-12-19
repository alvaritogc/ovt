package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;
import bo.gob.mintrabajo.ovt.repositories.ModuloRepository;
import bo.gob.mintrabajo.ovt.repositories.RecursoRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pc01
 */
@Named("recursoService")
@TransactionAttribute
public class RecursoService implements IRecursoService {

    private final RecursoRepository recursoRepository;
    private final DominioRepository dominioRepository;
    private final ModuloRepository moduloRepository;
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public RecursoService(RecursoRepository recursoRepository, DominioRepository dominioRepository, ModuloRepository moduloRepository) {
        this.recursoRepository = recursoRepository;
        this.dominioRepository = dominioRepository;
        this.moduloRepository = moduloRepository;
    }

//    @Override
    public List<UsrRecurso> getAllRecursos() {
        List<UsrRecurso> allRecursos;
        allRecursos = recursoRepository.findAll();
        return allRecursos;
    }

    @Override
    public boolean delete(Long id) {
        boolean deleted = false;
        recursoRepository.delete(id);
        deleted = true;
        return deleted;
    }

    @Override
    public List<UsrRecurso> obtenerTodosRecursoLista() {
        return recursoRepository.obtenerRecursoDescripcionNoNull();
    }

    @Override
    public UsrRecurso findById(Long id) {
        return recursoRepository.findOne(id);
    }

    @Override
    public List<UsrRecurso> buscarPorUsuario(Long idUsuario) {
        return recursoRepository.buscarPorUsuario(idUsuario);
    }

    @Override
    public List<UsrRecurso> listarPorTipoRecurso(String tipoRecurso) {
        return recursoRepository.findByAttribute("tipoRecurso", tipoRecurso, -1, -1);
    }

    @Override
    public List<UsrRecurso> obtenerRecursoEnUsuarioRecurso(Long idUsuario) {
        return recursoRepository.obtenerRecursoEnUsuarioRecurso(idUsuario);
    }

    public List<UsrRecurso> listarRecursosPorTipo(String tipoRecurso) {
        return recursoRepository.listarRecursosPorTipo(tipoRecurso);

    }

    @Override
    public List<UsrRecurso> buscarRecursoPorUsuario(Long idUsuario) {
        return recursoRepository.buscarRecursoPorUsuario(idUsuario);
    }

    @Override
    public boolean guardarRecurso(UsrRecurso recurso, String usrModuloId, boolean estadoRecurso,
            String REGISTRO_BITACORA, String tipoNodo, Long idPadre, Long idHijo) {
        boolean guardado = false;
        System.out.println("entra a guardar");

        UsrRecurso uRecurso = new UsrRecurso();
        try {
            if (idHijo < 0) {
                //Nuevo
                uRecurso.setIdRecurso(this.valorSecuencia("USR_RECURSO_SEC"));
            } else {
                uRecurso = recursoRepository.findOne(idHijo);
            }

            uRecurso.setIdModulo(moduloRepository.findOne(usrModuloId));
            System.out.println("==> idmod " + uRecurso.getIdModulo());
            uRecurso.setTipoRecurso(recurso.getTipoRecurso());
            System.out.println("==> tiporecurso " + uRecurso.getTipoRecurso());
            uRecurso.setTipoPlataforma(recurso.getTipoPlataforma());
            System.out.println("==> plataform " + uRecurso.getTipoPlataforma());
            uRecurso.setOrden(recurso.getOrden());
            System.out.println("==> orden " + uRecurso.getOrden());
            uRecurso.setEtiqueta(recurso.getEtiqueta());
            System.out.println("==> etiqueta " + uRecurso.getEtiqueta());
            uRecurso.setDescripcion(recurso.getDescripcion());
            System.out.println("==> descrip " + uRecurso.getDescripcion());
            uRecurso.setEjecutable(recurso.getEjecutable());
            System.out.println("==> ejecut " + uRecurso.getEjecutable());
            uRecurso.setEsVerificable(recurso.getEsVerificable());
            System.out.println("==> verif " + uRecurso.getEsVerificable());
            if (tipoNodo.equals("Hijo")) {
                System.out.println("es hijo");
                System.out.println(" id padre " + idPadre);
                UsrRecurso uRecursoP = new UsrRecurso();
                uRecursoP = recursoRepository.findOne(idPadre);
                System.out.println(" etiq " + uRecursoP.getEtiqueta());
                uRecurso.setIdRecursoPadre(uRecursoP);
            } else {
                System.out.println("no es hijo");
            }

            if (estadoRecurso == true) {
                uRecurso.setEstado(dominioRepository.obtenerDominioPorNombreYValor("ESTADO", "A").getParDominioPK().getValor());
            } else {
                uRecurso.setEstado(dominioRepository.obtenerDominioPorNombreYValor("ESTADO", "X").getParDominioPK().getValor());
            }
            ///System.out.println("=>5 "+ aEconimica.getEstado());
            uRecurso.setFechaBitacora(new Date());
            uRecurso.setRegistroBitacora(REGISTRO_BITACORA);
            ///System.out.println("preparando para guardar");
            recursoRepository.save(uRecurso);
            ///System.out.println("guardardado");
            guardado = true;
        } catch (Exception e) {
            e.printStackTrace();
            guardado = false;
        }
        return guardado;
    }

    public Long valorSecuencia(String nombreSecuencia) {
        BigDecimal rtn;
        rtn = (BigDecimal) entityManager.createNativeQuery("SELECT " + nombreSecuencia + ".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }

}
