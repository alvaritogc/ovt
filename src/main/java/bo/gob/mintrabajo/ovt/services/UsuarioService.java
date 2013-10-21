
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VHTC
 */
@Named("usuarioService")
@TransactionAttribute
public class UsuarioService implements IUsuarioService{
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;
        
    @Inject
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public List<UsrUsuarioEntity> getAllUsuarios() {
        logger.info("getAllUsuarios()");
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
        logger.info("save()");
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
        logger.info("delete()");
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
        logger.info("findById()");
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
    public int login(String username, String password) {
        logger.info("login("+username+","+password+")");
        /*
        UsrUsuarioEntity usrUsuarioEntity=null;

        try {
            usrUsuarioEntity = usuarioRepository.login(username,password);
        } catch (Exception e) {
            e.printStackTrace();
            usrUsuarioEntity = null;
        }
        if(usrUsuarioEntity==null){
            return false;
        }
        else{
            return true;
        }*/
//        UsrUsuarioEntity newUsrUsuarioEntity = new UsrUsuarioEntity();
//        newUsrUsuarioEntity.setUsuario(user);
//        newUsrUsuarioEntity.setClave(password);
//        newUsrUsuarioEntity.setEstadoUsuario("Activo");
//
//        repository.findByExample(newUsrUsuarioEntity, null, null, -1, -1);
//
//
//        entityManager.createNamedQuery("{call '''hacer-algo()'''}")ç
        
        //DataSource dataSource=
        List<UsrUsuarioEntity> listaUsuarios=null;
        
        try{
            listaUsuarios = usuarioRepository.findByAttribute("usuario", username, -1, -1);
        }
        catch(Exception e){
            throw new RuntimeException("Error en el login");
        }
        if (listaUsuarios.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        UsrUsuarioEntity usrUsuarioEntity = listaUsuarios.get(0);
        if (!password.equals(usrUsuarioEntity.getClave())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        /*
        if (usrUsuarioEntity.getFechaInhabilitacion().getTime() < System.currentTimeMillis()) {
            //....
        }*/
        return usrUsuarioEntity.getIdUsuario();
        //return true;
    }
    
}
