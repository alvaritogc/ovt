package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.bean.*;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@ViewScoped
public class TotalEmpleadorPorGeneroBean implements Serializable {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private Long idUsuario;
    private String idPersona;
    private String idEmpleador;
    private static final Logger logger = LoggerFactory.getLogger(EscritorioBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoEstadoService}")
    private IDocumentoEstadoService iDocumentoEstadoService;
    @ManagedProperty(value = "#{vperPersonaService}")
    private IVperPersonaService iVperPersonaService;
    @ManagedProperty(value = "#{definicionService}")
    private IDefinicionService iDefinicionService;
    @ManagedProperty(value = "#{docGenericoService}")
    private IDocGenericoService iDocGenericoService;
    @ManagedProperty(value = "#{entidadService}")
    private IEntidadService iEntidadService;
    @ManagedProperty(value = "#{localidadService}")
    private ILocalidadService iLocalidadService;
    //
    private String tipoReporte;
    private String codLocalidad;
    private List<ParLocalidad> listaLocalidades;


    @PostConstruct
    public void ini() {
        logger.info("TotalEmpleadorPorGeneroBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
        idEmpleador = (String) session.getAttribute("idEmpleador");
        cargar();
    }

    public void cargar() {
        listaLocalidades = iLocalidadService.listarDepartamentos();
    }

    public void generarReporte() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String rutaWebApp = servletContext.getRealPath("/");
        String jrxmlFileName = rutaWebApp + "/reportes/totalEmpleadorPorGenero.jrxml";
        String jasperFileName = rutaWebApp + "/reportes/totalEmpleadorPorGenero.jasper";
        String pdfFileName = "";
        String dbDriver = "oracle.jdbc.driver.OracleDriver";
        Connection conn = null;
        Date fecha = new Date();
        pdfFileName = "totalEmpleadorPorGenero" + fecha.getTime() + ".pdf";
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            logger.error("ORACLE JDBC Driver no encontrado");
        }
        try {
            //conn= DriverManager.getConnection(dbUrl, dbUname, dbPwd);
            conn = Util.obtenerDatasource().getConnection();
        } catch (SQLException e) {
            logger.error("Error de conexion " + e.getMessage());
        }

        try {
            // Paramatros para el reporte
            //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            HashMap<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("escudoBolivia", servletContext.getRealPath("/") + "images/escudo.jpg");
            parametros.put("logo", servletContext.getRealPath("/") + "images/logoMIN.jpg");
            if (tipoReporte.equals("todas")) {
                parametros.put("mostrarDetalles", "true");
            } else {
                parametros.put("mostrarDetalles", "false");
            }
            parametros.put("codLocalidad", codLocalidad);
            JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);

            String rutaPdf = "reportes/temp/" + pdfFileName;
            String nombrePdf = rutaWebApp + rutaPdf;
            JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, parametros, conn);
            JasperExportManager.exportReportToPdfFile(jprint, nombrePdf);
            redirecionarReporte(nombrePdf);

        } catch (Exception ex) {
            logger.error("====>>>> Error al exportar el reporte a PDF <<<<<=====");
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void redirecionarReporte(String rutaReporte) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        File file = new File(rutaReporte);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {

            input = new BufferedInputStream(new FileInputStream(file), 10240);
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "filename=" + file.getName());
            response.setContentLength((int) file.length());
            output = new BufferedOutputStream(response.getOutputStream(), 10240);

            byte[] buffer = new byte[10240];
            int length;

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(output);
            close(input);
        }

        facesContext.responseComplete();
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public IVperPersonaService getiVperPersonaService() {
        return iVperPersonaService;
    }

    public void setiVperPersonaService(IVperPersonaService iVperPersonaService) {
        this.iVperPersonaService = iVperPersonaService;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public IDocumentoEstadoService getiDocumentoEstadoService() {
        return iDocumentoEstadoService;
    }

    public void setiDocumentoEstadoService(IDocumentoEstadoService iDocumentoEstadoService) {
        this.iDocumentoEstadoService = iDocumentoEstadoService;
    }

    public IDefinicionService getiDefinicionService() {
        return iDefinicionService;
    }

    public void setiDefinicionService(IDefinicionService iDefinicionService) {
        this.iDefinicionService = iDefinicionService;
    }

    public IDocGenericoService getiDocGenericoService() {
        return iDocGenericoService;
    }

    public void setiDocGenericoService(IDocGenericoService iDocGenericoService) {
        this.iDocGenericoService = iDocGenericoService;
    }

    public IEntidadService getiEntidadService() {
        return iEntidadService;
    }

    public void setiEntidadService(IEntidadService iEntidadService) {
        this.iEntidadService = iEntidadService;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getCodLocalidad() {
        return codLocalidad;
    }

    public void setCodLocalidad(String codLocalidad) {
        this.codLocalidad = codLocalidad;
    }

    public List<ParLocalidad> getListaLocalidades() {
        return listaLocalidades;
    }

    public void setListaLocalidades(List<ParLocalidad> listaLocalidades) {
        this.listaLocalidades = listaLocalidades;
    }

    public ILocalidadService getiLocalidadService() {
        return iLocalidadService;
    }

    public void setiLocalidadService(ILocalidadService iLocalidadService) {
        this.iLocalidadService = iLocalidadService;
    }
}