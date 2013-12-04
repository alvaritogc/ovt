/*
 * Copyright 2013 vealaro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bo.gob.mintrabajo.ovt.webservices;

import bo.gob.mintrabajo.ovt.webServiceJbpm.UsuariosInternosExternosforJPBM;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vealaro
 */
@WebService(serviceName = "WebServiceForJBPM")
public class WebServiceForJBPM {

    private static final Logger logger = LoggerFactory.getLogger(WebServiceForJBPM.class);

    @WebMethod(operationName = "helloSuma")
    public double Add(@WebParam(name = "variable_x") double x, @WebParam(name = "variable_y") double y) {
        logger.info("Probando el test");
        double resultado = x + y;
        return resultado;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "UsuariosExternos")
    public UsuariosInternosExternosforJPBM UsuariosExternos() {
        logger.info("Listando a los usuarios");
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.50.7:1521:DESA", "ovt", "prueba");
            Statement stmt = conn.createStatement();
            UsuariosInternosExternosforJPBM jBPM = new UsuariosInternosExternosforJPBM();
            ResultSet rset1 = stmt.executeQuery("SELECT * FROM usr_usuario u where u.es_interno=1 and u.fecha_inhabilitacion is null and u.estado_usuario='A' ");
            jBPM.setListaUsuariosInternos(jBPM.converterObjectUsuario(rset1));
            //rset1.next();
            ResultSet rset2 = stmt.executeQuery("SELECT * FROM usr_usuario u where u.es_interno=0 and u.fecha_inhabilitacion is null and u.estado_usuario='A' ");
            //rset2.next();
            jBPM.setListaUsuariosExternos(jBPM.converterObjectUsuario(rset2));
            return jBPM;
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("Error al ejecutar webservice", e.getMessage());
        }
        return null;
    }

    ///*** Método que retorna el login por documento de documento  ***///
    @WebMethod(operationName = "retornaCorreoElectronico")
    public String retornaCorreoElectronico (@WebParam(name = "nroDocumento") String nroDocumento) {
        try{

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            Connection conn = DriverManager.getConnection ("jdbc:oracle:thin:@192.168.50.7:1521:DESA", "ovt", "prueba");

            Statement stmt = conn.createStatement();
            ResultSet rset1 = stmt.executeQuery("select * from PER_PERSONA where NRO_IDENTIFICACION = '" + nroDocumento + "'");
            rset1.next();

            String idPersona = rset1.getString("ID_PERSONA");
            ResultSet rset2 = stmt.executeQuery("select * from USR_USUARIO where ID_PERSONA = '" + idPersona + "'");
            rset2.next();

            String usuarioLogin = rset2.getString("USUARIO");

            return usuarioLogin;
        }catch (Exception e){
            //e.printStackTrace();
            logger.error("Error al ejecutar el metodo retornaCorreoElectronico() " + e.getMessage());
        }
        return null;
    }
}
