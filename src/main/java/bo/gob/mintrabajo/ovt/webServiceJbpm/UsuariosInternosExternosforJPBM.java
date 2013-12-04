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

package bo.gob.mintrabajo.ovt.webServiceJbpm;

import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vealaro
 */
public class UsuariosInternosExternosforJPBM {
    private List<UsrUsuario> listaUsuariosInternos = new ArrayList<UsrUsuario>();
    private List<UsrUsuario> listaUsuariosExternos = new ArrayList<UsrUsuario>();
    private static final Logger logger = LoggerFactory.getLogger(UsuariosInternosExternosforJPBM.class);

    public UsuariosInternosExternosforJPBM() {
    }

    public UsuariosInternosExternosforJPBM(List<UsrUsuario> resultSetUsuariosInternos, List<UsrUsuario> resultSetUsuariosExternos) {
        logger.info("cargando a los usuarios para el servicio");
        this.listaUsuariosInternos = resultSetUsuariosInternos;
        this.listaUsuariosExternos = resultSetUsuariosExternos;
    }

    public List<UsrUsuario> getListaUsuariosInternos() {
        return listaUsuariosInternos;
    }

    public void setListaUsuariosInternos(List<UsrUsuario> listaUsuariosInternos) {
        this.listaUsuariosInternos = listaUsuariosInternos;
    }

    public List<UsrUsuario> getListaUsuariosExternos() {
        return listaUsuariosExternos;
    }

    public void setListaUsuariosExternos(List<UsrUsuario> listaUsuariosExternos) {
        this.listaUsuariosExternos = listaUsuariosExternos;
    }

    public List<UsrUsuario> converterObjectUsuario(ResultSet lista) {

        List<UsrUsuario> listaUsuarios = new ArrayList<UsrUsuario>();
        boolean sw = true;
        try {
            String campo = "";
            SimpleDateFormat format = new SimpleDateFormat();
            Date date = new Date();
            while (lista.next()) {
                UsrUsuario usrUsuario = new UsrUsuario();
                campo = lista.getString("ID_USUARIO");
                usrUsuario.setIdUsuario(new Long(campo));
                campo = lista.getString("USUARIO");
                usrUsuario.setUsuario(campo);
                campo = lista.getString("CLAVE");
                usrUsuario.setClave(campo);
                campo = lista.getString("TIPO_AUTENTICACION");
                usrUsuario.setTipoAutenticacion(campo);
                campo = lista.getString("ES_INTERNO");
                usrUsuario.setEsInterno(new Short(campo));
                campo = lista.getString("ES_DELEGADO");
                usrUsuario.setEsDelegado(new Short(campo));
                campo = lista.getString("FECHA_INHABILITACION");
                if (sw) {
                    System.out.println("         (campo != null)= " + (campo != null));
                    sw = false;
                }

                if (null != campo) {
                    date = format.parse(campo);
                    usrUsuario.setFechaInhabilitacion(date);
                }
                campo = lista.getString("FECHA_REHABILITACION");
                if (null != campo) {
                    date = format.parse(campo);
                    usrUsuario.setFechaRehabilitacion(date);
                }
                campo = lista.getString("ESTADO_USUARIO");
                usrUsuario.setEstadoUsuario(campo);
                campo = lista.getString("FECHA_BITACORA");
                //date = format.parse(campo != null && !campo.trim().isEmpty() && !campo.equals("null")?campo:null);
                //System.out.println("FECHA_BITACORA : "+campo);
                //usrUsuario.setFechaBitacora(date);
                campo = lista.getString("REGISTRO_BITACORA");
                usrUsuario.setRegistroBitacora(campo);
                listaUsuarios.add(usrUsuario);
            }
            System.out.println("ISISISISIISI:  " + listaUsuarios.size());
            System.out.println("ISISISISIISI:  " + listaUsuarios.size());
            System.out.println("ISISISISIISI:  " + listaUsuarios.size());
            System.out.println("ISISISISIISI:  " + listaUsuarios.size());
            System.out.println("ISISISISIISI:  " + listaUsuarios.size());

            return listaUsuarios;

        } catch (SQLException e) {
            logger.error("Error en SQLException", e.getMessage());
        } catch (ParseException p) {
            logger.error("Error en parser date", p.getMessage());
        } finally {
            logger.info("Limpiando los errores de la lista");
            listaUsuarios = new ArrayList<UsrUsuario>();
        }
        return listaUsuarios;
    }
}
