package bo.gob.mintrabajo.ovt.Util;

import bo.gob.mintrabajo.ovt.bean.TemplateInicioBean;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;
import com.google.common.cache.Cache;
import com.google.common.io.ByteStreams;
import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
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
            String tmp = parDominio.getDescripcion();
            if(tmp != null){
                return tmp;
            }
        } catch (Exception e) {
            System.out.println(" error " + e.getMessage());
        }
        return "DESCRIPCIÓN INVÁLIDA";
    }

    public static DataSource obtenerDatasource(){
        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteStreams.copy(Util.class.getResourceAsStream("/ovt.conf"), out);
            String fileData = new String(out.toByteArray());
            String jsonFinal = "";
            boolean tmp = false;

            for (int x = 0; x < fileData.length(); x++) {

                if (fileData.charAt(x) == 'j') {
                    tmp = true;
                }

                if (fileData.charAt(x) != ' ' && tmp) {
                    jsonFinal += fileData.charAt(x);
                } else if (tmp) {
                    break;
                }
            }

            String[] token = jsonFinal.split("[/:@\"]");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setServerName(token[7]); //192.168.50.12
            dataSource.setUser(token[3]);  //ovt
            dataSource.setPassword(token[4]);   //prueba
            dataSource.setDatabaseName(token[9]); //desa
            dataSource.setPortNumber(Integer.parseInt(token[8])); //1521
            dataSource.setDriverType(token[2]); // thin

            return dataSource;

        } catch (SQLException se) {
            System.out.println("Error sql al obtener el dataSource " + se.getMessage());
        } catch (IOException ie) {
            System.out.println("Error io al obtener el dataSource " + ie.getMessage());
        }

        return null;
    }


}
