package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IRolRecursoService;
import bo.gob.mintrabajo.ovt.entities.UsrRolRecurso;
import bo.gob.mintrabajo.ovt.entities.UsrRolRecursoPK;
import bo.gob.mintrabajo.ovt.repositories.RecursoRepository;
import bo.gob.mintrabajo.ovt.repositories.RolRecursoRepository;
import bo.gob.mintrabajo.ovt.repositories.RolRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pc01
 */
@Named("rolRecursoService")
@TransactionAttribute
public class RolRecursoService implements IRolRecursoService {

    private final RolRecursoRepository rolRecursoRepository;
    private final RolRepository rolRepository;
    private final RecursoRepository recursoRepository;

    @Inject
    public RolRecursoService(RolRecursoRepository rolRecursoRepository, RolRepository rolRepository, RecursoRepository recursoRepository) {
        this.rolRecursoRepository = rolRecursoRepository;
        this.rolRepository = rolRepository;
        this.recursoRepository = recursoRepository;
    }

    //@Override
    public List<UsrRolRecurso> getAllRecursos() {
        List<UsrRolRecurso> lista;

        try {
            lista = rolRecursoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }

//    @Override
    public UsrRolRecurso save(UsrRolRecurso rolRecurso) {
        UsrRolRecurso entity;
        entity = rolRecursoRepository.save(rolRecurso);
        return entity;
    }

//    @Override
    public boolean delete(UsrRolRecurso rolRecurso) {
        boolean deleted = false;
        rolRecursoRepository.delete(rolRecurso);
        deleted = true;
        return deleted;
    }

//    @Override
    public UsrRolRecurso findById(UsrRolRecursoPK id) {
        UsrRolRecurso entity;
        entity = rolRecursoRepository.findOne(id);
        return entity;
    }

    public UsrRolRecurso guardarRolRecurso(UsrRolRecurso usrRolRecurso, Long idRol, Long idRecurso){
        try {
            usrRolRecurso.setFechaBitacora(new Date());
            usrRolRecurso.setRegistroBitacora("ROE");
            usrRolRecurso.setUsrRol(rolRepository.findOne(idRol));
            usrRolRecurso.setUsrRecurso(recursoRepository.findOne(idRecurso));

            UsrRolRecursoPK usrRolRecursoPK = new UsrRolRecursoPK();
            usrRolRecursoPK.setIdRol(idRol);
            usrRolRecursoPK.setIdRecurso(idRecurso);

            usrRolRecurso.setUsrRolRecursoPK(usrRolRecursoPK);
            return rolRecursoRepository.save(usrRolRecurso);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean eliminarRolRecurso(Long idRol, Long idRecurso) {
        try{
            UsrRolRecursoPK usrRolRecursoPK = new UsrRolRecursoPK();
            usrRolRecursoPK.setIdRol(idRol);
            usrRolRecursoPK.setIdRecurso(idRecurso);
            UsrRolRecurso rolRecursoTmp = rolRecursoRepository.findOne(usrRolRecursoPK);

            if(rolRecursoTmp != null){
                rolRecursoRepository.delete(rolRecursoTmp);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public UsrRolRecurso tieneRelacionRolRecurso(UsrRolRecursoPK usrRolRecursoPK){
        try{
            UsrRolRecurso rrTmp = rolRecursoRepository.findOne(usrRolRecursoPK);
            if(rrTmp != null) {
                return rrTmp;
            }else{
                return null;
            }
        }catch (Exception e){
             return null;
        }
    }
}
