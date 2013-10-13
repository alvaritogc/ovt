package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPlanillaService;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.PlanillaRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.ejb.TransactionAttribute;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("planillaService")
@TransactionAttribute
public class PlanillaService implements IPlanillaService {
    private final PlanillaRepository planillaRepository;
    private HashMap<String,Object> map = new HashMap<String,Object>();

    @Inject
    public PlanillaService(PlanillaRepository planillaRepository) {
        this.planillaRepository = planillaRepository;
    }

    @Override
    public void guardar(DocPlanillaEntity objeto){
        objeto.setIdPlanilla(planillaRepository.findAll().size() + 1);
        planillaRepository.save(objeto);
    }

    public void generaReporte(DocPlanillaEntity docPlanillaEntity, PerPersonaEntity persona , DocDocumentoEntity docDocumentoEntity, PerUnidadEntity perUnidadEntity, VperPersonaEntity vperPersonaEntity){
        String parametro ="";
        map.clear();

        map.put("nroOrden", docDocumentoEntity.getNumeroDocumento());
        map.put("rectificatoria", " ");
        map.put("nroRectificatoria", " ");
        map.put("totalNacional", "X");
        map.put("oficinaCentral", perUnidadEntity.getNombreComercial());
        map.put("mesPresentacion", docPlanillaEntity.getPeriodo());


        map.put("empleadorMTEPS", perUnidadEntity.getNroReferencial());
        map.put("razonSocial", persona.getNombreRazonSocial());

        parametro= vperPersonaEntity.getDirDepartamento();
        if(parametro==null)
            parametro=" ";
        map.put("departamento", parametro);

        parametro= vperPersonaEntity.getDirDireccion();
        if(parametro==null)
            parametro=" ";
        map.put("direccion", parametro);

        parametro= vperPersonaEntity.getTelefono();
        if(parametro==null)
            parametro=" ";
        map.put("telefono", parametro);

        map.put("patronalSS", perUnidadEntity.getNroCajaSalud());

        parametro= vperPersonaEntity.getLocalidad();
        if(parametro==null)
            parametro=" ";
        map.put("ciudadLocalidad", parametro);

        parametro= vperPersonaEntity.getFax();
        if(parametro==null)
            parametro=" ";
        map.put("fax", parametro);

        parametro= vperPersonaEntity.getNroIdentificacion() +"";
        if(parametro==null)
            parametro=" ";
        map.put("nit", parametro);

        parametro= vperPersonaEntity.getActividadDeclarada();
        if(parametro==null)
            parametro=" ";
        map.put("actividadEconomica", parametro);

        parametro= vperPersonaEntity.getDirZona();
        if(parametro==null)
            parametro=" ";
        map.put("zona", parametro);

        parametro= vperPersonaEntity.getDirNroDireccion();
        if(parametro==null)
            parametro=" ";
        map.put("numero", parametro);

        parametro= vperPersonaEntity.getEmail();
        if(parametro==null)
            parametro=" ";
        map.put("correoElectronico", parametro);

        map.put("nroAsegurados", docPlanillaEntity.getNroAsegCaja());
        map.put("montoAportadoAsegurados",docPlanillaEntity.getMontoAsegCaja());
        map.put("gestorSalud",docPlanillaEntity.getIdEntidadSalud()); //----------------
        map.put("nroAfiliados",docPlanillaEntity.getNroAsegAfp());
        map.put("montoAportadoAfiliados",docPlanillaEntity.getMontoAsegAfp());
        map.put("haberBasico",docPlanillaEntity.getHaberBasico());
        map.put("bonoAntiguedad",docPlanillaEntity.getBonoAntiguedad());
        map.put("bonoProduccion",docPlanillaEntity.getBonoProduccion());
        map.put("subsidioFrontera",docPlanillaEntity.getSubsidioFrontera());
        map.put("laborExtraordinaria",docPlanillaEntity.getLaborExtra());
        map.put("otrosBono",docPlanillaEntity.getOtrosBonos());
        map.put("aporteAFP",docPlanillaEntity.getAporteAfp());
        map.put("rcIVA",docPlanillaEntity.getRciva());
        map.put("otrosDescuentos",docPlanillaEntity.getOtrosDescuentos());
        map.put("totalMujeres",docPlanillaEntity.getNroM());
        map.put("totalVarones",docPlanillaEntity.getNroH());
        map.put("mujeresJubiladas",docPlanillaEntity.getNroJubiladosM());
        map.put("varonesJubilados",docPlanillaEntity.getNroJubiladosH());
        map.put("mujeresExtranjeras",docPlanillaEntity.getNroExtranjerosM());
        map.put("varonesExtranjeros",docPlanillaEntity.getNroExtranjerosH());
        map.put("mujeresDiscapacidad",docPlanillaEntity.getNroDiscapacidadM());
        map.put("varonesDiscapacidad",docPlanillaEntity.getNroDiscapacidadH());
        map.put("mujeresContratadas",docPlanillaEntity.getNroContratadosM());
        map.put("varonesContratados",docPlanillaEntity.getNroContratadosH());
        map.put("mujeresRetiradas",docPlanillaEntity.getNroRetiradosM());
        map.put("varonesRetirados",docPlanillaEntity.getNroRetiradosH());
        map.put("totalAccidentes",docPlanillaEntity.getNroAccidentes());
        map.put("accidentesMuerte",docPlanillaEntity.getNroMuertes());
        map.put("enfermedadesTrabajos",docPlanillaEntity.getNroEnfermedades());
        map.put("email",docPlanillaEntity.getIdEntidadBanco()); //----------------;

        Calendar cal = Calendar.getInstance();
        cal.setTime(docPlanillaEntity.getFechaOperacion());

        map.put("diaDeposito",cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.MONTH, 1);
        map.put("mesDeposito",cal.get(Calendar.MONTH));
        map.put("anioDeposito",cal.get(Calendar.YEAR));

        cal = Calendar.getInstance();
        cal.setTime(docDocumentoEntity.getFechaDocumento());

        map.put("diaFechaPresentacion",cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.MONTH, 1);
        map.put("mesFechaPresentacion",cal.get(Calendar.MONTH));
        map.put("anioFechaPresentacion", cal.get(Calendar.YEAR));

        map.put("montoDeposito",docPlanillaEntity.getMontoOperacion());
        map.put("nroComprobante",docPlanillaEntity.getNumOperacion());

        parametro= vperPersonaEntity.getRlNombre();
        if(parametro==null)
            parametro=" ";
        map.put("nombreEmpleador", parametro);

        parametro= vperPersonaEntity.getRlNroIdentidad();
        if(parametro==null)
            parametro=" ";
        map.put("nroDocumento", parametro);
        map.put("lugarPresentacion", "Oficina Virtual");

        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        String pathReport = (String) servletContext.getRealPath("/");
        map.put("escudoBolivia", pathReport+"/pages/reportes/escudo.jpg");
        map.put("logo",pathReport+"/pages/reportes/logoMIN.jpg");

        try {
            generateReport();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR al generar el reporte: "+e.getMessage());
        }
    }

    public void generateReport()throws ClassNotFoundException, IOException,JRException {
        List<String> lista= new ArrayList<String>();
        lista.add("asd");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        //Obteniendo el origen del jasper
        InputStream reportStream = facesContext.getExternalContext().getResourceAsStream("/pages/reportes/formularioLC1010V1.jasper");
        //Obteniendo la salida del pdf
        FacesContext.getCurrentInstance().responseComplete();
        ServletOutputStream servletOutputStream = response.getOutputStream();
        //generando el tipo de documento a mostrar
        response.setContentType("application/pdf");
        //generando el JasperRunManager con los datos
        JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, new JRBeanCollectionDataSource(lista));

        //terminando el proceso
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}

