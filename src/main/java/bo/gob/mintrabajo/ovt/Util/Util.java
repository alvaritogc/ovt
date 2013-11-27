package bo.gob.mintrabajo.ovt.Util;

import bo.gob.mintrabajo.ovt.bean.TemplateInicioBean;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;
import com.google.common.cache.Cache;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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
        try{
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(email);
        return matcher.matches();
        }catch (NullPointerException ne){
            return false;
        }
    }

    private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

    public static String encriptaMD5(String stringAEncriptar) {

        try {
            MessageDigest msgd = MessageDigest.getInstance("MD5");
            byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
            StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++)
            {
                int bajo = (int)(bytes[i] & 0x0f);
                int alto = (int)((bytes[i] & 0xf0) >> 4);
                strbCadenaMD5.append(CONSTS_HEX[alto]);
                strbCadenaMD5.append(CONSTS_HEX[bajo]);
            }
            return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (NullPointerException ne) {
            return "cadena invalida";
        }
    }


    public static String descripcionDominio(String idDominio, String valor){
        try{
            Cache<ParDominioPK,ParDominio> mapaDominio = TemplateInicioBean.mapaDominio;
            ParDominioPK pk = new ParDominioPK();
            pk.setIdDominio(idDominio);
            pk.setValor(valor);
            ParDominio parDominio = mapaDominio.asMap().get(pk);
            return parDominio.getDescripcion();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
