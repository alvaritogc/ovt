package bo.gob.mintrabajo.ovt.auth;

import bo.gob.mintrabajo.ovt.Util.Util;
import com.google.common.io.ByteStreams;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
* Created with IntelliJ IDEA.
* User: gmercado
* Date: 10/4/13
* Time: 3:08 PM
* To change this template use File | Settings | File Templates.
*/


public class AuthenticationRealm extends JdbcRealm {

    /**
     * The default query used to retrieve account data for the user.
     */
    protected static final String AUTENTICACION_DE_USUARIO =
            "SELECT CLAVE FROM USR_USUARIO WHERE USUARIO = ?";
    /**
     * The default query used to retrieve the roles that apply to a user.
     */
    protected static final String AUTENTICACION_ROLES_DE_USUARIO =
            "SELECT usr_rol.nombre " +
                    "FROM usr_rol " +
                    "INNER JOIN usr_usuario_rol " +
                    "ON usr_usuario_rol.id_rol = usr_rol.id_rol " +
                    "WHERE usr_usuario_rol.id_usuario = (SELECT id_usuario FROM usr_usuario WHERE usr_usuario.usuario = ?)";

    private static final String AUTENTICACION_PERMISO_DE_USUARIO =
            "select ejecutable " +
                    "from usr_recurso " +
                    "inner join usr_rol_recurso on usr_rol_recurso.id_recurso = usr_recurso.id_recurso " +
                    "inner join usr_rol on usr_rol_recurso.id_rol = usr_rol.id_rol " +
                    "where usr_rol.nombre = ?";
    private static final Logger log = LoggerFactory.getLogger(AuthenticationRealm.class);

    public AuthenticationRealm() {

        setAuthenticationQuery(AUTENTICACION_DE_USUARIO);
        setUserRolesQuery(AUTENTICACION_ROLES_DE_USUARIO);
        setPermissionsQuery(AUTENTICACION_PERMISO_DE_USUARIO);
        setPermissionsLookupEnabled(false);
        setDataSource(Util.obtenerDatasource());
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo authenticationInfo = (SimpleAuthenticationInfo) super.doGetAuthenticationInfo(token);

        log.info("Credentials="+new String((char[])authenticationInfo.getCredentials()));
        log.info("Credentials.string="+new String((char[])authenticationInfo.getCredentials()));

        log.info("=================== TOKEN PASSWORD " + new String((char[])token.getCredentials()));
        log.info("=================== TOKEN PRINCIPA " + token.getPrincipal().toString());
        log.info("=================== AUTHE USUARIO " + new String((char[]) authenticationInfo.getCredentials()));
        log.info("La comparación de credenciales es ..... "+getCredentialsMatcher().doCredentialsMatch(token, authenticationInfo));


        CredentialsMatcher cm = getCredentialsMatcher();
        cm.doCredentialsMatch(token, authenticationInfo);


        //Hash hash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, token.getCredentials(), salt, 1024);
        //System.out.println("TOKENS to hash="+hash.toBase64());

        Object credentials = authenticationInfo.getCredentials();
        byte[] storedBytes = CodecSupport.toBytes((char[]) credentials);


        if (credentials instanceof String || credentials instanceof char[]) {
            //account.credentials were a char[] or String, so
            //we need to do text decoding first:
//            if (((HashedCredentialsMatcher)getCredentialsMatcher()).isStoredCredentialsHexEncoded()) {
//                storedBytes = Hex.decode(storedBytes);
//            } else {
//                storedBytes = Base64.decode(storedBytes, 0, storedBytes.length);
//            }
        }
        return authenticationInfo;
    }

    private void checkNotNull(Object reference, String message) {
        if (reference == null) {
            throw new AuthenticationException(message);
        }
    }

    public static void main(String[] args) {
        try {
            // read JSON file data as String
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteStreams.copy(AuthenticationRealm.class.getResourceAsStream("/ovt.conf"), out);
            String fileData = new String(out.toByteArray());

            String jsonFinal = "";
            boolean tmp= false;

                for (int x = 0; x < fileData.length(); x++) {

                    if(fileData.charAt(x) == 'j') {
                        tmp = true;
                    }

                    if (fileData.charAt(x) != ' ' && tmp) {
                        jsonFinal += fileData.charAt(x);
                    } else if(tmp){
                        break;
                    }
                }

            String[] token = jsonFinal.split("[/:@\"]");

            for(int i = 0; i<token.length ; i++){
                System.out.println("------ " + token[i] + " " + i);
            }

        } catch (Exception ie) {
            ie.printStackTrace();
        }
    }
}