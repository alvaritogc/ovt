
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public Long login(String username, String password) {
        logger.info("login("+username+","+password+")");
        List<UsrUsuario> listaUsuarios=null;
        try{
            listaUsuarios = usuarioRepository.findByAttribute("usuario", username, -1, -1);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error en el login");
        }
        if (listaUsuarios.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        UsrUsuario usuario = listaUsuarios.get(0);
        if (!password.equals(usuario.getClave())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return usuario.getIdUsuario();
    }
    
    @Override
    public List<UsrUsuario> getAllUsuarios() {
        List<UsrUsuario> lista;
        lista = usuarioRepository.findAll();
        return lista;
    } 
    
    @Override
    public UsrUsuario findById(Long id) {
        return usuarioRepository.findOne(id);
    }

    @Override
    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }

    @Override
    public UsrUsuario save(UsrUsuario usrUsuario){
        UsrUsuario usr = usuarioRepository.save(usrUsuario);

        return usr;
    }

    public UsrUsuario obtenerUsuarioPorNombreUsuario(String email){

       return usuarioRepository.findByUsuario(email);
    }

    @Override
   public UsrUsuario findByUsuarioAndClave(String email,String clave,String nuevaClave){
        System.out.println("====>> BUSCANDO USUARIO "+email+"  CLAVE "+clave);
        UsrUsuario usuario= usuarioRepository.findByUsuarioAndClave(email,clave);
        if(usuario!=null)  {
            usuario.setClave(nuevaClave);
            return usuarioRepository.save(usuario);
        }
        else
            return null;
   }
}
