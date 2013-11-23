package bo.gob.mintrabajo.ovt.Util;

import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
import bo.gob.mintrabajo.ovt.bean.persona.PersonaBean;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import bo.gob.mintrabajo.ovt.services.ParametrizacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedProperty;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/28/13
 * Time: 12:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServicioEnvioEmail implements Serializable {

    private String from = "";
    private String password = "";
    private ArrayList<String> to = new ArrayList<String>();
    private String host = "";
    private String port = "";
    private String subject = "";
    private String cuerpoMensaje = "";
    private String urlRedireccion = "";

    private static final Logger logger = LoggerFactory.getLogger(ServicioEnvioEmail.class);

    public void envioEmail(PersonaBean bean) {

        armadoEmail(bean);
        try {
            Properties props = new Properties();
            props.put("mail.smtp.socketFactory.port", port);
            props.setProperty("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.fallback", "false");

            props.put("mail.smtp.user", from);
            props.put("mail.smtp.pass", password);

            props.put("mail.smtp.tls.enable", "false");
            props.put("mail.smtp.tls.required", "false");
            props.put("mail.smtp.time", "11000");
            props.put("mail.debug", "true");

            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            logger.info("Autenticando usuario .... ");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (int i = 0; i < getTo().size(); i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(getTo().get(i)));
            }

            // *** Esto crea el cuerpo del mensaje *** //
            message.setSubject(subject);
            message.setText(cuerpoMensaje.concat("\n\nNotificación mail - Oficina Virtual: ").concat("\n\n\natte. \nAdministración Oficina Virtual"));

            // *** Enviamos el mensaje *** //
            logger.info("Enviando email .....");

            Transport t = session.getTransport("smtp");
            t.connect();
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (NoSuchProviderException nspe) {
            logger.info("No se encontró el servidor para envio de mensaje " + nspe.getMessage());
        } catch (MessagingException me) {
            logger.info("Error al envia el mensaje " + me.getMessage());
        }
    }

    public void armadoEmail(PersonaBean bean) {

        from = bean.getFrom();
        subject = bean.getSubject();
        urlRedireccion = bean.getUrlRedireccion();
        urlRedireccion = urlRedireccion.concat("/registroConfirmacion.xhtml?codeUnic=#codeUnic&codeNam=#codeNam");

        String usuPassword = Util.crypt(bean.getUsuario().getClave());
        urlRedireccion = urlRedireccion.replace("#codeNam", bean.getUsuario().getUsuario());
        urlRedireccion = urlRedireccion.replace("#codeUnic", usuPassword);

        cuerpoMensaje = bean.getCuerpoMensaje() + " " + urlRedireccion;
        password = bean.getPassword();
        host = bean.getHost();
        port = bean.getPort();

        getTo().add(bean.getUsuario().getUsuario());
    }




    /*
     * Este metodo envia un email.
     * Usuario.- es la cuenta del usuario a la que se enviara el correo
     * configuracion.- es una estructura de dato del tipo mapa, el cual
     * contiene la configuracion del correo electronico.
     *
     */
    public void envioEmail2(UsrUsuario usuario,Map<String,String>configuracion) {

        //cargar(bean);
        olvidarContrasenia(usuario,configuracion);
        try {
            Properties props = new Properties();
            props.put("mail.smtp.socketFactory.port", port);
            props.setProperty("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.fallback", "false");

            props.put("mail.smtp.user", from);
            props.put("mail.smtp.pass", password);

            props.put("mail.smtp.tls.enable", "false");
            props.put("mail.smtp.tls.required", "false");
            props.put("mail.smtp.time", "11000");
            props.put("mail.debug", "true");

            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            logger.info("Autenticando usuario .... ");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (int i = 0; i < getTo().size(); i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(getTo().get(i)));
            }

            // *** Esto crea el cuerpo del mensaje *** //
            message.setSubject(subject);
            message.setText(cuerpoMensaje.concat("\n\nNotificación mail - Oficina Virtual: ").concat("\n\n\natte. \nAdministración Oficina Virtual"));

            // *** Enviamos el mensaje *** //
            logger.info("Enviando email .....");

            Transport t = session.getTransport("smtp");
            t.connect();
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (NoSuchProviderException nspe) {
            logger.info("No se encontró el servidor para envio de mensaje " + nspe.getMessage());
        } catch (MessagingException me) {
            logger.info("Error al envia el mensaje " + me.getMessage());
        }
    }

    public void olvidarContrasenia(UsrUsuario usuario,Map<String,String>configuracion) {
        // ** Se debe obtener todos estos Datos de un archivo property o de las paramétricas ** //TODO
        from=configuracion.get("from");
        subject=configuracion.get("subject");
        urlRedireccion = configuracion.get("urlRedireccion");
        String sw=configuracion.get("sw");

        //registroConfirmacion
        if(sw.equals("0")){
            urlRedireccion = urlRedireccion.concat("/registroConfirmacion.xhtml?codeUnic=#codeUnic&codeNam=#codeNam");
            String usuPassword = Util.crypt(usuario.getClave());
            urlRedireccion = urlRedireccion.replace("#codeNam", usuario.getUsuario());
            urlRedireccion = urlRedireccion.replace("#codeUnic", usuPassword);
        }
        //olvido contrasenia
        if(sw.equals("1")){
            urlRedireccion = urlRedireccion.concat("/olvidoContrasenia.xhtml?codeUnic=#codeUnic&codeNam=#codeNam");

            String usuPassword = Util.crypt(usuario.getClave());
            //usuPassword.replace("==","");
            urlRedireccion = urlRedireccion.replace("#codeNam", usuario.getUsuario());
            urlRedireccion = urlRedireccion.replace("#codeUnic", usuPassword);
        }


        cuerpoMensaje = configuracion.get("cuerpoMensaje")+"\n"+urlRedireccion;
        password = configuracion.get("password");
        host = configuracion.get("host");
        port = configuracion.get("port");
        //getTo().add("aquiroz@mc4.com.bo");
        getTo().add(usuario.getUsuario());



/*        from = "aquiroz@mc4.com.bo";
        subject = "Olvidar contrasenia";
        urlRedireccion = "http://localhost:8080/faces/pages";
        urlRedireccion = urlRedireccion.concat("/olvidoContrasenia.xhtml?codeUnic=#codeUnic");
        System.out.println("====>>>>  MANDANDO EMAIL direccion00  "+urlRedireccion);
        String usuPassword = Util.crypt(usuario.getClave());
        System.out.println("====>>>>  MANDANDO EMAIL usuPassword1  "+usuPassword);
        usuPassword.replace("==","");
        System.out.println("====>>>>  MANDANDO EMAIL usuPassword2  "+usuPassword);
        //urlRedireccion = urlRedireccion.replace("#codeNam", usuario.getUsuario());
        urlRedireccion = urlRedireccion.replace("#codeUnic", usuPassword);

        cuerpoMensaje = "Para completar esta operacion dirijase al siguiente Link :"+"\n"+urlRedireccion;
        password = "Ar13lqu1r0z";
        host = "mc4.com.bo";
        port = "25";
        //getTo().add("aquiroz@mc4.com.bo");
        getTo().add(usuario.getUsuario());*/
    }

    public ArrayList<String> getTo() {
        return to;
    }
}
