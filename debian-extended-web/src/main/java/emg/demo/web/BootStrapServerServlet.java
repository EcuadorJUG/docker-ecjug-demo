package emg.demo.web;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class BootStrapServerServlet extends HttpServlet {
    @Override
    public void doGet (HttpServletRequest hreq, HttpServletResponse hres)
            throws ServletException, IOException {
        String page = "<html><head><title>Embedded Tomcat</title></head>"
                + "<body>"
                + "<script type='text/javascript'>"
                + "function reallysure() { return confirm('Desea detener el servidor?'); }"
                + "</script>"
                + "<h1>Tomcat ECJUG demo server!</h1>"
                + "<form method='POST' action='/shutdown'>"
                + "Para detener el servidor presione en detener<br/>"
                + "<input type='submit' value='Detener' onclick='return reallysure()'/>"
                + "</form></body></html>";
        OutputStream out = hres.getOutputStream();
        out.write(page.getBytes());
        out.close();
    }
}
