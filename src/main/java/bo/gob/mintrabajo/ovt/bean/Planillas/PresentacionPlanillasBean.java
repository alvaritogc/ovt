package bo.gob.mintrabajo.ovt.bean.Planillas;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedProperty;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.DocDocumentoEntity;
import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import bo.gob.mintrabajo.ovt.entities.PerUnidadEntity;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import java.math.BigDecimal;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class PresentacionPlanillasBean {


    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private int idUsuario;
    private static final Logger logger = LoggerFactory.getLogger(PresentacionPlanillasBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;

    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;

    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService idDocumentoService;

    @ManagedProperty(value = "#{definicionService}")
    private IDefinicionService iDefinicionService;
    //
    private String textoBenvenida;
    //
    private PerPersonaEntity persona;
    //
    private DocDocumentoEntity documento;

    @PostConstruct
    public void ini() {
        logger.info("BienvenidaBean.init()");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal bi = BigDecimal.valueOf(idUsuario);
        logger.info("Buscando usuario" + bi);
        UsrUsuarioEntity usuario = iUsuarioService.findById(bi);
        logger.info("usuario ok");
        persona = iPersonaService.buscarPorId(usuario.getIdPersona());
        logger.info("persona ok");
        cargar();
    }

    public void cargar() {
        documento = new DocDocumentoEntity();
    }

    public void guardar(FileUploadEvent event) {
        System.out.println("entrando a guardarBinario...............................");

        documento = new DocDocumentoEntity();
        logger.info("1");
        documento.setIdPersona(persona.getIdPersona());
        logger.info("2");
        PerUnidadEntity unidad = iUnidadService.listarPorPersona(persona.getIdPersona()).get(0);
        logger.info("3");
        documento.setIdUnidad(unidad.getIdUnidad());
        logger.info("4");
        documento.setRegistroBitacora(""+idUsuario);
        documento = idDocumentoService.guardar(documento);
        logger.info("7");
        Random r= new Random(1000);

        UploadedFile file = event.getFile();
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu  = new ServletFileUpload(factory);

            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.50.7:1521:DESA", "ovt", "ovt");
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("insert into DOC_BINARIO values(?,?,?,?,?,?,?)");
            ps.setInt(1, r.nextInt());

            ps.setInt(2, documento.getIdDocumento());
            ps.setString(3, file.getContentType());
            ps.setBinaryStream(4, new ByteArrayInputStream(file.getContents()), (int) file.getSize());
            ps.setString(5, file.getFileName()+file.getContentType());
            ps.setDate(6, fecha());
            ps.setString(7, "ROE");

            ps.executeUpdate();
            con.commit();
            con.close();
            System.out.println("archivo añadido exitosamente");
        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Error --> " + ex.getMessage());
        }
    }

    public java.sql.Date fecha(){
        java.util.Date fecha= new java.util.Date();
        return new java.sql.Date(fecha.getTime());
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public String getTextoBenvenida() {
        return textoBenvenida;
    }

    public void setTextoBenvenida(String textoBenvenida) {
        this.textoBenvenida = textoBenvenida;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public PerPersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PerPersonaEntity persona) {
        this.persona = persona;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public DocDocumentoEntity getDocumento() {
        return documento;
    }

    public void setDocumento(DocDocumentoEntity documento) {
        this.documento = documento;
    }

    public IDefinicionService getiDefinicionService() {
        return iDefinicionService;
    }

    public void setiDefinicionService(IDefinicionService iDefinicionService) {
        this.iDefinicionService = iDefinicionService;
    }

    public IDocumentoService getIdDocumentoService() {
        return idDocumentoService;
    }

    public void setIdDocumentoService(IDocumentoService idDocumentoService) {
        this.idDocumentoService = idDocumentoService;
    }
}