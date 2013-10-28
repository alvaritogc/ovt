package bo.gob.mintrabajo.ovt.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/28/13
 * Time: 12:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServicioEnvioEmail {

    private String from = "";
    private String password = "";
    private ArrayList<String> to = new ArrayList<String>();
    private String host = "";
    private String port = "";
    private String subject = "";
    private String cuerpoMensaje = "";

    private static final Logger logger = LoggerFactory.getLogger(ServicioEnvioEmail.class);

    public void envioEmail(String emailRegistrado) {

        cargar(emailRegistrado);
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

    public void cargar(String email) {
        // ** Se debe obtener los datos de un archivo property o de las paramétricas ** //
        from = "gmercado@mc4.com.bo";
        subject = "Confirmación de registro de la oficina virtual";
        cuerpoMensaje = "Para completar su registro dirijase al siguiente Link https://localhost:8080/faces/pages/confirmacion.xhtml";
        password = "G4rym3rc4d0";
        host = "mc4.com.bo";
        port = "25";

        getTo().add(email);
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }
}
