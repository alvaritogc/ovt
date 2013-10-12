package bo.gob.mintrabajo.ovt.envano;

import bo.gob.mintrabajo.ovt.entities.VperPersonaEntity;

import java.sql.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/12/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class DobleTrabajoConexion {

    public static VperPersonaEntity obtenerPersona(String id) {

        VperPersonaEntity temp = new VperPersonaEntity();

        try{
        DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());

        Connection conn = DriverManager.getConnection ("jdbc:oracle:thin:@192.168.50.7:1521:DESA", "ovt", "ovt");

        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("select * from vper_persona p where id_unidad = 0 and id_persona = " + "'" + id + "'");
        while (rset.next()) {
            temp.setActividadDeclarada(rset.getString("ACTIVIDAD_DECLARADA"));
            temp.setApellidoMaterno(rset.getString("APELLIDO_MATERNO"));
            temp.setApellidoPaterno(rset.getString("APELLIDO_PATERNO"));
            temp.setCodLocalidad(rset.getString("COD_LOCALIDAD"));
            temp.setDirCodDepartamento(rset.getBigDecimal("DIR_COD_DEPARTAMENTO"));
            temp.setDirDepartamento(rset.getString("DIR_DEPARTAMENTO"));
            temp.setDirDireccion(rset.getString("DIR_DIRECCION"));
            temp.setDirLocalidad(rset.getString("DIR_LOCALIDAD"));
            temp.setDirNroDireccion(rset.getString("DIR_NRO_DIRECCION"));
            temp.setDirZona(rset.getString("DIR_ZONA"));
            temp.setEsNatural(rset.getBigDecimal("ES_NATURAL"));
            temp.setEstadoUnidad(rset.getString("ESTADO_UNIDAD"));
            temp.setFechaNacimiento(rset.getDate("FECHA_NACIMIENTO"));
            temp.setIdPersona(rset.getString("ID_PERSONA"));
            temp.setIdUnidad(rset.getBigDecimal("ID_UNIDAD"));
            temp.setNombreComercial(rset.getString("NOMBRE_COMERCIAL"));
            temp.setNombreRazonSocial(rset.getString("NOMBRE_RAZON_SOCIAL"));
            temp.setNroAfp(rset.getString("NRO_AFP"));
            temp.setNroCajaSalud(rset.getString("NRO_CAJA_SALUD"));
            temp.setNroFundaempresa(rset.getString("NRO_FUNDAEMPRESA"));
            temp.setNroIdentificacion(rset.getBigDecimal("NRO_IDENTIFICACION"));
            temp.setNroOtro(rset.getString("NRO_OTRO"));
            temp.setRlNombre(rset.getString("RL_NOMBRE"));
            temp.setRlNroIdentidad(rset.getString("RL_NRO_IDENTIDAD"));
            temp.setRlNroPermiso(rset.getString("RL_NRO_PERMISO"));
            temp.setTipoEmpresa(rset.getString("TIPO_EMPRESA"));
            temp.setTipoIdentificacion(rset.getString("TIPO_IDENTIFICACION"));
            temp.setTipoSociendad(rset.getString("TIPO_SOCIENDAD"));

            temp.setEmail(rset.getString("EMAIL"));
            temp.setEstado(rset.getString("ESTADO"));
            temp.setEstado(rset.getString("FAX"));
            temp.setLocalidad(rset.getString("LOCALIDAD"));
            temp.setObservaciones(rset.getString("OBSERVACIONES"));
            temp.setTelefono(rset.getString("TELEFONO"));
            temp.setTempresa(rset.getString("TEMPRESA"));
            temp.setTsociedad(rset.getString("TSOCIEDAD"));
        }
        stmt.close();

        }catch (SQLException se){
            System.out.println("Error al cargar la persona y sus entidades " + se.getMessage());
        }

        return temp;
    }

//    public static void main(String arg[]) throws SQLException {
//        DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
//
//        Connection conn = DriverManager.getConnection ("jdbc:oracle:thin:@192.168.50.7:1521:DESA", "ovt", "ovt");
//
//        Statement stmt = conn.createStatement();
//        ResultSet rset = stmt.executeQuery("select * from vper_persona p where rownum < 20");
//        while (rset.next()){
//                System.out.println (rset.getString("ID_PERSONA"));   // Print col 1
//                System.out.println (rset.getString("EMAIL"));
//                System.out.println(rset.getString("TSOCIEDAD"));
//            }
//        stmt.close();
//    }
}
