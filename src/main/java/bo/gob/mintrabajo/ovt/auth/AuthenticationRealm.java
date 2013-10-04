package bo.gob.mintrabajo.ovt.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.AbstractHash;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ByteSource;
import org.postgresql.util.Base64;

import javax.ejb.TransactionAttribute;
import javax.inject.Named;
import java.io.IOException;
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
    protected static final String CUSTOM_AUTHENTICATION_QUERY =
            "SELECT USR_PASSWD, USR_PASSWD_SALT FROM SEC_PRI_USERS WHERE USR_LOGIN_NAME = ?";
    /**
     * The default query used to retrieve the roles that apply to a user.
     */
    protected static final String CUSTOM_USER_ROLES_QUERY =
            // from user self
            "SELECT SROLE_NAME from SEC_CLA_ROLES "
                    + "JOIN SEC_REL_ROLE_USERS ON SROLE_ID = RRA_SROLE_ID "
                    + "JOIN SEC_PRI_USERS ON RRA_USR_ID = USR_ID "
                    + "WHERE USR_LOGIN_NAME = ?";
    private static final String CUSTOM_PERMISSIONS_QUERY =
            "select RES_DESC from SEC_CLA_RESOURCES "
                    + "JOIN SEC_REL_ROLE_RESOURCES ON RES_ID = SROR_RES_ID "
                    + "JOIN SEC_CLA_ROLES ON SROR_SROLE_ID = SROLE_ID "
                    + "where SROLE_NAME = ?";
    //private static final Logger log = LoggerFactory.getLogger(AuthenticationRealm.class);

    public AuthenticationRealm() {
        //assert jndiDataSourceName!=null;

        //this.jndiDataSourceName = jndiDataSourceName;
        //setDataSource(getDataSourceFromJNDI(getJndiDataSourceName()));

        setAuthenticationQuery(CUSTOM_AUTHENTICATION_QUERY);
        setUserRolesQuery(CUSTOM_USER_ROLES_QUERY);
        setPermissionsQuery(CUSTOM_PERMISSIONS_QUERY);

        setPermissionsLookupEnabled(true);

        setSaltStyle(SaltStyle.COLUMN);

        //try {
            //setDataSource((DataSource) new InitialContext().lookup("java:comp/env/jdbc/data"));
            ///setDataSource(DataStoreManager.getDataSource());
        //} catch (IOException ex) {
          //  java.util.logging.Logger.getLogger(AuthenticationRealm.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo authenticationInfo = (SimpleAuthenticationInfo) super.doGetAuthenticationInfo(token);

        final String storedSalt = new String(authenticationInfo.getCredentialsSalt().getBytes());

        ByteSource salt = ByteSource.Util.bytes(Base64.decode(storedSalt));

        authenticationInfo.setCredentialsSalt(salt);
//
//        /*
//        //AS we stored the password and credentials in BASE64, then need to DECODE de stored salt in BASE64
//        Object storedPassword = authenticationInfo.getCredentials();//char array
//        SimpleByteSource storedSalt = (SimpleByteSource) authenticationInfo.getCredentialsSalt();
//        String hashedPassword = new Sha256Hash(token.getCredentials(), storedSalt, 1024).toBase64();
//
//        System.out.println(">>>SALT="+storedSalt);
//        System.out.println(">>>STORED PWD="+new String((char[])storedPassword));
//        System.out.println(">>>TRY PWD="+hashedPassword);
//
//        String ss = CodecSupport.toString(storedSalt.getBytes());
//        ByteSource newSalt = ByteSource.Util.bytes(Base64.decode(ss));
//
//        System.out.println(">>>SALT DECODED="+newSalt.toBase64());
//
//        authenticationInfo.setCredentialsSalt(newSalt);
//        */
//
//
//
        System.out.println("SALT========================"+authenticationInfo.getCredentialsSalt());
        System.out.println("Credentials="+authenticationInfo.getCredentials().getClass());
        System.out.println("Credentials.string="+new String((char[])authenticationInfo.getCredentials()));
        System.out.println("CredentialMatcher..."+getCredentialsMatcher().doCredentialsMatch(token, authenticationInfo));


        CredentialsMatcher cm = getCredentialsMatcher();

        cm.doCredentialsMatch(token, authenticationInfo);

        Hash hash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, token.getCredentials(), salt, 1024);

        System.out.println("TOKENS to hash="+hash.toBase64());

        Object credentials = authenticationInfo.getCredentials();

        byte[] storedBytes = CodecSupport.toBytes((char[])credentials);

        if (credentials instanceof String || credentials instanceof char[]) {
            //account.credentials were a char[] or String, so
            //we need to do text decoding first:
            if (((HashedCredentialsMatcher)getCredentialsMatcher()).isStoredCredentialsHexEncoded()) {
                storedBytes = Hex.decode(storedBytes);
            } else {
                storedBytes = Base64.decode(storedBytes, 0, storedBytes.length);
            }
        }

        AbstractHash ahash = new SimpleHash(Sha256Hash.ALGORITHM_NAME);
        ahash.setBytes(storedBytes);

        System.out.println("GENERATED FROM DATABASE="+ahash.toBase64());

        return authenticationInfo;
    }

    public static void main(String[] args) {
        Object credentials = "OXv5nAd75V9GfsKwElTg1YlfTEqQjn10HXIGfosZHXk=".toCharArray();

        byte[] storedBytes = CodecSupport.toBytes((char[])credentials);

        boolean isStoredCredentialsHexEncoded = false;

        if (credentials instanceof String || credentials instanceof char[]) {
            //account.credentials were a char[] or String, so
            //we need to do text decoding first:
            if (isStoredCredentialsHexEncoded) {
                storedBytes = Hex.decode(storedBytes);
            } else {
                storedBytes = Base64.decode(storedBytes, 0, storedBytes.length);
            }
        }

        AbstractHash ahash = new SimpleHash(Sha256Hash.ALGORITHM_NAME);
        ahash.setBytes(storedBytes);

        System.out.println("GENERATED FROM DATABASE="+ahash.toBase64());
    }
}