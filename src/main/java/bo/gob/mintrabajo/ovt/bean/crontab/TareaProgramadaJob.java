package bo.gob.mintrabajo.ovt.bean.crontab;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/26/13
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class TareaProgramadaJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("SE EJECUTO LA TAREA PROGRAMADA !!!!!!!!!!!!!!!!!!!!!!!!!");

        if (!TareaProgramadaBean.execPeriodic) {
            try {
                Scheduler scheduler = new StdSchedulerFactory().getScheduler();
                scheduler.unscheduleJob(jobExecutionContext.getTrigger().getKey());
            } catch (Exception e) {

            }
        }
    }
}
