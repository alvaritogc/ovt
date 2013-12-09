package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUsuarioRecursoService;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecurso;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecursoPK;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRecursoRepository;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRepository;

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
@Named("usuarioRecursoService")
@TransactionAttribute
public class UsuarioRecursoService implements IUsuarioRecursoService{
    private final UsuarioRecursoRepository usuarioRecursoRepository;
    private final UsuarioRepository usuarioRepository;

    @Inject
    public UsuarioRecursoService(UsuarioRecursoRepository usuarioRecursoRepository, UsuarioRepository usuarioRepository) {
        this.usuarioRecursoRepository = usuarioRecursoRepository;
        this.usuarioRepository = usuarioRepository;
    }
    
//    @Override
    public List<UsrUsuarioRecurso> getAllUsuarios() {
        List<UsrUsuarioRecurso> lista;

        try {
            lista = usuarioRecursoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public UsrUsuarioRecurso save(UsrUsuarioRecurso ur) {
           return usuarioRecursoRepository.save(ur);
    }

    @Override
    public UsrUsuarioRecurso editar(UsrUsuarioRecurso usrUsuarioRecurso, UsrUsuarioRecursoPK usrUsuarioRecursoPK){
        try{
            UsrUsuarioRecurso usuarioRecursoTmp = usuarioRecursoRepository.findOne(usrUsuarioRecursoPK);
            usuarioRecursoTmp.setWx(usrUsuarioRecurso.getWx());
            usuarioRecursoTmp.setFechaLimite(usrUsuarioRecurso.getFechaLimite());
            usuarioRecursoTmp.setEsDenegado(usrUsuarioRecurso.getEsDenegado());

            return  usuarioRecursoRepository.save(usuarioRecursoTmp);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean eliminarUsuarioRecurso(UsrUsuarioRecursoPK usrUsuarioRecursoPK){
        try{
        UsrUsuarioRecurso usuarioRecursoTmp = usuarioRecursoRepository.findOne(usrUsuarioRecursoPK);
        usuarioRecursoRepository.delete(usuarioRecursoTmp);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public UsrUsuarioRecurso buscarUnUsuarioRecurso(UsrUsuarioRecursoPK usrUsuarioRecursoPK){
        return usuarioRecursoRepository.findOne(usrUsuarioRecursoPK);
    }

//    @Override
    public boolean delete(UsrUsuarioRecurso usuarioRecurso) {
        boolean deleted = false;

        try {
            usuarioRecursoRepository.delete(usuarioRecurso);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

//    @Override
    public UsrUsuarioRecurso findById(UsrUsuarioRecursoPK id) {
        UsrUsuarioRecurso entity;
        entity = usuarioRecursoRepository.findOne(id);
        return entity;
    }
}
