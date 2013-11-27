package bo.gob.mintrabajo.ovt.bean.crontab;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/26/13
 * Time: 10:26 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class TareaProgramadaBean {

    public static java.sql.Timestamp execDate = new Timestamp(new Date().getTime());
    public static boolean execEnabled;
    public static boolean execPeriodic;
    public static boolean execMon;
    public static boolean execTue;
    public static boolean execWed;
    public static boolean execThu;
    public static boolean execFri;
    public static boolean execSat;
    public static boolean execSun;

    public void guardarTarea() {
        try {
            if (execEnabled) {
                Scheduler scheduler = new StdSchedulerFactory().getScheduler();
                scheduler.shutdown();
                new CronScheluder();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Tarea programada correctamente actualizada"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "Tarea programada no esta activada!"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Tarea programada no se actualizó!"));
        }
    }





    /// *** Getters && Setters *** ///
    public Timestamp getExecDate() {
        return execDate;
    }

    public void setExecDate(Timestamp execDate) {
        this.execDate = execDate;
    }

    public boolean isExecEnabled() {
        return execEnabled;
    }

    public void setExecEnabled(boolean execEnabled) {
        this.execEnabled = execEnabled;
    }

    public boolean isExecPeriodic() {
        return execPeriodic;
    }

    public void setExecPeriodic(boolean execPeriodic) {
        this.execPeriodic = execPeriodic;
    }

    public boolean isExecMon() {
        return execMon;
    }

    public void setExecMon(boolean execMon) {
        this.execMon = execMon;
    }

    public boolean isExecTue() {
        return execTue;
    }

    public void setExecTue(boolean execTue) {
        this.execTue = execTue;
    }

    public boolean isExecWed() {
        return execWed;
    }

    public void setExecWed(boolean execWed) {
        this.execWed = execWed;
    }

    public boolean isExecThu() {
        return execThu;
    }

    public void setExecThu(boolean execThu) {
        this.execThu = execThu;
    }

    public boolean isExecFri() {
        return execFri;
    }

    public void setExecFri(boolean execFri) {
        this.execFri = execFri;
    }

    public boolean isExecSat() {
        return execSat;
    }

    public void setExecSat(boolean execSat) {
        this.execSat = execSat;
    }

    public boolean isExecSun() {
        return execSun;
    }

    public void setExecSun(boolean execSun) {
        this.execSun = execSun;
    }

    public Date getExecDateSimple() {
        return new Date(execDate.getTime());
    }

    public void setExecDateSimple(Date execDateSimple) {
        this.execDate = new Timestamp(execDateSimple.getTime());
    }
}
