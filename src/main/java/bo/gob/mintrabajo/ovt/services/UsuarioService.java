
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
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
@Named("usuarioService")
@TransactionAttribute
public class UsuarioService implements IUsuarioService{
    
    private final UsuarioRepository usuarioRepository;

    @Inject
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public List<UsrUsuarioEntity> getAllUsuarios() {
        List<UsrUsuarioEntity> allUsuarios;

        try {
            allUsuarios = usuarioRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allUsuarios = null;
        }
        return allUsuarios;
    }
    
    @Override
    public UsrUsuarioEntity save(UsrUsuarioEntity usuario) {
        UsrUsuarioEntity usrUsuarioEntity;

        try {
            usrUsuarioEntity = usuarioRepository.save(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            usrUsuarioEntity = null;
        }

        return usrUsuarioEntity;
    }

    @Override
    public boolean delete(UsrUsuarioEntity usuario) {
        boolean deleted = false;

        try {
            usuarioRepository.delete(usuario);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public UsrUsuarioEntity findById(BigDecimal id) {
        UsrUsuarioEntity usrUsuarioEntity;

        try {
            usrUsuarioEntity = usuarioRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            usrUsuarioEntity = null;
        }

        return usrUsuarioEntity;
    }
    
    @Override
    public boolean login(String username, String password) {
        UsrUsuarioEntity usrUsuarioEntity=null;

        try {
            //usrUsuarioEntity = usuarioRepository.login(username,password);
        } catch (Exception e) {
            e.printStackTrace();
            usrUsuarioEntity = null;
        }
        if(usrUsuarioEntity==null){
            return false;
        }
        else{
            return true;
        }
    }
    
}
