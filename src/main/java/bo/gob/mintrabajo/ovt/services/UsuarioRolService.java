
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUsuarioRolService;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRol;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRolPK;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRolRepository;
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
@Named("usuarioRolService")
@TransactionAttribute
public class UsuarioRolService implements IUsuarioRolService{
    
    private final UsuarioRolRepository usuarioRolRepository;

    @Inject
    public UsuarioRolService(UsuarioRolRepository usuarioRolRepository) {
        this.usuarioRolRepository = usuarioRolRepository;
    }
    
//    @Override
    public List<UsrUsuarioRol> getAllUsuarios() {
        List<UsrUsuarioRol> allUsuarios;
        allUsuarios = usuarioRolRepository.findAll();
        return allUsuarios;
    }
    
//    @Override
    public UsrUsuarioRol save(UsrUsuarioRol usuarioRol) {
        UsrUsuarioRol entity;
        entity = usuarioRolRepository.save(usuarioRol);
        return entity;
    }

//    @Override
    public boolean delete(UsrUsuarioRol usuarioRol) {
        boolean deleted = false;
        usuarioRolRepository.delete(usuarioRol);
        return deleted;
    }

//    @Override
    public UsrUsuarioRol findById(UsrUsuarioRolPK id) {
        UsrUsuarioRol entity;
        entity = usuarioRolRepository.findOne(id);
        return entity;
    }
    
}
