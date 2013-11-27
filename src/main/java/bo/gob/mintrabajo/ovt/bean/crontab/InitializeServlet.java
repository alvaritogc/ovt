package bo.gob.mintrabajo.ovt.bean.crontab;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/26/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitializeServlet extends HttpServlet {
    public void init() throws ServletException {
        try {
            getServletContext().getRealPath("/");
            new CronScheluder();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
