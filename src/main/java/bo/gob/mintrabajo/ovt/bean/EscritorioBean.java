package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//import bo.gob.mintrabajo.ovt.envano.DobleTrabajoConexion;
//import java.util.Collection;
//import java.util.Collection;

@ManagedBean
@ViewScoped
public class EscritorioBean {
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
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{planillaService}")
    private IPlanillaService iPlanillaService;
    @ManagedProperty(value = "#{mensajeAppService}")
    private IMensajeAppService iMensajeAppService;
    @ManagedProperty(value = "#{documentoEstadoService}")
    private IDocumentoEstadoService iDocumentoEstadoService;
    @ManagedProperty(value = "#{vperPersonaService}")
    private IVperPersonaService iVperPersonaService;
    @ManagedProperty(value = "#{binService}")
    private IBinarioService iBinarioService;

    //
    private String textoBenvenida;
    //
    private UsrUsuario usuario;
    private PerPersona empleador;
    private PerPersona persona;
    private List<PerUnidad> listaUnidades;
    private List<DocDocumento> listaDocumentos;
    //
    private boolean esInterno;
    //
    private DocDocumento docDocumento;
    private DocPlanilla docPlanilla;
    private ParDocumentoEstado parDocumentoEstado;
    private List<ParDocumentoEstado> listaDocumentoEstado;
    private String codEstadoFinal;
    private VperPersona vperPersona;
    //
    private boolean mostrarCambioDeEstados;

    private HashMap<String,Object> parametros = new HashMap<String,Object>();

    @PostConstruct
    public void ini() {
        logger.info("BienvenidaBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
        idPersona = (String) session.getAttribute("idPersona");
        idEmpleador = (String) session.getAttribute("idEmpleador");
        System.out.println("idPersona: " + idPersona);
        System.out.println("idEmpleador: " + idEmpleador);
        //
        usuario = iUsuarioService.findById(idUsuario);
        persona = iPersonaService.findById(idPersona);
        empleador = iPersonaService.findById(idEmpleador);
        if (usuario.getEsInterno() == 1) {
            esInterno = true;
        } else {
            esInterno = false;
        }
        cargar();
    }

    public void cargar() {
        textoBenvenida = "Bienvenido  OVT";
        listaUnidades = iUnidadService.buscarPorPersona(idEmpleador);
        cargarDocumentos();
    }

    public void cargarDocumentos() {
        try {
            listaDocumentos = iDocumentoService.listarPorPersona(idEmpleador);
            if (listaDocumentos == null) {
                listaDocumentos = new ArrayList<DocDocumento>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            listaDocumentos = new ArrayList<DocDocumento>();
        }
    }

    public String download() {
        session.setAttribute("idDocumento", docDocumento.getIdDocumento());
        return "irDownload";
    }

    public String irRealizarCambioDeEstados() {
//        session.setAttribute("idDocumento", docDocumentoEntity.getIdDocumento());
//        session.setAttribute("codEstadoInicial", docDocumentoEntity.getCodEstado());
//        session.setAttribute("codDocumento", docDocumentoEntity.getCodDocumento());
//        session.setAttribute("version", docDocumentoEntity.getVersion());
        return "irCambioEstado";
    }
    public void cargarCambioDeEstados(){
        try {
            docPlanilla=iPlanillaService.buscarPorDocumento(docDocumento.getIdDocumento());
        } catch (Exception e) {
            //e.printStackTrace();
            docPlanilla=null;
        }
        
        listaDocumentoEstado=iDocumentoEstadoService.listarSiguientesTransiciones(docDocumento);
        System.out.println("size: "+listaDocumentoEstado.size());
        if(!listaDocumentoEstado.isEmpty()){
            codEstadoFinal=listaDocumentoEstado.get(0).getCodEstado();
            mostrarCambioDeEstados=true;
        }
        else{
            codEstadoFinal="";
            mostrarCambioDeEstados=false;
        }
        
        
    }
    public String realizarCambioDeEstados(){
        parDocumentoEstado=iDocumentoEstadoService.findById(codEstadoFinal);
        //
        docDocumento=iDocumentoService.guardarCambioEstado(docDocumento, parDocumentoEstado, idPersona);
        //
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("cambioEstadoDialog.hide()");
        //
        cargar();
        return "";
    }

    public void irImprimirDocumento() {
        String codDocumento =docDocumento.getDocDefinicion().getDocDefinicionPK().getCodDocumento();
        String rutaPdf;
        String idPersonaPorDocumento= docDocumento.getPerUnidad().getPerPersona().getIdPersona();
        vperPersona = iVperPersonaService.cargaVistaPersona(idPersonaPorDocumento);
        Long idUsuarioEmpleador=iUsuarioService.obtenerUsuarioPorIdPersona(idPersonaPorDocumento).getIdUsuario();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if(codDocumento.equals("LC1010")){
            try{
//                vperPersona = iVperPersonaService.cargaVistaPersona(docDocumento.getPerUnidad().getPerPersona().getIdPersona());
                docPlanilla=iPlanillaService.buscarPorDocumento(docDocumento.getIdDocumento());
                parametros.clear();
                parametros.put("nroOrden", docDocumento.getNumeroDocumento());
                parametros.put("rectificatoria", " ");
                parametros.put("nroRectificatoria", " ");
                parametros.put("totalNacional", "X");
                parametros.put("oficinaCentral", docDocumento.getPerUnidad().getNombreComercial());
                parametros.put("mesPresentacion", docPlanilla.getParCalendario().getParCalendarioPK().getTipoPeriodo());
                parametros.put("empleadorMTEPS", docDocumento.getPerUnidad().getNroReferencial());
                parametros.put("razonSocial", persona.getNombreRazonSocial());
                parametros.put("departamento", vperPersona.getDirDepartamento());
                parametros.put("direccion", vperPersona.getDirDireccion());
                parametros.put("telefono", vperPersona.getTelefono());
                parametros.put("patronalSS", docDocumento.getPerUnidad().getNroCajaSalud());
                parametros.put("ciudadLocalidad", vperPersona.getLocalidad());
                parametros.put("fax", vperPersona.getFax());
                parametros.put("nit", vperPersona.getNroIdentificacion() +"");
                parametros.put("actividadEconomica", vperPersona.getActividadDeclarada());
                parametros.put("zona", vperPersona.getDirZona());
                parametros.put("numero", vperPersona.getDirNroDireccion());
                parametros.put("correoElectronico", vperPersona.getEmail());
                parametros.put("nroAsegurados", docPlanilla.getNroAsegCaja());
                parametros.put("montoAportadoAsegurados",docPlanilla.getMontoAsegCaja());
                parametros.put("gestorSalud", docPlanilla.getIdEntidadSalud().getDescripcion());
                parametros.put("nroAfiliados",docPlanilla.getNroAsegAfp());
                parametros.put("montoAportadoAfiliados",docPlanilla.getMontoAsegAfp());
                parametros.put("haberBasico",docPlanilla.getHaberBasico());
                parametros.put("bonoAntiguedad",docPlanilla.getBonoAntiguedad());
                parametros.put("bonoProduccion",docPlanilla.getBonoProduccion());
                parametros.put("subsidioFrontera",docPlanilla.getSubsidioFrontera());
                parametros.put("laborExtraordinaria",docPlanilla.getLaborExtra());
                parametros.put("otrosBono",docPlanilla.getOtrosBonos());
                parametros.put("aporteAFP",docPlanilla.getAporteAfp());
                parametros.put("rcIVA",docPlanilla.getRciva());
                parametros.put("otrosDescuentos",docPlanilla.getOtrosDescuentos());
                parametros.put("totalMujeres",docPlanilla.getNroM());
                parametros.put("totalVarones",docPlanilla.getNroH());
                parametros.put("mujeresJubiladas",docPlanilla.getNroJubiladosM());
                parametros.put("varonesJubilados",docPlanilla.getNroJubiladosH());
                parametros.put("mujeresExtranjeras",docPlanilla.getNroExtranjerosM());
                parametros.put("varonesExtranjeros",docPlanilla.getNroExtranjerosH());
                parametros.put("mujeresDiscapacidad",docPlanilla.getNroDiscapacidadM());
                parametros.put("varonesDiscapacidad",docPlanilla.getNroDiscapacidadH());
                parametros.put("mujeresContratadas",docPlanilla.getNroContratadosM());
                parametros.put("varonesContratados",docPlanilla.getNroContratadosH());
                parametros.put("mujeresRetiradas",docPlanilla.getNroRetiradosM());
                parametros.put("varonesRetirados",docPlanilla.getNroRetiradosH());
                parametros.put("totalAccidentes",docPlanilla.getNroAccidentes());
                parametros.put("accidentesMuerte",docPlanilla.getNroMuertes());
                parametros.put("enfermedadesTrabajos",docPlanilla.getNroEnfermedades());
//        parametros.put("email",docPlanilla.getIdEntidadBanco()); //----------------;
                Calendar cal = Calendar.getInstance();
                cal.setTime(docPlanilla.getFechaOperacion());
                parametros.put("diaDeposito", cal.get(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.MONTH, 1);
                parametros.put("mesDeposito", cal.get(Calendar.MONTH));
                parametros.put("anioDeposito", cal.get(Calendar.YEAR));
                cal = Calendar.getInstance();
                cal.setTime(docDocumento.getFechaDocumento());
                parametros.put("diaFechaPresentacion", cal.get(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.MONTH, 1);
                parametros.put("mesFechaPresentacion", cal.get(Calendar.MONTH));
                parametros.put("anioFechaPresentacion", cal.get(Calendar.YEAR));
                parametros.put("montoDeposito", docPlanilla.getMontoOperacion());
                parametros.put("nroComprobante",docPlanilla.getNumOperacion());
                parametros.put("nombreEmpleador", vperPersona.getRlNombre());
                parametros.put("nroDocumento", vperPersona.getRlNroIdentidad());
                parametros.put("lugarPresentacion", "Oficina Virtual");
                List<DocBinario> lista= iBinarioService.listarPorIdDocumento(docDocumento.getIdDocumento());

                for(int i=0;i<3;i++){
                    int nroArchivo=i+1;
                    parametros.put("archivo"+String.valueOf(nroArchivo), lista!=null && !lista.isEmpty()?lista.get(nroArchivo-1).getTipoDocumento():"");
                }
                parametros.put("escudoBolivia", servletContext.getRealPath("/")+"/images/escudo.jpg");
                parametros.put("logo",servletContext.getRealPath("/")+"/images/logoMIN.jpg");

                String nombrePdf="LC1010-".concat(Util.encriptaMD5(String.valueOf(idUsuarioEmpleador).concat(String.valueOf(idPersonaPorDocumento))))+".pdf";

                redirecionarReporte(iDocumentoService.generateReport(nombrePdf, "/reportes/formularioLC1010V1.jasper", parametros));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }


        if(codDocumento.equals("ROE010")){
            parametros.clear();
            parametros.put("codigoEmpleador", vperPersona.getNroIdentificacion());
            parametros.put("nombreRazonSocial", vperPersona.getNombreRazonSocial());
            parametros.put("departamento", vperPersona.getDirDepartamento());
            parametros.put("domOficina", vperPersona.getDirDireccion());
            parametros.put("repLegal", vperPersona.getRlNombre());
            parametros.put("fechaEmision", (new SimpleDateFormat("dd/MM/yyyy")).format(new Date()));
            parametros.put("nroUbicaciones", vperPersona.getNroOtro());


            parametros.put("roe", servletContext.getRealPath("/")+"/images/roe.jpg");

            try {
                String nombrePdf="ROE010".concat(Util.encriptaMD5(String.valueOf(idUsuarioEmpleador).concat(String.valueOf(idPersonaPorDocumento))))+".pdf";
                HttpServletRequest httpServletRequest = ((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext()).getRequest());
                String rutaUrl= httpServletRequest.getRequestURL().toString();
                if(rutaUrl.contains(".xhtml"))
                    rutaUrl= rutaUrl.replace("pages/escritorio.xhtml", "");
                if(rutaUrl.contains(".jsf"))
                    rutaUrl= rutaUrl.replace("pages/escritorio.jsf", "");

                String url=rutaUrl.concat("reportes/temp/")+nombrePdf;
                //genera el QR
                ByteArrayOutputStream out = QRCode.from(url).to(ImageType.PNG).stream();
               File file = new File(servletContext.getRealPath("/")+"/images/qr"+UUID.randomUUID()+".png");
                FileOutputStream fout = new FileOutputStream(file);
                fout.write(out.toByteArray());
                fout.flush();
                fout.close();
                //se asigna la imagen QR al reporte
                parametros.put("qr",servletContext.getRealPath("/")+"/images/"+file.getName());
                //manda al metodo generateReport()
                redirecionarReporte(iDocumentoService.generateReport(nombrePdf, "/reportes/roe.jasper", parametros));

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR al generar el reporte: "+e.getMessage());
            }
        }

        if(codDocumento.equals("ROE012")){
            try{
                parametros.clear();
                parametros.put("empleadorMTEPS", docDocumento.getPerUnidad().getNroReferencial());
                parametros.put("razonSocial", vperPersona.getNombreRazonSocial());
                parametros.put("departamento", vperPersona.getDirDepartamento());
                parametros.put("direccion", vperPersona.getDirDireccion());
                parametros.put("telefono", vperPersona.getTelefono());
                parametros.put("patronalSS", vperPersona.getNroCajaSalud());
                parametros.put("ciudadLocalidad", vperPersona.getLocalidad());
                parametros.put("fax", vperPersona.getFax());
                parametros.put("nit", vperPersona.getNroIdentificacion() +"");
                parametros.put("actividadEconomica", vperPersona.getActividadDeclarada());
                parametros.put("zona", vperPersona.getDirZona());
                parametros.put("numero", vperPersona.getDirNroDireccion());
                parametros.put("correoElectronico", vperPersona.getEmail());
                parametros.put("escudoBolivia", servletContext.getRealPath("/")+"/images/escudo.jpg");
                parametros.put("logo",servletContext.getRealPath("/")+"/images/logoMIN.jpg");
                Calendar cal = Calendar.getInstance();
                cal.setTime(docDocumento.getFechaDocumento());
                parametros.put("diaFechaPresentacion", cal.get(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.MONTH, 1);
                parametros.put("mesFechaPresentacion", cal.get(Calendar.MONTH));
                parametros.put("anioFechaPresentacion", cal.get(Calendar.YEAR));
                parametros.put("nombreEmpleador", vperPersona.getRlNombre());
                parametros.put("nroDocumento", vperPersona.getRlNroIdentidad());
                parametros.put("lugarPresentacion", "Oficina Virtual");

                String nombrePdf="ROE012-".concat(Util.encriptaMD5(String.valueOf(idUsuarioEmpleador).concat(String.valueOf(idPersonaPorDocumento))))+".pdf";

                redirecionarReporte(iDocumentoService.generateReport(nombrePdf, "/reportes/roe012.jasper", parametros));
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("ERROR al generar el reporte: "+e.getMessage());
            }
        }
    }

    private static void redirecionarReporte (String rutaReporte) throws IOException {
        FacesContext facesContext=FacesContext.getCurrentInstance();
        HttpServletResponse response=(HttpServletResponse) facesContext.getExternalContext().getResponse();

        File file=new File(rutaReporte);
        BufferedInputStream input=null;
        BufferedOutputStream output=null;
        try{

            input=new BufferedInputStream(new FileInputStream(file),10240);
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader( "Content-Disposition", "filename=" + file.getName());
            response.setContentLength((int)file.length());
            output=new BufferedOutputStream(response.getOutputStream(), 10240);

            byte[] buffer=new byte[10240];
            int length;

            while((length=input.read(buffer))>0){
                output.write(buffer,0,length);
            }
            output.flush();
        }finally{
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
    public String irEdicionRoe(){
        session.setAttribute("idDocumento", docDocumento.getIdDocumento());
        return "irEdicionRoe";
    }

//
//    public String download(){
//        session.setAttribute("idDocumento", docDocumentoEntity.getIdDocumento());
//        return "irDownload";
//    }



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

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public List<PerUnidad> getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(List<PerUnidad> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public List<DocDocumento> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocDocumento> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public IPlanillaService getiPlanillaService() {
        return iPlanillaService;
    }

    public void setiPlanillaService(IPlanillaService iPlanillaService) {
        this.iPlanillaService = iPlanillaService;
    }

    public DocDocumento getDocDocumento() {
        return docDocumento;
    }

    public void setDocDocumento(DocDocumento docDocumento) {
        this.docDocumento = docDocumento;
    }

    public PerPersona getEmpleador() {
        return empleador;
    }

    public void setEmpleador(PerPersona empleador) {
        this.empleador = empleador;
    }

    public boolean isEsInterno() {
        return esInterno;
    }

    public void setEsInterno(boolean esInterno) {
        this.esInterno = esInterno;
    }

    public IMensajeAppService getiMensajeAppService() {
        return iMensajeAppService;
    }

    public void setiMensajeAppService(IMensajeAppService iMensajeAppService) {
        this.iMensajeAppService = iMensajeAppService;
    }

    public DocPlanilla getDocPlanilla() {
        return docPlanilla;
    }

    public void setDocPlanilla(DocPlanilla docPlanilla) {
        this.docPlanilla = docPlanilla;
    }

    public IDocumentoEstadoService getiDocumentoEstadoService() {
        return iDocumentoEstadoService;
    }

    public void setiDocumentoEstadoService(IDocumentoEstadoService iDocumentoEstadoService) {
        this.iDocumentoEstadoService = iDocumentoEstadoService;
    }

    public List<ParDocumentoEstado> getListaDocumentoEstado() {
        return listaDocumentoEstado;
    }

    public void setListaDocumentoEstado(List<ParDocumentoEstado> listaDocumentoEstado) {
        this.listaDocumentoEstado = listaDocumentoEstado;
    }

    public String getCodEstadoFinal() {
        return codEstadoFinal;
    }

    public void setCodEstadoFinal(String codEstadoFinal) {
        this.codEstadoFinal = codEstadoFinal;
    }

    public IVperPersonaService getiVperPersonaService() {
        return iVperPersonaService;
    }

    public void setiVperPersonaService(IVperPersonaService iVperPersonaService) {
        this.iVperPersonaService = iVperPersonaService;
    }

    public IBinarioService getiBinarioService() {
        return iBinarioService;
    }

    public void setiBinarioService(IBinarioService iBinarioService) {
        this.iBinarioService = iBinarioService;
    }

    public boolean isMostrarCambioDeEstados() {
        return mostrarCambioDeEstados;
    }

    public void setMostrarCambioDeEstados(boolean mostrarCambioDeEstados) {
        this.mostrarCambioDeEstados = mostrarCambioDeEstados;
    }

}