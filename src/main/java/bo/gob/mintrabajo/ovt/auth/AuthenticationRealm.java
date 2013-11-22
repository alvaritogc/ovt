package bo.gob.mintrabajo.ovt.auth;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.realm.jdbc.JdbcRealm;

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
//    protected static final String CUSTOM_USER_ROLES_QUERY =
//            // from user self
//            "SELECT SROLE_NAME from SEC_CLA_ROLES "
//                    + "JOIN SEC_REL_ROLE_USERS ON SROLE_ID = RRA_SROLE_ID "
//                    + "JOIN SEC_PRI_USERS ON RRA_USR_ID = USR_ID "
//                    + "WHERE USR_LOGIN_NAME = ?";
//    private static final String CUSTOM_PERMISSIONS_QUERY =
//            "select RES_DESC from SEC_CLA_RESOURCES "
//                    + "JOIN SEC_REL_ROLE_RESOURCES ON RES_ID = SROR_RES_ID "
//                    + "JOIN SEC_CLA_ROLES ON SROR_SROLE_ID = SROLE_ID "
//                    + "where SROLE_NAME = ?";
    //private static final Logger log = LoggerFactory.getLogger(AuthenticationRealm.class);

    public AuthenticationRealm() {

        setAuthenticationQuery(AUTENTICACION_DE_USUARIO);
        setPermissionsLookupEnabled(true);

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
//            DataSource dataSource = (DataSource) DriverManager.getConnection("jdbc:oracle:thin:@192.168.50.7:1521:DESA", "ovt", "ovt");
//            setDataSource(dataSource);

            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setServerName("192.168.50.7");
            dataSource.setUser("ovt");
            dataSource.setPassword("prueba");
            dataSource.setDatabaseName("DESA");
            dataSource.setPortNumber(1521);
            dataSource.setDriverType("thin");
            setDataSource(dataSource);

        } catch (SQLException se) {
            se.printStackTrace();
            java.util.logging.Logger.getLogger(AuthenticationRealm.class.getName()).log(Level.SEVERE, null, se);
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo authenticationInfo = (SimpleAuthenticationInfo) super.doGetAuthenticationInfo(token);



        System.out.println("SALT========================"+authenticationInfo.getCredentialsSalt());
        System.out.println("Credentials="+new String((char[])authenticationInfo.getCredentials()));
        System.out.println("Credentials.string="+new String((char[])authenticationInfo.getCredentials()));

        System.out.println("=================== TOKEN PASSWORD " + new String((char[])token.getCredentials()));
        System.out.println("=================== TOKEN PRINCIPA " + token.getPrincipal().toString());
        System.out.println("=================== AUTHE USUARIO " + new String((char[]) authenticationInfo.getCredentials()));
        System.out.println("La comparación de credenciales es ..... "+getCredentialsMatcher().doCredentialsMatch(token, authenticationInfo));


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

        //AbstractHash ahash = new SimpleHash(Sha256Hash.ALGORITHM_NAME);
        //ahash.setBytes(storedBytes);
        //System.out.println("GENERATED FROM DATABASE="+ahash.toBase64());

        return authenticationInfo;
    }

    private void checkNotNull(Object reference, String message) {
        if (reference == null) {
            throw new AuthenticationException(message);
        }
    }
}