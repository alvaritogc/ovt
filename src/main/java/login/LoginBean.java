package login;

import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.api.Users;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

/**
 * User: Renato Velasquez. Date: 03-10-13
 */
@ManagedBean
public class LoginBean {

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{usrSrv}")
    public Users usrSrv;
    private String username;
    private String password;

    @PostConstruct
    public void ini() {
        System.out.println("===========================================");
        System.out.println("===========================================");
        System.out.println("===========================================");
        System.out.println("==========================init=============");
        System.out.println("===========================================");
        System.out.println("===========================================");
        username = "";
        password = "";
    }

    public String login() {
        try {
            System.out.println("===========================================");
            System.out.println("===========================================");
            iUsuarioService.login(username, password);
            System.out.println("login ok");
            System.out.println("===========================================");
            System.out.println("===========================================");
            return "loggin";
        } catch (RuntimeException e) {
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage(e.getMessage(), "Hello " ));  
            System.out.println("===========================================");
            System.out.println("===========================================");
            System.out.println("Error :  " + e.getMessage());
            System.out.println("===========================================");
            System.out.println("===========================================");
        }
        catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage(e.getMessage(), "Hello "));  
            System.out.println("===========================================");
            System.out.println("===========================================");
            System.out.println("Error :  " + e.getMessage());
            System.out.println("===========================================");
            System.out.println("===========================================");
        }
        password = "";
        /*
         try {
         UsrUsuarioEntity byUsuarioAndClave = usrSrv.findByUsuarioAndClave(username, password);
         System.out.println(byUsuarioAndClave);
         } catch (RuntimeException e) {
         FacesContext.getCurrentInstance().addMessage(e.getMessage(), new FacesMessage(e.getMessage()));
         }*/
        //System.out.println("sdkjfsdlkfjsdlkf sldkjfsldkfj ");
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
