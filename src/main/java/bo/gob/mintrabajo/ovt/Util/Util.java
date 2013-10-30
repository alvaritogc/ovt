package bo.gob.mintrabajo.ovt.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

}
