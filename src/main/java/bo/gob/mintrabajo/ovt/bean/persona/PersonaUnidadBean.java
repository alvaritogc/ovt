package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.Util.ServicioEnvioEmail;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bo.gob.mintrabajo.ovt.Util.Dominios.*;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.*;

/**
 * Created with IntelliJ IDEA.
 * User: aquiroz
 * Date: 10/25/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "personaUnidadBean")
@ViewScoped
public class PersonaUnidadBean implements Serializable{

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;

    @ManagedProperty(value = "#{localidadService}")
    private ILocalidadService iLocalidadService;

    @ManagedProperty(value = "#{utilService}")
    private IUtilsService iUtilsService;

    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;

    public IUnidadService getiUnidadService2() {
        return iUnidadService2;
    }

    public void setiUnidadService2(IUnidadService iUnidadService2) {
        this.iUnidadService2 = iUnidadService2;
    }

    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService2;

    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadServiceModificar;

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @ManagedProperty(value="#{dominioService}")
    private IDominioService iDominioService;

    @ManagedProperty(value="#{direccionService}")
    private IDireccionService iDireccionService;

    @ManagedProperty(value = "#{repLegalService}")
    private IRepLegalService iRepLegalService;

    @ManagedProperty(value  ="#{actividadEconomicaService}")
    private IActividadEconomicaService iActividadEconomicaService;

    @ManagedProperty(value = "#{actividadService}")
    private IActividadService iActividadService;

    @ManagedProperty(value = "#{infoLaboralService}")
    private IInfoLaboralService iInfoLaboralService;

    @ManagedProperty(value = "#{definicionService}")
    private IDefinicionService definicionService;

    private PerPersona persona=new PerPersona();
    private String idLocalidadPersona;

    private PerPersona personaRegistro;

    private String idLocalidad;
    private List<SelectItem>listaLocalidad;

    private PerUnidad unidad;
    private List<PerUnidad>listaUnidad;



    private ExternalContext externalContext= FacesContext.getCurrentInstance().getExternalContext();

    private static final Logger logger = LoggerFactory.getLogger(PersonaUnidadBean.class);

    List<SelectItem>listaTipoEmpresa;
    List<SelectItem>listaTipoSociedad;
    List<SelectItem>listaTipoIdentificacion;


    private PerDireccion direccion;
    private List<PerDireccion>listaDireccion;
    private PerDireccion direccionPrincipal;
    private List<SelectItem>listaTipoDirecciones;



    //Sirve para mostrar
    //DIRECCCION
    private String departamentoDireccinoPrincipal;
    private String tipoDireccionPrincipal;
    //UNIDAD
    private String tipoSociedadPrincipal;
    private String tipoEmpresaPrincipal;

    private PerReplegal repLegal;
    private List<PerReplegal> listaRepLegal;
    private PerReplegal repLegalPrincipal;

    private PerActividad actividad;
    private List<PerActividad> listaActividad;
    private PerActividad actividadPrincipal;

    private ParActividadEconomica actividadEconomica;
    private List<ParActividadEconomica>listaActividadEconomica;

    private List<SelectItem>listaCodActividadEconomica;

    private String codigoActividadEconomicaPrincipal;

    private Long idActividadEconomicaPrincipal;
    private ParActividadEconomica actividadEconomicaPrincipal;


    private PerUnidad unidadRegistro;

    private String titulo;
    private String tituloDlgUnidad;
    private String tituloDialog;
    private String iconoUnidad;

    private   Long idUsuario;
    static String  REGISTRO_BITACORA;

    private PerInfolaboral infolaboral;
    private PerInfolaboral infolaboralRegistro;


    public boolean isMostrarBotonROE() {
        return mostrarBotonROE;
    }

    public void setMostrarBotonROE(boolean mostrarBotonROE) {
        this.mostrarBotonROE = mostrarBotonROE;
    }

    //sw para mostrar el boton de impresion de ROE1
    private boolean mostrarBotonROE;

    public boolean isTieneROE() {
        return tieneROE;
    }

    public void setTieneROE(boolean tieneROE) {
        this.tieneROE = tieneROE;
    }

    //Sirve para mostrar boton de impresion ROE2
    private boolean tieneROE=false;


    public IDocumentoService getDocumentoService() {
        return documentoService;
    }

    public void setDocumentoService(IDocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService documentoService;

    @PostConstruct
    public void ini(){

        persona=new PerPersona();
        String idEmpleador=   (String)session.getAttribute("idEmpleador");
         idUsuario = (Long) session.getAttribute("idUsuario");
        REGISTRO_BITACORA=iUsuarioService.findById(idUsuario).getUsuario();
        persona=iPersonaService.findById(idEmpleador);
        titulo=persona.getNombreRazonSocial().toUpperCase()+" ";
        titulo=persona.getApellidoPaterno()==null ?titulo+"":titulo+persona.getApellidoPaterno().toUpperCase()+" ";
        titulo=persona.getApellidoMaterno()==null ?titulo+"":titulo+persona.getApellidoMaterno().toUpperCase()+" ";

        actividadEconomica=new ParActividadEconomica();
        repLegal=new PerReplegal();
        unidadRegistro=new PerUnidad();
        cargar();

        mostrarBotonROE=generarReporteRoe();
        //tieneROE=yaTieneROE();
        System.out.println("=====>>>>>>>>>>> mostrarBotonROE mostrarBotonROE "+mostrarBotonROE);
        System.out.println("=====>>>>>>>>>>> TIENE ROE tieneROE "+tieneROE);

    }

    public boolean generarReporteRoe(){


        //validar datos de persona
        if(persona.getIdPersona()!=null){
            if(persona.getNombreRazonSocial()==null){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                  return false;
            }

            if(persona.getApellidoPaterno()==null){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

            if(persona.getApellidoMaterno()==null){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }
            if(persona.getNroIdentificacion()==null){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

            if(persona.getCodLocalidad()==null){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }
            if(persona.getTipoIdentificacion()==null){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }
        }

        //validar datos de unidad
        if(unidad.getPerUnidadPK()!=null){
            if(unidad.getNombreComercial()==null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

            if(unidad.getNombreComercial()==null){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

            if(unidad.getTipoEmpresa()==null)  {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

            if(unidad.getTipoSociedad()==null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

            if(unidad.getFechaNacimiento()==null)  {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

            if(unidad.getNroFundaempresa()==null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

            if(unidad.getNroAfp()==null)  {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

            if(unidad.getNombreComercial()==null)  {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

        }

        //vallidad actividad declarada
        if(actividadEconomicaPrincipal.getIdActividadEconomica()==null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

            ini();
            return false;
        }



                 System.out.print("===>> REP LEGAL "+repLegalPrincipal);
        System.out.print("===>> direccionPrincipal"+direccionPrincipal);
        System.out.print("===>> infolaboralL "+infolaboral);

        if(repLegalPrincipal!=null){
            //validar representante legal
            if(repLegalPrincipal.getIdReplegal()==null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                ini();
                return false;
            }

        }

         if(direccionPrincipal!=null){
             //validar direccion
             if(direccionPrincipal.getIdDireccion().equals(null)) {
                 FacesContext.getCurrentInstance().addMessage(null,
                         new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                 ini();
                 return false;
             }

         }

           if(infolaboral!=null){
               //validar informacion laboral
               if(infolaboral.getIdInfolaboral()==null)  {
                   FacesContext.getCurrentInstance().addMessage(null,
                           new FacesMessage(FacesMessage.SEVERITY_ERROR,"Exito","No se puede generar el documento ROE, por que falta registrar datos."));

                   ini();
                   return false;
               }

           }

           tieneROE=true;
        return true;
    }

    public  void cargar(){
        cargarLocalidad();
        cargarCodigoActividadEconomica();
        listaTipoEmpresa=cargarListas(listaTipoEmpresa,DOM_TIPOS_EMPRESA);
        listaTipoSociedad=cargarListas(listaTipoEmpresa,DOM_TIPOS_SOCIEDAD);
        listaTipoIdentificacion=cargarListas(listaTipoEmpresa,DOM_TIPOS_IDENTIFICACION);
        listaTipoDirecciones=cargarListas(listaTipoDirecciones,DOM_TIPO_DIRECCION);
        cargarUnidad();
        cargarActividadDeclarda();
        cargarDireccion();
        cargarRepLegal();
        cargarInfoLaboral();
    }

    /*
     *******************************************
     *          METODOS UNIDAD
     ******************************************
     */
    /*
     *  Setea la variable unidad, que representa la unidad principal.
     *  Setea la variable  listaUnidad, el cual se muestra en el componente <p:datagrid>
     */
    public void cargarUnidad(){
        //representa la unidad principal
        unidad=new PerUnidad();
        listaUnidad=new ArrayList<PerUnidad>();
        //Obtiene en una lista auxiliar las unidades de la persona
        List<PerUnidad>listaUnidadAux=iUnidadService.buscarPorPersona(persona.getIdPersona());
        //setea la unidad principal
        unidad=listaUnidadAux.get(listaUnidadAux.size()-1);
        try{
            tipoSociedadPrincipal=iDominioService.obtenerDominioPorNombreYValor(DOM_TIPOS_SOCIEDAD,unidad.getTipoSociedad()).getDescripcion();
            tipoEmpresaPrincipal=iDominioService.obtenerDominioPorNombreYValor(DOM_TIPOS_EMPRESA,unidad.getTipoEmpresa()).getDescripcion();
        }catch (Exception ex){
              logger.error(ex.getMessage());
        }

        // carga la varible listaUnidad desde el primer registro hasta el penultimo
        // se carga asi, por que en el ultimo registro esta la unidadPrincipal
       listaUnidad=listaUnidadAux.subList(0,listaUnidadAux.size()-1);
        for(PerUnidad u:listaUnidad){
            u.setTipoEmpresaAuxiliar(iDominioService.obtenerDominioPorNombreYValor(DOM_TIPOS_EMPRESA,u.getTipoEmpresa()).getDescripcion());
            u.setTipoSociedadAuxiliar(iDominioService.obtenerDominioPorNombreYValor(DOM_TIPOS_SOCIEDAD,u.getTipoSociedad()).getDescripcion());
        }
    }

    public void nuevo(){
        unidadRegistro=new PerUnidad();
        direccion=new PerDireccion();
        repLegal=new PerReplegal();
        actividadPrincipal=new PerActividad();
        infolaboralRegistro=new PerInfolaboral();
        //infolaboralRegistro.setNroTotalTrabajadores(10);


    }

    //PRIMERA VEZ
    public String generarCertificadoROE(){
        DocDefinicionPK docDefinicionPK=new DocDefinicionPK();
        docDefinicionPK.setCodDocumento("ROE010");
        docDefinicionPK.setVersion((short)1);
        session.setAttribute("docDefinicionPK",docDefinicionPK);
        return "irImpresionRoe";
    }

    //
    public boolean yaTieneROE(){
        List<DocDocumento> lista = documentoService.listarRoe013(unidad.getPerUnidadPK().getIdPersona(), unidad.getPerUnidadPK().getIdUnidad());
        System.out.println("=====>>>>>>>>>>> TIENE ROE ");
        System.out.println("=====>>>>>>>>>>> TIENE ROE lista: "+lista.size());
        if(lista!=null){
            if(!lista.isEmpty()){
                if(lista.size()>=0){
                    System.out.println("=====>>>>>>>>>>> TIENE ROE "+lista.get(0));
                    System.out.println("=====>>>>>>>>>>> TIENE ROE "+lista.get(0).getIdDocumento());
                    return true;
                }else{
                    return false;
                }
            } else{
                return false;
            }

        }else{
            return false;
        }
    }

    public void generarCertificadoROE2(){
        try{
            documentoService.guardarRoeGenerico(unidad.getPerUnidadPK(),REGISTRO_BITACORA);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se genero un nuevo documneto ROE."));

            ini();
            return ;
        } catch (Exception ex){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","No se pudo generar documento: "+ex.getMessage()));
            ini();
            return ;
        }


    }

    /*
    * Este metodo se encarga de realizar un INSERT o UPDATE
    * de un registro en la tabla  PER_UNIDAD
    *
     */
    public void procesarUnidad(){

        if(personaRegistro!=null){
            //Si es la unidad principal no realizar la validacion
            //if(unidad.getPerUnidadPK().getIdUnidad()==0){
                //Validaciones para persona
                if(personaRegistro.getNombreRazonSocial()==null || personaRegistro.getNombreRazonSocial().trim().equals("")){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nombre o Razon social es obligatorio."));
                    ini();
                    return ;
                }
                if(personaRegistro.getTipoIdentificacion()==null || personaRegistro.getTipoIdentificacion().trim().equals("")){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Tipo de identificacion es obligatorio."));
                    ini();
                    return ;
                }

                if(idLocalidadPersona==null && idLocalidadPersona.equals("")){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Departamento es obligatorio."));
                    ini();
                    return ;
                }

                if(personaRegistro.getNroIdentificacion()==null || personaRegistro.getNroIdentificacion().trim().equals("")){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nro. de identificacion es obligatorio."));
                    ini();
                    return ;
                }else{

                    if(!esNumero(personaRegistro.getNroIdentificacion())){
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","El valor del campo Nro. de identificacion debe ser numerico."));
                        ini();
                        return ;
                    }
                       //Si es distinto, entonces se modifico el nro de identificacion
                    if(!personaRegistro.getNroIdentificacion().equals(persona.getNroIdentificacion())){
                        //validar que nro de identificacion sea unico
                        if(iPersonaService.findByNroIdentificacion(persona.getNroIdentificacion())!=null){
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL valor del campo Nro. de identificacion ya existe. Modifique este valor"));
                            ini();
                            return ;
                        }
                    }
                }
            System.out.println("===>> MODIFICANDO PERSONA "+personaRegistro);
            //Si todo esta bien, entonces
            personaRegistro.setRegistroBitacora(REGISTRO_BITACORA);
            persona=personaRegistro;
            //}
        }


        //Validar unidad
        if (unidadRegistro.getNombreComercial()==null || unidadRegistro.getNombreComercial().trim().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nombre comercial es obligatorio."));
            ini();
            return ;
        }

        if (unidadRegistro.getNombreComercial()==null || unidadRegistro.getNombreComercial().trim().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nombre comercial es obligatorio."));
            ini();
            return ;
        }

        if(unidadRegistro.getTipoEmpresa()==null || unidadRegistro.getTipoEmpresa().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Tipo de empresa es obligatorio."));
            ini();
            return ;
        }

        if(unidadRegistro.getTipoSociedad()==null || unidadRegistro.getTipoSociedad().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Tipo de sociedad es obligatorio."));
            ini();
            return ;
        }

        if (unidadRegistro.getObservaciones()==null || unidadRegistro.getObservaciones().trim().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Observaciones es obligatorio."));
            ini();
            return ;
        }

        if (unidadRegistro.getActividadDeclarada()==null || unidadRegistro.getActividadDeclarada().trim().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Actividad declarada es obligatorio."));
            ini();
            return ;
        }



        //final String  REGISTRO_BITACORA="ROE";
        unidadRegistro.setRegistroBitacora(REGISTRO_BITACORA);

        unidadRegistro=iUnidadServiceModificar.save(unidadRegistro,persona);
        //ini();
        if(unidadRegistro==null){
            RequestContext.getCurrentInstance().execute("dlgUnidad.show()");
        }else {
            RequestContext.getCurrentInstance().execute("dlgUnidad.hide()");
        }
    }



    /*
     *******************************************
     *          METODOS DIRECCION
     ******************************************
     */
    public void procesarDireccion(){

        if(direccion.getTipoDireccion()==null || direccion.getTipoDireccion().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Tipo de direccion es obligatorio."));
            ini();
            return ;
        }

        if(direccion.getDireccion()==null || direccion.getDireccion().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Direccion es obligatorio."));
            ini();
            return ;
        }

        if(direccion.getZonaUrbanizacion()==null || direccion.getZonaUrbanizacion().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Zona es obligatorio."));
            ini();
            return ;
        }

        if(idLocalidad==null || idLocalidad.trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Localidad es obligatorio."));
            ini();
            return ;
        }

        direccion.setPerUnidad(unidadRegistro);
        direccion.setCodLocalidad(iLocalidadService.findById(idLocalidad));
        //Cambiar por el usuario de la session
        direccion.setRegistroBitacora(REGISTRO_BITACORA);
            iDireccionService.save(direccion);
        ini();
        RequestContext.getCurrentInstance().execute("dlgDireccion.hide()");
    }

    public void cargarDireccion(){
        // Cargando Direccion
        listaDireccion=new ArrayList<PerDireccion>();
        listaDireccion= iDireccionService.obtenerPorIdPersona(unidad.getPerPersona().getIdPersona());
        if (!listaDireccion.isEmpty()){
            //Significa que solo se registro la unidad principal, entonces solo puede haber una direccion
            // que seria para la unidad principal.
            if(listaUnidad.size()==0){
                direccionPrincipal= listaDireccion.get(0);
                tipoDireccionPrincipal=iDominioService.obtenerDominioPorNombreYValor(DOM_TIPO_DIRECCION,listaDireccion.get(0).getTipoDireccion()).getDescripcion();
/*                for(int i=listaDireccion.size()-1;i>=0;i--){
                    if(listaDireccion.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad()==unidad.getPerUnidadPK().getIdUnidad()){
                        direccionPrincipal= listaDireccion.get(i);
                        tipoDireccionPrincipal=iDominioService.obtenerDominioPorNombreYValor(DOM_TIPO_DIRECCION,listaDireccion.get(i).getTipoDireccion()).getDescripcion();
                    }
                }*/
            }else{
                for (int i=listaDireccion.size()-1;i>=0;i--){
                    for (int j=listaUnidad.size()-1;j>=0;j--){
                        if(listaDireccion.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad()==unidad.getPerUnidadPK().getIdUnidad()){
                            direccionPrincipal= listaDireccion.get(i);
                            tipoDireccionPrincipal=iDominioService.obtenerDominioPorNombreYValor(DOM_TIPO_DIRECCION,listaDireccion.get(i).getTipoDireccion()).getDescripcion();
                            break;
                        } else {
                            if(listaDireccion.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad()==listaUnidad.get(j).getPerUnidadPK().getIdUnidad()){
                                listaDireccion.get(i).setTipoDireccionAuxiliar(iDominioService.obtenerDominioPorNombreYValor(DOM_TIPO_DIRECCION,listaDireccion.get(i).getTipoDireccion()).getDescripcion());
                                listaUnidad.get(j).setDireccion(listaDireccion.get(i));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    public void cargarLocalidad(){
        try {
            List<ParLocalidad>localidades=new ArrayList<ParLocalidad>();
            listaLocalidad=new ArrayList<SelectItem>();
            localidades=iLocalidadService.getAllLocalidades();
            for (ParLocalidad l:localidades){
                if(!l.getDescripcion().equalsIgnoreCase("BOLIVIA"))
                    listaLocalidad.add(new SelectItem(l.getCodLocalidad(),l.getDescripcion()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    /*
     *******************************************
     *          METODOS REPLEGAL
     ******************************************
     */
    public void procesarRepLegal(){

        if(repLegal.getNombre()==null || repLegal.getNombre().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nombre es obligatorio."));
            ini();
            return ;
        }

        if(repLegal.getApellidoPaterno()==null || repLegal.getApellidoPaterno().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Ap. paterno es obligatorio."));
            ini();
            return ;
        }

        if(repLegal.getApellidoMaterno()==null || repLegal.getApellidoMaterno().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Ap. Materno es obligatorio."));
            ini();
            return ;
        }

        if(repLegal.getTipoIdentificacion()==null || repLegal.getTipoIdentificacion().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Tipo de identificacion es obligatorio."));
            ini();
            return ;
        }

        if(repLegal.getNroIndentificacion()==null || repLegal.getNroIndentificacion().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nro. de identificacion es obligatorio."));
            ini();
            return ;
        }else{
            if(!esNumero(repLegal.getNroIndentificacion())){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","El valor del campo Nro. de identificacion debe ser numerico."));
                ini();
                return ;
            }
        }

        if(repLegal.getTipoProcedencia()==null || repLegal.getTipoProcedencia().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Departamento es obligatorio."));
            ini();
            return ;
        }

        repLegal.setPerUnidad(unidadRegistro);
        //Cambiar por el usuario de la session
        //repLegal.setRegistroBitacora(persona.getNombreRazonSocial());
        repLegal.setRegistroBitacora(REGISTRO_BITACORA);
        iRepLegalService.save(repLegal);
        ini();
        RequestContext.getCurrentInstance().execute("dlgRepLegal.hide()");
    }

    public void cargarRepLegal(){
         repLegalPrincipal=new PerReplegal();
        listaRepLegal=new ArrayList<PerReplegal>();
        listaRepLegal=iRepLegalService.obtenerPorIdPersona(unidad.getPerPersona().getIdPersona());
        if(!listaRepLegal.isEmpty()){
            //Solo se registro a la unidad principal, entonces solo puede existir un
            //representante legal para esa unidad.
            if(listaUnidad.size()==0){
                repLegalPrincipal= listaRepLegal.get(0);
                departamentoDireccinoPrincipal=iLocalidadService.findById(repLegalPrincipal.getTipoProcedencia()).getDescripcion();
/*                for(int i=listaRepLegal.size()-1;i>=0;i--){
                    if(listaRepLegal.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad()==unidad.getPerUnidadPK().getIdUnidad()){
                        repLegalPrincipal= listaRepLegal.get(i);
                        departamentoDireccinoPrincipal=iLocalidadService.findById(repLegalPrincipal.getTipoProcedencia()).getDescripcion();
                    }
                }*/
            }else{
                for (int i=listaRepLegal.size()-1;i>=0;i--){
                    for (int j=listaUnidad.size()-1;j>=0;j--){
                        if(listaRepLegal.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad()==unidad.getPerUnidadPK().getIdUnidad()){
                            repLegalPrincipal= listaRepLegal.get(i);
                            departamentoDireccinoPrincipal=iLocalidadService.findById(repLegalPrincipal.getTipoProcedencia()).getDescripcion();
                            break;
                        }else{
                            if(listaRepLegal.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad()==listaUnidad.get(j).getPerUnidadPK().getIdUnidad()){
                                listaRepLegal.get(i).setDepartamento(iLocalidadService.findById(listaRepLegal.get(i).getTipoProcedencia()).getDescripcion());
                                listaUnidad.get(j).setRepLegal(listaRepLegal.get(i));

                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    //Valida si el parametro es numerico
    private static boolean esNumero(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    /*
      *******************************************
      *          METODOS ACTIVIDAD DECLARADA
      ******************************************
      */

    /*
    * Este metodo se encarga de realizar un INSERT o UPDATE
    * de un registro en las tablas: PAR_ACTIVIDAD_ECONOMICA y PER_ACTIVIDAD
     */
    public void procesarActividadEconomica(){


        actividadEconomica.setCodImpuestos("NADA");
        actividadEconomica.setDescricpionImpuestos("NADA");
        actividadEconomica.setDescripcion("NADA");

        //Cambiar por el usuario de la session
       // String registroPersona=  persona.getNombreRazonSocial();
        String registroPersona=REGISTRO_BITACORA;
       ParActividadEconomica actvEcon=iActividadEconomicaService.findByIdActividadEconomica(idActividadEconomicaPrincipal);
        actividadEconomica.setCodActividadEconomica(actvEcon.getCodActividadEconomica());
        actividadEconomica.setIdActividadEconomica2(actvEcon);
        iActividadEconomicaService.save(actividadEconomica,unidad,registroPersona);

        ini();
        //Cerrar dialog
        RequestContext.getCurrentInstance().execute("dlgActividadDeclarada.hide()");

    }

    public void cargarActividadDeclarda(){
        logger.info("===>> Ingresando a cargarActividadDeclarda()");
        logger.info("===>> UNIDAD "+unidad);
        //Cargando Actividad Economica
        listaActividad=iActividadService.findByPerUnidad(unidad);
        actividadEconomicaPrincipal=new ParActividadEconomica();
        if(listaActividad!=null ){
            if(!listaActividad.isEmpty()){
                //se obtiene el primer registro, por que una persona solo tiene
                // una actividad declarada
                actividadEconomicaPrincipal=listaActividad.get(0).getIdActividadEconomica();
                codigoActividadEconomicaPrincipal= iActividadEconomicaService.findByIdActividadEconomica(actividadEconomicaPrincipal.getIdActividadEconomica2().getIdActividadEconomica()).getDescripcion();
              //  iActividadEconomicaService.obtenerActividadEconomicaParaRegistro()
            }
        }
    }

    public void cargarCodigoActividadEconomica(){
        try {
            List<ParActividadEconomica>listaCodActEconomica=new ArrayList<ParActividadEconomica>();
            listaCodActividadEconomica=new ArrayList<SelectItem>();
            List<BigDecimal>lista=new ArrayList<BigDecimal>();
            lista  =iActividadEconomicaService.obtenerActividadEconomicaParaRegistro();
            //listaCodActEconomica
            for (BigDecimal actv2:lista){

               ParActividadEconomica actv= iActividadEconomicaService.findByIdActividadEconomica(actv2.longValue());
                listaCodActividadEconomica.add(new SelectItem(actv.getIdActividadEconomica(),actv.getCodActividadEconomica()+".- "+actv.getDescripcion()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    /*
    *******************************************
    *          METODOS INFORMACION LABORAL
    ******************************************
    */

    /*
 * Este metodo se encarga de realizar un INSERT o UPDATE
 * de un registro en las tablas: PAR_ACTIVIDAD_ECONOMICA y PER_ACTIVIDAD
  */
    public void procesarInfoLaboral(){


        infolaboralRegistro.setRegistroBitacora(REGISTRO_BITACORA);
        infolaboralRegistro.setPerUnidad(unidad);
         iInfoLaboralService.save(infolaboralRegistro);
        ini();
        //Cerrar dialog
        RequestContext.getCurrentInstance().execute("dlgInfoLaboral.hide()");

    }

    public void cargarInfoLaboral(){
        infolaboral=new PerInfolaboral();

        logger.info("===>> Ingresando a cargarInfoLaboral()");
        logger.info("===>> UNIDAD "+unidad);
        List<PerInfolaboral>listaInfoLaboral=new ArrayList<PerInfolaboral>();
        listaInfoLaboral=iInfoLaboralService.findByPerUnidad(unidad);
        if(listaInfoLaboral!=null){
           if(!listaInfoLaboral.isEmpty()){
               //se obtiene el primer registro, por que una persona solo tiene
               // una actividad declarada
               infolaboral=listaInfoLaboral.get(0);
               //codigoActividadEconomicaPrincipal= iActividadEconomicaService.findByIdActividadEconomica(actividadEconomicaPrincipal.getIdActividadEconomica2().getIdActividadEconomica()).getDescripcion();
           }
        }


    }

    public  void sumar(){
        //if(infolaboral.getNroHombres()!=null)
        infolaboral.setNroTotalTrabajadores(infolaboral.getNroHombres()+infolaboral.getNroMujeres());
    }

    /*
     **
     * Este metodo se utliza para cargar las listas
     * del tipo SelectItem(para el componente <p:selectOneMenu>).
     *@Param lista .- Es la variable que se utiliza para llenar los valores de dominio
     *@Param dominio .- Representa un dominio de la tabla PAR_DOMINIO. Estos valores
     *                  estan parametrizados en la clase Dominios.java
     */
    public List<SelectItem> cargarListas(List<SelectItem>lista,String dominio){
        lista=new ArrayList<SelectItem>();
        try{
            List<ParDominio>valoresDominio=iDominioService.obtenerItemsDominio(dominio);
            for(ParDominio d:valoresDominio){
                lista.add(new SelectItem(d.getParDominioPK().getValor(),d.getDescripcion()));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return lista;
    }


     /*
      ******************************************
      *
      *             GETTER Y SETTER
      * ****************************************
     */

    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public List<SelectItem> getListaTipoEmpresa() {
        return listaTipoEmpresa;
    }

    public void setListaTipoEmpresa(List<SelectItem> listaTipoEmpresa) {
        this.listaTipoEmpresa = listaTipoEmpresa;
    }

    public List<SelectItem> getListaTipoSociedad() {
        return listaTipoSociedad;
    }

    public void setListaTipoSociedad(List<SelectItem> listaTipoSociedad) {
        this.listaTipoSociedad = listaTipoSociedad;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }



    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public IUtilsService getiUtilsService() {
        return iUtilsService;
    }

    public void setiUtilsService(IUtilsService iUtilsService) {
        this.iUtilsService = iUtilsService;
    }

    public String getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(String idLocalidad) {
        this.idLocalidad = idLocalidad;
    }


    public ILocalidadService getiLocalidadService() {
        return iLocalidadService;
    }

    public void setiLocalidadService(ILocalidadService iLocalidadService) {
        this.iLocalidadService = iLocalidadService;
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



    public PerUnidad getUnidad() {
        return unidad;
    }

    public void setUnidad(PerUnidad unidad) {
        this.unidad = unidad;
    }

    public List<PerUnidad> getListaUnidad() {
        return listaUnidad;
    }

    public void setListaUnidad(List<PerUnidad> listaUnidad) {
        this.listaUnidad = listaUnidad;
    }

    public PerDireccion getDireccion() {


        return direccion;
    }

    public void setDireccion(PerDireccion direccion) {
        idLocalidad=iLocalidadService.findById(direccion.getCodLocalidad().getCodLocalidad()).getCodLocalidad();
        this.direccion = direccion;
    }

    public List<PerDireccion> getListaDireccion() {
        return listaDireccion;
    }

    public void setListaDireccion(List<PerDireccion> listaDireccion) {
        this.listaDireccion = listaDireccion;
    }

    public PerReplegal getRepLegal() {
        return repLegal;
    }

    public void setRepLegal(PerReplegal repLegal) {
        this.repLegal = repLegal;
    }

    public List<PerReplegal> getListaRepLegal() {
        return listaRepLegal;
    }

    public void setListaRepLegal(List<PerReplegal> listaRepLegal) {
        this.listaRepLegal = listaRepLegal;
    }


    public PerActividad getActividad() {
        return actividad;
    }

    public void setActividad(PerActividad actividad) {
        this.actividad = actividad;
    }


    public List<PerActividad> getListaActividad() {
        return listaActividad;
    }

    public void setListaActividad(List<PerActividad> listaActividad) {
        this.listaActividad = listaActividad;
    }

    public ParActividadEconomica getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(ParActividadEconomica actividadEconomica) {
       idActividadEconomicaPrincipal= actividadEconomica.getIdActividadEconomica2().getIdActividadEconomica();
        this.actividadEconomica = actividadEconomica;
    }


    public List<ParActividadEconomica> getListaActividadEconomica() {
        return listaActividadEconomica;
    }

    public void setListaActividadEconomica(List<ParActividadEconomica> listaActividadEconomica) {
        this.listaActividadEconomica = listaActividadEconomica;
    }

    public PerUnidad getUnidadRegistro() {
        return unidadRegistro;
    }

    public void setUnidadRegistro(PerUnidad unidadRegistro) {
        this.unidadRegistro = unidadRegistro;
    }


    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTituloDlgUnidad() {
        return tituloDlgUnidad;
    }

    public void setTituloDlgUnidad(String tituloDlgUnidad) {
        this.tituloDlgUnidad = tituloDlgUnidad;
    }


    public String getTituloDialog() {
        return tituloDialog;
    }

    public void setTituloDialog(String tituloDialog) {
        this.tituloDialog = tituloDialog;
    }



    public String getIconoUnidad() {
        return iconoUnidad;
    }

    public void setIconoUnidad(String iconoUnidad) {
        this.iconoUnidad = iconoUnidad;
    }

    public IUnidadService getiUnidadServiceModificar() {
        return iUnidadServiceModificar;
    }

    public void setiUnidadServiceModificar(IUnidadService iUnidadServiceModificar) {
        this.iUnidadServiceModificar = iUnidadServiceModificar;
    }

    public IDireccionService getiDireccionService() {
        return iDireccionService;
    }

    public void setiDireccionService(IDireccionService iDireccionService) {
        this.iDireccionService = iDireccionService;
    }


    public List<SelectItem> getListaLocalidad() {
        return listaLocalidad;
    }

    public void setListaLocalidad(List<SelectItem> listaLocalidad) {
        this.listaLocalidad = listaLocalidad;
    }

    public PerDireccion getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(PerDireccion direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    public IRepLegalService getiRepLegalService() {
        return iRepLegalService;
    }

    public void setiRepLegalService(IRepLegalService iRepLegalService) {
        this.iRepLegalService = iRepLegalService;
    }

    public PerReplegal getRepLegalPrincipal() {
        return repLegalPrincipal;
    }

    public void setRepLegalPrincipal(PerReplegal repLegalPrincipal) {
        this.repLegalPrincipal = repLegalPrincipal;
    }

    public List<SelectItem> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    public IActividadEconomicaService getiActividadEconomicaService() {
        return iActividadEconomicaService;
    }

    public void setiActividadEconomicaService(IActividadEconomicaService iActividadEconomicaService) {
        this.iActividadEconomicaService = iActividadEconomicaService;
    }

    public IActividadService getiActividadService() {
        return iActividadService;
    }

    public void setiActividadService(IActividadService iActividadService) {
        this.iActividadService = iActividadService;
    }


    public ParActividadEconomica getActividadEconomicaPrincipal() {
        return actividadEconomicaPrincipal;
    }

    public void setActividadEconomicaPrincipal(ParActividadEconomica actividadEconomicaPrincipal) {

        this.actividadEconomicaPrincipal = actividadEconomicaPrincipal;
    }

    public List<SelectItem> getListaTipoDirecciones() {
        return listaTipoDirecciones;
    }

    public void setListaTipoDirecciones(List<SelectItem> listaTipoDirecciones) {
        this.listaTipoDirecciones = listaTipoDirecciones;
    }

    public String getDepartamentoDireccinoPrincipal() {
        return departamentoDireccinoPrincipal;
    }

    public void setDepartamentoDireccinoPrincipal(String departamentoDireccinoPrincipal) {
        this.departamentoDireccinoPrincipal = departamentoDireccinoPrincipal;
    }

    public String getTipoDireccionPrincipal() {
        return tipoDireccionPrincipal;
    }

    public void setTipoDireccionPrincipal(String tipoDireccionPrincipal) {
        this.tipoDireccionPrincipal = tipoDireccionPrincipal;
    }

    public String getTipoSociedadPrincipal() {
        return tipoSociedadPrincipal;
    }

    public void setTipoSociedadPrincipal(String tipoSociedadPrincipal) {
        this.tipoSociedadPrincipal = tipoSociedadPrincipal;
    }

    public String getTipoEmpresaPrincipal() {
        return tipoEmpresaPrincipal;
    }

    public void setTipoEmpresaPrincipal(String tipoEmpresaPrincipal) {
        this.tipoEmpresaPrincipal = tipoEmpresaPrincipal;
    }

    public List<SelectItem> getListaCodActividadEconomica() {
        return listaCodActividadEconomica;
    }

    public void setListaCodActividadEconomica(List<SelectItem> listaCodActividadEconomica) {
        this.listaCodActividadEconomica = listaCodActividadEconomica;
    }

    public String getCodigoActividadEconomicaPrincipal() {
        return codigoActividadEconomicaPrincipal;
    }

    public void setCodigoActividadEconomicaPrincipal(String codigoActividadEconomicaPrincipal) {
        this.codigoActividadEconomicaPrincipal = codigoActividadEconomicaPrincipal;
    }

    public PerPersona getPersonaRegistro() {
        return personaRegistro;
    }

    public void setPersonaRegistro(PerPersona personaRegistro) {
        idLocalidadPersona=iLocalidadService.findById(personaRegistro.getCodLocalidad().getCodLocalidad()).getCodLocalidad();
        this.personaRegistro = personaRegistro;
    }


    public String getIdLocalidadPersona() {
        return idLocalidadPersona;
    }

    public void setIdLocalidadPersona(String idLocalidadPersona) {
        this.idLocalidadPersona = idLocalidadPersona;
    }

    public ExternalContext getExternalContext() {
        return externalContext;
    }

    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public Long getIdActividadEconomicaPrincipal() {
        return idActividadEconomicaPrincipal;
    }

    public void setIdActividadEconomicaPrincipal(Long idActividadEconomicaPrincipal) {
        this.idActividadEconomicaPrincipal = idActividadEconomicaPrincipal;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public PerInfolaboral getInfolaboral() {
        return infolaboral;
    }

    public void setInfolaboral(PerInfolaboral infolaboral) {
        this.infolaboral = infolaboral;
    }

    public PerInfolaboral getInfolaboralRegistro() {
        return infolaboralRegistro;
    }

    public void setInfolaboralRegistro(PerInfolaboral infolaboralRegistro) {
        this.infolaboralRegistro = infolaboralRegistro;
    }

    public IInfoLaboralService getiInfoLaboralService() {
        return iInfoLaboralService;
    }

    public void setiInfoLaboralService(IInfoLaboralService iInfoLaboralService) {
        this.iInfoLaboralService = iInfoLaboralService;
    }

    public IDefinicionService getDefinicionService() {
        return definicionService;
    }

    public void setDefinicionService(IDefinicionService definicionService) {
        this.definicionService = definicionService;
    }
}
