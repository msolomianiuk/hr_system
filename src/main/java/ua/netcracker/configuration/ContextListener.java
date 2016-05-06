package ua.netcracker.configuration;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * Class to initialize the logger <b>log4j</b>.
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Initialize log4j when the application is being started.
     *
     * @param event The event class for notifications about changes to the servlet context of a web application
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // initialize log4j here
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;

        PropertyConfigurator.configure(fullPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

}