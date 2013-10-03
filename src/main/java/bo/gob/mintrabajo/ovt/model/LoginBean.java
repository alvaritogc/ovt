package bo.gob.mintrabajo.ovt.model;

import bo.gob.mintrabajo.ovt.api.Users;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

/**
 * User: Renato Velasquez.
 * Date: 03-10-13
 */
@ManagedBean
public class LoginBean {

    @ManagedProperty(value = "#{usrSrv}")
    public Users usrSrv;

    private String username;

    private String password;

    public String login() {
        try {
            UsrUsuarioEntity byUsuarioAndClave = usrSrv.findByUsuarioAndClave(username, password);
            System.out.println(byUsuarioAndClave);
        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(e.getMessage(), new FacesMessage(e.getMessage()));
        }
        System.out.println("sdkjfsdlkfjsdlkf sldkjfsldkfj ");
        return "/djd";
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
}
