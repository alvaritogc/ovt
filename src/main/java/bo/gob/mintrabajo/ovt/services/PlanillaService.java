package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPlanillaService;
import bo.gob.mintrabajo.ovt.entities.DocPlanillaEntity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void generaReporte(DocPlanillaEntity docPlanillaEntity){
        map.clear();
        map.put("mesPresentacion", docPlanillaEntity.getPeriodo());
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
        map.put("email",docPlanillaEntity.getIdEntidadBanco()); //----------------
        map.put("diaDeposito",docPlanillaEntity.getFechaOperacion().getDay());
        map.put("mesDeposito",docPlanillaEntity.getFechaOperacion().getMonth());
        map.put("anioDeposito",docPlanillaEntity.getFechaOperacion().getYear());
        map.put("montoDeposito",docPlanillaEntity.getMontoOperacion());
        map.put("nroComprobante",docPlanillaEntity.getNumOperacion());
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

