package login;

import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.api.Users;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import java.io.IOException;
import java.util.logging.Level;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
//import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Renato Velasquez. Date: 03-10-13
 */
@ManagedBean
public class LoginBean{
    //
    private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{usrSrv}")
    public Users usrSrv;
    //
    private String username;
    private String password;    

    @PostConstruct
    public void ini() {
        logger.info("********************************************************************");
        logger.info("********************************************************************");
        logger.info("******************************LoginBean.init()**********************************");
        logger.info("********************************************************************");
        logger.info("********************************************************************");
        username = "";
        password = "";
        //
    }

    public String login() {
        logger.info("login()");
        try {
            logger.info("iUsuarioService.login("+username+","+password+")");
            int idUsuario= iUsuarioService.login(username, password);
            logger.info("usuario aceptado");
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("idUsuario", idUsuario);
            return "loggin";
        } catch (RuntimeException e) {
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage(e.getMessage(), "Hello " ));  
        }
        catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage(e.getMessage(), "Hello "));  
        }
        password = "";
        /*
         try {
         UsrUsuarioEntity byUsuarioAndClave = usrSrv.findByUsuarioAndClave(username, password);
         System.out.println(byUsuarioAndClave);
         } catch (RuntimeException e) {
         FacesContext.getCurrentInstance().addMessage(e.getMessage(), new FacesMessage(e.getMessage()));
         }*/
        return "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(Users usrSrv) {
        this.usrSrv = usrSrv;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }
}