package bo.gob.mintrabajo.ovt.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/28/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    //private static final Logger logger = LoggerFactory.getLogger(Util.class);

    // **** Encriptado y Decriptado BASE 64 **** //
    public static String crypt(String text) {
        Crypt crypt = new Crypt();
        return crypt.crypt(text);
    }

    public static String decrypt(String text) {
        Crypt crypt = new Crypt();
        return crypt.decrypt(text);
    }

    /// **** Validador de emails **** ///
    public static boolean validaCorreo(String email){
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(email);
        return matcher.matches();
    }
}
