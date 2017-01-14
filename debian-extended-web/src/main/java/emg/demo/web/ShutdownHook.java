package emg.demo.web;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShutdownHook extends HttpServlet {
    @Override
    public void doPost (HttpServletRequest hreq, HttpServletResponse hres) throws IOException {
        System.exit(0);
    }
}
