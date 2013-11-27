package bo.gob.mintrabajo.ovt.bean.crontab;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Calendar;

import static org.quartz.CronScheduleBuilder.cronSchedule;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/26/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class CronScheluder {

    public CronScheluder() throws Exception {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        JobDetail job;
        Trigger trigger;
        String group = "jobsMc4";
        String cron;

        cron = generateCronExpression();
        System.out.println("cron: " + cron);

        job = JobBuilder.newJob(TareaProgramadaJob.class).withIdentity("EnvioEmail", group).build();
        trigger = TriggerBuilder.newTrigger().withIdentity("EnvioEmail" + "TriggerName", group).withSchedule(cronSchedule(cron)).build();
        scheduler.scheduleJob(job, trigger);
    }

    private String generateCronExpression() {
        String cron = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(TareaProgramadaBean.execDate);
        int sec = cal.get(Calendar.SECOND);
        int min = cal.get(Calendar.MINUTE);
        int hou = cal.get(Calendar.HOUR_OF_DAY);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int mon = cal.get(Calendar.MONTH) + 1;
        if (TareaProgramadaBean.execPeriodic) {
            cron = sec + " " + min + " " + hou + " ? * " + generateDays();
        } else {
            cron = sec + " " + min + " " + hou + " " + day + " " + mon + " " + "?";
        }
        return cron;
    }

    private String generateDays() {
        String days = "";
        if (TareaProgramadaBean.execMon) {
            days += "MON";
        }
        if (TareaProgramadaBean.execTue) {
            days += days.isEmpty() ? "TUE" : ",TUE";
        }
        if (TareaProgramadaBean.execWed) {
            days += days.isEmpty() ? "WED" : ",WED";
        }
        if (TareaProgramadaBean.execThu) {
            days += days.isEmpty() ? "THU" : ",THU";
        }
        if (TareaProgramadaBean.execFri) {
            days += days.isEmpty() ? "FRI" : ",FRI";
        }
        if (TareaProgramadaBean.execSat) {
            days += days.isEmpty() ? "SAT" : ",SAT";
        }
        if (TareaProgramadaBean.execSun) {
            days += days.isEmpty() ? "SUN" : ",SUN";
        }
        return days.isEmpty() ? "*" : days;
    }
}
