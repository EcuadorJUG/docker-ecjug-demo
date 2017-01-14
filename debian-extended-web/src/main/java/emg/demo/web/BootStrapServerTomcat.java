package emg.demo.web;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import java.io.File;

public class BootStrapServerTomcat {
    public static void main(String[] args) throws LifecycleException {
        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        File base = new File(System.getProperty("java.io.tmpdir"));
        Context ctx = tomcat.addContext("", base.getAbsolutePath());
        Tomcat.addServlet(ctx, "bootstrap", new BootStrapServerServlet());
        ctx.addServletMapping("/*", "bootstrap");
        // killer servlet
        Tomcat.addServlet(ctx, "ShutdownHook", new ShutdownHook());
        ctx.addServletMapping("/shutdown", "ShutdownHook");

        tomcat.start();
        tomcat.getServer().await();
    }
}

